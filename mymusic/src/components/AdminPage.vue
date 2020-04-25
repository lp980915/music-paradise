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
                <a-button type="primary" block style="margin-bottom: 30px" @click="addUserModal=true">添加用户</a-button>
                <a-modal title="添加用户"
                         :visible="addUserModal"
                         :closable="false">
                    <template slot="footer">
                        <a-button @click="addUserModal=false">取消</a-button>
                        <a-button type="primary" @click="submitAddUser" >
                            提交
                        </a-button>
                    </template>
                    <a-form-model :model="addUserForm" ref="addUserForm" :rules="addUserRules">
                        <a-form-model-item label="用户名"  prop="username">
                            <a-input v-model="addUserForm.username"  />
                        </a-form-model-item>
                        <a-form-model-item label="密码"  prop="password">
                            <a-input type="password" v-model="addUserForm.password"  />
                        </a-form-model-item>
                        <a-form-model-item label="性别" prop="sex">
                            <a-radio-group v-model="addUserForm.sex">
                                <a-radio value="男">男</a-radio>
                                <a-radio value="女">女</a-radio>
                            </a-radio-group>
                        </a-form-model-item>
                        <a-form-model-item label="生日" prop="birth">
                            <a-date-picker v-model="addUserForm.birth"/>
                        </a-form-model-item>
                    </a-form-model>
                </a-modal>
                <a-table :dataSource="userData">
                    <a-table-column width="27%" title="用户名" dataIndex="username" key="username" />
                    <a-table-column width="27%" title="性别" dataIndex="sex" key="sex" />
                    <a-table-column width="27%" title="生日" dataIndex="birth" key="birth">
                        <template slot-scope="birth">
                            <span>{{birth}}</span>
                        </template>
                    </a-table-column>
                    <a-table-column width="19%" title="操作" key="action">
                        <template slot-scope="user">
                            <span>
                                <a @click="openUpUserModal(user)">修改</a>
                                <a-divider type="vertical" />
                                <a-popconfirm
                                        title="确认删除吗？"
                                        @confirm="deleteUser(user.userid)"
                                        okText="Yes"
                                        cancelText="No"
                                >
                                    <a href="#">删除</a>
                                </a-popconfirm>
                            </span>
                        </template>
                    </a-table-column>
                </a-table>
                <a-modal
                        :closable="false"
                        title="修改个人信息"
                        :visible="upUserModal"
                >
                    <template slot="footer">
                        <a-button @click="closeUpUserModal">取消</a-button>
                        <a-button type="primary" @click="updateUser" >
                            提交
                        </a-button>
                    </template>
                    <a-form-model :model="upUserForm" ref="upUserForm" :rules="upUserRules">
                        <a-form-model-item label="用户名"  prop="username">
                            <a-input v-model="upUserForm.username"  />
                        </a-form-model-item>
                        <a-form-model-item label="性别" prop="sex">
                            <a-radio-group v-model="upUserForm.sex">
                                <a-radio value="男">男</a-radio>
                                <a-radio value="女">女</a-radio>
                            </a-radio-group>
                        </a-form-model-item>
                        <a-form-model-item label="生日" prop="birth">
                            <a-date-picker v-model="upUserForm.birth"/>
                        </a-form-model-item>
                    </a-form-model>
                </a-modal>
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
    import moment from 'moment'
    export default {
        name: "AdminPage",
        data(){
            return{
                reviewList:[],
                userData:[],
                addUserModal:false,
                upUserModal:false,
                addUserForm:{
                    username:'',
                    password:'',
                    sex:'',
                    birth:null,
                },
                upUserForm:{
                    userid:'',
                    username:'',
                    password:'',
                    sex:'',
                    birth:null,
                },
                addUserRules:{
                    username:[
                        {required:true,message:'请输入用户名',trigger:'change'},
                        {min:3,max:10, message: '用户名应该在3-10位', trigger: 'change'}
                    ],
                    password:[
                        {required:true,message:'请输入密码',trigger:'change'},
                        {min:6,max:14, message: '密码应该在6-14位', trigger: 'change'}
                    ],
                    sex:[
                        {required:true,message:'请选择性别',trigger:'change'},
                    ],
                    birth:[
                        {required:true,message:'请填写生日',trigger:'change'},
                    ]
                },
                upUserRules:{
                    username:[
                        {required:true,message:'请输入用户名',trigger:'change'},
                        {min:3,max:10, message: '用户名应该在3-10位', trigger: 'change'}
                    ],
                    password:[
                        {required:true,message:'请输入密码',trigger:'change'},
                        {min:6,max:14, message: '密码应该在6-14位', trigger: 'change'}
                    ],
                    sex:[
                        {required:true,message:'请选择性别',trigger:'change'},
                    ],
                    birth:[
                        {required:true,message:'请填写生日',trigger:'change'},
                    ]
                },
            }
        },
        created(){
            this.getReview();
        },
        methods:{
            deleteUser(userid){
                this.axios.get('/user/deleteUser?userid='+userid)
                .then(res=>{
                    if(res.data.data){
                        this.$message.success("成功删除");
                        this.getAllUser();
                    }else {
                        this.$message.error("删除失败");
                    }
                }).catch(err=>{
                    console.log(err);
                })
            },
            openUpUserModal(user){
                this.upUserForm=user;
                this.upUserForm.birth=moment(user.birth,"YYYY-MM-DD")
                this.upUserModal=true;
            },
            closeUpUserModal(){
                this.getAllUser();
              this.upUserModal=false;
            },
            updateUser(){
                this.axios.post('/user/updateUser',this.upUserForm)
                .then(res=>{
                    if(res.data.data){
                        this.$message.success("修改成功");
                        this.upUserModal=false;
                        this.getAllUser();
                    }else {
                        this.$message.error("修改失败");
                    }
                }).catch(err=>{
                    console.log(err);
                })
            },
            submitAddUser(){
                this.$refs['addUserForm'].validate(valid=>{
                    if(valid){
                        for(let u of this.userData){
                            if(u.username===this.addUserForm.username){
                                this.$message.error("此用户名已被占用");
                                return false;
                            }
                        }
                    let user=this.addUserForm;
                        this.axios.post('/user/addUser',user)
                            .then(res=>{
                                console.log(res);
                                if(res.data.data){
                                    this.$message.success("添加成功");
                                    this.addUserModal=false;
                                    this.$refs['addUserForm'].resetFields();
                                    this.getAllUser();
                                }else {
                                    this.$message.error("添加失败");
                                }
                            }).catch(err=>{
                            console.log(err);
                        })
                    }else {
                        this.$message.error("请完善信息");
                        return false;
                    }
                })
            },
            getAllUser(){
                this.axios.get('/user/getAllUser')
                    .then(res=>{
                        let i=0;
                        let allUser=res.data.data
                        for (let user of allUser){
                            user.key=i;
                            i++
                        }
                        this.userData=allUser
                    }).catch(err=>{
                    console.log(err);
                })
            },
            getAllMusic(){
                this.axios.get('/user/getAllMusic')
                    .then(res=>{

                    }).catch(err=>{
                    console.log(err);
                })
            },
            goAdminReview:function(review){
                this.$router.push({path:'/adminReview',query:{'review':JSON.stringify(review)}})
            },
            callback(key) {
                console.log(key);
                if(key==="1"){
                    this.getReview();
                }if(key==="2"){
                this.getAllUser()
                }if(key==="3"){
                this.getAllMusic();
                }
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