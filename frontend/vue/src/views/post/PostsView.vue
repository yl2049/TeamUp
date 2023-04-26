<template>
  <div>

    <!-- Title -->
    <template>
      <div style="margin-left: -11%; margin-top: 10px; color: #303133">
        <h1> Welcome to {{course_num}}: {{course_name}}</h1>
        <h1> Task #{{task_id}}  </h1>
      </div>
    </template>

    <!-- Create new posts -->
    <el-button type="primary" @click="canCreate()" style="margin-left: -11%;">Create a Post</el-button>

      <el-dialog v-if="canCreatePost" title="Create a New Post !" :visible.sync="dialogVisible" width="65%" style="margin-left: 0%" >
        <el-card style="margin-left: 7%" class="box-card-choices" >
          <div slot="header" class="perf-header">
            <span>How do you want your team to be?</span>
            <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
          </div>

          <div style="margin-left: 10px;">
            <el-checkbox-group v-model="checkboxGroupWorkStyleCreatePost" :max="1">
              <el-checkbox v-for="prefer in workStylePrefer" :label="prefer" :key="prefer">{{prefer}}</el-checkbox>
            </el-checkbox-group>
          </div>

          <div style="margin-top: 10px; margin-left: 10px;">
            <el-checkbox-group v-model="checkboxGroupExperienceLevelCreatePost" :max="1">
              <el-checkbox v-for="prefer in experiencePrefer" :label="prefer" :key="prefer">{{prefer}}</el-checkbox>
            </el-checkbox-group>
          </div>

          <div style="margin-top: 10px; margin-left: 10px; margin-bottom: 10px;">
            <el-checkbox-group v-model="checkboxGroupWorkOnWeekendCreatePost" >
              <el-checkbox v-for="prefer in weekendPrefer" :label="prefer" :disabled="prefer==='Work on Weekdays'" :key="prefer">{{prefer}}</el-checkbox>
            </el-checkbox-group>
          </div>

          <el-input
            type="textarea"
            :rows="2"
            placeholder="Add additional comments (optional)"
            v-model="additionalComments">
          </el-input>

        </el-card>
        <!-- Footer: cancel/create post -->
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">Maybe Later..</el-button>
          <!-- Dialog is closed after successfully create new post -->
          <el-button type="primary" @click="createPost()">Create Now!</el-button>
        </span>
      </el-dialog>



    <!-- Preference bar -->
    <el-card class="box-card-choices">
      <div slot="header" class="perf-header">
        <span>Select Your Team Preferences</span>
        <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
      </div>

      <div style="margin-left: 10px;">
        <el-checkbox-group v-model="checkboxGroupWorkStyle" :min="1">
          <el-checkbox v-for="prefer in workStylePrefer" :label="prefer" :key="prefer">{{prefer}}</el-checkbox>
        </el-checkbox-group>
      </div>

      <div style="margin-top: 10px; margin-left: 10px;">
        <el-checkbox-group v-model="checkboxGroupExperienceLevel" :min="1">
          <el-checkbox v-for="prefer in experiencePrefer" :label="prefer" :key="prefer">{{prefer}}</el-checkbox>
        </el-checkbox-group>
      </div>

      <div style="margin-top: 10px; margin-left: 10px;">
        <el-checkbox-group v-model="checkboxGroupWorkOnWeekend" >
          <el-checkbox v-for="prefer in weekendPrefer" :label="prefer" :disabled="prefer==='Work on Weekdays'" :key="prefer">{{prefer}}</el-checkbox>
        </el-checkbox-group>
      </div>
    </el-card>

    <!-- Show All Posts -->
    <el-row :gutter="10"  class="row-box">
      <!-- <div style="margin-left: 2%; margin-bottom: 2%;"> -->
        <template v-for="post_detail in tableData">
          <el-col :span="7" v-if="toRenderCheck(post_detail)" class="col-box">
            <el-card class="el-card">
              <div slot="header" class="clearfix">
                <div style="padding: 14px">
                  <el-avatar> {{post_detail.userName}} </el-avatar>
                  <el-button type="primary" @click="$router.push({ name: 'user', params: {user_id: post_detail.userId} })">{{post_detail.userName}}</el-button>
                </div>
                <el-button 
                  v-if="(post_detail.userId===user_id)" 
                  style="float: right;"
                  @click="delDialogVisible = true"
                  type="danger">Delete</el-button>
  
                  <el-dialog
                    title="WARNING"
                    :visible.sync="delDialogVisible"
                    width="30%">
                    <span>Are you certain to delete your post?</span>
                    <span slot="footer" class="dialog-footer">
                      <el-button @click="delDialogVisible = false">Not now</el-button>
                      <el-button type="danger" @click="deletePost()">Delete NOW!</el-button>
                    </span>
                  </el-dialog>

              </div>

              <div class="text-item">
                <div class="left-style">
                  Work Style:
                </div>
                <div class="right-style">
                  {{workStyleExplain[post_detail.workStyle]}}
                </div>
              </div>
              <div class="text-item">
                <div class="left-style">
                  Experience:
                </div>
                <div class="right-style">
                  {{experienceExplain[post_detail.previousExperience]}}
                </div>
              </div>
              <div class="text-item">
                <div class="left-style">
                  Work on Weekend:
                </div>
                <div class="right-style">
                  {{weekendExplain[post_detail.weekendAvailability]}}
                </div>
              </div>
              <div class="text-item">
                <div class="left-style">
                  Additional Comments:
                </div>
              </div>
              <div class="long-text">
                <div>
                  <p>{{post_detail.additionalComments}}</p>
                </div>
              </div>
              
            </el-card>
          </el-col>
        </template>
      <!-- </div> -->
    </el-row>

  </div>
