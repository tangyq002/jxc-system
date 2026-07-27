package com.lzj.admin.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzj.admin.mapper.GoodsTypeMapper;
import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.pojo.GoodsType;
import com.lzj.admin.query.GoodsQuery;
import com.lzj.admin.service.GoodsService;

/**
 * 商品控制器
 * @author TianTian
 * @date 2022/1/18 22:50
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
	@Resource
    private GoodsService goodsService;
	@Resource
	private GoodsTypeMapper goodsTypeMapper;
    /**
     *  商品管理主页
     * @return
     */
    @RequestMapping("index" )
    public String index(){
        return "/goods/goods";
    }

    /**
     * 分页查询商品列表
     * @param goodsQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> goodsList(GoodsQuery goodsQuery){
        return goodsService.goodsList(goodsQuery);
    }

    /**
     * 进入编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateGoodsPage")
    public String addRolePage(Integer id, Model model){
        if(null !=id){
        	Goods goods = goodsService.queryGoodsById(id);
        	model.addAttribute("goods",goodsService.queryGoodsById(id));
        	GoodsType goodsType = goodsTypeMapper.selectById(goods.getTypeId());
            model.addAttribute("goodsType", goodsType);
        }
        return "goods/add_update";
    }

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public RespBean saveGoods(Goods goods){
    	goodsService.saveGoods(goods);
        return RespBean.success("记录添加成功");
    }

    /**
     * 更新商品
     * @param goods
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public RespBean updateGoods(Goods goods){
    	goodsService.updateGoods(goods);
        return RespBean.success("记录更新成功");
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public RespBean deleteGoods(Integer id){
    	goodsService.deleteGoods(id);
        return RespBean.success("商品记录删除成功");
    }
    
    /**
     * 查询全部商品
     * @return
     */
    @RequestMapping("allGoods")
    @ResponseBody
    public List<Goods> allGoods(){
        return goodsService.list(new QueryWrapper<Goods>().eq("is_del",0));
    }
    
    /**
     * 打开选择商品类别窗口
     * @param typeId
     * @param model
     * @return
     */
    @RequestMapping("/toGoodsTypePage")
    public String toGoodsTypePage(Integer typeId, Model model){
        model.addAttribute("typeId",typeId);
        return "goods/goods_type";

    }
}
