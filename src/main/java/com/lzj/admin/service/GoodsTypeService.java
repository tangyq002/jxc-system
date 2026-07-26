package com.lzj.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.pojo.GoodsType;

/**
 * 商品类别表单服务类
 * @author TianTian
 * @date 2022/1/19 13:56
 */
public interface GoodsTypeService extends IService<GoodsType> {
	List<GoodsType> queryAllGoodsTypes();
}
