package com.lzj.admin.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.mapper.GoodsMapper;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.query.GoodsQuery;
import com.lzj.admin.service.GoodsService;
import com.lzj.admin.utils.AssertUtil;
import com.lzj.admin.utils.PageResultUtil;

/**
 * 商品表实现类
 * @author TianTian
 * @date 2022/1/19 14:51
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	
	/**
	 * 分页查询商品列表
	 */
	@Override
	public Map<String, Object> goodsList(GoodsQuery goodsQuery) {
		//创建分页对象
		IPage<Goods> page =new Page<Goods>(goodsQuery.getPage(),goodsQuery.getLimit());
        QueryWrapper<Goods> queryWrapper =new QueryWrapper<Goods>();
        //拼接条件，查询没删除的数据
        queryWrapper.eq("is_del",0);
        //是否输入查询内容，进行模糊查询
        if(StringUtils.isNotBlank(goodsQuery.getGoodsName())){
            queryWrapper.like("name",goodsQuery.getGoodsName());
        }
        //联表查询
        page = goodsMapper.queryGoodsList(page,goodsQuery);
        return PageResultUtil.setResult(page.getTotal(),page.getRecords());
	}

	/**
	 * 添加商品
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void saveGoods(Goods goods) {
		checkParams(goods.getName(),goods.getModel(),goods.getTypeId(),goods.getUnit(),
				goods.getPurchasingPrice(),goods.getSellingPrice(),goods.getMinNum());
		//商品是否存在
	    AssertUtil.isTrue(null != this.findGoodsByName(goods.getName()),"商品已存在!");
	    //设置库存，状态，采购价，是否删除为否
	    goods.setInventoryQuantity(0);
	    goods.setState(0);
	    goods.setLastPurchasingPrice(goods.getPurchasingPrice());
	    goods.setIsDel(0);
	    AssertUtil.isTrue(!this.save(goods),"商品添加失败!");
	}

	/**
	 * 参数校验
	 * @param name
	 * @param model
	 * @param typeId
	 * @param unit
	 * @param purchasingPrice
	 * @param sellingPrice
	 * @param minNum
	 */
	private void checkParams(String name, String model, Integer typeId, String unit, Float purchasingPrice,
			Float sellingPrice, Integer minNum) {
	    AssertUtil.isTrue(StringUtils.isBlank(name),"请输入商品名称!");
	    AssertUtil.isTrue(StringUtils.isBlank(model),"请输入商品型号!");
	    AssertUtil.isTrue(null == typeId,"请选择商品类别!");
	    AssertUtil.isTrue(StringUtils.isBlank(unit),"请选择商品单位!");
	    AssertUtil.isTrue(null == purchasingPrice,"请输入采购价格!");
	    AssertUtil.isTrue(null == sellingPrice,"请输入销售价格!");
	    AssertUtil.isTrue(null == minNum,"请输入库存下限!");
	}

	/**
	 * 修改商品
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void updateGoods(Goods goods) {
		AssertUtil.isTrue(null == this.getById(goods.getId()),"请选择商品记录!");
        checkParams(goods.getName(), goods.getModel(), goods.getTypeId(), goods.getUnit(), goods.getPurchasingPrice(), goods.getSellingPrice(), goods.getMinNum());
        //查询是否存在相同的
        Goods temp = this.findGoodsByName(goods.getName());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(goods.getId())),"商品已存在!");
        AssertUtil.isTrue(!(this.updateById(goods)),"记录更新失败!");
	}

	/**
	 * 删除单个
	 */
	@Override
	public void deleteGoods(Integer id) {
		AssertUtil.isTrue(null == id,"请选择待删除记录id");
		Goods goods = new Goods();
	    goods.setId(id);
	    goods.setIsDel(1);
        AssertUtil.isTrue(!(this.updateById(goods)),"记录删除失败!");
	}

	/**
	 * 查询全部
	 */
	@Override
	public Goods findGoodsByName(String name) {
		return this.getOne(new QueryWrapper<Goods>().eq("is_del",0).eq("name",name));
	}

	/**
	 * 根据id查询商品，联表查
	 */
	@Override
	public Goods queryGoodsById(Integer id) {
		return goodsMapper.queryGoodsById(id);
	}


}
