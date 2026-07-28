package com.lzj.admin.controller;


import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.PurchaseList;
import com.lzj.admin.pojo.PurchaseListGoods;
import com.lzj.admin.query.PurchaseListQuery;
import com.lzj.admin.service.PurchaseListService;
import com.lzj.admin.service.UserService;

/**
 * 进货单控制器
 * @author TianTian
 * @date 2022/1/19 12:32
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseListController {
	@Resource
    private PurchaseListService purchaseListService;

    @Resource
    private UserService userService;

    /**
     *显示 进货单号
     * @param model
     * @return
     */
    @RequestMapping("index")
    public String index(Model model){
        model.addAttribute("purchaseNumber",purchaseListService.getNextPurchaseNumber());
        return "purchase/purchase";
    }

    /**
     * 进货入库
     * @param purchaseList
     * @param goodsJson
     * @param principal
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public RespBean save(PurchaseList purchaseList, String goodsJson, Principal principal){
        String userName = principal.getName();
        purchaseList.setUserId(userService.findForName(userName).getId());
        Gson gson = new Gson();
        System.out.println(goodsJson);
        List<PurchaseListGoods> plgList = gson.fromJson(goodsJson,new TypeToken<List<PurchaseListGoods>>(){}.getType());
        purchaseListService.savePurchaseList(purchaseList,plgList);
        return RespBean.success("商品进货入库成功!");
    }

    /**
     * 进货单据查询页
     * @return
     */
    @RequestMapping("searchPage")
    public String searchPage(){
        return "purchase/purchase_search";
    }

    /**
     * 进货列表
     * @param purchaseListQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> purchaseList(PurchaseListQuery purchaseListQuery){
        return purchaseListService.purchaseList(purchaseListQuery);
    }

	/**
	 * 删除
	 * @param id
	 * @return
	 */
    @RequestMapping("delete")
    @ResponseBody
    public RespBean delete(Integer id){
    	purchaseListService.deletePurchaseList(id);
        return RespBean.success("删除成功");
    }
    
    /**
     * 商品采购统计列表
     * @param purchaseListQuery
     * @return
     */
    @RequestMapping("countPurchase")
    @ResponseBody
    public Map<String,Object> countPurchase(PurchaseListQuery purchaseListQuery){
        return purchaseListService.countPurchase(purchaseListQuery);
    }
}
