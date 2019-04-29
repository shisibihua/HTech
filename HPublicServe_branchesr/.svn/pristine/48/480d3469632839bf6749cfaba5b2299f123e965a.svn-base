define('pagination/pagination', function(require, exports, module) {

  var $ = require('jquery');
  require('pagination/jquery.pagination');
  
  /*
   * 初始化分页
   * total : 所有数据的总个数
   * callback : 点击分页按钮时触发的方法，如ajax请求
   */
  function Pagination(total, callback) {
      this.totalCount = total;
      this.pageSelectCallback = callback;
  }
  
  module.exports = Pagination;
  
  Pagination.prototype.init = function () {
      $('.m-page').pagination(this.totalCount, {
          num_edge_entries: 1, //边缘页数
          num_display_entries: 4, //主体页数
          callback: this.pageSelectCallback,
          items_per_page: 10, //每页显示10项
          link_to: "javascript:void(0)",
          prev_text: "上一页",
          next_text: "下一页"
      });
  }

});
