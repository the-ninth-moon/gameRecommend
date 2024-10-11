const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
//配置请求转发的代理

//定义代理的对象
let proxyObj = {};

//拦截http请求
proxyObj['/api'] = {
    ws: false,  //关掉websocket
    // target: 'http://47.98.41.200:8000', //目标转发的地址
    target: 'http://localhost:8000', //目标转发的地址
    changeOrigin: true,
    pathRewrite:{  //请求地址重写
        '^/api': ''  //拦截到的地址不去修改它
    }
}
//把上面的导出来
module.exports = {
    devServer:{  //配置开发环境
        host: 'localhost', //端口号
        port: 8080,//自己的端口
        proxy: proxyObj  //代理对象
    },
    publicPath: "/",
    assetsDir: "static",
    outputDir: 'dist',



}

// vue.config.js

