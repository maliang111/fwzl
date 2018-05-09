<template>
  <el-menu
    class="el-menu-vertical-demo" router style="height: 100%">
      <el-submenu v-for="module in modules" v-if="module.children && module.children.length != 0"
                  :index="module.id" :key="module.id">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">{{module.text}}</span>
        </template>
        <el-menu-item v-for="child in module.children" :index="!child.url ? '-1' : child.url" :key="child.id">
            {{child.text}}
        </el-menu-item>
      </el-submenu>
    <el-menu-item  v-for="module in modules" v-if="!(module.children && module.children.length != 0)"
                   :index="!module.url ? '-1' : module.url" :key="module.id">
      <i class="el-icon-setting"></i>
      <span slot="title">{{module.text}}</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
    export default {
      name: "left-aside",
      data : function () {
          return {
            modules : []
          }
      },
      created: function () {
            this.$axios.post("module/getModules.do", "t=" + Math.random() + "&style=el")
              .then(res => {
                  this.modules = res.data;
              }).catch(error => {
                console.log(error);
            });
      }
    }
</script>

<style scoped>

</style>
