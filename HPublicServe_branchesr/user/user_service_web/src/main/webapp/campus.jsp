<%@ page import="com.honghe.web.user.util.HttpServiceUtil" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.honghe.service.client.Result" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>组织机构</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cxmenu.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/components.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/selectlist.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme/default/default.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/libs/layer/skin/layer.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.css"/>

    <%--教师管理jsp引入--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.cxcalendar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.Jcrop.min.css"/>
    <%--新增css样式--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/campus.css"/>
    <style>
        iframe {
            width: 100%;
        }

        .content {
            position: relative;
            z-index: 99;
        }

        .student-content, .parent-content {
            display: none;
        }

        #layer {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0, 0, 0, .8);
            display: none;
        }

        .m-delete {
            text-align: center;
            width: 100%;
        }

        .m-delete p {
            width: 100%;
        }

        .left-top .top-dr {
            display: none;
        }

        .left-top .top-xz {
            display: none;
        }
        .select-button{
            width:128px;
        }
        #orgLinkagePro,#orgLinkageCity{
            margin-right: 5px;
        }
    </style>
</head>

<body>
<div class="m-header">
    <div class="m-header-con">
        <%--<div class="logo"><span></span><img src="${pageContext.request.contextPath}/images/logo.png" alt="用户服务系统"--%>
        <%--title="用户服务系统">用户服务系统--%>
        <%--</div>--%>
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
//                System.out.println("userId===" + userId);

                Result result = HttpServiceUtil.userService("userSearch", params);
                String  userRealName = "";
                if (result.getCode() == 0 && result.getValue() != null) {
                    Map<String, String> map = (Map<String, String>) result.getValue();
                    userRealName = map.get("userRealName");
//                params.put("abc", userRealName);

                }

            %>
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

        <div class="user" }>
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
            <h1>组织机构</h1>
            <%--<span class="top-dr">导入机构</span>--%>
            <%--<span class="top-xz">下载模板</span>--%>
        </div>
        <div class="mCustomScrollbar" id="content-8" data-mcs-theme="minimal-dark"
             style="height: 630px;float:left;width: 320px;">
            <div class="org-menu" style="display: none">
                <%--<ul id="left_nav_ul">--%>
                <%--<li>--%>
                <%--</li>--%>
                <%--</ul>--%>
            </div>
            <div class="btn-add-org btnAddOrg">+</div>
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
                    <%--<jsp:include page="/teacher.jsp"></jsp:include>--%>
                    <iframe src="${pageContext.request.contextPath}/teacher.jsp" id="teacher-iframe"
                            frameborder="0"></iframe>
                </div>
                <div class="student-content">
                    <%--<jsp:include page="/student.jsp"></jsp:include>--%>
                    <iframe src="${pageContext.request.contextPath}/student.jsp" id="student-iframe"
                            frameborder="0"></iframe>
                </div>
                <div class="parent-content">
                    <iframe src="${pageContext.request.contextPath}/parent.jsp" id="parent-iframe"
                            frameborder="0"></iframe>
                </div>
            </div>
        </div>
        <%--<div class="right-top" ><p></p><span class="sprt-list"></span><span class="sprt-tab"></span></div>--%>
        <%--<div class="right-search">--%>
        <%--<div class="m-search" >--%>
        <%--<input type="text"  placeholder="请输入姓名进行搜索">--%>
        <%--<button class="btn-search" ></button>--%>
        <%--</div>--%>

        <%--<button class="btn btn-red btn-delete-members">批量移除</button>--%>
        <%--<button class="btn btn-blue btn-allot">分配人员</button>--%>
        <%--<button class="btn btn-blue btn-allot_student" style="display:none">分配学生</button>--%>
        <%--<button class="btn btn-green btn-allot_teacher" style="display: none">分配教师</button>--%>
        <%--<button class="btn btn-green btn-allot_director" style="display: none">分配年级主任</button>--%>
        <%--<button class="btn btn-green btn-allot_rector" style="display: none">分配学校领导</button>--%>


        <%--</div>--%>
        <%--<div class="user-list">--%>
        <%--<ul>--%>
        <%--<!-- 图片显示 -->--%>
        <%--</ul>--%>
        <%--</div>--%>

        <%--<div class="devive-list-ls">--%>
        <%--<!-- 列表显示 -->--%>
        <%--</div>--%>
        <%--<div id="a-m-page" class="m-page">--%>
        <%--<!-- 这里显示分页 -->--%>
        <%--</div>--%>
    </div>
