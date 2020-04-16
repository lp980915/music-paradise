<template>
    <div :style="backgroundDiv">
        <a-card  style="width: 400px;height: 500px;left: 39%;top:22%">
            <span style="text-align: left;font-size: 25px">Login</span>
            <a-form-model ref="loginForm" :model="loginForm" :rules="loginRules" >
                <a-form-model-item has-feedback label="用户名" prop="username" style="margin-top: 50px" >
                    <a-input v-model="loginForm.username" type="text" autocomplete="off" placeholder="username" size="large"/>
                </a-form-model-item>
                <a-form-model-item has-feedback label="密码" prop="password" >
                    <a-input v-model="loginForm.password" type="password" autocomplete="off" placeholder="password" size="large"/>
                </a-form-model-item>
                <a-form-model-item>
                    <a-button size="large" block type="primary" @click="submitLogin('loginForm')" style="margin-top: 30px">
                        登录
                    </a-button>
                    <a-button size="large" block  @click="" style="margin-top: 10px">
                        注册
                    </a-button>
                </a-form-model-item>
            </a-form-model>
        </a-card>
    </div>
</template>

<script>
    export default {
        name: "Login",
        mounted:function () {
            let that = this;  //在document.onkeydown = function(e)前将vue实例赋值给that,that才可以指向vue实例
            document.onkeydown = function(e){
                let key = window.event.keyCode;
                if(key === 13 || key === 100){
                    if(that.$route.path==='/login'){
                        that.submitLogin('loginForm');
                    }
                }
            }
        },
        data(){
          return{
              backgroundDiv:{
                  height:'100%',
                  backgroundImage:'url(' + require('@/assets/img/back.jpg') + ')',
                  backgroundRepeat:'no-repeat',
                  backgroundSize:'100% 100%'
              },
              loginForm:{
                  username:'',
                  password:'',
              },
              loginRules:{
                  username: [ { required: true, message: '请输入用户名', trigger: 'change' },
                      { min:3,max:10, message: '用户名应该在3-10位', trigger: 'change' },],
                  password: [ { required: true, message: '请输入密码', trigger: 'change' },
                      { min:6,max:14, message: '密码应该在6-14位', trigger: 'change' },],
              }
          }
        },
        methods:{
            submitLogin:function(formName){
                    this.$refs[formName].validate(valid => {
                        if (valid) {
                            let user=this.$qs.stringify(this.loginForm);
                            this.axios.post('/user/login',user)
                                .then(res=>{
                                    if(res.data.data.user!==null&&res.data.data.user!==undefined){
                                        localStorage.setItem('token',res.data.data.token);
                                    if(res.data.data.user.rowid==="1"){
                                        localStorage.setItem('admin',"1");
                                        this.$router.push('/welcome');
                                        this.$message.success("管理员身份登录成功!");
                                        localStorage.setItem('loginUser',JSON.stringify(res.data.data.user));
                                        localStorage.setItem('token',res.data.data.token);
                                    }else if(res.data.data.user.rowid==="0"){
                                        console.log(res)
                                        localStorage.setItem('admin',"0");
                                        this.$router.push('/welcome');
                                        this.$message.success("用户身份登录成功!");
                                        localStorage.setItem('loginUser',JSON.stringify(res.data.data.user));
                                        localStorage.setItem('token',res.data.data.token);
                                    }else if(res.data.data.user.rowid==="2"){
                                        console.log(res)
                                        localStorage.setItem('admin',"-1");
                                        this.$router.push('/welcome');
                                        this.$message.success("歌手身份登录成功!");
                                        localStorage.setItem('loginUser',JSON.stringify(res.data.data.user));
                                        this.axios.defaults.headers.common['token']=res.data.data.token;
                                    }
                                    }else {
                                        this.$message.error("用户名或密码有误!");
                                    }
                                }).catch(err=>{
                                console.log(err);
                            })
                        } else {
                            this.$message.error('请完善登录信息!');
                            return false;
                        }
                    });
                }
            },
    }
</script>

<style scoped>

</style>