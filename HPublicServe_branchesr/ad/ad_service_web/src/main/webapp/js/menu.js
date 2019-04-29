define('menu/menu', function(require, exports, module) {

  var $ = require('jquery');
  
  $('.m-menu li a.m-menu-btn').on('click', function () {
      $(this).parent('li').addClass('selected').siblings().removeClass('selected');
      var title = $(this).text();
      $('.right-top').children('h6').text(title);
  })

});
