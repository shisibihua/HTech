<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.honghe.service.client.Result" %>
<%@ page import="com.honghe.web.device.util.HttpServiceUtil" %>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>设备分组</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cxmenu.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/search.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/check.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/selectlist.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/components.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/global.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/button.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/a-button.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/libs/layer/skin/layer.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/winDeviceAdd.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.css"/>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/layui/css/layui.css"/>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
</head>
<body>
<%--显示头部信息--%>
<div class="m-header">
  <div class="m-header-con">
    <div class="logo">
      <span></span>
      <img src="${pageContext.request.contextPath}/images/logo.png" alt="设备服务系统" title="设备服务系统">
    </div>
    <div class='name'>
      <h1>设备管理</h1>
      <span>Equipment Management </span>
    </div>
    <div class="nav">
      <ul class="layui-nav">
        <%
          String param = request.getQueryString();
          String param2="";
          String userId = request.getParameter("userId");
          Map params = new HashMap();
          params.put("userId", userId);
//          System.out.println("userId===" + userId);
          Result result = HttpServiceUtil.userService("userSearch", params);
          if (result.getCode() == 0 && result.getValue() != null) {
            Map<String, String> map = (Map<String, String>) result.getValue();
            String  userRealName = map.get("userRealName");
            param2 = param.substring(0,param.indexOf("userRealName"))+"userRealName="+userRealName;

          }

        %>
        <li class="layui-nav-item"><a  href="${pageContext.request.contextPath}/device_service.jsp?<%=param2%>">设备管理</a></li>
        <li class="layui-nav-item layui-this"><a class="selected" href="${pageContext.request.contextPath}/device_block.jsp?<%=param2%>">设备分组</a></li>
      </ul>
    </div>
    <div class="user">
      <%--<ul class="layui-nav">--%>
        <%--<li class="layui-nav-item">--%>
          <%--<span class="user_before">--%>
            <%--<img src="${pageContext.request.contextPath}/images/user-head.png" class="user_img" />--%>
          <%--</span>--%>
          <%--<a href="javascript:;"  class="user_name">${param.userRealName}</a>--%>
          <%--<dl class="layui-nav-child">--%>
            <%--<dd><a class="logout" href="${param.logOut}">退出</a></dd>--%>
          <%--</dl>--%>
        <%--</li>--%>
      <%--</ul>--%>
        <li><a href="javascript:void(0)">${param.userRealName}</a></li>
        <span class="line-head"></span>
        <li><a class="logout" href="${param.logOut}">返回</a></li>
    </div>
  </div>
