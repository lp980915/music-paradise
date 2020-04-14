<template>
    <div>
       <div style="font-size: 22px;margin-top: 20px">个人信息</div>
        <a-divider></a-divider>
        <div style="font-size: 17px;margin-top: 20px;margin-left: 20px;">
            <span> 身份:</span>
            <span style="margin-left: 40px" v-show="loginUser.rowid==='0'">普通用户</span>
            <span style="margin-left: 40px" v-show="loginUser.rowid==='1'">管理员</span>
        </div>
        <div style="font-size: 17px;margin-top: 20px;margin-left: 20px">
            <span> 用户名:</span><span style="margin-left: 22px">{{loginUser.username}}</span>
        </div>
        <div style="font-size: 17px;margin-top: 20px;margin-left: 20px">
            <span> 性别:</span><span style="margin-left: 40px">{{loginUser.sex}}</span>
        </div>
        <div style="font-size: 17px;margin-top: 20px;margin-left: 20px">
            <span> 生日:</span><span style="margin-left: 40px">{{loginUser.birth}}</span>
        </div>
        <a-button type="primary" size="small" style="margin-top: 30px;font-size: 12px;margin-left: 17px">修改信息</a-button>
        <a-button size="small" style="margin-left: 30px;font-size: 12px">修改密码</a-button>


        <div v-show="collectMusic.length>0" style="font-size: 22px;margin-top: 100px">歌曲收藏</div>
        <a-divider ></a-divider>
        <a-row :gutter="[16,16]">
            <a-col :span="6"  v-for="(music,index) in collectMusic" :key="index">
                <a-card hoverable @click="toMusicInfo(music)" style="width: 200px">
                    <img :alt="music.musicname" :src="music.musicimg"  slot="cover" style="height: 200px"/>
                    <a-card-meta>
                        <template slot="description"
                        >
                            <div style="text-align: center">{{music.musicname}}</div>
                            <div style="text-align: center;margin-top: 10px">收藏日期</div>
                            <div style="text-align: center;">{{music.date}}</div>
                        </template>
                    </a-card-meta>
                </a-card>
            </a-col>
        </a-row>

        <div v-show="collectMusic.length>0" style="font-size: 22px;margin-top: 100px">歌单收藏</div>
        <a-divider ></a-divider>
        <a-row :gutter="[16,16]">
            <a-col :span="6"  v-for="(list,index) in collectList" :key="index">
                <a-card hoverable @click="toListInfo(list.listid)" style="width: 200px">
                    <img :alt="list.listname" :src="list.listimg"  slot="cover" style="height: 200px"/>
                    <a-card-meta>
                        <template slot="description"
                        >
                            <div style="text-align: center">{{list.listname}}</div>
                        </template>
                    </a-card-meta>
                </a-card>
            </a-col>
        </a-row>

    </div>


</template>

<script>
    export default {
        name: "UserInfo",
        data(){
            return{
                loginUser:{},
                collectMusic:[],
                collectList:[]
            }
        },
        mounted(){
            this.loginUser=JSON.parse(localStorage.getItem('loginUser'));
            this.getCollect();
        },
        methods:{
            toMusicInfo:function (music) {
                this.$router.push({path:'/musicInfo',query:{music:JSON.stringify(music)}})
            },
            getCollect:function () {
                this.axios.get('/user/getCollect?userid='+this.loginUser.userid)
                .then(res=>{
                    this.collectMusic=res.data.data.collectMusic;
                    this.collectList=res.data.data.collectList;
                    console.log(res)
                }).catch(err=>{
                    console.log(err);
                })
            },
            toListInfo(listid){
                this.$router.push({path:'/listInfo',query:{'listid':listid}})
            }
        }
    }
</script>

<style scoped>

</style>