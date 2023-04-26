<template>
<!--    <h1>This is a manage Task page</h1>-->
<!--  The head -->
  <div>
    <!--  <div style="display: flex">-->


  <div style="padding-top: 30px; padding-left: 20px; display: flex">

    <div>
<!--      <i class = "el-icon-back" style="font-size: large; font-weight: bolder"></i>-->
      <img src = "@/assets/out.png" alt = "" style="width: 20px; position: relative; margin-top: 0px">
      <span v-on:click="goback" style="cursor: pointer; padding-left: 5px; font-size: large">back</span>
    </div>
    <div style="flex: 1; margin-left: 30px; margin-top: 5px">
    <el-breadcrumb separator="/" style="font-size: large">
      <el-breadcrumb-item :to="{ name: 'teacherManageTaskInfo' }">Info</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ name: 'teacherManageTaskCurrentTeams' }">Current Teams</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ name: 'teacherManageTaskOperations' }">Operations</el-breadcrumb-item>
      <el-breadcrumb-item > </el-breadcrumb-item>
    </el-breadcrumb>
    </div>
  </div>

  <div style="flex: 1">
    <router-view/>

  </div>

  </div>



</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';

export default {
  name: "teacherManageTask",

  data(){
    return{
      temp:1
    }

  },

  methods:{
    goback(){
      this.$router.push({ name: 'teacherEnterCourse', params: { course_id: this.$route.params.course_id } })
    }
  }
}

</script>

<style scoped>
/*{cursor:pointer;}*/


</style>