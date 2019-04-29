/**
 * Created by honghe on 2017/9/7.
 */
// define('campus/tree',function(require,exports,module){
//     var $ = require('jquery');
//     var users = require('users/users');
//
//     var that;
//     function Tree(el,fn){
//         this.url = location.href.substring(0, location.href.lastIndexOf("/"));
//         this.el = $(el);
//         this.fn = fn;
//         that = this;
//         this.init();
//     }
//     $.extend(Tree.prototype,{
//         init:function(){
//             this._packetData();
//         }
//         ,recursion:function(data,str,count){
//             count++;
//             str+="<li>";
//             str+="<a href='javascript:void(0)' id='" + data.id + "' typeId='" + data.typeId + "' stagesId='" + data.stagesId + "'><i count='" + count + "'></i><input campusId='" + data.id + "'  type='checkbox'><h6>" + data.name +
//                 "</h6></a>";
//             if(data.directories && data.directories.length!=0){
//                 str+="<ul>"
//                 for(var i=0;i<data.directories.length;i++){
//                     str = that.recursion(data.directories[i],str,count);
//                 }
//                 str+="</ul>";
//             }
//             str+="</li>";
//             return str;
//         }
//         ,_getData:function(data, flag){
//             var html = "<ul id='left_nav_ul'>";
//             html = that.recursion(data,html,0);
//             html +="</ul>";
//             that.el.append(html);
//             require('cxMenu/jquery.cxmenu');
//             $('#left_nav_ul').cxMenu();
//             /*拦截input点击事件，不在派发*/
//             $('#left_nav_ul').find(':checkbox').each(function(i,n){
//                 $(n).on('click',function(e){
//                     var isChecked = this.checked;
//                     var campusId = $(this).attr('campusId');
//                     if(isChecked){
//                         $(this).prop('checked',isChecked);
//                         users.checked.push(campusId);
//                     }else{
//                         $(this).removeAttr('checked');
//                         user.checked = user.checked.filter(function (item) {
//                             return item !== campusId;
//                         })
//                     }
//                     //if($(this).prev().attr('count')==1){//第一个checkbox
//                     //    $('#left_nav_ul').find(':checkbox').not(':first').each(function(i,n){
//                     //        if(isChecked){
//                     //            $(this).prop('checked',isChecked);
//                     //        }else{
//                     //            $(this).removeAttr('checked');
//                     //        }
//                     //    });
//                     //}else{
//                     //    var isAll = true;
//                     //    $('#left_nav_ul').find(':checkbox:not(:first)').each(function(i,n){
//                     //        if(!n.checked){
//                     //            isAll = false;
//                     //        }
//                     //    });
//                     //    if(isAll){
//                     //        $('#left_nav_ul').find(':checkbox:first').prop('checked',true);
//                     //    }else{
//                     //        $('#left_nav_ul').find(':checkbox:first').removeAttr('checked');
//                     //    }
//                     //}
//
//
//                     e.stopPropagation();});
//             });
//             /*a标签点击事件*/
//             $('#left_nav_ul').on('click','a',function(){
//                 $('#left_nav_ul').find('a').css('background','');
//                 $(this).css('background','#eaedf1');
//                 var campusId = $(this).attr('id');
//                 users.campusId = campusId;
//                 users.searchWord = '';
//                 $('.main-right .main-top .m-search input').val('');
//                 users.initUserList(1);
//
//                 //    判断是不是管理员，不是管理员屏蔽添加 导入 导出功能
//                 var isAdmin = JSON.parse(sessionStorage.getItem('isAdmin'));
//                 var adminCampusId = sessionStorage.getItem('adminCampusId');
//                 var isSuperAdmin = JSON.parse(sessionStorage.getItem('isSuperAdmin'));
//
//                 if (isAdmin && adminCampusId.indexOf(campusId) > -1 || isSuperAdmin) {
//                     $('.user-btns').show();
//                 } else {
//                     $('.user-btns').hide();
//                 }
//             });
//
//             //setTimeout(function () {
//                 $('#left_nav_ul >li >a').trigger('click');
//             //}, 500);
//             if (flag) {
//                 $('#left_nav_ul >li >a').hide();
//                 $('#left_nav_ul >li >a +ul >li >a').trigger('click');
//             }
//         }
//         ,_packetData:function(){
//             // var userId = sessionStorage.getItem('userId');
//             // $.post(this.url+'/Campus/campusTree.do',{
//             //     'userId': userId,
//             // },function(data){
//             //     var flag = false;
//             //     if (!data.id && !data.level && data.directories && data.directories.length > 0) {
//             //         flag = true;
//             //     }
//             //     that._getData(data, flag);
//             // },'json');
//
//             var userId = sessionStorage.getItem('userId');
//             $.post(this.url+'/Campus/campusSearchByUserId.do',{
//                 'userId': userId,
//             },function(data){
//                 var flag = false;
//                 if (!data.id && !data.level && data.directories && data.directories.length > 0) {
//                     flag = true;
//                 }
//                 that._getData(data, flag);
//                 var flag = false;
//                 if (!data.id && !data.level && data.directories && data.directories.length > 0) {
//                     flag = true;
//                 }
//                 that._getData(data, flag);
//             },'json');
//         }
//     });
//     return Tree;
// });


