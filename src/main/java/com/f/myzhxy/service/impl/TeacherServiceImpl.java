package com.f.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.myzhxy.entity.Admin;
import com.f.myzhxy.entity.LoginForm;
import com.f.myzhxy.entity.Student;
import com.f.myzhxy.entity.Teacher;
import com.f.myzhxy.service.TeacherService;
import com.f.myzhxy.mapper.TeacherMapper;
import com.f.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author F
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2022-05-13 17:08:29
*/
@Service("teacherServiceImpl")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

    @Override
    public Teacher login(LoginForm loginForm) {
        //创建QueryWrapper对象
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
        //拼接查询条件
        queryWrapper.eq("name",loginForm.getUsername());
        //转换成密文进行查询
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper<Teacher>();
        queryWrapper.eq("id",userId);
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public IPage<Teacher> getTeacherByOpr(Page<Teacher> page, Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            if (teacher.getClazzName()!=null) {
                queryWrapper.eq("clazz_name",teacher.getClazzName());
            }
            if (teacher.getName()!=null) {
                queryWrapper.like("name",teacher.getName());
            }
            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");
        //创建分页对象
        Page<Teacher> pages = baseMapper.selectPage(page, queryWrapper);
        return pages;
    }
}




