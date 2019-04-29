define('select-list/select', function(require, exports, module) {

  var $ = require('jquery');
  require('select-list/selectlist');
  
  /*
   * 初始化下拉框
   * el : select的id，如：#system
   * fn : 点击下拉列表每一项时触发的方法，如ajax请求
   */
  function SelectList(el, fn) {
      this.el = $(el);
      this.fn = fn;
  }
  
  module.exports = SelectList;
  
  SelectList.prototype.init = function () {
      this.el.selectlist({
          zIndex: 10,
          width: 230,
          height: 30,
          onChange: this.fn
      });
  }

});
