package cn.itcast.core.service;

import cn.itcast.core.dao.template.TypeTemplateDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;




@Service
@Transactional
public class TemplateServiceImpl implements  TemplateService {

    @Autowired
    private TypeTemplateDao typeTemplateDao;

    @Override
    public PageResult findPage(TypeTemplate typeTemplate, Integer page, Integer rows) {
        Page page1 = PageHelper.startPage(page, rows);
        TypeTemplateQuery typeTemplateQuery=new TypeTemplateQuery();
        TypeTemplateQuery.Criteria criteria = typeTemplateQuery.createCriteria();
        if (typeTemplate != null){
            if (typeTemplate.getName()!=null&&!"".equals(typeTemplate.getName())){
                criteria.andNameLike("%"+typeTemplate.getName()+"%");
            }

        }
        Page<TypeTemplate> typeTemplates = (Page<TypeTemplate>) typeTemplateDao.selectByExample(typeTemplateQuery);
        return new PageResult(typeTemplates.getTotal(),typeTemplates.getResult());

    }

    @Override
    public void add(TypeTemplate typeTemplate) {
        typeTemplateDao.insertSelective(typeTemplate);
    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateDao.updateByPrimaryKeySelective(typeTemplate);

    }

    @Override
    public void delete(Long[] ids) {

        if (ids!=null) {
            for (Long id:ids) {
                typeTemplateDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateDao.selectByPrimaryKey(id);

    }


}
