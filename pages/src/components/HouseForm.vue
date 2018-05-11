<template>
    <div height="600px">
      <el-row v-if="!addFlag" style="margin-top: 30px; margin-left: 150px">
          <span style="font-size: 20px; color: black; margin-left: 10px">添加房屋信息</span>
      </el-row>
      <el-row :class="{add : !addFlag}">
        <el-col :span="10" v-if="addFlag" style="margin-top: auto">
          <el-carousel trigger="click" style="width: 100%" :interval="4000">
            <el-carousel-item v-for="(picture, index) in pictures" :key="index" style="text-align: center">
              <img :src="housePictureSrc(picture)">
            </el-carousel-item>
          </el-carousel>
        </el-col>

        <el-col :span="addFlag ?13 : 20">
          <el-row style="margin-top: 30px" v-if="addFlag">
            <el-col :span="15">
              <span
                    style="font-size: 40px; color: orangered; margin-left: 60px">{{house.rent}}元/月</span>
            </el-col>
            <el-col :span="6" style="margin-top: 10px">
                <span style="font-size: 15px;">出租人: {{house.ownerName}}</span>
            </el-col>
          </el-row>
          <el-row style="margin-top: 30px">
            <el-form style="height: 100%" ref="houseForm" accept="image/gif,image/jpeg,image/jpg,image/bmp,image/png"
                     label-width="100px" size="mini" :model="house" :rules="operation == 'view' ? null : rules">
              <el-row>
                <el-col :span="8" :offset="3">
                  <el-form-item label="出租时间" prop="leaseTime">
                    <el-date-picker
                      @change="dateChange"
                      v-model="house.leaseTime"
                      type="date"
                      value-format="yyyy-MM-dd"
                      format="yyyy-MM-dd"
                      placeholder="选择日期" :readonly="operation == 'view'">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="15" :offset="3">
                  <el-form-item label="地址" prop="address">
                    <el-input v-model="house.address" :readonly="operation == 'view'"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="10" :offset="3">
                  <el-form-item label="房屋户型" prop="residence">
                    <el-input v-model="house.residence" :readonly="operation == 'view'"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="20" :offset="3">
                  <el-form-item label="是否已出租" prop="isLeased">
                      <el-radio v-model="house.isLeased" label="0" :disabled="operation == 'view'">未出租</el-radio>
                      <el-radio v-model="house.isLeased" label="1" :disabled="operation == 'view'">已出租</el-radio>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="10" :offset="3">
                  <el-form-item label="房屋大小" prop="size">
                    <el-input v-model.number="house.size" :readonly="operation == 'view'">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="租金" prop="rent">
                    <el-input v-model.number="house.rent" :readonly="operation == 'view'">
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="20" :offset="3">
                  <el-form-item label="其他信息" prop="note">
                    <el-input :rows="addFlag ? 2 : 5" type="textarea" v-model="house.note" :readonly="operation == 'view'"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :offset="3">
                  <el-form-item v-if="operation != 'view'" label="图片上传">
                    <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传图片</el-button>
                    <el-upload  style="margin-top: 10px" class="upload-demo" list-type="picture-card"
                                ref="uploadHouse" action="" :auto-upload="false" :before-upload="beforeUploadHouse">
                      <i class="el-icon-plus"></i>
                    </el-upload>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </el-row>
        </el-col>
      </el-row>
      <div style="text-align: center" v-if="operation != 'view'">
        <el-button type="primary" @click="submitHouseForm">提交</el-button>
        <el-button @click="closeHouseForm">取消</el-button>
      </div>
    </div>
</template>

