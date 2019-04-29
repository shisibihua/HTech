define('organization/organization-user', function (require, exports, module) {

    var $ = require('jquery');
    var Config = require('configs/configs');
    require('libs/layer/layer');
    function OrganizationUser(ip,port) {
        this.searchWord = "";
        this.url = location.href.substring(0, location.href.lastIndexOf("/"));
        this.storageIp = ip;
        this.storagePort = port;
        //this.initCheck();
        //this.deleteMembers();
    }
    module.exports = OrganizationUser;
    //OrganizationUser.prototype.deleteMembers;
    OrganizationUser.prototype.init = function (id) {
        this.orgId = id;
        this.initSearch();
        this.getUserList();
    };
    OrganizationUser.prototype.clear = function () {
        $(".user-list ul").empty();
    };
    //初始化查找人员(搜索或点击左侧机构树)
    OrganizationUser.prototype.getUserList = function () {
        var that = this;
        var html = "";
        var lhtml = "";
        $.post(that.url+"/Campus/searchUsers.do", {
            'page': '1',
            'pageSize': '16',
            'campusId': this.orgId,
            'searchWord': this.searchWord
        }, function (data) {
            var totalCount = data.totalCount;
            html+=totalCount+"@_@";
            $.each(data.list,function(ind,value){
                var userPath = value["userPath"];
                var userId = value["userId"];
                var userRealName = value["userRealName"];
                var typeName = value["typeName"];
                if (totalCount != 0) {
                    var addr ="http://"+ that.storageIp+":"+that.storagePort;
                    var n = userPath.indexOf("/");//userPath为空为-1
                    if (userPath=="" || n == -1) {
                        userPath =  "./images/userimg.png";//获取显示的图片
                    } else {
                        userPath = userPath.substring(n, userPath.length);
                        userPath = addr + userPath;
                    }
                }
               html+="<li>" +
               "<div class='user-name'>" +
               "<span><img src='" + userPath + "' class='headimg'><img src='images/usersimg.png' " + "class='head-select'></span>"
               + "<span class='uname' title='"+userRealName+"'>" + userRealName + "</span><span class='urole'>" + typeName + "</span>" + "</div>"
               + "<div class='user-info'>" +
               "<a href='javascript:void(0)' id='" + userId + "' class='a-btn a-btn-details btn-member-details'></a>" +
               "<a href='javascript:void(0)' id='" + userId + "' class='a-btn a-btn-delete btn-delete'></a>" + "</div>"
               + "</li>"
            })
            if (totalCount != 0) {
                html+="<div class='clearfix'></div>";
            }
            var n =  html.indexOf("@_@");
            var total =  html.substring(0, n);
            html =  html.substring(n + 3, html.length);
            $(".user-list ul").html( html);
            //列表显示
            lhtml += totalCount + "@_@";
            lhtml += "<div class='devive-list-title'>"
            + " <div class='u-check'>"
            + "<i class='m-check-all'></i>"
            + "</div>"
            + "<div class='devive-name'>名称</div>"
            + "<div class='devive-lei'>身份</div>"
            + "<div class='devive-ip'>手机</div>"
            + "<div class='devive-dev'>电子邮箱</div>"
            + "<div class='devive-cz'>操作</div>"
            + "</div>"
            $.each(data.list, function (ind, value) {
                //循环获取设备信息并显示列表(拼接html)
                var userPath = value["userPath"];
                var userId = value["userId"];
                var userRealName = value["userRealName"];
                var typeName = value["typeName"];
                var userMobile = value["userMobile"];
                var userEmail = value["userEmail"];
                lhtml += "<div class='devive-list-main'>"
                + "<div class='u-check'>"
                + "<i  id='" + userId + "'  class='m-check'></i>"
                + "</div>"
                + "<div class='devive-name' title='" + userRealName + "'>" + userRealName + " </div>"
                + "<div class='devive-lei'  title='" + typeName + "'>" + typeName + "</div>"
                + "<div class='devive-ip'   title='" + userMobile + "'>" + userMobile + "</div>"
                + "<div class='devive-dev'  title='" + userEmail + "'>" + userEmail + "</div>"
                + "<div class='devive-cz'>"
                + "<a href='javascript:void(0)' id='" + userId + "' class='a-btn a-btn-delete devie-delete' title='删除'></a>"
                + "</div>"
                + "</div>"
            })
            var n = lhtml.indexOf("@_@");
            var total = lhtml.substring(0, n);
            lhtml = lhtml.substring(n + 3, lhtml.length);//把总页数和标记符(@_@)截去
            $(".devive-list-ls").html(lhtml);
            that.initDelete();
            that.initlDelete();
            that.initUserSelected();
            that.initCheck();
            that.initPage(total);
            that.showMemberDetails();
        }, 'json');
    };
    //删除成员
    OrganizationUser.prototype.deleteUser = function (userId) {
        var currentThis = this;
        $.post(currentThis.url+"/Campus/deleteUser.do", {
            'campusId': currentThis.orgId,
            'userId': userId
        }, function (data) {
            if (data.code == 0) {
                if (data.value) {
                    currentThis.getUserList();
                    layer.msg("删除成功");
                    return true;
                }
            }
        layer.msg("删除失败");
            return false;
        }, 'json');
    };
    //*-*
    OrganizationUser.prototype.initDelete = function () {
        var currentThis = this;
        $('.btn-delete').on('click', function () {
            var con = $('.m-delete-org-user');
            var that = $(this);
            layer.open({
                type: 1,
                title: '删除人员',
                shadeClose: true,
                shade: 0.8,
                content:con,
                btn:["确定", "取消"],
                end:function (index, layero) {
                },
                yes: function (index, layero) {
                    layer.close(index);
                    var userId = that.attr("id");
                    currentThis.deleteUser(userId);

                }

            });
            return false;
        });

    };

    OrganizationUser.prototype.initlDelete = function () {
        var currentThis = this;
        $('.devie-delete').on('click', function () {
            var con = $('.m-delete-org-user');
            var that = $(this);
            layer.open({
                type: 1,
                title: '删除人员',
                shadeClose: true,
                shade: 0.8,
                content:con,
                btn:["确定", "取消"],
                end:function (index, layero) {
                },
                yes: function (index, layero) {
                    layer.close(index);
                    var userId = that.attr("id");
                    currentThis.deleteUser(userId);
                }
            });
        });
    };

    //生成分页数据
    var Pagination = require('pagination/pagination');
    OrganizationUser.prototype.initPage = function (total) {
        $('#a-m-page').html('');
        if (total > 16) {
            var that = this;
            var page = new Pagination(parseInt(total), function (pageIndex) {
                that.getUserFromPage(pageIndex);
            });
            page.init('#a-m-page', 16);
        }
    };
    //分页显示成员
    OrganizationUser.prototype.getUserFromPage = function (index) {
        var that = this;
        var html = " ";
        var lhtml ="";
        index = index + 1;
        $.post(that.url+"/Campus/searchUsers.do", {
            'page': index,
            'pageSize': '16',
            'campusId': this.orgId,
            'searchWord': this.searchWord
        }, function (data) {
            var totalCount = data.totalCount;
            html+=totalCount+"@_@";
            $.each(data.list,function(ind,value){
                var userPath = value["userPath"];
                var userId = value["userId"];
                var userRealName = value["userRealName"];
                var typeName = value["typeName"];
                if (totalCount != 0) {
                    var addr ="http://"+ that.storageIp+":"+that.storagePort;
                    var n = userPath.indexOf("/");//userPath为空为-1
                    if (userPath=="" || n == -1) {
                        userPath =  "./images/userimg.png";//获取显示的图片
                    } else {
                        userPath = userPath.substring(n, userPath.length);
                        userPath = addr + userPath;
                    }
                }
                html+="<li>" +
                "<div class='user-name'>" +
                "<span><img src='" + userPath + "' class='headimg'><img src='images/usersimg.png' " + "class='head-select'></span>"
                + "<span class='uname' title='"+userRealName+"'>" + userRealName + "</span><span class='urole'>" + typeName + "</span>" + "</div>"
                + "<div class='user-info'>" +
                "<a href='javascript:void(0)' id='" + userId + "' class='a-btn a-btn-details btn-member-details'></a>" +
                "<a href='javascript:void(0)' id='" + userId + "' class='a-btn a-btn-delete btn-delete'></a>" + "</div>"
                + "</li>"

            })
            if (totalCount != 0) {
                html+="<div class='clearfix'></div>";
            }
            var n = html.indexOf("@_@");
            html = html.substring(n + 3, html.length);
            $(".user-list ul").html(html);


            //列表显示
            lhtml += totalCount + "@_@";
            lhtml += "<div class='devive-list-title'>"
            + " <div class='u-check'>"
            + "<i class='m-check-all'></i>"
            + "</div>"
            + "<div class='devive-name'>名称</div>"
            + "<div class='devive-lei'>身份</div>"
            + "<div class='devive-ip'>手机</div>"
            + "<div class='devive-dev'>电子邮箱</div>"
            + "<div class='devive-cz'>操作</div>"
            + "</div>"

            $.each(data.list, function (ind, value) {
                //循环获取设备信息并显示列表(拼接html)
                var userPath = value["userPath"];
                var userId = value["userId"];
                var userRealName = value["userRealName"];
                var typeName = value["typeName"];
                var userMobile = value["userMobile"];
                var userEmail = value["userEmail"];
                lhtml += "<div class='devive-list-main'>"
                + "<div class='u-check'>"
                + "<i  id='" + userId + "'  class='m-check'></i>"
                + "</div>"
                + "<div class='devive-name' title='" + userRealName + "'>" + userRealName + " </div>"
                + "<div class='devive-lei'  title='" + typeName + "'>" + typeName + "</div>"
                + "<div class='devive-ip'   title='" + userMobile + "'>" + userMobile + "</div>"
                + "<div class='devive-dev'  title='" + userEmail + "'>" + userEmail + "</div>"
                + "<div class='devive-cz'>"
                + "<a href='javascript:void(0)' id='" + userId + "' class='a-btn a-btn-delete devie-delete' title='删除'></a>"
                + "</div>"
                + "</div>"
            })
            var n = lhtml.indexOf("@_@");
            var total = lhtml.substring(0, n);
            lhtml = lhtml.substring(n + 3, lhtml.length);//把总页数和标记符(@_@)截去
            $(".devive-list-ls").html(lhtml);
            that.initDelete();
            that.initlDelete();
            that.initUserSelected();
            that.showMemberDetails();
            that.initCheck();
        }, 'json');
    };

    OrganizationUser.prototype.initUserSelected = function () {
        //点击头像加对勾
        $('.user-list li ').on('click', function () {
            var headImgSelect = $(this).find(".headimg").siblings('.head-select');
            if ($(this).find(".headimg").hasClass('selected')) {
                $(this).find(".headimg").removeClass('selected');
                headImgSelect.hide();
                $(this).find(".headimg").parents('li').removeClass('member-selected');
            } else {
                $(this).find(".headimg").addClass('selected');
                headImgSelect.show();
                $(this).find(".headimg").parents('li').addClass('member-selected');
            }
        })
    };


    OrganizationUser.prototype.initSearch = function () {
        var that = this;
        //搜索
        $('.right-search .btn-search').on('click', function () {
            that.searchWord = $('.right-search .m-search input').val();
            that.getUserList();
        });

        $('.right-search .m-search input').bind('keypress', function(event){
            if(event.keyCode == '13'){
                that.searchWord = $('.right-search .m-search input').val();
                that.getUserList();
            }
        });
    };
    //删除人员
    OrganizationUser.prototype.deleteMembers = function () {
        var currentThis = this;
        var btnDeleteMembers = $('.right-search .btn-delete-members');
        btnDeleteMembers.on('click', function () {
            var userId = "";
            $('.user-list li.member-selected .user-info .a-btn-details').each(function () {
                userId = userId + $(this).attr('id') + ",";
            });

            if (userId == "") {
                $('.devive-list-main .m-checked').each(function () {//图标没有选中设备则执行列表选中的设备
                    userId = userId + $(this).attr('id') + ",";
                });
            }

            if (userId == "") {
               layer.msg("请先选择人员");
                return false;
            }
            layer.open({
                type: 1,
                title: '删除人员',
                shadeClose: true,
                shade: 0.8,
                content:$('.m-delete-org-user'),
                btn:["确定", "取消"],
                end: function (index,layero) {
                },
                yes: function (index,layero) {
                    layer.close(index)
                    currentThis.deleteUser(userId.substring(0, userId.length - 1));
                }

            });

        });
    };
    //显示用户详细列表
    OrganizationUser.prototype.showMemberDetails = function () {
        var that = this;
        $('.btn-member-details').on('click', function () {
            var memberId = $(this).attr('id');
            var con = $('.m-details');
            var userPath = "";
            var userType ="";
            var userRealName = "";
            var userEmail = "";
            var userNum = "";
            var userMobile = "";
            var userBirthday = "";
            var userAddress = "";
            var userRealName = "";
            var addr = "";
            $.post(that.url+"/Campus/loadUsers.do", {
                'userId': memberId
            }, function (data) {
                var html="";
                $.each(data,function(key,value){

                    if(value) {
                        userPath = value['userPath'];
                        userType = value['userType'];
                        userRealName = value['userRealName'];
                        userEmail = value['userEmail'];
                        userNum = value['userNum'];
                        userMobile = value['userMobile'];
                        userBirthday = value['userBirthday'];
                        userAddress = value['userAddress'];
                        userRealName = value['userRealName'];
                    }
                    addr ="http://"+ that.storageIp+":"+that.storagePort;
                    var n = userPath.indexOf("/");//userPath为空为-1
                    if (userPath=="" || n == -1) {
                        userPath =  "./images/userimg.png";//获取显示的图片
                    } else {
                        userPath = userPath.substring(n, userPath.length);
                        userPath = addr + userPath;
                    }
                    var numName = "编号";
                    if (userType=="18") {
                        numName = "编号";//如果是学生将numName设定为学籍号
                    }
                    html+= "<div class='details-name'>"
                    + "<img src='" + userPath + "'>"
                    + " <div class='name-details-name'>"
                    + " <span class='name-first'>" + userRealName + "</span>"
                    + "<span>" + userEmail + "</span>"
                    + "</div>"
                    + "</div>"
                    + "<div class='details-content'>"
                    + "<div class='details-content-title'>详细信息</div>"
                    + "<div class='details-left'>"
                    + "<div class='number'>" + numName + "：" + userNum + "</div>"
                    + "<div class='tel'>电话：" + userMobile + "</div>"
                    + "<div class='birthday'>生日：" + userBirthday + "</div>"
                    + "<div class='address'>地址：" + userAddress + "</div>"
                    + "</div>"
                    + "</div>";
                });

                con.html(html);
            }, 'json');
            layer.open({
                type: 1,
                title: '用户信息',
                shadeClose: true,
                shade: 0.8,
                area:["360px","360px"],
                content:$('.m-details')
            });
            return false;
        });
    }

    // 初始化复选框
    OrganizationUser.prototype.initCheck = function () {
        var checkAll = $('.devive-list-ls  .m-check-all'),
            checks = $('.devive-list-ls .devive-list-main');
        //全选
        checkAll.on('click', function () {

            if ($(this).hasClass('m-checked')) {
                $(this).removeClass('m-checked');
                $('.devive-list-main').find('.m-check').removeClass('m-checked');
                $('.devive-list-main').css("background-color","white");
                $('.u-item').css("background-color","white");

            } else {
                $(this).addClass('m-checked');
                $('.devive-list-main').find('.m-check').addClass('m-checked');
                $('.devive-list-main').css("background-color","#eaedf1");
                $('.u-item').css("background-color","#eaedf1")
            }
        });

        //单选
        checks.on('click', function () {
            $(this).find(".m-check").hasClass('m-checked') ? $(this).find(".m-check").removeClass('m-checked') && $(this).css("background-color","white") : $(this).find(".m-check").addClass('m-checked') && $(this).css("background-color","#eaedf1");
            if ($('.devive-list-main').find('.m-check').length == $('.devive-list-main .m-checked').length) {
                $('.devive-list-ls').find(".m-check-all").addClass('m-checked');

            }
            // 未全被勾选，全选框取消勾选
            else
            {
                $('.devive-list-ls').find(".m-check-all").removeClass('m-checked');
            }
        });
    }
});
