<template>
    <div>
        <h1>{{"User Profile"}}</h1>
        
        <div>
            <el-avatar :size="100"> <span class="text-item">{{nameInitial}}</span> </el-avatar>
        </div>
        <h2>{{ name }}</h2>
        <h2>{{ emailAddress }}</h2>
    </div>
  </template>



<script>
import request from "@/utils/request";

export default {
  data() {
    return {
    //   user: {
        name: " ",
        emailAddress: " ",
        userId: null,
        jwt: "",
        gender: "",
        identity: "",
        nameInitial: ""
    //   },
    };
  },
  created() {
      this.get()
  },
  methods: {
    get() {
        const user_id = this.$route.params.user_id
        console.log(user_id)
        request.get("/user/"+user_id).then(res => {
            console.log(res)
            this.name = res.detail.name
            this.userId = res.detail.userId
            this.emailAddress = res.detail.emailAddress
            if (this.name.split(" ")[1] != null) {
                this.nameInitial = this.name.charAt(0) + this.name.split(" ")[1].charAt(0)
            } else {
                this.nameInitial = this.name.charAt(0)
            }
        }
      )
    },
  },
//   mounted() {
//     if (sessionStorage.getItem('userInfo')) {
//       this.user = JSON.parse(sessionStorage.getItem('userInfo'));
//     }
//   },
};
</script>

<style scoped>
    .text-item {
        font-size: 30px;
    }
</style>