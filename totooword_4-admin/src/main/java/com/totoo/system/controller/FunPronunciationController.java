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
import com.totoo.system.domain.FunPronunciation;
import com.totoo.system.service.IFunPronunciationService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 单词发音Controller
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@RestController
@RequestMapping("/system/pronunciation")
public class FunPronunciationController extends BaseController
{
    @Autowired
    private IFunPronunciationService funPronunciationService;

    /**
     * 查询单词发音列表
     */
    @PreAuthorize("@ss.hasPermi('system:pronunciation:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunPronunciation funPronunciation)
    {
        startPage();
        List<FunPronunciation> list = funPronunciationService.selectFunPronunciationList(funPronunciation);
        return getDataTable(list);
    }

    /**
     * 导出单词发音列表
     */
    @PreAuthorize("@ss.hasPermi('system:pronunciation:export')")
    @Log(title = "单词发音", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunPronunciation funPronunciation)
    {
        List<FunPronunciation> list = funPronunciationService.selectFunPronunciationList(funPronunciation);
        ExcelUtil<FunPronunciation> util = new ExcelUtil<FunPronunciation>(FunPronunciation.class);
        util.exportExcel(response, list, "单词发音数据");
    }

    /**
     * 获取单词发音详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:pronunciation:query')")
    @GetMapping(value = "/{wordId}")
    public AjaxResult getInfo(@PathVariable("wordId") String wordId)
    {
        return success(funPronunciationService.selectFunPronunciationByWordId(wordId));
    }

    /**
     * 新增单词发音
     */
    @PreAuthorize("@ss.hasPermi('system:pronunciation:add')")
    @Log(title = "单词发音", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunPronunciation funPronunciation)
    {
        return toAjax(funPronunciationService.insertFunPronunciation(funPronunciation));
    }

    /**
     * 修改单词发音
     */
    @PreAuthorize("@ss.hasPermi('system:pronunciation:edit')")
    @Log(title = "单词发音", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunPronunciation funPronunciation)
    {
        return toAjax(funPronunciationService.updateFunPronunciation(funPronunciation));
    }

    /**
     * 删除单词发音
     */
    @PreAuthorize("@ss.hasPermi('system:pronunciation:remove')")
    @Log(title = "单词发音", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wordIds}")
    public AjaxResult remove(@PathVariable String[] wordIds)
    {
        return toAjax(funPronunciationService.deleteFunPronunciationByWordIds(wordIds));
    }
}
