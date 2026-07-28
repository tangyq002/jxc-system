package com.lzj.admin.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.pojo.PurchaseList;
import com.lzj.admin.pojo.PurchaseListGoods;
import com.lzj.admin.query.PurchaseListQuery;

/**
 * 进货单服务类
 * @author TianTian
 * @date 2022/1/19 13:58
 */
public interface PurchaseListService extends IService<PurchaseList> {
	String getNextPurchaseNumber();

    void savePurchaseList(PurchaseList purchaseList, List<PurchaseListGoods> plgList);

    Map<String, Object> purchaseList(PurchaseListQuery purchaseListQuery);

    void deletePurchaseList(Integer id);

	Map<String, Object> countPurchase(PurchaseListQuery purchaseListQuery);
}
