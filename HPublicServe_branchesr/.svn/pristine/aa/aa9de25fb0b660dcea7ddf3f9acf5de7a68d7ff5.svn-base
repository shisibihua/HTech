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
            width: 249,
            height: 32,
            onChange: this.fn
        });
    };

    SelectList.prototype.typeSelect = function () {
        this.el.selectlist({
            zIndex: 55,
            width: 230,
            height: 32,
            onChange: this.fn
        });
    };
    SelectList.prototype.orgSSQ = function () {
        this.el.selectlist({
            zIndex: 55,
            width: 120,
            height: 32,
            onChange: this.fn
        });
    };
    SelectList.prototype.campusSelect = function () {

        this.el.selectlist({
            zIndex: 50,
            width: 370,
            height: 32,
            onChange: this.fn
        });
    };

});