</div>
<%--显示头部信息完--%>
<div class="main">
  <%--左侧地点树--%>
  <div class="main-left">
    <div class="left-top">
      <h1>设备地点</h1>
      <%--<span class="top-dr">导入机构</span>--%>
      <%--<span class="top-xz">下载模板</span>--%>
    </div>
    <div class="mCustomScrollbar" style="height: 630px;">
      <div class="org-menu">
      </div>
    </div>
  </div>
    <%--右侧设备信息--%>
  <div class="main-right">
    <div class="right-top" ><p></p><span class="sprt-list"></span><span class="sprt-tab"></span></div>
    <div class="right-search">
      <div class="select-litit">
        <div class="m-search">
          <input type="text" placeholder="请输入设备名称或ip进行搜索" >
          <button class="btn-search"></button>
        </div>
      </div>
      <div class="select-litit" id ="selecttype">
      </div>

      <button class="btn btn-red btn-delete-members">批量移除</button>
      <button class="btn btn-blue btn-allot" >分配设备</button>
    </div>
    <div class="devive-list-ls">
      <!-- 列表显示 -->
    </div>
    <div class="user-list">
      <ul>
        <!-- 图片显示 -->
    </ul>

  </div>
  <div class="clearfix"></div>

  <div class="m-page">
    <!-- 这里显示分页 -->
  </div>

    <!--    添加机构-->
    <div class="add-org">
      <div class="select-org">
        <p class="add-org-t-u"> &nbsp;&nbsp;&nbsp;当前地点：</p>
        <div class="current-organization"></div>
      </div>
      <div class="add-org-u">
        <p class="add-org-t-u" id="areaType"><span style="color: red">* &nbsp;</span>地点类型：</p>
        <select id='winleix' name='winleix' >
        </select>
      </div>
      <div class="add-org-u" id="room"  >
        <p class="add-org-t-u" id="roomType"><span style="color: red">* &nbsp;</span>教室类型：</p>
        <select id='winroom' name='winroom' >
        </select>
      </div>
      <div class="add-org-u org-name">
        <p class="add-org-t-u"><span style="color: red">* &nbsp;</span>地点名称：</p>
        <input class="organization-name " type="text" >
      </div>
      <div class="add-org-u" >
        <p class="add-org-t-u"  id="campus">&nbsp;&nbsp;&nbsp;校区： </p>
        <select id="winxiaoqu" name="winxiaoqu">
        </select>
      </div>
      <div class="add-org-u">
        <p class="add-org-t-u" >&nbsp;&nbsp;&nbsp;容纳人数：</p>
        <input class="org-input" id="totalstu" type="text" maxlength="9" onkeyup="this.value=this.value.replace(/[^\d]/g,'') ">
      </div>
      <div class="add-org-u">
        <p class="add-org-t-u"><span style="color: red">* &nbsp;</span>是否选课：</p>
        <div class="select-sf">
          <span ><span value="1" id="1"></span>是</span>
          <span ><span value="0" id="0"></span>否</span>
        </div>
      </div>
      <div class="add-org-u">
        <p class="add-org-t-u">&nbsp;&nbsp;&nbsp;备注：</p>
        <textarea class="org-text"id="remark" type="text"></textarea>
      </div>

    </div>

    <!--    批量删除/删除机构-->
    <div class="m-delete">
      确定删除此地点吗？
    </div>

    <!--    批量删除/删除地点-->
    <div class="m-delete-org-user">
      确定从该地点中删除此设备吗？
    </div>
    <!--详情-->
    <div class="m-details">

    </div>

    <!--分配地点-->
    <div class="m-allot-member win-block-fep">
      <div class="win-block-fep-title">
        <div class="select-litit">
          <div class="m-search">
            <input type="text" id="search" placeholder="请输入设备名称或ip进行搜索" >
            <button class="btn-search"></button>
          </div>
        </div>


        <div class="select-litit" id="allocateselect">
        </div>
      </div>
      <div class="win-block-fep-main">
        <div class="win-block-fep-main-title">
          <div class="u-check">
            <i class="a-check-all "></i>
          </div>
          <span class="w-block-fep-name">名称</span>
          <span class="w-block-fep-lei">类型</span>
          <span class="w-block-fep-ip">IP</span>
          <span class="w-block-fep-jg">所属地点</span>
        </div>
        <div class="win-block-fep-main-infoa">
        </div>
      </div>
      <div class="a-page m-page">
        <!-- 这里显示分页 -->
      </div>
    </div>

</div>
</div>
<div class="m-footer">
  Copyright © 2016-2018  鸿合科技 All Rights Reserved.
</div>
<script src="${pageContext.request.contextPath}/js/libs/mod.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cxmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cxmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/search.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/check.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/block.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/layui/layui.js"></script>
<script>
  var $ = require('jquery');

  require('header/header');
  require('search/search');
  require('cxMenu/cxmenu');
  require('check/check');
  require('device/block');
  require('libs/layer/layer');

  layui.use('element', function(){
    var element = layui.element(); //导航的hover效果、二级菜单等功能，需要依赖element模块

    //监听导航点击
    element.on('nav(demo)', function(elem){

    });
  });
</script>
</body>

</html>