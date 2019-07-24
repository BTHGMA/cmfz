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
    <script charset="utf-8" src="../kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="../kindeditor/lang/zh-CN.js"></script>

    <title>持明法洲后台管理系统</title>
    <script>
        $(function(){
            $("#carouselTable").jqGrid({
                styleUI:"Bootstrap",
                url:"${pageContext.request.contextPath}/article/queryAll",
                datatype:"json",
                colNames:["编号","上师编号","名称","内容","上传时间"],
                colModel:[
                    {name:"id"},
                    {name:"guruId",editable:true},
                    {name:"title",editable:true},
                    {name:"content",formatter:function(cellvalue, options, rowObject){
                return "<button onclick=\"queryByContent('"+rowObject.id+"')\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"button\" aria-pressed=\"false\" autocomplete=\"off\">\n" +
                    "预览" +
                    " </button>"
            }},
                    {name:"publishTime",edittype:"date",editable:true}],
                pager:"carouselPager",
                rowNum:3,
                rowList:[3,5,7],
                viewrecords:true,
                autowidth:true,
                editurl:"${pageContext.request.contextPath}/article/edit",
                height:"100%",
                multiselect:true,
                rownumbers:true,
            }).jqGrid("navGrid","#carouselPager",{add:false},{
                //删除部分
                closeAfterDel:true,
                afterSubmit:function(response){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/article/removeArticle",
                        //fileElementId:"imgPath",
                        data:{"id":response.responseText},
                        type:"post",
                        success:function(){
                            $("#carouselTable").trigger("reloadGrid");
                        }
                    })
                    return "[true]";
                },
            },{
                //添加的部分

            }).navButtonAdd("#carouselPager",{caption:"添加",buttonicon:"glyphicon glyphicon-plus",position:"first",onClickButton:function () {


                    $('#myModal').modal({
                        backdrop:true,
                        keyboard:true,


                    })
                    KindEditor.create('#editor_id',{
                        width : '700px',
                        uploadJson:'${pageContext.request.contextPath}/article/upload',
                        fileManagerJson:'${pageContext.request.contextPath}/article/showAll',
                        allowFileManager:true,
                        filePostName:'file',

                        afterBlur:function(){
                            this.sync();
                        }
                    });
                }});



        })
        function addArticle(){
            $.ajax({
                url:"${pageContext.request.contextPath}/article/addArticle",
                type:"post",
                datatype:"json",
                data:$("#articleForm").serialize(),
                success:function(){
                    $('#myModal').modal('hide')
                    $("#carouselTable").trigger("reloadGrid");
                }
            })

        }
        function queryByContent(id) {
            $('#myModal1').modal({
                backdrop:true,
                keyboard:true,
            })
            $.ajax({
                url:"${pageContext.request.contextPath}/article/querybycontent?id=" + id,
                type:"post",
                datatype:"json",
                data:$("#articleForm1").serialize(),
               // data:{"id":response.responseText},
                success:function(obj){
                    $("#modalContent").html(obj[0].content)

                }
            })

        }
    </script>
</head>
<body>
<div class="jumbotron">
    <h3>文章管理</h3>
</div>
<table id="carouselTable"></table>
<div id="carouselPager"></div>


<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:800px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">预览</h4>
            </div>
            <form action="javascript:void(0);" id="articleForm">
                <div class="modal-body"style="padding-left: 50px">
                   上师编号: <input type="text" name="guruId">
                    名称: <input type="text" name="title">
                    上传时间: <input type="date" name="publishTime">
                    <textarea id="editor_id" name="content" style="width:850px;height:300px;">
                </textarea>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addArticle()">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:800px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel1">预览</h4>
            </div>
            <form action="javascript:void(0);" id="articleForm1">
                <div class="modal-body" id="modalContent">

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