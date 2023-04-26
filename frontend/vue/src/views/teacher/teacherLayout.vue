<template>
  <div>
  <div>
    <!--    The Head-->
    <div style="height:60px; line-height: 60px; background-color: beige; display: flex">

      <div style="width: 300px">
        <img src = "@/assets/team.png" alt = "" style="width: 40px; position: relative; top:10px;left:20px">
        <span style="padding-left: 50px; front-size:50px; font-size: large">TeamUp</span>
      </div>

      <div style="flex: 1; text-align: right; padding-right: 50px">
        <el-dropdown size="medium">
          <span class="el-dropdown-link" style="cursor: pointer; font-size: large">
            {{ user.name }}
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown" style="margin-top: -5px">
            <el-dropdown-item><div style="width: 100px; text-align: center;" @click="logout">Log Out</div></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>

    </div>


    <div style="display: flex">

      <!--  The Side bar  -->
      <div style="width: 200px; min-height: calc(100vh - 80px); overflow:hidden;margin-right:2px; background-color: burlywood">
        <el-menu :default-active="$route.path === '/' ? $route.path : $route.path.substring(1)"  :default-openeds="['/']" router class="el-menu-demo" >
          <el-menu-item index="/teacher/home">Home</el-menu-item>
          <el-menu-item index="/teacher/course">
            <i class = "el-icon-notebook-1"></i>
            <span>My Courses</span>
          </el-menu-item>
          <el-submenu index="/teacher/course">
            <template slot="title">
              <i class = "el-icon-edit"></i>
              <span>Manage Courses</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/teacher/course" @click="Add_a_course">
                <i class = "el-icon-circle-plus"></i>
                <span>Add a Course</span>
                </el-menu-item>
              <el-menu-item index="/teacher/course" @click="delete_a_course">
                <i class = "el-icon-delete-solid"></i>
                <span>Delete a Course</span>
                </el-menu-item>
            </el-menu-item-group>
<!--            <el-menu-item-group title="Group Two">-->
<!--              <el-menu-item index="1-3">item three</el-menu-item>-->
<!--            </el-menu-item-group>-->
<!--            <el-submenu index="1-4">-->
<!--              <template slot="title">item four</template>-->
<!--              <el-menu-item index="1-4-1">item one</el-menu-item>-->
<!--            </el-submenu>-->
          </el-submenu>
          <!--        TODO: CHANGE /USER/555!!!-->
<!--          <el-menu-item index="/teacher/course">-->
<!--            <i class = "el-icon-notebook-1"></i>-->
<!--            <span>My Courses</span>-->
<!--          </el-menu-item>-->
<!--          <el-menu-item index="/user/1/teams">-->
<!--            &lt;!&ndash;            <i class = "el-icon-chat-dot-square"></i>&ndash;&gt;-->
<!--            <i class = "el-icon-user"></i>-->
<!--            <span>My Teams</span>-->
<!--          </el-menu-item>-->
<!--                    <el-submenu>-->
<!--                      <template slot="title">Add a Course</template>-->
<!--&lt;!&ndash;                      <el-menu-item index="/teacher/addACourse">item one</el-menu-item>&ndash;&gt;-->

<!--&lt;!&ndash;                      <el-menu-item index="2-2">item two</el-menu-item>&ndash;&gt;-->
<!--&lt;!&ndash;                      <el-menu-item index="2-3">item three</el-menu-item>&ndash;&gt;-->
<!--                    </el-submenu>-->
        </el-menu>
      </div>



      <!--  The Main  -->  <!-- style="flex: 1": give the remaining part to the Main Body -->
      <div style="flex: 1; background-color: bisque">
        <router-view/>

      </div>


    </div>

  </div>

  <!--    ADD  the dialog form-->
  <el-dialog title="ADD A Course" :visible.sync="ADDdialogFormVisible">
    <el-form :model="ADDform">
      <el-form-item style="text-align: left" label="Semester" :label-width="ADDformformLabelWidth">
                  <el-select v-model="ADDform.semester" placeholder="Please select a Semester">
                    <el-option label="Fall 2022" value="fall2022"></el-option>
                    <el-option label="Spring 2023" value="spring2023"></el-option>
                  </el-select>
      </el-form-item>
      <el-form-item label="Course Name" :label-width="ADDformformLabelWidth">
        <el-input v-model="ADDform.course_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Course Num" :label-width="ADDformformLabelWidth">
        <el-input v-model="ADDform.course_num" autocomplete="off"></el-input>
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
          <el-button @click="return_back">Cancel</el-button>
            <el-button type="primary" @click="submit_ADD_form">Confirm</el-button>
      <!--        </div>-->
  </span>
  </el-dialog>

    <!--    Delete  the dialog form-->

    <el-dialog title="---!!! DELETE A COURSE !!!---" :visible.sync="DELETEdialogFormVisible">
      <el-form :model="DELETEform">
        <el-form-item label="Register Code" :label-width="DELETEformformLabelWidth">
          <el-input v-model="DELETEform.register_code" autocomplete="off"></el-input>
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

  name: 'teacherLayout',

  data(){
    return{
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      ADDdialogFormVisible: false,
      DELETEdialogFormVisible: false,
      ADDform: {
        semester: '',
        course_name: '',
        course_num: '',
        userId: ''
      },
      ADDformformLabelWidth: '120px',
      DELETEform:{
        register_code:'',
        userId: ''
      },
      DELETEformformLabelWidth: '120px',
      Register_code_of_new_course : ''
    }
  },
  // created() {
  //
  // },
  methods:{
    logout(){
      Cookies.remove('user')
      this.$router.push('/login')
    },
    Add_a_course(){
      this.ADDform= {
        semester: '',
            course_name: '',
            course_num: '',
            userId: ''
      }
      this.ADDdialogFormVisible = true
      // this.$router.push('/teacher/course')
    },
    delete_a_course(){
      this.DELETEform={
        register_code:'',
            userId: ''
      }
      this.DELETEdialogFormVisible = true
      // this.$router.push('/teacher/course')
    },
    return_back(){
      this.ADDdialogFormVisible = false
      this.DELETEdialogFormVisible = false
      // this.$router.push('/teacher/course')
    },
    submit_ADD_form(){
      this.ADDform.userId = this.user.userId
      // console.log("THE USER ADDED IS:", ADDform)
      request.post('/course/addcourse', this.ADDform).then(res =>{
        if (res.success){
          this.Register_code_of_new_course = res.detail.register_code
          this.$notify.success("Added Successfully, the Register Code is:"+ this.Register_code_of_new_course)
          this.$router.push({ name: 'teacherEnterCourse', params: { course_id: res.detail.new_course_id } })
        }else{
          this.$notify.error("Failure to add")
        }
      })
      this.ADDdialogFormVisible = false
    },
    submit_DELETE_form(){
      this.DELETEform.userId = this.user.userId
      // console.log("THE USER ADDED IS:", ADDform)
      request.post('/course/deletecourse', this.DELETEform).then(res =>{
        if (res.success){
          this.$notify.success("===DELETED Successfully===")
          // this.$router.replace({ path: '/teacher/course/empty'})

        }else{
          this.$notify.error(res.msg)
        }
      })

      this.DELETEdialogFormVisible = false
    }
  }

}

</script>

<!--<style scoped>-->
<!--body{-->
<!--  margin:0;-->
<!--  padding:0;-->
<!--  border:0-->
<!--}-->

<!--</style>-->