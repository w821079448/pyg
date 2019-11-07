package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationDao;
import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.specification.SpecificationQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationDao specificationDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;


    @Override
    public PageResult findPage(Specification specification, Integer page, Integer rows) {
        Page page1 = PageHelper.startPage(page, rows);
       SpecificationQuery specificationQuery=new SpecificationQuery();
        SpecificationQuery.Criteria criteria = specificationQuery.createCriteria();
        if (specification!=null){
            if (specification.getSpecName()!=null&&!"".equals(specification.getSpecName())) {
                criteria.andSpecNameLike("%" + specification.getSpecName() + "%");
            }
        }
        Page<Specification> specifications = (Page<Specification>) specificationDao.selectByExample(specificationQuery);
        return new PageResult(specifications.getTotal(),specifications.getResult());
    }

    @Override
    public void add(SpecEntity specEntity) {
        specificationDao.insertSelective(specEntity.getSpecification());
        if(specEntity.getSpecificationOptionList()!=null){
            for (SpecificationOption specificationOptions :specEntity.getSpecificationOptionList()){
                specificationOptions.setSpecId(specEntity.getSpecification().getId());
                specificationOptionDao.insertSelective(specificationOptions);
            }
        }
    }

    @Override
    public SpecEntity findOne(Long id) {
        Specification specification = specificationDao.selectByPrimaryKey(id);
        SpecificationOptionQuery specificationOptionQuery=new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(specificationOptionQuery);
        SpecEntity specEntity=new SpecEntity();
        specEntity.setSpecification(specification);
        specEntity.setSpecificationOptionList(specificationOptions);
        return specEntity;
    }

    @Override
    public void update(SpecEntity specEntity) {
        specificationDao.updateByPrimaryKeySelective(specEntity.getSpecification());
        if(specEntity.getSpecificationOptionList()!=null){
            for (SpecificationOption specificationOptions :specEntity.getSpecificationOptionList()){
                specificationOptions.setSpecId(specEntity.getSpecification().getId());
                specificationOptionDao.updateByPrimaryKeySelective(specificationOptions);
            }
        }
    }

    @Override
    public void delete(Long []ids) {
        if (ids != null) {
            for (Long id : ids) {
                specificationDao.deleteByPrimaryKey(id);

                SpecificationOptionQuery query=new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = query.createCriteria();
                criteria.andSpecIdEqualTo(id);
                specificationOptionDao.deleteByExample(query);
            }
        }
    }

    @Override
    public List<Map> selectOptionList() {
        return specificationDao.selectOptionList();
    }
}
