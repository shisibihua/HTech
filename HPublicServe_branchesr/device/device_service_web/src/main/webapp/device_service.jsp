<%@ page isELIgnored="false" %>
<%@ page import="com.honghe.service.client.Result" %>
<%@ page import="com.honghe.web.device.util.DeviceNameUtil" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="com.honghe.web.device.util.HttpServiceUtil" %>
<%@ page import="jodd.util.URLDecoder" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>设备管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/search.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/check.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/selectlist.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/default.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/a-button.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/button.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/libs/layer/skin/layer.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/winDeviceAdd.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
</head>

<body>
<div class="m-header">
    <div class="m-header-con">
        <div class="logo">
            <span></span>
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="设备服务系统" title="设备服务系统">
        </div>
        <div class='name'>
            <h1>设备管理</h1>
            <span>Equipment Management</span>
        </div>
        <div class="nav">
            <ul class="layui-nav">
               <%-- <%
                    String param = request.getQueryString();
                    String param2="";
                    String userId = request.getParameter("userId");
                    Map params = new HashMap();
                    params.put("userId", userId);
//          System.out.println("userId===" + userId);
                    Result result = HttpServiceUtil.userService("userSearch", params);
                    if (result.getCode() == 0 && result.getValue() != null) {
                        String  userRealName = ((Map<String, String>) result.getValue()).get("userRealName");
                        param2 = param.substring(0,param.indexOf("userRealName"))+"userRealName="+userRealName;

                    }

                %>--%>
                <li class="layui-nav-item layui-this"><a class="selected" href="${pageContext.request.contextPath}/device_service.jsp?">设备管理</a></li>
                <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/device_block.jsp?">设备分组</a></li>
            </ul>
        </div>
        <div class="user">
            <%--<%--%>
                <%--String userRealName = "";--%>
                <%--if (request.getAttribute("userRealName") != null) {--%>
                    <%--userRealName = (String) request.getAttribute("userRealName");--%>
                    <%--request.setAttribute("userRealName", userRealName);--%>
                <%--}else{--%>
                    <%--Result result = HttpServiceUtil.userService("userSearch", params);--%>
                    <%--if (result.getCode() == 0 && result.getValue() != null) {--%>
                        <%--userRealName = ((Map<String, String>) result.getValue()).get("userRealName");--%>
                        <%--request.setAttribute("userRealName", userRealName);--%>
                    <%--}--%>
                <%--}--%>
            <%--%>--%>
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
<div class="user-main">
    <div class="main-top">
        <div class="main-top-info">
        </div>

    </div>
    <div class="main-list">
        <div class="main-list-info">
            <div class="main-list-info-li">
                <div class="m-search">
                    <input type="text" id="search" placeholder="请输入设备名称或ip进行搜索">
                    <button class="btn-search"></button>
                </div>
            </div>
            <div class="main-list-info-li">
                <select id="system" name="system">
                </select>
            </div>
            <div class="user-btns">
                <%--<button class="btn btn-blue btn-zadd-device" type="button">自动添加</button>--%>
                <button class="btn btn-blue btn-add-device" type="button">手动添加</button>
                <button class="btn btn-red btn-delete-device" type="button">批量删除</button>
            </div>
        </div>
        <div class="u-title">
            <div class="u-check">
                <i class="m-check-all"></i>
            </div>
            <div class="u-name">名称</div>
            <div class="u-number">类型</div>
            <div class="u-tel">IP</div>
            <div class="u-email">所属地点</div>
            <%--<div class="u-deviceCode">设备编号</div>--%>
            <div class="u-operation">操作</div>
        </div>
        <div id="deviceList">
        </div>
        <div class="m-page" style="margin-top: 36px">
            <!-- 这里显示分页 -->
        </div>
    </div>
</div>
<%--<div>--%>
<%--<iframe id="abcde" src="${pageContext.request.contextPath}/device_add.jsp" hidden="hidden"></iframe>--%>
<%--</div>--%>
<%
    ResourceBundle resourceBundle = ResourceBundle.getBundle("copyright");
