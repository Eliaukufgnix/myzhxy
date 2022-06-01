package com.f.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.myzhxy.entity.Grade;
import com.f.myzhxy.service.GradeService;
import com.f.myzhxy.mapper.GradeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author F
* @description 针对表【tb_grade】的数据库操作Service实现
* @createDate 2022-05-13 17:08:29
*/
@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService{

    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> pageParam, String gradeName) {
        //设置查询条件
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like("name",gradeName);
        }
        //查询结果排序
        queryWrapper.orderByDesc("id");
        //分页查询数据
        Page<Grade> page = baseMapper.selectPage(pageParam, queryWrapper);
        return page;
    }

    @Override
    public List<Grade> getGrades() {
        List<Grade> grades = baseMapper.selectList(null);
        return grades;
    }

}




