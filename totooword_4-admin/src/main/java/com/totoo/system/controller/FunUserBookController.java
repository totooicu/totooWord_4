package com.totoo.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.totoo.system.domain.FunBookWord;
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
import com.totoo.system.domain.FunUserBook;
import com.totoo.system.service.IFunUserBookService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 用户收藏书本Controller
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@RestController
@RequestMapping("/system/userbook")
public class FunUserBookController extends BaseController
{
    @Autowired
    private IFunUserBookService funUserBookService;

    /**
     * 查询用户收藏书本列表
     */
//    @PreAuthorize("@ss.hasPermi('system:book:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunUserBook funUserBook)
    {
        startPage();
        List<FunUserBook> list = funUserBookService.selectFunUserBookList(funUserBook);
        return getDataTable(list);
    }

    /**
     * 导出用户收藏书本列表
     */
//    @PreAuthorize("@ss.hasPermi('system:book:export')")
    @Log(title = "用户收藏书本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunUserBook funUserBook)
    {
        List<FunUserBook> list = funUserBookService.selectFunUserBookList(funUserBook);
        ExcelUtil<FunUserBook> util = new ExcelUtil<FunUserBook>(FunUserBook.class);
        util.exportExcel(response, list, "用户收藏书本数据");
    }

    /**
     * 获取用户收藏书本详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:book:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(funUserBookService.selectFunUserBookListByUserId(userId));
    }

    /**
     * 新增用户收藏书本
     */
//    @PreAuthorize("@ss.hasPermi('system:book:add')")
    @Log(title = "用户收藏书本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunUserBook funUserBook)
    {
        return toAjax(funUserBookService.insertFunUserBook(funUserBook));
    }

    /**
     * 修改用户收藏书本
     */
//    @PreAuthorize("@ss.hasPermi('system:book:edit')")
    @Log(title = "用户收藏书本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunUserBook funUserBook)
    {
        return toAjax(funUserBookService.updateFunUserBook(funUserBook));
    }

    /**
     * 删除用户收藏书本
     */
//    @PreAuthorize("@ss.hasPermi('system:book:remove')")
    @Log(title = "用户收藏书本", businessType = BusinessType.DELETE)
	@DeleteMapping("removeByUserIds/{userIds}")
    public AjaxResult removeByUserIds(@PathVariable Long[] userIds)
    {
        return toAjax(funUserBookService.deleteFunUserBookByUserIds(userIds));
    }

    @DeleteMapping("removeByBookIdsSelf/{bookIds}")
    public AjaxResult removeByBookIdsSelf(@PathVariable Long[] bookIds)
    {
        return toAjax(funUserBookService.deleteFunUserBookByBookIdsUserId(getUserId(),bookIds));
    }
    @PostMapping("editByBookIdsSelf")
    public AjaxResult editByBookIdsSelf(@RequestBody Map<String, Object> receive){
        System.out.println(">>>FunUserBookController>>>editByBookIdsSelf>>>receive "+receive);
        List<String> newBookIds = ((List<String>) receive.get("bookIds"));
        List<FunUserBook> oldBook = funUserBookService.selectFunUserBookListByUserId( getUserId());
        List<String> oldBookIds = oldBook.stream().map(FunUserBook::getBookId).collect(Collectors.toList());

        // 计算需要添加和删除的bookIds
        List<String> toAdd = newBookIds.stream().filter(id -> !oldBookIds.contains(id)).collect(Collectors.toList());
        List<String> toDelete = oldBookIds.stream().filter(id -> !newBookIds.contains(id)).collect(Collectors.toList());


        // 调用ADD函数
        for (String bookId : toAdd) {
            FunUserBook funUserBook = new FunUserBook();
            funUserBook.setBookId(bookId);
            funUserBook.setUserId(getUserId());
            funUserBook.setCollectionTime(new Date());
            add(funUserBook);
        }

        //delete转为Long[]
        Long[] toDeleteInt = toDelete.stream().map(Long::parseLong).toArray(Long[]::new);
        // 调用DEL函数
        if(toDeleteInt.length>0) removeByBookIdsSelf(toDeleteInt);

        return AjaxResult.success();
    }
}
