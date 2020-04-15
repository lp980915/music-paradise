<template>
    <div>
        <span style="font-size: 25px">开启歌手之路</span>
        <a-divider></a-divider>
        <a-alert
                message="注意！"
                description="请认真填写以下信息，告诉我们你想成为歌手的原因，并提交你的作品，审核一般在24小时之内完成。"
                type="info"
                showIcon
        />

<a-form-model layout="horizontal" style="margin-top: 30px" :model="singerReqForm" ref="singerReqForm" :rules="singerReqRules">
    <a-form-model-item label="你来自哪个国家"  prop="details" >
        <a-input v-model="singerReqForm.country" />
    </a-form-model-item>
    <a-form-model-item label="为什么想当歌手" prop="reason">
        <a-textarea v-model="singerReqForm.reason" />
    </a-form-model-item>
    <a-form-model-item label="你的简介"  prop="details">
        <a-textarea v-model="singerReqForm.details"  />
    </a-form-model-item>
</a-form-model>
        <a-upload-dragger
                name="file"
                :multiple="true"
                accept=".jpg, .jpeg, .png, .jfif, .mp3, .ogg"
                action="http://localhost:8088/api/user/uploadSingerReq"
                @change="handleChange"
                @reject="handlereject"
        >
            <p class="ant-upload-drag-icon">
                <a-icon type="inbox" />
            </p>
            <p class="ant-upload-text">点击或拖拽文件上传</p>
            <p class="ant-upload-hint">
              请在此处上传您的照片以及您的作品(请不要上传大于5m的作品)
            </p>
        </a-upload-dragger>
        <div style="margin-top: 20px;font-size: 15px;text-align: center">您的照片请上传以下案例相似的尺寸</div>
        <img src="http://localhost:8088/singerimg/Wiz Khalifa.jpg" alt="例子" style="margin-left:170px;margin-top: 20px">
        <a-button type="primary" @click="" :loading="submitLoading" block style="margin-top: 30px">
            提交
        </a-button>


    </div>
</template>

<script>
    export default {
        name: "SingerRequest",
        created(){
            this.existSingerReq();
        },
        data(){
            return{
                singerReqForm:{
                    userid:JSON.parse(localStorage.getItem('loginUser')).userid,
                    country:'',
                    reason:'',
                    details:''
                },
                singerReqRules:{

                },
                submitLoading:false,
                imgURL:'',
                musicURL:''
            }
        },
        methods:{
            existSingerReq:function () {

            },
            handleChange(info) {
                const status = info.file.status;
                if (status !== 'uploading') {
                    console.log(info.file, info.fileList);
                }
                if (status === 'done') {
                    this.$message.success(`${info.file.name} 上传成功！`);
                    if(info.file.response.data.imgURL!==undefined&&info.file.response.data.imgURL!==null){
                        this.imgURL=info.file.response.data.imgURL;
                    }
                    if(info.file.response.data.musicURL!==undefined&&info.file.response.data.musicURL!==null){
                        this.musicURL=info.file.response.data.musicURL;
                    }
                } else if (status === 'error') {
                    this.$message.error(`${info.file.name} 上传失败！`);
                }
            },
            handlereject(){
                this.$message.error("仅支持上传.jpg, .jpeg, .png, .jfif, .mp3, .ogg格式")
            }
        }

    }
</script>

<style scoped>

</style>