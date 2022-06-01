package com.f.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.myzhxy.entity.LoginForm;
import com.f.myzhxy.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author F
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2022-05-13 17:08:29
*/
public interface StudentService extends IService<Student> {

    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);

    IPage<Student> getStudentByOpr(Page<Student> page, Student student);
}
