<%--
  Created by IntelliJ IDEA.
  User: qinzhihui
  Date: 2016/10/10 0010
  Time: 9:48:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>目录服务</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cxmenu.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/search.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/check.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/selectlist.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme/default/default.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/button.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/a-button.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalog.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/libs/layer/skin/layer.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/winDeviceAdd.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shake.css" />
</head>

<body>
<div class="m-header">
    <div class="m-header-con">
        <div class="logo"><span></span><img src="${pageContext.request.contextPath}/images/mulicon.png" alt="" title="">目录服务系统
        </div>
        <div class="nav">
            <!-- <ul>
                <li><a href="device_service.html?id=0">设备管理</a></li>
                <li><a href="device_block.html?id=1">设备分组</a></li>
            </ul> -->
        </div>

        <div class="user">
                <li><a href="javascript:void(0)">${param.userName}</a></li>
                <span class="line-head"></span>
                <li><a class="logout" href="${param.logOut}">返回</a></li>
                <%--<li><a href="javascript:void(0)">返回首页</a></li>--%>
        </div>
    </div>
</div>

<div class="main">
    <div class="main-catalog-left">
        <div class="main-catalog-top">
            <h1>组织机构</h1>
        </div>
        <div class="org-menu">
            <div class="main-catalog-o-s">
                <select id="lxing" name="lxing">
                </select>
            </div>
            <input type="hidden" id="tree-id">
            <input type="hidden" id="tree-name">

            <div class="mCustomScrollbar" style="width: 100%; float:left;height: 700px;">
                <div id="campus-tree"></div>
            </div>
        </div>
    </div>
    <div class="n-line-icon">
        <span></span>
    </div>
    <div class="main-catalog-left">
        <div class="main-catalog-top">
            <div class="main-catalog-o-s">
                <div class="m-search">
                    <input id="userSearchWord" type="text" placeholder="请输入姓名进行搜索">
                    <button class="btn-search search-campus"></button>
                </div>
            </div>
        </div>
        <div class="mCustomScrollbar" style="height: 750px;float: left;width: 100%;">
        <div class="main-catalog-info-p" id="userList">
        </div>
        </div>
    </div>
    <div class="n-line-icon">
        <span></span>
    </div>
    <div class="main-catalog-right">
        <div class="main-catalog-right-search">
            <div class="select-litit" style="margin-left:10px;">
                <div class="m-search">
                    <input id="deviceSearchWord" type="text" placeholder="请输入设备名称进行搜索">
                    <button class="btn-search search-device"></button>
                </div>
            </div>
            <div class="select-litit">
                <select id="systlt" name="systlt">
                </select>
            </div>
        </div>
        <div class="main-catalog-right-search">
            <button class="btn btn-red btn-delete">批量移除</button>
            <button class="btn btn-blue btn-allot">分配设备</button>
        </div>
        <div class="catalog-list mCustomScrollbar">
            <div class="catalog-list-title">
                <div class="u-check">
                    <i class="m-check-all"></i>
                </div>
                <div class="catalog-name">名称</div>
                <div class="catalog-lei">类型</div>
                <div class="catalog-ip">IP</div>
                <div class="catalog-dev">所属地点</div>
                <div class="catalog-cz">操作</div>
            </div>
            <div class="catalog-list-main-z" id="deviceList">
            </div>

        </div>

        <div class="clearfix"></div>

        <div class="m-page">
            <!-- 这里显示分页 -->
        </div>
    </div>
</div>
<div class="m-footer">
    Copyright ©2018 HiteVision.All Rights Reserved.
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cxmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/search.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/check.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mulec.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/device.bean.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cxmenu.js"></script>
<script>
    var $ = require('jquery');

    require('header/header');
    require('search/search');
    require('check/check');
    require('libs/layer/layer');
    require('mCustomScrollbar/jquery.mCustomScrollbar');
    require('mCustomScrollbar/jquery.mousewheel');
    //初始化方法
    var device = require('device/mulec');
    device.init();
    $(".n-line-icon").height($(".main-catalog-left").height());
    $(".n-line-icon > span").css("margin-top", $(".n-line-icon").height() / 2 + "px");
    $(window).resize(function () {
        $(".n-line-icon").height($(".main-catalog-left").height());
        $(".n-line-icon > span").css("margin-top", $(".n-line-icon").height() / 2 + "px");
    })


</script>
</body>

</html>