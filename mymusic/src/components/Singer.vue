<template>
    <div>
        <a-row  :gutter="70">
            <a-col :span="12"  v-for="(singer,index) in singerList" :key="index" style="margin-top: 30px">
                <a-card  class="activeClass" @click="toSingerInfo(singer.singerid)" v-show="index<showNum">
                    <img :src="singer.singerimg" height="100%" width="100%" :alt="singer.singername">
                    <div style="text-align: center;font-size: 20px;margin-top: 15px">{{singer.singername}}</div>
                </a-card>
            </a-col>
            <a-button shape="circle" icon="down" style="margin-left: 48%" @click="showMore" :disabled="showNum>=singerList.length" ></a-button>
        </a-row>
    </div>
</template>

<script>
    export default {
        name: "Singer",
        data(){
            return{
                singerList:[],
                showNum:6
            }
        },
        mounted(){
            this.getSingerList();
        },
        methods:{
            showMore(){
                this.showNum+=2;
            },
            toSingerInfo:function(singerid){
             this.$router.push({path:'/singerInfo',query:{singerid:singerid}})
            },
            getSingerList:function () {
                this.axios.get('/user/getSingerList')
                .then(res=>{
                    this.singerList=res.data.data;
                    console.log(res);
                }).catch(err=>{
                    console.log(err);
                })
            }
        }
    }
</script>

<style scoped>
    :hover.activeClass{
        color: #2d8cf0;
        cursor: pointer;
    }
</style>