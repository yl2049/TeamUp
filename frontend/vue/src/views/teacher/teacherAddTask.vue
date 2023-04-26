<template>
<!--  <h1>This is a Add Task page</h1>-->
  <div style="margin-left: 50px; margin-right: 50px; margin-top: 35px">
    <span style="font-size: larger">
      Add a New Task
    </span>
  <div style="margin-top: 30px; padding-right: 150px; text-align: left">
    <el-form ref="form" :model="form" label-width="200px">
      <el-form-item label="Task name">
        <el-input v-model="form.task_name"></el-input>
      </el-form-item>
      <el-form-item label="Max Group Size" style="padding-right: 360px" >
        <el-input v-model="form.max_size" placeholder=" ( Default: 10 ) "></el-input>
      </el-form-item>
      <el-form-item label="min group size" style="padding-right: 360px" >
        <el-input v-model="form.min_size" placeholder=" ( Default: 1 ) "></el-input>
      </el-form-item>
<!--      <el-form-item label="Activity zone">-->
<!--        <el-select v-model="form.region" placeholder="please select your zone">-->
<!--          <el-option label="Zone one" value="shanghai"></el-option>-->
<!--          <el-option label="Zone two" value="beijing"></el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item label="Deadline">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="Pick a date" v-model="form.ddl" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
        </el-col>
        <el-col :span="11" style="padding-left: 10px">
          <el-time-picker type="time" value-format="hh:mm:ss" placeholder="Pick a time" v-model="form.ddl_time" style="width: 100%;"></el-time-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="Publish to students">
        <el-switch v-model="form.published"></el-switch>
      </el-form-item>
<!--      <el-form-item label="Activity type">-->
<!--        <el-checkbox-group v-model="form.type">-->
<!--          <el-checkbox label="Online activities" name="type"></el-checkbox>-->
<!--          <el-checkbox label="Promotion activities" name="type"></el-checkbox>-->
<!--          <el-checkbox label="Offline activities" name="type"></el-checkbox>-->
<!--          <el-checkbox label="Simple brand exposure" name="type"></el-checkbox>-->
<!--        </el-checkbox-group>-->
<!--      </el-form-item>-->
      <el-form-item label="Grouping Approach">
        <el-radio-group v-model="form.type">
<!--          TODO: label="" OR value = "" ???-->
          <el-radio label="0">Self-design</el-radio>
          <el-radio label="1">Random</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="Description">
        <el-input type="textarea" v-model="form.description" placeholder=" ( Optional ) "></el-input>
      </el-form-item>
    </el-form>

  </div>
    <div style="text-align: center">
      <el-button type="primary" @click="create">Create</el-button>
      <el-button @click="goback">Cancel</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "teacherAddTask",
  data() {
    return{
      form: {
        course_id:'',
        task_name: '',
        max_size: '',
        min_size: '',
        ddl: '',
        ddl_time: '',
        published: true, // default: as soon as the task is created, students can see it
        type: '',
        description:''
      }
      }
      // numberOfTeams:0
  },
  methods:{
    create() {
      // console.log(this.form)
      this.form.course_id = this.$route.params.course_id
      request.post('/course/addtask', this.form).then(res =>{
        if (res.success){
          this.$notify.success(res.msg)
          this.form= {
            course_id:'',
                task_name: '',
                max_size: '',
                min_size: '',
                ddl: '',
                ddl_time: '',
                published: true, // default: as soon as the task is created, students can see it
                type: '',
                description:''
          }


        }else{
          this.$notify.error("Failure to add")
        }
      })
    },
    goback(){
      this.$router.push({ name: 'teacherEnterCourse', params: { course_id: this.$route.params.course_id } })
    }
  }
}
</script>

<style scoped>

</style>