<template>
  <div ref="chartRef" :style="{ width: '100%', height: '400px' }"></div>
</template>

<script>
import { defineComponent, onMounted, ref, watch } from 'vue';
import ECharts from 'vue-echarts';
import * as echarts from 'echarts';

export default defineComponent({
  name: 'LineChart',
  components: {
    ECharts // 注意：通常我们不需要在组件中导入ECharts，除非我们要用它的组件
  },
  props: {
    X: {
      type: Array,
      required: true // 确保传入了X数组
    },
    Y: {
      type: Array,
      required: true // 确保传入了Y数组
    },
  },
  setup(props, { attrs }) {
    const chartRef = ref(null); // 更改ref名称为chartRef以避免混淆
    let chartInstance = null; // 用来存储ECharts实例

    onMounted(() => {
      if (chartRef.value) {
        chartInstance = echarts.init(chartRef.value); // 使用DOM元素初始化ECharts实例
        updateChart();
      }
    });

    const updateChart = () => {
      if (!chartInstance || !props.X || !props.Y) return;
      const option = {
        // ... 省略其他配置项
        xAxis: {
          type: 'category',
          name: '书本名',
          data: props.X
        },
        yAxis: {
          type: 'value',
          name: '单词数量'
        },
        series: [
          {
            data: props.Y,
            type: 'line'
          },
        ],
        tooltip: {
          trigger: 'item',
        },
        // ... 省略其他配置项
      };

      chartInstance.setOption(option);
      chartInstance.resize(); // 如果需要，可以在这里调用resize
    };

    // 监听props的变化
    watch([() => props.X, () => props.Y], () => {
      updateChart();
    }, {
      deep: true // 因为我们监听的是数组，所以需要深度监听
    });

    return {
      chartRef // 返回模板中需要的ref
    };
  }
});
</script>