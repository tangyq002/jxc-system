package com.lzj.admin.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.mapper.GoodsUnitMapper;
import com.lzj.admin.pojo.GoodsUnit;
import com.lzj.admin.service.GoodsUnitService;

@Service
public class GoodsUnitServiceImpl extends ServiceImpl<GoodsUnitMapper, GoodsUnit> implements GoodsUnitService {

	/**
	 * 查询商品单位列表
	 */
	@Override
	public List<GoodsUnit> allGoodsUnits() {
		return this.list();
	}
}

