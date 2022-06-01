package com.f.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.myzhxy.entity.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author F
* @description 针对表【tb_clazz】的数据库操作Service
* @createDate 2022-05-13 17:08:29
*/
public interface ClazzService extends IService<Clazz> {

    IPage<Clazz> getClazzsByOpr(Page<Clazz> page, Clazz clazz);

    List<Clazz> getClazzs();
}
