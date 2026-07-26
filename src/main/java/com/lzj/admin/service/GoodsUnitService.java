package com.lzj.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.pojo.GoodsUnit;
/**
 * 商品单元表单服务类
 * @author TianTian
 * @date 2022/1/19 13:57
 */
public interface GoodsUnitService extends IService<GoodsUnit> {
	List<GoodsUnit> allGoodsUnits();
}
