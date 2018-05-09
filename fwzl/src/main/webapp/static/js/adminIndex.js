var jq = jQuery.noConflict();
var tree;
jq(function () {
    tree = doInitTree();
    doInitPanel();

    jq("#logout").bind("click", function () {
        doLogout();
    });
});

function doLogout() {
    jq.post(getFullPath("logout.do"), function (result) {
        result = JSON.parse(result);
        if (result.success) {
            window.location.href = getFullPath("loginUI.do");
        }
    })
}

function doInitPanel() {
    new Ext.Viewport({
        "layout" : "border",
        "items" : [
            {
                "region" : "north",
                contentEl : "header"
            },
            {
                "region" : 'center',
                "layout" : "fit",
                border:true,
                "items" : [
                    new Ext.ux.MainTabPanel({
                        "id" : "mainPanel",
                        border:false
                    })
                ]
            },
            {
                "region" : "west",
                "width" : 200,
                "items" : [tree]
            }
        ]
    });
}