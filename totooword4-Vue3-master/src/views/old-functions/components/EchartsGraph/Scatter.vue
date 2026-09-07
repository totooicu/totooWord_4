<template>
  <div ref="chartRef" className="chart-container"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'Scatter',
  props: {
    words: {
      type: Array,
      default: () => [],
    },
    bname: {
      type: Array,
      default: () => [],
    },
  },
  mounted() {
    // this.initChart();
    this.$nextTick(() => {
      this.initChart();
    });
  },
  beforeUnmount() {
    if (this.chart) {
      this.chart.dispose();
    }
  },
  methods: {
    initChart() {
      const chartRef = this.$refs.chartRef;
      this.chart = echarts.init(chartRef);

      // 处理数据
      const xAxisData = this.bname.flat(); // 假设 bname 是一个二维数组，每个数组只有一个书名

      const yAxisData = this.processYAxisData(this.words);
      const points = this.processPoints(this.words, yAxisData);

      // 动态调整图表容器的高度
      const chartHeight = Math.max(400, points.length * 10); // 每个点占用10像素高度
      chartRef.style.height = `${chartHeight}px`;

      // 配置项
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: function (params) {
            return `(${params.data[2]})`; // 显示单词
          },
        },
        grid: {
          left: '10%',
          right: '10%',
          bottom: '15%', // 调整底部空间以容纳虚线
          containLabel: true, // 包含标签
        },
        xAxis: {
          type: 'category',
          data: xAxisData,
          axisLabel: {
            interval: 1, // 显示所有标签
            rotate: 0, // 旋转角度以适应较长的标签
          },
        },
        yAxis: {
          splitNumber: 4000,
          type: 'category', // 设置为类别轴
          data: yAxisData, // 使用单词列表作为 y 轴数据
          splitLine: {
            show: true, // 显示虚线
            lineStyle: {
              type: 'dashed', // 虚线样式
              color: '#ccc', // 虚线颜色
              alignWithLabel: true, // 使虚线与标签对齐
            },
          },
          axisLabel: {
            interval: 1, // 显示所有标签
            rotate: 0, // 旋转角度以适应较长的标签
          },
        },
        series: [{
          type: 'scatter',
          data: points,
          symbolSize: 5, // 散点大小
          label: {
            show: false,
            formatter: function (params) {
              return params.data[2]; // 显示单词
            },
            position: 'top', // 标签位置可以根据需要调整
          },
          encode: {
            x: 0,
            y: 1,
            label: 2,
          },
          itemStyle: {
            // 可以在这里设置散点的样式，比如颜色等
          },
        }],
      };

      // 使用配置项和数据显示图表
      this.chart.setOption(option);

      // 监听窗口大小变化以自适应
      window.addEventListener('resize', () => {
        this.chart.resize();
      });
    },
    processYAxisData(words) {
      const memo2 = new Set();
      const yAxisData = [];
      for (let i = 0; i < words.length; i++) {
        for (let j = 0; j < words[i].length; j++) {
          if (!memo2.has(words[i][j])) {
            memo2.add(words[i][j]);
            yAxisData.push(words[i][j]);
          }
        }
      }
      yAxisData.sort((a, b) => a.localeCompare(b));
      return yAxisData;
    },
    processPoints(words, yAxisData) {
      const memo = new Map();
      yAxisData.forEach((word, index) => memo.set(word, index));
      const points = [];
      for (let i = 0; i < words.length; i++) {
        for (let j = 0; j < words[i].length; j++) {
          points.push([i, memo.get(words[i][j]), words[i][j]]);
        }
      }
      return points;
    },
  },
};
</script>

<style scoped>
.chart-container {
  width: 100%;
  position: relative; /* 如果需要绝对定位子元素 */
}
</style>