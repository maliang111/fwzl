<template>
  <el-row style="margin-top: 10px ">
    <el-form label-width="70px" size="mini" :inline="true">
      <el-form-item label="价格大于">
      <el-input style="width: 90%" v-model="params.rent1"></el-input>
      </el-form-item>
      <el-form-item label="小于">
      <el-input style="width: 90%" v-model="params.rent2"></el-input>
      </el-form-item>
      <el-form-item label="地址">
      <el-input v-model="params.address" style="width: 100%"></el-input>
      </el-form-item>
      <el-form-item label="厅室">
      <el-select v-model="params.residence">
      <el-option key="" value="">请选择</el-option>
      <el-option key="一室" value="一室">一室</el-option>
      <el-option key="两室" value="两室">两室</el-option>
      <el-option key="三室" value="三室">三室</el-option>
      <el-option key="四室" value="四室">四室</el-option>
      </el-select>
      </el-form-item>
      <el-form-item style="text-align: left; margin-left: 15px">
      <el-button type="primary" icon="el-icon-search" @click="doSearchHouses">搜索</el-button>
      <el-button v-if="deleteBtn" type="danger" icon="el-icon-delete" @click="doDeleteHouses">删除</el-button>
      </el-form-item>
      <!--<el-row type="flex" justify="center" :gutter="2">-->
        <!--<el-col :span="3" :xs="2" :sm="6" :md="8" :lg="4" :xl="4">-->
          <!--<el-form-item label="价格大于">-->
            <!--<el-input style="width: 100%" v-model="params.rent1"></el-input>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="2" :sm="6" :md="8" :lg="4" :xl="4">-->
          <!--<el-form-item label="小于">-->
            <!--<el-input style="width: 100%" v-model="params.rent2"></el-input>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="6" :sm="6" :md="8" :lg="6" :xl="6">-->
          <!--<el-form-item label="地址">-->
            <!--<el-input v-model="params.address" style="width: 100%"></el-input>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="8" :sm="6" :md="8" :lg="4" :xl="4">-->
          <!--<el-form-item label="厅室">-->
            <!--<el-select v-model="params.residence">-->
              <!--<el-option key="" value="">请选择</el-option>-->
              <!--<el-option key="一室" value="一室">一室</el-option>-->
              <!--<el-option key="两室" value="两室">两室</el-option>-->
              <!--<el-option key="三室" value="三室">三室</el-option>-->
              <!--<el-option key="四室" value="四室">四室</el-option>-->
            <!--</el-select>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="4" :sm="4" :md="5" :lg="5" :xl="5">-->
          <!--<el-form-item style="text-align: left; margin-left: 15px">-->
            <!--<el-button type="primary" icon="el-icon-search" @click="doSearchHouses">搜索</el-button>-->
            <!--<el-button v-if="deleteBtn" type="danger" icon="el-icon-delete" @click="doDeleteHouses">删除</el-button>-->
          <!--</el-form-item>-->
        <!--</el-col>-->

      <!--</el-row>-->
    </el-form>
  </el-row>
</template>

<script>

    import connector from '@/util/connector';
    import ElFormItem from "element-ui/packages/form/src/form-item";
    export default {
      components: {ElFormItem},
      name: "house-header",
        props : ['searchParams', 'deleteBtn'],
        computed : {
            params : function () {
              return this.searchParams;
            }
        },
        methods : {
          doSearchHouses : function () {
              this.$emit("update:searchParams", this.params);
              connector.$emit("doSearchHouses", "1");
          },

          doDeleteHouses : function () {
              connector.$emit("doDeleteHouses", 1);
          }

        }

    }
</script>

<style scoped>


</style>
