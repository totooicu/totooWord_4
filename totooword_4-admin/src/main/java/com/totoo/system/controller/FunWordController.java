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
import com.totoo.system.domain.FunWord;
import com.totoo.system.service.IFunWordService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 单词基本信息Controller
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@RestController
@RequestMapping("/system/word")
public class FunWordController extends BaseController
{
    @Autowired
    private IFunWordService funWordService;

    /**
     * 查询单词基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:word:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunWord funWord)
    {
        startPage();
        List<FunWord> list = funWordService.selectFunWordList(funWord);
        return getDataTable(list);
    }

    /**
     * 导出单词基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:word:export')")
    @Log(title = "单词基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunWord funWord)
    {
        List<FunWord> list = funWordService.selectFunWordList(funWord);
        ExcelUtil<FunWord> util = new ExcelUtil<FunWord>(FunWord.class);
        util.exportExcel(response, list, "单词基本信息数据");
    }

    /**
     * 获取单词基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:word:query')")
    @GetMapping(value = "/{wordId}")
    public AjaxResult getInfo(@PathVariable("wordId") String wordId)
    {
        return success(funWordService.selectFunWordByWordId(wordId));
    }

    /**
     * 新增单词基本信息
     */
    @PreAuthorize("@ss.hasPermi('system:word:add')")
    @Log(title = "单词基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunWord funWord)
    {
        return toAjax(funWordService.insertFunWord(funWord));
    }

    /**
     * 修改单词基本信息
     */
    @PreAuthorize("@ss.hasPermi('system:word:edit')")
    @Log(title = "单词基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunWord funWord)
    {
        return toAjax(funWordService.updateFunWord(funWord));
    }

    /**
     * 删除单词基本信息
     */
    @PreAuthorize("@ss.hasPermi('system:word:remove')")
    @Log(title = "单词基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wordIds}")
    public AjaxResult remove(@PathVariable String[] wordIds)
    {
        return toAjax(funWordService.deleteFunWordByWordIds(wordIds));
    }


}
