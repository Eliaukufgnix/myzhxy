# 智慧校园

## 1. 具体的业务功能

![项目功能介绍](readmeimg/%E9%A1%B9%E7%9B%AE%E5%8A%9F%E8%83%BD%E4%BB%8B%E7%BB%8D.png)

## 2. 开发工具和技术栈

![项目技术栈](readmeimg/%E9%A1%B9%E7%9B%AE%E6%8A%80%E6%9C%AF%E6%A0%88.png)

1 操作系统的选择

​			开发者使用Windows10 ,项目运行环境为Linux.服务容器使用Tomcat

2 开发使用的工具

​		  前端开发工具为HbuilderX,配合NodeJS运行时

​          后端开发工具为Idea,使用maven作为项目构建工具

3 数据库管理系统

​			mysql作为数据库服务,同时配合Navicat可视化数据库前端工具

4 相关框架  

​			VUE 独立开发前端代码

​		   SpringBoot快速搭建后端服务

​          MyBatisPlus快速实现数据操作,同时优化业务层代码

​          swagger2快速生成接口文档,同时可以帮助接口测试

​          fastjson快速实现前后端数据转换

​		  commons-fileupload快速实现文件上传

问题1 前后端分离开发,接口文档和接口测试问题        解决方案是: swagger2

问题2 cookie和Session控制登录校验的有效期问题   解决方案是:token

问题3 管理员,教师,学生三个身份登录后权限不同       解决方案是:根据用户类型查询不同的表格,根据不同的类型展示不同的页面

问题4 数据安全问题												   	解决方案是:MD5加密,密码的校验在密文状态下



