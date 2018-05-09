<template>
    <el-row>
      <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="text-color" style="font-size: 20px; font-weight: bold; text-align: left; margin-left: 30px">
        <span>房屋租赁信息发布系统</span>
      </el-col>
      <span>
        <a href="javascript:" class="text-color"  style="text-decoration: none">{{realName}}</a>
      </span>
      <span>
        <a href="javascript:" class="text-color" @click="logout" style="text-decoration: none">logout</a>
      </span>
    </el-row>
</template>

<script>
    export default {
      name: "home-header",
      computed : {
        realName : function () {
            return this.$store.state.realName;
        }
      },
      methods : {
        logout : function () {
            this.$axios.post("logout.do?t=" + Math.random())
              .then(res => {
                  var result = res.data;
                  if (result.success) {
                    window.location.href = this.$axios.defaults.baseURL + "/loginUI.do";
                  }
              })
        }
      }
    }
</script>

<style scoped>

  #buttonDiv {
    text-align: left;
  }
  .text-color {
    padding-right: 20px;
    color: white;
    font-size: 18px
  }

  a:hover {
    font-weight: bold;
  }

</style>
