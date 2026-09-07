package com.totoo.system.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.totoo.common.utils.SecurityUtils;
import com.totoo.system.domain.*;
import com.totoo.system.mapper.FunBookWordMapper;
import com.totoo.system.mapper.FunWordMsgMapper;
import com.totoo.system.service.IFunBookWordService;
import com.totoo.system.service.IFunMemorizedUserConfigService;
import com.totoo.system.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.totoo.system.service.IFunMemorizedService;
import com.totoo.common.utils.poi.ExcelUtil;
import com.totoo.common.core.page.TableDataInfo;

/**
 * 单词记忆跟踪Controller
 * 
 * @author ruoyi
 * @date 2025-02-02
 */
@RestController
@RequestMapping("/system/memorized")
public class FunMemorizedController extends BaseController
{
    @Autowired
    private IFunMemorizedService funMemorizedService;
    @Autowired
    private IFunMemorizedUserConfigService funMemorizedUserConfigService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IFunBookWordService funBookWordService;
    @Autowired
    public FunWordMsgMapper wordMsgMapper;
    @Autowired
    public ObjectMapper objectMapper;
    @Autowired
    public FunBookWordMapper bookWordMapper;

    /**
     * 查询单词记忆跟踪列表
     */
    @PreAuthorize("@ss.hasPermi('system:memorized:list')")
    @GetMapping("/list")
    public TableDataInfo list(FunMemorized funMemorized)
    {
        startPage();
        List<FunMemorized> list = funMemorizedService.selectFunMemorizedList(funMemorized);
        return getDataTable(list);
    }
    @GetMapping("/listByMemorizedSelf")
    public AjaxResult listByMemorizedSelf(FunMemorized funMemorized)
    {
        funMemorized.setUserId(SecurityUtils.getUserId());
        List<FunMemorized> list = funMemorizedService.selectFunMemorizedList(funMemorized);
        return AjaxResult.success(list);
    }
    /**
     * 导出单词记忆跟踪列表
     */
    @PreAuthorize("@ss.hasPermi('system:memorized:export')")
    @Log(title = "单词记忆跟踪", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FunMemorized funMemorized)
    {
        List<FunMemorized> list = funMemorizedService.selectFunMemorizedList(funMemorized);
        ExcelUtil<FunMemorized> util = new ExcelUtil<FunMemorized>(FunMemorized.class);
        util.exportExcel(response, list, "单词记忆跟踪数据");
    }

    /**
     * 获取单词记忆跟踪详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:memorized:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(funMemorizedService.selectFunMemorizedByUserId(userId));
    }

    /**
     * 新增单词记忆跟踪
     */
    @PreAuthorize("@ss.hasPermi('system:memorized:add')")
    @Log(title = "单词记忆跟踪", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FunMemorized funMemorized)
    {
        return toAjax(funMemorizedService.insertFunMemorized(funMemorized));
    }

    /**
     * 修改单词记忆跟踪
     */
    @PreAuthorize("@ss.hasPermi('system:memorized:edit')")
    @Log(title = "单词记忆跟踪", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FunMemorized funMemorized)
    {
        return toAjax(funMemorizedService.updateFunMemorized(funMemorized));
    }

    /**
     * 删除单词记忆跟踪
     */
    @PreAuthorize("@ss.hasPermi('system:memorized:remove')")
    @Log(title = "单词记忆跟踪", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(funMemorizedService.deleteFunMemorizedByUserIds(userIds));
    }

    @GetMapping("getMemorizedDayBySelf")
    public AjaxResult getMemorizedDayBySelf()
    {
        HashMap<String,Long> memorizedDay = new HashMap<>();
        memorizedDay.put("AllDays",0L);memorizedDay.put("continuousDays",0L);

        FunMemorizedUserConfig funMemorizedUserConfig = funMemorizedUserConfigService.selectFunMemorizedUserConfigByUserId(SecurityUtils.getUserId());
        if(funMemorizedUserConfig == null)return AjaxResult.success(memorizedDay);
        memorizedDay.put("AllDays",funMemorizedUserConfig.getTotalCheckinDays());
        memorizedDay.put("continuousDays", redisService.getContinuousDays(Long.toString(SecurityUtils.getUserId())));
        return AjaxResult.success(memorizedDay);
    }

