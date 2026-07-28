package com.lzj.admin.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.pojo.Menu;
/**
 * 菜单表服务类
 * @author TianTian
 * @date 2022/1/19 13:57
 */
public interface MenuService extends IService<Menu> {

	Map<String, Object> menuList();

	List<TreeDto> queryAllMenu(Integer roleId);

	void saveMenu(Menu menu);

	void deleteMenu(Integer id);

	void updateMenu(Menu menu);

}
