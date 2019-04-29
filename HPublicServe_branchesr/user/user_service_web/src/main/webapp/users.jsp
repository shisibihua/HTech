<%@ page import="com.honghe.web.user.util.HttpServiceUtil" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.honghe.service.client.Result" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@ page isELIgnored="false"%>
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/components.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cxmenu.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/selectlist.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme/default/default.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.cxcalendar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.Jcrop.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cxmenu.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/allotDeviceCss/cxmenu.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/allotDeviceCss/a-button.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/winDeviceAdd.css"/>
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

        .u-title > div {
            padding: 0 5px;
        }
        .u-name {
            width: 132px;
        }
        #user-admin {
            padding: 1px 0;
            background: #4cae4c;
            border-radius: 2px;
            color: #fff;
            position: absolute;
            right: 8px;
            height: 20px;
            line-height: 20px;
            top: 19px;
        }
        .u-tel {
            width: 95px;
        }
        .u-operation {
            width: 85px;
        }
        .u-number {
            width: 110px;
        }
        /*.u-sex {*/
            /*width: 40px;*/
        /*}*/
        .u-sex {
            width: 80px;
        }
        .u-email {
            width: 100px;
        }
        .u-identity {
            width: 70px;
        }
        .u-status {
            width: 70px;
        }

        .u-item > div {
            padding: 0 5px;
        }

        .u-name img {
            width: 50px;
            height: 50px;
            margin: 14px 0;
            display: none;
        }
        .u-name .user-con {
            margin: 9px 0 5px 5px;
            width: 75px;
            padding-right: 6px;
            position: relative;
            text-align: center;
        }
        .btn-allot-admin {
            margin-right: 10px;
        }
        .allot-admin {
            display: none;
        }
        .allot-admin .item {
            border-bottom: 1px solid #ccc;
            height: 34px;
            line-height: 34px;
            text-align: center;
        }
        .allot-admin .item:after {
            content: '';
            display: block;
            clear: both;
        }

        .allot-admin .allot-admin-title {
            background: #eaedf1;
        }
        .allot-admin .item > div {
            float: left;
        }
        .allot-admin .item .check {
            width: 50px;
            height: 100%;
        }
        .allot-admin .item .number {
            width: 140px;
            height: 100%;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
        }
        .allot-admin .item .name {
            width: 130px;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
        }
        .allot-admin .item .tel {
            width: 140px;
        }
        .allot-admin .search-teacher {
            margin: 10px;
        }

        .main-right {
            border-right: none;
        }
        .main-top .m-search {
            width: 300px;
        }
        .main-top input {
            width: 220px;
        }
    </style>
</head>

