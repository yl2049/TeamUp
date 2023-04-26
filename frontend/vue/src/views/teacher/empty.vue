<template>

</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';

export default {
  name: "empty",
  data(){},
  created(){
    // this.$router.replace({ name: 'teacherMyCoursesView' })
    this.$router.replace({ path: '/teacher/course'})
    // this.$router.push({ name: 'teacherMyCoursesView'})
  }
}
</script>

<style scoped>

</style>