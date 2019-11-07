package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.TemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {

    @Reference
    private TemplateService templateService;

    @RequestMapping("/search")
    public PageResult search(@RequestBody TypeTemplate typeTemplate, Integer page, Integer rows){
        PageResult pageResult = templateService.findPage(typeTemplate, page, rows);
        return pageResult;
    }
    @RequestMapping("/add")
    private Result add(@RequestBody TypeTemplate typeTemplate){

        try {
            templateService.add(typeTemplate);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }

    @RequestMapping("/update")
    private Result update(@RequestBody TypeTemplate typeTemplate){

        try {
            templateService.update(typeTemplate);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }
    @RequestMapping("/delete")
    private Result delete(Long[] ids){
            try {
                    templateService.delete(ids);
                return new Result(true, "删除成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "删除失败");
            }
    }

    @RequestMapping("/findOne")
    public TypeTemplate findOne(Long id){
        return templateService.findOne(id);
    }
}
