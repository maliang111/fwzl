var jq = jQuery.noConflict();

var zmglGrid;
var tabbar;

jq(function () {

    zmglGrid = doInitGrid();
    doFindZmxx();

    jq("#zmglInfoHeader #searchZmxx").bind("click", function () {
        doFindZmxx();
    });

    var view = new Ext.Viewport({
        layout : "border",
        items : [{
            "region" : "north",
            "height" : 70,
            "contentEl" : "zmglInfoHeader"
        }, {
            "region" : "center",
            "layout" : "fit",
            items : [zmglGrid]
        }, {
            "region" : "south",
            "height" : 240,
            "items" : [{
		    		"contentEl" : "zmDetail",
		    		"autoScroll" : true
		    	}]
        }]
    });

});

function doInitTabbar() {
    tabbar = new dhtmlXTabBar("zmDetail", "top");
    var imagePath = getFullPath("dhtmlxTabBar/imgs/");
    imagePath = imagePath.substring(0, imagePath.indexOf("?"));
    tabbar.setImagePath(imagePath);
    tabbar.setSkin('dhx_skyblue');
    tabbar.setHrefMode("iframes-on-demand");
    tabbar.loadXML(getFullPath("static/xml/tab.xml"), function () {
        tabbar.setTabActive("orderInfo");
    });
    tabbar.attachEvent("onSelect", function (id) {
        showZmDetail(id);
        return true;
    });
}

function doFindZmxx(type) {
    var limit = zmglGrid.getBottomToolbar().pageSize;
    var start = type ? zmglGrid.getBottomToolbar().cursor : 0;
    zmglGrid.getStore().load({
        "params" : {
            "start" : start,
            "limit" : limit
        },
        "callback" : function(r, option, success) {
            if (!success) {
                alert("查询失败");
            } else {
                zmglGrid.getSelectionModel().selectFirstRow();
                if (!tabbar) {
                    doInitTabbar();
                } else {
                    showZmDetail(tabbar.getActiveTab());
                }
            }
        }
    })
}

function showZmDetail(id) {
    var row = zmglGrid.getSelectionModel().getSelected();
    var orderId = "";
    var houseId = "";
    var ownerId = "";
    var tenantId = "";
    if (row) {
        orderId = row.get("orderId");
        houseId = row.get("houseId");
        ownerId = row.get("ownerId");
        tenantId = row.get("tenantId");
    }
    var url;
    if (id == "orderInfo") {
        url = getFullPath("zmgl/zmglEditUI.do") + "&id=" + orderId + "&operation=edit";
    } else if (id == "houseInfo") {
        url = getFullPath("house/houseForm.do") + "&houseId=" + houseId;
    } else if (id == "ownerInfo") {
        url = getFullPath("user/userEditUI.do") + "&id=" + ownerId + "&operation=view";
    } else if (id == "tenantInfo") {
        url = getFullPath("user/userEditUI.do") + "&id=" + tenantId + "&operation=view";
    }
    tabbar.setHrefMode("iframes-on-demand");
    tabbar.setContentHref(id, url);
}

function doDeleteZmxx(orderId, houseId) {
    Ext.MessageBox.confirm("提示", "确定要删除该条记录？", function (btn) {
        if (btn == 'yes') {
            jq.post(getFullPath("zmgl/deleteZmxx.do"), {orderId : orderId, houseId : houseId}, function (result) {
                result = JSON.parse(result);
                if (result.success) {
                    Ext.MessageBox.alert('提示', '删除成功');
                    doFindZmxx();
                } else {
                    Ext.MessageBox.alert("提示", "删除失败");
                }
            });
        }
    });
}

/**
 * 初始化extjsgrid
 * @returns
 */
