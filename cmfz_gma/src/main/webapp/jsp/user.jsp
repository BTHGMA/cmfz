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
                url:"${pageContext.request.contextPath}/user/queryAll",
                datatype:"json",
                colNames:["编号","电话","密码","法名","省","市","性别","个人签名","头像","状态","创建时间"],
                colModel:[
                    {name:"id"},
                    {name:"phone"},
                    {name:"password"},
                    {name:"dharmaName"},
                    {name:"province"},
                    {name:"city"},
                    {name:"gender"},
                    {name:"personalSign"},
                    {name:"profile"},
                    {name:"status",editable:true},
                    {name:"registTime"},

                    ],
                pager:"carouselPager",
                rowNum:3,
                rowList:[3,5,7],
                viewrecords:true,
                autowidth:true,
                editurl:"${pageContext.request.contextPath}/user/edit",
                height:"100%",
                multiselect:true,
                rownumbers:true,
            }).jqGrid("navGrid","#carouselPager",{add:false},{
                //修改的部分
                closeAfterEdit:true,
                afterSubmit:function(response){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/user/changeUser",
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

            }).navButtonAdd("#carouselPager",{caption:"导入导出",buttonicon:"glyphicon glyphicon-plus",position:"first",onClickButton:function () {


                    $('#myModal').modal({
                        backdrop:true,
                        keyboard:true,


                    })
                    <%--KindEditor.create('#editor_id',{--%>
                    <%--    width : '700px',--%>
                    <%--    uploadJson:'${pageContext.request.contextPath}/article/upload',--%>
                    <%--    fileManagerJson:'${pageContext.request.contextPath}/article/showAll',--%>
                    <%--    allowFileManager:true,--%>
                    <%--    filePostName:'file',--%>

                    <%--    afterBlur:function(){--%>
                    <%--        this.sync();--%>
                    <%--    }--%>
                    <%--});--%>
                }})


        });

        $("#addByeasyPoi").click(function () {
            $.ajaxFileUpload({
                type: "POST",   //提交的方法
                url:"${pageContext.request.contextPath}/user/addByeasyPoi", //提交的地址
                fileElementId:"inputFile",
                datatype:"json",
                success:function(){
                    $('#myModal').modal('hide')
                    $("#carouselTable").trigger("reloadGrid");
                }
            })
        });


    </script>
</head>
<body>
<div class="jumbotron">
    <h3>用户管理管理</h3>
</div>
<table id="carouselTable"></table>
<div id="carouselPager"></div>
<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:800px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">导入导出</h4>
            </div>
            <form action="javascript:void(0);" id="articleForm">
                <div class="modal-body"style="padding-left: 50px">
                    <div>

                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs" role="tablist">
                            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">导出</a></li>
                            <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">导入</a></li>

                        </ul>
                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div role="tabpanel" class="tab-pane active" id="home">
                                    <button name="outFile" id="outFile" onclick="window.open('${pageContext.request.contextPath}/user/queryByeasyPoi');">导出</button>
                            </div>
                            <div role="tabpanel" class="tab-pane" id="profile">
                                <form id="myForm">
                                    <input type="file" name="file" id="inputFile">
                                    <input type="button" id="addByeasyPoi" value="导入">
                                </form>
                            </div>

                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>