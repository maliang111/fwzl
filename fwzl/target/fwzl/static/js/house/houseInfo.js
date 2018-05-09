var jq = jQuery.noConflict();

var houseGrid;
jq(function () {

    houseGrid = doInitGrid();
    doFindHouses();

    jq("#houseInfoHeader #searchHouse").bind("click", function () {
        doFindHouses();
    });

    var view = new Ext.Viewport({
        layout : "border",
        items : [{
            "region" : "north",
            "height" : 70,
            "contentEl" : "houseInfoHeader"
        }, {
            "region" : "center",
            "layout" : "fit",
            items : [houseGrid]
        }]
    })
});


function doFindHouses() {

    var limit = houseGrid.getBottomToolbar().pageSize;
    var start = 0;
    houseGrid.getStore().load({
        "params" : {
            "start" : start,
            "limit" : limit
        },
        "callback" : function(r, option, success) {
            if (!success) {
                alert("查询失败");
            }
        }
    })

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
            "url" : getFullPath("house/findHouses.do")
        }),
        "baseParams" : {
            "start" : 0,
            "limit" : pageSize
        },
        "fields" : [{
            "name" : "houseId",
            "type" : 'int'
        }, {
            "name" : "ownerId",
            "type" : 'int'
        },{
            "name" : "houseCode"
        },{
            "name" : "address"
        }, {
            "name" : "ownerName"
        },{
            "name" : "leaseTime"
        },{
            "name" : "residence"
        },{
            "name" : "size",
            "type" : "float"
        },{
            "name" : "rent",
            "type" : "float"
        },{
            "name" : "note"
        }]
    });

    var selectModel = new Ext.grid.RowSelectionModel({
        // 单行选择模式
        singleSelect : true
    });

    var grid = new Ext.grid.GridPanel({
        "store" : store,
        selModel : selectModel,
        "columns" : [
            new Ext.grid.RowNumberer({
                "header" : "序号",
                "width" : 50
            }),
            {
                "id" : "houseId",
                "hidden" : true,
                "dataIndex" : "houseId",
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
                "header" : "地址",
                "width" : 150,
                "align" : "center",
                "dataIndex" : "address",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "出租者姓名",
                "width" : 60,
                "align" : "center",
                "dataIndex" : "ownerName",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "发布时间",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "leaseTime",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "户型",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "residence",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "大小",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "size",
                "menuDisabled" : true,
                "sortable" : false
            },
            {
                "header" : "租金",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "rent",
                "menuDisabled" : true,
                "sortable" : false
            },
            {
                "header" : "备注",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "note",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "hidden" : true,
                "dataIndex" : "ownerId",
                "menuDisabled" : true,
                "sortable" : false
            }],
        "renderTo" : "houseGrid",
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
            rowdblclick : function (grid, rowIndex, e) {
                var row = grid.getStore().getAt(rowIndex);
                var houseId = row.get("houseId");
                var ownerId = row.get("ownerId");
                var address = row.get("address");
                var houseCode = row.get("houseCode");
                var ownerName = row.get("ownerName");
                window.opener.setHouseId(houseId);
                window.opener.setOwnerId(ownerId);
                window.opener.setHouseCode(houseCode);
                window.opener.setAddress(address);
                window.opener.setOwnerName(ownerName);
                window.close();
            }
        }
    });
    store.on("beforeload", function() {
         this.baseParams.houseCode = trim(jq("#houseInfoHeader #houseCode").val());
         this.baseParams.ownerName = trim(jq("#houseInfoHeader #ownerName").val());
         this.baseParams.isLeased = trim("0");
         this.baseParams.leaseTime1 = trim(jq("#houseInfoHeader #leaseTime1").val());
         this.baseParams.leaseTime2 = trim(jq("#houseInfoHeader #leaseTime2").val());
    });

    //重写刷新方法,默认为this.doLoad(this.cursor)，即刷新当前页
    grid.getBottomToolbar().refresh.handler = function() {
        //刷新到第一页
        this.doLoad(0);
    };
    grid.show();
    return grid;
}