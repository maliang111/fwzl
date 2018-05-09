var jq = jQuery.noConflict();

var userGrid;
jq(function () {

    userGrid = doInitGrid();
    doFindUser();
    if (jq("#userInfoForm :input[name=operation]").val() == 'view') {
        doFindUser();
        jq("#deleteUsers").css("display", "none");
    }

    jq("#searchUser").bind("click", function () {
        doFindUser();
    });

    jq("#deleteUsers").bind("click", function () {
        doDeleteUsers();
    });


    var view = new Ext.Viewport({
        layout : "border",
        items : [{
            "region" : "north",
            "height" : 70,
            "contentEl" : "userInfoHeader"
        }, {
            "region" : "center",
            "layout" : "fit",
            items : [userGrid]
        }]
    })
});

function doDeleteUsers() {
    Ext.MessageBox.confirm("提示", "确定要删除该用户？", function (btn) {
        if (btn == 'yes') {
            var selections = userGrid.getSelectionModel().getSelections();
            console.log(selections);
            var ids = "";
            if (selections) {
                for (var i = 0; i < selections.length; i++) {
                    if (ids) {
                        ids += "," + selections[i].id;
                    } else {
                        ids = selections[i].id;
                    }
                }
                if (ids) {
                    jq.post(getFullPath("user/deleteUsers.do"), {ids : ids}, function (result) {
                        result = JSON.parse(result);
                        if (result.success) {
                            Ext.MessageBox.alert("提示", "删除成功");
                            doFindUser();
                        } else {
                            Ext.MessageBox.alert("提示", "删除失败");
                        }
                    })
                } else {
                    Ext.MessageBox.alert("提示", "请至少选择一项");
                }
            } else {
                Ext.MessageBox.alert("提示", "请至少选择一项");
            }
        }
    })
}


function doFindUser(type) {

    var limit = userGrid.getBottomToolbar().pageSize;
    var start = type ? userGrid.getBottomToolbar().cursor : 0;
    userGrid.getStore().load({
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


function doDeleteUser(id) {
    Ext.MessageBox.confirm("提示", "确定要删除该用户？", function (btn) {
        if (btn == 'yes') {
            jq.post(getFullPath("user/deleteUser.do"), {id : id}, function (result) {
                result = JSON.parse(result);
                if (result.success) {
                    Ext.MessageBox.alert('提示', '删除成功');
                    doFindUser();
                } else {
                    Ext.MessageBox.alert("提示", "删除失败");
                }
            });
        }
    });

}

function doModifyUser(id) {

    var path = getFullPath("user/userEditUI.do") + "&id=" + id + "&operation=edit";
    openWin(path, 900, 600);
}

/**
 * 初始化extjsgrid
 * @returns
 */
function doInitGrid() {
    var pageSize = 5;
    var store = new Ext.data.JsonStore({
        "root" : "data",
        "totalProperty" : "totalCount",
        "proxy" : new Ext.data.HttpProxy({
            "url" : getFullPath("user/findUsers.do")
        }),
        "baseParams" : {
            "start" : 0,
            "limit" : pageSize
        },
        "fields" : [{
            "name" : "id",
            "type" : 'int'
        }, {
            "name" : "username"
        }, {
            "name" : "realName"
        },{
          "name" : "email"
        },{
            "name" : "mobile"
        },{
            "name" : "gender"
        },{
            "name" : "birthday"
        },{
            "name" : "valid"
        },{
            "name" : "email"
        }, {
            "name" : "mobile"
        }]
    });

    var selectModel = selectModel = new Ext.grid.CheckboxSelectionModel({
        "checkOnly" : false,
        "singleSelect" : jq("#userInfoForm :input[name=operation]").val() == 'view'
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
                "id" : "id",
                "hidden" : true,
                "dataIndex" : "id",
                "menuDisabled" : true,
                "sortable" : false
            },
            // {
            //     "xtype" : "actioncolumn",
            //     "header" : "权限",
            //     "width" : 80,
            //     "align" : "center",
            //     "menuDisabled" : true,
            //     "sortable" : false,
            //     items : [{
            //         "icon" : getFullPath("resource/icons/user.png"),
            //         "tooltip" : "删除",
            //         "handler" : function(grid, rowIndex, colIndex) {
            //
            //         }
            //     }]
            // },
            {
                "header" : "姓名",
                "width" : 200,
                "align" : "center",
                "dataIndex" : "realName",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "账号",
                "width" : 150,
                "align" : "center",
                "dataIndex" : "username",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "性别",
                "width" : 60,
                "align" : "center",
                "dataIndex" : "gender",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "出生日期",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "birthday",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "邮箱",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "email",
                "menuDisabled" : true,
                "sortable" : false
            }, {
                "header" : "移动电话",
                "width" : 80,
                "align" : "center",
                "dataIndex" : "mobile",
                "menuDisabled" : true,
                "sortable" : false
            },
            // {
            //     "header" : "是否禁用",
            //     "width" : 60,
            //     "align" : "center",
            //     "dataIndex" : "valid",
            //     "menuDisabled" : true,
            //     "sortable" : false
            // },
            {
                "xtype" : "actioncolumn",
                "hidden" : jq("#userInfoForm :input[name=operation]").val() == 'view',
                "header" : "删除",
                "width" : 80,
                "align" : "center",
                "menuDisabled" : true,
                "sortable" : false,
                items : [{
                    "icon" : getFullPath("resource/icons/user_delete.png"),
                    "tooltip" : "删除",
                    "handler" : function(grid, rowIndex, colIndex) {
                        doDeleteUser(store.getAt(rowIndex).get("id"));
                    }
                }]
            }, {
                "xtype" : "actioncolumn",
                "hidden" : jq("#userInfoForm :input[name=operation]").val() == 'view',
                "header" : "修改",
                "width" : 80,
                "align" : "center",
                "menuDisabled" : true,
                "sortable" : false,
                items : [{
                    "icon" : getFullPath("resource/icons/user_edit.png"),
                    "tooltip" : "修改",
                    "handler" : function(grid, rowIndex, colIndex) {
                       doModifyUser(store.getAt(rowIndex).get("id"));
                    }
                }]
            }],
        "selModel" : selectModel,
        "renderTo" : "userGrid",
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
                if (window.opener) {
                    if (jq("#userInfoForm :input[name=operation]").val() == 'view') {
                        var yhid = jq("#userInfoForm :input[name=yhid]").val();
                        var row = grid.getStore().getAt(rowIndex);
                        var id = row.get("id");
                        if (id == yhid) {
                            alert("租房者与房屋拥有者不能一致");
                        } else {
                            var realName = row.get("realName");
                            window.opener.setTenantId(id);
                            window.opener.setTenantName(realName);
                            window.close();
                        }
                    }
                }
            }
        }
    });
    store.on("beforeload", function() {
        this.baseParams.username = trim(jq("#userInfoHeader #username").val());
        this.baseParams.realName = trim(jq("#userInfoHeader #realName").val());
        this.baseParams.birthday = trim(jq("#userInfoHeader #birthday").val());
        this.baseParams.gender = trim(jq("#userInfoHeader #gender").val());
    });

    //重写刷新方法,默认为this.doLoad(this.cursor)，即刷新当前页
    grid.getBottomToolbar().refresh.handler = function() {
        //刷新到第一页
        this.doLoad(0);
    };
    grid.show();
    return grid;
}
