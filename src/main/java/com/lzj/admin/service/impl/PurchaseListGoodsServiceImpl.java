package com.lzj.admin.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.mapper.PurchaseListGoodsMapper;
import com.lzj.admin.pojo.PurchaseListGoods;
import com.lzj.admin.query.PurchaseListGoodsQuery;
import com.lzj.admin.service.PurchaseListGoodsService;
import com.lzj.admin.utils.PageResultUtil;

/**
 * <p>
 * 进货单商品表 服务实现类
 * </p>
 *
 * @author 老李
 */
@Service
public class PurchaseListGoodsServiceImpl extends ServiceImpl<PurchaseListGoodsMapper, PurchaseListGoods> implements PurchaseListGoodsService {

	/**
	 * 分页查询进货单明细
	 */
	@Override
	public Map<String, Object> purchaseListGoodsList(PurchaseListGoodsQuery purchaseListGoodsQuery) {
		IPage<PurchaseListGoods> page = new Page<PurchaseListGoods>(purchaseListGoodsQuery.getPage(),purchaseListGoodsQuery.getLimit());
        QueryWrapper<PurchaseListGoods> queryWrapper =new QueryWrapper<PurchaseListGoods>();
        if(null != purchaseListGoodsQuery.getPurchaseListId()){
            queryWrapper.eq("purchase_list_id",purchaseListGoodsQuery.getPurchaseListId());
        }
        page =  this.baseMapper.selectPage(page,queryWrapper);
        return PageResultUtil.setResult(page.getTotal(),page.getRecords());
	}

}
