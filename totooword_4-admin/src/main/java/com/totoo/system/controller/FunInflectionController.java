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
import com.totoo.system.domain.FunInflection;
import com.totoo.system.service.IFunInflectionService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 单词时态变形Controller
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@RestController
@RequestMapping("/system/inflection")
public class FunInflectionController extends BaseController
{
    @Autowired
    private IFunInflectionService funInflectionService;

    /**
     * 查询单词时态变形列表
     */
    @PreAuthorize("@ss.hasPermi('system:inflection:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunInflection funInflection)
    {
        startPage();
        List<FunInflection> list = funInflectionService.selectFunInflectionList(funInflection);
        return getDataTable(list);
    }

    /**
     * 导出单词时态变形列表
     */
    @PreAuthorize("@ss.hasPermi('system:inflection:export')")
    @Log(title = "单词时态变形", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunInflection funInflection)
    {
        List<FunInflection> list = funInflectionService.selectFunInflectionList(funInflection);
        ExcelUtil<FunInflection> util = new ExcelUtil<FunInflection>(FunInflection.class);
        util.exportExcel(response, list, "单词时态变形数据");
    }

    /**
     * 获取单词时态变形详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:inflection:query')")
    @GetMapping(value = "/{wordId}")
    public AjaxResult getInfo(@PathVariable("wordId") String wordId)
    {
        return success(funInflectionService.selectFunInflectionByWordId(wordId));
    }

    /**
     * 新增单词时态变形
     */
    @PreAuthorize("@ss.hasPermi('system:inflection:add')")
    @Log(title = "单词时态变形", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunInflection funInflection)
    {
        return toAjax(funInflectionService.insertFunInflection(funInflection));
    }

    /**
     * 修改单词时态变形
     */
    @PreAuthorize("@ss.hasPermi('system:inflection:edit')")
    @Log(title = "单词时态变形", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunInflection funInflection)
    {
        return toAjax(funInflectionService.updateFunInflection(funInflection));
    }

    /**
     * 删除单词时态变形
     */
    @PreAuthorize("@ss.hasPermi('system:inflection:remove')")
    @Log(title = "单词时态变形", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wordIds}")
    public AjaxResult remove(@PathVariable String[] wordIds)
    {
        return toAjax(funInflectionService.deleteFunInflectionByWordIds(wordIds));
    }
}
