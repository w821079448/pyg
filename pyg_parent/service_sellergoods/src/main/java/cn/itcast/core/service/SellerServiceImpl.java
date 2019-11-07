package cn.itcast.core.service;

import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.pojo.seller.SellerQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerDao sellerDao;
    @Override
    public void add(Seller seller) {
        seller.setCreateTime(new Date());
        seller.setStatus("0");
        sellerDao.insertSelective(seller);
    }

    @Override
    public PageResult search(Seller seller, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        seller.setStatus("0");
        SellerQuery query=new SellerQuery();
        SellerQuery.Criteria criteria = query.createCriteria();
        if(seller!=null){
            if (seller.getStatus() != null && !"".equals(seller.getStatus())) {
                criteria.andStatusEqualTo(seller.getStatus());
            }
            if(seller.getName()!=null&&!"".equals(seller.getName())){
                criteria.andNameLike("%"+seller.getName()+"%");
            }
            if(seller.getNickName()!=null&&!"".equals(seller.getNickName())){
                criteria.andNameLike("%"+seller.getNickName()+"%");
            }
        }
        Page<Seller> sellers = (Page<Seller>) sellerDao.selectByExample(query);
        return new PageResult(sellers.getTotal(),sellers.getResult());
    }

    @Override
    public Seller findOne(String id) {
        Seller seller = sellerDao.selectByPrimaryKey(id);
        return seller;
    }

    @Override
    public void updateStatus(String id, String status) {
        Seller seller=new Seller();
        seller.setStatus(status);
        seller.setSellerId(id);
        sellerDao.updateByPrimaryKeySelective(seller);
    }
}