</template>


<style>
  .perf-header {
    margin-left: 10px;
    margin-right: 10px;
  }
  .box-card-choices {
    margin-top: 10px;
    margin-bottom: 10px;
    margin-left: 10px;
    margin-right: 10px;
    width: 87.5%;  /* 21/24 */
  }
  .long-text{
    font-size: 14px;
    white-space: pre-line;
    text-align: left;
  }
  .text-item {
    font-size: 14px;
    display: flex;
  }
  .text-item .left-style{
    text-align: left;
    /* width: 10%; */
  }
  .text-item .right-style{
    text-align: left;
    margin-left: auto;
    width: 50%;
  }
  .row-box{
    margin-top: 10px;
    margin-bottom: 10px;
    margin-left: 10px;
    margin-right: 10px;
    display: flex;
    flex-flow: wrap;
  }
  .row-box .col-box {
    margin-top: 0px;
    margin-bottom: 10px;
    margin-left: 0px;
    margin-right: 0px;
  }
  .row-box .el-card{
    min-width: 100%;
    height: 100%;
    margin-top: 0px;
    margin-bottom: 0px;
    margin-left: 10px;
    margin-right: 10px;
    border: 0px;
    transition: all .5s;
  }
  .item {
    margin-bottom: 10px;
  }

  .clearfix:before {
    clear: none
  }
  .clearfix:after {
    display: table;
  }
  .clearfix:after {
    clear: none
  }
  /* .box-card {
    width: 45%;
  } */
</style>

<script>

import request from "@/utils/request";
const workStylePrefer = ["Deadline pushes", "Finish tasks ASAP", "Flexible"]
const experiencePrefer = ["No experience", "Not so experienced", "Very experienced", "EXPERT!"]
const weekendPrefer = ["Work on Weekdays", "Work on Weekends"]

