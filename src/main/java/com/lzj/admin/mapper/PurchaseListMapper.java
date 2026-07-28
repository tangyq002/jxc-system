package com.lzj.admin.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lzj.admin.pojo.PurchaseList;
import com.lzj.admin.query.PurchaseListQuery;

/**
 * 进货单接口
 * @author TianTian
 * @date 2022/1/21 18:27
 */
@Mapper
public interface PurchaseListMapper extends BaseMapper<PurchaseList> {
	String  getNextPurchaseNumber();

    IPage<PurchaseList>  purchaseList(IPage<PurchaseList> page, @Param("purchaseListQuery") PurchaseListQuery purchaseListQuery);
   
    IPage<Map<String,Object>>  countPurchase(IPage<Map<String,Object>> page, @Param("purchaseListQuery") PurchaseListQuery purchaseListQuery);
}
