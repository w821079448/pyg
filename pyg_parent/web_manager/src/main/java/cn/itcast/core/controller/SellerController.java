package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    @RequestMapping("/search")
    public PageResult search(@RequestBody Seller seller,Integer page,Integer rows){
        PageResult result=sellerService.search(seller,page,rows);
        return result;
    }
    @RequestMapping("/findOne")
    public Seller findOne(String id){
        Seller seller=sellerService.findOne(id);
        return seller;
    }

    @RequestMapping("/updateStatus")
    public Result updateStatus(String sellerId,String status){
        try {
            sellerService.updateStatus(sellerId,status);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,"修改失败");
        }

    }
}
