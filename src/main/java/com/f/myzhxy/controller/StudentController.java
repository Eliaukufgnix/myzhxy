package com.f.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.myzhxy.entity.Student;
import com.f.myzhxy.service.StudentService;
import com.f.myzhxy.util.MD5;
import com.f.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="学生控制器")
@RestController
@RequestMapping("sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;

    // sms/studentController/addOrUpdateStudent
    @ApiOperation("增加或修改学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(
          @ApiParam("要保存或者修改得学生JSON") @RequestBody Student student
    ){
        //对学生的密码进行加密
        Integer id = student.getId();
        if (id==null||id==0) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        //保存学生信息进入数据库
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    // sms/studentController/delStudentById
    @ApiOperation("删除学生信息")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(
            @ApiParam("要删除的所有的学生的JSON的集合") @RequestBody List<Integer> ids
    ){
        //接收参数
        //调用Service层方法，完成删除
        studentService.removeByIds(ids);
        return Result.ok();
    }

    // sms/studentController/getStudentByOpr/1/3
    @ApiOperation("分页带条件的查询学生信息")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
           @ApiParam("分页查询的页码数")  @PathVariable("pageNo") Integer pageNo,
           @ApiParam("分页查询的页大小")  @PathVariable("pageSize") Integer pageSize,
           @ApiParam("分页查询的查询条件") Student student
    ){
        //分页信息封装Page对象
        Page<Student> page = new Page<>(pageNo, pageSize);
        //进行查询
        IPage<Student> studentIPage=studentService.getStudentByOpr(page,student);
        //封装Result返回
        return Result.ok(studentIPage);
    }
}
