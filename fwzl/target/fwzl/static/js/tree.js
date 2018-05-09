function doInitTree() {
	var loader = new Ext.tree.TreeLoader({
		url : getFullPath("module/getModules.do")
	});
    var tree = new Ext.tree.TreePanel({
        title : "功能列表",
        "renderTo" : "moduleTree",
        "height" : 900,
        "width" : 200,
        "border" : false,
        "autoScroll" : true,
        "lines" : true,
        "rootVisible" : false,
        "autoHeight" : false,
        "singleExpand" : false,
		"loader" : loader,
        "root" : {
            "nodeType" : "async"
        },
        "listeners" : {
            "click" : function(node) {
                //如果不是叶子节点
                if (!node.isLeaf()) {
                    //如果未展开
                    if (!node.isExpanded()) {
                        node.expand();
                    } else {
                        node.collapse();
                    }
                } else {
                    doLoadPage(node);
                }
            }
        }
    });
    tree.show();
    return tree;

}

//根据nodeId加载页面
function doLoadPage(node) {
    jq.post(getFullPath("module/getModuleUrl.do"), {"id" : node.id}, function(result) {
        result = JSON.parse(result);
        if (result.success) {
            Ext.getCmp("mainPanel").addNode("tab_module_" + node.id, node.text, getFullPath(result.data));
        } else {
            alert("载入页面失败");
        }
    });
}