export default {
  name: "PostsView",
  
  data() {
    return {
      tableData: [],
      post_details: ["workStyle", "previousExperience", "weekendAvailability", "timeSlot"],
      workStyleExplain: {"0": "Deadline pushes", "1": "Flexible", "2": "Finish tasks ASAP"},
      experienceExplain: {"0": "None", "1": "Not so experienced", "2": "Very experienced", "3": "EXPERT!"},
      weekendExplain: {"0": "NO", "1": "YES"},

      checkboxGroupWorkStyle: ["Deadline pushes", "Finish tasks ASAP", "Flexible"],
      workStylePrefer: workStylePrefer,
      checkboxGroupWorkOnWeekend: ["Work on Weekdays", "Work on Weekends"],
      weekendPrefer: weekendPrefer,
      checkboxGroupExperienceLevel: ["No experience", "Not so experienced", "Very experienced", "EXPERT!"],
      experiencePrefer: experiencePrefer,

      checkboxGroupWorkStyleCreatePost: [],
      checkboxGroupWorkOnWeekendCreatePost: ["Work on Weekdays"],
      checkboxGroupExperienceLevelCreatePost: [],

      dialogVisible: false,
      delDialogVisible: false,
      
      additionalComments: "",
      canCreatePost: true,

      course_num: "",
      course_name: "",
      user_id: "",
      user_name: "",
    }
  },

  created() {
    this.load()
    this.user_id = this.$parent.user.userId
    console.log("created(): user_id =", this.user_id)
  },

  methods: {
     load(){
      // course_id, task_id from from ==studentEnterCourse==
      var post_result = []
      this.task_id = this.$route.params.task_id
      this.course_id = this.$route.params.course_id

      request.get("/course/"+ this.course_id).then(res => {
        this.course_num = res.courses_result.detail[0].course_num
        this.course_name = res.courses_result.detail[0].course_name
      })

      request.get("post/" + this.course_id + "/getPost/" + this.task_id).then( res => {
        console.log(res)
        if (res.success) {
          for (let i = 0; i < res.detail.length; i++) {
            let temp = res.detail[i]
            request.get("/user/"+ temp.userId).then(async res => {
              if (res.success) {
                var userName = await res.detail.name
                // console.log("getUserName:", userName)
                temp.userName = userName
                post_result.push(temp)
              } else {
                console.log("userName request failed")
              }
            })
            
          }
          console.log(post_result)
          this.tableData = post_result
        } else {
          console.log("request failed.")
        }

      })
    },

     async getUserName(userId) {
      var userName = ""
       request.get("/user/"+ userId).then(async res => {
        if (res.success) {
          userName = await res.detail.name
          console.log("getUserName:", userName)
          return userName
        } else {
          console.log("userName request failed")
        }
      })
    
      
    },

    checkCurrUserHasOnePost() {
      var hasOnePost = false

      for (var post_detail of this.tableData) {
        console.log("canCreate():userId: ", post_detail.userId)
        if (post_detail.userId == this.user_id) {
          hasOnePost = true
          break
        }
      }

      return hasOnePost
    },

    deletePost() {
      request.post("post/deletePost/" + this.course_id + "/" + this.task_id + "/" + this.user_id).then(res => {
        console.log(res)
        if (res.success) {
          console.log("Delete post succeeded")
          this.$alert('Post Deleted!', 'Delete Succeeded!', {
            confirmButtonText: 'OK!',
            callback: action => {
              window.location.reload(); // reload window after clicking yay
            }
            });
        } else {
          console.log("Delete post failed.")
        }
      })
    },

    canCreate() {
      this.canCreatePost = !(this.checkCurrUserHasOnePost())
      
      // Decide to show dialog or not
      if (this.canCreatePost === false) {
        this.$alert('You already have one post! Please delete it and then create a new one!', 'Create Failed!', {
            confirmButtonText: 'I\'ll go check it',
            callback: action => {
            }
          })
        this.dialogVisible = false
      } else {
        this.dialogVisible = true
      }
    },

    createPost(){
      var returnMsg = ""
      console.log(this.checkboxGroupWorkOnWeekendCreatePost.length)
      console.log(this.checkboxGroupWorkStyleCreatePost.length)
      console.log(this.checkboxGroupExperienceLevelCreatePost.length)

      if (this.checkboxGroupWorkOnWeekendCreatePost.length < 1 || this.checkboxGroupWorkStyleCreatePost.length < 1 || 
          this.checkboxGroupExperienceLevelCreatePost.length < 1) {
            returnMsg = "need more selection"
            this.$alert('Please check you selected at least one element for each section!', 'Create Failed!', {
            confirmButtonText: 'I\'ll go check it',
            callback: action => {}
            });
          console.log("Post failed to create")
          
      } else { // Succeeded
        var weekendAvailability = "0"
        if (this.checkboxGroupWorkOnWeekendCreatePost.includes("Work on Weekends")){
          weekendAvailability = "1"
        }

        var expMapper = {"No experience": "0", "Not so experienced": "1", "Very experienced": "2", "EXPERT!": "3"}
        var experienceLevel = expMapper[this.checkboxGroupExperienceLevelCreatePost[0]]

        var workStyleMapper = {"Deadline pushes": "0", "Flexible":"1", "Finish tasks ASAP": "2"}
        var workStyle = workStyleMapper[this.checkboxGroupWorkStyleCreatePost[0]]

        console.log("weekend:", weekendAvailability)
        console.log("exp:", experienceLevel)
        console.log("work:", workStyle)
        console.log("post/addPost/" + this.$route.params.task_id + "/" + this.$route.params.course_id + "/" +
                      this.$parent.user.userId + "/" + weekendAvailability + "/" + experienceLevel + "/" + 
                      workStyle + "/" + this.additionalComments + "/")
        request.post("post/addPost/" + this.$route.params.task_id + "/" + this.$route.params.course_id + "/" +
                      this.$parent.user.userId + "/" + weekendAvailability + "/" + experienceLevel + "/" + 
                      workStyle + "/" + this.additionalComments + "/").then(res => {
          if (res.success == false) {
            console.log("Create post failed!")
          }
          else {
            console.log("Create post succeeded!")
          }
        })
        this.dialogVisible = false 
        this.$alert('Congratulations! You have created a new post!', 'Create Succeeded!', {
            confirmButtonText: 'Yay!',
            callback: action => {
              window.location.reload(); // reload window after clicking yay
            }
            });

      }
  

    },

    toRenderCheck(post_detail){
      var workStyle = post_detail.workStyle.toString()
      var experience = post_detail.previousExperience.toString()
      var weekendAvailability = post_detail.weekendAvailability.toString()

      // if user choices match all 3 conditions
      return (this.workStyleMap().includes(workStyle)) && 
            (this.experienceMap().includes(experience)) && 
            (this.workWeekendMap().includes(weekendAvailability)) 
    },

    workStyleMap() {
      var mappedResults = []
      var mapper = {"Deadline pushes": "0", "Flexible":"1", "Finish tasks ASAP": "2"}
      
      for (var i = 0; i < this.checkboxGroupWorkStyle.length; i++) {
        mappedResults.push(mapper[this.checkboxGroupWorkStyle[i]])
      }

      return mappedResults
    },

    experienceMap() {
      var mappedResults = []
      var mapper = {"No experience": "0", "Not so experienced": "1", "Very experienced": "2", "EXPERT!": "3"}
      
      for (var i = 0; i < this.checkboxGroupExperienceLevel.length; i++) {
        mappedResults.push(mapper[this.checkboxGroupExperienceLevel[i]])
      }

      return mappedResults   
    },

    workWeekendMap() {
      var mappedResults = []
      var mapper = {"Work on Weekdays": "0", "Work on Weekends": "1"}
  
      for (var i = 0; i < this.checkboxGroupWorkOnWeekend.length; i++) {
        mappedResults.push(mapper[this.checkboxGroupWorkOnWeekend[i]])
      } 

      return mappedResults
    },

    reset(){
      this.params = {
        pageNum:1,
        pageSize:10,
        task_id: ''
      }
      this.load()
    },

  }
}
</script>

<style scoped>

</style>

