package com.f.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.myzhxy.entity.Clazz;
import com.f.myzhxy.service.ClazzService;
import com.f.myzhxy.mapper.ClazzMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author F
* @description 针对表【tb_clazz】的数据库操作Service实现
* @createDate 2022-05-13 17:08:29
*/
@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz>
    implements ClazzService{

    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> pageParam, Clazz clazz) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(clazz != null) {
            //年级名称条件
            String gradeName = clazz.getGradeName();
            if (!StringUtils.isEmpty(gradeName)) {
                queryWrapper.eq("grade_name", gradeName);
            }
            //班级名称条件
            String name = clazz.getName();
            if (!StringUtils.isEmpty(name)) {
                queryWrapper.like("name", name);
            }
            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");
        }
        Page<Clazz> clazzPage = baseMapper.selectPage(pageParam, queryWrapper);
        return clazzPage;
    }

    @Override
    public List<Clazz> getClazzs() {
        List<Clazz> clazzes = baseMapper.selectList(null);
        return clazzes;
    }
}




