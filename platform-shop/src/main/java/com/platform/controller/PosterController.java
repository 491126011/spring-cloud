package com.platform.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.entity.SellerEntity;
import com.platform.entity.SysUserEntity;
import com.platform.service.SellerService;
import com.platform.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("poster")
public class PosterController extends AbstractController {

    @Autowired
    private SellerService sellerService;

    @PostMapping
    public R genPoster() {

        SysUserEntity user = getUser();
        Long userId = user.getUserId();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("sys_id", userId);
        SellerEntity seller = sellerService.getOne(wrapper);
        if (seller != null) {

        }

        return null;
    }

}
