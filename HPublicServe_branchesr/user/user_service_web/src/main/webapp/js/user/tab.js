/**
 * Created by honghe on 2017/9/7.
 */
define('tab/tab-li',function(require,exports,module) {
    var $ = require('jquery');
    function Tab(el, fn,options) {
        this.el = $(el);
        this.fn = fn;
        $.extend(this.default={
            events:'click',
            selectedClass:'tab-this'
        },options);
        this.init();
    }
    $.extend(Tab.prototype,{
        init:function(){
            var that = this;
            $(this.el).on(this.default.events,'li',function(){
                $(that.el).find('li').each(function(i,n){
                    $(n).removeClass(that.default.selectedClass);
                });
                $(this).addClass(that.default.selectedClass);
                that.fn(this);
            });
        }
    });
    return Tab;
});
