package com.lzj.admin.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	 * 展示商品分类名列表
	 * @return
	 */
	@RequestMapping("/queryAllGoodsTypes")
	@ResponseBody
	public List<GoodsType> queryAllGoodsTypes(){
	    return goodsTypeService.queryAllGoodsTypes();
	}
}
