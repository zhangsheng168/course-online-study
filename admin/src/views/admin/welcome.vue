<template>
    <div>
        <h1>您好，欢迎进入控制台</h1>
    </div>
</template>


<script>

    export default {
        name: 'welcome',
        mounted() {
            //调用父组件的activeSidebar方法
            //sidebar菜单激活样式一
            //this.$parent.activeSidebar("welcome-sidebar");
        },
        methods:{

        }
    }
</script>
