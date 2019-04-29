define('device/device.bean', function (require, exports, module) {

    var $ = require('jquery');
    var cxmenu = require('cxMenu/cxmenu');

    function DeviceBean(id, name, typeName, orgId) {
        this.id = id;
        this.name = name;
        this.typeName = typeName;
        this.isSelected = false;
        this.orgID = orgId;
    }

    module.exports = DeviceBean;

    DeviceBean.prototype.setSelect = function (isSelect) {
        this.isSelected = isSelect;
    };

    DeviceBean.prototype.getSelect = function () {
        return this.isSelected;
    };

    DeviceBean.prototype.getId = function () {
        return this.id;
    };

    DeviceBean.prototype.getOrgId = function () {
        return this.orgID;
    };

    DeviceBean.prototype.getHtml = function () {
        var html = '<div class="win-block-mains-li" id="' + this.id + '" orgId="' + this.orgID +'">';
        html += '<span class="w-mains-li-name">' + this.name + '</span>';
        html += '<span class="w-mains-li-lei">' + this.typeName + '</span>';
        html += '<span class="w-mains-li-icon">';
        var span;
        if (this.isSelected) {
            span = '<span class="a-btn w-mains-bth"></span>';
        }
        else {
            span = '<span class="a-btn a-btn-add"></span>';
        }
        html += span;
        html += '</span></div>';
        return html;
    };

});
