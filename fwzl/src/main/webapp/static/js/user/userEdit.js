var jq = jQuery.noConflict();

jq(function () {

    var params = window.location.href.split("&");
    var id = trim(jq("#userEditForm :input[name=userId]").val());
    if (!id) {
        jq("#userEditForm :input[name=close]").css("display", "none");
    }
    var operation = trim(jq("#userEditForm :input[name=operation]").val());
    if (operation != 'add' && id) {
        doFindUserById(id);
        doFindUserPicture(id);
    } else if (!id) {
        jq("#userImage").attr("src", getFullPath("showUserPicture.do") + "&pictureName=");
    }

    jq("#userEditForm :input[name=uploadImage]").bind("click", function () {
       doUploadImage();
    });

    jq("#userEditForm :input[name=saveUser]").bind("click", function () {
       doUpdateUser();
    });


    jq("#userEditForm :input[name=close]").bind("click", function () {
       window.close();
    });
    
    setFormStyle();

});

function setFormStyle() {
    var operation = trim(jq("#userEditForm :input[name=operation]").val());
    if (operation == 'view') {
        jq("#userEditForm :input[name=uploadImage]").css("display", "none");
        jq("#userEditForm :input[name=file]").css("display", "none");
        jq("#formDiv").css("padding-top", 0);
        jq("#titleDiv").css("display", "none");
        jq("#btns").css("display", "none");
        disableForm("userEditForm");
    }

    if (jq("#userEditForm :input[name=source]").val()) {
        jq("#titleDiv").css("display", "none");
    }
}

function doUpdateUser() {
  var params = jq("#userEditForm").serialize();
  jq.post(getFullPath("user/updateUser.do"), params, function (result) {
      result = JSON.parse(result);
      if (result.success) {
          alert("更新成功");
          if (window.opener) {
              window.opener.doFindUser(true);
              window.close();
          }
      } else {
          alert("更新失败");
      }
  });
};

function uploadResult(result) {
    result = JSON.parse(result);
    if (result.success) {
        alert("上传成功");
        var picture = result.data;
        jq("#userEditForm :input[name=pictureName]").val(trim(picture));
        jq("#userImage").attr("src", getFullPath("showUserPicture.do") + "&pictureName=" + picture);
    } else {
        alert("上传失败");
    }
}

function doUploadImage() {
    jq("#userEditForm").attr("action", getFullPath("userPictureUpload.do") + "&style=script");
    jq("#userEditForm").submit();
}

function doFindUserPicture(id) {
    jq.post(getFullPath("getUserPicture.do"), {id : id}, function (result) {
        result = JSON.parse(result);
        if (result.success) {
            var picture = result.data;
            if (picture) {
                jq("#userImage").attr("src", getFullPath("showUserPicture.do") + "&pictureName=" + picture.pictureName);
            } else {
                jq("#userImage").attr("src", getFullPath("showUserPicture.do") + "&pictureName=");
            }
        }
    })
}

function doFindUserById(id) {
    jq.post(getFullPath("user/findUserById.do"), {id : id}, function (result) {
        result = JSON.parse(result);
        if (result.success) {
            var user = result.data;
            jq("#userEditForm :input[name=username]").val(trim(user.username));
            jq("#userEditForm :input[name=realName]").val(trim(user.realName));
            jq("#userEditForm :input[name=email]").val(trim(user.email));
            jq("#userEditForm :input[name=birthday]").val(trim(user.birthday));
            jq("#userEditForm :input[name=mobile]").val(trim(user.mobile));
            var gender = trim(user.gender);
            if (gender) {
                gender == "1"? jq("#userEditForm #gender_male").attr("checked", "checked")
                    : jq("#userEditForm #gender_female").attr("checked", "checked");
            }
        }
    })
}