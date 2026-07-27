package com.lzj.admin.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.GoodsType;
import com.lzj.admin.service.GoodsTypeService;

/**
 * @author TianTian
 * @date 2022/1/19 8:36
 */
@Controller
@RequestMapping("/goodsType")
public class GoodsTypeController {
	@Resource
    private GoodsTypeService goodsTypeService;
	
    /**
     *  商品分类管理主页
     * @return
     */
    @RequestMapping("index" )
    public String index(){
        return "/goodsType/goods_type";
    }
    
	/**
	 * 展示商品分类名列表
	 * @return
	 */
	@RequestMapping("/queryAllGoodsTypes")
	@ResponseBody
	public List<GoodsType> queryAllGoodsTypes(){
	    return goodsTypeService.queryAllGoodsTypes();
	}
	
	/**
	 * 商品分类主页列表
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public  Map<String,Object> list(){
		Map<String,Object> map = new HashMap<>();
		//查出分类list放入map
	    List<GoodsType> list = goodsTypeService.queryAllGoodsTypes();
	    map.put("code",0);
	    map.put("msg","");
	    map.put("data",list);
	    return map;
	}
	
	/**
	 * 添加子类
	 * @param goodsType
	 * @return
	 */
    @RequestMapping("save")
    @ResponseBody
    public RespBean saveGoodsType(GoodsType goodsType){
    	goodsTypeService.saveGoodsType(goodsType);
        return RespBean.success("记录添加成功");
    }

    /**
     * 删除当前分类
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public RespBean deleteGoodsType(Integer id){
    	goodsTypeService.deleteGoodsType(id);
        return RespBean.success("商品分类记录删除成功");
    }
    
    /**
     * 打开新增子类窗口
     * @param typeId
     * @param model
     * @return
     */
    @RequestMapping("/addGoodsTypePage")
    public String addGoodsTypePage(Integer pId, Model model){
        model.addAttribute("pId",pId);
        return "goodsType/add";
    }
}