</div>
<div class="campus-leading">
    <form id="campusUploadFile" enctype="multipart/form-data" method="post">
        <input id="campus_upload_fileName" type="text" readonly/>
        <a href="javascript:void(0)">
            <input id="campus_upload" name="myCampusFile" type="file" value=""/>选择文件
        </a>
    </form>
</div>
<div id="layer"></div>
<%
    ResourceBundle resourceBundle = ResourceBundle.getBundle("copyright");
%>
<div class="m-footer">
    <%=new String(resourceBundle.getString("copyright").getBytes("ISO-8859-1"),"utf-8")%>
    <%--Copyright © 2016-2018  鸿合科技 All Rights Reserved.--%>
</div>

<!--    分配人员-->
<div class="m-allot-member">
    <div class="m-search" style="margin: 10px">
        <input type="text" id="text_search_xin" placeholder="请输入姓名进行搜索" maxlength="15">
        <button class="btn-search"></button>
    </div>
    <div class="main-list">

    </div>

    <div class="clearfix"></div>
    <div class="m-page" id="m-page">
        <!-- 这里显示分页 -->
    </div>
</div>
<!--    添加机构-->
<div class="add-org">
    <div class="select-org">
        &nbsp;&nbsp;&nbsp;当前机构：
        <div class="current-organization"></div>
    </div>
    <div class="add-org-u orgSSQ" style="position: relative;">
        <div class="orgSSQbj" style="position: absolute;left:0px;top:0px;width: 100%;height:30px;z-index: 1000;display: none"></div>
        <p class="add-org-t-u"><span style="color: red">* &nbsp;</span>所属行政区域：</p>
        <select id="orgLinkagePro" name="orgLinkagePro">
            <%--<option value="0">请选择省</option>--%>
            <%--<option value="1">定位系统</option>--%>
            <%--<option value="2">设备集控</option>--%>
            <%--<option value="3">设备集控</option>--%>
            <%--<option value="4">设备集控</option>--%>
            <%--<option value="5">设备集控</option>--%>
            <%--<option value="6">设备集控</option>--%>
        </select>
        <select id="orgLinkageCity" name="orgLinkageCity">
            <option value="0">请选择市</option>
            <%--<option value="1">定位系统</option>--%>
            <%--<option value="2">设备集控</option>--%>
            <%--<option value="3">设备集控</option>--%>
            <%--<option value="4">设备集控</option>--%>
            <%--<option value="5">设备集控</option>--%>
            <%--<option value="6">设备集控</option>--%>
        </select>
        <select id="orgLinkageArea" name="orgLinkageArea" style="margin-left:5px;">
            <option value="0">请选择区县</option>
        </select>
    </div>
    <div class="add-org-u" id="orgtype">
        <p class="add-org-t-u" id="typename"><span style="color: red">* &nbsp;</span>机构类型：</p>
        <select id="winleix" name="winleix">
        </select>
    </div>
    <div class="add-org-u org-name">
        <p class="add-org-t-u"><span style="color: red">* &nbsp;</span>机构名称：</p>
        <span><input class="organization-name " id="campusname" type="text" maxlength="45"></span>
    </div>
    <div class="add-org-u schoolProps">
        <p class="add-org-t-u"><span style="color: red">* &nbsp;</span>学段：</p>
        <select id="windidian" name="windidian">
        </select>
    </div>
    <div class="add-org-u schoolProps">
        <p class="add-org-t-u">&nbsp;&nbsp;&nbsp;学年：</p>
        <input class="org-input" id="schoolyear" type="text" maxlength="3"
               onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,'');}).call(this)"
               onblur="this.v()" >
    </div>
    <div class="add-org-u schoolProps">
        <p class="add-org-t-u">&nbsp;&nbsp;&nbsp;预计人数：</p>
        <input class="org-input" id="totalstu" type="text" maxlength="6" onkeyup="this.value=this.value.replace(/[^\d]/g,'') ">
    </div>
    <div class="add-org-u schoolProps">
        <p class="add-org-t-u">&nbsp;&nbsp;&nbsp;备注：</p>
        <textarea class="org-text" id="remark" type="text" maxlength="200"></textarea>
    </div>
    <div class="senior-setting schoolProps" style="display: none;">
        <p class="title" style="display: none">高级设置</p>
        <div class="setting-content clearFix" style="display: none;">
            <div class="ip-setting">
                IP地址：<input id="ip" type="text">
            </div>
            <div class="port-setting">
                端口：<input id="port" type="text">
            </div>
        </div>
    </div>
