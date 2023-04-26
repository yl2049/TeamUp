<template>
  <div>
    <!--    search bar-->
    <div style="margin-bottom: 5px; margin-left: 5px; margin-top: 10px ">
      <el-input style="width: 240px" placeholder="Course Num" v-model="params.course_num"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Course Name" v-model="params.course_name"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i> Search</el-button>
      <el-button style="margin-left: 5px" type="info" plain round @click="reset"> Reset</el-button>
    </div>


    <!--        <h1>tableData : {{tableData}}</h1>-->
    <div style="margin-left: 5px">
      <el-table :data="tableData" stripe >
        <el-table-column prop="course_num" label="Course Num"  min-width="30%" ></el-table-column>
        <el-table-column prop="semester" label="Semester" min-width="30%"></el-table-column>
        <el-table-column prop="course_name" label="Course Name" min-width="100%"></el-table-column>
        <el-table-column prop="register_code" label="Register Code" min-width="45%"></el-table-column>
        <el-table-column label=" " min-width="30%">
          <template v-slot="scope">
            <el-button
                size="mini"
                type="success"
                @click="$router.push({ name: 'entercourse', params: { course_id: scope.row.course_id } })">ENTER</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--    Page-->
    <div style="margin-top: 20px">
      <el-pagination
          background
          :current-page="params.pageNum"
          :page-size="params.pageSize"
          layout="prev, pager, next"
          @current-change="handlePages"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>


<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';

export default {
  name: 'studentMyCoursesView',
  data() {
    return {
      tableData: [],
      total: 0,
      params:{
        pageNum:1,
        pageSize:10,
        course_name: '',
        course_num:'',
        userId: this.$parent.user.userId,
        identity: this.$parent.user.identity
      }
    }
  },
  created() {
    this.load()
    console.log("THE USER ID IS: ",this.params.userId)
    console.log("THE identity IS: ",this.params.identity)
  },
  methods:{
    load(){
      request.get('/course/page', {
        params:this.params
      }).then(res =>{
        if(res.courses_result.success){
          console.log("HERE!!!")
          this.tableData = res.courses_result.detail
        }
        if(res.num_of_courses.success){
          this.total = res.num_of_courses.detail
        }
      })
    },
    reset(){
      this.params = {
        pageNum:1,
            pageSize:10,
            course_name: '',
            course_num:'',
            userId: this.$parent.user.userId,
            identity: this.$parent.user.identity
      }
      this.load()
    },
    handlePages(pageN){
      this.params.pageNum = pageN
      this.load()
    }
  }
}
</script>

<style scoped>

</style>