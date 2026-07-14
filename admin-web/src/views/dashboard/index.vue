<template>
  <div class="dashboard">
    <el-row :gutter="16" class="stat-row">
      <el-col :span="6" v-for="item in statCards" :key="item.title">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-value">{{ item.value }}</div>
          <div class="stat-title">{{ item.title }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="chart-row">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>访问量趋势</template>
          <div ref="lineChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>用户分布</template>
          <div ref="pieChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16">
      <el-col :span="24">
        <el-card shadow="hover">
          <template #header>月度数据</template>
          <div ref="barChartRef" class="chart-container-lg"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue'
import * as echarts from 'echarts'

const lineChartRef = ref<HTMLElement>()
const pieChartRef = ref<HTMLElement>()
const barChartRef = ref<HTMLElement>()

let lineChart: echarts.ECharts | null = null
let pieChart: echarts.ECharts | null = null
let barChart: echarts.ECharts | null = null

const statCards = [
  { title: '总用户数', value: '1,286' },
  { title: '今日访问', value: '3,542' },
  { title: '订单总数', value: '8,921' },
  { title: '总收入', value: '¥128,450' },
]

onMounted(() => {
  initLineChart()
  initPieChart()
  initBarChart()
})

onUnmounted(() => {
  lineChart?.dispose()
  pieChart?.dispose()
  barChart?.dispose()
})

function initLineChart() {
  if (!lineChartRef.value) return
  lineChart = echarts.init(lineChartRef.value)
  lineChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] },
    yAxis: { type: 'value' },
    series: [
      {
        name: '访问量',
        type: 'line',
        smooth: true,
        data: [820, 932, 901, 934, 1290, 1330, 1520],
        areaStyle: { opacity: 0.3 },
      },
    ],
  })
}

function initPieChart() {
  if (!pieChartRef.value) return
  pieChart = echarts.init(pieChartRef.value)
  pieChart.setOption({
    tooltip: { trigger: 'item' },
    legend: { bottom: 0 },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        data: [
          { value: 1048, name: '管理员' },
          { value: 735, name: '编辑员' },
          { value: 580, name: '查看员' },
          { value: 484, name: '其他' },
        ],
      },
    ],
  })
}

function initBarChart() {
  if (!barChartRef.value) return
  barChart = echarts.init(barChartRef.value)
  barChart.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['收入', '支出'] },
    xAxis: { type: 'category', data: ['1月', '2月', '3月', '4月', '5月', '6月'] },
    yAxis: { type: 'value' },
    series: [
      { name: '收入', type: 'bar', data: [12000, 19000, 15000, 22000, 18000, 25000] },
      { name: '支出', type: 'bar', data: [8000, 12000, 10000, 15000, 11000, 18000] },
    ],
  })
}
</script>

<style scoped lang="scss">
.dashboard {
  .stat-row {
    margin-bottom: 16px;
  }

  .stat-card {
    text-align: center;

    .stat-value {
      font-size: 28px;
      font-weight: bold;
      color: #409eff;
    }

    .stat-title {
      margin-top: 8px;
      color: #999;
    }
  }

  .chart-row {
    margin-bottom: 16px;
  }

  .chart-container {
    height: 300px;
  }

  .chart-container-lg {
    height: 350px;
  }
}
</style>
