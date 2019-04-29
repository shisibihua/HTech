define('cxMenu/cxmenu', function(require, exports, module) {

  var $ = require('jquery');
  require('cxMenu/jquery.cxmenu');
  
  $('#left_nav_ul').cxMenu();
  
  //将左侧选中的分类，显示到右侧顶部
  $('#left_nav_ul li').on('click', function () {
      setTimeout(function () {
          var lis = $('#left_nav_ul').find('li.selected');
          var title = '';
          for (var i = 0; i < lis.length; i++) {
              title += lis.eq(i).children('a').children('h6').text() + ' > ';
          }
          $('.right-top p').text(title + ' 详情');
      }, 100);
  
  });

});
