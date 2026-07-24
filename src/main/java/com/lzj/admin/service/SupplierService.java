package com.lzj.admin.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.pojo.Supplier;
import com.lzj.admin.query.SupplierQuery;

/**
 * 供应商服务类
 * @author TianTian
 * @date 2022/1/19 13:59
 */
public interface SupplierService extends IService<Supplier> {
	Map<String, Object> supplierList(SupplierQuery supplierQuery);

    void saveSupplier(Supplier supplier);

    void updateSupplier(Supplier supplier);

    void deleteSupplier(Integer[] ids);

    Supplier findSupplierByName(String name);
}
