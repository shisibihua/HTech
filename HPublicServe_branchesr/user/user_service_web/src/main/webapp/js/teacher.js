define('teacher/teacher', function (require, exports, module) {
    var $ = require('jquery');
    var Pagination = require('pagination/pagination');
    var SelectList = require('select-list/select');
    var Config = require('configs/configs');
    var ERROR_SAME = -5; //与其他数据重复
    var ERROR = -1;//失败
    var ERROR_NOOBJ =-4;//对象不存在
    require('calendar/cxcalendar');
    require('jquery/jquery-form');
    require('libs/layer/layer');
    module.exports = {
        init: function (storageIp, storagePort) {
            this.url = location.href.substring(0, location.href.lastIndexOf("/"));
            this.storageIp = storageIp;
            this.storagePort = storagePort;
            this.pageNumber = 7;
            this.searchWord = "";
            this.addTeacher();
            this.initSearch();
            //this.initTeacherList(1);
            this.initUpload();
            this.initLeadingIn();
            this.initLeadingOut();
            this.initDownloadMould();
            this.initUrls();
            this.previews();
            // this.initHeadImage();
            // this.initAddHeadImage();
            this.closedates();
        },

        closedates: function(){

            $(document).on("click","#userBirthday,#userStartWork,#e_userBirthday,#e_userStartWork,.year,.month,.y,.m",function(){
                $(this).unbind("click");
                $(document).bind("click",function(){
                    $(".cxcalendar").hide();
                    $('.cxcalendar_lock').hide();
                })
                return false
            })
        },
        initUrls: function () {
            var userName = $('.user').find('a').first().text();
            $('.nav').find('a').each(function () {
                $(this).attr('href', $(this).attr('href') + '&userName=' + userName);
            });
            $("#genders> span >span").click(function(){
                $("#genders> span >span").removeClass("color-gree").find("i").remove();
                $(this).addClass("color-gree").append("<i></i>")
            });
            $("#genderss> span >span").click(function(){
                $("#genderss> span >span").removeClass("color-gree").find("i").remove();
                $(this).addClass("color-gree").append("<i></i>")
            });
            $("#work> span >span").click(function(){
                $("#work> span >span").removeClass("color-gree").find("i").remove();
                $(this).addClass("color-gree").append("<i></i>")
            });
            $("#works> span >span").click(function(){
                $("#works> span >span").removeClass("color-gree").find("i").remove();
                $(this).addClass("color-gree").append("<i></i>")
            });

        },


        //添加教师  rewrite on 2017.1.9
        addTeacher: function () {
            var that = this;
            var userId = sessionStorage.getItem('userId');
            // console.log(userId);

            $('.btn-add-users').on('click', function () {
                that.initGetSubject();
                that.initGetDuty();
                that.initGetStage();
                $(".add-users").find("img").attr("src", "./images/userimg.png");
                $("#userRealName").val("");
                $("#teacherNamePy").val("");
                $("#userNum").val("");
                $("#userMobile").val("");
                $("#userEmail").val("");
                $("#identity").val("");
                $("#userShortMobile").val("");
                $("#userQQ").val("");
                $("#userBirthday").val("");
                $("#userAddress").val("");
                $("#userStartWork").val("");
                $("#userWorkYear").val("");
                //初始化单选按钮
                $(".select-sf> span >span").removeClass("color-gree").find("i").remove();
                $('#11').addClass("color-gree").append("<i></i>")
                $('#111').addClass("color-gree").append("<i></i>")
                //初始化下拉框
                $('#political').find("input").val('党员');
                $('#select-nation').find("input").val('汉族');
                $('#qualification').find("input").val(5);
                $('#political .select-button').val('党员')
                $('#select-nation .select-button').val('汉族')
                $('#qualification .select-button').val('正高级教师')
                console.log("********************************");
                console.log(sessionStorage.getItem('campusId'));
                console.log(sessionStorage.getItem('stageId'));

                layer.open({
                    type: 1,
                    title: '添加教师',
                    shadeClose: false,
                    offset: ['100px', '100px'],
                    shade: 0.8,
                    content: $('.add-users'),
                    area: ['410px', '520px'],
                    btn: ["确定", "取消"],
                    success: function () {
                        $('#layer', window.parent.document).show();
                        // 获取机构树数据
                        $.post(that.url + "/Campus/campusTree.do", {
                            'userId': userId,
                        }, function (data) {
                            that.initCampusTree(JSON.parse(data));  // 初始化机构树

                        })
                    },
                    end: function (index, layero) {
                        $('#layer', window.parent.document).hide();
                        $('.cxcalendar').hide();
                        $('.cxcalendar_lock').hide();

                        that.campus = '';   // 清除分配的机构
                    },
                    yes: function (index, layero) {
                        $('.cxcalendar').hide();
                        $('.cxcalendar_lock').hide();
                        var userGender = $('#genders').find('.color-gree').attr("value");//性别
                        var isJob = $('#work').find('.color-gree').attr("value");//是否在职
                        var userRealName = $("#userRealName").val().trim();//姓名
                        var teacherNamePy = $("#teacherNamePy").val().trim();//姓名拼音
                        var employNo = $("#userNum").val().trim();//工号
                        var userMobile = $("#userMobile").val().trim();//电话
                        var userEmail = $("#userEmail").val().trim();//邮箱
                        var userShortMobile = $("#userShortMobile").val().trim();//邮箱
                        var qq = $("#userQQ").val().trim();//qq
                        var identity = $("#identity").val().trim();//身份证号
                        var stage = $('#stage').find("input").val().trim();//学段id
                        var subject = $('#subject').find("input").val().trim();//科目id
                        var political = $('#political').find("input").val().trim();//政治面貌
                        var nation = $('#select-nation').find("input").val().trim();//民族
                        var duty = $('#duty').find("input").val().trim();//职务id
                        var qualification = $('#qualification').find("input").val().trim();//职务id
                        var userBirthday = $("#userBirthday").val().trim();//生日
                        var userStartWork = $("#userStartWork").val().trim();//参工时间
                        var userAddress = $("#userAddress").val().trim();//通讯地址
                        var userAvatar = $(".headimages").attr("id");//头像路径
                        var campus = sessionStorage.getItem('campusId'); // 分配的机构

                        $(".headimages").attr("id","userHeadImage");
                        if (userRealName.length == 0) {
                            layer.msg("姓名不能为空");
                            return false;
                        }
                        //var pattern = new RegExp("[`~!@#$%^&()（）_+-——<>?:？：“”{},，.。、\\\/;'￥《》]");
                        var pattern = new RegExp("[~'!@#￥$%^&*()-+_=:]");
                        if(pattern.test(userRealName)){
                            layer.msg("姓名不能有~'!@#￥$%等特殊字符");
                            return false;
                        }
                        if (userRealName.length > 45) {
                            layer.msg("姓名请小于45个字符");
                            return false;
                        }
                        if (teacherNamePy === '') {
                            layer.msg("拼音不能为空");
                            return false;
                        } else if (!teacherNamePy.match(/^[a-zA-z0-9]+$/)) {
                            layer.msg('请输入正确的拼音格式')
                            return false;
                        }
                        if (employNo !== '' && !employNo.match(/^[a-zA-z0-9]+$/)) {
                            layer.msg('工号只能为数字或字母');
                            return false;
                        }
                        if (userEmail != "" && !userEmail.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
                            layer.msg("邮箱格式不正确！请重新输入");
                            $("#userEmail").focus();
                            return false;
                        }
                        if(userMobile != "" && !(/^1(3|4|5|7|8|9)\d{9}$/.test(userMobile))){
                            layer.msg("手机号码格式不正确！请重新输入");
                            return false;
                        }
                        // if (employNo.length == 0 && userMobile.length == 0 && userEmail.length == 0) {
                        //     layer.msg("工号,手机号码或邮箱至少填写一个");
                        //     return false;
                        // }
						if (employNo.length == 0) {
                            layer.msg("工号不能为空");
                            return false;
                        }
                        if (stage == "") {
                            layer.msg("请选择所授课学段");
                            return false;
                        }
                        if(userStartWork != "" && !(/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))$/.test(userStartWork))){
                            layer.msg("参工时间格式不正确，请重新输入");
                            return false;
                        }
                        if(userBirthday != "" && !(/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))$/.test(userBirthday))){
                            layer.msg("出生年月格式不正确，请重新输入");
                            return false;
                        }
                        if((userStartWork<userBirthday)&&userBirthday != ""&&userStartWork !="")
                        {
                            layer.msg("入学日期不能小于出生日期");
                            return false;
                        }

                        // if (campus == "") {
                        //     layer.msg("机构不能为空");
                        //     return false;
                        // }
                        //if (duty == "") {
                        //    layer.msg("请选择职务");
                        //    return false;
                        //}
                        //if (subject == "") {
                        //    layer.msg("请选择所授课程");
                        //    return false;
                        //}
                        $.post(that.url + "/Teacher/teacherAdd.do", {
                            "teacherName":userRealName,
                            "teacherNamePy":teacherNamePy,
                            "employeeNo": employNo,
                            "stage": stage,
                            "gender":userGender,
                            "mobile":userMobile,
                            "email":userEmail,
                            "qq":qq,
                            "identity":identity,
                            "birthday":userBirthday,
                            "shortMobile":userShortMobile,
                            "nation":nation,
                            "politics":political,
                            "address":userAddress,
                            "startWork":userStartWork,
                            "isWork":isJob,
                            "teacherAvatar":userAvatar,
                            "qualification":qualification,
                            "duty":duty,
                            "subject":subject,
                            "campusId": campus
                        }, function (data) {
                            //layer.close(index);
                            if (data.code != 0) {
                                layer.msg("教师增加失败");
                                return false;
                            } else {
                                /*if (data.result == ERROR_SAME) {
                                 layer.msg("教师已存在或手机邮箱已注册");
                                 return false;
                                 }*/
                                if (data.result!="" && data.result.indexOf(",")>-1) {
                                    layer.msg(data.result);
                                    return false;
                                }
                                if (data.result == ERROR) {
                                    layer.msg("教师增加失败");
                                    return false;
                                }
                                layer.msg("教师添加成功");
                                that.initTeacherList(1);
                                layer.close(index);
                                return false;
                            }
                        }, 'json');
                    }
                });
            });
        },

        // 修改教师信息
        editTeacher: function () {
            var that = this;
            var userId = sessionStorage.getItem('userId');


            that.initGetDuty();
            that.initGetStage();
           // that.initGetGrade();
            that.initGetSubject();
            // that.initGetGradeEdit();
            $('.btn-edit-users').on('click', function () {
                // 获取机构树数据
                $.post(that.url + "/Campus/campusTree.do", {
                    'userId': userId
                }, function (data) {
                    that.initCampusTree(JSON.parse(data));  // 初始化机构树

                })

                var id = $(this).closest('.u-item').find('.u-check').attr('id');
                var teacherCode = $(this).closest('.u-item').find('.u-check').attr('teachercode');
                $.post(that.url + "/Teacher/teacherDetails.do", {
                    'teacherCode': teacherCode,
                    'userId':id,
                    'page': 1,
                    'pageSize':1,
                    'searchWord':""
                }, function (data) {
                    var value = data.userInfo.items[0];
                    if(value.user!=null) {
                        var userGender = (value.gender == 1 ? "男" : (value.gender == 2 ? "女" : "未知"));
                        var isJob = (value.is_job == 1 ? "是" : "否");//是否在职
                        var namepy = value.namepy?value.namepy:"";
                        var teacherName = value.teacher_name?value.teacher_name:"";//姓名
                        var employNo = value.employeeno?value.employeeno:"";//工号
                        var userMobile = value.mobile?value.mobile:"";//电话
                        var userEmail = value.email?value.email:"";//邮箱
                        var userShortMobile = value.qq?value.qq:"";//邮箱
                        var qq = value.qq?value.qq:"";//qq
                        var identity = value.idcode?value.idcode:"";//身份证号
                        var stage = value.stage?value.stage.name:""//学段名称
                        var political = value.political?value.political:""//政治面貌
                        var nation = value.nation?value.nation:""//民族
                        var qualification = (value.professional_title == 1 ? "三级教师" : (value.professional_title == 2 ? "二级教师" : (value.professional_title == 3 ? "一级教师" : (value.professional_title == 4 ? "高级教师" : (value.professional_title == 5 ? "正高级教师" : "其他")))));//职业资格
                        var userBirthday = value.birthday?value.birthday:""//生日
                        var userStartWork = value.work_date?value.work_date:"";//参工时间
                        var userWorkYear = value.teach_date?value.teach_date:"";//从教时间
                        var userAddress = value.address?value.address:""//通讯地址
                        var campus = new Array();
                        var subject = new Array();
                        var duty = new Array();
                        var subjectId = new Array();
                        var dutyId = new Array();
                        var userPath = value.teacher_path;
                        //
                        //var arrid = [];
                        //var arrName = [];
                        //for(var i = 0; i < value.user.campuses.length; i++) {
                        //    arrid.push(value.user.campuses[i].id);
                        //    arrName.push(value.user.campuses[i].name);
                        //}
                        //that.campus = arrid.join(','); // 设置机构id
                        //$('.campus-title-edit').html(arrName.join('、'));  // 设置机构名字

                        that.campus = value.user.campuses[0].id; // 设置机构id 单选机构用
                        $('.campus-title').html( value.user.campuses[0].name);  // 设置机构名字 单选机构用

                        // 获取机构树数据  复选机构用
                        //$.post(that.url + "/Campus/campusTree.do", {
                        //}, function (data) {
                        //    that.initCampusTreeEdit(JSON.parse(data), value.user.campuses);  // 初始化机构树 复选机构用
                        //
                        //})


                        $.each(value.subject,function(ind,value){
                            subjectId.push(value.id);
                        })
                        $.each(value.teacherDutyTypes,function(ind,value){
                            dutyId.push(value.id);
                        })
                        $.each(value.subject,function(ind,value){
                            subject.push(value.name);
                        })
                        $.each(value.teacherDutyTypes,function(ind,value){
                            duty.push(value.type_name);
                        })
                        $.each(value.user.campuses,function(ind,value){
                            campus.push(value.name);
                        })
                        var addr = "http://" + that.storageIp + ":" + that.storagePort;
                        var n = userPath.indexOf("/");//userPath为空为-1
                        if (userPath == "" || n == -1) {
                            userPath = "./images/userimg.png";//获取显示的图片
                        } else {
                            userPath = userPath.substring(n, userPath.length);
                            userPath = addr + userPath;
                        }
                    }

                    that.getSubjectByStage(value.stage.id, subjectId[0]);
                    $('#e_duty').find("input").val(dutyId);//职务
                    $('#e_stage').find("input").val(value.stage?value.stage.id:"");//学段
                    $('#e_subject').find("input").val(subjectId);//科目
                    $('#e_political').find("input").val(political);//政治面貌
                    $('#e_select-nation').find("input").val(nation);//民族
                    $('#e_qualification').find("input").val(value.professional_title);//职称
                    $('#e_duty .select-button').val(duty)//职务
                    $('#e_stage .select-button').val(stage)//学段
                    if(subject=="" || subject==undefined || subject.length==0){
                        subject="全部课程";
                    }
                    $('#e_subject .select-button').val(subject)//科目
                    $('#e_political .select-button').val(political)//政治面貌
                    $('#e_select-nation .select-button').val(nation)//民族
                    $('#e_qualification .select-button').val(qualification)//职称
                    $("#e_userRealName").val(teacherName);
                    $("#e_teacherNamePy").val(namepy);
                    $("#e_userNum").val(employNo);
                    $("#e_userBirthday").val(userBirthday);
                    $("#e_userMobile").val(userMobile);
                    $("#e_userShortMobile").val(userShortMobile);
                    $("#e_userEmail").val(userEmail);
                    $("#e_userQQ").val(qq);
                    $("#e_userAddress").val(userAddress);
                    $("#e_identity").val(identity);
                    $("#e_userStartWork").val(userStartWork);
                    //初始化选择按钮
                    $(".select-sf> span >span").removeClass("color-gree").find("i").remove();
                    $('#'+value.is_job).addClass("color-gree").append("<i></i>")
                    $('#'+value.gender+value.gender+value.gender+value.gender).addClass("color-gree").append("<i></i>")
                    $(".edit-users").find("img").attr("src",userPath);
                }, 'json');
                layer.open({
                    type: 1,
                    title: '修改教师信息',
                    shadeClose: false,
                    offset: ['100px', '100px'],
                    shade: 0.8,
                    content: $('.edit-users'),
                    area: ['410px', '520px'],
                    btn: ["确定", "取消"],
                    success: function () {
                        $('#layer', window.parent.document).show();
                    },
                    end: function (index, layero) {
                        $('#layer', window.parent.document).hide();
                        $('.cxcalendar').hide();
                        $('.cxcalendar_lock').hide();

                        that.campus = ''; // 清除选择的机构id

                    },
                    yes: function (index, layero) {
                        $('.cxcalendar').hide();
                        $('.cxcalendar_lock').hide();
                        var userGender = $('#genderss').find('.color-gree').attr("value");//性别
                        var isJob = $('#works').find('.color-gree').attr("value");//是否在职
                        var userRealName = $("#e_userRealName").val().trim();//姓名
                        var teacherNamePy = $("#e_teacherNamePy").val().trim();//姓名拼音
                        var employNo =  $("#e_userNum").val().trim();//工号
                        var userMobile = $("#e_userMobile").val().trim();//电话
                        var userEmail = $("#e_userEmail").val().trim();//邮箱
                        var userShortMobile = $("#e_userShortMobile").val().trim();//邮箱
                        var qq = $("#e_userQQ").val().trim();//qq
                        var identity =  $("#e_identity").val().trim();//身份证号
                        var stage = $('#e_stage').find("input").val().trim();//学段id
                        var subject1 = $('#e_subject').find("input[type='hidden']").val().trim();//科目id
                        var political = $('#e_political').find("input").val().trim();//政治面貌
                        var nation = $('#e_select-nation').find("input").val().trim();//民族
                        var duty = $('#e_duty').find("input").val().trim();//职务id
                        var qualification = $('#e_qualification').find("input").val().trim();//职称id
                        var userBirthday = $("#e_userBirthday").val().trim();//生日
                        var userStartWork = $("#e_userStartWork").val();//参工时间
                        var userAddress = $("#e_userAddress").val().trim();//通讯地址
                        var userAvatar = $(".headimage").attr("id");//头像路径

                        var campus = that.campus;
                        $(".headimage").attr("id","e_userHeadImage");
                        var pattern = new RegExp("[~'!@#￥$%^&*()-+_=:]");
                        if(pattern.test(userRealName)){
                            layer.msg("姓名不能有~'!@#￥$%等特殊字符");
                            return false;
                        }
                        if (userRealName.length == 0) {
                            layer.msg("姓名不能为空");
                            return false;
                        }
                        if (userRealName.length > 45) {
                            layer.msg("姓名请小于45个字符");
                            return false;
                        }
                        if (teacherNamePy === '') {
                            layer.msg("拼音不能为空");
                            return false;
                        } else if (!teacherNamePy.match(/^[a-zA-z0-9]+$/)) {
                            layer.msg('请输入正确的拼音格式')
                            return false;
                        }
                        if (employNo !== '' && !employNo.match(/^[a-zA-z0-9]+$/)) {
                            layer.msg('工号只能为数字或字母');
                            return false;
                        }
                        if (userEmail != "" && !userEmail.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
                            layer.msg("邮箱格式不正确！请重新输入");
                            $("#userEmail").focus();
                            return false;
                        }
                        if(userMobile != "" &&!(/^1(3|4|5|7|8|9)\d{9}$/.test(userMobile))){
                            layer.msg("手机号码格式不正确！请重新输入");
                            return false;
                        }
                        if (employNo.length == 0) {
                            layer.msg("工号不能为空");
                            return false;
                        }
                        if (stage == "") {
                            layer.msg("请选择所授课学段");
                            return false;
                        }
                        if(userStartWork != "" && !(/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))$/.test(userStartWork))){
                            layer.msg("参工时间格式不正确，请重新输入");
                            return false;
                        }
                        if(userBirthday != "" && !(/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))$/.test(userBirthday))){
                            layer.msg("出生年月格式不正确，请重新输入");
                            return false;
                        }
                        if((userStartWork<userBirthday)&&userBirthday != ""&&userStartWork !="")
                        {
                            layer.msg("入学日期不能小于出生日期");
                            return false;
                        }
                        // if (!campus) {
                        //     layer.msg("机构不能为空");
                        //     return false;
                        // }
                        //if (duty == "") {
                        //    layer.msg("请选择职务");
                        //    return false;
                        //}
                        //if (subject == "") {
                        //    layer.msg("请选择所授课程");
                        //    return false;
                        //}
                        $.post(that.url + "/Teacher/teacherUpdate.do", {
                            "teacherCode":teacherCode,
                            "teacherName":userRealName,
                            "teacherNamePy":teacherNamePy,
                            "employeeNo": employNo,
                            "stage": stage,
                            "gender":userGender,
                            "mobile":userMobile,
                            "email":userEmail,
                            "qq":qq,
                            "identity":identity,
                            "birthday":userBirthday,
                            "shortMobile":userShortMobile,
                            "nation":nation,
                            "politics":political,
                            "address":userAddress,
                            "startWork":userStartWork,
                            "isWork":isJob,
                            "teacherAvatar":userAvatar,
                            "qualification":qualification,
                            "duty":duty,
                            "subject":subject1,
                            'campusId': campus
                        }, function (data) {
                            layer.close(index);
                            /*if (data.code == ERROR_SAME) {
                             layer.msg("教师已存在或手机邮箱已注册");
                             return false;
                             }*/
                            if (data.code !="" && data.code.indexOf(",")>-1) {
                                layer.msg(data.code);
                                return false;
                            }
                            if (data.code == ERROR) {
                                layer.msg("教师修改失败");
                                return false;
                            }
                            if (data.code == ERROR_NOOBJ) {
                                layer.msg("修改对象不存在");
                                return false;
                            }
                            else{
                                layer.msg("修改成功");
                            }
                            var page = $(".current");
                            if (page.length < 1) {
                                that.initTeacherList(1);
                            } else {
                                for (var i = 0; i < page.length; i++) {
                                    if (page.eq(i).attr("class") == "current") {
                                        that.initTeacherList(page.eq(i).text());
                                    }
                                }
                            }
                        }, 'json');
                        layer.close(index);
                    }
                });
                return false;
            });
        },

        //删除成员
        deleteTeacher: function () {
            var that = this;
            $('.a-btn-delete').on('click', function () {
                var userId = sessionStorage.getItem('userId');
                var teacher_userId = $(this).closest('.u-item').find('.u-check').attr('id');
                if(userId==teacher_userId){
                    layer.msg("无权限删除,请联系管理员。");
                    return false;
                }
                var teacherCode = $(this).closest('.u-item').find('.u-check').attr('teachercode');
                var status = $(this).closest('.u-item').find('.u-check').attr('status');
                if(status==1)
                {
                    layer.msg("用户已启用无法删除");
                    return false;
                }
                //var con = $('.m-delete');
                var con = '<p class="delete-role-confirm" style="text-align: center ; line-height: 40px;font-size: 15px">确认删除该老师吗?</p>';  // update by caoqian

                layer.open({
                    type: 1,
                    title: '删除教师',
                    shadeClose: true,
                    offset: ['200px', '200px'],
                    shade: 0.8,
                    content:con,
                    btn:["确定", "取消"],
                    success: function () {
                        $('#layer', window.parent.document).show();
                    },
                    end: function (index, layero) {
                        $('#layer', window.parent.document).hide();
                    },
                    yes: function (index, layero) {
                        layer.close(index);
                        $.post(that.url +'/Teacher/teacherDelete.do', {
                            'id':teacherCode
                        }, function (data) {
                            if (data.code == 0) {
                                if (data.value) {
                                    layer.msg("删除成功");
                                    var page = $(".current");
                                    if (page.length < 1) {
                                        that.initTeacherList(1);
                                    } else {
                                        for (var i = 0; i < page.length; i++) {
                                            if (page.eq(i).attr("class") == "current") {
                                                that.initTeacherList(page.eq(i).text());
                                            }
                                        }
                                    }
                                    return true;
                                }else {
                                    layer.msg("删除失败");
                                    return false;
                                }
                            }else {
                                layer.msg("删除失败");
                                return false;
                            }
                        }, 'json');
                    }
                });
                return false;
            });
        },

        //预览头像
        previews: function () {
            var that = this;
            $("#uploads").on('change', function () {
                var thatt =this
                var flag ="";
                var holeName = $("#uploads").val();
                var name = holeName.split("\\");
                var fileName = name[name.length - 1];
                $("#upload_fileNames").val(fileName)

                var file = $("#uploadFiles");
                var extension = (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)).toLowerCase();//截取文件后缀名
                if (!(extension == "bmp" || extension == "jpg" || extension == "png" || extension == "jpeg")) {
                    $("#aaa").attr("style", "display:none");
                    $("#aa").attr("style", "display:none");
                    layer.msg("目前仅支持jpg、png、bmp、jpeg格式的图片");
                    $("#upload_fileNames").val("")
                    return false;
                }
                var options = {
                    url: that.url + '/Teacher/teacherImageCheck.do',
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if(data.result>1024){

                            layer.msg("请上传1M以下的图片");
                            $("#upload_fileNames").val("")
                            flag = "false";
                            return false;
                        }
                        else{
                            that.preview(thatt)
                        }
                    }
                };
                file.ajaxSubmit(options)
            })
        },

        //预览头像
        /*preview: function (file) {
            var prevDiv = document.getElementById('aaa');
            if (file.files && file.files[0]) {
                var reader = new FileReader();
                reader.onload = function (evt) {
                    $("#aaa").find("img").attr("src", evt.target.result);
                    $("#aaa").attr("style", "display: inline");
                    $("#aa").attr("style", "width:100px;height:100px;overflow:hidden");
                    $("#preview").attr("src", evt.target.result);
                }
                reader.readAsDataURL(file.files[0]);
            }
            else {
                prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
            }
        },

        //初始化设置头像（修改用）
        initHeadImage: function () {
            var that = this;
            $('.headimage').on('click', function () {
                $("#upload_fileNames").val("");
                $("#uploads").val("");
                var id = $(this).attr("id");
                $("#aaa").attr("style", "display:none");
                $("#aa").attr("style", "display:none");
                var con = $('.user-heading');
                layer.open({
                    type: 1,
                    title: '设置头像',
                    shadeClose: true,
                    offset: ['100px', '100px'],
                    shade: 0.8,
                    area:"430px",
                    content: $('.user-heading'),
                    btn: ["确定", "取消"],
                    success: function () {
                        $('#layer', window.parent.document).show();
                    },
                    end: function (index, layero) {
                        $('#layer', window.parent.document).hide();
                    },
                    yes: function (index, layero) {
                        var file = $("#uploadFiles");
                        var fileName = $("#upload_fileNames").val();
                        if(fileName=="")
                        {
                            layer.msg("请选择图片");
                            return false;
                        }
                        var imageDate = $("#imageData").val();
                        var array = imageDate.split(",");
                        var x = array[0];
                        var y = array[1];
                        var w = array[2];
                        var h = array[3];
                        var width = array[4];
                        var height = array[5];
                        var extension = (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)).toLowerCase();//截取文件后缀名
                        if (extension == "bmp" || extension == "jpg" || extension == "png" || extension == "jpeg") {
                            var options = {
                                url: that.url + '/Teacher/imageUpload.do?x=' + x + '&y=' + y + '&w=' + w + '&h=' + h + "&width=" + width + "&height=" + height + "&extension=" + extension,
                                type: 'post',
                                dataType: 'json',
                                success: function (data) {
                                    layer.close(index);
                                    var time = data.filename;
                                    if (data.result == "success") {
                                        var src = "./headImage/" + time + "." + extension + "";
                                        $(".headimage").attr("id", time + "." + extension)
                                        $(".edit-users").find("img").attr("src", src)

                                    }
                                    else {
                                        layer.msg("设置头像失败");
                                    }

                                }
                            };
                            file.ajaxSubmit(options);

                        } else {
                            layer.msg("目前仅支持jpg、png、bmp、jpeg格式的图片");
                            return false;
                        }
                    }
                });
            });
        },

        //初始化设置头像（添加用）
        initAddHeadImage: function () {
            var that = this;

            $('#userHeadImage').on('click', function () {
                $("#upload_fileNames").val("");
                $("#uploads").val("");
                $("#aaa").attr("style", "display:none");
                $("#aa").attr("style", "display:none");
                layer.open({
                    type: 1,
                    title: '设置头像',
                    shadeClose: true,
                    offset: ['100px', '100px'],
                    shade: 0.8,
                    area:"430px",
                    content: $('.user-heading'),
                    btn: ["确定", "取消"],
                    success: function () {
                        $('#layer', window.parent.document).show();
                    },
                    end: function (index, layero) {
                        $('#layer', window.parent.document).hide();
                    },
                    yes: function (index, layero) {
                        var file = $("#uploadFiles");
                        var fileName = $("#upload_fileNames").val();
                        if(fileName=="")
                        {
                            layer.msg("请选择图片");
                            return false;
                        }
                        var imageDate = $("#imageData").val();
                        var array = imageDate.split(",");
                        var x = array[0];
                        var y = array[1];
                        var w = array[2];
                        var h = array[3];
                        var width = array[4];
                        var height = array[5];
                        var extension = (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)).toLowerCase();//截取文件后缀名
                        if (extension == "bmp" || extension == "jpg" || extension == "png" || extension == "jpeg") {
                            var options = {

                                url: that.url + '/Teacher/imageUpload.do?x=' + x + '&y=' + y + '&w=' + w + '&h=' + h + "&width=" + width + "&height=" + height + "&extension=" + extension,
                                type: 'post',
                                dataType: 'json',
                                success: function (data) {
                                    layer.close(index);
                                    if (data.result == "success") {
                                        var time = data.filename;
                                        var src = "./headImage/" + time + "." + extension + "";
                                        $(".add-users").find("img").attr("src", src)
                                        $("#userHeadImage").attr("id", time + "." + extension)

                                    }
                                    else {
                                        layer.msg("设置头像失败");
                                    }
                                }
                            };
                            file.ajaxSubmit(options);

                        } else {
                            layer.msg("目前仅支持jpg、png、bmp、jpeg格式的图片");
                            return false;
                        }
                    }
                });
            });
        },*/

        // 显示用户信息
        showTeacherDetails: function () {
            var that = this;
            $('.btn-details').on('click', function () {
                var id = $(this).closest('.u-item').find('.u-check').attr('id');
                var teacherCode = $(this).closest('.u-item').find('.u-check').attr('teachercode');
                $.post(that.url + "/Teacher/teacherDetails.do", {
                    'teacherCode': teacherCode,
                    'userId':id,
                    'page': 1,
                    'pageSize':1,
                    'searchWord':""
                }, function (data) {
                    var value = data.userInfo.items[0];
                    if(value.user!=null) {
                        var userGender = (value.gender == 1 ? "男" : (value.gender == 2 ? "女" : "未知"));
                        var isJob = (value.is_job == 1 ? "是" : "否");//是否在职
                        var teacherName = value.teacher_name;//姓名
                        var userName = value.namepy;       // 用户名
                        var employNo = value.employeeno;//工号
                        var userMobile = value.mobile;//电话
                        var userEmail = value.email;//邮箱
                        var userShortMobile = value.qq;//邮箱
                        var qq = value.qq;//qq
                        var identity = value.idcode;//身份证号
                        var stage = value.stage.name//学段名称
                        var political = value.political//政治面貌
                        var nation = value.nation//民族
                        var qualification = (value.professional_title == 1 ? "三级教师" : (value.professional_title == 2 ? "二级教师" : (value.professional_title == 3 ? "一级教师" : (value.professional_title == 4 ? "高级教师" : (value.professional_title == 5 ? "正高级教师" : "其他")))));//职业资格
                        var userBirthday = value.birthday//生日
                        var userStartWork = value.work_date;//参工时间
                        var userWorkYear = value.teach_date;//从教时间
                        var userAddress = value.address//通讯地址
                        var campus = new Array();
                        var subject = new Array();
                        var duty = new Array();

                        var userPath = value.teacher_path;
                        $.each(value.subject,function(ind,value){
                            subject.push(value.name);
                        })
                        $.each(value.teacherDutyTypes,function(ind,value){
                            duty.push(value.type_name);
                        })
                        $.each(value.user.campuses,function(ind,value){
                            campus.push(value.name);
                        })
                        var addr = "http://" + that.storageIp + ":" + that.storagePort;
                        var n = userPath.indexOf("/");//userPath为空为-1
                        if (userPath == "" || n == -1) {
                            userPath = "./images/userimg.png";//获取显示的图片
                        } else {
                            userPath = userPath.substring(n, userPath.length);
                            userPath = addr + userPath;
                        }
                    }
                    var system_list = "";
                    $.each(data.roleInfo, function (i, value) {
                        system_list += "<li title=" + value + ">" + value + "</li>"
                    })
                    system_list == "" ? system_list = "无" : "";
                    $(".details-name").find('img').attr('src', userPath);
                    $("#details-system-list").html(system_list);
                    $("#details-teacherName").html(teacherName);
                    $("#details-teacherName").attr("title", teacherName);
                    $('#user-name .user-name').html(userName); // 用户名
                    $("#details-employeeNo").html("工号：" + employNo);
                    $("#details-employeeNo").attr("title", employNo);
                    $("#details-campus").html("组织机构：" + campus);
                    $("#details-campus").attr("title", campus);
                    $("#details-duty").html("职务：" + duty);
                    $("#details-duty").attr("title", duty);
                    $("#details-teacherStage").html(stage);
                    $("#details-teacherStage").attr("title", stage);
                    $("#details-teacherSubject").html(subject);
                    $("#details-teacherSubject").attr("title", subject);
                    $("#details-teacherGender").html(userGender);
                    $("#details-teacherGender").attr("title", userGender);
                    $("#details-teacherMobile").html(userMobile);
                    $("#details-teacherMobile").attr("title", userMobile);
                    $("#details-teacherEmail").html(userEmail);
                    $("#details-teacherEmail").attr("title", userEmail);
                    $("#details-teacherQQ").html(qq);
                    $("#details-teacherQQ").attr("title", qq);
                    $("#details-teacherIdentity").html(identity);
                    $("#details-teacherIdentity").attr("title", identity);
                    $("#details-teacherBrithday").html(userBirthday);
                    $("#details-teacherBrithday").attr("title", userBirthday);
                    $("#details-teacherShortTel").html(userShortMobile);
                    $("#details-teacherShortTel").attr("title", userShortMobile);
                    $("#details-teacherNation").html(nation);
                    $("#details-teacherNation").attr("title", nation);
                    $("#details-teacherPolitical").html(political);
                    $("#details-teacherPolitical").attr("title", political);
                    $("#details-teacherAddr").html(userAddress);
                    $("#details-teacherAddr").attr("title", userAddress);
                    $("#details-teacherStartWork").html(userStartWork);
                    $("#details-teacherStartWork").attr("title", userStartWork);
                    $("#details-teacherWorkYear").html(userWorkYear);
                    $("#details-teacherWorkYear").attr("title", userWorkYear);
                    $("#details-teacherStillWork").html(isJob);
                    $("#details-teacherStillWork").attr("title", isJob);
                    $("#details-teacherQualification").html(qualification);
                    $("#details-teacherQualification").attr("title", qualification);
                }, 'json');
                $(".layui-layer-content").css("height","440px");
                layer.open({
                    type: 1,
                    title: '教师信息',
                    shadeClose: true,
                    offset: ['0', '100px'],
                    shade: 0.8,
                    area: ['380px', '550px'],
                    content: $('.m-details'),
                    btn: ["确定"],
                    success: function () {
                        $('#layer', window.parent.document).show();
                    },
                    end: function (index, layero) {
                        $('#layer', window.parent.document).hide();
                    },
                    yes: function (index, layero) {
                        layer.close(index);
                    }
                });
                return false;
            });
        },

        //选中 check
        initUserCheck: function () {
            var checkAll = $('.main-list .m-check-all');
            checkAll.removeClass('m-checked');
            var checks = $('.main-list .m-check');
            // 整条都可以点选
            $('.main-list .u-item').on('click', function () {
                var checkTemp = $(this).find('i');
                if (checkTemp.hasClass('m-checked')) {
                    checkTemp.removeClass('m-checked');
                    $(this).css("background-color", "white");
                } else {
                    checkTemp.addClass('m-checked');
                    $(this).css("background-color", "#eaedf1");
                }
                // 都被勾选 ，全选框也被勾选
                if (checks.length == $('.u-item .m-checked').length) {
                    checkAll.addClass('m-checked');
                }
                // 未全被勾选，全选框取消勾选
                else {
                    checkAll.removeClass('m-checked');
                }

            });

            //全选
            checkAll.on('click', function () {
                if ($(this).hasClass('m-checked')) {
                    $(this).removeClass('m-checked');
                    checks.removeClass('m-checked');
                    checks.parent().parent().css("background-color", "white");
                } else {
                    $(this).addClass('m-checked');
                    checks.addClass('m-checked');
                    checks.parent().parent().css("background-color", "#eaedf1");
                }

            });

        },
        //搜索
        initSearch: function () {
            var that = this;
            //搜索
            $('.user-main .btn-search').on('click', function () {
                that.searchWord = $('.user-main .m-search input').val();
                that.initTeacherList(1);
                that.initUserCheck();
            });

            $('.user-main .m-search input').bind('keypress', function (event) {
                if (event.keyCode == '13') {
                    that.searchWord = $('.user-main .m-search input').val();
                    that.initTeacherList(1);
                    that.initUserCheck();
                }
            });
        },
        //分页
        initPage: function (count) {
            $('.m-users.m-page').html('');
            var that = this;
            if (count > 0) {
                var page = new Pagination(count, function (pageIndex) {
                    that.initTeacherList(pageIndex + 1);
                    that.initUserCheck();
                });
                page.init('.m-users.m-page', this.pageNumber);
            }

        },
        //获取教师列表
        initTeacherList: function (page, isInit) {
            if (isInit) {
                this.searchWord = '';
            }
            var that = this;
            var userId = sessionStorage.getItem('userId');
            $.post(that.url + "/Teacher/teacherSearchByPage.do", {
                'page': page,
                'pageSize': that.pageNumber,
                'searchWord': that.searchWord,
                'campusId': that.campusId,
                'flag': 'ad',
                'userId': userId
            }, function (data) {
                var totalCount = data.totalCount;
                var html="";
                var perStr = "";
                $.each(data.list,function(ind,value){
                    var userId = value.user.user_id;
                    var status = value.user.user_status;
                    var teacherid = value.id;
                    var teacherCode = value.teacher_code;
                    var teacherName = value.teacher_name;
                    var mobile = value.mobile;
                    var email = value.email;
                    var userPath = value.teacher_path;
                    var employeeNo = value.employeeno;
                    var stage = value.stage&&value.stage.name?value.stage.name:'';//学段名称
                    var campus = new Array();
                    var subject = new Array();
                    var duty = new Array();
                    $.each(value.subject,function(ind,value){
                        subject.push(value.name);
                    })
                    $.each(value.teacherDutyTypes,function(ind,value){
                        duty.push(value.type_name);
                    })
                    $.each(value.user.campuses,function(ind,value){
                        campus.push(value.name);
                    })
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

                    html +="  <div class=\"u-item\">" +
                    "                <div class=\"u-check\" id='" + userId + "' teacherCode='" + teacherCode + "'teacherid='" + teacherid + "'status='" + status + "'>" +
                    "                    <i class=\"m-check\" style=\"margin: 31px 0 0 11px\"></i>" +
                    "                </div>" +
                    "                <div class=\"u-name\" title=\"" + teacherName + "\">" +
                    "                    <div class=\"user-head\">" +
                    "                        <img src='" + userPath + "'>" +
                    "                        <div class=\"user-con\">" + teacherName + "</div>" +
                    "                        <div style=\"clear:both;line-height:0; height:0;\"></div>" +
                    "                    </div>" +
                    "                </div>" +
                    "                <div class=\"u-number\" title=\"" + employeeNo + "\">" + employeeNo + "</div>" +
                    "                <div class=\"u-org\" title=\"" + campus + "\">" + campus + "</div>" +
                    "                <div class=\"u-tel\" title=\"" + mobile + "\">" + mobile + "</div>" +
                    "                <div class=\"u-duty\" title=\"" + duty + "\">" + duty + "</div>" +
                    "                <div class=\"u-subject\">" + subject + "</div>"+
                    "                <div class=\"u-stage\">" + stage + "</div>" +
                    "                <div class=\"u-operation\">" +
                    "                    <a href=\"javascript:void(0)\" class=\"a-btn a-btn-details btn-details\" title='用户详情'></a>" +
                    "                    <a href=\"javascript:void(0)\" class=\"a-btn a-btn-edit btn-edit-users\" title='用户编辑'></a>" +
                    "                    <a href=\"javascript:void(0)\" class=\"a-btn a-btn-delete\" title='删除用户'></a>" +
                    "                </div>" +
                    "            </div>";
                })

                if (page && page == 1) {

                    that.initPage(totalCount);
                }
                $('.main-list .u-item').remove();

                $('.main-list').append(html);

                $('.m-check-all').unbind();
                that.initUserCheck();
                that.editTeacher();
                that.deleteTeacher();
                that.showTeacherDetails();
                that.batchDelete(); // 批量删除
            }, 'json');
        },

        // 导入用户
        initLeadingIn: function () {
            var that = this;
            $('.btn-leading-in').on('click', function () {
                $("#upload_fileName").val("");
                $("#upload").val("");
                layer.open({
                    type: 1,
                    title: '导入教师',
                    shadeClose: false,
                    offset: ['100px', '100px'],
                    shade: 0.8,
                    area: ["450px", "160px"],
                    content: $('.user-leading'),
                    btn: ["确定", "取消"],
                    success: function () {
                        $('#layer', window.parent.document).show();
                    },
                    end: function (index, layero) {
                        $('#layer', window.parent.document).hide();
                    },
                    yes: function (index, layero) {
                        var indexed = layer.load(1, {
                            shade: [0.5,'#000'] //0.1透明度的白色背景
                        });
                        var file = $("#uploadFile");
                        var fileName = $("#upload_fileName").val();
                        var extension = (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)).toLowerCase();//截取文件后缀名
                        if (extension == "xls" || extension == "xlsx") {
                            var options = {
                                url: that.url + '/Teacher/teacherUpload.do',
                                type: 'post',
                                dataType: 'json',
                                success: function (data) {
                                    layer.close(index);
                                    layer.close(indexed);
                                    if (data.result == "success") {
                                        layer.msg("导入完成");
                                        that.initTeacherList(1);
                                    } else if (data.result == "fileError") {
                                        layer.msg("导入程序错误");
                                    } else {
                                        that.downFailedExcel(data.result);
                                    }
                                }
                            };
                            file.ajaxSubmit(options);
                        } else {
                            layer.msg("请选择xls或xlsx格式文件");
                            layer.close(indexed);
                            return false;
                        }
                    }
                });
            });
        },
        // 导出教师列表
        initLeadingOut: function () {
            var that = this;
            $('.btn-leading-out').on('click', function () {
                var campusId = that.campusId;

                layer.open({
                    type: 1,
                    title: '导出教师',
                    shadeClose: false,
                    offset: ['200px', '150px'],
                    shade: 0.8,
                    area: ["300px", "130px"],
                    content: '<div style="text-align: center; margin-top: 15px">确定要下载文件吗？</div>',
                    btn: ["确定", "取消"],
                    success: function () {
                        $('#layer', window.parent.document).show();
                    },
                    end: function (index, layero) {
                        $('#layer', window.parent.document).hide();
                    },
                    yes: function (index, layero) {
                        window.location.href = that.url + "/Teacher/teacherExportExcel.do?searchWord=" + that.searchWord+"&campusId="+campusId;
                        layer.close(index);
                    }
                });

            });
        },
        initDownloadMould: function () {
            var that = this;
            $('#download-template').on('click', function () {
                window.location.href = that.url + "/Teacher/teacherDownloadMould.do";//下载用户模板
            });
        },
        initUpload: function () {
            $("#upload").on("change", function () {
                var file = $("#upload");
                var holeName = $("#upload").val();
                var name = holeName.split("\\");
                var fileName = name[name.length - 1];
                $("#upload_fileName").val(fileName)
            })
        },

        //获取选中用户的id，返回数组
        getIdGroup: function () {
            var idGroup = [];
            $('.main-list .m-check.m-checked').each(function () {
                var id = $(this).closest('.u-check').attr('id');
                idGroup.push(id);
            });
            return idGroup;
        },

        // 部分导入失败，下载失败数据列表excel
        downFailedExcel: function (filePath) {
            var that = this;
            layer.open({
                type: 1,
                title: '部分导入失败',
                shadeClose: true,
                offset: ['100px', '100px'],
                shade: 0.8,
                content: $(".download-failed"),
                btn: ["确定", "取消"],
                success: function () {
                    $('#layer', window.parent.document).show();
                },
                end: function (index, layero) {
                    $('#layer', window.parent.document).hide();
                    that.initTeacherList(1);
                },
                yes: function (index, layero) {
                    window.location.href = that.url + "/Teacher/teacherDownloadFailed.do?filePath=" + filePath;//用户数据导出失败
                    that.initTeacherList(1);
                    layer.close(index);
                }
            });
        },

        //获取所有职务
        initGetDuty: function () {
        var that = this;
            $.post(that.url + "/Teacher/getTeacherDuty.do", function (data) {
            var html = "";
            html += "<option value= ''>全部职务</option>"
            $.each(data.teacherDutyList, function (ind, value) {
                var typename = value["type_name"];
                var typeid = value["id"];
                if(typeid!=0&&(typeid>19||typeid<18)){//屏蔽家长学生超级管理员职务
                html += "<option value='" + typeid + "'>" + typename + "</option>"
                }
            })
            html += " </select>"
            $('#duty').html(html);
            $('#e_duty').html(html);
            var select = new SelectList('#duty', function (option) {
            });
            select.init();
            document.getElementById("duty").style.zIndex = "60";
            var select2 = new SelectList('#e_duty', function (option) {
            });
            select2.init();
            document.getElementById("e_duty").style.zIndex = "60";

        }, 'json');
    },

        //获取所有课程
        initGetSubject: function (seasonId) {
            var that = this;
            var stageid =sessionStorage.getItem("stageId");
            if(!!stageid){
                seasonId = stageid;
            }
            $.post(that.url + "/Teacher/getTeacherSubject.do", {"seasonId":seasonId},function (data) {
                // console.log(">>>>>>>>>>>>>>>seasonId===="+seasonId)
                var html = "";
                html += "<option value= ''>全部课程</option>"
                $.each(data.teacherSubjectList, function (ind, value) {
                    var typename = value["name"];
                    var typeid = value["id"];
                    html += "<option value='" + typeid + "'>" + typename + "</option>"
                })
                html += " </select>"

                $('#subject').html(html);
                $('#e_subject').html(html);
                var select = new SelectList('#subject', function (option) {
                });
                select.init();
                document.getElementById("subject").style.zIndex = "40";
                var select2 = new SelectList('#e_subject', function (option) {
                });
                select2.init();
                document.getElementById("e_subject").style.zIndex = "40";
            }, 'json');
        },
        //获取学段课程根据学段ID
        getSubjectByStage: function (seasonId, subjectId) {
            var that = this;
            $.post(that.url + "/Teacher/getTeacherSubject.do", {"seasonId":seasonId},function (data) {
                var html = "";
                var currentSub = "";
                var typeId = '';
                html += "<option value= ''>全部课程</option>"
                $.each(data.teacherSubjectList, function (ind, value) {
                    var typename = value["name"];
                    var typeid = value["id"];
                    if (typeid == subjectId) {
                        html += "<option selected='selected' value='" + typeid + "'>" + typename + "</option>"
                        // console.log('元素：' + $('#e_subject').find('input[type=hidden]'));
                        // console.log('typeid;---->' + typeid);
                        // $('#e_subject').find('input[name=e_subject]').attr('value', typeid);
                        currentSub = typename;
                        typeId = typeid;
                        // $('#e_subject .e_subject').val(typeid)
                    } else {
                        html += "<option value='" + typeid + "'>" + typename + "</option>"
                    }
                })
                html += " </select>"
                $('#subject').html(html);
                $('#e_subject').html(html);
                var select = new SelectList('#subject', function (option) {
                });
                select.init();
                document.getElementById("subject").style.zIndex = "40";
                var select2 = new SelectList('#e_subject', function (option) {
                });
                select2.init();
                document.getElementById("e_subject").style.zIndex = "40";
                $('#e_subject .select-button').val(currentSub)//科目
                $('#e_subject').find('input[type="hidden"]').val(typeId)//科目
                //科目
            }, 'json');

        },
        //add by zlw
        initGetGrade: function (seasonId) {
            var that = this;
            $.post(that.url + "/Teacher/getTeacherGrade.do",{"seasonId":seasonId}, function (data) {
                var html = "";
                $.each(data.gradeList, function (ind, value) {
                    // console.log("teacherGradeList" +teacherGradeList);
                    var typename = value["grade_name"];
                    var typeid = value["id"];
                    html += "<input name='grade_name' style='height: 20px;width: 37px;margin-bottom: 10px;' type='checkbox' value='" + typeid + "'>" + "<span style='display:inline-block; width: 43px; vertical-align: 4px'>"+typename+"</span></input>"

                })
                $('#grade').html(html);
                // select.init();
                document.getElementById("grade").style.zIndex = "50";
                $('#e_grade').html(html);
                // select.init();
                document.getElementById("e_grade").style.zIndex = "50";
            }, 'json');
        },

        //根据学段获取年级ID
        //add by zlw
        getGradeByStage: function (seasonId, grade) {
            var that = this;
            $.post(that.url + "/Teacher/getTeacherGrade.do", {"seasonId":seasonId},function (data) {
                var html = "";
                var currentSub = "";
                var typeId = '';
                console.log('data.gradeList', data.gradeList);
                $.each(data.gradeList, function (ind, value) {

                    var typename = value["grade_name"];
                    var typeid = value["id"];
                    html += "<input name='grade_name' style='height: 20px;width: 37px;margin-bottom: 10px;' type='checkbox' value='" + typeid + "'>" + "<span style='display:inline-block; width: 43px; vertical-align: 4px'>"+typename+"</span></input>"
                })
                $('#grade').html(html);
                // select.init();
                document.getElementById("grade").style.zIndex = "50";
                console.log('html=', html);
                $('#e_grade').html(html);
                // select.init();
                document.getElementById("e_grade").style.zIndex = "50";
                for (var i = 0; i < grade.length; i++ ) {
                    $('#e_grade').find('input[value=' + grade[i] + ']').prop("checked",true);
                }
                // $('#e_grade').find('input').val(grade)//年级
            }, 'json');

        },


        // initGetGradeEdit: function () {
        //     var that = this;
        //     $.post(that.url + "/Teacher/getTeacherGrade.do", function (data) {
        //         var html = "";
        //         $.each(data.value, function (ind, value) {
        //             var typename = value["e_name"];
        //             var typeid = value["id"];
        //             html += "<input name='e_name' style='height: 20px;width: 30px;' type='checkbox' value='" + typeid + "'>" + "<span style='margin-right: 20px'>"+typename+"</span></input>"
        //
        //         })
        //         $('#e_grade').html(html);
        //         // select.init();
        //         document.getElementById("e_grade").style.zIndex = "50";
        //         $('#e_grade').html(html);
        //         // select.init();
        //         document.getElementById("e_grade").style.zIndex = "50";
        //
        //     }, 'json');
        // },
        //获取所有学段
        initGetStage: function () {
            var that = this;
            var stageId=sessionStorage.getItem("stageId");
            $.post(that.url + "/Teacher/getTeacherStage.do", function (data) {
                var html = "";
                var flag=false;
                if(stageId==null || stageId==undefined || stageId=="") {
                    html += "<option value= ''>全部学段</option>";
                    flag=true;
                }
                $.each(data.value, function (ind, value) {
                    var typename = value["name"];
                    var typeid = value["id"];
                    if(flag){ //查询所有学段
                        html += "<option value='" + typeid + "'>" + typename + "</option>";
                    }else {
                        if (typeid == stageId) {
                            html += "<option value='" + typeid + "'>" + typename + "</option>";
                        } else {
                            html += "";
                        }
                    }
                })
                html += " </select>"
                $('#stage').html(html);
                $('#e_stage').html(html);
                var select = new SelectList('#stage', function (option) {
                    var stageId = option.context.dataset.value;
                    that.initGetSubject(stageId);
                });
                select.init();
                if(!flag) {
                    $("#stage").css("margin-top","0px");
                }
                document.getElementById("stage").style.zIndex = "50";
                var select2 = new SelectList('#e_stage', function (option) {
                    var stageId = option.context.dataset.value;
                    that.getSubjectByStage(stageId, '');
                });
                select2.init();
                document.getElementById("e_stage").style.zIndex = "50";
            }, 'json');
        },
        //初始化下拉机构树
        initCampusTree: function(data) {
            var that = this;
            $('.campusTree').hide();
            var flag = false;
            $('.campus-title').on('click',function () {
                flag = !flag;
                if (flag) {
                    $('.campusTree').slideDown(200);
                } else {
                    $('.campusTree').slideUp(200);
                }
            })
            // 选择的机构名字
            var name = '';
            $('.campus-title').html(name);
            var zTreeObj;
            // zTree 的参数配置）
            var setting = {
                view: {
                    showIcon: false,
                    showLine: false,
                },
                callback: {
                    onClick: function (e, treeId, treeNode) {
                        var id = treeNode.id;
                        name = treeNode.name;
                        $('.campus-title').html(name);
                        that.campus = id;
                    }
                }
            };
            var formateDate = this.formateCampusData([data]);
            zTreeObj = $.fn.zTree.init($(".campusTree"), setting, formateDate);
            $('.campusTree a').each(function () {
                $(this).on('click', function () {
                    flag = false;
                    $('.campusTree').slideUp(200);
                })
            })
        },
        //        格式化机构树数据
        formateCampusData: function (dataArr) {
            var that = this;
            var result = [];
            if (dataArr && dataArr.length > 0) {
                result =  dataArr.map(function (item) {
                    return {
                        name: item.name,
                        id: item.id,
                        open: true,
                        children: that.formateCampusData(item.directories)
                    }
                })
            }
            return result;
        },
        //    根据机构id获取teacherlist
        initTeacherListByCampusId: function (campusId) {
            this.campusId = campusId;
            this.initTeacherList(1,true);
            $('.m-search input').val('');
        },

        //    批量删除
        batchDelete: function () {
            var that = this;
            $('#btn-batch-delete').unbind('click');
            $('#btn-batch-delete').on('click', function () {
                that.selected = [];
                var userId = sessionStorage.getItem('userId');
                var isMyself=false;
                $('i.m-check.m-checked').each(function(index, element) {
                    var teacherId=$(this).parent().attr('teacherid');    //教师表id
                    var teacher_userId=$(this).parent().attr('id');  //用户表id
                    if(userId!=teacher_userId) {
                        that.selected.push(teacherId);
                    }else{
                        isMyself=true;
                    }
                });
                var userIds = that.selected.join(',');
                if (that.selected.length === 0) {
                    if(isMyself){
                        layer.msg("无权限删除,请联系管理员。");
                        return false;
                    }else {
                        layer.msg('请先选择人员');
                    }
                } else {
                    //update by caoqian  增加删除提示
                    var con = '<p class="delete-role-confirm" style="text-align: center ; line-height: 40px;font-size: 15px">确认删除老师吗?</p>';
                    layer.open({
                        type: 1,
                        title: '删除老师',
                        shadeClose: false,
                        offset: ['200px', '200px'],
                        shade: 0.8,
                        content:con,
                        btn:["确定", "取消"],
                        success: function () {
                            $('#layer', window.parent.document).show();
                        },
                        end: function (index, layero) {
                            $('#layer', window.parent.document).hide();
                        },
                        yes: function (index, layero) {
                            layer.close(index);
                            $.post(that.url + "/Teacher/teachersDelete.do", {
                                userIds: userIds
                            }, function(result) {
                                var data = JSON.parse(result);
                                if (data.code === 0 && data.value) {
                                    layer.msg('删除成功');
                                    var page = $(".current");
                                    if (page.length < 1) {
                                        that.initTeacherList(1);
                                    } else {
                                        for (var i = 0; i < page.length; i++) {
                                            if (page.eq(i).attr("class") == "current") {
                                                that.initTeacherList(page.eq(i).text());
                                            }
                                        }
                                    }
                                }
                            })
                        }
                    });
                }
            })
        }

    }
});