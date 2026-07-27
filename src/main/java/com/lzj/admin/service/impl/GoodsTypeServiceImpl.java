package com.lzj.admin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.mapper.GoodsTypeMapper;
import com.lzj.admin.pojo.GoodsType;
import com.lzj.admin.service.GoodsTypeService;
import com.lzj.admin.utils.AssertUtil;

/**
 * 商品表类型实现类
 * @author TianTian
 * @date 2022/1/19 14:51
 */
@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {

	@Autowired
	GoodsTypeMapper goodsTypeMapper;
	/**
	 * 查询商品分类列表
	 */
	@Override
	public List<GoodsType> queryAllGoodsTypes() {
		return this.list();
	}

	/**
	 * 添加商品子类
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void saveGoodsType(GoodsType goodsType) {
		//不为空
	    AssertUtil.isTrue(StringUtils.isBlank(goodsType.getName()),"请输入商品类别名称!");
		//子类名是否已存在
	    AssertUtil.isTrue(null != this.findGoodsTypeByName(goodsType.getName()),"商品已存在!");
	    //设置state
	    goodsType.setState(0);
	    AssertUtil.isTrue(!this.save(goodsType),"商品添加失败!");
	}

	/**
	 * 删除当前类别
	 */
	@Override
	public void deleteGoodsType(Integer id) {
		AssertUtil.isTrue(null == id,"请选择待删除记录id");
		//查询当前类别是否下面还有子类 即是否有pid=当前id
		QueryWrapper<GoodsType> wrapper = new QueryWrapper<>();
		wrapper.eq("p_id", id);
		//有几条子类
		Integer count = goodsTypeMapper.selectCount(wrapper);
		AssertUtil.isTrue(count > 0,"该类别下存在子类别，不能删除!");
        AssertUtil.isTrue(!this.removeById(id),"记录删除失败!");
	}

	/**
	 * 根据分类名查询
	 */
	@Override
	public GoodsType findGoodsTypeByName(String name) {
	    QueryWrapper<GoodsType> wrapper = new QueryWrapper<>();
	    wrapper.eq("name", name);
	    return goodsTypeMapper.selectOne(wrapper);
	}

}
