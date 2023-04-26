<!-- <template>
  <h1>This is a home page</h1> -->

<!--  <div class="home">-->
<!--    <img alt="Vue logo" src="../assets/logo.png">-->
<!--    <HelloWorld msg="Welcome to Your Vue.js App"/>-->
<!--  </div>-->
<!--</template>-->

<!--<template>-->
<!--  <div>-->
<!--  </div>-->

<!-- </template> -->

<template>
    <div>
      <div>
        <el-avatar :size="100"> <span class="text-item">{{nameInitial}}</span> </el-avatar>
      </div>
      <h2>Welcome {{ user.name }}! Your uid is {{ user.userId }}</h2>
      <!-- <router-link to="/login">
        <el-button> Log out </el-button>
      </router-link> -->
    </div>
  </template>


<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'

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
      nameInitial: "",
    };
  },
  methods: {
    logout(){
      Cookies.remove('user');
      // sessionStorage.removeItem('userInfo');
      this.$message({
        message: "Logged out",
        type: "success",
      });
      this.$router.push('/login');
    }
  },
  mounted() {
    if (Cookies.get('user')) {
      this.user = JSON.parse(Cookies.get('user'));
      console.log(this.user.userId);
      if (this.user.name.split(" ")[1] != null) {
        this.nameInitial = this.user.name.charAt(0) + this.user.name.split(" ")[1].charAt(0)
      } else {
        this.nameInitial = this.user.name.charAt(0)
      }
    }
  },
};

</script>

<style scoped>
    .text-item {
        font-size: 30px;
    }
</style>