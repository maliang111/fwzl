<template>

  <el-container style="width: 100%; height: 100%">
    <el-header style="text-align: right; font-size: 12px">
      <home-header></home-header>
    </el-header>

    <el-container>
      <el-aside style="width: 260px">
        <left-aside></left-aside>
      </el-aside>
      <el-main style="padding: 5px">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>

    import LeftAside from "../components/LeftAside";
    import HomeHeader from "../components/HomeHeader";

    export default {
      components: {
        HomeHeader,
        LeftAside},
      name: "home",
      created : function () {
        this.doGetUserName();
      },
      methods : {
        doGetUserName : function () {
            this.$axios.post("user/getUser.do?t=" + Math.random())
              .then(res => {
                  var result = res.data;
                  if (result) {
                    this.$store.commit("setUserName", result.data.username);
                    this.$store.commit("setRealName", result.data.realName);
                  }
              }).catch(error => {
                  console.log(error);
            });
        }
      }
    }
</script>

<style scoped>
  .el-header {
    padding : 0;
    background-color: #399BFF;
    color: #333;
    line-height: 60px;
  }
</style>
