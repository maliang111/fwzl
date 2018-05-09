<template>
  <div>
    <el-container>
      <el-header style="margin-top: 10px">
        <house-header :searchParams.sync="searchParams" :deleteBtn="false"></house-header>
      </el-header>
      <el-main style="margin-top: 20px">
        <el-row style="margin-bottom: 20px;" v-for="(houses, outerIndex) in splitedHouses" :key="outerIndex">
          <el-col :xs="6" :sm="6" :md="8" :lg="7" :xl="7" v-for="(house, index) in houses" :key="index" :offset="index > 0 ? 1 : 0">
            <el-card :body-style="{ padding: '0px' }">
              <div style="text-align: center">
              <img class="image" :src="housePictureSrc(house)">
              </div>
              <div style="padding: 14px;">
                <span>户型: {{house.residence}}</span>
                <span>{{house.isLeased == '1'? "已租" : "未租"}}</span>
                <br>
                <span style="font-size: 20px; color: red">租金:{{house.rent}}元/月</span>
                <div class="bottom">
                  <time class="time">出租时间:{{house.leaseTime}}</time>
                  <el-button type="text" class="button" @click="showHouseInfo(house.houseId)">查看详情</el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
      <el-footer>
        <el-pagination
          layout="prev, pager, next"
          :total="total" :page-size="limit"
          @current-change="doFindHouseByPage" style="text-align: center">
        </el-pagination>
      </el-footer>
    </el-container>
    <div>
      <el-dialog  title="查看房屋信息" center :visible.sync="modalVisible" width="60%" v-if="modalVisible">
        <house-form :visible.sync="modalVisible" :houseId="selectedId" operation="view"></house-form>
      </el-dialog>
    </div>
  </div>

</template>

<script>
    import houseForm from '@/components/HouseForm';
    import HouseHeader from "../../components/HouseHeader";
    import connector from '@/util/connector';
    export default {
        name: "house-list",
        components : {
          HouseHeader,
          houseForm
        },
        data : function () {
            return {
              houses : [],
              searchParams : {
                rent1 : "",
                rent2 : "",
                address : "",
                residence : "",
                start : 0
              },
              total : 0,
              limit : 6,
              rowNum : 2,
              colNum : 3,
              modalVisible : false,
              selectedId : 0
            }
        },
        computed : {
            splitedHouses : function () {

              var result = [];
              if (this.houses == null) {
                return result;
              }
              var start = 0;
              for (var i = 0; i < this.houses.length; i++) {
                  if (i % this.colNum == this.colNum - 1) {
                      result.push(this.houses.slice(start, i+1));
                      start = i+1;
                  }
                  if (i == this.houses.length - 1) {
                      result.push(this.houses.slice(start, this.houses.length));
                  }
              }
              return result;
            }
        },
        methods : {
          housePictureSrc : function (house) {
            // if (this.modalVisible) {
            //   return null;
            // }
            return this.$axios.defaults.baseURL + "/getPicture.do?t=" + Math.random() + "&pictureName=" + house.pictureName;
          },
          showHouseInfo : function (id) {
              this.modalVisible = true;
              this.selectedId = id;
          },
          doFindHouseByPage : function (val) {
            val = !val ? 1 : val;
            this.searchParams.start = (val - 1) * this.limit;
            var params = this.$qs.stringify(this.searchParams) + "&limit=" + this.limit;
            console.log(params);
            this.$axios.post("house/findHouses.do?t=" + Math.random(),
              params)
              .then(res => {
                this.houses = res.data.data;
                this.total = res.data.totalCount;
              }).catch(error => {
              console.log(error);
            });
          }
        },
        created : function () {
            this.doFindHouseByPage();
        },
        mounted : function () {
          var self = this;
          connector.$on("doSearchHouses", function (result) {
            if (result == "1") {
              self.doFindHouseByPage();
            }
          })
        }
    }
</script>

<style scoped>
  el-input {
      width: 10%
  }
</style>
