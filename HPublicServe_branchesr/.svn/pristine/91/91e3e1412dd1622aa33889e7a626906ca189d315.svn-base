define('roles/roles-manage', function (require, exports, module) {

    var $ = require('jquery');
    require('select-list/selectlist');
    var SelectList = require('select-list/select');
    var Config = require('configs/configs');
    require('libs/layer/layer');
    module.exports = {
        init: function () {
            this.url = location.href.substring(0, location.href.lastIndexOf("/"));
            this.getRoles();
            this.initAddRole();
            this.initRoleConfirm();
            this.initUrls();
        },
        initUrls: function(){
            var userName = $('.user').find('a').first().text();
            $('.nav').find('a').each(function(){
                $(this).attr('href',$(this).attr('href')+'&userName=' + userName);
            });
        },
        // 获取所有角色列表
        getRoles: function () {
            var that = this;
            $.post(that.url + "/Role/getRoles.do", function (data) {
                // 判断用户是否是管理员， 不是管理员屏蔽修改和删除功能
                var isAdmin = JSON.parse(sessionStorage.getItem('isAdmin'));
                var isSuperAdmin = JSON.parse(sessionStorage.getItem('isSuperAdmin'));

                var roles = "";
                $.each(data.roleList, function (i, value) {
                    roles += "<li class=\"" + (i == 0 ? 'selected' : '') + "\">";
                    // roles += "<a href=\"javascript:void(0)\" class=\"m-menu-btn\" title=\"" + value.roleName + "\" role_id=\"" + value.roleId + "\">" + value.roleName + "</a>";

                    roles += "<a href=\"javascript:void(0)\" class=\"m-menu-btn\" title=\"" + value.roleName + "\" role_id=\"" + value.roleId + "\" role_init=\"" + value.roleInit + "\">" + value.roleName + "</a>";
                    roles += "<div class=\"menu-btns\">";


                    roles += "<a href=\"javascript:void(0)\" class=\"a-btn a-btn-edit btn-role-details\" title='修改'></a>";
                    roles += "<a href=\"javascript:void(0)\" class=\"a-btn a-btn-delete btn-role-delete\" title='删除'></a>";

                    roles += "</div></li>";
                })
                $("#roles").html(roles);
                var title = $(".m-menu").find(".selected").find(".m-menu-btn").text();
                $('.right-top').children('h6').text(title);
                that.changeRole();
                that.initMenu();
                that.initDeleteRole();
                that.initEditRole();
            }, 'json');
        },
        // 权限树显示
        showPermission : function (data) {
            var perList = data.AllPermission.permissions;
            var html="<ul>";
            html = this.addPermission(html,perList);
            html = html + "</ul>"
            $("#testPer").html(html);
        },
        // 在页面中添加权限
        addPermission : function (html,perList) {
            var that = this;
            $.each(perList,function(i,value){
                if(value.permissions!=null&&value.permissions.length>0){
                    html = html + "<li class='single-line'><i class=\"m-check " + value.isSelect + "\" per_id=\"" + value.id + "\"></i><lable>" + value.desc + "</lable>";
                    html = html + "<ul>";
                    // 如果有子权限，递归调用，为该权限增加子权限
                    html = that.addPermission(html,value.permissions);
                    html = html + "</ul>";
                }else{
                    html = html + "<li><i class=\"m-check " + value.isSelect + "\" per_id=\"" + value.id + "\"></i><lable>" + value.desc + "</lable>";
                }
                html = html + "</li>"
            })
            return html;
        },
        // 改变角色 获取对应角色的权限
        changeRole: function () {
            var that = this;
            // 点击的角色
            var role = $(".m-menu").find(".selected").find(".m-menu-btn");
            var role_id = role.attr("role_id");
            // 当前显示的系统
            var token = $("#system").find("input[name='system']").val();
            $.post(that.url + "/Role/changeRole.do", {
                role_id: role_id,
                token: token
            }, function (data) {
                // 系统下拉菜单添加数据
                var option = "";
                $.each(data.sysList, function (i, value) {
                    option += "<option value=\"" + value.name + "\">" + value.description + "</option>"
                })
                $("#system").html(option);
                // 权限树生成
                that.showPermission(data);
                that.initSelect();
                that.initRolesList();
            }, 'json');
        },
        // 改变系统 获取对应权限
        changeToken: function () {
            var that = this;
            var role = $(".m-menu").find(".selected").find(".m-menu-btn");
            var role_id = role.attr("role_id");
            var token = $("#system").find("input[name='system']").val();
            var permission = "";
            $.post(that.url + "/Role/changeToken.do", {
                role_id: role_id,
                token: token
            }, function (data) {
                that.showPermission(data);
                that.initRolesList();
            },'json');
        },
        //获取所有用户身份 添加角色时使用
        getUserType: function () {
            var that = this;
            $.post(that.url + "/Role/getUserType.do", {
            }, function (data) {
                var userTypeList = "";
                $.each(data.userTypeList, function (i, value) {
                    userTypeList += "<div class=\"con-item con-identify\"><i type_id=\"" + value.typeId + "\" class=\"m-check\"></i><label class=\"ellitext\">" + value.typeName + "</label></div>";
                });
                $("#usertype_List").html(userTypeList);
                $("#role_name").val("");
                that.initRolesList();
            }, 'json');

        },
        //获取所有用户身份  修改角色时使用
        getUserTypeUsed: function (role_id, role_name) {
            var that = this;
            $.post(that.url + "/Role/getUserTypeUsed.do", {
                role_id: role_id
            }, function (data) {
                var userTypeList = "";
                $.each(data.usedTypeList, function (i, value) {
                    userTypeList += "<div class=\"con-item con-identify\"><i type_id=\"" + value.typeId + "\" class=\"m-check " + value.isSelect + "\"></i><label class=\"ellitext\">" + value.typeName + "</label></div>";
                });
                $("#usertype_List").html(userTypeList);
                $("#role_name").val(role_name);
                $("#usertype_List").attr("oldUserType", data.oldUsedType);
                that.initRolesList();
            }, 'json');
        },
        // 确定添加角色
        addRole: function (that) {
            var role_name = $("#role_name").val();
            var pattern = /^[a-zA-Z\u4e00-\u9fa5]+$/;
            var usertypeSelect = $("#usertype_List").find(".m-checked");
            if (role_name.trim() == "") {
                layer.msg("角色名称不能为空！");
                return false;
            }
            if (!pattern.test(role_name)){
                layer.msg("角色名称只能输入中文和字母");
                return false;
            }
            else if (role_name.length > 45) {
                layer.msg("角色名称请小于45个字符");
                return false;
            }
            else {
                var typeIds = "";
                $.each(usertypeSelect, function (i, value) {
                    var typeId = usertypeSelect.eq(i).attr("type_id");
                    typeIds += typeId + ",";
                });
                $.post(that.url + "/Role/addRole.do", {
                    role_name: role_name,
                    typeIds: typeIds
                }, function (data) {
                    if (data.msg == 1) {
                        layer.msg("添加成功");
                    }else if(data.msg == -1){
                        layer.msg("角色名已存在");
                    }
                    else{
                        layer.msg("添加失败");
                    }
                    that.getRoles();
                }, 'json');
                return true;
            }
        },
        // 确定修改角色
        editRole: function (role_id) {
            var that = this;
            var pattern = /^[a-zA-Z\u4e00-\u9fa5]+$/;
            var role_name = $("#role_name").val();
            var usertypeSelect = $("#usertype_List").find(".m-checked");
            if (role_name.trim() == "") {
                layer.msg("角色名称不能为空！");
                return false;
            }
            else if (role_name.length > 45) {
                layer.msg("角色名称请小于45个字符");
                return false;
            }else if (!(pattern.test(role_name))){
                layer.msg("角色名称只能为中文和字母");
                return false;
            }
            else {
                var typeIds = "";
                $.each(usertypeSelect, function (i, value) {
                    var typeId = usertypeSelect.eq(i).attr("type_id");
                    typeIds += typeId + ",";
                });
                $.post(that.url + "/Role/editRole.do", {
                    role_name: role_name,
                    typeIds: typeIds,
                    oldRoleId: role_id
                }, function (data) {
                    if (data.msg) {
                        layer.msg("修改成功");
                    }
                    else{
                        layer.msg("角色名已存在");
                    }
                    that.getRoles();
                }, 'json');
                return true;
            }
        },
        // 确定删除角色
        deleteRole: function (role_id, that) {
            //var init = $('#roles').find(".selected").find("a").attr('role_init');
            //if (init == '1') {
            //    layer.msg("系统内置角色请误删除!");
            //    return false;
            //}
            $.post(that.url + "/Role/deleteRole.do", {role_id: role_id}, function (data) {
                if (data.msg) {
                    layer.msg("删除成功");
                }
                else{
                    layer.msg("删除失败");
                }
                that.getRoles();
            }, 'json');
        },
        // 确定修改权限
        updatePermission: function () {
            var that = this;
            var role = $(".m-menu").find(".selected").find(".m-menu-btn");
            var role_id = role.attr("role_id");
            var authority = $("#testPer").find(".m-checked");
            var authorityIds = "";
            // 系统名
            var token = $("#system").find("input[name='system']").val();
            // 选择的权限
            $.each(authority, function (i, value) {
                var authorityId = authority.eq(i).attr("per_id");
                authorityIds += authorityId + ",";
            });
            $.post(that.url + "/Role/updatePermisssion.do", {
                role_id: role_id,
                authorityIds: authorityIds,
                token: token
            }, function (data) {
                if (data.msg) {
                    layer.msg("权限分配成功");
                }
                else{
                    layer.msg("权限分配失败");
                }
            }, 'json');
        },
        // 左侧树选择
            initMenu: function () {
            var that = this;
            $('.m-menu li a.m-menu-btn').on('click', function () {
                $(this).parent('li').addClass('selected').siblings().removeClass('selected');
                var title = $(this).text();
                $('.right-top').children('h6').text(title);
                that.changeRole();
            })
        },
        // 初始化下拉框
        initSelect: function () {
            var that = this;
            var select = new SelectList('#system', function (option) {
                that.changeToken();
            });
            select.init();
        },
        // 上级权限勾选方法
        parentCheck: function (check) {
            var that = this;
            var parent = check.parent().parent().parent();
            if(parent.hasClass('permissions-item')){
                return;
            }else{
                // 如果没到最外层DIV，递归，使父级权限被勾选
                parent.find('i').first().addClass('m-checked');
                return that.parentCheck(parent.find('i').first());
            }
        },
        // 权限的上级权限取消勾选
        parentRemove: function (check) {
            var that = this;
            var parent = check.parent().parent().parent();
            if(parent.hasClass('permissions-item')){
                return;
            }else{
                if(parent.hasClass('single-line')){
                    var flag = true;
                    // 判断同级别下的权限是否都被取消勾选
                    check.parent().parent().children().each(function(){
                        if($(this).find('i').first().hasClass('m-checked')){
                            flag = false;
                        }
                    });
                    // 如果都未勾选，父级权限也取消勾选
                    if(flag){
                        parent.find('i').first().removeClass('m-checked');
                    }
                }
                // 递归父级权限
                return that.parentRemove(parent.find('i').first());
            }

        },
        // 选择框选中方法
        checkCheck: function (check) {
            var that = this;
            if(check.hasClass('m-checked')){
                check.removeClass('m-checked');
                that.parentRemove(check);
            }else{
                check.addClass('m-checked');
                that.parentCheck(check);
            }
            /*if($('#teach_sch').hasClass('m-checked')){
                $("#order_teaching").parent().css("display","block");
            }
            if($('#class_sch').hasClass('m-checked')){
                $("#order_teaching").parent().css("display","block");
            }
            if(!$('#teach_sch').hasClass('m-checked') && !$('#class_sch').hasClass('m-checked')){
                $("#order_teaching").removeClass('m-checked');
                $("#order_teaching").parent().css("display","none");
            }*/
        },
        // 初始化权限列表
        initRolesList: function () {
            var that = this;
            // 添加权限时身份选择的复选框组
            var checks = $('#usertype_List .m-check');
            // 使权限名被点击时，也相当于对应的选择框被选中
            $('.permissions-item').find('lable').each(function(){
                $(this).css("cursor","pointer");
                var checkTemp = $(this).parent().find('i');
                //-----------------add for 互动  begin   add by caoqian---------------------
                if(checkTemp.attr('per_id')=='500012'){  //教师课程表
                    checkTemp.attr('id','teach_sch');
                }
                if(checkTemp.attr('per_id')=='500013'){  //教室课程表
                    checkTemp.attr('id','class_sch');
                }
                /*
                if(checkTemp.attr('per_id')=='500015'){  //预约
                    checkTemp.attr('id','order_teaching');
                }
                if(!$('#teach_sch').hasClass('m-checked') && !$('#class_sch').hasClass('m-checked')){
                    $("#order_teaching").removeClass('m-checked');
                    $("#order_teaching").parent().css("display","none");
                }*/
                //首页与查看模块默认勾选、隐藏
                if(checkTemp.attr('per_id')=='500001'){  //首页
                    checkTemp.parent().css("display","none");
                }
                if(checkTemp.attr('per_id')=='10010'){  //查看模块
                    checkTemp.parent().css("display","none");
                }
                //-----------------add for 互动  end---------------------
                $(this).on('click', function () {
                    that.checkCheck(checkTemp);
                });
            });
            // 权限树复选框组
            var checks2 = $('#testPer .m-check');
            //单选
            checks2.on('click', function () {
                that.checkCheck($(this));
            });
            checks.on('click', function () {
                $(this).hasClass('m-checked') ? $(this).removeClass('m-checked') : $(this).addClass('m-checked');
            });

            //如果该项下面有子类，则该项的宽度设置为100%；
            $('.permissions-item ul li').each(function () {
                var childUl = $(this).children('ul');
                if (childUl && childUl.length > 0) {
                    $(this).addClass('single-line');
                }
            });

            //如果该项下面有子类，则添加全选功能
            $('.permissions-item ul li .m-check').on('click', function () {
                var li = $(this).closest('li');
                var childUl = li.children('ul');
                if (childUl && childUl.length > 0) {
                    var allChildCheck = li.find('.m-check');
                    $(this).hasClass('m-checked') ? allChildCheck.addClass('m-checked') : allChildCheck.removeClass('m-checked');
                }
            });
        },

        // 添加角色
        initAddRole: function () {
            var that = this;
            $('.btn-add-role').on('click', function () {
                that.getUserType();
                layer.open({
                    type: 1,
                    title: '添加角色',
                    shadeClose: false,
                    shade: 0.8,
                    // area:["670px","500px"],
                    area:["360px","155px"],
                    content:$('.add-role'),
                    btn:["确定", "取消"],
                    end: function (index, layero) {
                        that.initRolesList();
                    },
                    yes: function (index, layero) {
                        var bool = that.addRole(that);
                        if (!bool) {
                            return false;
                        }
                        layer.close(index);
                    }
                });
            });
        },
        // 修改角色
        initEditRole: function () {
            var that = this;
            //修改角色弹窗
            $('.btn-role-details').on('click', function () {
                var role = $(this).parent().parent().find(".m-menu-btn");
                var role_id = role.attr("role_id");
                var role_name = role.text();
                that.getUserTypeUsed(role_id, role_name);
                layer.open({
                    type: 1,
                    title: '编辑角色',
                    shadeClose: true,
                    area:["670px","500px"],
                    shade: 0.8,
                    content:$('.add-role'),
                    btn:["确定", "取消"],
                    end: function (index, layero) {
                        that.initRolesList();
                    },
                    yes: function (index, layero) {
                        var bool = that.editRole(role_id);
                        if (!bool) {
                            return false;
                        }
                        layer.close(index);
                    }
                });
            });
        },
        // 删除角色
        initDeleteRole: function () {
            var that = this;
            $('.btn-role-delete').on('click', function () {
                var role = $(this).parent().parent().find(".m-menu-btn");
                var role_id = role.attr("role_id");
                var con = '<p class="delete-role-confirm" style="text-align: center ; line-height: 40px;font-size: 15px">确认删除该角色吗?</p>';
                layer.open({
                    type: 1,
                    title: '删除角色',
                    shadeClose: true,
                    shade: 0.8,
                    content:con,
                    area:["300px","140px"],
                    btn:["确定", "取消"],
                    end: function (index, layero) {
                    },
                    yes: function (index, layero) {
                        layer.close(index);
                        that.deleteRole(role_id, that);
                    }
                });
            });
        },
        // 权限确定按钮
        initRoleConfirm: function () {
            var that = this;
            $('.btn-role-confirm').on('click', function () {
                that.updatePermission();
            });
        },
        //刷新cache内容
        initCache: function () {
            //$.post(this .url + "/Role/cacheReload.do", {
            //}, function (data) {
            //}, 'html');
        }
    }

});
