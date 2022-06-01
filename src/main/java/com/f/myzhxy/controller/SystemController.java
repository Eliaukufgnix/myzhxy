package com.f.myzhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.f.myzhxy.entity.Admin;
import com.f.myzhxy.entity.LoginForm;
import com.f.myzhxy.entity.Student;
import com.f.myzhxy.entity.Teacher;
import com.f.myzhxy.service.AdminService;
import com.f.myzhxy.service.StudentService;
import com.f.myzhxy.service.TeacherService;
import com.f.myzhxy.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Api(tags = "系统控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    // sms/system/updatePwd/123456/admin POST
    /*
    * 请求参数
    *   oldPwd
    *   newPwd
    *   token 头
    * 响应的数据
    *   Result OK data=null
    * */
    @ApiOperation("更新用户密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
           @ApiParam("token口令") @RequestHeader("token") String token,
           @ApiParam("旧密码") @PathVariable("oldPwd") String oldPwd,
           @ApiParam("新密码") @PathVariable("newPwd") String newPwd
    ){
        //检查token是否过期
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            //token过期
            return Result.fail().message("token失效，请重新登录");
        }
        //token未过期，获取用户ID和类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        //页面传递的密码是明文的，要进行密码转换
        oldPwd= MD5.encrypt(oldPwd);
        newPwd= MD5.encrypt(newPwd);

        //根据用户类型进行判断
        switch (userType){
            case 1:
                //查询条件

                QueryWrapper<Admin> queryWrapper1 = new QueryWrapper<>();
                //判断表中的id和获取的用户ID的值是否一致
                queryWrapper1.eq("id",userId.intValue());
                //判断表中的密码和获取的用户旧密码的值是否一致
                queryWrapper1.eq("password",oldPwd);
                //获取一个对象
                Admin admin = adminService.getOne(queryWrapper1);
                //与原密码一致
                if (admin!=null) {
                    //修改
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else {
                    return Result.fail().message("原密码错误");
                }
                break;

            case 2:
                //查询条件

                QueryWrapper<Student> queryWrapper2 = new QueryWrapper<>();
                //判断表中的id和获取的用户ID的值是否一致
                queryWrapper2.eq("id",userId.intValue());
                //判断表中的密码和获取的用户旧密码的值是否一致
                queryWrapper2.eq("password",oldPwd);
                //获取一个对象
                Student student = studentService.getOne(queryWrapper2);
                //与原密码一致
                if (student!=null) {
                    //修改
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }else {
                    return Result.fail().message("原密码错误");
                }
                break;

            case 3:
                //查询条件

                QueryWrapper<Teacher> queryWrapper3 = new QueryWrapper<>();
                //判断表中的id和获取的用户ID的值是否一致
                queryWrapper3.eq("id",userId.intValue());
                //判断表中的密码和获取的用户旧密码的值是否一致
                queryWrapper3.eq("password",oldPwd);
                //获取一个对象
                Teacher teacher = teacherService.getOne(queryWrapper3);
                //与原密码一致
                if (teacher!=null) {
                    //修改
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else {
                    return Result.fail().message("原密码错误");
                }
                break;
        }
        return Result.ok();
    }

    // sms/system/headerImgUpload
    @ApiOperation("文件上传统一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
           @ApiParam("头像文件") @RequestParam("multipartFile") MultipartFile multipartFile
            ){
        //修改图片名称

        //获取一个随机的字符串
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //获取上传文件的名称（1.jpg）
        String oldFilename = multipartFile.getOriginalFilename();
        //获取到最后一个.的索引
        int i = oldFilename.lastIndexOf(".");
        //从索引位置开始截取，并将uuid与其拼接形成新的文件名称
        String newFilename=uuid.concat(oldFilename.substring(i));

        //保存图片
        //生成文件的保存路径(实际生产环境这里会使用真正的文件存储服务器)
        String portraitPath ="E:/IDEAProjects/Code/myzhxy/target/classes/public/upload/".concat(newFilename);
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //响应图片的路径
        String path="upload/".concat(newFilename);
        return Result.ok(path);
    }

    @ApiOperation("通过token获取用户信息")
    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token){
        // 获取用户中请求的token
        // 检查token 是否过期 20H
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token解析出用户id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        Map<String,Object> map=new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin=adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student=studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher=teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }
        return Result.ok(map);
    }

    @ApiOperation("登录请求验证")
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm,HttpServletRequest request){
        //获取用户提交的验证码和session域中的验证码,进行验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode=(String) session.getAttribute("verifiCode");
        String loginVerifiCode=loginForm.getVerifiCode();
        if("".equals(sessionVerifiCode)||null==sessionVerifiCode){
            //session过期
            return Result.fail().message("验证码失效，请刷新后重试！");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)){
            //验证码错误
            return Result.fail().message("验证码错误！");
        }
        //从session域中移除现有验证码
        session.removeAttribute("verifiCode");

        //准备Map存放响应的数据
        Map<String,Object> map= new LinkedHashMap<>();
        //分用户类型进行校验，不同用户类型查询不同表
        switch (loginForm.getUserType()) {
            case 1:// 管理员身份
                try {
                    // 调用服务层登录方法,根据用户提交的LoginInfo信息,查询对应的Student对象,找不到返回Null
                    Admin admin=adminService.login(loginForm);
                    if (null!=admin) {
                        //用户不为空时，将用户id和用户类型转换成密文，以token的形式向客户端反馈
                        String token = JwtHelper.createToken(admin.getId().longValue(), 1);
                        //将token存放到Map中
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或者密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 捕获异常,向用户响应错误信息
                    return Result.fail().message(e.getMessage());
                }
            case 2:// 学生身份
                try {
                    // 调用服务层登录方法,根据用户提交的LoginInfo信息,查询对应的Teacher对象,找不到返回Null
                    Student student=studentService.login(loginForm);
                    if (null!=student) {
                        //用户不为空时，将用户id和用户类型转换成密文，以token的形式向客户端反馈
                        String token = JwtHelper.createToken(student.getId().longValue(), 2);
                        //将token存放到Map中
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或者密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 捕获异常,向用户响应错误信息
                    return Result.fail().message(e.getMessage());
                }
            case 3:// 教师身份
                try {
                    // 调用服务层登录方法,根据用户提交的LoginInfo信息,查询对应的Teacher对象,找不到返回Null
                    Teacher teacher=teacherService.login(loginForm);
                    if (null!=teacher) {
                        //用户不为空时，将用户id和用户类型转换成密文，以token的形式向客户端反馈
                        String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
                        //将token存放到Map中
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或者密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 捕获异常,向用户响应错误信息
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("查无此用户");
    }


    @ApiOperation("获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码放入session域，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        //将验证码响应给浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