%>
<div class="m-footer">
    <%=new String(resourceBundle.getString("copyright").getBytes("ISO-8859-1"),"utf-8")%>
    <%--Copyright © 2016-2018  鸿合科技 All Rights Reserved.--%>
</div>

<%--设备添加界面--%>
<div class="win-device-add" style="display: none">
    <div class="win-device-add-title" style="position:relative;z-index:1000">
        <p>设备类型:</p>
        <select id="select-device" name="select-device">
        </select>
    </div>
    <%--设备添加弹出框界面--%>
    <div class="win-device-add-main">
        <%--安防设备--%>
        <div class="win-device-add-li dtype" id="<%=DeviceNameUtil.DEVICE_SECURITYEQUIPMENT%>">
            <ul>
                <li>
                    <p>设备名称：</p>
                    <input class="hostName" type="text">
                </li>
                <li>
                    <p>IP地址：</p>
                    <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                </li>
                <li>
                    <p>端口号：</p>
                    <input id="hostPort" type="text"   maxlength="5" onkeyup="value=value.replace(/[^\d]/ig,'')">
                </li>
                <li>
                    <p>通道号：</p>
                    <input id="hostChannel" type="text" onkeyup="value=value.replace(/[^\d]/ig,'')">
                </li>
                <li>
                    <p>用户名：</p>
                    <input id="userName" type="text" onkeyup="value=value.replace(/[^\w]/ig,'')">
                </li>
                <li>
                    <p>密码：</p>
                    <input id="password" type="text" onKeyUp="value=value.replace(/[\u4E00-\u9FA5\uFF00-\uFFFF]/g,'')">
                </li>

                <li>
                    <p>支持巡课：</p>
                    <div class="select-dg">
                        <span id="istour" class="" valnum="1"><span></span></span>
                        <font>是</font>
                        <span id="notour" valnum="0"></span>
                        <font>否</font>
                    </div>
                </li>
            </ul>
        </div>
        <%--音频设备--%>
        <div class="win-device-add-li dtype" id="<%=DeviceNameUtil.DEVICE_AUDIOEQUIPMENT%>">
            <ul>
                <li>
                    <p>设备名称：</p>
                    <input class="hostName" type="text">
                </li>
                <li>
                    <p>设备ID：</p>
                    <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d]/ig,'')">
                </li>
            </ul>
        </div>
            <%--中控设备--%>
            <div class="win-device-add-li dtype" id="<%=DeviceNameUtil.DEVICE_CENTERCONTROL%>">
            <ul>
                <li>
                    <p>设备名称：</p>
                    <input class="hostName" type="text">
                </li>
                <li>
                    <p>设备Ip：</p>
                    <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                </li>
            </ul>
        </div>
            <%--会议终端设备--%>
            <div class="win-device-add-li dtype" id="<%=DeviceNameUtil.DEVICE_CONFERENCETERMINAL%>">
                <ul>
                    <li>
                        <p>设备名称：</p>
                        <input class="hostName" type="text">
                    </li>
                    <li>
                        <p>设备Ip：</p>
                        <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                    </li>
                    <li>
                        <p>设备编号：</p>
                        <input class="meetingClientCode" type="text"  maxlength="20">
                    </li>
                </ul>
            </div>
        <%--定位天线--%>
        <div class="win-device-add-li dtype" id="<%=DeviceNameUtil.DEVICE_POSITIONANTENNA%>">
            <ul>
                <li>
                    <p>设备名称：</p>
                    <input class="hostName" type="text">
                </li>
                <li>
                    <p>ip地址：</p>
                    <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                </li>
            </ul>
        </div>
        <%--大屏设备--%>
        <div class="win-device-add-li dtype" id="<%=DeviceNameUtil.DEVIC_SCREEN%>">
            <ul>
                <li>
                    <p>设备名称：</p>
                    <input class="hostName" type="text">
                </li>
                <li>
                    <p>ip地址：</p>
                    <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                </li>
            </ul>
        </div>
        <%--投影机--%>
        <div class="win-device-add-li dtype" id="<%=DeviceNameUtil.DEVICE_PROJECTOR%>">
            <ul>
                <li>
                    <p>设备名称：</p>
                    <input class="hostName" type="text">
                </li>
                <li>
                    <p>ip地址：</p>
                    <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                </li>
            </ul>
        </div>
        <%--白板设备--%>
        <div class="win-device-add-li dtype" id="<%=DeviceNameUtil.DEVICE_WHITEBOARD%>">
            <ul>
                <li>
                    <p>设备名称：</p>
                    <input class="hostName" type="text">
                </li>
                <li>
                    <p>ip地址：</p>
                    <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                </li>
            </ul>
        </div>
        <%--数字班牌--%>
        <div class="win-device-add-li dtype" id="<%=DeviceNameUtil.DEVICE_DIGSIGNBOARD%>">
            <ul>
                <li>
                    <p>设备名称：</p>
                    <input class="hostName" type="text">
                </li>
                <li>
                    <p>ip地址：</p>
                    <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                </li>
            </ul>
        </div>
            <%--视频会议设备--%>
            <div class="win-device-add-li dtype" id="<%=DeviceNameUtil.DEVICE_VIDEO_CONFERENCING%>">
                <ul>
                    <li>
                        <p>设备选择：</p>
                        <select id="select-mcu" name="select-mcu">
                            <option value="<%=DeviceNameUtil.DEVICE_VIDEO_NJXD%>">鸿合互动MCU</option>
                            <option value="<%=DeviceNameUtil.DEVICE_VIDEO_IFREECOMM%>">捷视飞通MCU</option>
                            <%--<option value="<%=DeviceNameUtil.DEVICE_VIDEO_CISCO%>">CiscoMCU</option>--%>
                            <option value="<%=DeviceNameUtil.DEVICE_VIDEO_VHD%>">鸿合内置MCU</option>
                            <option value="<%=DeviceNameUtil.DEVICE_VIDEO_HD300B%>">HD300B</option>
                        </select>
                    </li>
                    <%--鸿合互动MCU--%>
                    <div class="w-mcu-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_VIDEO_NJXD%>">
                        <ul>
                            <li>
                                <p>设备名称：</p>
                                <input class="hostName" type="text">
                            </li>
                            <li>
                                <p>ip地址：</p>
                                <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                            </li>
                            <%--设备编码 add by caoqian--%>
                            <li>
                                <p>设备编号：</p>
                                <input class="clientCode" type="text" maxlength="20">
                            </li>
                        </ul>
                    </div>
                    <%--CiscoMCU--%>
                    <div class="w-mcu-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_VIDEO_CISCO%>">
                        <ul>
                            <li>
                                <p>设备名称：</p>
                                <input class="hostName" type="text">
                            </li>
                            <li>
                                <p>ip地址：</p>
                                <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                            </li>
                            <%--设备编码 add by caoqian--%>
                            <li>
                                <p>设备编号：</p>
                                <input class="clientCode" type="text" maxlength="20">
                            </li>
                        </ul>
                    </div>
                    <%--维海得MCU--%>
                    <div class="w-mcu-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_VIDEO_VHD%>">
                        <ul>
                            <li>
                                <p>设备名称：</p>
                                <input class="hostName" type="text">
                            </li>
                            <li>
                                <p>ip地址：</p>
                                <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                            </li>
                            <li>
                                <p>设备编号：</p>
                                <input class="clientCode" type="text" maxlength="20">
                            </li>
                        </ul>
                    </div>

                    <%--捷视飞通MCU--%>
                    <div class="w-mcu-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_VIDEO_IFREECOMM%>">
                        <ul>
                            <li>
                                <p>设备名称：</p>
                                <input class="hostName" type="text">
                            </li>
                            <li>
                                <p>ip地址：</p>
                                <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                            </li>
                            <%--设备编码 add by caoqian--%>
                            <li>
                                <p>设备编号：</p>
                                <input class="clientCode" type="text" maxlength="20">
                            </li>
                        </ul>
                    </div>
                    <%--捷士飞通设备--%>
                    <div class="w-mcu-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_VIDEO_HD300B%>">
                        <ul>
                            <li>
                                <p>设备名称：</p>
                                <input class="hostName" type="text">
                            </li>
                            <li>
                                <p>ip地址：</p>
                                <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                            </li>
                            <li>
                                <p>设备编号：</p>
                                <input class="clientCode" type="text" maxlength="20">
                            </li>
                        </ul>
                    </div>
                </ul>
            </div>
        <%--录播设备--%>
        <div class="win-device-add-li dtype" id="<%=DeviceNameUtil.DEVICE_RECOURD%>">
            <ul>
                <li>
                    <p>设备选择：</p>
                    <select id="select-dws" name="select-dws">
                        <option value="<%=DeviceNameUtil.DEVICE_FINE_RECOURD%>">服务器架构精品</option>
                        <option value="<%=DeviceNameUtil.DEVICE_EMBEDDED_RECOURD%>">嵌入式精品录播主机(ZJ0500)</option>
                        <option value="<%=DeviceNameUtil.DEVICE_NORMALIZATION_RECOURD%>">常态化录播主机</option>
                        <option value="<%=DeviceNameUtil.DEVICE_TBOX_RECOURD%>">TBOX</option>
                        <option value="<%=DeviceNameUtil.DEVICE_WBOX_RECOURD%>">常态化录播主机（ZF0100)</option>
                        <option value="<%=DeviceNameUtil.DEVICE_CLASSROOMMONITOR%>">教室监控</option>
                        <option value="<%=DeviceNameUtil.DEVICE_OPS_RECOURD%>">ops录播主机</option>
                    </select>
                </li>
                <%--精品录播--%>
                <div class="w-tongdao-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_FINE_RECOURD%>">
                    <ul>
                        <li>
                            <p>设备名称：</p>
                            <input class="hostName" type="text">
                        </li>
                        <li>
                            <p>ip地址：</p>
                            <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                        </li>
                    </ul>
                </div>
                <%--爱录客录播主机--%>
                <div class="w-tongdao-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_EMBEDDED_RECOURD%>">
                    <ul>
                        <li>
                            <p>设备名称：</p>
                            <input class="hostName" type="text">
                        </li>
                        <li>
                            <p>ip地址：</p>
                            <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                        </li>
                    </ul>
                </div>
                <%--简易录播主机--%>
                <div class="w-tongdao-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_NORMALIZATION_RECOURD%>">
                    <ul>
                        <li>
                            <p>设备名称：</p>
                            <input class="hostName" type="text">
                        </li>
                        <li>
                            <p>Ip地址：</p>
                            <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                        </li>
                    </ul>
                </div>
                <%--WBOX录播主机--%>
                <div class="w-tongdao-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_WBOX_RECOURD%>">
                    <ul>
                        <li>
                            <p>设备名称：</p>
                            <input class="hostName" type="text">
                        </li>
                        <li>
                            <p>ip地址：</p>
                            <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                        </li>
                    </ul>
                </div>
                <%--TBOX录播主机--%>
                <div class="w-tongdao-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_TBOX_RECOURD%>">
                    <ul>
                        <li>
                            <p>设备名称：</p>
                            <input class="hostName" type="text">
                        </li>
                        <li>
                            <p>ip地址：</p>
                            <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                        </li>
                    </ul>
                </div>
                <%--ops录播主机--%>
                <div class="w-tongdao-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_OPS_RECOURD%>">
                    <ul>
                        <li>
                            <p>设备名称：</p>
                            <input class="hostName" type="text">
                        </li>
                        <li>
                            <p>ip地址：</p>
                            <input class="hostIp" type="text" onkeyup="value=value.replace(/[^\d\.]/ig,'')">
                        </li>
                    </ul>
                </div>
                <%--教室监控--%>
                <div class="w-tongdao-device-lis dtype" id="<%=DeviceNameUtil.DEVICE_CLASSROOMMONITOR%>">
                    <li>
                        <p>班级名称：</p>
                        <input class="hostName" type="text">
                    </li>
                    <div class="w-tongdao-device-title">
                        <span>通道</span>
                    </div>
                    <div class="w-tongdao-device-m mCustomScrollbar">
                        <input type="hidden" id="win-add-dev">
                        <div class="w-tongdao-device">
                            <li>
                                <p>通道名称：</p>
                                <input class="portName" type="text">
                            </li>
                            <li>
                                <p>通道地址：</p>
                                <input class="metString" type="text">
                            </li>
                            <span class="delete-tongdao-device"><span>删除</span></span>
                        </div>
                    </div>
                    <div class="add-tongdao-device">
                        <span>添加通道</span>
                    </div>
                </div>
            </ul>
        </div>
    </div>
