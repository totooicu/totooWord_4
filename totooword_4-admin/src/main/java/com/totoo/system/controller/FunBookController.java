package com.totoo.system.controller;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.totoo.common.utils.SecurityUtils;
import com.totoo.system.domain.FunBookWord;
import com.totoo.system.domain.FunUserBook;
import com.totoo.system.domain.FunWord;
import com.totoo.system.service.IFunBookWordService;
import com.totoo.system.service.IFunUserBookService;
import com.totoo.system.service.IFunWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.totoo.common.annotation.Log;
import com.totoo.common.core.controller.BaseController;
import com.totoo.common.core.domain.AjaxResult;
import com.totoo.common.enums.BusinessType;
import com.totoo.system.domain.FunBook;
import com.totoo.system.service.IFunBookService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 书本信息Controller
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@RestController
@RequestMapping("/system/book")
public class FunBookController extends BaseController
{
    @Autowired
    private IFunBookService funBookService;
    @Autowired
    private IFunWordService funWordService;
    @Autowired
    private IFunBookWordService  funBookWordService;
    @Autowired
    private IFunUserBookService funUserBookService;
    /**
     * 查询书本信息列表
     */
//    @PreAuthorize("@ss.hasPermi('system:book:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunBook funBook)
    {
        startPage();
        List<FunBook> list = funBookService.selectFunBookList(funBook);
        return getDataTable(list);
    }

    @GetMapping("/listBySelf")
    public TableDataInfo listBySelf(FunBook funBook)
    {
        startPage();
        funBook.setOwnerId(SecurityUtils.getUserId());
        List<FunBook> list = funBookService.selectFunBookList(funBook);
        return getDataTable(list);
    }
    @GetMapping("/listByUserBookBySelf_ByBook")
    public AjaxResult listByUserBookBySelf_ByBook(FunBook funBook){
        List<FunUserBook> listUserBook = funUserBookService.selectFunUserBookListByUserId(SecurityUtils.getUserId());
        if(listUserBook.size()==0) return AjaxResult.success();
        List<FunBook> listBook = new ArrayList<>();
        for (FunUserBook funUserBook : listUserBook) {
            System.out.println(">>>FunBookController>>>listByUserBookBySelf_ByBook>>>funUserBook.getBookId():"+funUserBook.getBookId());
            funBook.setBookId(funUserBook.getBookId());
            List<FunBook> funBookList= funBookService.selectFunBookList(funBook);
            System.out.println(">>>FunBookController>>>listByUserBookBySelf_ByBook>>>funBookList:"+funBookList);
            if(funBookList.size()==1) listBook.add(funBookList.get(0));
        }
    return AjaxResult.success(listBook);
    }
    @GetMapping("/listByUserBookBySelf")
    public TableDataInfo listByUserBookBySelf(FunBook funBook)
    {
        startPage();
        List<FunUserBook> listUserBook = funUserBookService.selectFunUserBookListByUserId(SecurityUtils.getUserId());
        if(listUserBook.size()==0) return getDataTable(new ArrayList<>());
        List<FunBook> listBook = new ArrayList<>();
        for (FunUserBook funUserBook : listUserBook) {
            System.out.println(">>>FunBookController>>>listByUserBookBySelf>>>funUserBook.getBookId():"+funUserBook.getBookId());
            listBook.add(funBookService.selectFunBookByBookId(funUserBook.getBookId()));
        }
        return getDataTable(listBook);
    }

    @GetMapping("/getBookByOwn")
    public AjaxResult getBookByOwn()
    {
        FunBook funBook = new FunBook();
        funBook.setOwnerId(SecurityUtils.getUserId());
        List<FunBook> list = funBookService.selectFunBookList(funBook);
        return success(list);
    }

