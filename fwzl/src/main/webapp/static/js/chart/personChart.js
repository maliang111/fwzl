var jq = jQuery.noConflict();

jq(function () {

    doGetHouseCount();

});

function doGetHouseCount() {

    jq.post(getFullPath("tjfx/getPersonalCount.do"), function (result) {
        result = JSON.parse(result);
        if (result.success) {
            doInitHouseDailyChart(result.data);
        } else {
            alert("查询失败");
        }
    })

}


function doInitHouseDailyChart(data) {
    var labelArray = [];
    var dataArray = [];
    var backgroundColor = [];
    for (var i = 0; i < data.length; i++) {
        labelArray.push(data[i].ownerName);
        dataArray.push(data[i].houseCount);
        var r = randomFrom(50, 255);
        var g = randomFrom(95, 210);
        var b = randomFrom(64, 255);
        backgroundColor.push('rgba(' + r + ', ' + g + ', ' + b + ', 0.6)');
    }

    var container = jq("#personChart");

    new Chart(container, {
        type : 'pie',
        data : {
            labels : labelArray,
            datasets : [
                {
                    data : dataArray,
                    backgroundColor : backgroundColor
                }
            ]

        }
    });
}