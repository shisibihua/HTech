define('device/block', function(require, exports, module) {
    var Pagination = require('pagination/pagination');
    var $ = require('jquery');
    var SelectList = require('select-list/select');
    var page = "1"; //默认初始页面
    var pageSize = 16;//默认分页条数
    var allopage = 10000;//默认分配设备分页数
    var areaIds = "-1";//所有地点
    var url = location.href.substring(0, location.href.lastIndexOf("/"));
    var userId = location.href.substring(location.href.lastIndexOf("userId=")+7,location.href.lastIndexOf("&userName"));
    $(function () {

        init();//初始化左侧树及左侧树功能
        initfunction();//初始化右侧功能按钮
        //初始化左侧树和右侧设备
        function init(id) {
            $.post(url+"/Group/searchCampus.do", {'userId': userId}, function (data) {//""为所有设备类型
                $('.org-menu').html(data);//显示根节点
                initMenu();//初始化左侧树
                //添加地点后定位到当前地点
                if(id!='' && id!=undefined){

                    var a = $('#' + id);
                    selected(a);
                    // 延迟操作，等待提示弹窗结束
                    setTimeout(function () {
                        // 循环，依次展开添加过标识的a标签
                        $('#left_nav_ul').find('.addSelect').each(function(){
                            if(!$(this).parent().hasClass('selected')){
                                $(this).trigger("click");//让系统自动执行单击事件
                            }
                        });
                        // 定位到新添加的标签
                        a.trigger("click");
                    }, 100);
                }
                //initAdd();//初始化左侧树添加
                //initEdit();//初始化左侧树修改
                //initareaType();//初始化获取所有地点类型
                //initCampus();//初始化获取所有校区
                //initRoomType();//获取所有教室类型
                //initDelete();//初始化左侧树删除
                initright();//初始化右侧设备(仅为根目录下的设备)
                initWinback();//左侧地点树信息悬浮
                $(".user-list").show();//显示图标
                $(".devive-list-ls").hide();//隐藏图标
                $(".sprt-tab").css("opacity","1");//选中图标变深
                $(".sprt-list").css("opacity",".5");//未选中图标变浅
                $(".select-sf> span >span").click(function(){
                    $(".select-sf> span >span").removeClass("color-gree").find("i").remove();
                    $(this).addClass("color-gree").append("<i></i>")
                });
            }, 'html');
        }


        // 递归调用，为每个需要展开的a标签添加class标识
        // 由于展开需要从上层到底层，此方法只能从底层上上层查找，添加
        // 所以本方法只添加标识
        function selected(a){
            a.addClass('addSelect');
            if(a.parent().parent().attr('id') == "left_nav_ul")
            {
                return;
            }
            else
            {
                return selected(a.parent().parent().prev('a'));
            }
        }

        //初始化右侧功能按钮
        function initfunction(){
            switchmode(); //初始化切换列表显示和图标显示
            initdeviceselect();//初始化搜索
            deleteDevices();//初始化批量删除设备
            allocateDevice();//初始化分配设备
        }

        //左侧地点树信息悬浮
        function initWinback(){
            $("#left_nav_ul").find("a").mouseenter(function(e){
                var areaId = $(this).attr('id');

                $.post(url+"/Group/getAreaDetail.do", {
                    'areaId': areaId
                }, function (data) {
                    if (data.code == 0) {
                        var number = data.value[0].number;//容纳人数
                        var typename = data.value[0].typename;//类型名称
                        if(number==0){
                            number="未填写"
                        }
                        var str = "<div class='winback_t'>" +
                            "<div class='winback_t_sj'>" +
                            "<span>类型：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+typename+"</span>" +
                                //"<span>容纳人数："+number+"</span>" +
                            "</div>"+
                            "</div>"
                        $("body").append(str);
                        var pointX = e.pageX;
                        var pointY = e.pageY;
                        $(".winback_t").css({"top":pointY+30,"left":pointX})
                    }
                }, 'json');
            }).mouseleave(function(){
                $(".winback_t").remove();
            })
        }
        //添加地点
        function initAdd(){
            $('.btn-add-org').on('click', function () {

                //初始化选择按钮
                $(".select-sf > span > span").removeClass("color-gree").find("i").remove();
                $(".select-sf").find('#1').addClass("color-gree").append("<i></i>")
                $('#room').hide();
                var orgName = $(this).closest('a').children('h6').text(); //获取父地点名称
                var campusId = $(this).closest('a').attr("id");//获取父地点id
                $('.select-org').show();//显示当前地点名称
                $('.current-organization').text(orgName);//显示父地点名称
                $('.organization-name').val('');//要添加的地点名称为空
                $('#totalstu').val('');//容纳人数显示为空
                $('#remark').val('');//备注显示为空
                $('#winxiaoqu').find("input").val('');
                $('#winleix').find("input").val('');
                $('#winroom').find("input").val('');
                $('#winleix .select-button').val('全部类型')//类型
                $('#winxiaoqu .select-button').val('全部类型')//校区类型
                $('#winroom .select-button').val('全部类型')//教室类型

                layer.open({
                    type: 1,
                    title: '添加地点',
                    shadeClose: true,
                    shade: 0.8,
                    //area:["430","550px"],
                    content:$('.add-org'),
                    btn:["确定", "取消"],
                    end:function (index, layero) {
                    },
                    yes:function (index, layero) {
                        var areaType = $('#winleix').find("input").val().trim();//类型
                        if(areaType==6){
                            areaType = $('#room').find("input").val().trim();//类型
                        }
                        var name = $('.add-org').find('.organization-name').val().trim(); //地点名称
                        var campus = $('#winxiaoqu').find("input").val().trim();//校区类型
                        var number =  $('#totalstu').val().trim();//容纳人数
                        var isSelect = $('.add-org-u').find('.color-gree').attr("value");//是否选课
                        var remark = $('#remark').val().trim();//备注
                        if (number == "") {
                            number=0;
                        }
                        if (name == "") {
                            layer.msg("地点名称不能为空");
                            return false;
                        }
                        if (name.length > 45) {
                            layer.msg("地点名称请小于45个字符");
                            return false;
                        }
                        if (areaType == "") {
                            layer.msg("请选择地点类型");
                            return false;
                        }
                        //if (campus == "") {
                        //    layer.msg("请选择校区");
                        //    return false;
                        //}

                        if(!isSelect) {
                            layer.msg("请选择是否选课");
                            return false;
                        }
                        $.post(url+"/Group/addCampus.do", {
                            'pId': campusId,
                            'name': name,
                            'areaType':areaType,
                            'campus':campus,
                            'number':number,
                            'isSelect':isSelect,
                            'remark':remark
                        }, function (data) {
                            layer.close(index);//执行完成先隐藏弹窗
                            if (data.code == 0) {

                                if (data.value == -2) {
                                    layer.msg("该名称已存在");

                                }
                                else if (data.value != -1) {
                                    layer.msg("添加地点成功");
                                    init(data.value);//刷新左侧树
                                } else {
                                    layer.msg("添加地点失败");
                                }
                            }
                            else {
                                layer.msg("添加地点失败");
                            }
                        }, 'json');
                    }
                });
                return false;//点击此按钮时不触发上一级事件
            });
        }

        //修改地点名称
        function initEdit(){
            $('.btn-edit-org').on('click', function () {
                $(".select-sf> span >span").removeClass("color-gree").find("i").remove();
                var areaId = $(this).closest('a').attr("id");//获取当前地点id
                $.post(url+"/Group/getAreaDetail.do", {
                    'areaId': areaId
                }, function (data) {
                    if (data.code == 0) {
                        var areaType = data.value[0].type_id;//类型
                        var name = data.value[0].areaname; //地点名称
                        var campus = data.value[0].campus;//校区类型
                        var number = data.value[0].number;//容纳人数
                        var isSelect = data.value[0].isselect;//是否选课
                        var remark = data.value[0].remark;//备注
                        var typename = data.value[0].typename;//备注
                        var campusname = data.value[0].campusname;//备注
                        if(campusname=="")
                        {campusname = "全部类型" }
                        if(number==0)
                        {number = "" }
                        $('#room').hide();
                        $('#winxiaoqu').find("input").val(campus);
                        $('#winleix').find("input").val(areaType);
                        $('#winleix .select-button').val(typename)//类型
                        $('#winxiaoqu .select-button').val(campusname)//校区类型
                        if(areaType==6||areaType>7)
                        {
                            $('#room').show();
                            $('#winleix').find("input").val(6);
                            $('#winleix .select-button').val("教室")//类型
                            $('#room').find("input").val(areaType);
                            $('#room .select-button').val(typename)//类型
                        }
                        $('.organization-name').val(name);//地点名称显示当前地点名称
                        $('#totalstu').val(number);//容纳人数显示
                        $('#remark').val(remark);//备注显示
                        //初始化选择按钮
                        $(".select-sf> span >span").removeClass("color-gree").find("i").remove();
                        $(".select-sf").find('#'+isSelect).addClass("color-gree").append("<i></i>")
                    }
                }, 'json');

                var pId = $(this).parents("ul").prev("a").attr("id");
                if(!pId){
                    pId = 0;
                }
                var h6 = $(this).closest('a').children('h6');//修改后的名称
                $('.select-org').hide();//隐藏当前地点名称

                layer.open({
                    type: 1,
                    title: '修改地点',
                    shadeClose: true,
                    shade: 0.8,
                    //area:["380px","520px"],
                    content:$('.add-org'),
                    btn:["确定", "取消"],
                    end:function (index, layero) {
                    },
                    yes:function (index, layero) {

                        var areaType = $('#winleix').find("input").val().trim();//类型
                        if(areaType==6){
                            areaType = $('#room').find("input").val().trim();//类型
                        }
                        var name = $('.add-org').find('.organization-name').val().trim(); //地点名称
                        var campus = $('#winxiaoqu').find("input").val().trim();//校区类型
                        var number =  $('#totalstu').val().trim();//容纳人数
                        var isSelect = $('.add-org-u').find('.color-gree').attr("value");//是否选课
                        var remark = $('#remark').val().trim();//备注
                        if (number == "") {
                            number=0;
                        }
                        if (name == "") {
                            layer.msg("地点名称不能为空");
                            return false;
                        }
                        if (name.length > 45) {
                            layer.msg("地点名称请小于45个字符");
                            return false;
                        }
                        if (areaType == "") {
                            layer.msg("请选择地点类型");
                            return false;
                        }
                        if(!isSelect) {
                            layer.msg("请选择是否选课");
                            return false;
                        }
                        $.post(url+"/Group/updateCampus.do", {
                            'id': areaId,
                            'name': name,
                            'pId':pId,
                            'areaType':areaType,
                            'campus':campus,
                            'number':number,
                            'isSelect':isSelect,
                            'remark':remark
                        }, function (data) {
                            layer.close(index);//执行完成先隐藏弹窗
                            if (data.code == 0) {
                                if (data.value == -2) {
                                    layer.msg("该地点已存在");

                                }
                                else if (data.value) {
                                    layer.msg("修改地点成功");
                                    h6.text(name);//修改地点名称并刷新
                                    setTimeout(function () {
                                        var lis = $('#left_nav_ul').find('li.selected');
                                        var title = '';
                                        for (var i = 0; i < lis.length; i++) {
                                            title += lis.eq(i).children('a').children('h6').text() + ' > ';
                                        }
                                        if(title.length > 60){
                                            titz = title.substring(0,35);
                                            tittz = title.substring(title.length-20,title.length);
                                            $('.right-top > p').text(titz+"......"+tittz + '设备');
                                            $('.right-top').attr("title",title + '设备')

                                        }
                                        else {
                                            $('.right-top').attr("title", title + '设备')
                                            $('.right-top > p').text(title + '设备');
                                        }
                                    }, 100);
                                }
                                else {
                                    layer.msg("修改地点成功");
                                }
                            }
                            else {
                                layer.msg("修改地点成功");
                            }
                        }, 'json');
                    }
                });
                return false;//点击此按钮时不触发上一级事件
            });
        }

        //删除地点
        function initDelete(){
            $('.org-menu .btn-delete-org').on('click', function () {
                var current = $(this); //将current固定为.org-menu .btn-delete-org
                layer.open({
                    type: 1,
                    title: '删除地点',
                    shadeClose: true,
                    shade: 0.8,
                    content:$('.m-delete'),
                    btn:["确定", "取消"],
                    end:function (index, layero) {
                    },
                    yes:function (index, layero) {
                        var ids = "";
                        current.closest("li").find("a").each(function () {
                            ids = ids + $(this).attr("id") + ",";//循环获取当前地点id
                        });
                        ids = ids.substring(0, ids.length - 1);
                        $.post(url+"/Group/deleteCampus.do", {
                            'id': ids
                        }, function (data) {
                            layer.close(index);//执行完成先隐藏弹窗
                            if (data.code == 0) {
                                if (data.value) {
                                    layer.msg("删除地点成功");
                                    init();//刷新左侧树
                                } else {
                                    layer.msg("删除地点失败");
                                }
                            }
                            else {
                                layer.msg("删除地点失败");
                            }
                        }, 'json');
                    }
                });

                return false;//点击此按钮时不触发上一级事件
            });
        }

        //初始化点击左侧树
        function initMenu() {
            require('cxMenu/jquery.cxmenu');
            $('#left_nav_ul').cxMenu();//为左侧树添加样式
            //将左侧选中的分类，显示到右侧顶部
            $('#left_nav_ul li a').on('click', function () {

                setTimeout(function () {
                    var lis = $('#left_nav_ul').find('li.selected');
                    var title = '';
                    for (var i = 0; i < lis.length; i++) {
                        title += lis.eq(i).children('a').children('h6').text() + ' > ';
                    }
                    if(title.length > 68){
                        titz = title.substring(0,35);
                        tittz = title.substring(title.length-20,title.length);
                        $('.right-top > p').text(titz+"......"+tittz + '设备');
                        $('.right-top').attr("title",title + '设备')

                    }else{
                        $('.right-top > p').text(title + '设备');
                        $('.right-top').attr("title",title + '设备')
                    }

                }, 100);
                var areaId = $(this).closest('a').attr('id');
                var areaName = $(this).closest('a').text().trim();
                //将areaId和areaName传给右侧设备
                initTree(areaId, areaName);
            });
        }

        //打开页面初始化右侧设备(根节点下设备)
        function initright(){
            $.post(url+"/Group/initDevice.do", {'type': 0}, function (data) {
                var areaId = data.value["id"]
                var areaName = data.value["name"]
                initTree(areaId, areaName)
                $('.right-top > p').text(areaName +' > '+ ' 设备');
                $('.right-top').attr("title",areaName +' > '+ ' 设备')
                if(!areaName)
                {
                    $('.right-top > p').text("");
                }
            }, 'json');
        }

        //初始化右侧图标或列表
        function initTree(areaId, areaName) {

            this.areaId = areaId;
            this.areaName = areaName;
            $('.m-search input').val(""); //将搜索框置空
            getDevice();
        };

        //查找设备(搜索或点击左侧地点树)
        function getDevice() {
            var html = ""; //图标显示
            var lhtml = ""; //列表显示
            this.conditions = $('.m-search input').val().trim();//搜索词
            this.deviceType = $("#systlt").find("input[name='systlt']").val();//设备类型
            if(this.deviceType == null)
            {
                this.deviceType = "";
            }
            $.post(url+"/Group/searchDevice.do", {
                'page': page,
                'pageSize': pageSize,
                'areaId': this.areaId,
                'deviceType': this.deviceType,
                'conditions': this.conditions
            }, function (data) {

                var totalCount = data.itemsCount;
                //图标显示
                html += totalCount + "@_@";//标记总页数
                $.each(data.hostInfo, function (ind, value) {
                    //循环获取设备信息并显示图标(拼接html)
                    var id = value["id"];
                    var name = value["name"];
                    var typeName = value["typeName"];
                    html += "<li>" +
                        "<div class='user-name'>" +
                        "<span><img src='" + "./images/userimg.png" + "' class='headimg'><img src='images/usersimg.png' " + "class='head-select'></span>"
                        + "<span class='uname' title='" + name + "' >" + name + "<br/>" + typeName + "</span>" + "</div>"
                        + "<div class='user-info'>"
                        + "<a href='javascript:void(0)' id='" + id + "' class='a-btn a-btn-details btn-member-details' title='详情'></a>"
                        + "<a href='javascript:void(0)' id='" + id + "' class='a-btn a-btn-delete btn-delete' title='删除'></a>" + "</div>"
                        + "</li>"
                })
                if (totalCount == 0) {//没有设备
                    $(".user-list ul").html("<div class='note-m'>此地点没有任何设备</div>");
                    $(".devive-list-ls").html("<div class='note-m' style='margin-left:10px '>此地点没有任何设备</div>");
                    $('.m-page').html('');//清空分页
                    return false;
                }
                else {
                    var n = html.indexOf("@_@");
                    html = html.substring(n + 3, html.length);//把总页数和标记符(@_@)截去
                    $(".user-list ul").html(html);

                    //列表显示
                    lhtml += totalCount + "@_@";
                    lhtml += "<div class='devive-list-title'>"
                        + " <div class='u-check'>"
                        + "<i class='m-check-all'></i>"
                        + "</div>"
                        + "<div class='devive-name'>名称</div>"
                        + "<div class='devive-lei'>类型</div>"
                        + "<div class='devive-ip'>IP</div>"
                        + "<div class='devive-dev'>所属地点</div>"
                        + "<div class='devive-cz'>操作</div>"
                        + "</div>"

                    $.each(data.hostInfo, function (ind, value) {
                        //循环获取设备信息并显示列表(拼接html)
                        var ip = value["ip"];
                        var id = value["id"];
                        var name = value["name"];
                        var typeName = value["typeName"];
                        lhtml += "<div class='devive-list-main'>"
                            + "<div class='u-check'>"
                            + "<i  id='" + id + "'  class='m-check'></i>"
                            + "</div>"
                            + "<div class='devive-name' title='" + name + "'>" + name + " </div>"
                            + "<div class='devive-lei'  title='" + typeName + "'>" + typeName + "</div>"
                            + "<div class='devive-ip'   title='" + ip + "'>" + ip + "</div>"
                            + "<div class='devive-dev'  title='" + areaName + "'>" + areaName + "</div>"
                            + "<div class='devive-cz'>"
                            + "<a href='javascript:void(0)' id='" + id + "' class='a-btn a-btn-delete devie-delete' title='删除'></a>"
                            + "</div>"
                            + "</div>"
                    })
                    var n = lhtml.indexOf("@_@");
                    var total = lhtml.substring(0, n);
                    lhtml = lhtml.substring(n + 3, lhtml.length);//把总页数和标记符(@_@)截去
                    $(".devive-list-ls").html(lhtml);
                    initDeleteDeviceList();//初始化列表删除设备
                    initDeleteDevice();//初始化图标删除设备
                    initDeviceSelected();//初始化选中图标
                    showDeviceDetails();//初始化显示详情
                }
                initPage(total);//分页
            }, 'json');
        };

        //分页查找设备(搜索或点击左侧地点树)
        function getDeviceFromPage(index) {
            var html = ""; //分页图标显示
            var lhtml = ""; //分页列表显示
            index = index + 1;
            this.conditions = $('.m-search input').val().trim();
            this.deviceType = $("#systlt").find("input[name='systlt']").val();
            if(this.deviceType = null)
            {
                this.deviceType = "";
            }
            $.post(url+"/Group/searchDevice.do", {
                'page': index,
                'pageSize': pageSize,
                'areaId': this.areaId,
                'deviceType': this.deviceType,
                'conditions': this.conditions
            }, function (data) {
                var totalCount = data.itemsCount;
                html += totalCount + "@_@";
                $.each(data.hostInfo, function (ind, value) {
                    //循环获取设备信息并显示图标(拼接html)
                    var id = value["id"];
                    var name = value["name"];
                    var typeName = value["typeName"];
                    html += "<li>" +
                        "<div class='user-name'>" +
                        "<span><img src='" + "./images/userimg.png" + "' class='headimg'><img src='images/usersimg.png' " + "class='head-select'></span>"
                        + "<span class='uname' title='" + name + "'>" + name + "<br/>" + typeName + "</span>" + "</div>"
                        + "<div class='user-info'>" +
                        "<a href='javascript:void(0)' id='" + id + "' class='a-btn a-btn-details btn-member-details' title='详情'></a>" +
                        "<a href='javascript:void(0)' id='" + id + "' class='a-btn a-btn-delete btn-delete' title='删除'></a>" + "</div>"
                        + "</li>"
                })
                var n = html.indexOf("@_@");
                html = html.substring(n + 3, html.length);//把总页数和标记符(@_@)截去
                $(".user-list ul").html(html); //将html拼接后传给前台
                //列表显示
                lhtml += totalCount + "@_@";
                lhtml += "<div class='devive-list-title'>"
                    + " <div class='u-check'>"
                    + "<i class='m-check-all'></i>"
                    + "</div>"
                    + "<div class='devive-name'>名称</div>"
                    + "<div class='devive-lei'>类型</div>"
                    + "<div class='devive-ip'>IP</div>"
                    + "<div class='devive-dev'>所属地点</div>"
                    + "<div class='devive-cz'>操作</div>"
                    + "</div>"
                $.each(data.hostInfo, function (ind, value) {
                    //循环获取设备信息并显示列表(拼接html)
                    var ip = value["ip"];
                    var id = value["id"];
                    var name = value["name"];
                    var typeName = value["typeName"];
                    lhtml += "<div class='devive-list-main'>"
                        + "<div class='u-check'>"
                        + "<i  id='" + id + "'  class='m-check'></i>"
                        + "</div>"
                        + "<div class='devive-name'  title='" + name + "'>" + name + " </div>"
                        + "<div class='devive-lei' title='" + typeName + "'>" + typeName + "</div>"
                        + "<div class='devive-ip' title='" + ip + "'>" + ip + "</div>"
                        + "<div class='devive-dev' title='" + areaName + "'>" + areaName+ "</div>"
                        + "<div class='devive-cz'>"
                        + "<a href='javascript:void(0)' id='" + id + "' class='a-btn a-btn-delete devie-delete' title='删除'></a>"
                        + "</div>"
                        + "</div>"
                })
                var n = lhtml.indexOf("@_@");
                lhtml = lhtml.substring(n + 3, lhtml.length);//把总页数和标记符(@_@)截去
                $(".devive-list-ls").html(lhtml);//将html拼接后传给前台
                initDeleteDeviceList();//初始化列表删除设备
                initDeleteDevice();//初始化图标删除设备
                initDeviceSelected();//初始化选中图标
                showDeviceDetails();//初始化显示详情
            }, 'json');
        };

        //设备显示进行分页
        function initPage(total) {
            $('.m-page').html('');//调用时先将页码置空
            if (total > pageSize) {
                var page = new Pagination(parseInt(total), function (pageIndex) {
                    getDeviceFromPage(pageIndex);//调用分页方法
                });
                page.init('.main-right .m-page', pageSize);
            }
        };

        //数据库中删除设备和地点关联关系
        function deleteDevice(deviceId) {
            $.post(url+"/Group/deleteDevices.do", {
                'areaId': areaId,
                'deviceId': deviceId
            }, function (data) {
                if (data.code == 0) {
                    if (data.value) {
                        layer.msg("删除成功");
                        getDevice();//刷新右侧设备
                        return true;
                    }
                }
                layer.msg("删除失败");
                return false;//点击此按钮时不触发上一级事件
            }, 'json');
        };

        //初始化图标删除设备
        function initDeleteDevice(){
            $('.btn-delete').on('click', function () {
                var that = $(this);
                layer.open({
                    type: 1,
                    title: '删除设备',
                    shadeClose: true,
                    shade: 0.8,
                    content:$('.m-delete-org-user'),
                    btn:["确定", "取消"],
                    end:function (index, layero) {
                    },
                    yes:function (index, layero) {
                        var deviceId = that.attr("id");//获取设备id
                        layer.close(index);//执行完成先隐藏弹窗
                        deleteDevice(deviceId);//删除设备
                    }
                });
                return false;
            });
        }

        //初始化列表删除设备
        function initDeleteDeviceList(){
            $('.devie-delete').on('click', function () {
                var that = $(this);
                layer.open({
                    type: 1,
                    title: '删除设备',
                    shadeClose: true,
                    shade: 0.8,
                    content:$('.m-delete-org-user'),
                    btn:["确定", "取消"],
                    end:function (index, layero) {
                    },
                    yes:function (index, layero) {
                        var deviceId = that.attr("id");
                        layer.close(index);//执行完成先隐藏弹窗
                        deleteDevice(deviceId);//删除设备
                    }
                });
                return false;
            });
        }

        //点击图标选中设备
        function initDeviceSelected() {
            $('.user-list li ').on('click', function () {
                var headImgSelect = $(this).find(".headimg").siblings('.head-select');
                if ($(this).find(".headimg").hasClass('selected')) {//点击后如果选中则取消选中
                    $(this).find(".headimg").removeClass('selected');
                    headImgSelect.hide();//隐藏选中样式
                    $(this).find(".headimg").parents('li').removeClass('member-selected');
                } else {//点击后如果没选中则选中
                    $(this).find(".headimg").addClass('selected');
                    headImgSelect.show();//显示选中样式
                    $(this).find(".headimg").parents('li').addClass('member-selected');
                }
            })
        }

        //显示设备详情
        function showDeviceDetails() {
            $('.btn-member-details').on('click', function () {
                var hostId = $(this).attr('id');//获取设备id
                $.post(url+"/Group/loadDevices.do", {
                    'hostId': hostId
                }, function (data) {
                    var html = "";
                    $.each(data, function (key, value) {
                        //拼接html
                        var dtype_name = value['dtype_name'];
                        var host_ipaddr = value['host_ipaddr'];
                        var host_name = value['host_name'];
                        html+= "<div class='details-name'>"
                            + "<img src=./images/userimg.png>"
                            + " <div class='name-details-name'>"
                            + " <span class='name-first' title='" + host_name + "'>" + host_name + "</span>"
                            + "</div>"
                            + "</div>"
                            + "<div class='details-content'>"
                            + "<div class='details-content-title'>详细信息</div>"
                            + "<div class='details-left'>"
                            + "<div class='tel' title='" + dtype_name + "'>设备类型：" + dtype_name + "</div>"
                            + "<div class='birthday' title='" + host_ipaddr + "'>ip地址：" + host_ipaddr + "</div>"
                            + "<div class='address' title='" + areaName + "'>地点名称：" + areaName + "</div>"
                            + "</div>"
                            + "</div>";
                    });
                    $('.m-details').html(html);//显示详细信息
                }, 'json');
                layer.open({
                    type: 1,
                    title: '设备信息',
                    shadeClose: true,
                    shade: 0.8,
                    content:$('.m-details')
                });
                return false;
            });
        }

        //批量删除设备
        function deleteDevices() {
            var btnDeleteMembers = $('.right-search .btn-delete-members');
            btnDeleteMembers.on('click', function () {
                var hostId = "";
                //循环获取选中的设备id
                $('.user-list li.member-selected .user-info .a-btn-details').each(function () {//图标选中设备则执行图标选中的设备
                    hostId = hostId + $(this).attr('id') + ",";
                });
                if (hostId == "") {
                    $('.devive-list-main .m-checked').each(function () {//图标没有选中设备则执行列表选中的设备
                        hostId = hostId + $(this).attr('id') + ",";
                    });
                }
                if (hostId == "") { //图标和列表都未选中设备
                    layer.msg("请至少选择一个设备");
                    return false;
                }
                layer.open({
                    type: 1,
                    title: '删除设备',
                    shadeClose: true,
                    shade: 0.8,
                    content:$('.m-delete-org-user'),
                    btn:["确定", "取消"],
                    end:function (index, layero) {
                    },
                    yes:function (index, layero) {
                        layer.close(index);//执行完成先隐藏弹窗
                        deleteDevice(hostId.substring(0, hostId.length - 1));//(批量)删除
                    }
                });
            });
        }

        //分配设备(建立匹配关系)
        function allocateDevice() {
            $('.btn-allot').on('click', function () {
                $('.win-block-fep-main-title').find('i').attr('class','a-check-all'); //初始化删除选中图标
                $('.m-search input').val("");//搜索词置空
                $.post(url+"/Group/deviceType.do", {}, function (data) {
                    var html = "";
                    html += " <select id='systlt2' name='systlt2'>"
                        + "<option value= ''>全部类型</option>"
                    $.each(data.value, function (ind, value) {
                        //循环获取未分配设备
                        var dtype_name_cn = value["dtype_name_cn"];
                        var dtype_name = value["dtype_name"];

                        html += "<option value='" + dtype_name + "'>" + dtype_name_cn + "</option>"
                    })
                    html += " </select>"
                    $('#allocateselect').html(html);
                    var select = new SelectList('#systlt2', function (option) {
                        initAllocate();//分配设备
                    });
                    select.init();
                }, 'json');

                var areaId = $('#left_nav_ul').find('li.selected:last').children('a').attr('id');
                if(!areaId)
                {
                    layer.msg("请先选择要分配的地点"); //areaId不能为空
                    return false;
                }

                initAllocate();
                //搜索
                $('.win-block-fep-title .btn-search').on('click', function () {
                    //点击搜索按钮搜索
                    initAllocate();
                });

                $('.win-block-fep-title  .m-search input').bind('keypress', function(event){
                    if(event.keyCode == '13'){
                        //点回车键进行搜索
                        initAllocate();
                    }
                });
                layer.open({
                    type: 1,
                    title: '分配设备',
                    shadeClose: true,
                    shade: 0.8,
                    area:["801px","475px"],
                    content:$('.m-allot-member'),
                    btn:["确定", "取消"],
                    end:function (index, layero) {
                        this.deviceType = $("#systlt2").find("input[name='systlt2']").val("");
                    },
                    yes:function (index, layero)  {
                        var deviceId = "";
                        $('.win-block-fep-main-infoa .a-checked').each(function () {
                            //循环获取设备id
                            deviceId = deviceId + $(this).attr("id") + ",";
                        });

                        if (deviceId == '') {
                            layer.msg("请先选择设备");
                            return false;
                        } else {
                            deviceId = deviceId.substring(0, deviceId.length - 1);
                            $.post(url+"/Group/allocateDevices.do", {
                                'areaId': areaId,
                                'deviceId': deviceId
                            }, function (data) {
                                layer.close(index);//执行完成先隐藏弹窗
                                if (data.code == 0) {
                                    if (data.value) {
                                        layer.msg("分配成功");
                                        getDevice();//刷新右侧设备
                                        return true;
                                    }
                                }
                                layer.msg("分配失败");
                                return false;
                            }, 'json');
                        }
                    }
                });
            });
        }

        //初始化分配设备
        function initAllocate() {
            var html = " ";
            this.conditions = $('.m-search #search ').val().trim();
            this.deviceType = $("#systlt2").find("input[name='systlt2']").val();
            if(!this.deviceType)
            {
                this.deviceType = "";
            }
            $.post(url+"/Group/searchDevice.do", {
                'page': page,
                'pageSize': allopage,
                'deviceType': this.deviceType,
                'conditions': this.conditions,
                'areaId': areaIds
            }, function (data) {
                console.log('data:   ' + data)
                console.log('date.hostInfo:  ' + data.hostInfo);
                var totalCount = data.itemsCount;
                html += totalCount + "@_@";
                $.each(data.hostInfo, function (ind, value) {
                    var ip = value["ip"];
                    var id = value["id"];
                    var name = value["name"];
                    var typeName = value["typeName"];
                    var arecName = "未分组";
                    html += "<div class='win-block-fep-main-info'>"
                        + "<div class='u-check'>"
                        + "<i id='" + id + "' class='a-check'></i>"
                        + "</div>"
                        + "<span class='w-block-fep-name' title='" + name + "' >" + name + "</span>"
                        + "<span class='w-block-fep-lei'  title='" + typeName + "'>" + typeName + "</span>"
                        + "<span class='w-block-fep-ip'   title='" + ip + "'> " + ip + "</span>"
                        + "<span class='w-block-fep-jg'   title='" + arecName + "'>" + arecName + "</span>"
                        + " </div>"
                })
                if (totalCount == 0) {
                    $('.win-block-fep-main-infoa').empty();
                    $(".win-block-fep-main-infoa").html("<div class='note-m' style='margin-top: 115px'>未发现可分配的设备</div>");
                    $('.a-page').html('');//没有设备则清空页码
                    return false;
                } else {
                    var n = html.indexOf("@_@");
                    var total = html.substring(0, n);
                }
                allocateInitPage(total);//分配分页
                html = html.substring(n + 3, html.length); //删除总页数和标记@_@
                $('.win-block-fep-main-infoa').empty();
                $(".win-block-fep-main-infoa").html(html);
                $('.a-check-all').removeClass('a-checked');

            }, 'json');
        }
        //设备分配分页
        function allocateInitPage(total) {
            $('.a-page').html('');//初始化置空
            if (total > allopage) {
                var page = new Pagination(parseInt(total), function (pageIndex) {
                    searchAllocate(pageIndex);
                });
                page.init('.a-page',allopage);
            }
        }

        //分页分配设备中搜索设备
        function searchAllocate(pageIndex) {
            var html = " ";
            this.conditions = $('.m-search #search ').val().trim();
            this.deviceType = $("#systlt2").find("input[name='systlt2']").val();
            pageIndex += 1;
            $.post(url+"/Group/searchDevice.do", {
                'page': pageIndex,
                'pageSize': allopage,
                'deviceType': this.deviceType,
                'conditions': this.conditions,
                'areaId': areaIds
            }, function (data) {
                var totalCount = data.itemsCount;
                html += totalCount + "@_@";
                $.each(data.hostInfo, function (ind, value) {
                    //分页循环获取未分组设备
                    var ip = value["ip"];
                    var id = value["id"];
                    var name = value["name"];
                    var typeName = value["typeName"];
                    var arecName = value["arecName"];
                    if (arecName == "") {
                        arecName = "未分组";
                    }
                    html += "<div class='win-block-fep-main-info'>"
                        + "<div class='u-check'>"
                        + "<i id='" + id + "'  class='a-check'></i>"
                        + "</div>"
                        + "<span class='w-block-fep-name' title='" + name + "'>" + name + "</span>"
                        + "<span class='w-block-fep-lei'  title='" + typeName + "'>" + typeName + "</span>"
                        + "<span class='w-block-fep-ip'   title='" + ip + "'> " + ip + "</span>"
                        + "<span class='w-block-fep-jg'   title='" + arecName + "'>" + arecName + "</span>"
                        + " </div>"
                })
                var n = html.indexOf("@_@");
                html = html.substring(n + 3, html.length);
                $('.win-block-fep-main-infoa').empty();
                $(".win-block-fep-main-infoa").html(html);
            }, 'json');
        }

        //点击下拉框搜索设备
        function initdeviceselect() {
            $.post(url+"/Group/deviceType.do", {}, function (data) {
                var html = "";
                html += " <select id='systlt' name='systlt'>"
                    + "<option value= ''>全部类型</option>"
                $.each(data.value, function (ind, value) {
                    //后台获取已有设备
                    var dtype_name_cn = value["dtype_name_cn"];
                    var dtype_name = value["dtype_name"];
                    html += "<option value='" + dtype_name + "'>" + dtype_name_cn + "</option>"
                })
                html += " </select>"
                $('#selecttype').html(html);
                var select = new SelectList('#systlt', function (option) {
                    getDevice();//刷新右侧设备
                });
                select.init();
            }, 'json');

            $('.right-search .btn-search').on('click', function () {
                //点击按钮进行搜索
                getDevice();//执行搜索
            });
            $('.right-search .m-search input').bind('keypress', function(event){
                if(event.keyCode == '13'){
                    //点回车键进行搜索
                    getDevice();//执行搜索
                }
            });
        }

        //获取地点类型
        function initareaType() {
            $.post(url+"/Group/areaType.do", {}, function (data) {
                var html = "";
                html +=  "<option value= ''>全部类型</option>"
                $.each(data.value, function (ind, value) {
                    //后台获取已有设备
                    var dtype_name_cn = value["name"];
                    var dtype_name = value["id"];
                    html += "<option value='" + dtype_name + "'>" + dtype_name_cn + "</option>"
                })

                $('#winleix').html(html);
                var select = new SelectList('#winleix', function (option) {
                    if($('#winleix').find("input").val().trim()==6){
                        $('#room').show();
                    }
                    if($('#winleix').find("input").val().trim()!=6){
                        $('#room').hide();
                    }
                });
                select.init();
                var winleix =document.getElementById("winleix")
                winleix.style.zIndex = "30";

            }, 'json');
        }

        //获取所有校区
        function initCampus() {
            $.post(url+"/Group/getCampus.do", {}, function (data) {
                var html = "";
                html += "<option value= ''>全部类型</option>"
                $.each(data.value, function (ind, value) {
                    //后台获取已有设备
                    var dtype_name_cn = value["name"];
                    var dtype_name = value["id"];
                    html += "<option value='" + dtype_name + "'>" + dtype_name_cn + "</option>"
                })
                html += " </select>"
                $('#winxiaoqu').html(html);
                var select = new SelectList('#winxiaoqu', function (option) {
                });
                select.init();
                var winleix =document.getElementById("winxiaoqu")
                winleix.style.zIndex = "10";
            }, 'json');
        }

        //获取所有教室类型
        function initRoomType() {
            $.post(url+"/Group/getRoom.do", { 'p_id': 6}, function (data) {
                var html = "";
                html += "<option value= ''>全部类型</option>"
                $.each(data.value, function (ind, value) {
                    //后台获取已有设备
                    var dtype_name_cn = value["name"];
                    var dtype_name = value["id"];
                    html += "<option value='" + dtype_name + "'>" + dtype_name_cn + "</option>"
                })
                html += " </select>"
                $('#winroom').html(html);
                var select = new SelectList('#winroom', function (option) {
                });
                select.init();
                var winleix =document.getElementById("winroom")
                winleix.style.zIndex = "28";
            }, 'json');
        }

        //切换列表显示和图标显示
        function switchmode() {
            $(document).on("click", ".right-top > span", function () {
                var flag = $(this).attr("class");
                if (flag == "sprt-list") {
                    $(this).css("opacity","1"); //选中时当前图标变深
                    $(".sprt-tab").css("opacity",".5");//未选中图标变浅
                    $(".devive-list-ls").show();//显示列表
                    $(".user-list").hide();//隐藏图标
                    $('.user-list li .headimg').siblings('.head-select').hide(); //切换时取消选中图标
                    $('.user-list li .headimg').parents('li').removeClass('member-selected');//切换时取消选中图标
                } else if (flag == "sprt-tab") {
                    $(this).css("opacity","1");//选中时当前图标变深
                    $(".sprt-list").css("opacity",".5");//未选中图标变浅
                    $(".devive-list-ls").hide();//隐藏列表
                    $(".user-list").show();//显示图标
                    $('.m-check-all').removeClass('m-checked');//切换时取消选中设备
                    $('.m-check').removeClass('m-checked');//切换时取消选中设备
                }
            })
        }
    });

});
