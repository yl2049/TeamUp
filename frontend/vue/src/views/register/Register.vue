<template>
    <div>
        <el-card class="box-card">
        <h2>Register</h2>
    <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-position="left"
        label-width="150px"
        class="demo-ruleForm">
            <el-form-item label="Email Address" prop="emailAddress">
                <el-input v-model="ruleForm.emailAddress" placeholder="@illinois.edu"></el-input>
            </el-form-item>

            <el-form-item label="Name" prop="name">
                <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>

            <el-form-item label="Gender" prop="gender">
                <el-select v-model="ruleForm.gender" placeholder="Select">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="Password" prop="pass">
                <el-input
                    type="password"
                    v-model="ruleForm.pass"
                    autocomplete="off">
                </el-input>
            </el-form-item>
            <el-form-item label="Confirm password" prop="password">
                <el-input
                    type="password"
                    v-model="ruleForm.password"
                    autocomplete="off">
                </el-input>
            </el-form-item>
                <el-form-item label="Verification code" prop="verificationCode">
                    <el-row type="flex" justify="center">
                    <el-col :span = "12">
                        <el-input
                            type="verificationCode"
                            v-model="ruleForm.verificationCode"
                            autocomplete="off">
                        </el-input>
                    </el-col>
                    <el-col :span = "12">
                        <el-button type="primary" @click="sendEmail('ruleForm')"  v-loading="loading">Send email</el-button>
                    </el-col>
                </el-row>
            </el-form-item>
    </el-form>
        <div class="btnGroup">
          <el-button type="primary" @click="submitForm('ruleForm')"  v-loading="loading"
            >Submit</el-button>
          <el-button @click="resetForm('ruleForm')">Reset</el-button>
          <el-button @click="goBack">Back</el-button>
        </div>
      </el-card>
    </div>
  </template>
  
  <script>
  import request from "@/utils/request";

  export default {
    data() {
        var validateEmail = (rule, value, callback) => {
            const regex = /@illinois.edu$/i;
            if (value === "") {
                callback(new Error("Please enter your Illinois email"))
            } else if (regex.test(value) === false) {
                callback(new Error("Please enter a valid Illinois email"))
            } else {
                callback();
            }
        };

      var validatePass = (rule, value, callback) => {
        if (value === "") {
            callback(new Error("Please enter password"));
        } else {
          if (this.ruleForm.checkPass !== "") {
            this.$refs.ruleForm.validateField("checkPass");
          }
            callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === "") {
            callback(new Error("Please re-enter password"));
        } else if (value !== this.ruleForm.pass) {
            callback(new Error("Passwords does not match!"));
        } else {
            callback();
        }
      };
      return {
        options: [{
            value: '0',
            label: 'Female'
        }, {
            value: '1',
            label: 'Male'
        }, {
            value: '2',
            label: 'Other'
        }],
        ruleForm: {
            emailAddress: "",
            name: "",
            gender: "0",
            pass: "",
            password: "",
            verificationCode: "",
        },
        rules: {
            emailAddress: [{ required: true, validator: validateEmail, trigger: "blur" },],
            name: [{ required: true, message: "", trigger: "blur" },],
            // gender: [{ required: true, message: "", trigger: "blur" },],
            pass: [{ required: true, validator: validatePass, trigger: "blur" }],
            password: [{ required: true, validator: validatePass2, trigger: "blur" },],
            verificationCode:[{required: false, message: "", trigger: "blur"}],
        },
        loading: false
      };
    },
    methods: {
        handleClick() {
            alert('button click');
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
            this.loading = true;
            if (valid) {
                let _this = this;
                request.post('/user/register', {
                    emailAddress: _this.ruleForm.emailAddress,
                    name: _this.ruleForm.name,
                    gender: _this.ruleForm.gender,
                    password: _this.ruleForm.password,
                    verificationCode: _this.ruleForm.verificationCode,
                }).then((res) => { // res : response from backend
                    console.log(res)
                if (res.success == true) {  // success
                    // display msg
                    this.$message({
                    message: res.msg,
                    type: "success",
                    });
                    this.$router.go(-1);
                } else {                      // failed
                    // display msg
                    this.$message({
                    message: res.msg,
                    type: "warning",
                    });
                }
                _this.loading = false;
                console.log(res);
                });
            } else {
                console.log("error submit!");
                this.loading = false;
                return false;
            }
            });
        },
        sendEmail(formName) {
            this.$refs[formName].validate((valid) => {
            this.loading = true;
            if (valid) {
                let _this = this;
                request.post('/user/sendVerification', {
                    emailAddress: _this.ruleForm.emailAddress,
                    name: _this.ruleForm.name,
                    gender: _this.ruleForm.gender,
                    password: _this.ruleForm.password,
                }).then((res) => { // res : response from backend
                    console.log(res)
                if (res.code == 201) {  // success
                    // display msg
                    this.$message({
                    message: res.msg,
                    type: "success",
                    });
                } else {                      // failed
                    // display msg
                    this.$message({
                    message: res.msg,
                    type: "warning",
                    });
                }
                _this.loading = false;
                console.log(res);
                });
            } else {
                console.log("error submit!!");
                this.loading = false;
                return false;
            }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        goBack() {
            this.$router.go(-1);
            // this.$router.go('/login');
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