<template>
    <div>
        <div style="font-size: 22px;margin-top: 170px">上传曲目</div>
        <a-divider></a-divider>
        <a-upload-dragger
                name="file"
                :multiple="true"
                accept=".jpg, .jpeg, .png, .jfif, .mp3, .ogg"
                action="http://localhost:8088/api/user/uploadMusic"
                @change="handleChange"
                @reject="handleReject"
        >
            <p class="ant-upload-drag-icon">
                <a-icon type="inbox" />
            </p>
            <p class="ant-upload-text">点击或拖拽文件上传</p>
            <p class="ant-upload-hint">
                请上传曲目以及曲目封面(请不要上传大于5m的作品),
                歌曲封面请上传正方形的jpg/jpeg/png/jfif格式文件
            </p>
        </a-upload-dragger>

        <a-form-model layout="horizontal" style="margin-top: 30px" :model="uploadMusicForm" ref="uploadMusicForm" >
            <a-form-model-item label="曲目名称"  prop="musicname" >
                <a-input placeholder="请输入曲目名" v-model="uploadMusicForm.musicname" size="large"/>
            </a-form-model-item>
        </a-form-model>

        <a-button @click="submitForm" :disabled="uploadMusicForm.musicname===''||uploadMusicForm.musicimg===''||uploadMusicForm.musicfile===''"
        style="margin-left: 45%;margin-top: 50px">提交</a-button>
    </div>
</template>

<script>
    export default {
        name: "UploadMusic",
        data(){
          return{
              uploadMusicForm:{
                  singername:JSON.parse(localStorage.getItem('loginUser')).username,
                  musicname:'',
                  musicimg:'',
                  musicfile:''
              }
          }
        },
        methods:{
            submitForm:function(){
                console.log(this.uploadMusicForm);
                let addMusicForm=this.$qs.stringify(this.uploadMusicForm);
                this.axios.post(`/user/addMusicBySinger`,addMusicForm)
                .then(res=>{
                    if(res.data.data){
                        this.$message.success("音乐添加成功!");
                        this.uploadMusicForm={}
                    }else {
                        this.$message.error("音乐添加失败!");
                    }
                }).catch(err=>{
                    console.log(err);
                })
            },
            handleChange(info) {
                const status = info.file.status;
                if (status !== 'uploading') {
                    console.log(info.file, info.fileList);
                }
                if (status === 'done') {
                    this.$message.success(`${info.file.name} 上传成功！`);
                    if(info.file.response.data.musicimg!==undefined&&info.file.response.data.musicimg!==null){
                        this.uploadMusicForm.musicimg=info.file.response.data.musicimg;
                        console.log(this.uploadMusicForm.musicimg)
                    }
                    if(info.file.response.data.musicfile!==undefined&&info.file.response.data.musicfile!==null){
                        this.uploadMusicForm.musicfile=info.file.response.data.musicfile;
                        console.log(this.uploadMusicForm.musicfile)
                    }
                } else if (status === 'error') {
                    this.$message.error(`${info.file.name} 上传失败！`);
                }
            },
            handleReject(){
                this.$message.error("仅支持上传.jpg, .jpeg, .png, .jfif, .mp3, .ogg格式")
            }
        }
    }
</script>

<style scoped>

</style>