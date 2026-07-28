package com.lzj.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.mapper.MenuMapper;
import com.lzj.admin.pojo.Menu;
import com.lzj.admin.pojo.RoleMenu;
import com.lzj.admin.service.MenuService;
import com.lzj.admin.service.RoleMenuService;
import com.lzj.admin.utils.AssertUtil;
import com.lzj.admin.utils.PageResultUtil;
import com.lzj.admin.utils.StringUtil;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    private RoleMenuService roleMenuService;
    /**
     * 菜单管理列表
     */
	@Override
	public Map<String, Object> menuList() {
	    QueryWrapper<Menu> wrapper=new QueryWrapper<>();
	    wrapper.eq("is_del",0).orderByAsc("id");
	    List<Menu> menus=this.list(wrapper);
	    return PageResultUtil.setResult((long)menus.size(),menus);
	}

	/**
	 * 所有菜单
	 */
	@Override
	public List<TreeDto> queryAllMenu(Integer roleId) {
        List<TreeDto> list = this.baseMapper.queryAllMenu(roleId);
        return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void saveMenu(Menu menu) {
        AssertUtil.isTrue(StringUtil.isEmpty(menu.getName()),"菜单名称不能为空");
        AssertUtil.isTrue(menu.getGrade()==null,"菜单层级不能为空");
        //最多三级菜单
        AssertUtil.isTrue(menu.getGrade()>3,"菜单最多三级");
        menu.setIsDel(0);
        AssertUtil.isTrue(!this.save(menu),"菜单添加失败");
	}

	/**
	 * 删除，有子目录存在增删改查权限不能删除
	 */
	@Override
	public void deleteMenu(Integer id) {
		AssertUtil.isTrue(id==null, "菜单id不能为空");
		//判断有没有子菜单
        Integer count = this.count(new QueryWrapper<Menu>().eq("p_id",id).eq("is_del",0));
        AssertUtil.isTrue(count>0,"该目录存在子菜单，不能删除");
        //判断是否分配给角色
        Integer roleCount = roleMenuService.count(new QueryWrapper<RoleMenu>().eq("menu_id",id));
        AssertUtil.isTrue(roleCount>0,"该菜单已经分配权限，不能删除");
        Menu menu = this.getById(id);
        menu.setIsDel(1);
        AssertUtil.isTrue(!this.updateById(menu),"删除失败");
	}

	/**
	 * 修改菜单
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void updateMenu(Menu menu) {
	    AssertUtil.isTrue(menu.getId()==null || this.getById(menu.getId())==null,"待修改菜单不存在");
        //检查父菜单
        Integer grade = 1;
        if(menu.getpId()!=null && menu.getpId()!=0){
            Menu parent=this.getById(menu.getpId());
            AssertUtil.isTrue(parent==null,"父菜单不存在");
            grade=parent.getGrade()+1;
        }
        //菜单最多三级
        AssertUtil.isTrue(grade>3,"菜单最多三级");
        menu.setGrade(grade);
        //修改自己
        AssertUtil.isTrue(!this.updateById(menu),"修改失败");
        //同步修改子菜单
        updateChildGrade(menu.getId(),grade);
	}

	/**
	 * 修改子菜单
	 * @param id
	 * @param grade
	 */
	private void updateChildGrade(Integer pid, Integer parentGrade) {
		List<Menu> children =this.list(new QueryWrapper<Menu>().eq("p_id",pid));
	    for(Menu child:children){
	        int grade = parentGrade+1;
	        //超过三级
	        AssertUtil.isTrue(grade>3,"修改后菜单超过三级");
	        child.setGrade(grade);
	        this.updateById(child);
	        //继续处理下级菜单
	        updateChildGrade(child.getId(),grade);
	    }
	}

}

