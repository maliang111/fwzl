/**
 * 获取以url结尾的完整路径名
 * @param url
 * @returns
 */
function getFullPath(url) {
	var protocol = location.protocol;	//获得协议名
	var host = location.host;	//获得主机名和端口号
	var pathName = location.pathname;	//获得url路径部分
	var projectName;
	if (pathName.indexOf("/") == 0) {
		projectName = pathName.substring(1, pathName.indexOf("/", 1));	//截取出当前项目名
	} else {
		projectName = pathName.substring(0, pathName.indexOf("/", 1));
	}
    //拼接出完整字符串
	return (protocol + "//" + host + "/" + projectName + "/" + url + "?t=" + Math.random());
}


/**
 * 将参数进行二次编码
 * */
function encodingStr(val) {
	return encodeURIComponent(encodeURIComponent(trim(val)));
}

/**
 * 去掉空格
 * @param value 需要去空格的值
 * @returns
 */
function trim(value) {
    if (value) {
    	value = value.replace(/^\s*|\s*$/g, "");
    }
    if (!value) {
        return "";
    }
    else {
        return value;
    }
}

function openWin(url, width, height) {
    var Left_size = (screen.width) ? (screen.width-width)/2 : 0;
    var Top_size = (screen.height) ? (screen.height-height)/2 : 0;
    window.open(url, '_blank', 'width=' + width + 'px, height=' + height + 'px, left=' + Left_size + 'px, top=' + Top_size + 'px,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no' );
}


function fillFormData(form,data) {
    jq.each(data, function(name, value) {
        form.find(jq("[name=" + name + "] ")).val(value);
    })
}


function randomFrom(lowerValue,upperValue)
{
    return Math.floor(Math.random() * (upperValue - lowerValue + 1) + lowerValue);
}
function disableForm(id) {
    jq('#' + id).find("input,select,textarea").each(function() {
        // jq(this).attr("disabled","disabled");
        if (jq(this).attr('tagName') == 'SELECT' || jq(this).attr('type') == 'checkbox' || jq(this).attr('type') == 'radio') {
            jq(this).wrap('<span onmousemove= "this.setCapture(); " onmouseout= "this.releaseCapture(); "></span>');
            jq(this).focus(function() {
                this.blur();
            });
            jq(this).attr("disabled","disabled");
        } else if (jq(this).attr('class') == 'sala_dp') {
            jq(this).removeClass();
            jq(this).attr('class', 'sala_definput');
            jq(this).attr('onclick', '');
            jq(this).attr('onfocus', '');
            jq(this).attr("readOnly", "readOnly");
        } else {
            jq(this).attr("readOnly", "readOnly");
        }
    });
}