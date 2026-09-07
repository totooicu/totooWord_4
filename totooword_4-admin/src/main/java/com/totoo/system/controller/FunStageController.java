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
import com.totoo.system.domain.FunStage;
import com.totoo.system.service.IFunStageService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 单词阶段要求Controller
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@RestController
@RequestMapping("/system/stage")
public class FunStageController extends BaseController
{
    @Autowired
    private IFunStageService funStageService;

    /**
     * 查询单词阶段要求列表
     */
    @PreAuthorize("@ss.hasPermi('system:stage:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunStage funStage)
    {
        startPage();
        List<FunStage> list = funStageService.selectFunStageList(funStage);
        return getDataTable(list);
    }

    /**
     * 导出单词阶段要求列表
     */
    @PreAuthorize("@ss.hasPermi('system:stage:export')")
    @Log(title = "单词阶段要求", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunStage funStage)
    {
        List<FunStage> list = funStageService.selectFunStageList(funStage);
        ExcelUtil<FunStage> util = new ExcelUtil<FunStage>(FunStage.class);
        util.exportExcel(response, list, "单词阶段要求数据");
    }

    /**
     * 获取单词阶段要求详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:stage:query')")
    @GetMapping(value = "/{wordId}")
    public AjaxResult getInfo(@PathVariable("wordId") String wordId)
    {
        return success(funStageService.selectFunStageByWordId(wordId));
    }

    /**
     * 新增单词阶段要求
     */
    @PreAuthorize("@ss.hasPermi('system:stage:add')")
    @Log(title = "单词阶段要求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunStage funStage)
    {
        return toAjax(funStageService.insertFunStage(funStage));
    }

    /**
     * 修改单词阶段要求
     */
    @PreAuthorize("@ss.hasPermi('system:stage:edit')")
    @Log(title = "单词阶段要求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunStage funStage)
    {
        return toAjax(funStageService.updateFunStage(funStage));
    }

    /**
     * 删除单词阶段要求
     */
    @PreAuthorize("@ss.hasPermi('system:stage:remove')")
    @Log(title = "单词阶段要求", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wordIds}")
    public AjaxResult remove(@PathVariable String[] wordIds)
    {
        return toAjax(funStageService.deleteFunStageByWordIds(wordIds));
    }
}
