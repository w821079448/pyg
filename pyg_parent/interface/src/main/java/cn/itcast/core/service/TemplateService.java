package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.template.TypeTemplate;

public interface TemplateService {
    public PageResult findPage(TypeTemplate typeTemplate, Integer page, Integer rows);


    void add(TypeTemplate typeTemplate);

    void update(TypeTemplate typeTemplate);

    void delete(Long[] ids);

    TypeTemplate findOne(Long id);
}