    /**
     * 导出书本信息列表
     */
//    @PreAuthorize("@ss.hasPermi('system:book:export')")
    @Log(title = "书本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunBook funBook)
    {
        List<FunBook> list = funBookService.selectFunBookList(funBook);
        ExcelUtil<FunBook> util = new ExcelUtil<FunBook>(FunBook.class);
        util.exportExcel(response, list, "书本信息数据");
    }

    /**
     * 获取书本信息详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:book:query')")
    @GetMapping(value = "/{bookId}")
    public AjaxResult getInfo(@PathVariable("bookId") String bookId)
    {
        return success(funBookService.selectFunBookByBookId(bookId));
    }

    /**
     * 新增书本信息
     * 传入title、language、[description,intro]
     */
//    @PreAuthorize("@ss.hasPermi('system:book:add')")
    @Log(title = "书本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunBook funBook)
    {
        Date nowDate = new Date();
        funBook.setIsPublished(0);
        funBook.setOwnerId(SecurityUtils.getUserId());
        funBook.setAddTime(nowDate);
        if(funBookService.insertFunBook(funBook)>0){return AjaxResult.success(funBook);}
        else return AjaxResult.error();
    }

    /**
     * 修改书本信息
     */
//    @PreAuthorize("@ss.hasPermi('system:book:edit')")
    @Log(title = "书本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunBook funBook)
    {
        return toAjax(funBookService.updateFunBook(funBook));
    }

    /**
     * 删除书本信息
     */
//    @PreAuthorize("@ss.hasPermi('system:book:remove')")
    @Log(title = "书本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable String[] bookIds)
    {
        return toAjax(funBookService.deleteFunBookByBookIds(bookIds));
    }
    @GetMapping("/getBookByOwnWordIdOrLan")
    public AjaxResult getBookByOwnWordIdOrLan(@RequestParam(required = false) String lan, @RequestParam(required = false) String wordId){
        FunBook funBook = new FunBook();
        funBook.setOwnerId(SecurityUtils.getUserId());
        if(lan!=null&&!Objects.equals(lan, "")) funBook.setLanguage(lan);
        else {
            if(wordId==null||Objects.equals(wordId, "")) return AjaxResult.error();
            FunWord funWord = funWordService.selectFunWordByWordId(wordId);
            funBook.setLanguage(funWord.getLanguage());
        }
        List<FunBook> list = funBookService.selectFunBookList(funBook);
        return success(list);
    }
    @PostMapping("/BooksStatisticsByBookIds")
    public AjaxResult BooksStatisticsByBookIds(@RequestBody Map<String,List<String>> receive) {
        List<String> bids = receive.get("bookIds");
        ArrayList<Integer>bookWordNums=new ArrayList<>(bids.size());
        for(String bid:bids){
            FunBookWord funBookWord = new FunBookWord();
            funBookWord.setBookId(bid);
            FunBook funBook = new FunBook();
            funBook.setBookId(bid);funBook.setOwnerId(SecurityUtils.getUserId());
            if(funBookService.selectFunBookList(funBook).isEmpty())return  AjaxResult.warn("您没有这本书","bid:"+bid);
            bookWordNums.add((funBookWordService.selectFunBookWordList(funBookWord).isEmpty()?0:funBookWordService.selectFunBookWordList(funBookWord).size()));
        }
        ArrayList<HashSet<String>>bookWordIdsStr=new ArrayList<>(bids.size());
        ArrayList<String>bname=new ArrayList<>();
        for(String bid:bids){
            FunBookWord funBookWord = new FunBookWord();
            funBookWord.setBookId(bid);
            bookWordIdsStr.add(new HashSet<>(funBookWordService.selectFunBookWordList(funBookWord).stream().map(bookWord -> bookWord.getWordId()).collect(Collectors.toList())));
            bname.add(funBookService.selectFunBookByBookId(bid).getTitle());
        }
        ArrayList<HashSet<Integer>>bookWordIds=new ArrayList<>(bids.size());
        for(HashSet<String> bookWordIdStr : bookWordIdsStr) {
             bookWordIds.add(new HashSet<>(bookWordIdStr.stream().map(wordId -> Integer.parseInt(wordId)).collect(Collectors.toList())));
         }

        ArrayList< HashSet<String> >bookWords=new ArrayList<>(bids.size());

        for(int i=0;i<bids.size();i++){
            bookWords.add(new HashSet<>());
            for(int wid : bookWordIds.get(i))
                bookWords.get(i).add(funWordService.selectFunWordByWordId(String.valueOf(wid)).getSpell());
        }
        HashMap<String,Object> booksStatisticsPage =new HashMap<>();
        booksStatisticsPage.put("bids",bids);
        booksStatisticsPage.put("words",bookWords);
        booksStatisticsPage.put("bookWordNums",bookWordNums);
        booksStatisticsPage.put("bname",bname);
        return AjaxResult.success(booksStatisticsPage);

    }

    @PostMapping("/listByBookSelf")
    public AjaxResult listByBookSelf(@RequestBody FunBook funBook) {
        funBook.setOwnerId(SecurityUtils.getUserId());
        System.out.println(">>>FunBookController>>>listByBookSelf>>>funBook:"+funBook);
        return AjaxResult.success(funBookService.selectFunBookList(funBook));
    }
}