<body>
<div class="m-header">
    <div class="m-header-con">
        <div class="logo">
            <span></span>
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="用户管理" title="用户管理">
        </div>
        <div class='name'>
            <h1>用户管理</h1>
            <span>User Management</span>
        </div>
        <%
            String param = request.getQueryString();
            String param2="";
            String userId = request.getParameter("userId");
            Map params = new HashMap();
            params.put("userId", userId);
            System.out.println("userId===" + userId);

            Result result = HttpServiceUtil.userService("userSearch", params);
            String  userRealName = "";
            if (result.getCode() == 0 && result.getValue() != null) {
                Map<String, String> map = (Map<String, String>) result.getValue();
                userRealName = map.get("userRealName");

            }

        %>
        <div class="nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/roles.jsp?id=0&userRealName=<%=userRealName%>&userId=${param.userId}&logOut=${param.logOut.replaceAll("/","%2F").replaceAll(":","%3A")}">角色权限</a></li>
                <li><a href="${pageContext.request.contextPath}/campus.jsp?id=1&userRealName=<%=userRealName%>&userId=${param.userId}&logOut=${param.logOut.replaceAll("/","%2F").replaceAll(":","%3A")}">组织机构</a></li>
                <li><a href="${pageContext.request.contextPath}/users.jsp?id=2&userRealName=<%=userRealName%>&userId=${param.userId}&logOut=${param.logOut.replaceAll("/","%2F").replaceAll(":","%3A")}">用户管理</a></li>
                <%--<li><a href="${pageContext.request.contextPath}/student.jsp?id=3&logOut=${param.logOut.replaceAll("/","%2F").replaceAll(":","%3A")}">学生管理</a></li>--%>
                <%--<li><a href="${pageContext.request.contextPath}/teacher.jsp?id=4&logOut=${param.logOut.replaceAll("/","%2F").replaceAll(":","%3A")}">教师管理</a></li>--%>
                <%--<li><a href="${pageContext.request.contextPath}/parent.jsp?id=5&logOut=${param.logOut.replaceAll("/","%2F").replaceAll(":","%3A")}">家长管理</a></li>--%>
            </ul>
        </div>

        <div class="user">
            <ul>
                <li><a href="javascript:void(0)"><%=userRealName%></a></li>
                <span class="line-head"></span>
                <li><a class="logout" href="${param.logOut}">返回</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="user-main">
    <div class="main-left">
        <div class="left-top">
            <h1>用户组织</h1>
        </div>
        <div class="mCustomScrollbar" id="content-8" data-mcs-theme="minimal-dark" style="height: 630px;float:left;width: 320px;">
            <div class="org-menu ">
                <%--<ul id="left_nav_ul">--%>
                <%--<li>--%>
                <%--</li>--%>
                <%--</ul>--%>
            </div>
        </div>
    </div>
    <div class="main-right">
        <div class="campus-tab">
            <ul class="title">
                <li key="teacher" class="teacher current">教师管理</li>
                <li key="student" class="student" style="display: none;">学生管理</li>
                <%--<li key="parent" class="parent">家长管理</li>--%>
            </ul>
            <div class="content">
                <div class="teacher-content">
                </div>
            </div>
        </div>
        <div class="main-top">
            <div class="m-search">
                <input type="text" placeholder="请输入用户名/姓名/用户类型进行搜索" maxlength="15">
                <button class="btn-search"></button>
            </div>

            <div class="user-btns">

                <div class="btn-group">
                    <%--<button class="btn btn-green btn-allot-admin" type="button" style="border-radius: 3px;">分配管理员</button>--%>
                    <button class="btn btn-green btn-allot-roles" type="button" style="border-radius: 3px;">分配角色</button>
                        <button class="btn btn-green btn-allot-device" type="button" style="border-radius: 3px;display: none">分配设备</button>
                    <input id="seletedrole" type="hidden"  class="" value=""/>
                    <%--<button class="btn btn-green btn-net-users" type="button">分配设备</button>--%>
                </div>

                <div class="btn-group">
                    <button class="btn btn-orange btn-enable" type="button">启用</button>
                    <button class="btn btn-orange btn-disable" type="button">禁用</button>
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
                <%--<div class="u-name">姓名</div>--%>
                <%--<div class="u-number">编号</div>--%>
                <%--<div class="u-sex">性别</div>--%>
                <%--<div class="u-tel">手机号码</div>--%>
                <%--<div class="u-email">邮箱</div>--%>
                <%--<div class="u-identity" style="max-height: 140px">身份</div>--%>
                <%--<div class="u-status">状态</div>--%>
                <%--<div class="u-operation">操作</div>--%>
                <div class="u-name">用户名</div>
                <div class="u-number">教职工工号</div>
                <div class="u-sex">性别</div>
                <div class="u-tel">手机号码</div>
                <div class="u-email">邮箱</div>
                <div class="u-identity" style="max-height: 100px">用户类型</div>
                <div class="u-status">状态</div>
                <div class="u-operation">操作</div>
            </div>

        </div>
        <div class="m-users m-page">
            <!-- 这里显示分页 -->
        </div>
    </div>
</div>
</div>
<%
    ResourceBundle resourceBundle = ResourceBundle.getBundle("copyright");
%>
<div class="m-footer">
    <%=new String(resourceBundle.getString("copyright").getBytes("ISO-8859-1"),"utf-8")%>
    <%--Copyright © 2016-2018  鸿合科技 All Rights Reserved.--%>
</div>

<!--    添加用户-->
<div class="add-users">
    <table>
        <%--<tr>
            <td class="user-add-subject">头像：</td>
            <td>
                <img  src="./images/userimg.png" style="height: 60px;width: 60px;border-radius: 50%;">
                <span class="headimages"  id="userHeadImage" style="text-decoration: underline; color: #da4453;cursor: pointer">点击这里 </span>设置头像
            </td>
        </tr>--%>
        <tr>
            <td class="user-add-subject">姓名：</td>
            <td>
                <input id="userRealName" type="text">
            </td>
        </tr>
        <tr>
            <td class="user-add-subject">密码：</td>
            <td>
                <input id="userPwd" type="password" onKeyUp="value=value.replace(/[^\w\.\/]/ig,'')" maxlength="15">
            </td>
        </tr>
        <tr>
            <td>性别：</td>
            <td>
                <input id="male" type="radio" value="1" checked name="sex"/><label for="male">男</label>
                <input id="female" type="radio" value="2" name="sex"/><label for="female">女</label>
            </td>
        </tr>
        <tr style="display: none;">
            <td class="user-add-subject">生日：</td>
            <td>
                <input type="text" id="userBirthday" readonly="readonly">
            </td>
        </tr>
        <tr>
            <td>身份：</td>
            <td>
                <div class="select-identity">

                </div>
            </td>
        </tr>
        <tr>
            <td class="user-add-subject">编号：</td>
            <td>
                <input type="text" id="userNum" onKeyUp="value=value.replace(/[^\w\.\/]/ig,'')">
            </td>
        </tr>

        <tr>
            <td class="user-add-subject">手机号码：</td>
            <td>
                <input type="text" id="userMobile" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
            </td>
        </tr>
        <tr>
            <td class="user-add-subject">邮箱：</td>
            <td>
                <input type="text" id="userEmail"/>
            </td>
        </tr>


        <tr>
            <td class="user-add-subject">地址：</td>
            <td>
                <input id="userAddress" type="text">
            </td>
        </tr>

    </table>
