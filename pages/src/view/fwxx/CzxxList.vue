<template>
  <div>
    <el-container>
      <el-header style="margin-top: 10px">
        <house-header :searchParams="searchParams" :deleteBtn="true" :selection.sync="selection"></house-header>
      </el-header>
      <el-container></el-container>
      <el-main style="margin-top: 20px">
        <el-table
          :data="houses"
          style="width: 100%; text-align: center" border fit
          :header-cell-style="{textAlign: 'center'}" @select="doGetSelection" @select-all="doGetSelection" @row-click="showHouseInfo">
          <el-table-column
            type="selection"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            label="地址"
          >
          </el-table-column>
          <el-table-column
            prop="ownerName"
            label="出租者"
          >
          </el-table-column>
          <el-table-column
            prop="residence"
            label="户型">
          </el-table-column>
          <el-table-column
            prop="size"
            label="大小">
          </el-table-column>
          <el-table-column
            prop="rent"
            label="租金">
          </el-table-column>
          <el-table-column
            prop="note"
            label="备注">
          </el-table-column>
          <el-table-column
            prop="isLeased"
            label="是否已出租">
            <template slot-scope="scope">
              {{(scope.row.isLeased == "0") ? "未出租" : "已出租"}}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="updateHouseInfo(scope.row.houseId)"
              >编辑</el-button>
              <el-button
                size="mini"
                type="danger"
                @click="doDeleteHouse(scope.row.houseId)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          layout="prev, pager, next"
          :total="total" :page-size="limit"
          @current-change="doFindHouseByPage" style="text-align: center">
        </el-pagination>
      </el-main>
    </el-container>
    <div>
      <el-dialog :visible.sync="modalVisible" width="60%" v-if="modalVisible" center>
        <span slot="title">{{operation == 'view' ? '查看房屋信息' : '修改房屋信息'}}</span>
        <house-form :visible.sync="modalVisible" :houseId="selectedId" :operation="operation"></house-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
    import HouseHeader from "../../components/HouseHeader";
    import connector from '@/util/connector';
    import houseForm from '@/components/HouseForm';
    import ElContainer from "element-ui/packages/container/src/main";
    export default {
      components: {
        ElContainer,
        HouseHeader, houseForm},
      name: "czxx-list",
        data : function () {
            return {
                searchParams : {
                  rent1 : "",
                  rent2 : "",
                  address : "",
                  residence : "",
                  start : 0
                },
                selectedId : 0,
                selection : [],
                operation : 'edit',
                modalVisible : false,
                houses : [],
                total : 0,
                limit : 4
            }
        },
        methods : {
          showHouseInfo : function (row, event, column) {
            if (column.label != '操作') {
                this.modalVisible = true;
                this.operation = 'view';
                this.selectedId = row.houseId;
            }
          },
          updateHouseInfo : function (id) {
            this.modalVisible = true;
            this.operation = 'edit';
            this.selectedId = id;
          },
            doDelete : function (id) {
              this.$axios.post("house/deleteHouse.do?t=" + Math.random(), "houseId=" + id)
                .then(res => {
                  var result = res.data;
                  if (result.success) {
                    this.$alert("删除成功");
                    this.doFindHouseByPage();
                  }
                }).catch(error => {
                  console.log(error);
                  this.$alert("删除失败");
              })
            },
            doDeleteHouse : function (id) {
              this.$confirm("是否删除该信息?", "提示", {
                  type : "warning",
                confirmButtonText: '确定',
                cancelButtonText: '取消'
              }).then(() => {
                this.doDelete(id);
              }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '已取消删除'
                });
              });
            },
            doGetSelection : function (selection) {
              this.selection = selection;
            },
            doFindHouseByPage : function (val) {
              val = !val ? 1 : val;
              this.searchParams.start = (val - 1) * this.limit;
              var params = this.$qs.stringify(this.searchParams) + "&limit=" + this.limit + "&username=" + this.username;
              this.$axios.post("house/findHouses.do?t=" + Math.random(),
                params)
                .then(res => {
                  this.houses = res.data.data;
                  this.total = res.data.totalCount;
                }).catch(error => {
                console.log(error);
              });
            },
          doDeleteHouses : function () {
            this.$confirm("是否删除选中的信息?", "提示", {
              type : "warning",
              confirmButtonText: '确定',
              cancelButtonText: '取消'
            }).then(() => {
              console.log(this.selection);
              var selectedStr = "";
              for (var i = 0; i < this.selection.length; i++) {
                if (!selectedStr) {
                  selectedStr = this.selection[i].houseId;
                } else {
                  selectedStr += "," + this.selection[i].houseId;
                }
              }
              if (!selectedStr) {
                this.$alert("请选择一条记录");
              } else {
                this.$axios.post("house/deleteHouses.do?t=" + Math.random(), "ids=" + selectedStr)
                  .then(res => {
                    this.$alert("删除成功");
                    this.doFindHouseByPage();

                  }).catch(error => {
                  console.log(error);
                });
              }
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消删除'
              });
            });
          }
        },
        computed : {
          username : function () {
              return this.$store.state.username;
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
          });
          connector.$on("doDeleteHouses", function (result) {
              if (result == "1") {
                self.doDeleteHouses();
              }
          });
        }
    }
</script>

<style scoped>

</style>
