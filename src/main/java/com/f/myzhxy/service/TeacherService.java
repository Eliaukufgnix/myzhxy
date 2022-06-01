package com.f.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.myzhxy.entity.LoginForm;
import com.f.myzhxy.entity.Student;
import com.f.myzhxy.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author F
* @description 针对表【tb_teacher】的数据库操作Service
* @createDate 2022-05-13 17:08:29
*/
public interface TeacherService extends IService<Teacher> {

    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

    IPage<Teacher> getTeacherByOpr(Page<Teacher> page, Teacher teacher);
}
