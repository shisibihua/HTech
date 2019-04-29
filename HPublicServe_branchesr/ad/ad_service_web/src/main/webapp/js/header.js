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
  
  $('a.logout').on('click', function(){
  });

});
