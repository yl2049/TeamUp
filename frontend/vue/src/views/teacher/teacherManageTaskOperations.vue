<template>
  <div>
      <h1>This is a Task Operations page</h1>


    <el-button size="medium" style = "margin-top:20px; margin-right: 10px" type="danger" round @click="tryTOdeletethisTask">Delete This Task</el-button>

    <el-button size="medium" style = "margin-right: 10px" type="danger" round @click="generateRandomGroups">Generate Random Groups</el-button>

    <el-dialog title="---!!! DELETE This Task ??? ---" :visible.sync="DELETEdialogFormVisible">
<!--      <el-form :model="DELETEform">-->
<!--        &lt;!&ndash;        <el-form-item label="Zones" :label-width="formLabelWidth">&ndash;&gt;-->
<!--        &lt;!&ndash;          <el-select v-model="form.region" placeholder="Please select a zone">&ndash;&gt;-->
<!--        &lt;!&ndash;            <el-option label="Zone No.1" value="shanghai"></el-option>&ndash;&gt;-->
<!--        &lt;!&ndash;            <el-option label="Zone No.2" value="beijing"></el-option>&ndash;&gt;-->
<!--        &lt;!&ndash;          </el-select>&ndash;&gt;-->
<!--        &lt;!&ndash;        </el-form-item>&ndash;&gt;-->
<!--      </el-form>-->
      <span>
<!--        <div style="text-align: center; margin-top: 5px">-->
          <el-button @click="return_back">Cancel</el-button>
            <el-button type="primary" @click="submit_DELETE_form">Confirm</el-button>
        <!--        </div>-->
  </span>
    </el-dialog>

  </div>

</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';

export default {
  name: "teacherManageTaskOperations",
  data(){
    return{
      DELETEdialogFormVisible: false,
      DELETEform: {
        task_id: this.$route.params.task_id,
        course_id: this.$route.params.course_id
      },
      generateRandomGroupsRequest:{
        course_id:this.$route.params.course_id,
        task_id:this.$route.params.task_id
      }
    }},
  methods:{
    tryTOdeletethisTask(){
      this.DELETEdialogFormVisible= true
    },
    return_back(){
      this.DELETEdialogFormVisible= false
    },
    submit_DELETE_form(){
      // console.log("THE USER ADDED IS:", ADDform)
      this.DELETEform= {
        task_id: this.$route.params.task_id,
        course_id: this.$route.params.course_id
      }
      request.post('/course/teacher/deleteatask', this.DELETEform).then(res =>{
        if (res.success){
          this.$notify.success(res.detail)
          this.DELETEdialogFormVisible = false
          this.$router.push({ name: 'teacherEnterCourse', params: { course_id: this.$route.params.course_id } })
        }else{
          this.$notify.error(res.msg)
        }
      })
    },
    generateRandomGroups(){
      this.generateRandomGroupsRequest={
        course_id:this.$route.params.course_id,
        task_id:this.$route.params.task_id
      }
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

</style>