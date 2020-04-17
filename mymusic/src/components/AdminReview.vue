<template>
    <div>
        <a-card>
            <p style="font-size: 20px;text-align: center">{{review.username}}的歌手申请</p>
            <img style="margin-left: 20%" :src="review.img" :alt="review.username">
            <div style="font-size: 15px;text-align: center;margin-top: 20px">{{review.username}}的音乐作品:</div>
            <audio controls="controls" style="margin-left: 13%;width: 700px;height: 30px;margin-top: 20px">
                <source :src="review.music">
                Your browser does not support the audio element.
            </audio>
            <div style="font-size: 15px;text-align: center;margin-top: 20px">{{review.username}}想成为歌手的原因:</div>
            <a-button @click="submitModal=true">aa</a-button>
            <a-modal v-model="submitModal" :closable="false" :footer="null" width="350px">
                <slide-verify  ref="slideblock"
                               @again="onAgain"
                               @success="onSuccess"
                               @fail="onFail"
                               :accuracy="accuracy"
                               slider-text="滑一滑"
                              ></slide-verify>
            </a-modal>
        </a-card>
    </div>
</template>

<script>
    export default {
        name: "AdminReview",
        mounted(){
            this.review=JSON.parse(this.$route.query.review);
        },
        data(){
            return{
                review:{},
                submitModal:false,
                // 精确度小，可允许的误差范围小；为1时，则表示滑块要与凹槽完全重叠，才能验证成功。默认值为5
                accuracy: 7,
            }
        },
        methods:{
            onSuccess(){
                this.$message.success("验证成功");
            },
            onFail(){
                this.$message.error("验证失败");
            },
            onAgain() {
                this.$message.warning("检测到非人为操作");
                this.$refs.slideblock.reset();
            },
        }
    }
</script>

<style scoped>

</style>