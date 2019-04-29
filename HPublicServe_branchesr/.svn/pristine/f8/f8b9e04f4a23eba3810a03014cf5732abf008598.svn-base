<%@ page import="com.honghe.web.user.util.HttpServiceUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@ page isELIgnored="false"%>
<head>
    <meta charset="UTF-8">
    <title>家长管理</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/components.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/selectlist.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme/default/default.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.cxcalendar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.Jcrop.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/libs/layer/skin/layer.css" />
    <style>
        .campus-tab .title {
            height: 50px;
            line-height:50px;
            border-bottom: 1px solid #ccc;
            margin: 0 10px;
        }

        .campus-tab .title li {
            float: left;
            width: 100px;
            height: 50px;
            position: relative;
            text-align: center;
            cursor: pointer;
        }
        .campus-tab .title .current:after {
            content: '';
            display: block;
            width: 100%;
            height: 3px;
            background: #286090;
            position: absolute;
            left: 0;
            bottom: -1px;
        }


        /* 教师管理tab */
        .user-main {
            width: 100%;
            border: none;
        }
        .main-top {
            margin: 10px;
        }
        .main-list {
            width: 100%;
        }

        .u-title {
            width: 100%;
        }

        .u-studentName,.u-tel,.u-email,.u-operation {
            width: 112px;
        }
        .u-name {
            width: 120px;
        }
        .u-item .u-name .user-con {
            width: 70px;
        }
        .u-name img {
            width: 50px;
            height: 50px;
            margin: 16px 0;
        }
    </style>
</head>

<body>
<div class="user-main">
    <div class="main-top">
        <div class="m-search">
            <input type="text" placeholder="请输入姓名进行搜索" maxlength="15">
            <button class="btn-search"></button>
        </div>

        <div class="user-btns">
            <div class="btn-group">
                <button class="btn btn-blue btn-add-users" type="button">添加家长</button>
                <button class="btn btn-blue" id="btn-batch-delete" type="button">批量删除</button>
            </div>

            <div class="btn-group">
                <button class="btn btn-light-blue btn-leading-in" type="button">导入</button>
                <button class="btn btn-light-blue btn-leading-out" type="button">导出</button>
            </div>
            <div class="btn-group">

            </div>

        </div>
    </div>
    <div class="main-list">
        <div class="u-title">
            <div class="u-check">
                <i class="m-check-all"></i>
            </div>
            <div class="u-name">家长姓名</div>
            <div class="u-studentName">学生姓名</div>
            <div class="u-tel">手机号码</div>
            <div class="u-email">邮箱</div>
            <div class="u-tel">成员关系</div>
            <div class="u-operation">操作</div>
        </div>

    </div>
    <div class="m-users m-page">
        <!-- 这里显示分页 -->
    </div>
</div>
</div>

<!--    添加用户-->
<div class="add-users">
    <table>
        <tr style="display: none">
            <td class="user-add-subject">&nbsp;&nbsp;&nbsp;头像：</td>
            <td>
                <img  src="./images/userimg.png" style="height: 60px;width: 60px;">
                <%--<span class="headimages"  id="userHeadImage" style="text-decoration: underline; color: #da4453;cursor: pointer">点击这里 </span>设置头像--%>
            </td>
        </tr>
        <tr>
            <td class="user-add-subject"><span style="color: red">* &nbsp;</span>家长姓名：</td>
            <td>
               <input id="parentRealName" type="text" >
            </td>
        </tr>
        <tr>
            <td class="teacher-add-subject"><span style="color: red">* &nbsp;</span>姓名拼音：</td>
            <td>
                <input id="parentNamePy" type="text">
            </td>
        </tr>
        <tr>
            <td>&nbsp;&nbsp;&nbsp;学生姓名：</td>
            <td>
                <select id="studentName" name="studentName">
                </select>
            </td>
        </tr>
        <tr>
            <td>&nbsp;&nbsp;&nbsp;成员关系：</td>
            <td>
                <select id="relation" name="relation">
                    <option value="1">父亲</option>
                    <option value="2">母亲</option>
                    <option value="3">其他</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="user-add-subject">&nbsp;&nbsp;&nbsp;手机号码：</td>
            <td>
                <input type="text" id="userMobile" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
            </td>
        </tr>
        <tr>
            <td class="user-add-subject">&nbsp;&nbsp;&nbsp;邮箱：</td>
            <td>
                <input type="text" id="userEmail"/>
            </td>
        </tr>
        <tr>
            <td id="guardian">&nbsp;&nbsp;&nbsp;监护人：</td>
            <td>
                <div class="select-sf" id="guardians">
                    <span ><span value="1" id="isGuardians"></span> 是</span>
                    <span ><span value="0" id="isNotGuardians"></span>否</span>
                </div>
            </td>
        </tr>
        <tr>
            <td id="together">&nbsp;&nbsp;&nbsp;一起生活：</td>
            <td>
                <div class="select-sf" id="isTogether">
                    <span ><span value="1" id="isT"></span> 是</span>
                    <span ><span value="0" id="isNotT"></span>否</span>
                </div>
            </td>
        </tr>

    </table>
