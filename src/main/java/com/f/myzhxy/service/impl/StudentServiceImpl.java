package com.f.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.myzhxy.entity.LoginForm;
import com.f.myzhxy.entity.Student;
import com.f.myzhxy.service.StudentService;
import com.f.myzhxy.mapper.StudentMapper;
import com.f.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author F
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2022-05-13 17:08:29
*/
@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

    @Override
    public Student login(LoginForm loginForm) {
        //创建QueryWrapper对象
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        //拼接查询条件
        queryWrapper.eq("name",loginForm.getUsername());
        //转换成密文进行查询
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper<Student> queryWrapper=new QueryWrapper<Student>();
        queryWrapper.eq("id",userId);
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> page, Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (student!=null) {
            if (student.getClazzName()!=null){
                queryWrapper.eq("clazz_name",student.getClazzName());
            }
            if (student.getName()!=null){
                queryWrapper.like("name",student.getName());
            }
            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");
        }
        //创建分页对象
        Page<Student> pages = baseMapper.selectPage(page, queryWrapper);
        return pages;
    }
}




