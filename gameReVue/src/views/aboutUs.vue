<template>
    <div class="about-us">
      <h1>关于我们</h1>
      <div class="team">
        <div class="team-member">
          <img src="https://img.hongyoubizhi.com/picture/pages/regular/2021/08/11/12/17/85633671_p0_master1200.jpg" alt="李裕卓" />
          <h2>李裕卓 - 组长</h2>
          <p>李裕卓负责项目总体的协调与规划，解决开发中遇到的复杂问题，负责了后端和一部分前端的开发。</p>
        </div>
  
        <div class="team-member">
          <img src="https://img.hongyoubizhi.com/picture/pages/regular/2022/02/18/20/17/96311355_p0_master1200.jpg" alt="王嘉鋆" />
          <h2>王嘉鋆 - 组员</h2>
          <p>王嘉鋆负责数据库数据的填充，并对功能进行了充分测试，同时负责完善了项目计划、项目测试文档。</p>
        </div>
  
        <div class="team-member">
          <img src="https://img.hongyoubizhi.com/picture/pages/regular/2021/01/17/05/40/87036407_p0_master1200.jpg" alt="张修铭" />
          <h2>张修铭 - 组员</h2>
          <p>张修铭负责了一部分前端开发和数据库的填充，同时负责完善了数据库设计、结项评审文档。</p>
        </div>
      </div>
    </div>

    <div class="introduce">
      <h1>技术栈</h1>
      <p>
        后端：SpringBoot<br>
        前端：Vue + Element-Plus <br>
        数据库：Mysql <br>
        服务器环境：阿里云 Ubuntu22.04
      </p>
    </div>
    <div class="introduce">
      <h1>关于验证码部分的说明</h1>
      <p>
        由于向手机发送验证码的api审核没有通过（需要网站提前备案）<br>
        <img style="width:50%" src="https://imgcache.qq.com/operation/dianshi/other/smsv2.f51231111396fe42300595f62ae3f67f9d44b89f.png" alt="困难" />
        该部分实现如下所述：<br>
        1.完成了前端向后端请求验证码的部分。<br>
        2.后端接受请求后，生成验证码被规定为1234以便于后续使用（随机生成的话就不知道说什么了）<br>
        3.完成前端向后端验证验证码的部分。<br>
      </p>
    </div>
    <div class="reflection">
      <h2>软件工程课设的感悟</h2>
      <p>
        在这个项目中，我们不仅回顾并巩固了大学期间学到的很多知识，
        如数据库设计、前后端分离架构、API开发以及用户体验设计等，还学习了新的技术工具。
        从前端的 Vue.js 到后端的 SpringBoot，再到数据库的设计与优化，项目的每个环节都充满了挑战与成长的机会。
      </p>
      <p>
        这次课设也提醒我们，作为即将毕业的大学生，这可能是我们在大学期间最后一个大型的团队合作项目了。
        回首过去的四年，曾经的代码调试、方案讨论与合作，已经成为我们宝贵的回忆。
        我们感受到的是成长与进步，而这次课设也为我们的大学时光画上了一个圆满的问号。
      </p>
    </div>

  </template>
  
  <script>
  export default {
    name: "AboutUs",
  };
  </script>
  
  <style scoped>
  .about-us {
    text-align: center;
    padding: 2rem;
  }
  
  .team {
    display: flex;
    justify-content: space-around;
    margin-top: 2rem;
  }
  
  .team-member {
    width: 30%;
    padding: 1rem;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
  }
  
  .team-member img {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    margin-bottom: 1rem;
  }
  
  .team-member h2 {
    margin-bottom: 1rem;
  }
  
  .team-member p {
    font-size: 14px;
    color: #555;
  }

  .introduce p {
    font-size: 1.1rem;
    color: #555;
    line-height: 1.8;
    margin-bottom: 1rem;
  }

  .reflection {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #f0f8ff;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-top: 2rem;
}

.reflection h2 {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 1.5rem;
  text-align: center;
}

.reflection p {
  font-size: 1.1rem;
  color: #555;
  line-height: 1.8;
  max-width: 800px;
  text-align: center;
  margin-bottom: 1rem;
}

.reflection p:last-child {
  margin-bottom: 0;
}

@media (max-width: 768px) {
  .reflection p {
    font-size: 1rem;
    padding: 0 1rem;
  }
}

.introduce {
    background: #fff;
    margin: 20px auto;
    padding: 20px;
    max-width: 90%;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
}

.introduce h1 {
    font-size: 24px;
    color: #333;
    margin-bottom: 15px;
    text-align: center;
}

.introduce p {
    font-size: 16px;
    line-height: 1.8;
    margin-bottom: 20px;
}

.introduce img {
    display: block;
    margin: 0 auto;
    max-width: 100%;
    height: auto;
    border-radius: 8px;
}

/* 链接样式 */
a {
    color: #007BFF;
    text-decoration: none;
}

a:hover {
    text-decoration: underline;
}
  </style>
  