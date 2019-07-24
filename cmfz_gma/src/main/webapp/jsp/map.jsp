<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<script src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/echarts/china.js"></script>
<script type="text/javascript">
    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_map'));
        $.get("${pageContext.request.contextPath}/user/userDistribution",function (data) {

            option = {
                title : {
                    text: '全国用户分布图',
                    subtext: '中国地区',
                    left: 'center'
                },
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:['男性用户','女性用户']
                },
                visualMap: {
                    min: 0,
                    max: data.maxCount,
                    left: 'left',
                    top: 'bottom',
                    text:['高','低'],           // 文本，默认为数值文本
                    calculable : true
                },
                toolbox: {
                    show: true,
                    orient : 'vertical',
                    left: 'right',
                    top: 'center',
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                series : [
                    {
                        name: '男性用户',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:data.men
                    },
                    {
                        name: '女性用户',
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:data.women
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        })
    })


</script>
<div class="page-header">
    <h1>全国用户分布图</h1>
</div>
<div id="echarts_map" style="width: 900px;height:650px;"></div>