</div>

<!--    分配权限-->
<div class="allot-users mCustomScrollbar" style="height: 300px;">
    <input type="hidden" id="allot-role-list">

    <%--<div class="select-system">--%>
    <%--选择系统：--%>
    <%--<select id="allot-system" name="allot-system">--%>
    <%--</select>--%>
    <%--</div>--%>
    <ul id="allot-syetem-list" class="allot-syetem-list">
    </ul>
    <div class="clearfix"></div>
</div>

<!--    分配设备-->
<div class="net-users">
    <div class="select-system">
        选择位置：
        <select id="users-system" name="users-system">
            <option value="1">教育视频资源平台</option>
            <option value="2">校园定位系统</option>
            <option value="3">设备集控</option>
        </select>
    </div>
    <ul class="users-system-list">
        <li><i class="m-check"></i>录播设备</li>
        <li><i class="m-check"></i>天线</li>
        <li><i class="m-check"></i>摄像头</li>
        <li><i class="m-check"></i>录播设备</li>
        <li><i class="m-check"></i>天线</li>
        <li><i class="m-check"></i>摄像头</li>
        <li><i class="m-check"></i>录播设备</li>
        <li><i class="m-check"></i>天线</li>
        <li><i class="m-check"></i>摄像头</li>
    </ul>
    <div class="clearfix"></div>
</div>

<!--    删除-->
<div class="delete-users">
    确定删除此用户吗？
</div>

<!--    修改-->
<div class="edit-users">
    <table>
        <%--<tr>
            <td class="edit-add-subject">头像：</td>
            <td>
                <img  src="./images/userimg.png" style="height: 60px;width: 60px;border-radius: 50%;">
                <span class="headimage"  style="text-decoration: underline; color: #da4453;cursor: pointer">点击这里 </span>设置头像
            </td>
        </tr>--%>
        <tr>
            <td class="edit-add-subject">姓名：</td>
            <td>
                <input id="e_userRealName" type="text">
            </td>
        </tr>

        <tr>
            <td>性别：</td>
            <td id="e_sex">
            </td>
        </tr>
        <tr style="display: none;">
            <td class="edit-add-subject">生日：</td>
            <td>
                <input type="text" id="e_userBirthday" placeholder="例：2017-01-01">
            </td>
        </tr>
        <tr>
            <td>身份：</td>
            <td>
                <div class="e_select-identity">

                </div>
            </td>
        </tr>
        <tr>
            <td class="edit-add-subject">编号：</td>
            <td>
                <input type="text" id="e_userNum" onKeyUp="value=value.replace(/[^\w\.\/]/ig,'')">
            </td>
        </tr>
        <tr>
            <td class="edit-add-subject">手机号码：</td>
            <td>
                <input type="text" id="e_userMobile" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();">
            </td>
        </tr>
        <tr>
            <td class="edit-add-subject">邮箱：</td>
            <td>
                <input type="text" id="e_userEmail"/>
            </td>
        </tr>

        <tr style="display: none">
            <td class="edit-add-subject">地址：</td>
            <td>
                <input id="e_userAddress" type="text">
            </td>
        </tr>
    </table>
</div>

<!--详情-->
<div class="m-details">
    <div class="details-name">
        <img src="${pageContext.request.contextPath}/images/userimg.png">

        <div class="name-details-name">
            <span id="details-userRealName" class="name-first" title=""></span>
            <span id="details-userName" title=""></span>
            <span id="details-userSex"></span>
            <span id="details-userNum" class="number" title=""></span>
            <span id="details-userAgency" title=""></span>
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
    <%--<div class="details-content">--%>
    <%--<div class="details-content-title">相关设备</div>--%>
    <%--<div class="equipment">暂无相关设备</div>--%>
    <%--</div>--%>
    <div class="details-content">
        <div class="details-content-title">详细信息</div>
        <div class="details-left">
            <div>邮箱：<span id="details-userEmail"></span></div>
            <div class="birthday" style="display: none;">生日：<span id="details-userBrithday"></span></div>
            <div class="tel">手机号码：<span id="details-userMobile"></span></div>
            <div class="address" style="display: none">地址：<span id="details-userAddr"></span></div>
            <div class="status">状态：<span id="details-userStatus"></span></div>
        </div>
    </div>
