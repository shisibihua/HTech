define('cxMenu/cxmenu', function(require, exports, module) {

  var $ = require('jquery');
  var DeviceBean = require('device/device.bean');
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
          $('.right-top').text(title + ' 详情');
      }, 100);

  });

    module.exports = {
        //初始化页面内容
        init: function () {
            this.selectDeviceArray = []; //可选设备列表
            this.selectedDeviceArray = []; //已选设备列表
            this.initSelectDeviceHtml();
            this.url = location.href.substring(0, location.href.lastIndexOf("/"));
            this.initDeviceTree();
            this.initSelect();
            this.initUserDevice();
            this.initSelectAllClick();
            this.initRemoveAllClick();
        },
        //查询地点下所有设备的方法
        searchAreaDevice: function (areaId, deviceType, searchWord,deviceId) {
            var that = this;
            $.post(that.url + "/User/searchAreaDevice.do", {
                areaId: areaId,
                deviceType: deviceType,
                searchWord: searchWord,
                deviceId:deviceId
            }, function (data) {
                $.each(data.allDeviceList, function (i, value) {
                    var device = new DeviceBean(value.id, value.name, value.dtype_name, areaId);
                    device.setSelect(false);
                    that.selectDeviceArray.push(device);
                });
                that.initSelectDeviceHtml();
            }, 'json');
        },
        //初始化该用户已选设备列表
        initUserDevice:function(){
            var that = this;
            var userId = $("#userId").val();
            $.post(that.url + "/User/searchDevice.do",{
                userId:userId,
                deviceType:"",
                searchWord:""
            }, function (data) {
                $.each(data.deviceList,function(i,value){
                    var device = new DeviceBean(value.hostId, value.hostName, value.typeName, value.areaId);
                    device.setSelect(true);
                    that.selectedDeviceArray.push(device);
                });
                that.initSelectedDeviceHtml();
            }, 'json');
        },
        //初始化弹框后左侧目录点击方法
        initDeviceTree: function () {
            var that = this;
            $('#device-tree').cxMenu();
            $('#device-tree #left_nav_ul li a').on('click', function () {
                var areaId = $(this).attr("id");//获取地点ID
                $("#deviceTree-id").val(areaId);
                var deviceType = $("#win-sys").find("input[name='win-sys']").val();//获取所选设备类型
                var searchWord = $("#win-input-i").val();//获取关键字
                var devices = $("#win-deviceList div div").find(".win-block-mains-li");
                var deviceId ="";
                $.each(devices,function(i,value){
                    deviceId+=devices.eq(i).attr("id")+",";
                })
                that.selectDeviceArray = [];
                that.searchAreaDevice(areaId, deviceType, searchWord,deviceId);
            });
        },
        //初始化下拉框的方法
        initSelect: function () {
            var that = this;
            var SelectList = require('select-list/select');
            var select = new SelectList('#win-sys', function (option) {
                var areaId = $("#deviceTree-id").val();//获取地点ID
                var deviceType = option.attr('data-value');//获取设备类型
                var searchWord = $("#win-input-i").val();//获取关键字
                var devices = $("#win-deviceList").find(".win-block-mains-li");
                var deviceId ="";
                $.each(devices,function(i,value){
                    deviceId+=devices.eq(i).attr("id")+",";
                })
                that.selectDeviceArray = [];
                that.searchAreaDevice(areaId, deviceType, searchWord,deviceId);
            });
            select.typeSelect();
        },
        //初始化搜索所有设备按钮事件
        initSearchAllDevice: function () {
            var that = this;
            $(".search-allDevice").on("click", function () {
                var areaId = $("#deviceTree-id").val();//获取地点ID
                var searchWord = $("#win-input-i").val();//获取关键字
                var deviceType = $("#win-sys").find("input[name='win-sys']").val();//获取所选设备类型
                var devices = $("#win-deviceList").find(".win-block-mains-li");
                var deviceId ="";
                $.each(devices,function(i,value){
                    deviceId+=devices.eq(i).attr("id")+",";
                })
                that.selectDeviceArray = [];
                that.searchAreaDevice(areaId, deviceType, searchWord,deviceId);
            })
        },

        //渲染可选设备列表
        initSelectDeviceHtml: function () {
            var html = "",
                i,
                length = this.selectDeviceArray.length;
            if (length > 0) {
                for (i = 0; i < length; i++) {
                    html += this.selectDeviceArray[i].getHtml();
                }
            }

            var target = $("#allDeviceList .mCSB_container");
            target.html(html); //重新渲染可选设备列表
            this.initSelectDeviceClick();
        },
        //渲染已选设备列表
        initSelectedDeviceHtml: function () {
            var html = "",
                i,
                length = this.selectedDeviceArray.length;
            if (length > 0){
                for (i = 0; i < length; i++){
                    html += this.selectedDeviceArray[i].getHtml();
                }
            }

            var target = $("#win-deviceList .mCSB_container");
            target.html(html); //重新渲染已选设备列表
            this.initSelectedDeviceClick();
        },

        //可选设备列表内 点击事件
        initSelectDeviceClick: function () {
            var that = this;
            $("#allDeviceList .a-btn").on("click", function () {
                var html = $(this).parents(".win-block-mains-li");
                var id = html.attr("id");
                var index = that.getDeviceItem(id, that.selectDeviceArray);
                var device = that.selectDeviceArray[index];
                device.setSelect(true);
                that.selectedDeviceArray.unshift(device);//从可选设备列表添加到已选设备列表
                that.selectDeviceArray.splice(index, 1);//从可选设备列表中删除

                that.initSelectedDeviceHtml();
                //that.addShake($("#win-deviceList .mCSB_container"));

                html.remove();
            });
        },
        //已选设备点击事件
        initSelectedDeviceClick: function () {
            var that = this;
            $("#win-deviceList .w-mains-bth").on("click", function () {
                var html = $(this).parents(".win-block-mains-li");

                var id = html.attr("id");
                var index = that.getDeviceItem(id, that.selectedDeviceArray);
                var device = that.selectedDeviceArray[index];
                device.setSelect(false);

                var orgId = html.attr("orgId");
                // 判断该已选设备是否在当前选中的组织机构下
                if (orgId === $("#deviceTree-id").val()){
                    that.selectDeviceArray.unshift(device);//从已选设备列表添加到可选设备列表
                    that.initSelectDeviceHtml();
                    //that.addShake($("#allDeviceList .mCSB_container"));
                }
                that.selectedDeviceArray.splice(index, 1);//从已选设备列表中删除

                html.remove();
            });
        },
        //根据id从设备列表中取出某一项, 返回index
        getDeviceItem: function (id, arrs) {
            var length = arrs.length;
            if (length > 0){
                for (var i = 0; i < length; i++){
                    if (arrs[i].getId() == id){
                        return i;
                    }
                }
            }
            return -1;
        },
        //添加抖动效果
        addShake: function (target) {
            var first = target.find(".win-block-mains-li").eq(0);
            first.addClass("shake-slow");
            setTimeout(function () {
                first.removeClass("shake-slow");
            }, 500);
        },

        //选择全部 点击事件
        initSelectAllClick: function () {
            var that = this;
            $(".win-select-block.blue").on("click", function () {
                var len = that.selectDeviceArray.length;
                if (len > 0) {
                    for(var i = 0; i < len; i++){
                        that.selectDeviceArray[i].setSelect(true);
                    }
                    that.selectedDeviceArray = that.selectDeviceArray.concat(that.selectedDeviceArray);
                    that.selectDeviceArray = [];
                    that.initSelectDeviceHtml();
                    that.initSelectedDeviceHtml();
                }
            });

        },
        //移除全部 点击事件
        initRemoveAllClick: function () {
            var that = this;
            $(".win-select-block.hot").on("click", function () {
                var len = that.selectedDeviceArray.length,
                    orgId = $("#deviceTree-id").val(),
                    tempSelectedArray = [];
                if (len > 0) {
                    for(var i = 0; i < len; i++){
                        var device = that.selectedDeviceArray[i];
                        device.setSelect(false);
                        if (device.getOrgId() === orgId){
                            tempSelectedArray.push(device);
                        }
                    }
                    that.selectDeviceArray = tempSelectedArray.concat(that.selectDeviceArray);
                    that.selectedDeviceArray = [];
                    that.initSelectDeviceHtml();
                    that.initSelectedDeviceHtml();
                }
            });
        }
    }
});
