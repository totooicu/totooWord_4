package com.totoo.system.controller;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.totoo.common.utils.SecurityUtils;
import com.totoo.system.domain.FunBook;
import com.totoo.system.domain.FunWord;
import com.totoo.system.domain.FunWordMsg;
import com.totoo.system.mapper.FunWordMsgMapper;
import com.totoo.system.service.IFunBookService;
import com.totoo.system.service.IFunWordService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.totoo.common.annotation.Log;
import com.totoo.common.core.controller.BaseController;
import com.totoo.common.core.domain.AjaxResult;
import com.totoo.common.enums.BusinessType;
import com.totoo.system.domain.FunBookWord;
import com.totoo.system.service.IFunBookWordService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 书本单词关联Controller
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
@RestController
@RequestMapping("/system/bookword")
public class FunBookWordController extends BaseController
{
    @Autowired
    private IFunBookWordService funBookWordService;
    @Autowired
    private IFunBookService funBookService;
    @Autowired
    private IFunWordService funWordService;
    @Autowired
    private FunWordMsgMapper wordMsgMapper;

    /**
     * 查询书本单词关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:word:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunBookWord funBookWord)
    {
        startPage();
        List<FunBookWord> list = funBookWordService.selectFunBookWordList(funBookWord);
        return getDataTable(list);
    }

    /**
     * 导出书本单词关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:word:export')")
    @Log(title = "书本单词关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunBookWord funBookWord)
    {
        List<FunBookWord> list = funBookWordService.selectFunBookWordList(funBookWord);
        ExcelUtil<FunBookWord> util = new ExcelUtil<FunBookWord>(FunBookWord.class);
        util.exportExcel(response, list, "书本单词关联数据");
    }

    /**
     * 获取书本单词关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:word:query')")
    @GetMapping(value = "/{bookId}")
    public AjaxResult getInfo(@PathVariable("bookId") String bookId)
    {
        return success(funBookWordService.selectFunBookWordByBookId(bookId));
    }

    /**
     * 新增书本单词关联
     * 填写 bookId wordId [annotation]
     */
//    @PreAuthorize("@ss.hasPermi('system:word:add')")
    @Log(title = "书本单词关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunBookWord funBookWord)
    {
        funBookWord.setAddTime(new Date());
        return toAjax(funBookWordService.insertFunBookWord(funBookWord));
    }
    @PostMapping("/addsByBookWordsByBookIdWordId")
    public AjaxResult addsByBookWordsByBookIdWordId(@RequestBody Map<String,List<FunBookWord>> res)
    {
        List<FunBookWord> list=res.get("bookWords");
        int count=0;
        for(FunBookWord funBookWord:list){
            funBookWord.setAddTime(new Date());
            if(funBookWord.getWordId()==null||funBookWord.getWordId().equals("")||funBookWord.getBookId()==null||funBookWord.getBookId().equals(""))return AjaxResult.error("添加失败，请检查输入",funBookWord);
            //如果报 Duplicate entry '3-2' for key 'fun_book_word.PRIMARY'错误，则继续执行
            try {
                funBookWordService.insertFunBookWord(funBookWord);
            } catch (Exception e) {
                count++;
                System.out.println(">>>FunBookWordController>>>addsByBookWordsByBookIdWordId>>>catch>>>"+e);
            }
        }
        return AjaxResult.success("添加成功，其中已有"+count+"个单词已经在书本中");
    }

    /*{
    "chosenBookIds1":[""],//op1控制
    "chosenBookIds2":[""],//op2控制
    "savedBookId":"",//chosenBookIds1 op3 chosenBookIds2
    "op1":"","op2":"","op3":""//op1 op2为1并2交中的一个，op3多了个3补

}*/
    public Set<String> operateBookWordsByBookIds_ByLogical( List<String>chosenBookIds,String op){
        Set<String>res=new HashSet<>();
        int i=0;
        for(String bookId:chosenBookIds){i++;
            FunBookWord funBookWordSelector=new FunBookWord();
            funBookWordSelector.setBookId(bookId);
            List<FunBookWord> funBookWordList=funBookWordService.selectFunBookWordList(funBookWordSelector);
            if(funBookWordList.size()==0)continue;
            Set<String> wordIds=funBookWordList.stream().map(FunBookWord::getWordId).collect(Collectors.toSet());
            //根据op将结果存入到res
            if(op!=null&&!op.equals(""))switch (op){
                case "1": res.addAll(wordIds);break;
                case "2": if(i==1)res.addAll(wordIds);else res.retainAll(wordIds);break;
            }
    }
    return res;
    }
    public Map<String,Set<String>>getByBookWordsByBookIds_ByLogical(Map<String,Object> res){
        List<String> chosenBookIds1= (List<String>) res.get("chosenBookIds1"), chosenBookIds2= (List<String>) res.get("chosenBookIds2");
        String op1= (String) res.get("op1"),op2= (String) res.get("op2"),op3= (String) res.get("op3");
        Set<String> wordIds1, wordIds2;
        Map<String,Set<String>> result=new HashMap<>();
        //处理chosenBookIds1 op1，得到wordIds1
        result.put("wordIds1",wordIds1= operateBookWordsByBookIds_ByLogical(chosenBookIds1,op1));
        System.out.println(">>>FunBookWordController>>>addsByBookWordsByBookIds_ByLogical>>>wordIds1:"+wordIds1);
        result.put("wordIds2",wordIds2= operateBookWordsByBookIds_ByLogical(chosenBookIds2,op2));
        System.out.println(">>>FunBookWordController>>>addsByBookWordsByBookIds_ByLogical>>>wordIds2:"+wordIds2);
        if(op3!=null&&!op3.equals(""))switch (op3){
            case "1": wordIds1.addAll(wordIds2);break;
            case "2": wordIds1.retainAll(wordIds2);break;
            case "3": wordIds1.addAll(wordIds2);wordIds1.removeAll(wordIds2);break;
        }
        result.put("wordIds3",wordIds1);
        System.out.println(">>>FunBookWordController>>>addsByBookWordsByBookIds_ByLogical>>>wordIds1:"+wordIds1);
        return result;
    }
    //预览处理结果
    @PostMapping("/getWordMsgMapByBookWordsByBookIds_ByLogical")
    public AjaxResult getWordMsgMapByBookWordsByBookIds_ByLogical(@RequestBody Map<String,Object> res){
        Map<String,List<FunWordMsg>> ans=new HashMap<>();
        List<String> wordIds1= new ArrayList<>( getByBookWordsByBookIds_ByLogical(res).get("wordIds1"));
        List<String> wordIds2= new ArrayList<>( getByBookWordsByBookIds_ByLogical(res).get("wordIds2"));
        List<String> wordIds3= new ArrayList<>( getByBookWordsByBookIds_ByLogical(res).get("wordIds3"));
        List<FunWordMsg> wordMsgList=new ArrayList<>();
        for(String wordId:wordIds1) wordMsgList.add( (new FunWordMsg(wordMsgMapper)).init(Long.parseLong( wordId)));
        ans.put("preview1",wordMsgList);wordMsgList=new ArrayList<>();
        for(String wordId:wordIds2) wordMsgList.add( (new FunWordMsg(wordMsgMapper)).init(Long.parseLong( wordId)));
        ans.put("preview2",wordMsgList);wordMsgList=new ArrayList<>();
        for(String wordId:wordIds3) wordMsgList.add((new FunWordMsg(wordMsgMapper)).init(Long.parseLong( wordId)));
        ans.put("preview3",wordMsgList);
        System.out.println(">>>BookWordController>>>getWordMsgMapByBookWordsByBookIds_ByLogical>>>ans:"+wordIds1+wordIds2+wordIds3);
    return AjaxResult.success(ans);
    }

    @PostMapping("/addsByBookWordsByBookIds_ByLogical")
    public AjaxResult addsByBookWordsByBookIds_ByLogical(@RequestBody Map<String,Object> res){
        Set<String> wordIds1=getByBookWordsByBookIds_ByLogical(res).get("wordIds3");
        String savedBookId= (String) res.get("savedBookId");
        int count=0;
        for(String wordId:wordIds1){
            FunBookWord funBookWord=new FunBookWord();
            funBookWord.setBookId(savedBookId);
            funBookWord.setWordId(wordId);
            funBookWord.setAddTime(new Date());
            //追加，若重复则不提醒报错，统计多少重复
            try {
                funBookWordService.insertFunBookWord(funBookWord);
            } catch (Exception e) {
                count++;
                System.out.println(">>>FunBookWordController>>>addsByBookWordsByBookIds_ByLogical>>>catch>>>"+e);
            }
        }
        return AjaxResult.success("添加成功，其中追加到书本中的重复单词有"+count+"个");
    }

    /**
     * 修改书本单词关联
     */
    @PreAuthorize("@ss.hasPermi('system:word:edit')")
    @Log(title = "书本单词关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunBookWord funBookWord)
    {
        return toAjax(funBookWordService.updateFunBookWord(funBookWord));
    }

    /**
     * 删除书本单词关联
     */
    @PreAuthorize("@ss.hasPermi('system:word:remove')")
    @Log(title = "书本单词关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable String[] bookIds)
    {
        return toAjax(funBookWordService.deleteFunBookWordByBookIds(bookIds));
    }


    @GetMapping("/searchBookIdsByWordIdSelf")
    public AjaxResult searchBookIdsByWordIdSelf(@RequestParam String wordId)
    {
        System.out.println(">>>FunBookWordController>>>wordId "+wordId);
        List<Integer> bookIds = funBookWordService.searchBookIdsByWordIdSelf(Integer.valueOf(wordId), getUserId());
        System.out.println(">>>FunBookWordController>>>bookIds "+bookIds);
        return AjaxResult.success(bookIds.stream()
                .map(Object::toString)  // 将所有元素转换为字符串
                .collect(Collectors.toList()));
    }
    @PostMapping("editByBookIdsWordIdSelf")
    public AjaxResult editByBookIdsWordIdSelf(@RequestBody Map<String, Object> receive)
    {
        String wordId = (String) receive.get("wordId");
        List<String> bookIdsNew = ((List<String>) receive.get("bookIds"));;

        List<String> bookIdsOld = ((List<String>) searchBookIdsByWordIdSelf(wordId).get(AjaxResult.DATA_TAG));
        System.out.println(">>>FunBookWordController>>>bookIdsOld "+bookIdsOld);
        System.out.println(">>>FunBookWordController>>>bookIdsNew "+bookIdsNew);

        //抽取bookid
//        List<String> bookIdsOld = list.stream().map( FunBook::getBookId).collect(Collectors.toList());

        // 计算需要添加和删除的bookIds
        List<String> toAdd = bookIdsNew.stream().filter(id -> !bookIdsOld.contains(id)).collect(Collectors.toList());
        List<String> toDelete = bookIdsOld.stream().filter(id -> !bookIdsNew.contains(id)).collect(Collectors.toList());


        // 调用ADD函数
        for (String bookId : toAdd) {
            FunBookWord funBookWord = new FunBookWord();
            funBookWord.setBookId(bookId);
            funBookWord.setWordId(wordId);
            funBookWord.setAddTime(new Date());
            add(funBookWord);
        }

        // 调用DEL函数
        for (String bookId : toDelete) {
            remove(new String[]{bookId});
        }

        return AjaxResult.success();
    }


}
