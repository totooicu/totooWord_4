<template>
  <div ref="pieChart" :style="{ width: '100%', height: '50%' }"></div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import * as echarts from 'echarts';

const props = defineProps({
  X: {
    type: Array,
    required: true,
  },
  Y: {
    type: Array,
    required: true,
  },
});

const pieChart = ref(null);
let chartInstance = null;

const initChart = () => {
  if (pieChart.value && !chartInstance) {
    chartInstance = echarts.init(pieChart.value);
    const option = {
      tooltip: {
        trigger: 'item',
      },
      legend: {
        orient: 'vertical',
        left: 'left',
      },
      series: [
        {
          name: '访问来源',
          type: 'pie',
          radius: '75%',
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: 'center',
          },
          emphasis: {
            label: {
              show: true,
              fontSize: '18',
              position: 'center',
              fontWeight: 'bold',
            },
          },
          labelLine: {
            show: false,
          },
          data: props.X.map((name, index) => ({
            value: props.Y[index],
            name,
          })),
        },
      ],
    };
    chartInstance.setOption(option);
  }
};

onMounted(() => {
  initChart();
});

onBeforeUnmount(() => {
  if (chartInstance) {
    chartInstance.dispose();
    chartInstance = null;
  }
});
</script>

<style scoped>
/* 样式可以根据需要添加 */
</style>