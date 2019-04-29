define('check/check', function(require, exports, module) {

  var $ = require('jquery');
  
  var checkAll = $('.m-check-all'),
      checks = $('.m-check');
  //全选
    $(document).on('click','.m-check-all', function () {
      if ($(this).hasClass('m-checked')) {
          $(this).removeClass('m-checked');
          $('.m-check').removeClass('m-checked');
      } else {
          $(this).addClass('m-checked');
          $('.m-check').addClass('m-checked');
      }
  });
  //单选

    $(document).on('click', '.m-check',function () {
      $(this).hasClass('m-checked') ? $(this).removeClass('m-checked') : $(this).addClass('m-checked');
        var sel_leng = $(".m-check").length;
        var device_count =$("#deviceList").find(".m-checked").length
        if(sel_leng == device_count){
            $(".m-check-all").addClass("m-checked");
        }else{
            $(".m-check-all").removeClass("m-checked");
        }
        return false
  });
    $(document).on("click",".catalog-list-main",function(){
        $(this).find('.m-check').click();
    })
});
