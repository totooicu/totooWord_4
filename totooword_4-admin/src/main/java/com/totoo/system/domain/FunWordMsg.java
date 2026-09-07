package com.totoo.system.domain;

import com.totoo.system.mapper.FunWordMsgMapper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@Component
@RestController
public class FunWordMsg {

    private FunWordMsgMapper wordMsgMapper;
    private FunWord word;
    private List<FunPronunciation>  pronunciation;
    private List<FunMeaning> means;
    private List<FunStage> stage;//阶段
    private List<FunInflection> tense;//时态
    private List<FunPhrase> phrase;//短语
    private List<FunExamplesentence> exampleSentence;//例句

//    @Autowired
    public FunWordMsg(FunWordMsgMapper wordMsgMapper){
        this.wordMsgMapper = wordMsgMapper;
    };
    @Autowired
    public FunWordMsg(){};


    public FunWordMsg init(String spell){
        if(wordMsgMapper ==null){
            System.out.println(">>>WordMsg>>>wordMapper==null");
            return this;
        }
        word= wordMsgMapper.GetWordBySpell(spell);
        if(word==null){
            System.out.println(">>>WordMsg>>>word==null");
            return this;
        }
        if(word.getWordId()==null){
            System.out.println(">>>WordMsg>>>word.getWordId()==null");
            return this;
        }
        long id= Long.parseLong(word.getWordId());
        init(id);
        return this;
    }

    public FunWordMsg init(long id){
        if(wordMsgMapper ==null){
            System.out.println(">>>WordMsg>>>wordMapper==null");
            return this;
        }
        word= wordMsgMapper.GetWord(id);
        if(word==null)return this;
        pronunciation= wordMsgMapper.GetPronunciation(id);
        means= wordMsgMapper.GetMeans(id);
        stage= wordMsgMapper.GetStage(id);
        tense= wordMsgMapper.GetTense(id);
        phrase= wordMsgMapper.GetPhrase(id);
        exampleSentence= wordMsgMapper.GetExamplesentence(id);
        return this;
    }
    @Transactional
    public void insert(){
        wordMsgMapper.InsertWord(word);
        for(FunPronunciation i : pronunciation) {
            System.out.println(">>>pronunciation"+i);;wordMsgMapper.InsertPronunciation(i);}
        for(FunMeaning i : means) wordMsgMapper.InsertMeans(i);
        for(FunStage i : stage) wordMsgMapper.InsertStage(i);
        for(FunInflection i : tense) wordMsgMapper.InsertTense(i);
        for(FunPhrase i : phrase) wordMsgMapper.InsertPhrase(i);
        for(FunExamplesentence i : exampleSentence) wordMsgMapper.InsertExamplesentence(i);
    }
}
