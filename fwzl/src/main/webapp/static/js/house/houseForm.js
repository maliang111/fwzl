var jq = jQuery.noConflict();

jq(function () {

    var houseId = jq("#houseForm :input[name=houseId]").val();
    if (houseId) {
        doFindHouseById();
    }


});

function doFindHouseById() {

    var houseId = jq("#houseForm :input[name=houseId]").val();


    jq.post(getFullPath("house/findHouseById.do"), {houseId : houseId}, function (result) {
        result = JSON.parse(result);
        if (result.success) {
            fillFormData(jq("#houseForm"), result.data);
            doFindHousePictures(houseId);
        } else {
            alert("加载房屋信息失败");
        }
    });

}


function doFindHousePictures(houseId) {
    jq.post(getFullPath("getHousePictures.do"), {houseId : houseId}, function (result) {
        result = JSON.parse(result);

        if (result.success) {
            var pictures = result.data;
            if (pictures && pictures.length) {
                var jqTd = jq("#imageTd");
                for (var i = 0; i < pictures.length; i++) {
                    var jqStr = "<table style='border-collapse: collapse; display: inline; margin: 1px'>";
                    jqStr += "<tr>";
                    jqStr += "<td class='tdInput' style='width: 50px; height: 50px' align=center>";
                    jqStr += "<img style='width: 50px; height: 50px' src='"+getFullPath("getPicture.do") +"&pictureName=" + pictures[i] + "'/>";
                    jqStr += "</td>";
                    jqStr += "</tr>";
                    jqStr += "</table>";
                    jqTd.append(jq(jqStr));
                }
            }
        }

    })
}