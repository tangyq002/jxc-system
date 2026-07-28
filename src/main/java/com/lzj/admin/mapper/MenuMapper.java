package com.lzj.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.pojo.Menu;
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

	List<TreeDto> queryAllMenu(Integer roleId);

}
