var jq = jQuery.noConflict();
var toolbar;
jq(function () {
    var id = trim(jq("#zmglInfoForm :input[name=orderId]").val());
    var operation = trim(jq("#zmglInfoForm :input[name=operation]").val());
    if (operation != 'add' && id) {
        doFindOrderById(id);
    }
    jq("#viewHouse").bind("click", function () {
       doShowHouseWindow();
    });

    jq("#yjje").bind("blur", function () {
        doCheckYjje();
    });

    jq("#sjje").bind("blur", function () {
        doCheckSjje();
    });

    jq("#viewTenant").bind("click", function () {
        doShowTenantWindow();
    });
    doLoadToolbar();
    setFormStyle(operation);
    
});

function doCheckYjje() {
    if (isNaN(jq("#yjje").val())) {
        alert("不是数字");
        return false;
    }
    return true;
}

function doCheckSjje() {
    if (isNaN(jq("#yjje").val())) {
        alert("不是数字");
        return false;
    }
    if (parseFloat(jq("#yjje").val()) < parseFloat(jq("#sjje").val())) {
        alert("实交金额不能大于应交金额");
        return false;
    }
    doCalculateWjje();
    return true;
}

function doCalculateWjje() {

    jq("#wjje").val(parseFloat(jq("#yjje").val()) - parseFloat(jq("#sjje").val()));
}

function doShowTenantWindow() {
    if (!jq("#zmglInfoForm :input[name=houseId]").val()) {
        alert("请先选选择房屋");
        return;
    }
    openWin(getFullPath("user/yhxx.do") + "&operation=view&yhid=" + jq("#zmglInfoForm :input[name=ownerId]").val()
        , 1000, 600);
}


function doLoadToolbar() {
    toolbar = new dhtmlXToolbarObject("toolbar");
    var imagePath = getFullPath("dhtmlxToolBar/imgs/");
    imagePath = imagePath.substring(0, imagePath.indexOf("?"));
    toolbar.setSkin('dhx_skyblue');
    toolbar.loadXML(getFullPath("static/xml/toolbar.xml"));
    toolbar.attachEvent("onClick", function (id) {
        if (id == "new") {
            window.location.href = getFullPath("zmgl/zmglEditUI.do") + "&operation=add";
        } else if (id == "save") {
            doSave();
        }
    })
}


function doCheck() {
    if (!jq("#zmglInfoForm :input[name=houseCode]").val()) {
        return false;
    }
    if (!jq("#zmglInfoForm :input[name=ownerName]").val()) {
        return false;
    }
    if (!jq("#zmglInfoForm :input[name=address]").val()) {
        return false;
    }
    if (!jq("#zmglInfoForm :input[name=tenant]").val()) {
        return false;
    }
    if (!jq("#zmglInfoForm :input[name=leaseTime]").val()) {
        return false;
    }
    if (!jq("#zmglInfoForm :input[name=leaseLength]").val()) {
        return false;
    }
    if (!jq("#zmglInfoForm :input[name=deposit]").val()) {
        return false;
    }
    if (!doCheckYjje()) {
        return false;
    }
    if (!doCheckSjje()) {
        return false;
    }
    if (!jq("#zmglInfoForm :input[name=dueDate]").val()) {
        return false;
    }
    return true;
}


function doSave() {

    if (!doCheck()) {
        alert('所有项均必填，请检查');
        return;
    }

    var params = jq("#zmglInfoForm").serialize();

    jq.post(getFullPath("zmgl/doSaveZmxx.do"), params, function (result) {
        result = JSON.parse(result);
        if (result.success) {
            alert("保存成功");
            window.parent.location.reload();
        } else {
            alert("保存失败");
        }
    });

}

function setFormStyle(operation) {
    if (operation == 'view') {
        jq("#viewHouse").css("display", "none");
        jq("#viewTenant").css("display", "none");
    }

}


function doFindOrderById(id) {

    jq.post(getFullPath("zmgl/findZmxxById.do"), {id : id}, function (result) {
        result = JSON.parse(result);
        if (result.success) {
            fillFormData(jq("#zmglInfoForm"), result.data);
        } else {
            alert("加载账目信息失败");
        }
    })
}

function setTenantId(tenantId) {
    jq("#zmglInfoForm :input[name=tenantId]").val(tenantId);
}

function setTenantName(tenant) {
    jq("#zmglInfoForm :input[name=tenant]").val(tenant);
}

function setHouseId(houseId) {
    jq("#zmglInfoForm :input[name=houseId]").val(houseId);
}

function setHouseCode(houseCode) {
    jq("#zmglInfoForm :input[name=houseCode]").val(houseCode);
}

function setOwnerName(ownerName) {
    jq("#zmglInfoForm :input[name=ownerName]").val(ownerName);
}


function setOwnerId(ownerId) {
    jq("#zmglInfoForm :input[name=ownerId]").val(ownerId);
}


function setAddress(address) {
    jq("#zmglInfoForm :input[name=address]").val(address);
}

function doShowHouseWindow() {

    openWin(getFullPath("house/houseInfo.do"), 1000, 600);


};