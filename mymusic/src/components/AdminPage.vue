<template>
    <div>
        <div class="card-container">
        <a-tabs type="card" :animated="true" @change="callback">
            <a-tab-pane tab="歌手审核" key="1">
                <a-row>
                    <a-col v-for="(review,index) in reviewList" :key="index">
                        {{review}}
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
</style>