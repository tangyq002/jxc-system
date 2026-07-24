package com.lzj.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.mapper.SupplierMapper;
import com.lzj.admin.pojo.Supplier;
import com.lzj.admin.query.SupplierQuery;
import com.lzj.admin.service.SupplierService;
import com.lzj.admin.utils.AssertUtil;
import com.lzj.admin.utils.PageResultUtil;

/**
 * 供应商服务类
 * @author TianTian
 * @date 2022/1/19 14:43
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

	/**
	 * 分页展示供应商列表
	 */
	@Override
	public Map<String, Object> supplierList(SupplierQuery supplierQuery) {
		//创建分页对象
		IPage<Supplier> page =new Page<Supplier>(supplierQuery.getPage(),supplierQuery.getLimit());
        QueryWrapper<Supplier> queryWrapper =new QueryWrapper<Supplier>();
        //拼接条件，查询没删除的数据
        queryWrapper.eq("is_del",0);
        //是否输入查询内容，进行模糊查询
        if(StringUtils.isNotBlank(supplierQuery.getSupplierName())){
            queryWrapper.like("name",supplierQuery.getSupplierName());
        }
        //分页查询
        page =  this.baseMapper.selectPage(page,queryWrapper);
        return PageResultUtil.setResult(page.getTotal(),page.getRecords());
	}

	/**
	 * 添加供应商
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void saveSupplier(Supplier supplier) {
		checkParams(supplier.getName(),supplier.getContact(),supplier.getNumber());
		//查询名字是否重复
        AssertUtil.isTrue(null !=this.findSupplierByName(supplier.getName()),"供应商已存在!");
        supplier.setIsDel(0);
        AssertUtil.isTrue(!(this.save(supplier)),"记录添加失败!");
	}

	/**
	 * 参数非空校验
	 * @param name
	 * @param contact
	 * @param number
	 */
	private void checkParams(String name, String contact, String number) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"请输入供应商名称!");
        AssertUtil.isTrue(StringUtils.isBlank(contact),"请输入联系人!");
        AssertUtil.isTrue(StringUtils.isBlank(number),"请输入联系电话!");
    }
	
	/**
	 * 更新供应商
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void updateSupplier(Supplier supplier) {
		AssertUtil.isTrue(null == this.getById(supplier.getId()),"请选择供应商记录!");
        checkParams(supplier.getName(),supplier.getContact(),supplier.getNumber());
        //查询是否存在相同的
        Supplier temp = this.findSupplierByName(supplier.getName());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(supplier.getId())),"供应商已存在!");
        AssertUtil.isTrue(!(this.updateById(supplier)),"记录更新失败!");
	}

	@Override
	public void deleteSupplier(Integer[] ids) {
		AssertUtil.isTrue(null == ids || ids.length==0,"请选择待删除记录id");
        List<Supplier> supplierList =new ArrayList<Supplier>();
        for (Integer id : ids) {
        	Supplier temp =this.getById(id);
            temp.setIsDel(1);
            supplierList.add(temp);
        }
        AssertUtil.isTrue(!(this.updateBatchById(supplierList)),"记录删除失败!");
	}

	@Override
	public Supplier findSupplierByName(String name) {
		return this.getOne(new QueryWrapper<Supplier>().eq("is_del",0).eq("name",name));
	}

}
