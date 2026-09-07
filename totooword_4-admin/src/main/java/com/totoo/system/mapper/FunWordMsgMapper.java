package com.totoo.system.mapper;


import com.totoo.system.domain.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FunWordMsgMapper {
    @Select("SELECT max(`word_id`) from Fun_Word")
    Integer GetWordIdMax();
    @Select("select * from Fun_Word where spell=#{spell}")
    FunWord GetWordBySpell(String spell);
    @Select("select * from Fun_Word where word_id=#{id}")
    FunWord GetWord(long id);
    @Insert("Insert into Fun_Word (word_Id,spell, language)values (#{wordId},#{spell},#{language} )")
    void InsertWord(FunWord word);
    @Select("select * from Fun_Pronunciation where word_id=#{id}")
    List<FunPronunciation> GetPronunciation(long id);
    @Insert("Insert into Fun_Pronunciation (word_Id,pronunciation_Type, transcription)values (#{wordId},#{pronunciationType},#{transcription} )")
    void InsertPronunciation(FunPronunciation pronunciation);
    @Select("select * from Fun_Meaning where word_id=#{id}")
    List<FunMeaning> GetMeans(long id);
    @Insert("Insert into Fun_Meaning (word_Id,meaning_Type, meaning_Definition)values (#{wordId},#{meaningType},#{meaningDefinition} )")
    void InsertMeans(FunMeaning means);
    @Select("select * from Fun_Stage where word_Id=#{id}")
    List<FunStage> GetStage(long id);
    @Insert("Insert into Fun_Stage (word_Id,stage_Text)values (#{wordId},#{stageText} )")
    void InsertStage(FunStage stage);
    @Select("select * from Fun_Inflection where word_Id=#{id}")
    List<FunInflection> GetTense(long id);
    @Insert("Insert into Fun_Inflection (word_Id,inflection_Type,inflection_Text)values (#{wordId},#{inflectionType},#{inflectionText} )")
    void InsertTense(FunInflection tense);
    @Select("select * from Fun_Phrase where word_Id=#{id}")
    List<FunPhrase> GetPhrase(long id);
    @Insert("Insert into Fun_Phrase (word_Id,phrase_Id,phrase_Text,phrase_Definition)values (#{wordId},#{phraseId},#{phraseText},#{phraseDefinition} )")
    void InsertPhrase(FunPhrase phrase);
    @Select("select * from Fun_Examplesentence where word_Id=#{id}")
    List<FunExamplesentence> GetExamplesentence(long id);
    @Insert("Insert into Fun_Examplesentence (word_Id,sentence_id,sentence_Text,sentence_Definition)values (#{wordId},#{sentenceId},#{sentenceText},#{sentenceDefinition} )")
    void InsertExamplesentence(FunExamplesentence examplesentence);

}

