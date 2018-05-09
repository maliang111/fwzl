var jq = jQuery.noConflict();

jq(function(){
	
	jq('#switch_qlogin').click(function(){
		jq('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
		jq('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
		jq('#switch_bottom').animate({left:'0px',width:'70px'});
		jq('#qlogin').css('display','none');
		jq('#web_qr_login').css('display','block');
		
		});
	jq('#switch_login').click(function(){
		
		jq('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
		jq('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
		jq('#switch_bottom').animate({left:'154px',width:'70px'});
		
		jq('#qlogin').css('display','block');
		jq('#web_qr_login').css('display','none');
		});

	jq("#btn_login").bind("click", function () {
		doLogin();
    });

	jq("#passwd2").bind("blur", function () {
       doCheckPassword();
    });

	jq("#email").bind("blur", function () {
        doCheckEmail();
    });

	jq("#user").bind("blur", function () {
		doCheckUser();
    });

	jq("#reg").bind("click", function () {
        doRegister();
    })
});


function doCheckEmail() {
    var email = trim(jq("#email").val());
    if (email) {
        if (!(/^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+(\.[a-zA-Z]+)+/.test(email))) {
            alert("email的格式不正确");
            return false;
        }
    }
    return true;
}

	
function doRegister() {
    if (!doCheckUser()) {
        return;
    }
    if (!doCheckPassword()) {
        return;
    }

    if (!doCheckEmail()) {
        return;
    }

    var params = jq("#register_form").serialize();

    jq.post(getFullPath("user/register.do"), params, function (result) {
       console.log(result);
       result = JSON.parse(result);
       if (result.success) {
           alert("注册成功");
           window.location.reload();
       }
    });

}

function doCheckPassword() {
    var password1 = trim(jq("#passwd").val());
    var password2 = trim(jq("#passwd2").val());
    if (password1 != password2) {
        alert("两次密码不一致");
        return false;
    }
    return true;
}

function doCheckUser() {
	var username = trim(jq("#user").val());
	if (!username) {
	    alert("用户名不能为空");
	    return false;
    }
    var rtn = true;
    jq.ajax({
        url : getFullPath("user/findUserByUsername.do"),
        dataType : "text",
        async : false,
        type : "POST",
        data : {username : username},
        success : function (result) {
            result = JSON.parse(result);
            if (!result.success) {
                alert("该用户已存在");
                rtn = false;
            }
        },
        error : function (error) {
            console.log(error);
        }
    });
    return rtn;
}

function doLogin() {
	var params = {
		"username" : trim(jq("#username").val()),
		"password" : trim(jq("#password").val())
	};

	jq.ajax({
		url : getFullPath("login.do"),
		data : params,
		dataType : "text",
		type : "POST",
		success : function (result) {
			result = JSON.parse(result);
			if (result.success) {
                alert("登录成功");
                window.location.href = getFullPath("indexUI.do");
            } else {
				alert("用户名或密码错误");
			}
        },
		error : function (error) {
			console.log(error);
        }
	})

}