define('campus/tree',function(require,exports,module){
    var $ = require('jquery');
    var users = require('users/users');

    var that;
    function Tree(el,fn){
        this.url = location.href.substring(0, location.href.lastIndexOf("/"));
        this.el = $(el);
        this.fn = fn;
        that = this;
        this.init();
    }
    $.extend(Tree.prototype,{
        init:function(){
            this._packetData();
        }
        ,recursion:function(data,str,count){
            count++;
            str+="<li>";
            str+="<a href='javascript:void(0)' id='" + data.id + "' typeId='" + data.typeId + "' stagesId='" + data.stagesId + "'><i count='" + count + "'></i><input campusId='" + data.id + "'  type='checkbox'><h6>" + data.name +
                "</h6></a>";
            if(data.directories && data.directories.length!=0){
                str+="<ul>"
                for(var i=0;i<data.directories.length;i++){
                    str = that.recursion(data.directories[i],str,count);
                }
                str+="</ul>";
            }
            str+="</li>";
            return str;
        }
        ,_getData:function(data, flag){
            // var html = "<ul id='left_nav_ul'>";
            // html = that.recursion(data,html,0);
            // html +="</ul>";
            that.el.append(data);
            require('cxMenu/jquery.cxmenu');
            $('#left_nav_ul').cxMenu();

            /*拦截input点击事件，不在派发*/
            $('#left_nav_ul').find(':checkbox').each(function(i,n){
                $(n).on('click',function(e){
                    var isChecked = this.checked;
                    var campusId = $(this).attr('campusId');
                    if(isChecked){
                        $(this).prop('checked',isChecked);
                        users.checked.push(campusId);
                    }else{
                        $(this).removeAttr('checked');
                        user.checked = user.checked.filter(function (item) {
                            return item !== campusId;
                        })
                    }
                    //if($(this).prev().attr('count')==1){//第一个checkbox
                    //    $('#left_nav_ul').find(':checkbox').not(':first').each(function(i,n){
                    //        if(isChecked){
                    //            $(this).prop('checked',isChecked);
                    //        }else{
                    //            $(this).removeAttr('checked');
                    //        }
                    //    });
                    //}else{
                    //    var isAll = true;
                    //    $('#left_nav_ul').find(':checkbox:not(:first)').each(function(i,n){
                    //        if(!n.checked){
                    //            isAll = false;
                    //        }
                    //    });
                    //    if(isAll){
                    //        $('#left_nav_ul').find(':checkbox:first').prop('checked',true);
                    //    }else{
                    //        $('#left_nav_ul').find(':checkbox:first').removeAttr('checked');
                    //    }
                    //}


                    e.stopPropagation();});
            });
            /*a标签点击事件*/
            $('#left_nav_ul').on('click','a',function(){
                $('#left_nav_ul').find('a').css('background','');
                $(this).css('background','#eaedf1');
                var campusId = $(this).attr('id');
                users.campusId = campusId;
                users.searchWord = '';
                $('.main-right .main-top .m-search input').val('');
                users.initUserList(1);
            });

            //setTimeout(function () {
            $('#left_nav_ul >li >a').trigger('click');
            //}, 500);
            if (flag) {
                $('#left_nav_ul >li >a').hide();
                $('#left_nav_ul >li >a +ul >li >a').trigger('click');
            }
        }
        ,_packetData:function(){
            var userId = sessionStorage.getItem('userId');
            $.post(this.url+'/Campus/campusSearchByUserId.do',{
                'userId': userId,
                'type': 0
            },function(data){
                that._getData(data);
            },'html');
        }
    });
    return Tree;
});