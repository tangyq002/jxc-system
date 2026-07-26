package com.lzj.admin.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzj.admin.pojo.GoodsUnit;
import com.lzj.admin.service.GoodsUnitService;

/**
 * @author TianTian
 * @date 2022/1/19 8:54
 */
@Controller
@RequestMapping("/goodsUnit")
public class GoodsUnitController {
	@Resource
    private GoodsUnitService goodsUnitService;
	
	@RequestMapping("/allGoodsUnits")
	@ResponseBody
	public List<GoodsUnit> allGoodsUnits(){
	    return goodsUnitService.allGoodsUnits();
	}
}
