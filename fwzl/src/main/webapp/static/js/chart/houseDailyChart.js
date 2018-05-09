var jq = jQuery.noConflict();

jq(function () {

    doGetHouseCount();

});

function doGetHouseCount() {

    jq.post(getFullPath("tjfx/getHouseDailyCount.do"), function (result) {
        result = JSON.parse(result);
        if (result.success) {
            doInitHouseDailyChart(result.data);
        }
    })

}


function doInitHouseDailyChart(data) {
    var labelArray = [];
    var dataArray = [];
    var backgroundColor = [];
    for (var i = 0; i < data.length; i++) {
        labelArray.push(data[i].leaseTime);
        dataArray.push(data[i].houseCount);
        var r = randomFrom(50, 255);
        var g = randomFrom(95, 210);
        var b = randomFrom(64, 255);
        backgroundColor.push('rgba(' + r + ', ' + g + ', ' + b + ', 0.6)');
    }

    var container = jq("#houseDailyChart");

    new Chart(container, {
        type : 'bar',
        data : {
            labels : labelArray,
            datasets : [
                {
                    label: '每日发布房屋数',
                    data : dataArray,
                    backgroundColor : backgroundColor,
                    borderWidth: 1
                }
            ]

        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    })



}