package com.lzj.admin.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.query.GoodsQuery;

/**
 * 商品表服务
 * @author TianTian
 * @date 2022/1/19 13:55
 */
public interface GoodsService extends IService<Goods> {
	Map<String, Object> goodsList(GoodsQuery goodsQuery);

    void saveGoods(Goods goods);

    void updateGoods(Goods goods);

    void deleteGoods(Integer id);

    Goods findGoodsByName(String name);
    
    Goods queryGoodsById(Integer id);

	Map<String, Object> stockList(GoodsQuery goodsQuery);
}