<script>
    import connector from '@/util/connector';
    export default {
      name: "house-form",
        data : function () {
            return {
                house : {},
                pictures : [],
                newPictures : [],
                addFlag : this.operation == 'view' || this.operation == 'edit',
                rules : {
                  leaseTime : [
                    { required: true, message: '请选择日期', trigger: 'blur' }
                  ],
                  address : [
                    { required: true, message: '请输入地址', trigger: 'blur' }
                  ],
                  residence : [
                    { required: true, message: '请输入房屋户型', trigger: 'blur' }
                  ],
                  isLeased : [
                    { required: true, message: '请选择是否已出租', trigger: 'change' }
                  ],
                  size : [
                    {type : 'number', required: true, message : '请输入大小', trigger : 'blur'}
                  ],
                  rent : [
                    {type : 'number', required: true, message : '请输入租金', trigger : 'blur'}
                  ]
                }
            }
        },
        created : function () {
          if (this.addFlag) {
            this.doFindHouseById();
          }
        },
        computed : {
          id : function () {
              return this.houseId;
          }
        },
        props : ['visible', 'operation', 'houseId'],
        methods : {
          dateChange : function (val) {
            this.house.leaseTime = val;
          },
          housePictureSrc : function (picture) {
            return this.$axios.defaults.baseURL + "/getPicture.do?t=" + Math.random() + "&pictureName=" + picture;
          },
          doFindHouseById : function () {
              this.$axios.post("house/findHouseById.do?t=" + Math.random(), "houseId=" + this.id)
                .then(res => {
                    var result = res.data;
                    if (result.success) {
                      this.house = result.data;
                      this.getHousePictures(this.id);
                    }

                }).catch(error => {
                    console.log(error);
              });
          },
          getHousePictures : function (id) {
              this.$axios.post("getHousePictures.do?t=" + Math.random(), "houseId=" + id)
                .then(res => {
                    var result = res.data;
                    if (result.success) {
                      if (result.data.length == 0) {
                        this.pictures.push("");
                      } else {
                        this.pictures = result.data;
                      }
                    }
                }).catch(error => {
                    console.log(error);
              });
          },
          beforeUploadHouse : function (file) {
            var isImage = (/\.(gif|jpg|jpeg|png|bmp|BMP|GIF|JPG|PNG)$/.test(file.name));
            var isLt2M = file.size / 1024 / 1024 < 2;

            if (!isImage) {
              this.$message.error('上传的必须是图片');
              return false;
            }
            if (!isLt2M) {
              this.$message.error('上传图片大小不能超过 2MB!');
              return false;
            }

            var data = new FormData();
            data.append("file", file);
            this.$axios.post("housePictureUpload.do?t=" + Math.random(), data)
              .then(res => {
                var result = res.data;
                if (result.success) {
                    for (var i = 0; i < this.pictures.length; i++) {
                      if (this.pictures[i] == "") {
                        this.pictures.splice(i, 1);
                        break;
                      }
                    }
                    this.pictures.push(result.data);
                    this.newPictures.push(result.data);
                } else {
                    this.$alert("上传失败");
                }
              }).catch(error => {
              console.log(error);
            });
          },
          submitUpload : function () {
              this.$refs.uploadHouse.submit();
          },
          submitHouseForm : function () {
            this.$refs.houseForm.validate((valid) => {
                if (valid) {
                  var params = this.$qs.stringify(this.house);
                  if (this.addFlag) {
                    params += "&houseId=" + this.id;
                  }
                  var pictureString = "";
                  for (var i = 0; i < this.newPictures.length; i++) {
                    var picture = this.newPictures[i];
                    if (picture) {
                      if (pictureString) {
                        pictureString += "," + picture;
                      } else {
                        pictureString = picture;
                      }
                    }
                  }
                  if (pictureString) {
                    params += "&pictures=" + pictureString;
                  }
                  this.$axios.post("house/saveHouse.do?t=" + Math.random(), params)
                    .then(res => {
                      console.log(res.data);
                      var result = res.data;
                      if (result.success) {
                        this.$alert("提交成功");
                        if (this.addFlag) {
                          connector.$emit("doSearchHouses", "1");
                          this.$emit('update:visible', false);
                        } else {
                          this.$refs.houseForm.resetFields();
                        }

                      } else {
                        this.$alert("提交失败");
                      }
                    }).catch(error => {
                    console.log(error);
                  });
                } else {
                    this.$alert("请检查必填项");
                    return false;
                }
            })
          },
          closeHouseForm : function () {
            this.$emit('update:visible', false);
          }
        }
    }
</script>

<style scoped>
  .add {
    margin-left: 100px;
  }


</style>
