<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!doctype html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../jqgrid/extend/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="../boot/js/jquery-3.3.1.min.js"></script>
    <script src="../boot/js/bootstrap.3.3.7.min.js"></script>
    <script src="../jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="../jqgrid/extend/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>

    <title>持明法洲后台管理系统</title>
    <script>
        $(function(){
            $("#carouselTable").jqGrid({
                styleUI:"Bootstrap",
                url:"${pageContext.request.contextPath}/carousel/queryAll",
                datatype:"json",
                colNames:["编号","名称","图片","状态","上传时间"],
                colModel:[
                    {name:"id"},
                    {name:"title",editable:true},
                    {name:"imgPath",editable:true,edittype:"file",formatter:function(cellvalue, options, rowObject){
                            return "<img style='width:60px;height:60px' src='${pageContext.request.contextPath}/carouselPic/"+cellvalue+"'/>";
                        }},
                    {name:"status",editable:true},
                    {name:"createTime",edittype:"date"}],
                pager:"carouselPager",
                rowNum:3,
                rowList:[3,5,7],
                viewrecords:true,
                autowidth:true,
                editurl:"${pageContext.request.contextPath}/carousel/edit",
                height:"100%",
                multiselect:true,
                rownumbers:true,
            }).jqGrid("navGrid","#carouselPager",{},{
                //修改的部分
                closeAfterEdit:true,
                afterSubmit:function(response){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/carousel/changeCarousel",
                        //fileElementId:"imgPath",
                        data:{"id":response.responseText},
                        type:"post",
                        success:function(){
                            $("#carouselTable").trigger("reloadGrid");
                        }
                    })
                    return "[true]";
                },
                closeAfterDel:true,
                afterSubmit:function(response){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/carousel/removeCarousel",
                        //fileElementId:"imgPath",
                        data:{"id":response.responseText},
                        type:"post",
                        success:function(){
                            $("#carouselTable").trigger("reloadGrid");
                        }
                    })
                    return "[true]";
                }
            },{
                //添加的部分
                closeAfterAdd:true,
                afterSubmit:function(response){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/carousel/upload",
                        fileElementId:"imgPath",
                        data:{"id":response.responseText},
                        type:"post",
                        success:function(){
                            $("#carouselTable").trigger("reloadGrid");
                        }
                    })
                    return "[true]";
                }
            });

        })
    </script>
</head>
<body>
<div class="jumbotron">
    <h3>轮播图管理</h3>
</div>
<table id="carouselTable"></table>
<div id="carouselPager"></div>
</body>
</html>