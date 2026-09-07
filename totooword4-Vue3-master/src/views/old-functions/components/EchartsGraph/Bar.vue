<template>
  <div ref="chart" :style="{ width: '100%', height: '50%' }"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  props: {
    X: Array, // 输入的单词数组
    Y: Array, // 对应的频率数组
  },
  data() {
    return {
      chartInstance: null,
    };
  },
  mounted() {
    this.initChart();
  },
  beforeDestroy() {
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
  },
  methods: {
    initChart() {
      this.chartInstance = echarts.init(this.$refs.chart);
      this.updateChart();
    },
    updateChart() {
      const option = {
        title: {
          text: '单词频率条形图',
        },
        // 交换xAxis和yAxis的配置
        yAxis: {
          type: 'category', // 将Y轴设为分类轴
          data: this.X, // X的值现在作为Y轴的数据
          name: '单词', // 可选：给Y轴命名
          nameLocation: 'end', // 名称位置，可调整
          nameTextStyle: {
            align: 'center', // 名称文本对齐方式
          },
          inverse: true, // 翻转Y轴，使文字向上
          axisLabel: {
            interval: 0, // 显示所有标签，避免遮挡可通过其他策略优化，如旋转或省略
            rotate: 0, // 可选：旋转标签文本，防止重叠
            margin: 15, // 可调整标签与轴线的距离
          },
        },
        xAxis: {
          type: 'value', // Y轴变为数值轴
          name: '频率', // 可选：给X轴命名
        },
        series: [
          {
            name: '频率',
            type: 'bar',
            data: this.Y.map((value, index) => [value, this.X[index]]), // 调整数据结构以匹配新的坐标系
            label: {
              show: true, // 可选：在柱子上显示具体数值
              position: 'top', // 标签位置
            },
          },
        ],
        tooltip: {
          // trigger: 'axis',
          trigger: 'item',
          // axisPointer: {
          //   type: 'shadow',
          // },
          // formatter: function (params) {
          //   return params[0].seriesName + '<br />' + params[0].data[1] + ': ' + params[0].data[0]; // 由于数据结构改变，调整显示格式
          // },
        },
      };

      this.chartInstance.setOption(option);
    },


  },
  watch: {
    X: {
      handler(newX, oldX) {
        if (newX !== oldX) {
          this.updateChart();
        }
      },
      deep: true,
    },
    Y: {
      handler(newY, oldY) {
        if (newY !== oldY) {
          this.updateChart();
        }
      },
      deep: true,
    },
  },
};
</script>
