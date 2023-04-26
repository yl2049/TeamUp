<template>
    <div id="app">
  
      <!--    The Head-->
      <div style="height:60px; line-height: 60px; background-color: beige">
  
        <img src = "@/assets/team.png" alt = "" style="width: 40px; position: relative; top:10px;left:20px">
        <span style="margin-left: 25px; front-size:50px">TeamUp</span>
  
      </div>
  
  
      <div style="display: flex">
  
        <!--  The Side bar  -->
        <div style="width: 200px; min-height: calc(100vh - 80px); overflow:hidden;margin-right:2px; background-color: burlywood">
  
          <el-menu :default-active="$route.path" router class="el-menu-demo" >
            <el-menu-item index="/">Home</el-menu-item>
            <el-menu-item index="course">
              <i class = "el-icon-notebook-1"></i>
              <span>My Courses</span>
            </el-menu-item>
            <el-menu-item index="/">
              <!--            <i class = "el-icon-chat-dot-square"></i>-->
              <i class = "el-icon-user"></i>
              <span>My Teams</span>
            </el-menu-item>
            <!--          <el-submenu>-->
            <!--            <template slot="title">try</template>-->
            <!--            <el-menu-item index="/about">item one</el-menu-item>-->
            <!--            <el-menu-item index="2-2">item two</el-menu-item>-->
            <!--            <el-menu-item index="2-3">item three</el-menu-item>-->
            <!--          </el-submenu>-->
          </el-menu>
  
        </div>
  
        <!--  The Main  -->  <!-- style="flex: 1": give the remaining part to the Main Body -->
        <div style="flex: 1; background-color: bisque">
          <router-view/>
  
        </div>
  
  
      </div>
  
  
  
    </div>
  </template>
  
  
  <!--VUE默认模板-->
  <!--<style>-->
  <!--#app {-->
  <!--  font-family: Avenir, Helvetica, Arial, sans-serif;-->
  <!--  -webkit-font-smoothing: antialiased;-->
  <!--  -moz-osx-font-smoothing: grayscale;-->
  <!--  text-align: center;-->
  <!--  color: #2c3e50;-->
  <!--}-->
  
  <!--nav {-->
  <!--  padding: 30px;-->
  <!--}-->
  
  <!--nav a {-->
  <!--  font-weight: bold;-->
  <!--  color: #2c3e50;-->
  <!--}-->
  
  <!--nav a.router-link-exact-active {-->
  <!--  color: #42b983;-->
  <!--}-->
  <!--</style>-->
  