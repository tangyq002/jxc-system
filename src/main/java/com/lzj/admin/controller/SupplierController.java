package com.lzj.admin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.Supplier;
import com.lzj.admin.query.SupplierQuery;
import com.lzj.admin.service.SupplierService;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
	
    @Resource
    private SupplierService supplierService;

    /**
     * 供应商管理主页
     * @return
     */
    @RequestMapping("index" )
    public String index(){
        return "/supplier/supplier";
    }

    /**
     * 分页查询供应商列表
     * @param supplierQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> supplierList(SupplierQuery supplierQuery){
        return supplierService.supplierList(supplierQuery);
    }

    /**
     * 进入编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateSupplierPage")
    public String addRolePage(Integer id, Model model){
        if(null !=id){
            model.addAttribute("supplier",supplierService.getById(id));
        }
        return "supplier/add_update";
    }


    /**
     * 添加供应商
     * @param supplier
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public RespBean saveSupplier(Supplier supplier){
    	supplierService.saveSupplier(supplier);
        return RespBean.success("记录添加成功");
    }

    /**
     * 更新供应商
     * @param supplier
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public RespBean updateSupplier(Supplier supplier){
    	supplierService.updateSupplier(supplier);
        return RespBean.success("记录更新成功");
    }

    /**
     * 删除供应商
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public RespBean deleteSupplier(Integer[] ids){
    	supplierService.deleteSupplier(ids);
        return RespBean.success("客户记录删除成功");
    }
    
    /**
     * 查询全部供应商
     * @return
     */
    @RequestMapping("allSuppliers")
    @ResponseBody
    public List<Supplier> allSuppliers(){
        return supplierService.list(new QueryWrapper<Supplier>().eq("is_del",0));
    }
}
