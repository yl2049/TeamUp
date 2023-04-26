<template>
  <div>
    <h1>This is a manage Task Info page</h1>

    <el-card>
      <div id = "line" style="width:100%; height:400px">

      </div>
    </el-card>

  </div>

</template>

<script>

import request from "@/utils/request";
import Cookies from 'js-cookie';
import * as echarts from 'echarts';

const option = {
  tooltip: {
    trigger: 'item'
  },
  legend: {
    top: '5%',
    left: 'center'
  },
  series: [
    {
      name: 'Current teams',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: '40',
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
        // { value: 1048, name: 'Search Engine' },
        // { value: 735, name: 'Direct' },
        // { value: 580, name: 'Email' },
        // { value: 484, name: 'Union Ads' },
        // { value: 300, name: 'Video Ads' }
      ]
    }
  ]
};


export default {
  name: "teacherManageTaskInfo",
  data(){
    return{
      lineBox:null, //for echart

    }},
  mounted() { //等页面元素全部初始化之后加载
    this.load()
  },
  methods:{
    load(){
      if (!this.lineBox){
        this.lineBox = echarts.init(document.getElementById('line')) // init the container (of echarts)
      }
      // then get data from backend
      request.get('/course/pieCharts/' + this.$route.params.course_id + '/' + this.$route.params.task_id).then(res =>{
        option.series[0].data = res.taskPieChart_result.detail
        this.lineBox.setOption(option)
      })

      // option.series[0].data = [
      //   { "value": 1048, "name": 'Search Engine' },
      //   { "value": 735, "name": 'Direct' },
      //   { "value": 580, "name": 'Email' },
      //   { "value": 484, "name": 'Union Ads' },
      //   { "value": 300, "name": 'Video Ads' }
      // ]

      // option.series[0].data = [
      //   { "test": 1048, "test2": 'Search Engine' },
      //   { "test": 735, "test2": 'Direct' },
      //   { "test": 580, "test2": 'Email' },
      //   { "test": 484, "test2": 'Union Ads' },
      //   { "test": 300, "test2": 'Video Ads' }
      // ]

      this.lineBox.setOption(option)
}

  }
}
</script>

<style scoped>

</style>