</div>


<!--    批量删除/删除机构-->
<div class="m-delete">
    <%--<p>删除机构将导致该机构下的视频无法查看</p>--%>
    <p>确定删除此机构吗?</p>
</div>

<!--    批量删除/删除人员-->
<div class="m-delete-org-user">
    确定从该机构中删除此用户吗？
</div>

<!--    详情-->
<%--<div class="m-details">--%>

<%--</div>--%>

<script src="${pageContext.request.contextPath}/js/libs/mod.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cxmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/popup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/allot-member.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/organization.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/organization-user.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/layer/layer.js"></script>
<script>
    var $ = require('jquery');

    require('header/header');

    var Organization = require('organization/organization');
    var org = new Organization("<%=HttpServiceUtil.getStorageIp()%>", "<%=HttpServiceUtil.getStoragePort()%>");
    org.initData();

    var AllotMember = require('organization/allot-member');
    var allot = new AllotMember("<%=HttpServiceUtil.getStorageIp()%>", "<%=HttpServiceUtil.getStoragePort()%>");
    allot.initView();

    $("#content-8").mCustomScrollbar({
        axis: "yx"
    });
</script>

<script>

    $('.content').hide();
    var $ = require("jquery");

    // iframe自适应高度
    function setIframeHeight(iframe) {
        if (iframe) {
            var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
            if (iframeWin.document.body) {
                iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
            }
        }
    };
    //    $('#text_search_xin').on('input', function(e) {
    //        if (this.value.length === 6) { //如果输入长度等于6，则禁用输入
    //            $('input[type="submit"]').prop('disabled', false);
    //        } else {
    //            $('input[type="submit"]').prop('disabled', true);
    //        }
    //    });

    $('.campus-tab ul li').on('click', function () {
        var key = $(this).attr('key');
        if (key === 'teacher') {
            setIframeHeight(document.getElementById('teacher-iframe'));
        } else if (key === 'student') {
            setIframeHeight(document.getElementById('student-iframe'));
        } else if (key === 'parent') {
            setIframeHeight(document.getElementById('parent-iframe'));
        }
    })
    $('#layer').on('click', function () {
        $("#teacher-iframe")[0].contentWindow.closeLayer();
        $("#student-iframe")[0].contentWindow.closeLayer();
        $("#parent-iframe")[0].contentWindow.closeLayer();
    })
    window.onload = function () {
        $('.content').show();
        $('.campus-tab ul li.teacher').trigger('click');
    }
    //    判断是不是区管理员，不是区管理员屏蔽下载模板 和 导入机构功能
    var isAdmin = JSON.parse(sessionStorage.getItem('isAdmin'));
    var isSuperAdmin = JSON.parse(sessionStorage.getItem('isSuperAdmin'));
    if (isSuperAdmin) {
        $('.left-top .top-dr').show();
        $('.left-top .top-xz').show();
    } else {
        $('.left-top .top-dr').hide();
        $('.left-top .top-xz').hide();
    }

</script>
</body>

</html>