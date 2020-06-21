package com.platform.controller;

import com.platform.entity.OrderEntity;
import com.platform.entity.OrderGoodsEntity;
import com.platform.service.OrderGoodsService;
import com.platform.service.OrderService;
import com.platform.utils.*;
import com.platform.vo.OrderVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-13 10:41:09
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderGoodsService orderGoodsService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("order:list")
    public R list(@RequestParam Map<String, Object> params) {
        Long userId = ShiroUtils.getUserId();
        if (userId> Constant.SUPER_ADMIN_MAX){
            params.put("sellerId",userId);
        }
        // 查询列表数据
        Query query = new Query(params);

        List<OrderEntity> orderList = orderService.queryList(query);
        List<OrderVo> orderVoList = new ArrayList<>();
        if (orderList!=null && orderList.size()>0){
            Map goodsParam = new HashMap();
            for (int i = 0; i < orderList.size(); i++) {
                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(orderList.get(i),orderVo);
                //查找此订单的所有商品和封面图，转成string
                goodsParam.put("orderId",orderVo.getId());
                List<OrderGoodsEntity> goodsList = orderGoodsService.queryList(goodsParam);
                List<String> picList = new ArrayList<>();
                if (goodsList!=null && goodsList.size()>0){
                    StringBuffer goodsNameStr = new StringBuffer();
                    for (int j = 0; j < goodsList.size(); j++) {
                        OrderGoodsEntity orderGoodsEntity = goodsList.get(j);
                        picList.add(orderGoodsEntity.getListPicUrl());
                        if (j==goodsList.size()-1){
                            goodsNameStr.append(orderGoodsEntity.getGoodsName());
                        }else {
                            goodsNameStr.append(orderGoodsEntity.getGoodsName()).append(",");
                        }
                    }
                    orderVo.setPicList(picList);
                    orderVo.setPicCount(goodsList.size());
                    orderVo.setGoodsArr(goodsNameStr.toString());
                    orderVoList.add(orderVo);
                }
            }
        }
        int total = orderService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(orderVoList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("heheh");
        list.add("qqq");
        list.toString();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("order:info")
    public R info(@PathVariable("id") Integer id) {
        OrderEntity order = orderService.queryObject(id);
        order.getOrderType();
        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("order:save")
    public R save(@RequestBody OrderEntity order) {
        order.setSellerId(ShiroUtils.getUserId());
        orderService.save(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("order:update")
    public R update(@RequestBody OrderEntity order) {
        orderService.update(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("order:delete")
    public R delete(@RequestBody Integer[] ids) {
        orderService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {
        Long userId = ShiroUtils.getUserId();
        if (userId> Constant.SUPER_ADMIN_MAX){
            params.put("sellerId",userId);
        }
        List<OrderEntity> list = orderService.queryList(params);

        return R.ok().put("list", list);
    }

    /**
     * 总计
     */
    @RequestMapping("/queryTotal")
    public R queryTotal(@RequestParam Map<String, Object> params) {
        Long userId = ShiroUtils.getUserId();
        if (userId> Constant.SUPER_ADMIN_MAX){
            params.put("sellerId",userId);
        }
        int sum = orderService.queryTotal(params);

        return R.ok().put("sum", sum);
    }

    /**
     * 确定收货
     *
     * @param id
     * @return
     */
    @RequestMapping("/confirm")
    @RequiresPermissions("order:confirm")
    public R confirm(@RequestBody Integer id) {
        orderService.confirm(id);

        return R.ok();
    }

    /**
     * 发货
     *
     * @param order
     * @return
     */
    @RequestMapping("/sendGoods")
    @RequiresPermissions("order:sendGoods")
    public R sendGoods(@RequestBody OrderEntity order) {
        orderService.sendGoods(order);

        return R.ok();
    }
}