</div>


<!--    修改-->
<div class="edit-users">
    <table>
        <tr  style="display: none">
            <td class="user-add-subject">&nbsp;&nbsp;&nbsp;头像：</td>
            <td>
                <img  src="./images/userimg.png" style="height: 60px;width: 60px;">
                <%--<span class="headimages"  id="e_userHeadImage" style="text-decoration: underline; color: #da4453;cursor: pointer">点击这里 </span>设置头像--%>
            </td>
        </tr>
        <tr>
            <td class="user-add-subject"><span style="color: red">* &nbsp;</span>家长姓名：</td>
            <td>
                <input id="e_userRealName" type="text">
            </td>
        </tr>
        <tr>
            <td class="teacher-add-subject"<span style="color: red">* &nbsp;</span>姓名拼音：</td>
            <td>
                <input id="e_parentNamePy" type="text">
            </td>
        </tr>
        <tr>
            <td>&nbsp;&nbsp;&nbsp;学生姓名：</td>
            <td>
                <select id="e_studentName" name="e_studentName">
                </select>
            </td>
        </tr>
        <tr>
            <td>&nbsp;&nbsp;&nbsp;成员关系：</td>
            <td>
                <select id="e_relation" name="e_relation">
                    <option value="1">父亲</option>
                    <option value="2">母亲</option>
                    <option value="3">其他</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="user-add-subject">&nbsp;&nbsp;&nbsp;手机号码：</td>
            <td>
                <input type="text" id="e_userMobile" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
            </td>
        </tr>
        <tr>
            <td class="user-add-subject">&nbsp;&nbsp;&nbsp;邮箱：</td>
            <td>
                <input type="text" id="e_userEmail"/>
            </td>
        </tr>
        <tr>
            <td>&nbsp;&nbsp;&nbsp;监护人：</td>
            <td>
                <div class="select-sf" id="guardianss">
                    <span ><span value="1" id="1"></span> 是</span>
                    <span ><span value="0" id="0"></span>否</span>
                </div>
            </td>
        </tr>
        <tr>
            <td >&nbsp;&nbsp;&nbsp;一起生活：</td>
            <td>
                <div class="select-sf" id="togethers">
                    <span ><span value="1" id="11"></span> 是</span>
                    <span ><span value="0" id="00"></span>否</span>
                </div>
            </td>
        </tr>


    </table>
</div>

<!--    批量删除/删除机构-->
<div class="m-delete">
    确定删除此家长吗？
</div>

<!--详情-->
<div class="m-details">
    <div class="details-name">
        <img src="${pageContext.request.contextPath}/images/userimg.png">

        <div class="name-details-name">
            <span id="details-userRealName" class="name-first" title=""></span>
            <span id="details-userName" title=""></span>
            <span id="details-userRelation"></span>
        </div>
    </div>
    <div class="details-content">
        <div class="details-content-title">系统角色</div>
        <div class="system-role-content">
            <div class="select-system">
            </div>
            <ul id="details-system-list" class="details-system-list">
            </ul>
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="details-content">
        <div class="details-content-title">详细信息</div>
        <div class="details-left">
            <div>学生姓名：<span id="details-studentName"></span></div>
            <div class="mobile">手机号码：<span id="details-userMobile"></span></div>
            <div class="email">邮箱：<span id="details-email"></span></div>
            <div class="guardian">是否是监护人：<span id="details-guardian"></span></div>
            <div class="istogether">是否生活在一起：<span id="details-istogether"></span></div>
        </div>
    </div>
