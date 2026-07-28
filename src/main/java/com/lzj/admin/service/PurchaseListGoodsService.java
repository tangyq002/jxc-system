package com.lzj.admin.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.pojo.PurchaseListGoods;
import com.lzj.admin.query.PurchaseListGoodsQuery;

/**
 * 进货单商品表服务类
 * @author TianTian
 * @date 2022/1/19 13:58
 */
public interface PurchaseListGoodsService extends IService<PurchaseListGoods> {
	/**
	 * 查询进货单商品
	 * @param purchaseListGoodsQuery
	 * @return
	 */
	Map<String, Object> purchaseListGoodsList(PurchaseListGoodsQuery purchaseListGoodsQuery);
}
