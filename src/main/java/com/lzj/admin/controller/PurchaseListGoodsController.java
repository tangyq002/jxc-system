package com.lzj.admin.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzj.admin.query.PurchaseListGoodsQuery;
import com.lzj.admin.service.PurchaseListGoodsService;

/**
 * 进货单商品表
 * @author TianTian
 * @date 2022/1/19 12:32
 */
@Controller
@RequestMapping("/purchaseListGoods")
public class PurchaseListGoodsController {
	@Resource
    private PurchaseListGoodsService purchaseListGoodsService;

	/**
	 * 进货商品列表
	 * @param purchaseListGoodsQuery
	 * @return
	 */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> purchaseListGoodsList(PurchaseListGoodsQuery purchaseListGoodsQuery){
        return purchaseListGoodsService.purchaseListGoodsList(purchaseListGoodsQuery);
    }
    
    
}
