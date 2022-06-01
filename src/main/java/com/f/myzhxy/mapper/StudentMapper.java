package com.f.myzhxy.mapper;

import com.f.myzhxy.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author F
* @description 针对表【tb_student】的数据库操作Mapper
* @createDate 2022-05-13 17:08:29
* @Entity com.f.myzhxy.entity.Student
*/

@Repository
public interface StudentMapper extends BaseMapper<Student> {

}




