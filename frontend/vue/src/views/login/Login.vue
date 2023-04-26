<template>
  <div style="width: 500px; height: 40vh; overflow: hidden; background-color: white; border-radius: 10px;
          margin: 150px auto; padding:50px">
      <el-card class="box-card">
<!--        <h2>Login</h2>-->
        <div style="margin: 30px; text-align: center; font-size: 30px; font-weight: bold; color: black">Log In</div>
        <el-form
          :model="ruleForm"
          status-icon
          :rules="rules"
          ref="ruleForm"
          label-position="left"
          label-width="100px"
          class="login-from"
        >
          <el-form-item label="Email" prop="emailAddress">
            <el-input
              type="emailAddress"
              v-model="ruleForm.emailAddress"
              autocomplete="on"
              placeholder="@illinois.edu">
            </el-input>
          </el-form-item>

          <el-form-item label="Password" prop="password">
            <el-input
              type="password"
              v-model="ruleForm.password"
              autocomplete="off">
            </el-input>
          </el-form-item>
        </el-form>
        <div class="btnGroup">
          <el-button  @click="resetForm('ruleForm')">Reset</el-button>
          <el-button style="margin-left:10px; width: 30%" size="medium" type="primary" @click="submitForm('ruleForm')">Login</el-button>
          <router-link to="/register">
            <el-button style="margin-left:10px">Register</el-button>
          </router-link>
        </div>
      </el-card>
    </div>
  </template>
  
  <script>
  import request from "@/utils/request";
  import Cookies from 'js-cookie';

  export default {
    data() {
      return {
        ruleForm: {
          emailAddress: "",
          password: "",
        },
        rules: {
          emailAddress: [
            { required: true, message: "Please enter a valid email！", trigger: "blur" },
          ],
          password: [
            { required: true, message: "Please enter your password！", trigger: "blur" },
          ],
        },
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          this.loading = true;
          if (valid) {
            let _this = this;
            request.post('/user/login', {
                emailAddress: _this.ruleForm.emailAddress,
                password: _this.ruleForm.password,
              }).then((res) => { // res : response from backend
                console.log(res)
              if (res.code == 200) {  // success
                // TODO: session to JWT
                // sessionStorage.setItem("userInfo", JSON.stringify(res.detail.jwt));
                // sessionStorage.setItem("userInfo", JSON.stringify(res.detail));

                // this.$notify.success("Login Success!")

                Cookies.set('user', JSON.stringify(res.detail))
                // if (res.detail!=null) {
                  if (res.detail.identity == '0') {
                    this.$router.push('/home')
                  } else if (res.detail.identity == '1') {
                    this.$router.push('/teacher/home')
                  }
                // }

                // jump to home; TODO: jump to user profile
                // this.$router.push('/home');
                // this.$router.push('/user/' + res.detail.userId);
                // display msg
                this.$message({
                  message: res.msg,
                  type: "success",
                }
                );
              } else {                      // failed
                // display msg
                this.$message({
                  message: res.msg,
                  type: "warning",
                });
              }
              _this.loading = false;
            });
          } else {
            console.log("cannot submit!");
            this.loading = false;
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    },
  };
  </script>
  
  <style scoped>
  .box-card {
    margin: auto auto;
    width: 400px;
  }
  .login-from {
    margin: auto auto;
  }
  </style>