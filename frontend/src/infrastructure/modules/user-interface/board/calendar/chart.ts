import {
  ApexAxisChartSeries,
  ApexChart,
  ApexDataLabels,
  ApexFill,
  ApexGrid,
  ApexLegend, ApexNoData,
  ApexPlotOptions, ApexResponsive,
  ApexTitleSubtitle, ApexTooltip,
  ApexXAxis,
  ApexYAxis
} from 'ng-apexcharts';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart
  fill?: ApexFill;
  dataLabels?: ApexDataLabels;
  grid: ApexGrid;
  yaxis: ApexYAxis;
  xaxis: ApexXAxis;
  plotOptions: ApexPlotOptions;
  title?: ApexTitleSubtitle;
  legend: ApexLegend
  responsive?: ApexResponsive[],
  tooltip?: ApexTooltip,
  noData?: ApexNoData
};

export type DataOptions = {
  w: {
    globals: {
      labels: { [x: string]: any }
      seriesNames: { [x: string]: any }
    }
  }
  dataPointIndex: string | number
  seriesIndex: string | number
};

