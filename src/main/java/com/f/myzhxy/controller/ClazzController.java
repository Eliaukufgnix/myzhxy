package com.f.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.myzhxy.entity.Clazz;
import com.f.myzhxy.service.ClazzService;
import com.f.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "班级控制器")
@RestController
@RequestMapping("sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    // sms/clazzController/saveOprUpdateClazz
    @ApiOperation("增加或者修改班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(
            @ApiParam("JSON格式的班级信息") @RequestBody Clazz clazz
    ){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    // sms/clazzController/deleteClazz
    @ApiOperation("删除班级信息")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(
          @ApiParam("要删除的所有班级的id的JSON集合") @RequestBody List<Integer> ids
    ){
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    // sms/clazzController/getClazzs
    @ApiOperation("查询所有班级信息")
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> clazzs=clazzService.getClazzs();
        return Result.ok(clazzs);
    }

    // sms/clazzController/getClazzByOpr/1/3?gradeName=&name=
    @ApiOperation("分页带条件的查询班级信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzByOpr(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询的页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询的查询条件") Clazz clazz
    ){
        //设置分页信息
        Page<Clazz> page = new Page<>(pageNo, pageSize);
        IPage<Clazz> iPage = clazzService.getClazzsByOpr(page,clazz);
        return Result.ok(iPage);
    }
}
