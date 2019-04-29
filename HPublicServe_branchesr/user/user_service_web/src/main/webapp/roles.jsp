<%@ page import="com.honghe.web.user.util.HttpServiceUtil" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.honghe.service.client.Result" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@ page isELIgnored="false" %>
<head>
    <meta charset="UTF-8">
    <title>角色管理</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/components.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/selectlist.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme/default/default.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/libs/layer/skin/layer.css"/>
</head>

<body>
<div class="m-header">
    <div class="m-header-con">
        <%
            String param = request.getQueryString();
            String param2="";
            String userId = request.getParameter("userId");
            Map params = new HashMap();
            params.put("userId", userId);
//                System.out.println("userId===" + userId);

            Result result = HttpServiceUtil.userService("userSearch", params);
            String  userRealName = "";
            if (result.getCode() == 0 && result.getValue() != null) {
                Map<String, String> map = (Map<String, String>) result.getValue();
                userRealName = map.get("userRealName");
//                params.put("abc", userRealName);

            }

        %>
        <%--<div class="logo"><span></span><img src="${pageContext.request.contextPath}/images/logo.png" alt="用户服务系统" title="用户服务系统">用户服务系统</div>--%>
        <div class="logo">
            <span></span>
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="用户管理" title="用户管理">
        </div>
        <div class='name'>
            <h1>用户管理</h1>
            <span>User Management</span>
        </div>
        <div class="nav">
            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/roles.jsp?id=0&userRealName=<%=userRealName%>&userId=${param.userId}&logOut=${param.logOut.replaceAll("/","%2F").replaceAll(":","%3A")}">角色权限</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/campus.jsp?id=1&userRealName=<%=userRealName%>&userId=${param.userId}&logOut=${param.logOut.replaceAll("/","%2F").replaceAll(":","%3A")}">组织机构</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/users.jsp?id=2&userRealName=<%=userRealName%>&userId=${param.userId}&logOut=${param.logOut.replaceAll("/","%2F").replaceAll(":","%3A")}">用户管理</a>
                </li>
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

<div class="main">
    <div class="main-left">
        <div class="left-top">
            <h1>角色权限</h1>
            <button class="btn btn-blue btn-add-role" type="button">添加角色</button>
        </div>
        <!--所有角色-->
        <div class="mCustomScrollbar scrollbar-left" data-mcs-theme="minimal-dark">
            <ul class="m-menu" id="roles">
            </ul>
        </div>
    </div>
    <div class="main-right">
        <div class="right-top">
            <h6></h6>
        </div>
        <div class="select-system">
            所属系统：
            <!--该角色对应的所有系统-->
            <select id="system" name="system"></select>
        </div>
        <div class="mCustomScrollbar" data-mcs-theme="minimal-dark" style="height:410px;">
            <div class="permissions-item" id="testPer">

            </div>
        </div>
        <div class="operations-btns">
            <button class="btn btn-blue btn-role-confirm" type="button">确 定</button>
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
<!--    添加角色-->
<div class="add-role">
    <div class="add-role-name" id="role-name">
        角色名称：<input id="role_name" class="role-name" style="margin-left: 30px" type="text" maxlength="45">
    </div>
    <!--<div class="add-role-con">
        <p>对应身份：</p>
        <div id="usertype_List" class="con-list">
        </div>
    </div>-->
</div>

<script src="${pageContext.request.contextPath}/js/libs/mod.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/popup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/roles-manage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/layer/layer.js"></script>
<script>
    var $ = require('jquery');
    require('header/header');
    var roles = require('roles/roles-manage');
    roles.init();
    //    $(".main-left").css("min-height",$(".main-right").height());
    //    $(".mCustomScrollbar").css("max-height",$(".main-left").height()-50);
    //    $(window).resize(function(){
    //        $(".main-left").css("min-height",$(".main-right").height());
    //        $(".mCustomScrollbar").css("max-height",$(".main-left").height()-50);
    //    })
    //    判断是不是管理员，不是管理员屏蔽添加功能
    var isAdmin = JSON.parse(sessionStorage.getItem('isAdmin'));
    var isSuperAdmin = JSON.parse(sessionStorage.getItem('isSuperAdmin'));
    if (isAdmin && isSuperAdmin) {
        $('.left-top .btn-add-role').show();
        $('.operations-btns .btn-role-confirm').show();

    } else {
        $('.left-top .btn-add-role').hide();
        $('.operations-btns .btn-role-confirm').hide();
    }
</script>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mousewheel.js"></script>
</html>