var jq = jQuery.noConflict();

jq(function () {
   doInitPanel();
});


function doInitPanel() {

    var dailyPath = getFullPath("tjfx/houseDailyChart.do");

    var personPath = getFullPath("tjfx/personChart.do");

    var tab = new Ext.TabPanel({
        activeTab: 0,
        items : [
            {
                title : '每日房屋发布数',
                html : "<iframe width='100%' height='100%' src='" + dailyPath + "'></iframe>"
            },
            {
                title : '个人发布数占比',
                html : "<iframe width='100%' height='100%' src='" + personPath + "'></iframe>"
            }
        ]
    });
    new Ext.Viewport({
        layout : "border",
        border : false,
        items : [{
            region : "center",
            layout : "fit",
            border : false,
            items : [ tab ]
        } ]
    });
}

