package com.f.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.myzhxy.entity.Grade;
import com.f.myzhxy.service.GradeService;
import com.f.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器")
@RestController
@RequestMapping("sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    // sms/GradeController/saveOrUpdateGrade
    @ApiOperation("增加或者修改年级信息，有id是修改，没有id是删除")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
            @ApiParam("JSON格式的Grade对象") @RequestBody Grade grade
    ){
        //接收参数
        //调用Service层方法，完成增加和修改
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    // sms/GradeController/deleteGrade
    @ApiOperation("删除年级信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
            @ApiParam("要删除的所有的grade的JSON的集合") @RequestBody List<Integer> ids
    ){
        //接收参数
        //调用Service层方法，完成删除
        gradeService.removeByIds(ids);
        return Result.ok();
    }

    // sms/GradeController/getGrades
    @ApiOperation("查询所有年级信息")
    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> grades = gradeService.getGrades();
        return Result.ok(grades);
    }

    // sms/GradeController/getGrades/1/3?gradeName=
    @ApiOperation("分页带条件查询年级信息")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGradeByOpr(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,//页码数
            @ApiParam("分页查询的页大小") @PathVariable("pageSize") Integer pageSize,//页大小
            @ApiParam("模糊查询匹配的名称") String gradeName//模糊查询条件
    ){
        //分页 带条件查询
        Page<Grade> page=new Page<>(pageNo,pageSize);
        //调用Service层方法，传入分页信息和查询条件
        IPage<Grade> pageRs=gradeService.getGradeByOpr(page,gradeName);
        //封装Result对象并返回
        return Result.ok(pageRs);
    }
}
