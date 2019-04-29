define('organization/organization', function (require, exports, module) {

    var $ = require('jquery');
    //添加机构弹窗
    var Config = require('configs/configs');
    var SelectList = require('select-list/select');
    var OrganizationUser = require('organization/organization-user');
    var TOPLEVEL = '-1';

    require('jquery/jquery-form');
    require('libs/layer/layer');
    var userId = sessionStorage.getItem('userId');
    function Organization(ip, port) {
        this.url = location.href.substring(0, location.href.lastIndexOf("/"));
        //alert(this.url);
        this.initUpload();
        this.initDownloadCampus();
        this.initImportCampus();
        this.switchmode();
        this.getStages();
        this.user = new OrganizationUser(ip, port);
        this.user.deleteMembers();

        this.changeTab(); // 切换tab
        // this.orgSSQ(); // 切换tab
        //this.onClickCampus(); // 点击组织机构
    }

    module.exports = Organization;

    // 根据用户id获取获取所对应的行政区域列表
    Organization.prototype.initAreaList = function () {
    }

    //获取左侧组织机构树
    Organization.prototype.initData = function (id) {
        var that = this;
        var userId = sessionStorage.getItem('userId');
        // $.post(that.url + "/Campus/searchCampus.do", {userId: userId}, function (data) { 之前版本
        $.post(that.url + "/Campus/campusSearchByUserId.do", {userId: userId, type:1}, function (data) {
            if(data){
                $('.btnAddOrg').hide();
                $('.org-menu').show();
                $('.org-menu').html(data);
            }else{
                $('.btnAddOrg').show();
            }

            // // 区管理员显示所有功能， 校级管理员值显示自己学校的功能
            // var isAdmin = JSON.parse(sessionStorage.getItem('isAdmin'));
            // var isSuperAdmin = JSON.parse(sessionStorage.getItem('isSuperAdmin'));
            // var adminCampusId = sessionStorage.getItem('adminCampusId');
            // $('#left_nav_ul li a').on('mouseenter', function() {
            //     var campusId = $(this).attr('id');
            //     $('#left_nav_ul .btn-edit-org').hide();
            //     $('#left_nav_ul .btn-delete-org').hide();
            //      $('#left_nav_ul .btn-add-org').hide();
            //     if (isSuperAdmin) {
            //         $('#left_nav_ul .btn-edit-org').show();
            //         $('#left_nav_ul .btn-delete-org').show();
            //         $('#left_nav_ul .btn-add-org').show();
            //     } else if (isAdmin && adminCampusId.indexOf(campusId) > -1) {
            //         $('#left_nav_ul .btn-edit-org').show();
            //     }
            // })
            //
            that.initMenu();
            // if (id != '' && id != undefined) {
            //     var a = $('#' + id);
            //     that.selected(a);
            //     // 延迟操作，等待提示弹窗结束
            //     setTimeout(function () {
            //         // 循环，依次展开添加过标识的a标签
            //         $('#left_nav_ul').find('.addSelect').each(function () {
            //             if (!$(this).parent().hasClass('selected')) {
            //                 $(this).trigger("click");//让系统自动执行单击事件
            //             }
            //         });
            //         // 定位到新添加的标签
            //         a.trigger("click");
            //     }, 1100);
            // }
            that.orgSSQ(); // 获取省/市/区列表
            // that.orgCity();
            that.getType(0);
            that.initAdd();
            that.initEdit();
            that.initDelete();
            that.initUrls();
            that.initWinback();
            $(".user-list").show();//显示图标
            $(".devive-list-ls").hide();//隐藏图标
            $(".sprt-tab").css("opacity", "1");//选中图标变深
            $(".sprt-list").css("opacity", ".5");//未选中图标变浅
            that.onClickCampus(); // 点击组织机构


        }, 'html');

    }
    // 递归调用，为每个需要展开的a标签添加class标识
    // 由于展开需要从上层到底层，此方法只能从底层上上层查找，添加
    // 所以本方法只添加标识
    Organization.prototype.selected = function (a) {
        a.addClass('addSelect');
        if (a.parent().parent().attr('id') == "left_nav_ul") {
            return;
        } else {
            return this.selected(a.parent().parent().prev('a'));
        }
    }

    Organization.prototype.middle = function (a,name) {
        if (a.parent().parent().attr('id') == "left_nav_ul") {
            return name;
        }
        else {
            name += "," + a.parent().parent().prev('a').find('h6').text();
            return this.middle(a.parent().parent().prev('a'),name);
        }

    }

    Organization.prototype.initUrls = function () {
        var userName = $('.user').find('a').first().text();
        $('.nav').find('a').each(function () {
            $(this).attr('href', $(this).attr('href') + '&userName=' + userName);
        });
    };
    Organization.prototype.initWinback = function () {
        var that = this;
        var typename = "";
        var stagename = "";
        var schoolyear = "";
        var schoolId = '';

        $("#left_nav_ul").find("a").mouseenter(function (e) {
            if (!$(this).parent().hasClass('selected')) {
                $(this).find('menu-btns').find('a-btn-delete').removeClass('btn-delete-org');
                $(this).find('menu-btns').find('span.a-btn-delete').css('display', 'none');
            }
            var campusId = $(this).attr('id');

            $.post(that.url + "/Campus/getCampusInfo.do", {
                'campusId': campusId
            }, function (data) {
                if (data.code == 0) {
                    if (data.value != null) {
                        typename = data.value[0].typename;
                        stagename = data.value[0].stagename;
                        schoolyear = data.value[0].schoolyear;
                        schoolId = data.value[0].id;
                    }

                    var str = "<div class='winback_t'>" +
                        "<div class='winback_t_sj'>" +
                        "<span><p>机构类型：</p><span>" + typename + "</span></span>" +
                        "<span><p>机构编号：</p><span>" + schoolId + "</span></span>" +
                        //"<span><p>学段：</p><span>" + stagename + "</span></span>" +
                        // "<span><p>学年：</p><span>" + schoolyear + "</span></span>" +
                        "</div>" +
                        "</div>"
                    $("body").append(str);
                    var pointX = e.pageX;
                    var pointY = e.pageY;
                    $(".winback_t").css({"top": pointY + 30, "left": pointX})
                    setTimeout(function () {
                        $(".winback_t").remove()
                    }, 2500);
                }
            }, 'json');
        }).mouseleave(function () {
            $(".winback_t").remove();
        })

    }
    //添加组织机构
    Organization.prototype.initAdd = function () {
        var that = this;
        $('.btn-add-org').on('click', function () {
            layer.closeAll();
            $('.layui-layer-shade').remove();
            // 点击高级设置控制内容显示和隐藏

            var seniorSettingVisible = false;
            $('.schoolProps').hide();
            $('.senior-setting .title').on('click', function () {
                seniorSettingVisible = !seniorSettingVisible;
                if (seniorSettingVisible) {
                    $('.senior-setting .setting-content').show();
                } else {
                    $('.senior-setting .setting-content').hide();
                }
            })

            $("#orgLinkageArea .select-button").css('width','128px')
            $("#orgLinkagePro .select-button").removeAttr('disabled');
            $("#orgLinkagePro .select-button").css({'background':'#fff'});

            $("#orgLinkageCity .select-button").removeAttr('disabled');
            $("#orgLinkageCity .select-button").css({'background':'#fff'});

            $("#orgLinkageArea .select-button").removeAttr('disabled');
            $("#orgLinkageArea .select-button").css({'background':'#fff'});

            $("#winleix .select-button").removeAttr('disabled');
            $("#winleix .select-button").css({'background':'#fff'});
            // console.log($(this));

            var orgName = $(this).closest('a').children('h6').text();
            // alert(orgName)
            var stageId = $(this).closest('a').attr("stagesid");
            var typeId = $(this).closest('a').attr("typeid");
            if(typeId==6)
            {
                layer.msg("此机构下不允许添加任何机构");
                return false;
            }

            if(typeId==1 || typeId==2 || typeId==3){
                 $('.organization-name').attr("readonly","readonly");
            }
            $('.select-org').show();
            $('.organization-name').val('');
            $('#orgtype').show();
            $('.current-organization').text(orgName);//显示父机构名称
            $('.current-organization').attr("title",orgName);//显示当前机构类型
            $('.organization-name').val('');//要添加的机构名称为空

            $('#schoolyear').val('');//教学楼显示为空
            $('#totalstu').val('');//容纳人数显示为空
            $('#remark').val('');//备注显示为空
            $('#windidian').find("input").val(stageId);
            $('#winleix').find("input").val('');
            //$('#winleix .select-button').val('全部类型')//类型
            $('#ip').val(''); // ip
            $('#port').val(''); // 端口
            var stageName = (stageId == 1 ? "小学" : (stageId == 2 ? "初中" : (stageId == 3 ? "高中" : (stageId == 4 ? "院系" : ""))));
            $('#windidian .select-button').val(stageName)//校区类型
            var campusId = $(this).closest('a').attr("id");
            var campusTypeId = $(this).closest('a').attr("typeId");  //组织机构类型的id
            var level = 0;
            // $.post(that.url + "/Campus/getCampusInfo.do", {
            //     'campusId': campusId
            // }, function (data) {
            //     if (data.code == 0) {
            //         if (data.value != null) {
            //             level = data.value[0].level;
            //         }
            //         that.getType(level);
            //     }
            // }, 'json');
            console.log(typeId);
            if(typeId==1){
                $("#winleix").find("input").eq(1).val("省");
                $("#winleix").find("input").val("1");
                // var province = $("#orgLinkagePro").find("input").eq(1).val();
                $("#campusname").val(orgName);
            }

            // $("#orgLinkagePro").attr("disabled",true);
            var date=new Date();
            var hatprovince_id = 0;
            var hatcity_id = 0;
            var hatarea_id = 0;

            layer.open({
                type: 1,
                title: '添加机构',
                shadeClose: false,
                shade: 0.8,
                area: ["530px", "380px"],
                // area: ["510px", "540px"],
                content: $('.add-org'),
                btn: ["确定", "取消"],
                success: function() {
                    // that.orgSSQ();
                    // that.selectCity()
                    $("#orgLinkageArea").css("margin-left","5px");
                    $.post(that.url + "/Campus/getAreaListByCampusId.do", {
                        'userId': userId,
                        'campusId': campusId,
                        'data':date.getTime()
                    }, function (data) {

                        if (data.value[0] == undefined) {
                            hatprovince_id = 0
                            hatcity_id = 0;
                            hatarea_id = 0;
                        } else {
                            hatprovince_id = data.value[0].provinceID || 0;
                            hatcity_id = data.value[0].cityID || 0;
                            hatarea_id = data.value[0].areaID || 0;
                        }

                        console.log("hatprovince_id:" + hatprovince_id);
                        console.log("hatcity_id:" + hatcity_id);
                        console.log("hatarea_id:" + hatarea_id);

                        $('#orgLinkagePro').find('.select-list').find('li').each(function (i,item) {
                            // if (item.attributes[0].value != 0 && item.attributes[0].value==hatprovince_id) {
                            var province_id=$(this).attr("data-value"); //update by caoqian
                            // console.log(province_id);
                            if (province_id==hatprovince_id) {
                                console.log("$$$$$$$$$$$$"+hatprovince_id+"aaaaaaaaaaaa"+province_id);
                                if(hatprovince_id==0){
                                    $('#orgLinkagePro .select-button').attr("disabled",false);
                                    $('#orgLinkagePro i.icon').css('display', 'block');
                                }else{
                                    $('#orgLinkagePro .select-button').attr("disabled",true);
                                    $('#orgLinkagePro i.icon').css('display', 'none');
                                }
                                $('#orgLinkagePro').find("input").val(province_id);//类型id
                                $('#orgLinkagePro .select-button').val(item.innerHTML)//类型名称;
                                $('#campusname').attr("readonly","readonly");

                                $('#winleix .select-button').attr("disabled","true");

                                $('#orgLinkageCity i.icon').css('display', 'block');
                                $('#winleix i.icon').css('display', 'none');
                                // $("#campusname").val(item.innerHTML);
                                $.post(that.url + "/Campus/getCityList.do", {provinceId: hatprovince_id}, function (data) {
                                    var cityhtml = "";
                                    cityhtml += "<option value= '0'>请选择市</option>";
                                    $.each(data.value, function (ind, value) {
                                        var city = value["city"];
                                        var id = value["id"];
                                        cityhtml += "<option value='" + id + "'>" + city + "</option>"
                                    })
                                    $('#orgLinkageCity').html(cityhtml);


                                    var select = new SelectList('#orgLinkageCity', function (option) {
                                        $('.schoolProps').hide();
                                        $("#winleix").find("input").eq(1).val("市");
                                        $("#winleix").find("input").eq(0).val("2");
                                        var city = $("#orgLinkageCity").find("input").eq(1).val();
                                        $("#campusname").val(city);
                                        $('#winleix .select-button').attr("disabled",true);
                                        $('#winleix i.icon').css('display', 'none');

                                        var cityId = option.context.dataset.value;
                                        $.post(that.url + "/Campus/getCountyList.do", {cityId: cityId}, function (data) {
                                            var countyhtml = "";
                                            countyhtml += "<option value= '0'>请选择区/县</option>";
                                            $.each(data.value, function (ind, value) {
                                                var county = value["area"];
                                                var id = value["id"];
                                                countyhtml += "<option value='" + id + "'>" + county + "</option>"
                                            })
                                            $('#orgLinkageArea').html(countyhtml);
                                            select = new SelectList('#orgLinkageArea', function (option) {
                                                $('.schoolProps').hide();
                                                $("#winleix").find("input").eq(1).val("区/县");
                                                $("#winleix").find("input").eq(0).val("3");
                                                var area = $("#orgLinkageArea").find("input").eq(1).val();
                                                $("#campusname").val(area);
                                                console.log(area);
                                                $('#winleix .select-button').attr("disabled",false);
                                                $('#winleix i.icon').css('display', 'block');
                                                console.log("aaaaaaaaaaaaaaaa");
                                                $('#winleix .select-list ul li').removeClass('selected');
                                                $('#winleix .select-list ul li').eq(1).css('display','none');
                                                $('#winleix .select-list ul li').eq(2).css('display','none');
                                                // $('#winleix .select-list ul li').eq(3).css('display','none');
                                                $('#winleix .select-list ul li').eq(4).addClass('selected');
                                            });
                                            select.orgSSQ();
                                        }, 'json');

                                    });
                                    select.orgSSQ();
                                    // $('#orgLinkageArea ul').html("<li data-value='0' class='selected' style='height: 34px;'>请选择区县4</li>");
                                    // $('#orgLinkageArea input').val('请选择区县5');
                                    $('#orgLinkageCity').find('.select-list').find('li').each(function (i,item) {
                                        $('.schoolProps').hide();
                                        // if (item.attributes[0].value != 0 && item.attributes[0].value==hatcity_id) {
                                        var city_id=$(this).attr("data-value"); //update by caoqian
                                        console.log(hatprovince_id+"*************************");
                                        if (city_id==hatcity_id) {
                                            $('#orgLinkageCity').find("input").val(city_id);//类型id
                                            $('#orgLinkageCity').find('.select-button').val(item.innerHTML)//类型名称
                                            $("#campusname").val(item.innerHTML);
                                            // $('#orgLinkageCity i.icon').css('display', 'none');
                                            // $('#winleix i.icon').css('display', 'none');

                                            var cityId = hatcity_id;
                                            $.post(that.url + "/Campus/getCountyList.do", {cityId: cityId}, function (data) {
                                                // console.log(data, '66666666666666')
                                                var countyhtml = "";
                                                countyhtml += "<option value= '0'>请选择区/县</option>";
                                                $.each(data.value, function (ind, value) {
                                                    var county = value["area"];
                                                    var id = value["id"];
                                                    countyhtml += "<option value='" + id + "'>" + county + "</option>"
                                                })

                                                $('#orgLinkageArea').html(countyhtml);
                                                // console.log(countyhtml);
                                                select = new SelectList('#orgLinkageArea', function (option) {
                                                    $('.schoolProps').hide();
                                                    $("#winleix").find("input").eq(1).val("区/县");
                                                    $("#winleix").find("input").eq(0).val("3");
                                                    var area = $("#orgLinkageArea").find("input").eq(1).val();
                                                    $("#campusname").val(area);
                                                    console.log("aaaaaaaaaaaaaaaadxxxxxx");
                                                    $('#winleix i.icon').css('display', 'block');
                                                });
                                                select.orgSSQ();
                                                var numhatcity = 0;
                                                $('#orgLinkageArea').find('.select-list').find('li').each(function (i,item) {
                                                    $('.schoolProps').hide();
                                                    if(hatcity_id!==0&&numhatcity!==1){
                                                        numhatcity += 1;
                                                        $("#winleix").find("input").eq(1).val("市");
                                                        $("#winleix").find("input").eq(0).val("2");
                                                        var city = $("#orgLinkageCity").find("input").eq(1).val();
                                                        $("#campusname").val(city);
                                                        $('#orgLinkageCity .select-button').attr("disabled",true);
                                                        $('#orgLinkageCity i.icon').css('display', 'none');
                                                        $('#winleix i.icon').css('display', 'none');
                                                        $('#winleix .select-button').attr("disabled",false);
                                                        console.log("aaaaaaaaaaaaaaaa");
                                                    }

                                                    // console.log(item, item.attributes[0].value)
                                                    // if (item.attributes[0].value != 0 && item.attributes[0].value==hatarea_id) {
                                                    var area_id=$(this).attr("data-value");  //update by caoqian
                                                    if (area_id==hatarea_id) {
                                                        if(hatarea_id!==0){
                                                            $("#winleix").find("input").eq(1).val("区/县");
                                                            $("#winleix").find("input").eq(0).val("3");
                                                            var area = $("#orgLinkageArea").find("input").eq(1).val();
                                                            $("#campusname").val(area);
                                                            console.log("aaaaaaaaaaaaaaaadxxxxxx");
                                                            $('#orgLinkageArea .select-button').attr("disabled",true);
                                                            $('#winleix .select-button').attr("disabled",false);
                                                            $('#winleix i.icon').css('display', 'block');
                                                            $('#orgLinkageArea i.icon').css('display', 'none');
                                                            console.log("aaaaaaaaaaaaaaaa");

                                                        }

                                                        $('#orgLinkageArea').find("input").val(area_id);//类型id
                                                        $('#orgLinkageArea .select-button').val(item.innerHTML)//类型名称;

                                                        $("#campusname").val(orgName);
                                                         console.log(typeId+"@@"+province_id+"@@"+city_id+"@@"+area_id);
                                                         console.log(orgName);
                                                    }
                                                })
                                            }, 'json');
                                        }
                                    })
                                }, 'json')
                            }
                        });
                        if(campusId=="" || campusId=="undefined"){
                            $("#orgLinkageCity").css("display","none");
                            $("#orgLinkageArea").css("display","none");
                        }
                        if(campusTypeId=="1"){   //省，添加省下面的机构
                            $("#orgLinkageCity").css("display","block");
                            $("#orgLinkageArea").css("display","none");
                        }
                        if(campusTypeId=="2"){   //市，添加市区下面的机构
                            $("#orgLinkagePro").css("display","block");
                            $("#orgLinkageCity").css("display","block");
                            $("#orgLinkageArea").css("display","block");
                        }

                     //   console.log(campusId+"sssssssss"+campusTypeId+"dddddddddddd");
                        //-------------------------------------add by caoqian 地点选择省时，机构类型不显示省，依次类推 begin-------------------
                        $('#winleix input.select-button').val('全部类型');
                        if(hatprovince_id!='0' && hatcity_id=='0' && hatarea_id=='0'){
                            $('#winleix .select-list ul li').removeClass('selected');
                            $('#winleix .select-list ul li').eq(1).css('display','none');
                            $('#winleix .select-list ul li').eq(2).addClass('selected');
                            $('#winleix input.select-button').val('省');
                        }else if(hatprovince_id!='0' && hatcity_id!='0' && hatarea_id=='0'){
                            $('#winleix .select-list ul li').removeClass('selected');
                            $('#winleix .select-list ul li').eq(1).css('display','none');
                            $('#winleix .select-list ul li').eq(2).css('display','none');
                            $('#winleix .select-list ul li').eq(3).addClass('selected');
                        }else if(hatprovince_id!='0' && hatcity_id!='0' && hatarea_id!='0'){
                            $('#winleix .select-list ul li').removeClass('selected');
                            $('#winleix .select-list ul li').eq(1).css('display','none');
                            $('#winleix .select-list ul li').eq(2).css('display','none');
                            $('#winleix .select-list ul li').eq(3).css('display','block');
                            $('#winleix .select-list ul li').eq(4).addClass('selected');
                        }
                        //-------------------------------------add by caoqian  end-------------------
                    }, 'json');
                    $('#windidian input[name=windidian]').removeAttr('value');
                    $('#windidian input.select-button').val('全部类型');
                    $('#windidian .select-list ul li').removeClass('selected');
                    $('#windidian .select-list ul li').eq(0).addClass('selected');
                },
                end: function (index, layero) {
                    // 弹层关闭后隐藏高级设置内容，避免下次打开弹层显示高级设置内容
                    $('.senior-setting .setting-content').hide();
                },
                yes: function (index, layero) {
                    var provinceId = $('#orgLinkagePro').find("input[name='orgLinkagePro']").val();
                    var cityId = $('#orgLinkageCity').find("input[name='orgLinkageCity']").val();
                    var areaId = $('#orgLinkageArea').find("input[name='orgLinkageArea']").val();


                    if (provinceId == '0') {
                        layer.msg("请选择省");
                        return false;
                    }

                    var typeId = $('#winleix').find("input").val().trim();//类型
                    console.log("组织机构类型: " + typeId);
                    var campusname = $('.add-org').find('.organization-name').val().trim(); //组织名称
                    console.log("组织机构名称" + campusname)
                    var stagesId = $('#windidian').find("input").val().trim();//学段id
                    console.log("学段Id: " + stagesId);
                    var schoolyear = $('#schoolyear').val().trim();//学年
                    var number = $('#totalstu').val().trim();//容纳人数
                    var remark = $('#remark').val().trim();//备注
                    var campusIp = $('#ip').val().trim(); // ip
                    var campusPort = $('#port').val().trim(); // 端口

                    if(typeId=="1"){
                        cityId=0;
                        areaId =0;
                    }else if(typeId =="2"){
                        areaId = 0;
                    }
                    console.log(provinceId, cityId, areaId);

                    // if (provinceId == "0") {
                    //     layer.msg("请选择省");
                    //     return false;
                    // } else if (cityId == "0") {
                    //     layer.msg("请选择市");
                    //     return false;
                    // } else if (areaId == "0") {
                    //     layer.msg("请选择区县");
                    //     return false;
                    // }

                    if (campusname == "") {
                        layer.msg("请输入机构名称");
                        return false;
                    }
                    if (campusname.length > 45) {
                        layer.msg("机构名称请小于45个字符");
                        return false;
                    }
                    var pattern = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
                    if ((typeId==4 || typeId==5) && !pattern.test(campusname)){
                        layer.msg("学校名称只能输入中文、字母和数字");
                        return false;
                    }
                    if (remark!="" && !pattern.test(remark)){
                        layer.msg("备注内容只能输入中文、字母和数字");
                        return false;
                    }
                    if (typeId == "") {
                        layer.msg("请选择机构类型");
                        return false;
                    }
                    if(typeId=="4" || typeId=="5"){  //add by caoqian  选择学校或分校时学段必选
                        if (stagesId == "") {
                             layer.msg("请选择学段");
                             return false;
                        }
                    }

                    if (campusIp !== '' && campusIp.search(/\b(([01]?\d?\d|2[0-4]\d|25[0-5])\.){3}([01]?\d?\d|2[0-4]\d|25[0-5])\b/) === -1) {
                        layer.msg("请输入正确格式的ip");
                        return false;
                    }
                    if (campusPort !== '' && campusPort.search(/^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-5]{2}[0-3][0-5])$/) === -1) {
                        layer.msg("请输入正确格式的端口");
                        return false;
                    }
                    $.post(that.url + "/Campus/addCampus.do", {
                        'provinceId': provinceId,
                        'cityId': cityId,
                        'areaId': areaId,
                        'pId': campusId,
                        'name': campusname,
                        'typeId': typeId,
                        'stagesId': stagesId,
                        'schoolYear': schoolyear,
                        'number': number,
                        'remark': remark,
                        'campusIp': campusIp,
                        'campusPort': campusPort
                    }, function (data) {
                        layer.close(index);//执行完成先隐藏弹窗
                        if (data.code == 0) {
                            if(data.value == -1) {
                                layer.msg("添加组织机构失败");
                            }
                            else if (data.value == -2) {
                                layer.msg("该名称已存在");
                            }
                            else if(data.value == -3){
                                layer.msg("已经存在该ip的组织机构");
                            }
                            else if(data.value == -4){
                                layer.msg("已经存在省级机构,不能重复添加");
                            }
                            else if(data.value == -5){
                                layer.msg("已经存在该市级机构,不能重复添加");
                            }
                            else{
                                layer.msg("添加组织机构成功");
                                console.log(data.value);
                                that.initData(data.value);//刷新左侧树
                            }
                        }
                        else {
                            layer.msg("添加组织机构失败");
                        }
                    }, 'json');
                }
            });
            return false;//点击此按钮时不触发上一级事件
        });
    }

    //修改组织机构
    Organization.prototype.initEdit = function () {
        var that = this;
       // console.log("修改Organization.prototype.initEdit");
        $('.btn-edit-org').on('click', function () {

            $('.layui-layer-shade').remove();
            // 点击高级设置控制内容显示和隐藏
            var seniorSettingVisible = false;
            $('.senior-setting .title').on('click', function () {
                seniorSettingVisible = !seniorSettingVisible;
                if (seniorSettingVisible) {
                    $('.senior-setting .setting-content').show();
                } else {
                    $('.senior-setting .setting-content').hide();
                }
            })


            $('.select-org').hide();
            $('#orgtype').show();
            var orgName = $(this).closest('a').children('h6').text();
            var campusId = $(this).closest('a').attr("id");
            var pId = $(this).parents("ul").prev('a').attr("id");
            if (!pId) {
                pId = 0;
            }
            var h6 = $(this).closest('a').children('h6')
            // 获取全部机构类型
            var level= TOPLEVEL;
            that.getType(level);
            $('.orgSSQbj').show();
            layer.open({
                type: 1,
                title: '编辑机构',
                shadeClose: false,
                area: ["530px", "380px"],
                // area: ["510px", "505px"],
                shade: 0.8,
                content: $('.add-org'),
                btn: ["确定", "取消"],
                end: function (index, layero) {
                    // 弹层关闭后隐藏高级设置内容，避免下次打开弹层显示高级设置内容
                    $('.orgSSQbj').hide();

                    $('.senior-setting .setting-content').hide();
                    $("#orgLinkagePro .select-button").removeAttr('disabled');
                    $("#orgLinkagePro .select-button").css({'background':'#fff'});

                    $("#orgLinkageCity .select-button").removeAttr('disabled');
                    $("#orgLinkageCity .select-button").css({'background':'#fff'});

                    $("#orgLinkageArea .select-button").removeAttr('disabled');
                    $("#orgLinkageArea .select-button").css({'background':'#fff'});

                    $("#winleix .select-button").removeAttr('disabled');
                    $("#winleix .select-button").css({'background':'#fff'});
                },
                yes: function (index, layero) {
                    $('.orgSSQbj').hide();

                    //var typeId = $('.current-orgtype').attr('id');//类型
                    var typeId =  $('#winleix').find("input").val().trim(); //类型id
                    var campusname = $('.add-org').find('.organization-name').val().trim(); //组织名称
                    var stagesId = $('#windidian').find("input").val().trim();//学段id
                    var schoolyear = $('#schoolyear').val().trim();//学年
                    var number = $('#totalstu').val().trim();//容纳人数
                    var remark = $('#remark').val().trim();//备注

                    var campusIp = $('#ip').val().trim(); // ip
                    var campusPort = $('#port').val().trim(); // 端口

                    // if (provinceId == "0") {
                    //     layer.msg("请选择省")
                    //     return false;
                    // } else if (cityId == "0") {
                    //     layer.msg("请选择市")
                    //     return false;
                    // } else if (areaId == "0") {
                    //     layer.msg("请选择区县")
                    //     return false;
                    // }


                    if (campusname == "") {
                        layer.msg("组织机构名称不能为空");
                        return false;
                    }
                    if (campusname.length > 45) {
                        layer.msg("机构名称请小于45个字符");
                        return false;
                    }
                    var pattern = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
                    if ((typeId==4 || typeId==5) && !pattern.test(campusname)){
                        layer.msg("学校名称只能输入中文、字母和数字");
                        return false;
                    }
                    if (remark!="" && !pattern.test(remark)){
                        layer.msg("备注内容只能输入中文、字母和数字");
                        return false;
                    }
                    if (typeId == "") {
                        layer.msg("请选择机构类型");
                        return false;
                    }
                    // if (stagesId == "") {
                    //     layer.msg("请选择学段");
                    //     return false;
                    // }
                    if (campusIp !== '' && campusIp.search(/\b(([01]?\d?\d|2[0-4]\d|25[0-5])\.){3}([01]?\d?\d|2[0-4]\d|25[0-5])\b/) === -1) {
                        layer.msg("请输入正确格式的ip");
                        return false;
                    }
                    if (campusPort !== '' && campusPort.search(/^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-5]{2}[0-3][0-5])$/) === -1) {
                        layer.msg("请输入正确格式的端口");
                        return false;
                    }
                    // console.log($('.selected').find('li').size());
                    $.post(that.url + "/Campus/updateCampus.do", {
                        'id': campusId,
                        'pId': pId,
                        'name': campusname,
                        'typeId': typeId,
                        'stagesId': stagesId,
                        'schoolYear': schoolyear,
                        'number': number,
                        'remark': remark,
                        'campusIp': campusIp,
                        'campusPort': campusPort
                    }, function (data) {
                        layer.close(index);//执行完成先隐藏弹窗
                        if (data.code == 0) {
                            if (data.value == -2) {
                                layer.msg("该名称已存在");

                            }
                            else{
                                layer.msg("修改组织机构完成");
                                setTimeout(function () {
                                    // h6.text(campusname);//修改组织名称并刷新
                                    //刷新页面
                                    window.location.reload();
                                },1000)
                            }
                        }
                        else {
                            layer.msg("修改组织机构失败");
                        }
                    }, 'json');
                    $('.senior-setting .setting-content').hide();
                    $("#orgLinkagePro .select-button").removeAttr('disabled');
                    // $("#orgLinkagePro .select-button").css({'background':'#fff'});

                    $("#orgLinkageCity .select-button").removeAttr('disabled');
                    // $("#orgLinkageCity .select-button").css({'background':'#fff'});

                    $("#orgLinkageArea .select-button").removeAttr('disabled');
                    // $("#orgLinkageArea .select-button").css({'background':'#fff'});

                    $("#winleix .select-button").removeAttr('disabled');
                    // $("#winleix .select-button").css({'background':'#fff'});
                }
            });

            $.post(that.url + "/Campus/getCampusInfo.do", {
                'campusId': campusId
            }, function (data) {
                if (data.code == 0) {
                    $.post(that.url + "/Campus/getAreaListByCampusId.do", {
                        'campusId': campusId,
                        'userId': userId
                    }, function (data) {
                        var hatprovince_id = data.value[0].provinceID || 0;
                        var hatcity_id = data.value[0].cityID || 0;
                        var hatarea_id = data.value[0].areaID || 0;

                        console.log("hatprovince_id: " + hatprovince_id);
                        console.log("hatcity_id: " + hatcity_id);
                        console.log("hatarea_id: " + hatarea_id);
                        $('#orgLinkagePro i.icon').css('display', 'none');
                        $('#orgLinkageCity i.icon').css('display', 'none');
                        $('#winleix i.icon').css('display', 'none');

                        $('#orgLinkagePro').find('.select-list').find('li').each(function (i,item) {
                            // if (item.attributes[0].value != 0 && item.attributes[0].value==hatprovince_id) {
                            var province=$(this).attr("data-value");
                            if (province==hatprovince_id) {
                                $('#orgLinkagePro').find("input").val(province);//类型id
                                $('#orgLinkagePro .select-button').val(item.innerHTML)//类型名称;
                                $('#orgLinkagePro i.icon').css('display', 'none');
                                $.post(that.url + "/Campus/getCityList.do", {provinceId: hatprovince_id}, function (data) {
                                    var cityhtml = "";
                                    cityhtml += "<option value= '0'>请选择市</option>";
                                    $.each(data.value, function (ind, value) {
                                        var city = value["city"];
                                        var id = value["id"];
                                        cityhtml += "<option value='" + id + "'>" + city + "</option>"
                                    })
                                    // alert(cityhtml);
                                    $('#orgLinkageCity').html(cityhtml);

                                    var select = new SelectList('#orgLinkageCity', function (option) {
                                        var cityId = option.context.dataset.value;
                                        $.post(that.url + "/Campus/getCountyList.do", {cityId: cityId}, function (data) {
                                            var countyhtml = "";
                                            countyhtml += "<option value= '0'>请选择区/县</option>";
                                            $.each(data.value, function (ind, value) {
                                                var county = value["area"];
                                                var id = value["id"];
                                                countyhtml += "<option value='" + id + "'>" + county + "</option>"
                                            })
                                            $('#orgLinkageArea').html(countyhtml);
                                            select = new SelectList('#orgLinkageArea', function (option) {
                                            });
                                            select.orgSSQ();
                                        }, 'json');
                                    });
                                    select.orgSSQ();
                                    $('#orgLinkageCity').find('.select-list').find('li').each(function (i,item) {
                                        // if (item.attributes[0].value != 0 && item.attributes[0].value==hatcity_id) {
                                        var city=$(this).attr("data-value");
                                        if (city==hatcity_id) {
                                            $('#orgLinkageCity').find("input").val(city);//类型id
                                            $('#orgLinkageCity').find('.select-button').val(item.innerHTML)//类型名称;
                                            $('#orgLinkageCity i.icon').css('display', 'none');

                                            var cityId = hatcity_id;
                                            $.post(that.url + "/Campus/getCountyList.do", {cityId: cityId}, function (data) {
                                                var countyhtml = "";
                                                countyhtml += "<option value= '0'>请选择区/县</option>";
                                                $.each(data.value, function (ind, value) {
                                                    var county = value["area"];
                                                    var id = value["id"];
                                                    countyhtml += "<option value='" + id + "'>" + county + "</option>"
                                                })
                                                $('#orgLinkageArea').html(countyhtml);
                                                select = new SelectList('#orgLinkageArea', function (option) {
                                                });
                                                select.orgSSQ();
                                                $('#orgLinkageArea').find('.select-list').find('li').each(function (i,item) {
                                                    // console.log("-----" + item.attributes[0].value)
                                                    // if (item.attributes[0].value != 0 && item.attributes[0].value==hatarea_id) {
                                                    var area=$(this).attr("data-value");
                                                    if (area==hatarea_id) {
                                                        $('#orgLinkageArea').find("input").val(area);//类型id
                                                        $('#orgLinkageArea .select-button').val(item.innerHTML)//类型名称;
                                                        $('#orgLinkageArea i.icon').css('display', 'none');
                                                    }
                                                })
                                            }, 'json');
                                        }
                                    })
                                }, 'json')
                            }
                        });
                    }, 'json');
                    var typename = data.value[0].typename;
                    var stagename = data.value[0].stagename;
                    var number = data.value[0].number;
                    var level = data.value[0].level;
                    var name = data.value[0].name;
                    var pId = data.value[0].pId;
                    var typeId = data.value[0].typeId;
                    var remark = data.value[0].remark;
                    var camupusid = data.value[0].id;
                    var schoolyear = data.value[0].schoolyear;
                    var stagesid = data.value[0].stagesId;
                    var url = data.value[0].url;

                    var campusIp = '';
                    var campusPort = '';
                    if (url && url !== ':') {
                        var matchArr = url.match(/[\d.]+/g);
                        campusIp = matchArr[0];
                        campusPort = matchArr[1];
                    }
                    if (stagename == "") {
                        stagename = "全部类型";
                    }

                    $('.current-orgtype').text(typename);//显示当前机构类型
                    $('.current-orgtype').attr('id', typeId);
                    $('#windidian').find("input").val(stagesid);//学段id
                    $('#windidian .select-button').val(stagename)//学段名称
                    $('.organization-name').val(name);//组织名称
                    $('#schoolyear').val(schoolyear);//学年
                    $('#totalstu').val(number);//容纳人数
                    $('#remark').val(remark);//备注

                    $('#winleix').find("input").val(typeId);//类型id
                    $('#winleix .select-button').val(typename)//类型名称
                    sessionStorage.setItem('typeId',typeId);
                    sessionStorage.setItem('typeName',typename);
                    if(typeId == '4'||typeId == '5'){
                        $('.schoolProps').show();
                    }else{
                        $('.schoolProps').hide();
                    }
                    $('#ip').val(campusIp);     // ip
                    $('#port').val(campusPort); // 端口

                    setTimeout(function () {
                        $("#orgLinkagePro .select-button").attr({'disabled':'disabled'});
                        // $("#orgLinkagePro .select-button").css({'background':'#e3e3e3'});

                        $("#orgLinkageCity .select-button").attr({'disabled':'disabled'});
                        // $("#orgLinkageCity .select-button").css({'background':'#e3e3e3'});

                        $("#orgLinkageArea .select-button").attr({'disabled':'disabled'});
                        // $("#orgLinkageArea .select-button").css({'background':'#e3e3e3'});

                        $("#winleix .select-button").attr({'disabled':'disabled'});
                        // $("#winleix .select-button").css({'background':'#e3e3e3'});

                    },500)


                }
            }, 'json');
            return false;//点击此按钮时不触发上一级事件
        });
    }



    //删除组织机构
    Organization.prototype.initDelete = function () {
        // alert('sssss')
        var that = this;
        $('.org-menu .btn-delete-org').on('click', function () {
            // alert('sssss')
            $('.layui-layer-shade').remove();
            var current = $(this);
            console.log($(this));
            console.log($(this).parent('a'));

            var id = $(this).parent('div').parent('a').attr('id');
            // console.log($(this).parent('div').parent('a').attr('id'));
            layer.open({
                type: 1,
                title: '删除机构',
                shadeClose: false,
                shade: 0.8,
                area: ["350px", "180px"],
                content: $('.m-delete'),
                btn: ["确定", "取消"],
                end: function (index, layero) {

                },
                yes: function (index, layero) {
                    // console.log($('#left_nav_ul').find('ul').find('li[class="selected"]').find('ul'));
                    // console.log("length: " + $('#left_nav_ul').find('ul').find('li[class="selected"]').find('ul').length);
                    // 如果机构下有子机构, 则提示不能删除
                    // if ($('li[class="selected"]').find('ul').find('li').length > 0) {
                    //     layer.close(index);//执行完成先隐藏弹窗
                    //     layer.msg("该机构下存在子机构不能删除!");
                    //     return false;
                    // }
                    console.log(id);
                    $.post(that.url + "/Campus/findChildCampusById.do", {
                        'id': id
                    }, function (data) {
                        console.log("----" + data.value);
                        if (data.value > 0) {
                            layer.msg("此机构下存在子机构, 请勿删除!");
                            layer.close(index);
                            return false;
                        }

                        var ids = "";
                        current.closest("li").find("a").each(function () {
                            ids = ids + $(this).attr("id") + ",";
                        });
                        ids = ids.substring(0, ids.length - 1);
                        $.post(that.url + "/Campus/deleteCampus.do", {
                            'id': ids
                        }, function (data) {
                            layer.close(index);//执行完成先隐藏弹窗
                            if (data.code == 0) {
                                if (data.value=="1") {
                                    layer.msg("删除成功");
                                    current.closest("li").remove();
                                    that.user.clear();
                                    console.log($("#left_nav_ul").find("li").length);
                                    if ($("#left_nav_ul").find("li").length == 0) {
                                        // $('.org-menu').hide();
                                        $('.btnAddOrg').show();
                                    }
                                } else if(data.value=="2"){
                                    layer.msg("请先删除机构下的用户");
                                } else if (data.value=="3") {
                                    layer.msg("该组织机构下的有关联地点, 请先删除关联的地点");
                                } else {
                                    layer.msg("删除失败");
                                }
                                layer.close(index);
                            }
                            else {
                                layer.msg("删除失败");
                                layer.close(index);
                            }
                        }, 'json');
                        layer.close(index);
                    },'json');
                }

            });

            return false;
        });
    }
    //初始化
    Organization.prototype.initMenu = function () {
        var that = this;
        require('cxMenu/jquery.cxmenu');

        $('#left_nav_ul').cxMenu();

        //将左侧选中的分类，显示到右侧顶部
        $('#left_nav_ul li a').on('click', function () {
            $('#left_nav_ul li ').find("a").attr("style", "");
            $('#left_nav_ul li ').find("a").attr("name", "");
            $(this).attr('style',"background: #eaedf1");
            $(this).attr('name',"thiscampus");
            var campusId =$("a[name = 'thiscampus']").attr('id');
            var list = $("a[name = 'thiscampus']").find('h6').text();
            var namelist = that.middle($("a[name = 'thiscampus']"),list)
            var name =namelist.split(",")
            name.reverse();
            var title ="";
            for (var i = 0; i < name.length; i++) {
                 title += name[i] + ' > ';
            }
            $('.right-top > p').text(title + ' 人员');

            var type = $("a[name = 'thiscampus']").attr('typeid')
                $('.btn-allot').attr("style", "display:none");
                $('.btn-allot_student').attr("style", "display:none");
                $('.btn-allot_teacher').attr("style", "display:none");
                $('.btn-allot_director').attr("style", "display:none");
                $('.btn-allot_rector').attr("style", "display:none");
                if (type == 1) {
                    $('.btn-allot_rector').attr("style", "");
                } else if (type == 2) {
                    $('.btn-allot_rector').attr("style", "");
                } else if (type == 3) {
                    $('.btn-allot_director').attr("style", "");
                } else if (type == 4 || type == 7) {
                    $('.btn-allot_teacher').attr("style", "");
                    $('.btn-allot_student').attr("style", "");
                } else if (type == 5) {
                    $('.btn-allot_teacher').attr("style", "");
                } else if (type == 6) {
                    $('.btn-allot_teacher').attr("style", "");
                } else {
                    $('.btn-allot').attr("style", "");
                }
            that.user.init(campusId);
        });
    }
    Organization.prototype.initUpload = function () {
        $("#campus_upload").on("change", function () {
            var file = $("#campus_upload");
            var holeName = $("#campus_upload").val();
            var name = holeName.split("\\");
            var fileName = name[name.length - 1];
            $("#campus_upload_fileName").val(fileName)
        })
    }

    Organization.prototype.initDownloadCampus = function () {
        var that = this;
        $('.top-xz').on('click', function () {
            window.location.href = that.url + "/Campus/campusDownloadMould.do";//下载组织结构模版
        });
    }

    Organization.prototype.initImportCampus = function () {
        var that = this;
        $('.top-dr').on('click', function () {
            $("#campus_upload_fileName").val("");
            $("#campus_upload").val("");
            layer.open({
                type: 1,
                title: '导入机构',
                shadeClose: false,
                shade: 0.8,
                content: $('.campus-leading'),
                btn: ["确定", "取消"],
                end: function (index, layero) {
                },
                yes: function (index, layero) {
                    var file = $("#campusUploadFile");
                    var fileName = $("#campus_upload_fileName").val();
                    var extension = (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)).toLowerCase();//截取文件后缀名
                    if (extension == "xls" || extension == "xlsx") {
                        var options = {
                            url: that.url + '/Campus/campusUpload.do',
                            type: 'post',
                            dataType: 'json',
                            success: function (data) {
                                layer.close(index);
                                if (data.result == "success") {
                                    layer.msg("导入完成");
                                    that.initData('');
                                } else if (data.result == "program_error") {
                                    layer.msg("导入程序错误");
                                } else if (data.result == "data_error") {
                                    layer.msg("数据错误");
                                }
                            }
                        };
                        file.ajaxSubmit(options);
                    } else {

                        layer.msg("请选择xls或xlsx格式文件");
                        return false;
                    }
                }

            });
        });
    }

    //获取学段
    Organization.prototype.getStages = function () {
        var that = this;
        $.post(that.url + "/Campus/getStages.do", {}, function (data) {
            var html = "";
            html += "<option value= ''>全部类型</option>"
            $.each(data.value, function (ind, value) {
                //后台获取已有设备
                var stagename = value["name"];
                var stageid = value["id"];
                html += "<option value='" + stageid + "'>" + stagename + "</option>"
            })

            html += " </select>"
            $('#windidian').html(html);

            var select = new SelectList('#windidian', function (option) {
            });
            select.campusSelect();

        }, 'json');
    }

     var aname ="";

    //获取机构类型
    Organization.prototype.getType = function (level) {
        var that = this;
        $.post(that.url + "/Campus/getCampusType.do", {'level': level}, function (data) {
            var campusName=sessionStorage.getItem('typeName'); //机构类型名称
            var campusId=sessionStorage.getItem('typeId');  //机构类型id
            var html = "";
            if(!!campusName && !!campusId){
                html += "<option value= '" + campusId + "'>"+campusName+"</option>"
            }else {
                html += "<option value= ''>全部类型</option>"
                $.each(data.value, function (ind, value) {
                    var typename = value["name"];
                    var typeid = value["id"];
                    html += "<option value='" + typeid + "'>" + typename + "</option>"
                })
            }
            html += " </select>"
            $('#winleix').html(html);

            var select = new SelectList('#winleix', function (option) {
                console.log(option.context.dataset.value)
                var val = option.context.dataset.value;
                var campusname = $("#campusname").val();
                console.log(campusname);
                if(val == '4' || val === '5'){
                    $(".organization-name").removeAttr("readOnly");
                    $(".organization-name").val('');
                    $('.schoolProps').show();
                }else{
                    $('.organization-name').attr("readonly","readonly");
                    $('.schoolProps').hide();
                    var area = $("#orgLinkageArea").find("input").eq(1).val();
                    $(".organization-name").val(area);
                }
            });
            select.campusSelect();
            document.getElementById("winleix").style.zIndex = "51";
        }, 'json');
    }

    //切换列表显示和图标显示
    Organization.prototype.switchmode = function () {
        $(document).on("click", ".right-top > span", function () {
            var flag = $(this).attr("class");
            if (flag == "sprt-list") {
                $(this).css("opacity", "1"); //选中时当前图标变深
                $(".sprt-tab").css("opacity", ".5");//未选中图标变浅
                $(".devive-list-ls").show();//显示列表
                $(".user-list").hide();//隐藏图标
                $('.user-list li .headimg').siblings('.head-select').hide(); //切换时取消选中图标
                $('.user-list li .headimg').parents('li').removeClass('member-selected');//切换时取消选中图标
            } else if (flag == "sprt-tab") {
                $(this).css("opacity", "1");//选中时当前图标变深
                $(".sprt-list").css("opacity", ".5");//未选中图标变浅
                $(".devive-list-ls").hide();//隐藏列表
                $(".user-list").show();//显示图标
                $('.m-check-all').removeClass('m-checked');//切换时取消选中设备
                $('.m-check').removeClass('m-checked');//切换时取消选中设备
            }
        })
    }

    // 切换tab
    Organization.prototype.changeTab = function () {
        $('.campus-tab ul li').on('click', function () {
            $(this).addClass('current').siblings().removeClass('current');
            var key = $(this).attr('key');
            $('.content >div').hide();
            $('.content >div.'+ key +'-content').show();
        })
    }

    // 点击组织机构树
    Organization.prototype.onClickCampus = function () {
        setTimeout(function () {
            $('#left_nav_ul >li >a').trigger('click');
        }, 600);

        $('#left_nav_ul').find('a').on('click', function () {

            // $(this).siblings().removeClass('selected');
            //$('.campus-tab ul li.teacher').trigger('click');
            //组织机构id
            var campusId = $(this).attr('id');
            //学段id
            var stageId = $(this).attr('stageid');
            sessionStorage.setItem('campusId', campusId);
            sessionStorage.setItem('stageId', stageId);
			setTimeout(function () {
                $(this).parents('li').removeClass('selected');
                $(this).next('ul').find('li').removeClass('selected');
                // 03-02 注释
                $("#teacher-iframe")[0].contentWindow.initTeacherList(campusId);
                // 03-02 注释
               // $("#student-iframe")[0].contentWindow.initStudentList(campusId);
                // 03-02 注释
                //$("#parent-iframe")[0].contentWindow.initParentList(campusId);
            },500)
        })
    }


    // 省市区select初始化 add mz 区域化用户服务新增
     Organization.prototype.orgSSQ = function () {
        console.log("8faaffffffffffff");
         var that = this;
        $.post(that.url + "/Campus/getAreaList.do", {userId: userId}, function (data) {
            // --------------------------------------------拼接省 start---------------------------------
            var html = "";
            html += "<option value= '0'>请选择省</option>";
            $.each(data.value, function (ind, value) {
                var province = value["province"];
                var id = value["id"];
                html += "<option value='" + id + "'>" + province + "</option>"
            })
            html += " </select>"
            $('#orgLinkagePro').html(html);
            $('.schoolProps').hide();

            // --------------------------------------------拼接省 end ---------------------------------

            // --------------------------------------------拼接市 start--------------------------------
            var select = new SelectList('#orgLinkagePro', function (option) {

                // that.getType(0);
                $("#winleix").find("input").eq(1).val("省");
                $("#winleix").find("input").eq(0).val("1");
                var province = $("#orgLinkagePro").find("input").eq(1).val();
                $("#campusname").val(province);
                var provinceId = option.context.dataset.value;
                // console.log(province);
                $('.schoolProps').hide();
                $.post(that.url + "/Campus/getCityList.do", {provinceId: provinceId}, function (data) {
                    var cityhtml = "";
                    cityhtml += "<option value= '0'>请选择市</option>";
                    $.each(data.value, function (ind, value) {
                        var city = value["city"];
                        var id = value["id"];
                        cityhtml += "<option value='" + id + "'>" + city + "</option>"

                    });
                    $('#orgLinkageCity').html(cityhtml);
                    // --------------------------------------------拼接市 end--------------------------------

                    // --------------------------------------------拼接区县 start--------------------------------
                    select = new SelectList('#orgLinkageCity', function (option) {

                        $("#winleix").find("input").eq(1).val("市");
                        $("#winleix").find("input").eq(0).val("2");
                        var city = $("#orgLinkageCity").find("input").eq(1).val();
                        $("#campusname").val(city);
                        $('#winleix .select-button').attr("disabled","false");

                        $('.schoolProps').hide();
                        //console.log("456789");
                        var cityId = option.context.dataset.value;
                        $.post(that.url + "/Campus/getCountyList.do", {cityId: cityId}, function (data) {
                            var countyhtml = "";
                            countyhtml += "<option value= '0'>请选择区/县</option>";
                            $.each(data.value, function (ind, value) {
                                var county = value["area"];
                                var id = value["id"];
                                countyhtml += "<option value='" + id + "'>" + county + "</option>"
                            })
                            $('#orgLinkageArea').html(countyhtml);
                            select = new SelectList('#orgLinkageArea', function (option) {
                                $('.schoolProps').hide();
                                $("#winleix").find("input").eq(1).val("区/县");
                                $("#winleix").find("input").eq(0).val("3");
                                var area = $("#orgLinkageArea").find("input").eq(1).val();
                                $("#campusname").val(area);
                                $('#winleix .select-button').attr("disabled",false);
                                $('#winleix i.icon').css('display', 'block');
                                $('#winleix .select-list ul li').removeClass('selected');
                                $('#winleix .select-list ul li').eq(1).css('display','none');
                                $('#winleix .select-list ul li').eq(2).css('display','none');
                                $('#winleix .select-list ul li').eq(3).css('display','block');
                                $('#winleix .select-list ul li').eq(4).addClass('selected');

                            });
                            select.orgSSQ();
                        }, 'json');
                    });
                    select.orgSSQ();
                    // --------------------------------------------拼接区县 end--------------------------------
                }, 'json')
            });
            select.orgSSQ();
        }, 'json');
        var select1 = new SelectList('#orgLinkageCity', function (option) {
            $("#winleix").find("input").eq(1).val("市");
            $("#winleix").find("input").eq(0).val("2");
            var city = $("#orgLinkageCity").find("input").eq(1).val();
            $("#campusname").val(city);


            //console.log("456789");
            var cityId = option.context.dataset.value;
            $.post(that.url + "/Campus/getCountyList.do", {cityId: cityId}, function (data) {
                var countyhtml = "";
                countyhtml += "<option value= '0'>请选择区/县</option>";
                $.each(data.value, function (ind, value) {
                    var county = value["area"];
                    var id = value["id"];
                    countyhtml += "<option value='" + id + "'>" + county + "</option>"
                })
                $('#orgLinkageArea').html(countyhtml);
                select = new SelectList('#orgLinkageArea', function (option) {
                    $("#winleix").find("input").eq(1).val("区/县");
                    $("#winleix").find("input").eq(0).val("3");
                    var area = $("#orgLinkageArea").find("input").eq(1).val();
                    $("#campusname").val(area);
                });
                select.orgSSQ();
            }, 'json');
        });
        select1.orgSSQ();

         select2 = new SelectList('#orgLinkageArea', function (option) {
            $("#winleix").find("input").eq(1).val("区/县");
            $("#winleix").find("input").eq(0).val("3");
            var area = $("#orgLinkageArea").find("input").eq(1).val();
            $("#campusname").val(area);
        });
        select2.orgSSQ();

     };


    // Organization.prototype.selectCity = function () {
    //     var select = new SelectList('#orgLinkageCity', function (option) {
    //         console.log(555555)
    //     });
    //     // select()
    // }

});