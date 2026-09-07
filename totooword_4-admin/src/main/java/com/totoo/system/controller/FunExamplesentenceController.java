package com.totoo.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
//import org.springframework.security.access.prepost.PreAuthorize;
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
import com.totoo.system.domain.FunExamplesentence;
import com.totoo.system.service.IFunExamplesentenceService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 单词例句Controller
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@RestController
@RequestMapping("/system/examplesentence")
public class FunExamplesentenceController extends BaseController
{
    @Autowired
    private IFunExamplesentenceService funExamplesentenceService;

    /**
     * 查询单词例句列表
     */
//    @PreAuthorize("@ss.hasPermi('system:examplesentence:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunExamplesentence funExamplesentence)
    {
        startPage();
        List<FunExamplesentence> list = funExamplesentenceService.selectFunExamplesentenceList(funExamplesentence);
        return getDataTable(list);
    }

    /**
     * 导出单词例句列表
     */
//    @PreAuthorize("@ss.hasPermi('system:examplesentence:export')")
    @Log(title = "单词例句", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunExamplesentence funExamplesentence)
    {
        List<FunExamplesentence> list = funExamplesentenceService.selectFunExamplesentenceList(funExamplesentence);
        ExcelUtil<FunExamplesentence> util = new ExcelUtil<FunExamplesentence>(FunExamplesentence.class);
        util.exportExcel(response, list, "单词例句数据");
    }

    /**
     * 获取单词例句详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:examplesentence:query')")
    @GetMapping(value = "/{wordId}")
    public AjaxResult getInfo(@PathVariable("wordId") String wordId)
    {
        return success(funExamplesentenceService.selectFunExamplesentenceByWordId(wordId));
    }

    /**
     * 新增单词例句
     */
//    @PreAuthorize("@ss.hasPermi('system:examplesentence:add')")
    @Log(title = "单词例句", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunExamplesentence funExamplesentence)
    {
        return toAjax(funExamplesentenceService.insertFunExamplesentence(funExamplesentence));
    }

    /**
     * 修改单词例句
     */
//    @PreAuthorize("@ss.hasPermi('system:examplesentence:edit')")
    @Log(title = "单词例句", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunExamplesentence funExamplesentence)
    {
        return toAjax(funExamplesentenceService.updateFunExamplesentence(funExamplesentence));
    }

    /**
     * 删除单词例句
     */
//    @PreAuthorize("@ss.hasPermi('system:examplesentence:remove')")
    @Log(title = "单词例句", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wordIds}")
    public AjaxResult remove(@PathVariable String[] wordIds)
    {
        return toAjax(funExamplesentenceService.deleteFunExamplesentenceByWordIds(wordIds));
    }
}