    @GetMapping("/listWordMsgsByBookWordByBookIdAndListWordMsgsByMemorizedByWordIdsByBookWordByBookId")
    public AjaxResult listWordMsgsByBookWordByBookIdAndListWordMsgsByMemorizedByWordIdsByBookWordByBookId(String bookId)
    {
        FunBookWord funBookWord = new FunBookWord();funBookWord.setBookId(bookId);
        FunWordMsgController funWordMsgController = new FunWordMsgController();
        funWordMsgController.wordMsgMapper=wordMsgMapper; funWordMsgController.bookWordMapper=bookWordMapper; funWordMsgController.objectMapper=objectMapper;
        List<FunWordMsg> wordMsgs = (List<FunWordMsg>) funWordMsgController.getWordMsgsByBookId(bookId).get(AjaxResult.DATA_TAG);
        List<FunBookWord> bookWords = funBookWordService.selectFunBookWordList(funBookWord);
        Set<String> BookWordWordIds = bookWords.stream().map(FunBookWord::getWordId).collect(Collectors.toSet());
        FunMemorized memorized = new FunMemorized();
        memorized.setUserId(SecurityUtils.getUserId());
        List<FunMemorized> memorizedList = funMemorizedService.selectFunMemorizedList(memorized);
        Set<String> memorizedWordIds = memorizedList.stream().map(FunMemorized::getWordId).collect(Collectors.toSet());
        List<FunWordMsg> memorizedWordMsgList=new ArrayList<>();
        for(String wordId:memorizedWordIds) memorizedWordMsgList.add((FunWordMsg) funWordMsgController.getWordMsgByWordId(Integer.parseInt(wordId)).get(AjaxResult.DATA_TAG));
        Set<String> commonWordIds = new HashSet<>(BookWordWordIds);
        commonWordIds.retainAll(memorizedWordIds);
        List<FunWordMsg> commonWordMsgList=new ArrayList<>();
        for(String wordId:commonWordIds) commonWordMsgList.add((FunWordMsg) funWordMsgController.getWordMsgByWordId(Integer.parseInt(wordId)).get(AjaxResult.DATA_TAG));
        Set<String> uncommonWordIds = new HashSet<>(BookWordWordIds);
        uncommonWordIds.removeAll(memorizedWordIds);
        List<FunWordMsg> uncommonWordMsgList=new ArrayList<>();
        for(String wordId:uncommonWordIds) uncommonWordMsgList.add((FunWordMsg) funWordMsgController.getWordMsgByWordId(Integer.parseInt(wordId)).get(AjaxResult.DATA_TAG));
        return AjaxResult.success(new HashMap<String,Object>(){{
            put("allWordMsgs",wordMsgs);//A
            put("memorizedWordMsgs",memorizedWordMsgList);//B
            put("commonWordMsgs",commonWordMsgList);//A*B->old
            put("uncommonWordMsgs",uncommonWordMsgList);//A-B->new
            put("bookWordsCount",bookWords.size());
            put("memorizedCount",memorizedWordIds.size());
            put("commonCount",commonWordIds.size());
            put("uncommonCount",uncommonWordIds.size());
        }});
    }
    @GetMapping("/getTodayReviewWordBySelf")
    public AjaxResult getTodayReviewWordBySelf() {

        if(redisService.getCheckinPattern(SecurityUtils.getUserId().toString(), "review"))return AjaxResult.warn("今日已打卡");

        FunMemorizedUserConfig funMemorizedUserConfig = funMemorizedUserConfigService.selectFunMemorizedUserConfigByUserId(SecurityUtils.getUserId());
        Integer bookId = funMemorizedUserConfig.getCurrentBookId();
        Map<String, Object> rst= (Map<String, Object>) listWordMsgsByBookWordByBookIdAndListWordMsgsByMemorizedByWordIdsByBookWordByBookId(String.valueOf(bookId)).get(AjaxResult.DATA_TAG);
        List<FunWordMsg>oldWordMsgList= (List<FunWordMsg>) rst.get("commonWordMsgs");
        List< FunMemorizedWordMsg> oldWordMsgList1=new ArrayList<>();
        for(FunWordMsg funWordMsg:oldWordMsgList) {
            FunMemorizedWordMsg funMemorizedWordMsg=new FunMemorizedWordMsg();
            funMemorizedWordMsg.setWordMsg(funWordMsg);
            FunBookWord funBookWord=new FunBookWord();
            funBookWord.setWordId(funWordMsg.getWord().getWordId());funBookWord.setBookId(String.valueOf(bookId));
            funMemorizedWordMsg.setBookWord(funBookWordService.selectFunBookWordList(funBookWord).get(0));
            FunMemorized funMemorized=new FunMemorized();funMemorized.setUserId(SecurityUtils.getUserId());funMemorized.setWordId(funWordMsg.getWord().getWordId());
            funMemorizedWordMsg.setMemorized(funMemorizedService.selectFunMemorizedList(funMemorized).get(0));
            //如果复习时间小于1天则不复习
            if (LocalDate.now().equals(funMemorizedWordMsg.getMemorized().getLastStudyTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) continue;            oldWordMsgList1.add(funMemorizedWordMsg);
        }
        oldWordMsgList1.sort( (o1, o2) -> {return (int) (o1.getScore()-o2.getScore());});

        return AjaxResult.success(new HashMap<String,Object>(){{
            put("MemorizedWordMsgList",oldWordMsgList1.subList(0,Math.min(oldWordMsgList1.size(),funMemorizedUserConfig.getDailyReviewGoal())));
            put("timestamp",System.currentTimeMillis());
        }});
    }
    @GetMapping("/getTodayNewWordBySelf")
    public AjaxResult getTodayNewWordBySelf() {
        if(redisService.getCheckinPattern(SecurityUtils.getUserId().toString(), "learnNew"))return AjaxResult.warn("今日已打卡");
        FunMemorizedUserConfig funMemorizedUserConfig = funMemorizedUserConfigService.selectFunMemorizedUserConfigByUserId(SecurityUtils.getUserId());
        Integer bookId = funMemorizedUserConfig.getCurrentBookId();
        Map<String, Object> rst= (Map<String, Object>) listWordMsgsByBookWordByBookIdAndListWordMsgsByMemorizedByWordIdsByBookWordByBookId(String.valueOf(bookId)).get(AjaxResult.DATA_TAG);
        List<FunWordMsg>newWordMsgList= (List<FunWordMsg>) rst.get("uncommonWordMsgs");
        List< FunMemorizedWordMsg> newWordMsgList1=new ArrayList<>();
        for(FunWordMsg funWordMsg:newWordMsgList) {
            FunMemorizedWordMsg funMemorizedWordMsg=new FunMemorizedWordMsg();
            funMemorizedWordMsg.setWordMsg(funWordMsg);
            FunBookWord funBookWord=new FunBookWord();
            funBookWord.setWordId(funWordMsg.getWord().getWordId());funBookWord.setBookId(String.valueOf(bookId));
            funMemorizedWordMsg.setBookWord(funBookWordService.selectFunBookWordList(funBookWord).get(0));
            newWordMsgList1.add(funMemorizedWordMsg);
        }
        newWordMsgList1.sort(Comparator.comparing(o -> o.getBookWord().getAddTime()));//按时间排序，时间久远的在前面
        return AjaxResult.success(new HashMap<String,Object>(){{
            put("MemorizedWordMsgList",newWordMsgList1.subList(0,Math.min(newWordMsgList1.size(),funMemorizedUserConfig.getDailyLearningGoal())));
            put("timestamp",System.currentTimeMillis());
        }});
    }
    @PutMapping("/updataStudayDataBySelfWordId")
    public AjaxResult updataStudayDataBySelfWordId(@RequestBody Map<String, Object> data) {
        String pattern= (String) data.get("pattern");
        System.out.println(">>>updataStudayDataBySelfWordId>>>pattern"+pattern);
        if(redisService.setCheckinPattern(String.valueOf(SecurityUtils.getUserId()),pattern)){
            FunMemorizedUserConfig funMemorizedUserConfig=funMemorizedUserConfigService.selectFunMemorizedUserConfigByUserId(SecurityUtils.getUserId());
            funMemorizedUserConfig.setTotalCheckinDays((int) (funMemorizedUserConfig.getTotalCheckinDays()+1));
            funMemorizedUserConfigService.updateFunMemorizedUserConfig(funMemorizedUserConfig);
        };
        //完成打卡，将打卡状态存储到redis，过了当天0点就失效删除redis的内容
        List<Map<String, Object>> newData = (List<Map<String, Object>>) data.get("newData");
        for(Map<String, Object> score:newData){
            System.out.println(">>>updataStudayDataBySelfWordId>>>score"+score);
            FunMemorized funMemorizedCondition=new FunMemorized(),funMemorized;
            funMemorizedCondition.setWordId(score.get("wordId").toString());funMemorizedCondition.setUserId(SecurityUtils.getUserId());
            List<FunMemorized> funMemorizedList= funMemorizedService.selectFunMemorizedList(funMemorizedCondition);
            System.out.println(">>>updataStudayDataBySelfWordId>>>funMemorizedList"+funMemorizedList);
            if(funMemorizedList.size()>0) funMemorized=funMemorizedList.get(0);
            else {
                funMemorized = funMemorizedCondition;funMemorized.setTotalStudyCount(0L);funMemorized.setConsecutiveCorrect(0L);funMemorized.setConsecutiveError(0L);
            }
            funMemorized.setLastStudyTime(new Date());
            funMemorized.setTotalStudyCount(funMemorized.getTotalStudyCount()+1);
            if((Boolean) score.get("isCorrect")){
                funMemorized.setConsecutiveCorrect(funMemorized.getConsecutiveCorrect()+1L);
                funMemorized.setConsecutiveError(0L);
            }else{
                funMemorized.setConsecutiveCorrect(0L);
                funMemorized.setConsecutiveError(funMemorized.getConsecutiveError()+1);
            }
            if(score.get("score")!=null)funMemorized.setLastScore(((Integer) score.get("score")).longValue());
            System.out.println(">>>updataStudayDataBySelfWordId>>>funMemorized"+funMemorized);
            if(funMemorizedList.size()>0) funMemorizedService.updateFunMemorized(funMemorized);
            else funMemorizedService.insertFunMemorized(funMemorized);
        }
        return AjaxResult.success();
    }
}
