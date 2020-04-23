<template>
    <div>
        <div class="card-container">
        <a-tabs type="card" :animated="true" @change="callback">
            <a-tab-pane tab="歌手审核" key="1">
                <a-row>
                    <a-col :span="8"><span style="margin-left: 10%">用户</span></a-col>
                        <a-col :span="8">  <span style="margin-left: 40%">国籍</span></a-col>
                            <a-col :span="8"> <span style="margin-left: 60%">审核状态</span></a-col>
                    <a-divider></a-divider>
                    <a-col v-for="(review,index) in reviewList" :key="index" class="reviewList" @click="goAdminReview(review)">
                        <a-col :span="8"> <span style="margin-left: 10%;">{{review.username}}</span></a-col>
                        <a-col :span="8"><span style="margin-left: 40%">{{review.country}}</span></a-col>
                        <a-col :span="8"> <a-tag style="margin-left: 60%" color="geekblue" v-show="review.result==='0'">未审核</a-tag></a-col>
                        <a-col :span="8"><a-tag style="margin-left: 60%" color="green" v-show="review.result==='1'">审核成功</a-tag></a-col>
                        <a-col :span="8"> <a-tag style="margin-left: 60%" color="volcano" v-show="review.result==='-1'">审核失败</a-tag></a-col>
                        <a-divider></a-divider>
                    </a-col>
                </a-row>
            </a-tab-pane>
            <a-tab-pane tab="用户管理" key="2">
                <p>Content of Tab Pane 2</p>
                <p>Content of Tab Pane 2</p>
                <p>Content of Tab Pane 2</p>
            </a-tab-pane>
            <a-tab-pane tab="曲目管理" key="3">
                <p>Content of Tab Pane 3</p>
                <p>Content of Tab Pane 3</p>
                <p>Content of Tab Pane 3</p>
            </a-tab-pane>
        </a-tabs>
        </div>
    </div>
</template>

<script>
    export default {
        name: "AdminPage",
        data(){
            return{
                reviewList:[]
            }
        },
        created(){
            this.getReview();
        },
        methods:{
            goAdminReview:function(review){
                this.$router.push({path:'/adminReview',query:{'review':JSON.stringify(review)}})
            },
            callback(key) {
                console.log(key);
            },
            getReview(){
                this.axios.get('/user/getReviewList')
                .then(res=>{
                    this.reviewList=res.data.data;
                    console.log(this.reviewList)
                }).catch(err=>{
                    console.log(err);
                })
            }
        }
    }
</script>

<style scoped>
    .card-container {
        background: #f5f5f5;
        overflow: hidden;
        padding: 24px;
    }
    .card-container > .ant-tabs-card > .ant-tabs-content {
        height: 120px;
        margin-top: -16px;
    }

    .card-container > .ant-tabs-card > .ant-tabs-content > .ant-tabs-tabpane {
        background: #fff;
        padding: 16px;
    }

    .card-container > .ant-tabs-card > .ant-tabs-bar {
        border-color: #fff;
    }

    .card-container > .ant-tabs-card > .ant-tabs-bar .ant-tabs-tab {
        border-color: transparent;
        background: transparent;
    }

    .card-container > .ant-tabs-card > .ant-tabs-bar .ant-tabs-tab-active {
        border-color: #fff;
        background: #fff;
    }
    .reviewList{
        cursor: pointer;
    }
    :hover.reviewList{
        color: #450a7c;
    }
</style>