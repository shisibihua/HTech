define('organization/allot-member', function (require, exports, module) {

    var $ = require('jquery');
    //分配人员弹窗
    var Config = require('configs/configs');
    var orgUser = require('organization/organization-user');

    require('libs/layer/layer');
    function AllotMember(storageIp,storagePort) {
        this.url = location.href.substring(0, location.href.lastIndexOf("/"));
        this.pageNumber = 10;
        this.searchWord = "";
        this.tsFlag="";
        this.users = new orgUser(storageIp,storagePort);
    }

    module.exports = AllotMember;

    AllotMember.prototype.allotPeople = function () {
        var that = this;
        var selectedLi = $('#left_nav_ul').find('li.selected');
        //当前选中机构的id
        var campusId =$("a[name = 'thiscampus']").attr('id');
        var typeId = $("a[name = 'thiscampus']").attr('typeid')
        //未选中机构时，弹出提示
        if (!campusId) {
            layer.msg("请点击左侧组织机构进行人员分配");
            return false;
        }

        that.searchWord = '';
        $('.m-allot-member .m-search input').val('');
        that.initUserList();
        //搜索
        $('.m-allot-member .btn-search').on('click', function () {
            that.searchWord = $('.m-allot-member .m-search input').val();
            that.initUserList();
        });
        //按回车搜索
        $('.m-allot-member .m-search input').bind('keypress', function(event){
            if(event.keyCode == '13'){
                that.searchWord = $('.m-allot-member .m-search input').val();
                that.initUserList();
            }
        });
        layer.open({
            type: 1,
            title: '分配人员',
            shadeClose: true,
            shade: 0.8,
            area:["720px","580px"],
            content: $('.m-allot-member'),
            btn:["确定", "取消"],
            end: function (index,layero)
            {

            },
            yes: function (index,layero) {
                var userId = "";
                // 批量分配时，多个userId按逗号分割
                $('.main-list .m-check.m-checked').each(function () {
                    userId = userId + $(this).attr("id") + ",";
                });

                if (userId == '') {
                    layer.msg("请选择人员");
                    return false;
                }
                else {
                    userId = userId.substring(0, userId.length - 1);
                    $.post(that.url + "/Campus/allocateCampus.do", {
                        'campusId': campusId,
                        'userId': userId,
                        'typeId':typeId
                    }, function (data) {
                        layer.close(index);
                        if (data.code == 0) {
                            if (data.value) {
                                // 分配成功，重新加载数据
                                that.users.init(campusId);
                                layer.msg("分配成功");
                                return true;
                            }
                        }
                        layer.msg("分配失败");
                        return false;
                    }, 'json');
                }

            }
        });
    };

   //分配人员到组织机构
    AllotMember.prototype.initView = function () {
        var that = this;
        $('.btn-allot_director').on('click', function () {
            that.tsFlag="TE"
            that.allotPeople();
        });

        $('.btn-allot').on('click', function () {
            that.tsFlag=""
            that.allotPeople();
        });

        $('.btn-allot_teacher').on('click', function () {
            that.tsFlag="TE"
            that.allotPeople();
        });

        $('.btn-allot_student').on('click', function () {
            that.tsFlag="ST"
            that.allotPeople();
        });

        $('.btn-allot_rector').on('click', function () {
            that.tsFlag="TE"
            that.allotPeople();
        });
    }
    //生成分页数据
    var Pagination = require('pagination/pagination');
    AllotMember.prototype.initPage = function (total) {
        $('#m-page').html('');
        if(total >10) {
            var that = this;
            var page = new Pagination(parseInt(total), function (pageIndex) {
                that.getUserFromPage(pageIndex);
            });
            page.init('#m-page');
        }
    }
    //初始化分配人员里获取用户详细列表
    AllotMember.prototype.initUserList = function () {
        var that = this;
        var html =" ";
        //当前选中机构的typeId
        var selectedLi = $('#left_nav_ul').find('li.selected');
        var campusId = selectedLi.eq(selectedLi.length-1).children('a').attr('id');
        var typeId = selectedLi.eq(selectedLi.length-1).children('a').attr('typeid');
        var stageId = selectedLi.eq(selectedLi.length-1).children('a').attr('stagesid');
        $.post(that.url+"/Campus/loadCampus.do", {
            'page': '1',
            'pageSize': that.pageNumber,
            'searchName': this.searchWord,
            'campusType':typeId,
            'tsFlag':this.tsFlag,
            'campusId':campusId,
            'stageId':stageId
        }, function (data) {
            var totalCount = data.totalCount;

            html+=totalCount
            + "@_@<div class='u-title'>"
            + "<div class='u-check'>"
            + "<i class='m-check-all'></i>"
            + "</div>"
            + "<div class='u-name'>姓名</div>"
            + "<div class='u-sex'>性别</div>"
            + "<div class='u-tel'>电话</div>"
            + "<div class='u-email'>邮箱</div>"
            + "<div class='u-identity'>身份</div>"
            + "</div>";

            $.each(data.list,function(ind,value){
                var userId = value["userId"];
                var userRealName = value["userRealName"];
                var userMobile = value["userMobile"];
                var userEmail = value["userEmail"];
                var typeName = value["typeName"];
                var userGender = value["userGender"];
                if (totalCount != 0) {
                    if (userGender == "1") {
                        userGender = "男";
                    } else if (userGender == "2") {
                        userGender = "女";
                    } else {
                        userGender = "未知";
                    }
                }
                html+="<div class='u-item'>"
                + "<div class='u-check'>" + "<i class='m-check' id='" + userId + "'></i>" + "</div>"
                + "<div class='u-name'>"
                + "<div class='user-head'>"
                + "<div class='user-con' title='" + userRealName + "'>" + userRealName + "</div>"
                + "</div>"
                + "</div>"
                + "<div class='u-sex'>" + userGender + "</div>"
                + "<div class='u-tel'>" + userMobile + "</div>"
                + "<div class='u-email' title='" + userEmail + "'>" + userEmail + "</div>"
                + "<div class='u-identity' title='" + typeName + "'>" + typeName + "</div>"
                + "</div>";
            })
            var n = html.indexOf("@_@");
            var total = html.substring(0, n);
            that.initPage(total);
            html = html.substring(n + 3, html.length);
            $('.main-list').empty();
            $('.main-list').append(html);
            that.initCheck();
        }, 'json');
    }

    //分页分配人员里获取用户详细列表
    AllotMember.prototype.getUserFromPage = function (pageIndex) {
        var that = this;
        var html ="";
        var selectedLi = $('#left_nav_ul').find('li.selected');
        //当前选中机构的typeId
        var typeId = selectedLi.eq(selectedLi.length-1).children('a').attr('typeid');
        var campusId = selectedLi.eq(selectedLi.length-1).children('a').attr('id');
        var stageId = selectedLi.eq(selectedLi.length-1).children('a').attr('stagesid');
        pageIndex = pageIndex + 1;
            $.post(that.url+"/Campus/loadCampus.do", {
            'page': pageIndex,
            'pageSize': this.pageNumber,
            'searchName': this.searchWord,
            'campusType':typeId,
            'tsFlag':this.tsFlag,
            'campusId':campusId,
            'stageId':stageId
        }, function (data) {
                var totalCount = data.totalCount;

                html+=totalCount
                + "@_@<div class='u-title'>"
                + "<div class='u-check'>"
                + "<i class='m-check-all'></i>"
                + "</div>"
                + "<div class='u-name'>姓名</div>"
                + "<div class='u-sex'>性别</div>"
                + "<div class='u-tel'>电话</div>"
                + "<div class='u-email'>邮箱</div>"
                + "<div class='u-identity'>身份</div>"
                + "</div>";

                $.each(data.list,function(ind,value){
                    var userId = value["userId"];
                    var userRealName = value["userRealName"];
                    var userMobile = value["userMobile"];
                    var userEmail = value["userEmail"];
                    var typeName = value["typeName"];
                    var userGender = value["userGender"];
                    if (totalCount != 0) {
                        if (userGender == "1") {
                            userGender = "男";
                        } else if (userGender == "2") {
                            userGender = "女";
                        } else {
                            userGender = "未知";
                        }
                    }
                    html+="<div class='u-item'>"
                    + "<div class='u-check'>" + "<i class='m-check' id='" + userId + "'></i>" + "</div>"
                    + "<div class='u-name'>"
                    + "<div class='user-head'>"
                    + "<div class='user-con' title='" + userRealName + "'>" + userRealName + "</div>"
                    + "</div>"
                    + "</div>"
                    + "<div class='u-sex'>" + userGender + "</div>"
                    + "<div class='u-tel'>" + userMobile + "</div>"
                    + "<div class='u-email' title='" + userEmail + "'>" + userEmail + "</div>"
                    + "<div class='u-identity' title='" + typeName + "'>" + typeName + "</div>"
                    + "</div>";
                })

                var n = html.indexOf("@_@");
                html = html.substring(n + 3, html.length);
                $('.main-list').empty();
                $('.main-list').append(html);
                that.initCheck();
        }, 'json');
    }
    // 初始化复选框
    AllotMember.prototype.initCheck = function () {
        var checkAll = $('.main-list .m-check-all'),
            checks = $('.main-list .u-item');

        //全选
        checkAll.on('click', function () {
            if ($(this).hasClass('m-checked')) {
                $(this).removeClass('m-checked');
                $('.main-list').find('.m-check').removeClass('m-checked');
                $('.u-item').css("background-color","white");
                $('.u-item').css("background-color","white");

            } else {
                $(this).addClass('m-checked');
                $('.main-list').find('.m-check').addClass('m-checked');
                $('.u-item').css("background-color","#eaedf1");
                $('.u-item').css("background-color","#eaedf1")
            }
        });

        //单选设备分配点击整行都可以选
        checks.on('click', function () {
            $(this).find(".m-check").hasClass('m-checked') ? $(this).find(".m-check").removeClass('m-checked') && $(this).css("background-color","white") : $(this).find(".m-check").addClass('m-checked') && $(this).css("background-color","#eaedf1");
            // 都被勾选 ，全选框也被勾选
            if ( $('.main-list').find('.m-check').length == $(' .u-item .m-checked').length) {
                $('.main-list').find(".m-check-all").addClass('m-checked');
            }
            // 未全被勾选，全选框取消勾选
            else
            {
                $('.main-list').find(".m-check-all").removeClass('m-checked');
            }
        });

    }


});
