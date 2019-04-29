define('device/mulec', function(require, exports, module) {

    var $ = require('jquery');
    var SelectList = require('select-list/select');
    require('cxMenu/jquery.cxmenu');
    var cxmenu=require('cxMenu/cxmenu');
    module.exports = {
        init:function(){
            this.url = location.href.substring(0, location.href.lastIndexOf("/"));
            this.initCampus();
            this.initAllotDevice();
            this.initUserChoose();
            this.initUserSearchBtn();
            this.initDeleteOneDevice();
            this.initDeleteDevice();
            this.initDeviceSearchBtn();
            this.initEnter();
            cxmenu.initSearchAllDevice();
        },
        //生成左侧机构数和身份列表
        initCampus:function(){
            var that = this;
            $.post(that.url + "/Device/searchCumpus.do", function (data) {
                //组织结构树
                $("#campus-tree").html(data.cumpus);
                //身份下拉框
                var userTypeList = "<option value=\"-1\">全部类型</option>";
                $.each(data.userTypeList,function(i,value){
                    userTypeList+="<option value=\""+value.typeId+"\">"+value.typeName+"</option>";
                })
                $("#lxing").html(userTypeList)
                //系统下拉框
                var sysList = "<option value=\"\">全部设备</option>";
                $.each(data.sysList,function(i,value){
                    sysList+="<option value=\""+value.dtype_name+"\">"+value.dtype_name_cn+"</option>";
                })
                $("#systlt").html(sysList);
                that.initTree();
                that.initSelect();
            }, 'json');
        },
        //根据身份及机构查询对应人员
        searchUser:function(userType,campusId,searchWord){
            var that = this;
            $.post(that.url + "/Device/searchUsers.do",{
                campusId:campusId,
                userType:userType,
                searchWord:searchWord
            }, function (data) {
                var userList = "";
                $.each(data.userList,function(i,value){
                    userList +="<a title=\""+value.userRealName+"("+value.typeName+")"+"\" class=\"user_a\" userId=\""+value.userId+"\" href=\"javascript:void(0)\">"+value.userRealName+"("+value.typeName+")</a>";
                })
                $("#userList").html(userList);
                //清空右侧设备列表
                $("#deviceList").html("");
            }, 'json');
        },
        //查询用户所分配的设备列表
        //参数：用户ID 设备类型 关键字
        searchDevice:function(userId,deviceType,searchWord){
            var that = this;
            $.post(that.url + "/Device/searchDevice.do",{
                userId:userId,
                deviceType:deviceType,
                searchWord:searchWord
            }, function (data) {
                var deviceList = "";
                $.each(data.deviceList,function(i,value){
                    deviceList+="<div class=\"catalog-list-main\">"+
                    "<div class=\"u-check\">"+
                    "<i class=\"m-check\" user2deviceId=\""+value.user2deviceId+"\"></i></div>"+
                    "<div class=\"catalog-name\">"+value.hostName+"</div>"+
                    "<div class=\"catalog-lei\">"+value.typeName+"</div>"+
                    "<div class=\"catalog-ip\">"+value.hostIP+"</div>"+
                    "<div class=\"catalog-dev\">"+value.areaName+"</div>"+
                    "<div class=\"catalog-cz\">"+
                    "<a  user2deviceId=\""+value.user2deviceId+"\" href=\"javascript:void(0)\" class=\"a-btn a-btn-delete device-delete\"></a></div></div>";
                })
                $("#deviceList").html(deviceList);
                that.initCheckBox();
                that.initDeleteOneDevice();
            }, 'json');
        },
        //删除用户设备关系
        deleteUser2Device:function(id){
            var that = this;
            $.post(that.url + "/Device/deleteUser2Device.do",{
                id:id
            }, function (data) {
                if(data.msg=="true"){
                    var userId= $("#userList").find(".back-mi").attr("userid");
                    var searchWord =$("#deviceSearchWord").val();
                    var deviceType=$("#systlt").children("input[name='systlt']").val();
                    layer.msg('删除成功');
                    that.searchDevice(userId,deviceType,searchWord);
                }
            }, 'json');
        },
        //分配设备
        initAllotDevice:function(){
            var that = this;
            $(".btn-allot").on("click", function () {
                var userId= $("#userList").find(".back-mi").attr("userid");
                if(userId==null){
                    layer.msg("请选择用户");
                    return;
                }
                var content = $("#win-allot");
                $("#win-input-i").val("");//清空查询框内容
                $("#deviceTree-id").val("");//清空临时ID
                $("#deviceTree-name").val("");//清空临时名称
                layer.open({
                    type: 1,
                    title: '分配设备',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['1000px', '600px'],
                    content: content,
                    btn: ["确定", "取消"],
                    success: function(layero, index){
                        //刷新界面固定数据 设备类型 设备树 用户设备数据
                        $.post(that.url + "/Device/searchDeviceTree.do",{
                            userId:userId
                        }, function (data) {
                            $("#userId").val(data.userId);
                            //左侧设备树
                            var treeHtml = '<div class="org-menu" id="device-tree">';
                            treeHtml += data.deviceTree;
                            treeHtml += "</div>";
                            $(".left-device-tree").html(treeHtml);
                            //左侧设备类型列表
                            var sysList = "<option value=\"\">全部类型</option>";
                            $.each(data.sysList,function(i,value){
                                sysList+="<option value=\""+value.dtype_name+"\">"+value.dtype_name_cn+"</option>";
                            })
                            $("#win-sys").html(sysList);
                            //初始化右侧设备列表
                            cxmenu.init();
                        }, 'json');
                    },
                    no: function (index, layero) {
                        layer.close(index);
                    },
                    yes: function (index, layero) {
                        var devices = $("#win-deviceList").find(".win-block-mains-li");
                        var deviceIds ="";
                        $.each(devices,function(i,value){
                            deviceIds+=devices.eq(i).attr("id")+",";
                        })
                        $.post(that.url + "/Device/allotDevice.do",{
                            userId:userId,
                            deviceId:deviceIds
                        }, function (data) {
                            if(data.msg=="true"){
                                var searchWord =$("#deviceSearchWord").val();
                                var deviceType=$("#systlt").children("input[name='systlt']").val();
                                that.searchDevice(userId,deviceType,searchWord);
                                layer.msg("分配成功");
                                $(".m-check-all").removeClass("m-checked");
                                layer.close(index);
                            }
                        }, 'json');
                    }
                });
            });
        },
        //初始化删除设备
        initDeleteOneDevice:function(){
            var that = this;
            $(".a-btn-delete").on("click", function () {
                var user2deviceId = $(this).attr("user2deviceId");
                layer.confirm("确定要删除设备吗？",function(){
                    that.deleteUser2Device(user2deviceId);
                    $(".m-check-all").removeClass("m-checked");
                    layer.closeAll('dialog');
                })
                return false
            });
        },

        //初始化批量删除
        initDeleteDevice:function(){
            var that = this;
            $(".btn-delete").on("click", function () {
                var checked = $("#deviceList").find(".m-checked");
                if(checked.length<1){
                    layer.msg("请选择需要删除的设备")
                    return;
                }
                layer.confirm("确定要删除所选设备吗？",function(){
                    var ids = "";
                    $.each(checked,function(i,value){
                        ids+=$(this).attr("user2deviceid")+",";
                    })
                    that.deleteUser2Device(ids);
                    $(".m-check-all").removeClass("m-checked");
                    layer.closeAll('dialog');
                })
            })
        },
        //初始化下拉框
        initSelect:function(){
            var that = this;
            //初始化系统下拉框 右侧下拉框
            var select = new SelectList('#systlt', function (option) {
                var userId= $("#userList").find(".back-mi").attr("userid");
                if(userId==null){
                    return;
                }
                var searchWord =$("#deviceSearchWord").val();
                var deviceType=$("#systlt").children("input[name='systlt']").val();
                that.searchDevice(userId,deviceType,searchWord);
            });
            select.init();
            //初始化身份下拉框 左侧下拉框
            var select = new SelectList('#lxing', function (option) {
                var userType = option.attr('data-value');
                var campusId = $("#tree-id").val()==""?1:$("#tree-id").val();
                var searchWord = $("#userSearchWord").val();
                that.searchUser(userType,campusId,searchWord);
            });
            select.init();
        },
        //初始化机构树点击事件
        initTree:function(){
            var that = this;
            $('#left_nav_ul').cxMenu();
            //将左侧选中的分类，显示到右侧顶部
            $('#campus-tree #left_nav_ul li a').on('click', function () {
                var campusId = $(this).attr("id").trim();
                var userTypeId=$("#lxing").children("input[name='lxing']").val();
                var searchWord = $("#userSearchWord").val();
                $("#tree-id").val(campusId)
                that.searchUser(userTypeId,campusId,searchWord)
            });
        },
        //初始化点击用户事件
        initUserChoose:function(){
            var that = this;
            $(document).on("click",".main-catalog-info-p a",function(){
                $(".main-catalog-info-p a").removeClass("back-mi");
                $(this).addClass("back-mi");
                var userId= $(this).attr("userId");
                var searchWord =$("#deviceSearchWord").val();
                var deviceType=$("#systlt").children("input[name='systlt']").val();
                that.searchDevice(userId,deviceType,searchWord);
            })
        },
        //初始化用户搜索按钮
        initUserSearchBtn:function(){
            var that = this;
            $(".search-campus").on("click",function(){
                var campusId = $("#tree-id").val()==""?1:$("#tree-id").val();
                var userTypeId=$("#lxing").children("input[name='lxing']").val();
                var searchWord = $("#userSearchWord").val();
                that.searchUser(userTypeId,campusId,searchWord)
            })
        },
        //初始化设备搜索按钮
        initDeviceSearchBtn:function(){
            var that = this;
            $(".search-device").on("click",function(){

                var userId= $("#userList").find(".back-mi").attr("userid");
                if(userId==null){
                    return;
                }
                var searchWord =$("#deviceSearchWord").val();
                var deviceType=$("#systlt").children("input[name='systlt']").val();
                that.searchDevice(userId,deviceType,searchWord);
            })
        },
        //初始化多选框
        initCheckBox:function(){
            //var checks = $('.m-check');
            ////单选
            //$(document).on('click','.m-check', function () {
            //    $(this).hasClass('m-checked') ? $(this).removeClass('m-checked') : $(this).addClass('m-checked');
            //});
            //如果该项下面有子类，则该项的宽度设置为100%；
            $('.catalog-list-title ul li').each(function () {
                var childUl = $(this).children('ul');
                if (childUl && childUl.length > 0) {
                    $(this).addClass('single-line');
                }
            });

            //如果该项下面有子类，则添加全选功能
            $(document).on('click','.catalog-list-main-z ul li .m-check', function () {
                var li = $(this).closest('li');
                var childUl = li.children('ul');
                if (childUl && childUl.length > 0) {
                    var allChildCheck = li.find('.m-check');
                    $(this).hasClass('m-checked') ? allChildCheck.addClass('m-checked') : allChildCheck.removeClass('m-checked');
                }
            });
        },
        //绑定回车事件
        initEnter: function (event) {
            var that = this;
            //初始化用户条件回车搜索
            $('#userSearchWord').bind('keypress', function(event){
                if(event.keyCode == '13'){
                    //点回车键进行搜索
                    var campusId = $("#tree-id").val()==""?1:$("#tree-id").val();
                    var userTypeId=$("#lxing").children("input[name='lxing']").val();
                    var searchWord = $("#userSearchWord").val();
                    that.searchUser(userTypeId,campusId,searchWord)
                }
            });
            //初始化设备条件回车搜索
            $('#deviceSearchWord').bind('keypress', function(event){
                if(event.keyCode == '13'){
                    //点回车键进行搜索
                    var userId= $("#userList").find(".back-mi").attr("userid");
                    if(userId==null){
                        return;
                    }
                    var searchWord =$("#deviceSearchWord").val();
                    var deviceType=$("#systlt").children("input[name='systlt']").val();
                    that.searchDevice(userId,deviceType,searchWord);
                }
            });
            //初始化弹框设备条件回车搜索
            $('#win-input-i').bind('keypress', function(event){
                if(event.keyCode == '13'){
                    //点回车键进行搜索
                    var areaId = $("#deviceTree-id").val();//获取地点ID
                    var searchWord = $("#win-input-i").val();//获取关键字
                    var deviceType = $("#win-sys").find("input[name='win-sys']").val();//获取所选设备类型
                    var devices = $("#win-deviceList").find(".win-block-mains-li");
                    var deviceId ="";
                    $.each(devices,function(i,value){
                        deviceId+=devices.eq(i).attr("id")+",";
                    })
                    cxmenu.selectDeviceArray = [];
                    cxmenu.searchAreaDevice(areaId, deviceType, searchWord,deviceId);
                }
            });
        }
}
});