function doInitGrid() {
    var pageSize = 4;
    var store = new Ext.data.JsonStore({
        "root" : "data",
        "totalProperty" : "totalCount",
        "proxy" : new Ext.data.HttpProxy({
            "url" : getFullPath("zmgl/findZmxx.do")
        }),
        "baseParams" : {
            "start" : 0,
            "limit" : pageSize
        },
        "fields" : [{
            "name" : "orderId",
            "type" : 'int'
        }, {
            "name" : "houseCode"
        },{
            "name" : "owner"
        }, {
            "name" : "tenant"
        },{
            "name" : "leaseTime"
        },{
            "name" : "leaseLength"
        },{
            "name" : "deposit",
            "type" : "float"
        },{
            "name" : "yjje",
            "type" : "float"
        },{
            "name" : "sjje",
            "type" : "float"
        },{
            "name" : "wjje",
            "type" : "float"
        },{
            "name" : "dueDate"
        }, {
            "name" : "ownerId",
            "type" : "int"
        }, {
            "name" : "tenantId",
            "type" : "int"
        }, {
            "name" : "houseId",
            "type" : "int"
        }]
    });

    var selectModel = new Ext.grid.CheckboxSelectionModel({
        "checkOnly" : false,
        "singleSelect" : false
    });

    var grid = new Ext.grid.GridPanel({
        "store" : store,
        "columns" : [
            selectModel,
            new Ext.grid.RowNumberer({
                "header" : "序号",
                "width" : 50
            }),
            {
                "id" : "orderId",
                "hidden" : true,
                "dataIndex" : "orderId",
                "menuDisabled" : true,
                "sortable" : false
            },
            {
                "header" : "房屋编号",
                "width" : 180,
                "align" : "center",
                "dataIndex" : "houseCode",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "房屋拥有者",
                "width" : 150,
                "align" : "center",
                "dataIndex" : "owner",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "租房者",
                "width" : 60,
                "align" : "center",
                "dataIndex" : "tenant",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "出租日期",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "leaseTime",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "出租时长",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "leaseLength",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "押金",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "deposit",
                "menuDisabled" : true,
                "sortable" : false
            },
            {
                "header" : "应交金额",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "yjje",
                "menuDisabled" : true,
                "sortable" : false
            },
            {
                "header" : "实交金额",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "sjje",
                "menuDisabled" : true,
                "sortable" : false
            },
            {
                "header" : "未交金额",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "wjje",
                "menuDisabled" : true,
                "sortable" : false
            },
            {
                "header" : "截止日期",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "dueDate",
                "menuDisabled" : true,
                "sortable" : false
            },
            {
                "xtype" : "actioncolumn",
                "header" : "删除",
                "width" : 80,
                "align" : "center",
                "menuDisabled" : true,
                "sortable" : false,
                items : [{
                    "icon" : getFullPath("resource/icons/delete.gif"),
                    "tooltip" : "删除",
                    "handler" : function(grid, rowIndex, colIndex) {
                        doDeleteZmxx(store.getAt(rowIndex).get("orderId"),store.getAt(rowIndex).get("houseId"));
                    }
                }]
            }, {
                "hidden" : true,
                "dataIndex" : "ownerId",
                "menuDisabled" : true,
                "sortable" : false
            },{
                "hidden" : true,
                "dataIndex" : "tenantId",
                "menuDisabled" : true,
                "sortable" : false
            },{
                "hidden" : true,
                "dataIndex" : "houseId",
                "menuDisabled" : true,
                "sortable" : false
            }],
        "selModel" : selectModel,
        "renderTo" : "zmGrid",
        "viewConfig" : {
            "forceFit" : true
        },
        "bbar" : new Ext.PagingToolbar({
            "store" : store,
            "pageSize" : pageSize,
            "displayInfo" : true,
            "displayMsg": '显示{0}-{1}条, 共{2}条'
        }),
        listeners : {
            cellclick : function (grid, rowIndex, columnIndex, e) {
                if (columnIndex != 0 && columnIndex != 1 && columnIndex != 13 && columnIndex != 14) {
                    showZmDetail(tabbar.getActiveTab());
                }
            }
        }
    });
    store.on("beforeload", function() {
        this.baseParams.houseCode = trim(jq("#zmglInfoHeader #houseCode").val());
        this.baseParams.tenantName = trim(jq("#zmglInfoHeader #tenantName").val());
        this.baseParams.leaseTime1 = trim(jq("#zmglInfoHeader #leaseTime1").val());
        this.baseParams.leaseTime2 = trim(jq("#zmglInfoHeader #leaseTime2").val());
    });

    //重写刷新方法,默认为this.doLoad(this.cursor)，即刷新当前页
    grid.getBottomToolbar().refresh.handler = function() {
        doFindZmxx();
    };
    grid.show();
    return grid;
}