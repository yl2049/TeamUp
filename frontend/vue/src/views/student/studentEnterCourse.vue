<template>
  <div>

  <div class="entercourse">
    <h1 style="margin-bottom: 5px; margin-left: 5px; margin-top: 15px ">{{course_num}}: {{course_name}}</h1>
  </div>

    <div v-if="THEREISTASK">
      <div style="margin-left: 30px;margin-top: 30px; margin-right: 50px">
    <el-collapse v-model="activeName" accordion>
    <div v-for="task in items_in_tasks" :key="task.task_id">
        <el-collapse-item>
          <span class="collapse-title" slot="title">Task: {{task.task_name}}</span>
          <span class="collapse-title" slot="name">{{task.task_id}}</span>
          <div style="margin-right: 20px;margin-left: 20px">
<!--            <span class="collapse-content"> &ndash;&gt; some description about this task</span>-->
<!--            <div class="button1">-->
<!--              <el-button style="margin-left: 5px" type="info" plain round @click="JOIN">JOIN</el-button>-->
<!--            </div>-->
            <el-container>
<!--              <el-header>Header</el-header>-->
              <el-container>
                <el-container>
                  <el-main> ---> some description about this task</el-main>
                  <el-footer>
                    <el-button size="medium" style="margin-left: 5px" type="info" class="button3" plain round 
                    @click="$router.push({ name: 'studentTeamDetail', params: { task_id: task.task_id } })"
                    >Current Team</el-button>
                  </el-footer>
                </el-container>
                <el-aside width="200px">
                  <el-button size="medium" type="danger" round @click="dialogFormVisible= true">JOIN A Team</el-button>
                  <el-button size="medium" style="margin-left: 5px"  class="button2" type="success" plain round 
                              @click="$router.push({name: 'post', params: {course_id: course_id, task_id: task.task_id }})">VIEW POSTs</el-button>
                </el-aside>
              </el-container>
            </el-container>
          </div>
        </el-collapse-item>
    </div>
    </el-collapse>
      </div>
    </div>
    <div v-else>
      <el-card class="box-card">
          <div class="between-box-card-and-no-task-content">
            <span class="no-task-content" >Take a breather!!! There is no task ~</span>
          </div>
      </el-card>
    </div>


<!--      the dialog form-->
    <el-dialog title="JOIN A TEAM" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="TEAM ID" :label-width="formLabelWidth">
          <el-input v-model="form.team_id" autocomplete="off"></el-input>
        </el-form-item>
<!--        <el-form-item label="Zones" :label-width="formLabelWidth">-->
<!--          <el-select v-model="form.region" placeholder="Please select a zone">-->
<!--            <el-option label="Zone No.1" value="shanghai"></el-option>-->
<!--            <el-option label="Zone No.2" value="beijing"></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
      </el-form>
      <span slot="footer" class="dialog-footer">
<!--        <div style="text-align: center; margin-top: 5px">-->
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
            <el-button type="primary" @click="joinTeamConfirmBTN()">Confirm</el-button>
<!--        </div>-->
  </span>
    </el-dialog>


  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';

export default {
  name: "studentEnterCourse",
  data() {
    return{
      course_num: " ",
      course_name: " ",
      activeName: '1',
      // collapseTitle: "Task 1",
      // items_in_courses: [],
      items_in_tasks: [],
      THEREISTASK: true,
      dialogFormVisible: false,
      form: {
        team_id: ''
      },
      formLabelWidth: '120px',
      user_id: -1

      // activeName: '1'
    }
  },
  created() {
      this.user_id = this.$parent.user.userId
      this.course_id = this.$route.params.course_id
      this.get()
  },
  methods:{
    get(){
      const course_id = this.$route.params.course_id
      console.log(course_id)
      request.get("/course/"+course_id).then(res => {
        this.course_num = res.courses_result.detail[0].course_num
        this.course_name = res.courses_result.detail[0].course_name
        this.items_in_tasks = res.tasks_result.detail
        console.log(res)
        if (this.items_in_tasks.length===0){
          this.THEREISTASK = false
        }
        // this.activeName= '1'
          }
      )
    },
    joinTeamConfirmBTN(){
      console.log("joinTeamConfirm BTN")
      request.post("/team/"+this.form.team_id+"/join/"+this.user_id).then(res => {
        if (res.success == false) {
          this.$notify.error(res.msg)
        }
        else {
          this.$notify.success("join team success!")
        }
      })
      this.dialogFormVisible = false
    }
  }
}
</script>

<style scoped>
.no-task-content{
  font-size: 30px;
}
.between-box-card-and-no-task-content{
  /*color: #A5FFD6;*/
}
.collapse-title {
  flex: 1 0 50%;
  order: 0;
  padding-left: 25px;
}
.collapse-content{
  flex: 1 0 50%;
  order: 0;
  padding-left: 20px;
  font-size: medium;
}

.button1{
  background: #FF686B;
  font-weight: bold;
}
.button2{
  color: #FF686B;
  font-weight: bolder;
}
.button3{
  font-weight: bolder;
}

/deep/ .box-card {
  margin-left: calc(20vw - 50px);
  margin-top: 100px;
  width: 560px;
}

/deep/ .el-collapse {
  border: none;
}

/deep/ .el-collapse-item__header{
  font-size: 30px;
  color: crimson;
  background: #F8EDEB;
  /*padding: 70px;*/
  /*position: relative;*/
  /*height*/
  font-weight: bold;
}

/deep/ .el-collapse-item__wrap{
  /*font-size: 30px;*/
  color: crimson;
  /*font-weight: bold;*/

}

/deep/ .el-collapse-item__content{
  font-size: 30px;
  color: crimson;
  background: #F8F7FF;
}

/deep/ .el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 50px;
}

/deep/ .el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 120px;
  height: auto;
}

/deep/ .el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: -moz-left;
  line-height: 70px;
  font-size: small;
  height: 20px;
}

/deep/ .el-container {
  /*min-height: 10px;*/
  /*margin-bottom: 40px;*/
}

/*/deep/ .el-button{*/
/*  font-size: 30px;*/
/*  color: crimson;*/
/*}*/
</style>