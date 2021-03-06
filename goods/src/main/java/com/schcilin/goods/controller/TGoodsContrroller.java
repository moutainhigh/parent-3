package com.schcilin.goods.controller;


import com.schcilin.goods.entity.TGoods;
import com.schcilin.goods.feign.BaseInfoFeignClient;
import com.schcilin.goods.service.TGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author schcilin
 * @since 2019-06-04
 */
//@RefreshScope
@RestController
@RequestMapping("/tGoods")
public class TGoodsContrroller {
    @Value("${app.name}")
    private String name;
    @Autowired
    private TGoodsService tGoodsService;

    @Autowired
    private BaseInfoFeignClient baseInfoFeignClient;

    @PostMapping("/add")
    public void add() {
        TGoods tGoods = new TGoods();
        tGoods.setId(String.valueOf(Math.random()));
        tGoods.setGoodName("哈哈哈");
        tGoodsService.insertModel(tGoods, "1");
    }

    @PostMapping("/sss")
    public String sss() {

        return name;
        //baseInfoFeignClient.addUser(null);
    }

}

