package com.f.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.myzhxy.entity.Admin;
import com.f.myzhxy.service.AdminService;
import com.f.myzhxy.util.MD5;
import com.f.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理员控制器")
@RestController
@RequestMapping("sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // sms/adminController/getAllAdmin/1/3 GET adminName=a
    @ApiOperation("分页带条件的查询管理员信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
          @ApiParam("页码数") @PathVariable("pageNo") Integer pageNo,
          @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize,
          @ApiParam("管理员名字") String adminName
    ){
        Page<Admin> pageParam = new Page<>(pageNo,pageSize);
        IPage<Admin> iPage=adminService.getAdminByOpr(pageParam,adminName);
        return Result.ok(iPage);
    }

    // sms/adminController/saveOrUpdateAdmin POST admin
    @ApiOperation("增加或修改管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
          @ApiParam("JSON格式的管理员信息") @RequestBody Admin admin
    ){
        Integer id = admin.getId();
        if (id==null||id==0) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    // sms/adminController/deleteAdmin DELETE List<Integer> ids
    @ApiOperation("删除管理员信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
          @ApiParam("要删除的管理员的多个id的JSON集合") @RequestBody List<Integer> ids
    ){
        adminService.removeByIds(ids);
        return Result.ok();
    }
}
