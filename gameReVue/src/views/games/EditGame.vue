<template>
    <div class="m_container1">
     
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
            <el-upload
              class="game-uploader"
              action="/api/upload/game"  
              :on-success="handleUploadSuccess"
              :before-upload="beforeUpload"
              accept="image/*"
              :show-file-list="false">
              <el-button size="small" type="primary">上传图片</el-button>
            </el-upload>
          </el-form-item>

          <el-form-item label="文章图片">
              <el-tag
                :key="index"
                v-for="(imageUrl, index) in this.editForm.images"
                closable
                :disable-transitions="false"
                @close="handleClose2(imageUrl)"
              >
                {{ imageUrl }}
              </el-tag>
              <el-input
                  style="width: 30%;margin-left: 10px;"
                  v-if="inputVisible2"
                  v-model="inputValue2"
                  ref="saveImageInput"
                  size="small"
                  @keyup.enter.native="handleInputConfirm2"
                  @blur="handleInputConfirm2"
                >
                </el-input>
            <el-button v-else class="button-new-tag" style="margin-left: 10px;" size="small" @click="showInput2">+ 添加图片</el-button>
            </el-form-item>

          <el-form-item label="文章标签">
            <!-- <el-input v-model="oldtags" placeholder="请选择标签，多个标签请用逗号隔开" width="80%"></el-input> -->
            <el-tag
              :key="index"
              v-for="(tag,index) in editForm.tGameTags"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{tag}}
            </el-tag>
            <el-input
              style="width: 30%;margin-left: 10px;"
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

          <el-form-item label="分类专栏" prop="typeId">
            <el-select v-model="editForm.typeId" placeholder="请选择分类专栏">
              <el-option v-for="(item,index) in getalltype" :key="item.index" :label="item.name" :value="item.id + ''"></el-option>
            </el-select>
            <el-button type="primary" size="small" @click="dialog2= true" style="margin-left: 10px;">新建分类专栏</el-button>
          </el-form-item>
          <el-form-item label="是否发布" prop="shareStatement">
            <el-select v-model="editForm.shareStatement" placeholder="选择是否发布，默认发布">
              <el-option label="发布" value="true"></el-option>
              <el-option label="草稿" value="false"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布形式" prop="published">
            <el-select v-model="editForm.published" placeholder="请选择发布形式，默认为私密">
              <el-option label="私密" value=false></el-option>
              <el-option label="公开" value=true></el-option>
            </el-select>
          </el-form-item>

          <!-- mavon-editor代码在vue3中有问题 -->
          <el-form-item label="介绍内容" prop="gameContent">
            <!-- <mavon-editor v-model="editForm.content"/> -->
            <v-md-editor v-model="editForm.gameContent" height="500px"></v-md-editor>
          </el-form-item>



          <el-form-item style="margin: auto;">
            <el-button type="primary" @click="submitForm('editForm')">保存发布</el-button>
            <router-link to="/admin/allgames">
              <el-button style="margin-left: 10px;">返回</el-button>
            </router-link>
          </el-form-item>
        </el-form>
      </div>
  
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
      name: "EditGame",
      data() {
        return {
          editForm: {  //游戏游戏表单
            id: null,
            name: '',
            description: '',
            developer:'',
            platform:'',
            first_picture: '',
            officialWeb:'',
            configRequire:'',
            gameContent: '',
            shareStatement:'',
            typeId: '1',
            published: null,
            tags: [],
            tGameTags:[],
            images:[],
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
          dialogFormVisible: false,  //控制修改游戏对话框
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
            configRequire: [
              {required: true, message: '请输入配置要求', trigger: 'blur'}
            ],
            first_picture: [
              {required: true, message: '请输入封面图片', trigger: 'blur'}
            ],
            gameContent: [
              {required: true, message: '请输入文章内容', trigger: 'blur'}
            ],
            typeId:[
            {required: true, message: '至少要有一个分类', trigger: 'blur'}
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
          inputVisible2: false,//图片的输入框
          inputValue2: '',
          btags: null,
          originalTags:[],
        }
      },
      props:['id'],
      mounted() {
        this.initGame()
        this.initType()
      },
      created(){

      },
      methods: {
        handleUploadSuccess(response, file, fileList) {
            // 上传成功后的处理
            if (response && response.data && response.data.url) {
              this.editForm.first_picture = response.data.url;  // 设置返回的 URL
            } else {
              this.$message.error('头像上传失败');
            }
          },
          beforeUpload(file) {
            // 可以在此检查文件类型、大小等
            const isImage = file.type.startsWith('image/');
            if (!isImage) {
              this.$message.error('只能上传图片');
            }
            return isImage;
          },
        handleClose(tag) {
          this.editForm.tGameTags.splice(this.editForm.tGameTags.indexOf(tag), 1);
        },

        handleClose2(imageUrl) {
          this.editForm.images.splice(this.editForm.images.indexOf(imageUrl), 1);
        },
  
        showInput() {
          this.inputVisible = true;
          this.$nextTick(_ => {
            this.$refs.saveTagInput.$refs.input.focus();
          });
        },

        showInput2() {
          this.inputVisible2 = true;
          this.$nextTick(_ => {
            this.$refs.saveImageInput.$refs.input.focus();
          });
        },
  
        handleInputConfirm() {
          let inputValue = this.inputValue;
          if (inputValue) {
            this.editForm.tGameTags.push(inputValue);
          }
          this.inputVisible = false;
          this.inputValue = '';
        },

        handleInputConfirm2() {
          let inputValue2 = this.inputValue2;
          if (inputValue2) {
            this.editForm.images.push(inputValue2);
          }
          this.inputVisible2 = false;
          this.inputValue2 = '';
        },
        //初始化游戏数据
        async initGame(){
          const _this = this
          //console.log(this.id)
          let resp = await  this.getRequest('/game/getByGameId?id=' + this.id)
          console.log(resp)
          //右侧的是后端传来的，即Java类中名字
          _this.editForm = resp.data.data
          _this.editForm.published = _this.editForm.published == "公开" ? "私密":"私密"
          _this.editForm.typeId = _this.editForm.typeId + ''
          _this.editForm.shareStatement = _this.editForm.shareStatement == true ? "发布" : "草稿"
          _this.editForm.first_picture = _this.editForm.firstPicture
          
          if(_this.editForm.typeId==null){
            _this.editForm.typeId = '1'
            console.log(this.editForm.typeId)
          }

          let resp2 = await  this.getRequest('/tag/getTagByGameId?id=' + this.id)
          
          
          resp2.data.forEach(element => {
            _this.originalTags.push(element.name + '');                
          });
          _this.editForm.tGameTags=_this.originalTags
          let resp3 = await this.getRequest(`/game-image/gameImage/${this.id}`);
          _this.editForm.images = resp3.data;


        },
        initType(){
          const _this = this
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
   //校验游戏基本内容表单
        submitForm(formName) {
          if(this.editForm.tGameTags.length==0)
          {
            alert("至少要有一个标签！")
            return
          }
          console.log(this.editForm.typeId)
          if(this.editForm.typeId=="null")
          {
            alert("必须要有分类！")
            return
          }
          if(true){
              this.$refs[formName].validate((valid) => {
              if (valid) {
                //发布博客
                this.postRequest(`/game-image/update/${this.id}`,this.editForm.images).then(resp2=>{
                })
                this.putRequest('/game/updateGame',this.editForm).then(resp=>{
                  //console.log(this.editForm)

                    if(resp){
                      //alert("更新成功，点击确定跳转到游戏列表页面!")
                      this.$router.push('/admin/allGames')
                    }
                })

            } else {
              return false;
            }
          });
        }else{
          this.temporarySave()
        }

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
  