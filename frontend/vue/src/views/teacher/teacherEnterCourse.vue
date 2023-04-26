<template>
  <div>

    <div class="entercourse">
      <h1 style="margin-bottom: 5px; margin-left: 5px; margin-top: 15px ">{{course_num}}: {{course_name}}</h1>
    </div>

    <div v-if="THEREISTASK">
      <div style="margin-left: 30px;margin-top: 30px; margin-right: 50px">
        <el-collapse v-model="activeName" accordion>
          <div v-for="theresult in items_in_tasks" :key="theresult.index">
<!--            <div v-if="task.courseId === course_id">-->
            <el-collapse-item>
              <span class="collapse-title" slot="title">Task {{theresult.index}}:  {{theresult.task_name}}</span>
              <span class="collapse-title" slot="name">{{theresult.index}}</span>
              <div style="margin-right: 20px;margin-left: 20px">
                <el-container>
                    <el-container>
                      <el-main>
<!--                        <div> -&ndash;&gt; some description about this task：</div>-->
                        <div style="text-align: left">
                          <span> ---> THE DDL is: {{theresult.ddl}} </span>
                          <span style="margin-left: 50px"> ---> Num of current teams：{{theresult.count_team}} </span>
                        </div>
                        <div style="text-align: left"> ---> Description: {{theresult.description}}</div>
<!--                        <div style="text-align: left"> -&ndash;&gt; Num of current teams：{{theresult.count_team}} </div>-->
                      </el-main>
                      <el-footer>
                        <el-button size="medium" style="margin-left: 5px" type="info" class="button3" plain round @click="view_current_team(theresult.task_id)"> View Current Teams</el-button>
                        <el-button size="medium" style = "margin-right: 10px" type="danger" round @click="generateRandomGroups(theresult.task_id)">Generate Random Groups</el-button>
                        <el-button size="medium" style="margin-left: 5px" type="info" class="button3" plain round @click="manage_current_team(theresult.task_id)">Manage this Task</el-button>
                      </el-footer>
                    </el-container>
                  </el-container>
              </div>
            </el-collapse-item>
<!--              </div>-->
          </div>
            <div >
              <el-card class="box-card">
                <el-button style="text-align: center;font-size: larger; padding: 3px 0" class="button2" type="plain" @click="add_more_asks">Add More Tasks</el-button>
<!--                <span style="text-align: center;font-size: larger; padding: 3px 0" @click="add_more_asks">Add More Tasks</span>-->
              </el-card>
            </div>
        </el-collapse>
          </div>

      </div>
<!--    <div v-else>-->
<!--      <el-card class="box-card">-->
<!--        <div class="between-box-card-and-no-task-content">-->
<!--          <span class="no-task-content" >Take a breather!!! There is no task ~</span>-->
<!--        </div>-->
<!--      </el-card>-->
<!--    </div>-->


    <!--      the dialog form-->
<!--    <el-dialog title="JOIN A TEAM" :visible.sync="dialogFormVisible">-->
<!--      <el-form :model="form">-->
<!--        <el-form-item label="TEAM ID" :label-width="formLabelWidth">-->
<!--          <el-input v-model="form.name" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        &lt;!&ndash;        <el-form-item label="Zones" :label-width="formLabelWidth">&ndash;&gt;-->
<!--        &lt;!&ndash;          <el-select v-model="form.region" placeholder="Please select a zone">&ndash;&gt;-->
<!--        &lt;!&ndash;            <el-option label="Zone No.1" value="shanghai"></el-option>&ndash;&gt;-->
<!--        &lt;!&ndash;            <el-option label="Zone No.2" value="beijing"></el-option>&ndash;&gt;-->
<!--        &lt;!&ndash;          </el-select>&ndash;&gt;-->
<!--        &lt;!&ndash;        </el-form-item>&ndash;&gt;-->
<!--      </el-form>-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--&lt;!&ndash;        <div style="text-align: center; margin-top: 5px">&ndash;&gt;-->
<!--          <el-button @click="dialogFormVisible = false">Cancel</el-button>-->
<!--            <el-button type="primary" @click="">Confirm</el-button>-->
<!--        &lt;!&ndash;        </div>&ndash;&gt;-->
<!--  </span>-->
<!--    </el-dialog>-->


  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';

export default {
  name: "teacherEnterCourse",
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
      generateRandomGroupsRequest:{
        course_id:" ",
        task_id:" "
      }
      // form: {
      //   name: '',
      //   region: '',
      //   date1: '',
      //   date2: '',
      //   delivery: false,
      //   type: [],
      //   resource: '',
      //   desc: ''
      // },
      // formLabelWidth: '120px'
      // numberOfTeams:0
    }
  },
  created() {
    this.get()
  },
  methods:{
    get(){
      const course_id = this.$route.params.course_id
      // console.log("The course id is: "+course_id)
      request.get("/course/tempQ2/"+course_id+"/"+this.$parent.user.userId).then(res => {
            this.course_num = res.courses_result.detail[0].course_num
            this.course_name = res.courses_result.detail[0].course_name
            this.items_in_tasks = res.tasks_result.detail
        // console.log("items_in_tasks is: ", items_in_tasks)
        // this.activeName = res.tasks_result.detail.length
        // console.log("items_in_tasks is:")
        // console.log(items_in_tasks)
            // if (this.items_in_tasks.length===0){
            //   this.THEREISTASK = false
            // }
            // this.activeName= '1'
          }
      )
    },
    // join_A_team(){
    //   this.dialogFormVisible = false
    // },
    view_current_team(task_id){
      this.$router.push({ name: 'teacherManageTaskCurrentTeams', params: { course_id: this.$route.params.course_id, task_id: task_id } })
    },
    add_more_asks(){
      this.$router.push({ name: 'teacherAddTask', params: { course_id: this.$route.params.course_id } })
    },
    manage_current_team(task_id){
      this.$router.push({ name: 'teacherManageTask', params: { course_id: this.$route.params.course_id, task_id: task_id } })
    },
    generateRandomGroups(task_id){
      this.generateRandomGroupsRequest.course_id = this.$route.params.course_id
      // console.log(task_id)
      this.generateRandomGroupsRequest.task_id = task_id
      request.post('/course/generateRandomGroups', this.generateRandomGroupsRequest).then(res =>{
        if (res.success){
          this.$notify.success("Generated Successfully")
        }else{
          this.$notify.error("!!!ZERO student current enrolled in this course!!!")
        }
      })
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
  /*height: 70px;*/
}

.button1{
  background: #FF686B;
  font-weight: bold;
}
.button2{
  color: #333333;
  /*font-weight: bolder;*/
}
.button3{
  font-weight: bolder;
}

/*/deep/ .box-card {*/
/*  margin-left: calc(20vw - 50px);*/
/*  margin-top: 100px;*/
/*  width: 560px;*/
/*}*/

.box-card {
  margin-left: 50px;
  margin-top: 10px;
  margin-right: 50px;
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
  /*height: 70px;*/

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
  height: 200px;
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