package com.totoo.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.totoo.common.core.domain.AjaxResult;
import com.totoo.system.domain.FunBookWord;
import com.totoo.system.domain.FunWord;
import com.totoo.system.domain.FunWordMsg;
import com.totoo.system.mapper.FunBookWordMapper;
import com.totoo.system.mapper.FunWordMsgMapper;
import com.totoo.system.tool.WordPackage;

import com.totoo.system.tool.WordStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wordMsg")
public class FunWordMsgController {
    @Autowired
    public FunWordMsgMapper wordMsgMapper;
    @Autowired
    public ObjectMapper objectMapper;
    @Autowired
    public FunBookWordMapper bookWordMapper;

    @GetMapping("getWordMsgBySpellLan")
    AjaxResult getWordMsgBySpellLan(@RequestParam String spell,@RequestParam String language) throws IOException {
        System.out.println(">>>"+spell+" "+language);
        FunWord word = new FunWord();word.setSpell(spell);word.setLanguage(language);
        if(spell==null||language==null)return new AjaxResult(401,"请求数据为空","");
        if(word.getSpell().equals(""))return new AjaxResult(401,"请输入单词","");
        if(word.getLanguage().equals(""))return new AjaxResult(401,"请选择语言","");
        FunWordMsg wordMsg = new FunWordMsg(wordMsgMapper);
//        wordMsg.setWord(word);
        wordMsg.init(word.getSpell());//数据库查询
        if(wordMsg.getWord()==null){//若数据库中没有数据，则调用爬虫爬取数据并保存到数据库中
            System.out.println(">>>WordMsgController>>>wordMsg.getWord()==null");
            WordPackage wordPackage=new WordPackage(wordMsgMapper,word.getSpell(),word.getLanguage());//初始化爬虫数据
            wordPackage.getWordPage();//爬取数据
            wordPackage.setmsg();//解析数据
            wordPackage.save2MySQL();//保存到数据库中
            wordMsg.init(word.getSpell());//再次查询
        }
        System.out.println(">>>WordMsgController>>>wordMsg "+wordMsg);
        return AjaxResult.success(wordMsg);
    }

    @GetMapping("getWordMsgByWordId")
    AjaxResult getWordMsgByWordId(@RequestParam int id) {
        FunWordMsg wordMsg = new FunWordMsg(wordMsgMapper);
        wordMsg.init(id);
        return AjaxResult.success(wordMsg);
    }



    @GetMapping("getWordMsgsByBookId")
    AjaxResult getWordMsgsByBookId(@RequestParam String bookId) {
        FunBookWord funBookWord = new FunBookWord();
        funBookWord.setBookId(bookId);
        List<FunBookWord> BookWordList = bookWordMapper.selectFunBookWordList(funBookWord);
        List<FunWordMsg> wordMsgList = new ArrayList<>();
        for (FunBookWord funBookWord1 : BookWordList) {
            FunWordMsg wordMsg = new FunWordMsg(wordMsgMapper);
            wordMsg.init(Integer.parseInt(funBookWord1.getWordId()));
            wordMsgList.add(wordMsg);
        }
        return AjaxResult.success(wordMsgList);
    }

    @PostMapping("ContentStatisticsByJson")
    public AjaxResult ContentStatisticsByJson(@RequestBody Map<String,String> receive) {
        System.out.println(receive);
        String language=receive.getOrDefault("language","");
        String context=receive.getOrDefault("text","");
        if(language.equals(""))return AjaxResult.error("请选择语言");
        if(context.equals(""))return AjaxResult.error("请输入内容");
        //初始化数据、文本分割、词（原型）频统计
        WordStatistics wordStatistics=WordStatistics.Init(context).Split(" ").StatisticsWithClassify();
        return AjaxResult.success(wordStatistics.statistics);//返回统计结果
    }
    @PostMapping("getWordMsgsBySpellsLan")
    public AjaxResult getWordMsgsBySpellsLan(@RequestBody Map<String, Object> receive) throws IOException {
        List<String> spells= (List<String>) receive.get("spells");
        String lan=(String) receive.get("language");
        ArrayList<FunWordMsg> wordMsgList=new ArrayList<>();
        for(String spell:spells)wordMsgList.add((FunWordMsg) getWordMsgBySpellLan(spell,lan).get(AjaxResult.DATA_TAG));
        return AjaxResult.success(wordMsgList);
    }

}
