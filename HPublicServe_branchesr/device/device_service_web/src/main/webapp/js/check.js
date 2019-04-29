define('check/check', function(require, exports, module) {

  var $ = require('jquery');
  var checkAll = $('.m-check-all'),
      checks = $('.m-check');

  //全选


    $(document).on('click', '.a-check-all  ',function () {
        if ($(this).hasClass('a-checked')) {
            $(this).removeClass('a-checked');
            $('.a-check').removeClass('a-checked');
            $('.win-block-fep-main-info').css("background-color","white");
        } else {
            $(this).addClass('a-checked');
            $('.a-check').addClass('a-checked');
            $('.win-block-fep-main-info').css("background-color","#eaedf1");
        }
    });

    $(document).on('click', '.m-check-all ',function () {
        if ($(this).hasClass('m-checked')) {
            $(this).removeClass('m-checked');
            $('.m-check').removeClass('m-checked');
            $('.devive-list-main').css("background-color","white");
            $('.u-item').css("background-color","white");

        } else {
            $(this).addClass('m-checked');
            $('.m-check').addClass('m-checked');
            $('.devive-list-main').css("background-color","#eaedf1");
            $('.u-item').css("background-color","#eaedf1")
        }
    });

  //单选设备分组点击整行都可以选
  $(document).on('click', '.devive-list-main',function () {
      $(this).find(".m-check").hasClass('m-checked') ? $(this).find(".m-check").removeClass('m-checked') && $(this).css("background-color","white") : $(this).find(".m-check").addClass('m-checked') && $(this).css("background-color","#eaedf1");
      if ($('.m-check').length == $('.devive-list-main .m-checked').length) {
          $(".m-check-all").addClass('m-checked');

      }
      // 未全被勾选，全选框取消勾选
      else
      {
          $(".m-check-all").removeClass('m-checked');
      }
  });
  //单选设备分配点击整行都可以选
  $(document).on('click', '.win-block-fep-main-info',function () {
      $(this).find(".a-check").hasClass('a-checked') ? $(this).find(".a-check").removeClass('a-checked') && $(this).css("background-color","white") : $(this).find(".a-check").addClass('a-checked') && $(this).css("background-color","#eaedf1");
      // 都被勾选 ，全选框也被勾选
      if ($('.a-check').length == $(' .win-block-fep-main-info .a-checked').length) {
          $(".a-check-all").addClass('a-checked');
      }
      // 未全被勾选，全选框取消勾选
      else
      {
          $(".a-check-all").removeClass('a-checked');
      }
  });
});
