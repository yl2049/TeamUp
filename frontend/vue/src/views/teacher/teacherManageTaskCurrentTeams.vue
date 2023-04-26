<template>
<!--      <h1>This is a manage Task Current Teams page</h1>-->
  <div>
    <!--    search bar-->
    <div style="margin-bottom: 5px; margin-left: 5px; margin-top: 10px ">
      <el-input style="width: 240px" placeholder="Team Num" v-model="params.team_id"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Student Name" v-model="params.student_name"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i> Search</el-button>
      <el-button style="margin-left: 5px" type="info" plain round @click="reset"> Reset</el-button>
    </div>


    <!--        <h1>tableData : {{tableData}}</h1>-->
    <div style="margin-left: 5px; margin-right: 5px">
      <el-table :data="tableData" stripe >
        <el-table-column prop="team_id" label="Team Num"  min-width="8%" ></el-table-column>
        <el-table-column prop="team_size" label="Team Size" min-width="8%"></el-table-column>
        <el-table-column prop="usersInfo" label="Team Members" min-width="100%">
<!--          <div v-for="users in usersInfo" :key="users.index">-->
<!--            <div>-->
<!--              <span >Task: a</span>-->
<!--            </div>-->
<!--          </div>-->
        </el-table-column>
<!--        <el-table-column prop="register_code" label="Register Code" min-width="45%"></el-table-column>-->
        <el-table-column label=" " min-width="30%">
<!--          <template v-slot="scope">-->
<!--            <el-button-->
<!--                size="mini"-->
<!--                type="success"-->
<!--                @click="$router.push({ name: 'teacherEnterCourse', params: { course_id: scope.row.course_id } })">ENTER</el-button>-->
<!--          </template>-->
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
  name: "teacherManageTaskCurrentTeams",
  data() {
    return {
      tableData: [],
      usersInfo:[],
      total: 0,
      params:{
        pageNum:1,
        pageSize:10,
        task_id: this.$route.params.task_id,
        course_id: this.$route.params.course_id
      }
    }
  },
  created() {
    this.load()
    // console.log(this.tableData)
  },
  methods:{
    load(){
      // this.tableData = 2
      request.get('/course/team/page', {
        params:this.params
      }).then(res =>{
        if(res.team_result.success){
          // console.log("HERE!!!")
          this.tableData = res.team_result.detail
          // this.usersInfo = JSON.parse(tableData)
        }
        if(res.num_of_teams.success){
          this.total = res.num_of_teams.detail
        }
      })
    },
    reset(){
      this.params = {
        pageNum:1,
        pageSize:10,
        task_id: this.$route.params.task_id,
        course_id: this.$route.params.course_id
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