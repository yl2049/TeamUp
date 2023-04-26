<template>
    <div>
      <h2>Welcome {{ user.name }}! Your uid is {{ user.userId }}</h2>
      <router-link to="/login">
        <el-button> Log out </el-button>
      </router-link>
    </div>
  </template>
  
<script>
import Cookies from 'js-cookie';

export default {
  data() {
    return {
      user: {
        name: "",
        emailAddress: "",
        userId: null,
        jwt: "",
        gender: "",
        identity: "",
      },
    };
  },
  methods: {
    logout(){
<<<<<<< HEAD
      Cookies.remove('user');
=======
      sessionStorage.removeItem('userInfo');
      Cookies.remove('user')
<<<<<<< HEAD
>>>>>>> 578d24526f2acbb4c7b0e8584ee5d97faaee00b8
=======
>>>>>>> 83341a9efc56958351b5108666d98c0c9d92953b
      this.$message({
        message: "Logged out",
        type: "success",
      });
      this.$router.push('/login');
    }
  },
  mounted() {
    if (sessionStorage.getItem('userInfo')) {
      this.user = JSON.parse(sessionStorage.getItem('userInfo'));
    }
  },
};
</script>