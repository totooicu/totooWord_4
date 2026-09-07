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
import com.totoo.system.domain.FunPhrase;
import com.totoo.system.service.IFunPhraseService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 单词短语Controller
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@RestController
@RequestMapping("/system/phrase")
public class FunPhraseController extends BaseController
{
    @Autowired
    private IFunPhraseService funPhraseService;

    /**
     * 查询单词短语列表
     */
//    @PreAuthorize("@ss.hasPermi('system:phrase:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunPhrase funPhrase)
    {
        startPage();
        List<FunPhrase> list = funPhraseService.selectFunPhraseList(funPhrase);
        return getDataTable(list);
    }

    /**
     * 导出单词短语列表
     */
//    @PreAuthorize("@ss.hasPermi('system:phrase:export')")
    @Log(title = "单词短语", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunPhrase funPhrase)
    {
        List<FunPhrase> list = funPhraseService.selectFunPhraseList(funPhrase);
        ExcelUtil<FunPhrase> util = new ExcelUtil<FunPhrase>(FunPhrase.class);
        util.exportExcel(response, list, "单词短语数据");
    }

    /**
     * 获取单词短语详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:phrase:query')")
    @GetMapping(value = "/{wordId}")
    public AjaxResult getInfo(@PathVariable("wordId") String wordId)
    {
        return success(funPhraseService.selectFunPhraseByWordId(wordId));
    }

    /**
     * 新增单词短语
     */
//    @PreAuthorize("@ss.hasPermi('system:phrase:add')")
    @Log(title = "单词短语", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunPhrase funPhrase)
    {
        return toAjax(funPhraseService.insertFunPhrase(funPhrase));
    }

    /**
     * 修改单词短语
     */
//    @PreAuthorize("@ss.hasPermi('system:phrase:edit')")
    @Log(title = "单词短语", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunPhrase funPhrase)
    {
        return toAjax(funPhraseService.updateFunPhrase(funPhrase));
    }

    /**
     * 删除单词短语
     */
//    @PreAuthorize("@ss.hasPermi('system:phrase:remove')")
    @Log(title = "单词短语", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wordIds}")
    public AjaxResult remove(@PathVariable String[] wordIds)
    {
        return toAjax(funPhraseService.deleteFunPhraseByWordIds(wordIds));
    }
}
