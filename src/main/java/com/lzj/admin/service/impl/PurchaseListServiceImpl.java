package com.lzj.admin.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.mapper.PurchaseListMapper;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.pojo.PurchaseList;
import com.lzj.admin.pojo.PurchaseListGoods;
import com.lzj.admin.query.PurchaseListQuery;
import com.lzj.admin.service.GoodsService;
import com.lzj.admin.service.PurchaseListGoodsService;
import com.lzj.admin.service.PurchaseListService;
import com.lzj.admin.utils.AssertUtil;
import com.lzj.admin.utils.DateUtil;
import com.lzj.admin.utils.PageResultUtil;
import com.lzj.admin.utils.StringUtil;

/**
 * <p>
 * 进货单 服务实现类
 * </p>
 *
 * @author 老李
 */
@Service
public class PurchaseListServiceImpl extends ServiceImpl<PurchaseListMapper, PurchaseList> implements PurchaseListService {
    @Resource
    private PurchaseListGoodsService purchaseListGoodsService;

    @Resource
    private GoodsService goodsService;
    
    /**
     * 生成进货单号
     */
	@Override
	public String getNextPurchaseNumber() {
        try {
            StringBuffer stringBuffer =new StringBuffer();
            stringBuffer.append("JH");
            stringBuffer.append(DateUtil.getCurrentDateStr());
            String purchaseNumber = this.baseMapper.getNextPurchaseNumber();
            if(null !=purchaseNumber){
                stringBuffer.append(StringUtil.formatCode(purchaseNumber));
            }else{
                stringBuffer.append("0001");
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
	}

	/**
	 * 添加进货记录
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void savePurchaseList(PurchaseList purchaseList, List<PurchaseListGoods> plgList) {
		AssertUtil.isTrue(purchaseList.getSupplierId()==0,"供应商为空");
        AssertUtil.isTrue(purchaseList.getAmountPayable()==null,"应付金额不能为空");
        AssertUtil.isTrue(purchaseList.getAmountPaid()==null,"实付金额不能为空");
        AssertUtil.isTrue(purchaseList.getPurchaseDate()==null,"请选择日期");
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(System.currentTimeMillis());
        formatter.format(purchaseList.getPurchaseDate()).compareTo(formatter.format(date1));
        AssertUtil.isTrue(formatter.format(purchaseList.getPurchaseDate()).compareTo(formatter.format(date1))!=0,"请选择本日时间");
        AssertUtil.isTrue(!(this.save(purchaseList)),"记录添加失败!");
        PurchaseList  temp = this.getOne(new QueryWrapper<PurchaseList>().eq("purchase_number",purchaseList.getPurchaseNumber()));
        AssertUtil.isTrue(plgList==null,"请选择商品");
        plgList.forEach(plg->{
        	plg.setPurchaseListId(temp.getId());
            Goods goods =goodsService.getById(plg.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity()-plg.getNum());
            goods.setState(2);
            goodsService.updateById(goods);

        });
        AssertUtil.isTrue(!(purchaseListGoodsService.saveBatch(plgList)),"记录添加失败!");
	}

	/**
	 * 进货列表
	 */
	@Override
	public Map<String, Object> purchaseList(PurchaseListQuery purchaseListQuery) {
		IPage<PurchaseList> page = new Page<PurchaseList>(purchaseListQuery.getPage(),purchaseListQuery.getLimit());
        page =  this.baseMapper.purchaseList(page,purchaseListQuery);
        return PageResultUtil.setResult(page.getTotal(),page.getRecords());
	}

	/**
	 * 删除记录，删除进货单和进货单明细
	 */
	@Override
	public void deletePurchaseList(Integer id) {
		AssertUtil.isTrue(!(purchaseListGoodsService.remove(new QueryWrapper<PurchaseListGoods>().eq("purchase_list_id",id))),"记录删除失败!");
        AssertUtil.isTrue(!(this.removeById(id)),"记录删除失败!");
	}

	/**
	 * 查询商品采购统计列表，分页
	 */
	@Override
	public Map<String, Object> countPurchase(PurchaseListQuery purchaseListQuery) {
		IPage<Map<String,Object>> page = new Page<Map<String,Object>>(purchaseListQuery.getPage(),purchaseListQuery.getLimit());
        page =  this.baseMapper.countPurchase(page,purchaseListQuery);
        return PageResultUtil.setResult(page.getTotal(),page.getRecords());
	}

}
