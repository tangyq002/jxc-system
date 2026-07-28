package com.lzj.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.query.GoodsQuery;

public interface GoodsMapper extends BaseMapper<Goods> {

	/**
	 * 联表查询
	 * @param page
	 * @param goodsQuery
	 * @return
	 */
	IPage<Goods> queryGoodsList(IPage<Goods> page, GoodsQuery goodsQuery);

	/**
	 * 根据id查询商品
	 * @param id
	 * @return
	 */
	Goods queryGoodsById(Integer id);

	IPage<Goods> stockList(IPage<Goods> page, GoodsQuery goodsQuery);

}