</div>
<%--设备编辑弹出框的内容--%>
<div class="win-device-edit" style="display: none">
    <div class="win-device-edit-main">
        <div class="win-device-edit-li dtype">
            <ul>
                <li>
                    <p>设备名称：</p>
                    <input class="hostName" value="" type="text">
                </li>
            </ul>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/libs/mod.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/search.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/check.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/device.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/layui/layui.js"></script>

<script>
    require('header/header');
    require('search/search');
    require('check/check');
    require('libs/layer/layer');
    require('device/device')
    var $ = require("jquery")
    layui.use('element', function(){
        var element = layui.element(); //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function(elem){

        });
    });
    var SelectList = require('select-list/select');

    var device = require('device/device');
    device.init();
    function tablist(tab_list, tab) {
        tab.each(function () {
            var x = $(this).attr("id");
            if (x == tab_list) {
                $(this).show();
            } else {
                $(this).hide();
            }
        })
    }
    var SelectList = require('select-list/select');
    var select = new SelectList('#select-device', function (option) {
        //处理选中的设备类型，不包括录播子类型
        var tab_list = option.attr('data-value');
        console.log('value = ' + option.attr('data-value'));
        var tab = $(".win-device-add-li");
        tablist(tab_list, tab);
        var select_va = $("input[name='select-dws']").val();
        tablist(select_va, $(".w-tongdao-device-lis"));
    });
    select.init();
    var select = new SelectList('#select-dws', function (option) {
//    console.log('value = ' + option.attr('data-value'));
//    console.log('text = ' + option.text());
        var tab_list = option.attr('data-value');
        var tab = $(".w-tongdao-device-lis");
        tablist(tab_list, tab);
    });
    select.init();
    //设置mcu选择框样式
    var select= new SelectList('#select-mcu', function (option) {
        var tab_list = option.attr('data-value');
        var tab = $(".w-mcu-device-lis");
        tablist(tab_list, tab);
    });
    select.init();

    var select_val = $("input[name='select-device']").val();
    tablist(select_val, $(".win-device-add-li"));

    //处理巡课单选按钮
    $(document).on("click", ".select-dg > span", function () {
        var flag = $(this).find("span").length;
        $(".select-dg > span").find("span").remove();
        if (flag == 0) {
            $(this).append("<span></span>");
            $(this).addClass('selected');
            $(this).siblings().removeClass('selected');
        } else {
            if (flag == 1) {
                $(this).append("<span></span>");
            } else {
                $(this).find("span").remove();
            }
        }
    })

    //处理添加和删除通道按钮功能
    $(document).on("click", ".delete-tongdao-device > span", function () {
        $(this).parents(".w-tongdao-device").remove();
    })
    $(document).on("click", ".add-tongdao-device > span", function () {
        var str = '<div class="w-tongdao-device">' +
                '<li>' +
                '<p>通道名称：</p>' +
                '<input class="portName" type="text">' +
                '</li>' +
                '<li>' +
                '<p>通道地址：</p>' +
                '<input class="metString" type="text">' +
                '</li>' +
                '<span class="delete-tongdao-device"><span>删除</span></span>' +
                '</div>'
        var li_leng = $(".w-tongdao-device").length;
        // if(li_leng != 0 ){
        //     $(".w-tongdao-device:eq(0)").before(str);
        // }else{
        $(this).parents(".w-tongdao-device-lis").find("#win-add-dev").after(str);
    })

</script>
</body>

</html>