<template>
  <div style="margin-top: 30px">

    <el-row style="margin-top: 20px">
    <span style="font-size: 20px; color: black; margin-left: 10px">修改个人信息</span>
    </el-row>
    <el-row style=" margin-top:50px; margin-left: 100px">
      <el-col :span="4">
        <el-upload ref="userUpload" class="avatar-uploader upload-border"
                   action="" accept="image/gif,image/jpeg,image/jpg,image/bmp,image/png"
                   :show-file-list="false"
                   :auto-upload="false" :before-upload="beforeUploadUser">
          <img v-if="picture" :src="pictureUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-button style="margin-left: 10px; margin-top: 30px" size="small" type="success" @click="uploadUserPicture">上传头像</el-button>
        <br>
        <el-button style="margin-left: 10px; margin-top: 30px" size="small" type="success" @click="modalVisible = true;">修改密码</el-button>
      </el-col>
      <el-col :span="20">
        <el-form label-width="80px" :model="user" :rules="rules" ref="userForm">
          <el-row>
            <el-col :span="20">
              <el-form-item label="真实姓名" prop="realName">
                <el-input style="width: 400px" v-model="user.realName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="20">
              <el-form-item label="邮箱" prop="email">
                <el-input style="width: 400px" v-model="user.email"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="20">
              <el-form-item label="生日" prop="birthday">
                <el-date-picker style="width: 400px;" v-model="user.birthday" format="yyyy-MM-dd" value-format="yyyy-MM-dd"></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="20">
              <el-form-item label="联系电话"  prop="mobile">
                <el-input style="width: 400px" v-model="user.mobile"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="性别" prop="gender">
                <el-radio label="1" v-model="user.gender">男</el-radio>
                <el-radio label="2" v-model="user.gender">女</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-col>
    </el-row>
    <div style="text-align: center; margin-top: 40px">
      <el-button type="primary" @click="saveUserInfo">保存</el-button>
    </div>

    <div>
      <el-dialog  title="修改密码" center :visible.sync="modalVisible" v-if="modalVisible">
          <el-form ref="passwordForm" label-width="80px" style="margin-left: 80px; margin-top: 20px" :model="passwords" :rules="passwordRules">
            <el-row :span="10">
              <el-form-item label="原密码" prop="oldPassword">
                <el-input type="password" style="width: 230px;" v-model="passwords.oldPassword"></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="新密码" prop="newPassword">
                <el-input type="password" style="width: 230px ;" v-model="passwords.newPassword"></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="确认密码" prop="newPassword2">
                <el-input type="password" style="width: 230px" v-model="passwords.newPassword2"></el-input>
              </el-form-item>
            </el-row>

            <div style="text-align: center; margin-top: 40px">
              <el-button type="primary" @click="updatePassword">保存</el-button>
              <el-button @click="modalVisible = false;">取消</el-button>
            </div>
          </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>

    export default {
      name: "grxx",
        data : function () {
          return {
              user : {},
              pictureName : "",
              passwords : {
                  oldPassword : "",
                  newPassword : "",
                  newPassword2 : ""
              },
              rules : {
                email : [
                  {validator : function (rule, val, callback) {
                      if (val && !(/^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+(\.[a-zA-Z]+)+/.test(val))) {
                        return callback(new Error("邮箱格式不正确"));
                      } else {
                        callback();
                      }
                    }, trigger : 'blur'}
                ]
              },
              passwordRules : {
                oldPassword : [
                  {required : true, message : "请输入原密码", trigger : 'blur'}
                ],
                newPassword : [
                  {required : true, message : "请输入新密码", trigger : 'blur'}
                ],
                newPassword2 : [
                  {required : true, message : "请再次输入新密码", trigger : 'blur'}
                ]
              },
              modalVisible : false
          }
        },
        computed : {
          username : function () {
              return this.$store.state.username;
          },
          picture : function () {
              return this.pictureName;
          },
          pictureUrl : function () {
              return this.$axios.defaults.baseURL + "/showUserPicture.do?t=" + Math.random() + "&pictureName=" + this.pictureName;
          }
        },
        methods : {
          beforeUploadUser : function (file) {
            var isImage = (/\.(gif|jpg|jpeg|png|bmp|BMP|GIF|JPG|PNG)$/.test(file.name));
            var isLt2M = file.size / 1024 / 1024 < 2;

            if (!isImage) {
              this.$message.error('上传的必须是图片');
              return false;
            }
            if (!isLt2M) {
              this.$message.error('上传头像图片大小不能超过 2MB!');
              return false;
            }
              var params = new FormData();
              params.append("file", file);
              this.$axios.post("userPictureUpload.do?t=" + Math.random(), params)
                .then(res => {
                    var result = res.data;
                    if (result.success) {
                        this.pictureName = result.data;
                    }
                }).catch(error => {
                    console.log(error);
              });
          },
          uploadUserPicture : function () {
              this.$refs.userUpload.submit();
          },
          saveUserInfo : function () {
              var form = this.$refs.userForm;
              form.validate((valid) => {
                  if (!valid) {
                    this.$alert("请检查信息是否正确");
                    return false;
                  } else {
                    var params = this.$qs.stringify(this.user);
                    if (this.pictureName) {
                      params += "&pictureName=" + this.pictureName;
                    }
                    this.$axios.post("user/updateUser.do?t=" + Math.random(), params)
                      .then(res => {
                        var result = res.data;
                        if (result.success) {
                          this.$alert("保存成功");
                        }
                      }).catch(error => {
                      console.log(error);
                    });
                  }
              });
          },
          doShowUserPicture : function () {
              this.$axios.post("getUserPicture.do?t=" + Math.random())
                .then(res => {
                    var result = res.data;
                    if (result.success) {
                        if (result.data) {
                            this.pictureName = result.data.pictureName;
                        }
                    }
                }).catch(error => {
                    console.log(error);
              });
          },
          updatePassword : function () {
              var form = this.$refs.passwordForm;
              form.validate((valid) => {
                  if (valid) {
                    if (this.passwords.oldPassword == this.passwords.newPassword) {
                      this.$alert("新旧密码一致");
                      return false;
                    }
                    if (this.passwords.newPassword != this.passwords.newPassword2) {
                      this.$alert("两次输入的密码不一致");
                      return false;
                    }
                    this.$axios.post("user/updatePassword.do", this.$qs.stringify(this.passwords))
                      .then(res => {
                        var result = res.data;
                        if (result.success) {
                          this.$alert("修改成功");
                          form.resetFields();
                          this.modalVisible = false;
                        } else {
                          this.$alert(result.msg);
                        }
                      }).catch(error => {
                      console.log(error);
                    })
                  } else {
                    this.$alert("请检查必填项");
                    return false;
                  }
              })
          }
        },
        created : function () {
            this.$axios.post("user/getUser.do?t=" + Math.random())
              .then(res => {
                var result = res.data;
                  if (result.success) {
                    this.user = result.data;
                    this.doShowUserPicture();
                  }
              }).catch(error => {
                console.log(error);
            })
        }
    }
</script>

<style scoped>

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .upload-border {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 178px;
    height: 178px;
  }


  .upload-border:hover {
    border-color: #409EFF;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

  .el-row {
    margin-left: 150px
  }
</style>
