<template>
  <div>

    <!--        <h1>tableData : {{tableData}}</h1>-->
    <div style="margin-left: 10%; width: 80%; margin-top: 30px;">
      <el-table :data="tableData" stripe :border="true" style="width: 100%">
        <el-table-column type="expand">
          <template #default="props">
            <h3 style="margin-left: 15%;">Teammates</h3>
              <el-table :data="props.row.teammates" :border="true" style="width: 80%; margin-left: 10%; margin-bottom: 10px;">
                <el-table-column label="UserId" prop="userId" />
                <el-table-column label="UserName" prop="name"></el-table-column>
              </el-table>
          </template>
        </el-table-column>
        <el-table-column label="CourseName" prop="course_name" />
        <el-table-column label="TaskName" prop="task_name" />
      </el-table>
    </div>
  </div>
</template>


<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';

export default {
  name: 'studentMyTeams',
  data() {
    return {
      tableData: [
        {
          course_name: 'CS411', 
          task_name: 'HW1',
          teammates: [
            {
              user_id: 'Jerry',
              user_name: 'California'
            }
          ],
        },
        {
          course_name: 'CS411', 
          task_name: 'HW2',
          teammates: [
            {
              user_id: 'Jerry',
              user_name: 'California'
            }
          ],
        },
      ],
      current_user_id: -1
    }
  },
  created() {
    this.current_user_id = this.$parent.user.userId
    //console.log("THE USER ID IS: ",this.$parent.user.userId,)
    //console.log("THE identity IS: ",this.$parent.user.identity,)
    this.load()
    console.log(this.tableData)
  },
  methods:{
    load(){
      request.get('/team/user/' + this.current_user_id).then(res =>{
        console.log(res)
        var alldata = []
        for (var course_name in res.detail) {
          for (var task_name in res.detail[course_name]) {
            for (var teamid in res.detail[course_name][task_name]) {
              var part = {}
              part["course_name"] = course_name
              part["task_name"] = task_name
              part["teammates"] = res.detail[course_name][task_name][teamid]
              alldata.push(part)
            }
          }
        }
        this.tableData = alldata
      })
    }
  }
}
</script>

<style scoped>

</style>