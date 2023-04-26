<template>
  <div style="width: 500px; height: 40vh; overflow: hidden; background-color: white; border-radius: 10px;
          margin: 150px auto; padding:50px">
    <el-card class="box-card">
      <div style="margin: 30px; text-align: center; font-size: 30px; font-weight: bold; color: black">Log In</div>
      <el-form
          :model="loginInfo"
          status-icon
          :rules="rules"
          ref="loginInfo"
          label-position="left"
          label-width="100px"
          class="login-from"
      >

        <!-- <el-form-item label="Email" prop="email_address"> -->
          <el-form-item label="Email" prop="emailAddress">
          <el-input
              type="emailAddress"
              v-model="loginInfo.emailAddress"
              prefix-icon="el-icon-s-promotion"
              autocomplete="on"
              placeholder="@illinois.edu">
          </el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input
              type="password"
              show-password prefix-icon="el-icon-lock"
              v-model="loginInfo.password"
              autocomplete="off">
          </el-input>
        </el-form-item>
      </el-form>
      <div class="btnGroup">
       <router-link to="/register">
          <el-button style="margin-left:80px">Register</el-button>
       </router-link>
        <el-button style="margin-left:10px; width: 30%" size="medium" type="primary" @click="login">Login</el-button>
        <el-button @click="resetForm('ruleForm')">Reset</el-button>
      </div>
    </el-card>
  </div>
</template>


<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';

export default {
  name: 'login',
  data(){
    return{
      loginInfo: {
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
      }
    }
  },
  // created() {
  //
  // },
  methods:{
    // replace This!!!!!!
    login(){
      this.$refs['loginInfo'].validate((valid) => {
          if (valid){
            let _this = this;
            request.post('/user/login',this.loginInfo).then(res =>{
              if (res.success){
                this.$notify.success("Login Success!")
                if (res.detail!=null){
                  if (res.detail.identity == '0'){
                    this.$router.push('/')
                  }
                  else if (res.detail.identity == '1'){
                    this.$router.push('/teacher')
                  }
                  // !!!!!!!!!!!!!!!!!Cookie!!!!!!!!!!!!!!!!!!!!MAY NEED TO CHANGE!!!!!!!!!!!!!!!!!!!!!!!ASK YL!!!!!!!!!!!!!!!!!!!!!!!!!
                  Cookies.set('user', JSON.stringify(res.detail))
                }
              }
              else{
                this.$notify.error(res.msg)
              }
            })
          }
      })
    },
    resetForm() {
      this.$refs['loginInfo'].resetFields();
    }
  }

}

</script>

<!--<style scoped>-->
<!--/*.box-card {*/-->
<!--/*  margin: auto auto;*/-->
<!--/*  width: 500px;*/-->
<!--/*}*/-->
<!--.login-from {-->
<!--  margin: auto auto;-->
<!--}-->
<!--</style>-->