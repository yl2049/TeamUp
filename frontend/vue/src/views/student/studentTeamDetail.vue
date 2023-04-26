<template>
  <div>

  <div class="teamdetail">
    <h1 style="margin-bottom: 5px; margin-left: 5px; margin-top: 15px ">{{this.course_num}} : {{this.course_name}} :TaskId {{this.task_id}}</h1>
    
  </div>
  <div style="margin-left: 130px;margin-top: 30px; margin-right: 150px">
    <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div v-if = "have_team">
          <span style="font-size: 50px;">Team {{team_id}} Details</span>
          <div v-if = "task_type == 1">
            <span style="font-size: 10px;"> ---- This is a self-assigned task, form your team here!</span>
          </div>
          <div v-else>
            <span style="font-size: 10px;"> ---- This is a RANDOM assign team task, you do not need to form your team.</span> 
          </div>
          
        </div>
        <div v-else>
          <span style="font-size: 50px;">You have no team in this task yet.</span>
        </div>
        
        <el-aside width="150px">
          <el-button :disabled = "!have_team||task_type == 0" size="medium" style="margin-bottom: 5px; margin-left: 10px;" type="success" round @click="dialogFormVisible= true">Invite User</el-button>
          <el-button :disabled = "!have_team||task_type == 0" size="medium" type="danger" round @click="quitBTN()">Quit Team</el-button>
        </el-aside>
      </div>
    </template>
    <div style="margin-left: 200px; margin-top: 30px; margin-right: 150px;display: flex;">
      <div v-for="(item,i) in this.teammates" class="text item">
        <div style="margin-left: 10px; margin-right: 10px">
          <el-card :body-style="{ padding: '0px' }">
              <div style="padding: 14px">
                <el-avatar> {{item.name[0]}} </el-avatar>
                <!-- <span>{{ 'Teammate ' + (i+1) + ':'}}</span> -->
                <el-link type="primary" @click="$router.push({ name: 'user', params: { user_id: item.userId } })">{{item.name}}</el-link>
              </div>
          </el-card>
        </div>
      </div>
    </div>
    
  </el-card>
  </div>

  <!--      the dialog form-->
  <el-dialog title="INVITE USER TO TEAM" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="netid" :label-width="formLabelWidth">
          <el-input v-model="invite_netid" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
          <el-button type="primary" @click="inviteToTeamConfirmBTN()">Confirm</el-button>
     </span>
    </el-dialog>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "studentTeamDetail",
  data() {
    return{
      course_num: " ",
      course_name: " ",
      course_id: -1,
      task_id: -1,
      user_id: -1,
      team_id: -1,
      have_team: false,
      dialogFormVisible: false,
      invite_netid: "",
      task_type: 1,
      // task_name: "",
      teammates: []
    }
  },
  created() {
      console.log("hi"+this.$route.params.course_id)
      console.log("hi"+this.$route.params.task_id)
      console.log("hi"+this.$parent.user.userId)
      this.course_id = this.$route.params.course_id
      this.task_id = this.$route.params.task_id
      this.user_id = this.$parent.user.userId
      request.get("/course/"+this.course_id).then(res => {
        //console.log(res)
        this.course_num = res.courses_result.detail[0].course_num
        this.course_name = res.courses_result.detail[0].course_name
      })
      request.get("/team/task/"+this.task_id+"/type").then(res => {
        
        this.task_type = res.detail
        console.log("this task is a task of type:"+this.task_type+"taskId:" + this.task_id)
      })
      // request.get("/team/task/"+this.task_id+"/name").then(res => {
        
      //   this.task_name = res.detail
      //   console.log("this task is a task of name:"+this.task_type+"taskId:" + this.task_id)
      // })
      this.get()
  },
  methods:{
    get(){
      request.get("/course/"+this.course_id+"/task/"+this.task_id+"/team/user/"+this.user_id).then(res => {
        if (res.success == false) {
          console.log("get teamid false")
        }
        else {
          this.team_id = res.detail
          console.log("get teamid success" + this.team_id)
          request.get("/team/"+this.team_id).then(res => {
            if (res.success == false) {
              console.log("get team false")
            }
            else {
              this.teammates = res.detail
              this.have_team = true
              console.log("get team success")
              console.log(this.teammates[0].name[0])
            }
          })
        }
      })
    },
    quitBTN(){      
      console.log("quit BTN")
      request.delete("/team/"+this.team_id+"/user/"+this.user_id).then(res => {
        if (res.success == false) {
          this.$notify.error("quit fail!")
        }
        else {
          this.$notify.success("quit success!")
        }
      })
    },
    inviteToTeamConfirmBTN(){
      console.log("inviteToTeamConfirm BTN")
      request.post("/team/"+this.team_id+"/courseid/"+this.course_id+"/taskid/"+this.task_id+"/invite/"+this.user_id+"/"+this.invite_netid).then(res => {
        if (res.success == false) {
          this.$notify.error("invite To Team fail!")
        }
        else {
          this.$notify.success("invite To Team success" + this.team_id)
        }
      })
      this.dialogFormVisible = false
    },
  }
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  /* height: 480px; */
}

</style>