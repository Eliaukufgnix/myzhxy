package com.f.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.myzhxy.entity.Student;
import com.f.myzhxy.entity.Teacher;
import com.f.myzhxy.service.TeacherService;
import com.f.myzhxy.util.MD5;
import com.f.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="教师控制器")
@RestController
@RequestMapping("sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // sms/teacherController/saveOrUpdateTeacher POST
    @ApiOperation("增加或修改教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @ApiParam("要保存或者修改得教师JSON") @RequestBody Teacher teacher
    ){
        //对教师的密码进行加密
        Integer id = teacher.getId();
        if (id==null||id==0) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        //保存教师信息进入数据库
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    // sms/teacherController/deleteTeacher DELETE
    @ApiOperation("删除教师信息")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
           @ApiParam("要删除的所有的教师的JSON的集合") @RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return Result.ok();
    }

    /*
        GET
        sms/teacherController/getTeachers/1/3
        sms/teacherController/getTeachers/1/3?name=&clazzName=
     */
    @ApiOperation("分页带条件的查询教师信息")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
          @ApiParam("页码数") @PathVariable("pageNo") Integer pageNo,
          @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize,
          @ApiParam("查询条件") Teacher teacher
    ){
        //分页信息封装Page对象
        Page<Teacher> page = new Page<>(pageNo, pageSize);
        //进行查询
        IPage<Teacher> teacherIPage=teacherService.getTeacherByOpr(page,teacher);
        //封装Result返回
        return Result.ok(teacherIPage);
    }


}