</div>

<div class="user-leading">
    <form id="uploadFile" enctype="multipart/form-data" method="post">
        <input id="upload_fileName" type="text" readonly onkeydown="if(event.keyCode==8) return false;"/>
        <a href="javascript:void(0)">
            <input id="upload" name="myFile" type="file" value=""/>选择文件
        </a>
        <button id="download-template" class="btn btn-orange" type="button">下载模板</button>
    </form>
</div>
<div class="download-failed">
    <div>是否要下载导入失败的数据？</div>
</div>

<div class="user-heading">

    <form id="uploadFiles" enctype="multipart/form-data" method="post" >

    <input id="upload_fileNames" type="text" readonly onkeydown="if(event.keyCode==8) return false;"/>
        <a href="javascript:void(0)">
            <input id="uploads" name="myFile" type="file"   value=""  />选择图片
        </a>

    </form>
    <div id="outer">
        <div class="jcExample">
            <div class="article">
                <table>
                    <tr>
                        <td style="width:100px;height:100px;overflow:hidden; display: none"  id="aaa" name="sss">
                            <img  id="target" alt="Flowers" width="300" height="300" />
                        </td>
                        <td id="aaaa">
                            <div style="width:100px;height:100px;overflow:hidden;display: none" id="aa">
                                <img  id="preview" alt="Preview" class="jcrop-preview" />
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <input id="imageData" value="" type="hidden"/>
</div>
<script src="${pageContext.request.contextPath}/js/libs/mod.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/popup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/users.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cxcalendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/parent.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.Jcrop.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/layer/layer.js"></script>
<script>
    var parents = require('parent/parent');
    parents.init("<%=HttpServiceUtil.getStorageIp()%>","<%=HttpServiceUtil.getStoragePort()%>");
    var SelectList = require('select-list/select');
    var $ = require("jquery");
    var select = new SelectList('#relation', function (option) {
    });
    select.init();
    document.getElementById("relation").style.zIndex = "30";
    var select2 = new SelectList('#e_relation', function (option) {
    });
    select2.init();
    document.getElementById("e_relation").style.zIndex = "30";
    new cutImage().init();
    function cutImage(){
        var oop = this;
        this.option = {
            x:170,
            y:110,
            w:350,
            h:200,
            t:'target',
            p:'preview',
            o:'aa'
        }
        this.init = function(){
            oop.target();
        }
        this.target = function(){
            $('#'+oop.option['t']).Jcrop({
                onChange: oop.updatePreview,
                onSelect: oop.updatePreview,
                aspectRatio: 1,
                setSelect: [ oop.option['x'], oop.option['y'], oop.option['w'],oop.option['h'] ],
                bgFade:     true,
                bgOpacity: .5
            });
        }
        this.updatePreview = function(obj){
            if (parseInt(obj.w) > 0)
            {

                var rx = $('#'+oop.option['o']).width()/ obj.w;
                var ry = $('#'+oop.option['o']).height()/ obj.h;
                var width = Math.round(rx*$('#'+oop.option['t']).width());
                var height = Math.round(ry*$('#'+oop.option['t']).height());
                $('#'+oop.option['p']).css({
                    width: Math.round(rx*$('#'+oop.option['t']).width()) + 'px',
                    height: Math.round(ry*$('#'+oop.option['t']).height()) + 'px',
                    marginLeft: '-' + Math.round(rx * obj.x) + 'px',
                    marginTop: '-' + Math.round(ry * obj.y) + 'px'
                });

                var temp = obj.x + "," + obj.y + "," + obj.w + "," + obj.h + "," + width + "," + height;
                $('#imageData').val(temp);
            }
        }
    }
    // 关闭弹窗
    var layer = require('libs/layer/layer.js');
    window.closeLayer = function () {
        layer.closeAll();

    }
    window.initParentList = function (campusId) {
        parents.initParentListByCampusId(campusId);
    }
</script>
</body>

</html>