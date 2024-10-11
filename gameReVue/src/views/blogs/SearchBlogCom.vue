<template>
    <div>
          <!-- 博客文章 -->
          <el-table
              :data="blogsData"
              stripe
              style="width: 100%">
              <el-table-column label="文章列表" min-width="180">
              <template #default="scope">
                  <el-card  class="box-card">
                  <div style="font-size: 18px;">
                      <!-- v-if="!scope.row.shareStatement" 这里根据shareStatement属性判断文章是否为草稿 -->
                      <el-button size="mini" v-if="!scope.row.shareStatement" type="info" icon="el-icon-edit" circle></el-button>
                      {{scope.row.title}}
                  </div>
                  <div style="margin-top: 10px;">
  
                      <el-tag size="small" style="margin-right: 10px;"  v-if="scope.row.isDelete" type="danger">已删除</el-tag>
                      <el-tag size="small" style="margin-right: 10px;"  v-if="!scope.row.shareStatement" type="warning">草稿</el-tag>
                      <el-tag style="margin-right: 10px;" v-if="scope.row.shareStatement" size="small">{{scope.row.flag}}</el-tag>
                      <el-tag style="margin-right: 20px;" v-if="scope.row.shareStatement" size="small" type="info">{{scope.row.published == '0' ? '私密' : '公开'}}</el-tag>
                      <i style="margin-right: 20px;"><el-icon style="transform: translateY(2px); margin-right: 4px;"><View /> </el-icon>{{scope.row.views}}</i>
                      <i style="margin-right: 20px;"><el-icon style="transform: translateY(2px); "><ChatSquare /></el-icon> {{scope.row.commentCount}} </i>
                      <i style="margin-right: 20px;"><el-icon style="transform: translateY(2px); margin-right: 4px;"><Calendar /></el-icon> {{scope.row.createTime}}</i>
                         
                      <el-button style="float: right;" type="danger" size="mini">删除</el-button>
                      <el-button style="float: right; margin-right: 10px;" type="primary" size="mini">编辑</el-button>
                  
                  </div>
  
                  </el-card>
              </template>
                  
              </el-table-column>
          </el-table>
  
    </div>
  </template>
  
  <script>
  export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: 'SearchBlogCom',
    data () {
     return {
      blogsData: [], //文章数据
  
     }
    },
    props:["blogsData"], //接收父组件传过来的值
    methods:{
    }
  }
  </script>
  
  <style scoped>
  
  </style>