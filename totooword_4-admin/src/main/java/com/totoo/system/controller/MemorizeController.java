package com.totoo.system.controller;

import com.totoo.common.core.controller.BaseController;
import com.totoo.common.core.domain.AjaxResult;
import com.totoo.system.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/memorize")
public class MemorizeController extends BaseController {
    @Autowired
    private RedisService redisService;

    @GetMapping("/getRunningDayBySelf")
    public AjaxResult getRunningDayBySelf() {
        return AjaxResult.success(redisService.getContinuousDays(String.valueOf(getLoginUser().getUserId())));
    }
}
