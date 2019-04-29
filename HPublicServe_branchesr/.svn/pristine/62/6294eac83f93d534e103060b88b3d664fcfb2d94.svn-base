define('header/header', function(require, exports, module) {

  var $ = require('jquery');
  
  //利用正则表达式获取链接中的参数
  function GetQueryString(name) {
      var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
      var r = window.location.search.substr(1).match(reg);
      if (r != null) return unescape(r[2]);
      return null;
  }
  
  //利用链接中传递参数实现导航的选中效果
  var id = GetQueryString('id'),
      lis = $('.nav li');
  if(id){
      lis.eq(parseInt(id)).find('a').addClass('selected');
  }
  else{
      lis.eq(0).find('a').addClass('selected');
  }
  // 点击退出，返回主页面
  $('a.logout').on('click', function(){
  });
    // 获取请求参数
    function GetRequest() {

        var url = location.search; //获取url中"?"符后的字串
        var theRequest = {};
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            strs = str.split("&");
            for(var i = 0; i < strs.length; i ++) {
                theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
            }
        }
        return theRequest;
    }
    var query = GetRequest();
    sessionStorage.setItem('userId', query.userId);
    var isAdmin = false;
    var isSuperAdmin = false;
    if (query && query.userId === '1') {
        var isAdmin = true;
        var isSuperAdmin = true;
        sessionStorage.setItem('adminCampusId','');
    } else {
        $.ajax({
            type: 'POST',
            url: location.href.substring(0, location.href.lastIndexOf("/")) + "/User/getUserInfo.do",
            data: {
                'userId': query.userId,
            },
            async: false,
            success: function (data) {
                var result = JSON.parse(data);
                if (result.result && result.result.userIsAdmin && result.result.userIsAdmin === '1') {
                    isAdmin = true;
                    if (result.result.campusId === '1') {
                        isSuperAdmin = true;
                    }
                }
                sessionStorage.setItem('adminCampusId', result.result.adminCampusId.join(','));
            },
        })
    }
    sessionStorage.setItem('isAdmin', isAdmin);
    sessionStorage.setItem('isSuperAdmin', isSuperAdmin);
});