</div>
<%--修改密码--%>
<div class="user-modify-pwd">
    <table>
        <tr>
            <td>新密码：</td>
            <td>
                <input id="user-new-pwd" type="password"  onKeyUp="value=value.replace(/[^\w\.\/]/ig,'')" maxlength="15" minlength="6"
                placeholder="密码允许英文、数字、点和下划线">
            </td>
        </tr>
    </table>
</div>
<div class="user-leading">
    <form id="uploadFile" enctype="multipart/form-data" method="post">
        <input id="upload_fileName" type="text" readonly/>
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

    <form id="uploadFiles" enctype="multipart/form-data" method="post">

        <input id="upload_fileNames" type="text" readonly/>
        <a href="javascript:void(0)">
            <input id="uploads" name="myFile" type="file"   value=""  />选择图片
        </a>

    </form>
    <div id="outer">
        <div class="jcExample">
            <div class="article">
                <table>
                    <tr>
                        <td style="width:100px;height:100px;overflow:hidden; display: none"  id="aaa">
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

<%--分配管理员--%>
<div class="allot-admin">
    <div class="m-search search-teacher">
        <input type="text" placeholder="请输入姓名进行搜索">
        <button class="btn-search"></button>
    </div>
    <div class="allot-admin-title">
        <div class="item">
            <div class="check"></div>
            <div class="number">编号</div>
            <div class="name">姓名</div>
            <div class="tel">联系方式</div>
        </div>
    </div>
    <div class="allot-admin-content">
        <%--<div class="item">--%>
            <%--<div class="check">--%>
                <%--<input type="checkbox" teacherId="1001">--%>
            <%--</div>--%>
            <%--<div class="number">T000001</div>--%>
            <%--<div class="name">张老师</div>--%>
            <%--<div class="tel">155533336666</div>--%>
        <%--</div>--%>
    </div>
    <div class="m-page allot-admin-page">
        <!-- 这里显示分页 -->
    </div>
</div>

<div id="win-allot" style="display: none;">
    <div class="win-block-mul">
        <div class="win-block-mul-tit">
            <div class="win-block-mul-title">
                <span>地点</span>
            </div>
            <input type="hidden" id="userId" value="">
            <div class="win-block-mul-main">
                <div class="win-block-mul-main-tit">
                    <div class="win-block-mul-s">
                        <select id="win-sys" name="win-sys">
                        </select>
                    </div>
                </div>
                <div class="win-block-mains mCustomScrollbar">
                    <input type="hidden" id="deviceTree-id">
                    <input type="hidden" id="deviceTree-name">
                    <div class="left-device-tree">

                    </div>
                </div>
            </div>
        </div>
        <div class="three-icon">
            <span></span>
        </div>
        <div class="win-block-mul-li">
            <div class="win-block-mul-title">
                <span>可选设备</span>
            </div>
            <div class="win-block-mul-main x-subt">
                <div class="win-block-mul-main-tit">
                    <div class="win-block-mul-d">
                        <div class="m-search">
                            <input type="text" placeholder="请输入设备名称进行搜索" id="win-input-i">
                            <button class="btn-search search-allDevice"></button>
                        </div>
                        <span class="win-select-block blue">选择全部</span>
                    </div>
                </div>
                <div class="win-block-mains mCustomScrollbar" id="allDeviceList">

                </div>
            </div>
        </div>
        <div class="three-icon">
            <span></span>
        </div>
        <div class="win-block-mul-li">
            <div class="win-block-mul-title">
                <span>已选设备</span>
            </div>
            <div class="win-block-mul-main y-subt">
                <div class="win-block-mul-main-tit">
                    <div class="win-block-mul-d">
                        <span class="win-select-block hot">移除全部</span>
                    </div>
                </div>
                <div class="win-block-mains mCustomScrollbar" id="win-deviceList">
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/libs/mod.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/popup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/users.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cxcalendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.Jcrop.min.js"></script>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/user/tab.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/user/campusTree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cxmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cxmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/device.bean.js"></script>
<script>
//    var Tab = require('tab/tab-li');
//    new Tab(".user-tab",function(){},{});
    require('header/header');
// 初始机构树
    var Tree = require('campus/tree');
    new Tree('.org-menu',function(){});
    var user = require('users/users');
    user.init("<%=HttpServiceUtil.getStorageIp()%>","<%=HttpServiceUtil.getStoragePort()%>");
    var $ = require("jquery");
    require('calendar/cxcalendar');
    $('#userBirthday').cxCalendar();
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
    // 点击切换tab页
    $('.campus-tab ul li').on('click', function () {
        $(this).addClass('current').siblings().removeClass('current');
        var key = $(this).attr('key');
        if (key === 'teacher') {
            $('.btn-allot-admin').show();
        } else {
            $('.btn-allot-admin').hide();
        }
        user.userType = key;
        user.initUserList(1);
    })
</script>
</body>

</html>