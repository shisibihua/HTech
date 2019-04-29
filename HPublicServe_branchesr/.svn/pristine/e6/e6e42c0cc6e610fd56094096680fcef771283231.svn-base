define('device/munest', function(require, exports, module) {

  var $ = require('jquery');
    var cxMenu = require("cxMenu/cxmenu");
  $(function () {

              $(document).on("mouseover",".win-block-mains-li",function(){
                  $(this).css("background","#eaedf1");
              });
              $(document).on("mouseleave",".win-block-mains-li",function(){
                  $(this).css("background","#fff");
              });

      //已选设备点击事件
              $("#deviceList .w-mains-bth").on("click",function(){
                  $(this).attr("class","a-btn a-btn-add btn-add-org btn-move-to-right");
                  var html = $(this).parents(".win-block-mains-li");
                  var id = html.attr("id");
                  $("#allDeviceList #mCSB_2_container").append(html[0].outerHTML);

                  $("#allDeviceList #" + id + " .btn-move-to-right").on("click", cxMenu.initSelectDeviceClick());

                  html.remove();
              });

              $(".win-select-block").on("click",function(){
                  var this_p = $(this).parents(".win-block-mul-main")
                  var flag = this_p.hasClass("y-subt");
                  if(flag){
                    this_p.find(".w-mains-bth").attr("class","a-btn a-btn-add btn-add-org");
                    var this_html = this_p.find("#mCSB_3_container").html();
                    $(".x-subt").find("#mCSB_2_container").append(this_html);
                    this_p.find(".win-block-mains-li").remove();
                  }else{
                    this_p.find(".btn-add-org").attr("class","a-btn w-mains-bth");
                    var this_html = this_p.find("#mCSB_2_container").html();
                    $(".y-subt").find("#mCSB_3_container").append(this_html);
                    this_p.find(".win-block-mains-li").remove();
                  }
              })
  });

});
