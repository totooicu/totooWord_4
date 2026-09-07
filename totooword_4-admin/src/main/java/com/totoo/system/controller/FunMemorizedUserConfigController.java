package com.totoo.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.totoo.common.annotation.Log;
import com.totoo.common.core.controller.BaseController;
import com.totoo.common.core.domain.AjaxResult;
import com.totoo.common.enums.BusinessType;
import com.totoo.system.domain.FunMemorizedUserConfig;
import com.totoo.system.service.IFunMemorizedUserConfigService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 用户学习配置Controller
 * 
 * @author ruoyi
 * @date 2025-02-02
 */
@RestController
@RequestMapping("/system/memorizedUserConfig")
public class FunMemorizedUserConfigController extends BaseController
{
    @Autowired
    private IFunMemorizedUserConfigService funMemorizedUserConfigService;

    /**
     * 查询用户学习配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunMemorizedUserConfig funMemorizedUserConfig)
    {
        startPage();
        List<FunMemorizedUserConfig> list = funMemorizedUserConfigService.selectFunMemorizedUserConfigList(funMemorizedUserConfig);
        return getDataTable(list);
    }

    /**
     * 导出用户学习配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:config:export')")
    @Log(title = "用户学习配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunMemorizedUserConfig funMemorizedUserConfig)
    {
        List<FunMemorizedUserConfig> list = funMemorizedUserConfigService.selectFunMemorizedUserConfigList(funMemorizedUserConfig);
        ExcelUtil<FunMemorizedUserConfig> util = new ExcelUtil<FunMemorizedUserConfig>(FunMemorizedUserConfig.class);
        util.exportExcel(response, list, "用户学习配置数据");
    }

    /**
     * 获取用户学习配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:config:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(funMemorizedUserConfigService.selectFunMemorizedUserConfigByUserId(userId));
    }
    /**
     * 获取自己学习配置详细信息
     */
    @GetMapping( "/getBySelf")
    public AjaxResult getBySelf()
    {
        FunMemorizedUserConfig funMemorizedUserConfig = funMemorizedUserConfigService.selectFunMemorizedUserConfigByUserId( getUserId());
        if(funMemorizedUserConfig == null){
            funMemorizedUserConfig = new FunMemorizedUserConfig();
            funMemorizedUserConfig.setUserId(getUserId());
            funMemorizedUserConfig.setDailyLearningGoal(10);
            funMemorizedUserConfigService.insertFunMemorizedUserConfig(funMemorizedUserConfig);
        }
        System.out.println(">>>funMemorizedUserConfig"+funMemorizedUserConfig);
        return AjaxResult.success(funMemorizedUserConfig);
    }
    /**
     * 新增用户学习配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:add')")
    @Log(title = "用户学习配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunMemorizedUserConfig funMemorizedUserConfig)
    {
        return toAjax(funMemorizedUserConfigService.insertFunMemorizedUserConfig(funMemorizedUserConfig));
    }

    /**
     * 修改用户学习配置
     */
    @PutMapping
    public AjaxResult edit(@RequestBody FunMemorizedUserConfig funMemorizedUserConfig)
    {
        funMemorizedUserConfig.setUserId(getUserId());
        funMemorizedUserConfig.setTotalCheckinDays(null);
        return toAjax(funMemorizedUserConfigService.updateFunMemorizedUserConfig(funMemorizedUserConfig));
    }

    /**
     * 删除用户学习配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:remove')")
    @Log(title = "用户学习配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(funMemorizedUserConfigService.deleteFunMemorizedUserConfigByUserIds(userIds));
    }
}
