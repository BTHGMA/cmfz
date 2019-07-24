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
            pageInit();
        });
        function pageInit(){
            jQuery("#listsg11").jqGrid(
                {
                    styleUI:"Bootstrap",
                    url : '${pageContext.request.contextPath}/album/queryAll',
                    datatype : "json",
                    height : 190,
                    colNames:["编号","专辑名称","专辑封面","专辑数量","专辑得分","专辑作者","播音员","专辑简介","出版时间"],
                    colModel:[
                        {name:"id"},
                        {name:"title",editable:true},
                        {name:"cover",editable:true,edittype:"file",formatter:function(cellvalue, options, rowObject){
                                return "<img style='width:60px;height:60px' src='${pageContext.request.contextPath}/albumPic/"+cellvalue+"'/>";
                            }},
                        {name:"count",editable:true},
                        {name:"score",editable:true},
                        {name:"author",editable:true},
                        {name:"broadcast",editable:true},
                        {name:"brief",editable:true},
                        {name:"publishTime",editable:true,edittype:"date"}
                    ],
                    multiselect:true,
                    rowNum : 8,
                    rowList : [ 8, 10, 20, 30 ],
                    pager : '#pagersg11',
                    sortname : 'id',
                    viewrecords : true,
                    sortorder : "desc",
                    subGrid : true,
                    editurl:"${pageContext.request.contextPath}/album/edit",
                    subGridRowExpanded : function(subgrid_id, row_id) {
                        // we pass two parameters
                        // subgrid_id is a id of the div tag created whitin a table data
                        // the id of this elemenet is a combination of the "sg_" + id of the row
                        // the row_id is the id of the row
                        // If we wan to pass additinal parameters to the url we can use
                        // a method getRowData(row_id) - which returns associative array in type name-value
                        // here we can easy construct the flowing
                        var subgrid_table_id, pager_id;
                        subgrid_table_id = subgrid_id + "_t";
                        pager_id = "p_" + subgrid_table_id;
                        $("#" + subgrid_id).html(
                            "<table id='" + subgrid_table_id
                            + "' class='scroll'></table><div id='"
                            + pager_id + "' class='scroll'></div>");
                        jQuery("#" + subgrid_table_id).jqGrid(
                            {
                                styleUI:"Bootstrap",
                                url : "${pageContext.request.contextPath}/chapter/queryAll?id=" + row_id,
                                datatype : "json",
                                colNames : [ '编号', '专辑编号', '音频名称', '音频大小','音频路径','上传时间','操作' ],                                colModel : [
                                    {name:"id"},
                                    {name:"albumId",editable:true,editoptions:{defaultValue:row_id},hidden:true
                                    },
                                    {name:"title",editable:true},
                                    {name:"size"},
                                    {name:"downPath",edittype:"file",editable:true},
                                    {name:"uploadTime",editable:true,edittype:"date"},
                                    {name : "downPath",formatter:function(cellvalue, options, rowObject){
                                            return "<a onclick=\"\" class=\"btn btn-primary\"href=\"${pageContext.request.contextPath}/chapter/download?downPath="+cellvalue+"\" role=\"button\">下载</a>"
                                        }},
                                ],
                                multiselect:true,
                                rowNum : 20,
                                pager : pager_id,
                                sortname : 'num',
                                sortorder : "asc",
                                height : '100%',
                                editurl:"${pageContext.request.contextPath}/chapter/edit",
                            }).jqGrid("navGrid","#" + pager_id,{},{
                            //修改的部分
                            closeAfterEdit:true,
                            afterSubmit:function(response){
                                $.ajaxFileUpload({
                                    url:"${pageContext.request.contextPath}/chapter/changeChapter",
                                    //fileElementId:"imgPath",
                                    data:{"id":response.responseText},
                                    type:"post",
                                    success:function(){
                                        $("#" + pager_id).trigger("reloadGrid");
                                    }
                                })
                                return "[true]";
                            },
                            closeAfterDel:true,
                            afterSubmit:function(response){
                                $.ajaxFileUpload({
                                    url:"${pageContext.request.contextPath}/chapter/removeChapter",
                                    //fileElementId:"imgPath",
                                    data:{"id":response.responseText},
                                    type:"post",
                                    success:function(){
                                        $("#" + pager_id).trigger("reloadGrid");
                                    }
                                })
                                return "[true]";
                            }
                        },{
                            //添加的部分
                            closeAfterAdd:true,
                            afterSubmit:function(response){
                                $.ajaxFileUpload({
                                    url:"${pageContext.request.contextPath}/chapter/upload",
                                    fileElementId:"downPath",
                                    data:{"id":response.responseText},
                                    type:"post",
                                    success:function(){
                                        $("#" + pager_ids).trigger("reloadGrid");
                                    }
                                })
                                return "[true]";
                            }
                        });

                    },
                    subGridRowColapsed : function(subgrid_id, row_id) {
                        // this function is called before removing the data
                        //var subgrid_table_id;
                        //subgrid_table_id = subgrid_id+"_t";
                        //jQuery("#"+subgrid_table_id).remove();
                    }
                }).jqGrid("navGrid","#pagersg11",{},{
                //修改的部分
                closeAfterEdit:true,
                afterSubmit:function(response){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/changeAlbum",
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
                        url:"${pageContext.request.contextPath}/album/removeAlbum",
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
                        url:"${pageContext.request.contextPath}/album/upload",
                        fileElementId:"cover",
                        data:{"id":response.responseText},
                        type:"post",
                        success:function(){
                            $("#listsg11").trigger("reloadGrid");
                        }
                    })
                    return "[true]";
                }
            });

        }
    </script>
</head>
<body>
<div class="jumbotron">
    <h3>专辑管理</h3>
</div>
<table id="listsg11"></table>
<div id="pagersg11"></div>
</body>
</html>