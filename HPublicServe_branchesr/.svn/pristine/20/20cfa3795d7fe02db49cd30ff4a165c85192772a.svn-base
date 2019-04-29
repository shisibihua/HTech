define('check/check', function(require, exports, module) {

  var $ = require('jquery');
  
  var checkAll = $('.m-check-all'),
      checks = $('.m-check');
  
  //全选
  checkAll.on('click', function () {
      if ($(this).hasClass('m-checked')) {
          $(this).removeClass('m-checked');
          checks.removeClass('m-checked');
      } else {
          $(this).addClass('m-checked');
          checks.addClass('m-checked');
      }
  });
  
  //单选
  checks.on('click', function () {
      $(this).hasClass('m-checked') ? $(this).removeClass('m-checked') : $(this).addClass('m-checked');
  });
  

});
