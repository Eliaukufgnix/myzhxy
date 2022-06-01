package com.f.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.myzhxy.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.f.myzhxy.entity.LoginForm;

/**
* @author F
* @description 针对表【tb_admin】的数据库操作Service
* @createDate 2022-05-13 17:08:29
*/
public interface AdminService extends IService<Admin> {

    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);

    IPage<Admin> getAdminByOpr(Page<Admin> pageParam, String adminName);
}
