<template>
    <div>
        <a-row :gutter="[30,50]" style="margin-top: 8px">
            <a-col :span="8"  v-for="(musiclist,index) in list" :key="index">
                <a-card hoverable style="width: 300px">
                    <img :alt="musiclist.listname" :src="musiclist.listimg"  slot="cover" style="height: 300px"
                         @click="toListInfo(musiclist.listid)"/>
                    <a-card-meta>
                        <template slot="description">
                            <div style="text-align: center"><span class="activeClass" @click="toListInfo(musiclist.listid)">{{musiclist.listname}}</span>
                                </div>
                        </template>
                    </a-card-meta>
                </a-card>
            </a-col>
        </a-row>
        <a-button shape="circle" icon="down" style="margin-left: 48%"></a-button>
    </div>
</template>

<script>
    export default {
        name: "MusicList",
        data(){
            return{
                list:[]
            }
        },
        created(){
            this.getList();
        },
        methods:{
            getList(){
                this.axios.get('/user/getList')
                .then(res=>{
                    this.list=res.data.data
                    console.log(res.data.data)
                }).catch(err=>{
                    console.log(err);
                })
            },
            toListInfo(listid){
                this.$router.push({path:'/listInfo',query:{'listid':listid}})
            }

        },
    }
</script>

<style scoped>
    :hover.activeClass{
        color: #2d8cf0;
    }
</style>