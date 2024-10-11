<template>
    <div class="m_container">
     
      <!-- 游戏内容 -->
      <div class="m_content">
        <el-form ref="editForm" status-icon :model="editForm" :rules="rules1"  label-width="80px">
          <el-form-item label="名字" prop="name">
            <el-input v-model="editForm.name"></el-input> 
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input v-model="editForm.description"></el-input>
          </el-form-item>
          <el-form-item label="开发商" prop="developer">
            <el-input v-model="editForm.developer"></el-input>
          </el-form-item>
          <el-form-item label="发售时间" prop="sellTime">
            <el-date-picker
                v-model="editForm.sellTime"
                type="datetimes"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                placeholder="选择日期时间"
                >
              </el-date-picker>
          </el-form-item>
          <el-form-item label="收录时间" prop="collectTime">
                <el-date-picker
                v-model="editForm.collectTime"
                type="datetimes"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                placeholder="选择日期时间"
                >
              </el-date-picker>
          </el-form-item>
          <el-form-item label="游戏平台" prop="platform">
            <el-input v-model="editForm.platform"></el-input>
          </el-form-item>
          <el-form-item label="官方网站" prop="officalWeb">
            <el-input v-model="editForm.officialWeb"></el-input>
          </el-form-item>
          <el-form-item label="推荐配置" prop="configRequire">
            <el-input v-model="editForm.configRequire"></el-input>
          </el-form-item>
          <el-form-item label="首页图片" prop="first_picture">
            <el-input v-model="editForm.first_picture"></el-input>
          </el-form-item>
          <el-form-item label="添加图片" prop="imageList">
            <el-button type="primary">添加图片</el-button>
          </el-form-item>
          <!-- mavon-editor代码在vue3中有问题 -->
          <el-form-item label="介绍内容" prop="gameContent">
            <!-- <mavon-editor v-model="editForm.content"/> -->
            <v-md-editor v-model="editForm.gameContent" height="500px"></v-md-editor>
          </el-form-item>

          <el-form-item style="margin: auto;">
            <el-button type="info" @click="temporarySave()">暂时保存</el-button>
            <el-button type="primary" @click="tosubmitForm('editForm')">发布游戏信息</el-button>
            <!-- <el-button>取消</el-button> -->
          </el-form-item>
        </el-form>
      </div>
  
     <!-- 对话框内容 -->
     <el-dialog title="发布游戏信息" v-model="dialogFormVisible" width="35%">
        <el-form :model="editForm" ref="editForm2">
          <el-form-item label="游戏标签"  :label-width="formLabelWidth">
            <!-- <el-input v-model="oldtags" placeholder="请选择标签，多个标签请用逗号隔开" width="80%"></el-input> -->
            <el-tag
              :key="tag"
              v-for="tag in editForm.tags"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{tag}}
            </el-tag>
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
            >
            </el-input>
            <el-button v-else class="button-new-tag" style="margin-left: 10px;" size="small" @click="showInput">+ New Tag</el-button>
          </el-form-item>
          <el-form-item label="分类专栏" prop="typeId" :label-width="formLabelWidth"
              :rules="{
                required: true, message: '分类专栏不能为空', trigger: 'blur'
              }">
            <el-select v-model="editForm.typeId" placeholder="请选择分类专栏">
              <el-option v-for="(item,index) in getalltype" :key="item.index" :label="item.name" :value="item.id + ''"></el-option>
            </el-select>
            <el-button type="primary" size="small" @click="dialog2= true" style="margin-left: 10px;">新建分类专栏</el-button>
          </el-form-item>
          <el-form-item label="发布形式" prop="published" :label-width="formLabelWidth"
              :rules="{
                required: true, message: '发布形式不能为空', trigger: 'blur'
              }">
            <el-select v-model="editForm.published" placeholder="请选择发布形式，，默认为私密">
              <el-option label="私密" value=0></el-option>
              <el-option label="公开" value=1></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitBlog('editForm2')">确 定</el-button>
        </div>
      </el-dialog>
  
      <!-- 新建分类专栏 -->
      <el-dialog
        title="新建分类专栏"
        v-model="dialog2"
        width="30%"
        >
        <el-form status-icon :model="type" ref="type" :rules="rules2" label-width="120px">
          <el-form-item label="分类专栏名" prop="name">
            <el-input v-model="type.name" placeholder="请输入要新建的分类专栏名"></el-input>
          </el-form-item>
        </el-form>
         <span slot="footer" class="dialog-footer">
            <el-button @click="dialog2 = false">取 消</el-button>
            <el-button type="primary" @click="submitForm('type')">确 定</el-button>
          </span>
      </el-dialog>
  
  
    </div>
  </template>
  <script>
    export default {
        inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
      name: "WriteGame",
      data() {
        return {
          editForm: {  //游戏文章表单
            id: null,
            name: '',
            description: '',
            developer:'',
            sellTime:'',
            collectTim:'',
            platform:'',
            first_picture: '',
            officialWeb:'',
            configRequire:'',
            gameContent: '',
            typeId: '',
            flag:'',
            published: 1,
            tags: [],
            tGameTags:[],
          },
          editForm2: {  //用来校验的表单
            typeId: null,
            flag:'',
            published: null,
          },
          oldtags:'',  //字符串类型的标签
          type:{  //分类专栏
             name:''
          },
          getalltype:[], // 所有分类专栏
          dialogFormVisible: false,  //控制发布游戏对话框
          dialog2: false,  //控制新增分类专栏对话框
          rules1: {  //表单校验
            name: [
              {required: true, message: '请输入标题', trigger: 'blur'},
              {min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur'}
            ],
            description: [
              {required: true, message: '请输入描述', trigger: 'blur'}
            ],
            developer: [
              {required: true, message: '请输入开发商', trigger: 'blur'}
            ],
            sellTime: [
              {required: true, message: '请输入发售时间', trigger: 'blur'}
            ],
            collectTime: [
              {required: true, message: '请输入收录时间', trigger: 'blur'}
            ],
            platform: [
              {required: true, message: '请输入游戏平台', trigger: 'blur'}
            ],
            officialWeb: [
              {required: true, message: '请输入官方网址', trigger: 'blur'}
            ],
            configRequireconfigRequire: [
              {required: true, message: '请输入配置要求', trigger: 'blur'}
            ],
            first_picture: [
              {required: true, message: '请输入封面图片', trigger: 'blur'}
            ],
            gameContent: [
              {required: true, message: '请输入文章内容', trigger: 'blur'}
            ],
          },
          rules2:{ //表单校验
            name: [
              {required: true, message: '分类专栏名不能为空', trigger: 'blur'}
            ],
          },
          formLabelWidth: '120px' , // 输入框的宽度
          inputVisible: false,
          inputValue: '',
          btags: null,
          tgameTags: [],
        }
      },
      created() {
       this.initType();
      },
      methods: {
        handleClose(tag) {
          this.editForm.tags.splice(this.editForm.tags.indexOf(tag), 1);
        },
  
        showInput() {
          this.inputVisible = true;
          this.$nextTick(_ => {
            this.$refs.saveTagInput.$refs.input.focus();
          });
        },
  
        handleInputConfirm() {
          let inputValue = this.inputValue;
          if (inputValue) {
            this.editForm.tags.push(inputValue);
          }
          this.inputVisible = false;
          this.inputValue = '';
        },
        //去发布文章
        toSubmit() {
          this.dialogFormVisible = true
          //console.log("set visible success")
          this.initType()
        },
        //初始化文章专栏
        initType(){
          const _this = this
          //console.log(111)
          this.getRequest('/type/getAllType').then(resp=>{
            //console.log(resp)
            _this.getalltype = resp.data
          })
        },
        //添加新的分类专栏
        addNewType(){
          const _this = this 
          this.postRequest('/type/saveType',this.type).then(resp=>{
            if(resp){
              _this.type.name = ''
              this.initType()
              _this.dialog2 = false
            }
          })
        },
        //校验添加分类专栏表单
        submitForm(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              this.addNewType()
            } else {
              return false;
            }
          });
        },
        //校验游戏基本内容表单
        tosubmitForm(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              this.toSubmit()
            } else {
                //console.log("tosubmitForm function says not valid")
              return false;
            }
          });
        },
        //校验发布游戏表单,校验成功后发布游戏
        submitBlog(formName) {
          console.log(this.editForm)
          const _this = this
          this.$refs[formName].validate((valid) => {
            if (valid) {
              //发布
              this.postRequest('/game/saveGame',this.editForm).then(resp=>{
                if(resp){
                  console.log(this.editForm)
                  _this.dialogFormVisible = false
                  //alert("发布成功，点击确定跳转到游戏列表页面!")
                  _this.$router.push('/admin/allgames')
                }
              })
            } else {
              return false;
            }
          });
        },
        //暂时保存游戏
        temporarySave(){
          const _this = this
          console.log( this.editForm)
          this.postRequest('/game/temporarySave',this.editForm).then(resp=>{
            if(resp){
              this.$message('已成功保存草稿成功');
            }
          })
        }
      }
    }
  </script>
  
  <style>
    .m_container{
      margin-top: 20px;
    }
    .el-tag + .el-tag {
      margin-left: 10px;
    }
    .button-new-tag {
      margin-left: 10px;
      height: 32px;
      line-height: 30px;
      padding-top: 0;
      padding-bottom: 0;
    }
    .input-new-tag {
      width: 90px;
      margin-left: 10px;
      vertical-align: bottom;
    }
  </style>
  