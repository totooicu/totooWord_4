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
import com.totoo.system.domain.FunMeaning;
import com.totoo.system.service.IFunMeaningService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 单词含义Controller
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@RestController
@RequestMapping("/system/meaning")
public class FunMeaningController extends BaseController
{
    @Autowired
    private IFunMeaningService funMeaningService;

    /**
     * 查询单词含义列表
     */
    @PreAuthorize("@ss.hasPermi('system:meaning:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunMeaning funMeaning)
    {
        startPage();
        List<FunMeaning> list = funMeaningService.selectFunMeaningList(funMeaning);
        return getDataTable(list);
    }

    /**
     * 导出单词含义列表
     */
    @PreAuthorize("@ss.hasPermi('system:meaning:export')")
    @Log(title = "单词含义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunMeaning funMeaning)
    {
        List<FunMeaning> list = funMeaningService.selectFunMeaningList(funMeaning);
        ExcelUtil<FunMeaning> util = new ExcelUtil<FunMeaning>(FunMeaning.class);
        util.exportExcel(response, list, "单词含义数据");
    }

    /**
     * 获取单词含义详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:meaning:query')")
    @GetMapping(value = "/{wordId}")
    public AjaxResult getInfo(@PathVariable("wordId") String wordId)
    {
        return success(funMeaningService.selectFunMeaningByWordId(wordId));
    }

    /**
     * 新增单词含义
     */
    @PreAuthorize("@ss.hasPermi('system:meaning:add')")
    @Log(title = "单词含义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunMeaning funMeaning)
    {
        return toAjax(funMeaningService.insertFunMeaning(funMeaning));
    }

    /**
     * 修改单词含义
     */
    @PreAuthorize("@ss.hasPermi('system:meaning:edit')")
    @Log(title = "单词含义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunMeaning funMeaning)
    {
        return toAjax(funMeaningService.updateFunMeaning(funMeaning));
    }

    /**
     * 删除单词含义
     */
    @PreAuthorize("@ss.hasPermi('system:meaning:remove')")
    @Log(title = "单词含义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wordIds}")
    public AjaxResult remove(@PathVariable String[] wordIds)
    {
        return toAjax(funMeaningService.deleteFunMeaningByWordIds(wordIds));
    }
}
