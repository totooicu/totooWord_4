package com.totoo.system.tool;
//修改于2025.01.18

import com.totoo.system.domain.*;
import com.totoo.system.mapper.FunWordMsgMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.totoo.system.tool.StringOP.reGetMid;
import static java.lang.System.out;

public class WordPackage {

    FunWordMsgMapper wordMsgMapper;
    FunWordMsg wordMsg=new FunWordMsg();
    public String webpag;
    public String word;
    public String note;
    public String means;
    public String stage;//阶段
    public String tense;//时态
    public String phrase1;//短语
    public String exampleSentence;//例句
    public String phrase2;//短语
    public String pronunciation;
    public String lang="en";
    public ArrayList<String> ALmeans=new ArrayList<>();
    public ArrayList<String> ALstage=new ArrayList<>();//阶段
    public ArrayList<String> ALtense=new ArrayList<>();//时态
    public ArrayList<String> ALphrase1=new ArrayList<>();//短语
    public ArrayList<String> ALexampleSentence=new ArrayList<>();//例句
    public ArrayList<String> ALphrase2=new ArrayList<>();//短语
    public ArrayList<String> ALpronunciation=new ArrayList<>();
    public ArrayList<String> ALexampleSentenceMean=new ArrayList<>();
    public ArrayList<String> ALnote=new ArrayList<>();

    public int MaxWordID,MaxbookID,wordID;
    public WordPackage(FunWordMsgMapper wordMsgMapper,String spell,String lan){
        this.wordMsgMapper=wordMsgMapper;
        wordMsg.setWordMsgMapper(wordMsgMapper);
        word=spell;if(lan!=null)lang=lan;
    }

    public boolean existTheWord() {
        return wordMsgMapper.GetWordBySpell(word)!=null;
    }
    public boolean existTheWord(String word) {
        return wordMsgMapper.GetWordBySpell(word)!=null;
    }

    public void save2MySQL(){
        int cacheCount;
        showTheWord();
        int NowWordID;
        if (wordMsgMapper.GetWordIdMax()==null)NowWordID=1;
        else NowWordID=wordMsgMapper.GetWordIdMax()+1;
        out.println(">>>NowWordID "+NowWordID);
        FunWord word1=new FunWord();
        word1.setSpell(word);
        word1.setLanguage(lang);
        word1.setWordId(String.valueOf(NowWordID));
        wordMsg.setWord(word1);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int i=0;
        ArrayList<FunMeaning>means=new ArrayList<>();
        for(i=0;i<ALmeans.size();i++){
            FunMeaning means1=new FunMeaning();
            means1.setMeaningType(String.valueOf(i));
            means1.setMeaningDefinition(ALmeans.get(i));
            means1.setWordId(String.valueOf(NowWordID));
            means.add(means1);
            //            SQL.add("insert means values("+NowWordID+","+i+",'"+ ALmeans.get(i) +"');");
        }
        wordMsg.setMeans(means);
        ArrayList<FunStage>stages=new ArrayList<>();
        for(i=0;i<ALstage.size();i++){
            FunStage stage1=new FunStage();
            stage1.setWordId(String.valueOf(NowWordID));
            stage1.setStageText(ALstage.get(i));
            stages.add(stage1);
//            SQL.add("insert stage values("+NowWordID+",'"+ ALstage.get(i) +"');");
        }
        wordMsg.setStage(stages);
        ArrayList<FunInflection>tenses=new ArrayList<>();
        for(i=0;i<ALtense.size();i++){
            FunInflection tense1=new FunInflection();
            tense1.setWordId(String.valueOf(NowWordID));
            tense1.setInflectionType(ALtense.get(i++));
            tense1.setInflectionText(ALtense.get(i));
            tenses.add(tense1);
//            SQL.add("insert tense values("+NowWordID+",'"+ ALtense.get(i++) +"','"+ALtense.get(i)+"',-1);");
        }
        wordMsg.setTense(tenses);
        ArrayList<FunPhrase>phrases=new ArrayList<>();
        for(i=0;i<ALphrase1.size();i++){
            FunPhrase phrase=new FunPhrase();
            phrase.setWordId(String.valueOf(NowWordID));
            phrase.setPhraseId(String.valueOf(i/2));
            phrase.setPhraseText(ALphrase1.get(i++));
            phrase.setPhraseDefinition(ALphrase1.get(i));
            phrases.add(phrase);
            //            SQL.add("insert phrase values("+NowWordID+","+i/2+",'"+ ALphrase1.get(i++) +"','"+ALphrase1.get(i)+"');");
        }

        for(i=0;i<ALphrase2.size();i++){
            FunPhrase phrase=new FunPhrase();
            phrase.setWordId(String.valueOf(NowWordID));
            phrase.setPhraseId(String.valueOf(i/2+100));
            phrase.setPhraseText(ALphrase2.get(i++));
            phrase.setPhraseDefinition(ALphrase2.get(i));
            phrases.add(phrase);
//            SQL.add("insert phrase values("+NowWordID+","+(100+i/2)+",'"+ ALphrase2.get(i++) +"','"+ALphrase2.get(i)+"');");
        }
        wordMsg.setPhrase(phrases);
        ArrayList<FunExamplesentence>examplesentences=new ArrayList<>();
        for(i=0;i<ALexampleSentence.size();i++){
            FunExamplesentence examplesentence=new FunExamplesentence();
            examplesentence.setWordId(String.valueOf(NowWordID));
            examplesentence.setSentenceId(String.valueOf(i));
            examplesentence.setSentenceText(ALexampleSentence.get(i));
            examplesentence.setSentenceDefinition(ALexampleSentenceMean.get(i));
            examplesentences.add(examplesentence);
//            SQL.add("insert exampleSentence values("+NowWordID+","+i+",'"+ ALexampleSentence.get(i) +"','"+ALexampleSentenceMean.get(i)+"');");

        }
        wordMsg.setExampleSentence(examplesentences);
        ArrayList<FunPronunciation>pronunciations=new ArrayList<>();
        cacheCount=0;
        for(i=0;i<ALpronunciation.size();i++){
            FunPronunciation pronunciation1=new FunPronunciation();
            pronunciation1.setWordId(String.valueOf(NowWordID));

            pronunciation1.setPronunciationType(ALpronunciation.get(i++));
            pronunciation1.setTranscription(ALpronunciation.get(i));

            pronunciations.add(pronunciation1);
//            SQL.add("insert pronunciation values("+NowWordID+",'"+ALpronunciation.get(i++)+"','"+ ALpronunciation.get(i) +"');");
        }
        wordMsg.setPronunciation(pronunciations);

        out.println(wordMsg);
        wordMsg.insert();
    }


    public void showTheWord() {
        out.print(System.getProperty("file.encoding"));
        out.println(">>>单词：|"+word);
        out.println(">>>WID：|"+wordID);
        out.println(">>>发音：|"+pronunciation);
        out.println(">>>词性：|"+tense);
        out.println(">>>意思：|"+means);
        out.println(">>>阶段：|"+stage);
        out.println(">>>短语：|"+phrase1);
        out.println(">>>短语：|"+phrase2);
        out.println(">>>例句：|"+exampleSentence);
        out.println(">>>笔记：|"+note);

    }


    public void AL2STR(){
        means= String.valueOf(ALmeans);
        tense= String.valueOf(ALtense);
        stage= String.valueOf(ALstage);
        phrase1= String.valueOf(ALphrase1);
        exampleSentence="";
        if(ALexampleSentence.size()==ALexampleSentenceMean.size())
            for (int i = 0; i< ALexampleSentence.size(); i++)
                exampleSentence+= ALexampleSentence.get(i)+ALexampleSentenceMean.get(i);
        else exampleSentence=String.valueOf(ALexampleSentence);
        phrase2= String.valueOf(ALphrase2);
        pronunciation= String.valueOf(ALpronunciation);
        note="";
        if(ALnote.size()!=0)
            note= String.valueOf(ALnote.get(0));
    }


    public void Clear(){
//        word="";
        webpag="";
        note="";
        means="";
        stage="";//阶段
        tense="";//时态
        phrase1="";//短语
        exampleSentence="";//例句
        phrase2="";//短语
        pronunciation="";
        ALmeans=new ArrayList<>();
        ALstage=new ArrayList<>();//阶段
        ALtense=new ArrayList<>();//时态
        ALphrase1=new ArrayList<>();//短语
        ALexampleSentence=new ArrayList<>();//例句
        ALphrase2=new ArrayList<>();//短语
        ALpronunciation=new ArrayList<>();
        ALexampleSentenceMean=new ArrayList<>();
        ALnote=new ArrayList<>();
        MaxWordID=-1;MaxbookID=-1;
    }
    public void setmsg() {
        switch (lang){
            case "en":setEn();break;
            case "fr":setFr();break;
            case "ja":setJa();break;
            case "ko":setKo();break;
        }
    }

    private void setKo() {

        String data="data-v-6e652f7c";
        ArrayList<String>CacheLab;

        CacheLab=reGetMid(webpag,"<span class=\"kcPos\" data-v-15becdc4>","<ul data-v-15becdc4>");
        for(String i : CacheLab)ALmeans.add(StringOP.splitHtmlTagsContent(i)
                .stream().reduce("", (partialResult, element) -> partialResult + element));


//        CacheLab=reGetMid(webpag,"<div class=\"word-pos color_text_2\" "+data,"</div>");
//        for(String i : CacheLab)ALtense.addAll(StringOP.splitHtmlTagsContent(i));


        CacheLab=reGetMid(webpag,"<div class=\"label color_text_3\" "+data+">","</div>");
        for(String i : CacheLab)ALstage.addAll(StringOP.splitHtmlTagsContent(i));


        ALphrase1=reGetMid(webpag,"(<a class=\"point\" data-v-61ce6cc7>)|(<p class=\"sen-phrase\">)","(</a>)|(</p>)");
        ALphrase1.replaceAll(e->e.replaceAll("(<[^>]*>)|(<p class=)|(</l)",""));
        ALphrase1.replaceAll(e->e.replaceAll("'","’"));

        ALexampleSentence=reGetMid(webpag,"<div class=\"sen-eng\" data-v-2c51ff5d>","</div>");
        ALexampleSentence.replaceAll(e->e.replaceAll("<[^>]*>",""));
        ALexampleSentence.replaceAll(e->e.replaceAll("'","’"));

        ALexampleSentenceMean=reGetMid(webpag,"<div class=\"sen-ch\" data-v-2c51ff5d>","</div>");
        ALexampleSentenceMean.replaceAll(e->e.replaceAll("<[^>]*>",""));
        ALexampleSentenceMean.replaceAll(e->e.replaceAll("'","’"));

        ALphrase2=reGetMid(webpag,"(<a class=\"point\" data-v-61ce6cc7 data-v-534bdaa5>)|(<span class=\"phr_trans\" data-v-534bdaa5>)","(</a>)|(</span>)");
        ALphrase2.replaceAll(e->e.replaceAll("(<[^>]*>)|(<span class=)|(</l)",""));
        ALphrase2.replaceAll(e->e.replaceAll("'","’"));

        CacheLab=reGetMid(webpag,"<span data-v-6e652f7c>","</sup>");
        out.println(">>>CacheLab"+CacheLab);
        for(String i : CacheLab)ALpronunciation.addAll(reGetMid(i,">","<").stream()
                .map(s -> s.replaceAll("[><]", "")).collect(Collectors.toList()));
        CacheLab=reGetMid(webpag,"<span class=\"tone\" "+data+">","</span>");
        for(String i : CacheLab)ALpronunciation.addAll(StringOP.splitHtmlTagsContent(i));
        AL2STR();
    }

    private void setJa() {

        String data="data-v-6e652f7c";
        ArrayList<String>CacheLab;

        CacheLab=reGetMid(webpag,"<div class=\"each-sense\" "+data+">","</div></div></div>");
        for(String i : CacheLab)ALmeans.add(StringOP.splitHtmlTagsContent(i)
                .stream().reduce("", (partialResult, element) -> partialResult + element));


//        CacheLab=reGetMid(webpag,"<div class=\"word-pos color_text_2\" "+data,"</div>");
//        for(String i : CacheLab)ALtense.addAll(StringOP.splitHtmlTagsContent(i));


        CacheLab=reGetMid(webpag,"<div class=\"label color_text_3\" "+data+">","</div>");
        for(String i : CacheLab)ALstage.addAll(StringOP.splitHtmlTagsContent(i));


        ALphrase1=reGetMid(webpag,"(<a class=\"point\" data-v-61ce6cc7>)|(<p class=\"sen-phrase\">)","(</a>)|(</p>)");
        ALphrase1.replaceAll(e->e.replaceAll("(<[^>]*>)|(<p class=)|(</l)",""));
        ALphrase1.replaceAll(e->e.replaceAll("'","’"));

        ALexampleSentence=reGetMid(webpag,"<div class=\"sen-eng\" data-v-2c51ff5d>","</div>");
        ALexampleSentence.replaceAll(e->e.replaceAll("<[^>]*>",""));
        ALexampleSentence.replaceAll(e->e.replaceAll("'","’"));

        ALexampleSentenceMean=reGetMid(webpag,"<div class=\"sen-ch\" data-v-2c51ff5d>","</div>");
        ALexampleSentenceMean.replaceAll(e->e.replaceAll("<[^>]*>",""));
        ALexampleSentenceMean.replaceAll(e->e.replaceAll("'","’"));

        ALphrase2=reGetMid(webpag,"(<a class=\"point\" data-v-61ce6cc7 data-v-534bdaa5>)|(<span class=\"phr_trans\" data-v-534bdaa5>)","(</a>)|(</span>)");
        ALphrase2.replaceAll(e->e.replaceAll("(<[^>]*>)|(<span class=)|(</l)",""));
        ALphrase2.replaceAll(e->e.replaceAll("'","’"));

        CacheLab=reGetMid(webpag,"<span (class=\"tone\" )?"+data+">","</sup>");
        out.println(">>>CacheLab"+CacheLab);
        for(String i : CacheLab)ALpronunciation.addAll(reGetMid(i,">","<").stream()
                .map(s -> s.replaceAll("[><]", ""))
                .collect(Collectors.toList()));
        CacheLab.clear();
        for (int i = 0; i < ALpronunciation.size(); i++) {
            CacheLab.add(String.format("%02d", i / 2));
            CacheLab.add(ALpronunciation.get(i)+ALpronunciation.get(++i));
        }
        ALpronunciation=CacheLab;
        AL2STR();
    }

    private void setFr() {
        String data="data-v-203c6c3b";
        ArrayList<String>CacheLab;

        CacheLab=reGetMid(webpag,"<li class=\"word-exp\" "+data+">","</li>");
        for(String i : CacheLab)ALmeans.add(StringOP.splitHtmlTagsContent(i)
                .stream().reduce("", (partialResult, element) -> partialResult + element));


//        CacheLab=reGetMid(webpag,"<div class=\"word-pos color_text_2\" "+data,"</div>");
//        for(String i : CacheLab)ALtense.addAll(StringOP.splitHtmlTagsContent(i));


        CacheLab=reGetMid(webpag,"<div class=\"label color_text_3\" "+data+">","</div>");
        for(String i : CacheLab)ALstage.addAll(StringOP.splitHtmlTagsContent(i));


        ALphrase1=reGetMid(webpag,"(<a class=\"point\" data-v-61ce6cc7>)|(<p class=\"sen-phrase\">)","(</a>)|(</p>)");
        ALphrase1.replaceAll(e->e.replaceAll("(<[^>]*>)|(<p class=)|(</l)",""));
        ALphrase1.replaceAll(e->e.replaceAll("'","’"));

        ALexampleSentence=reGetMid(webpag,"<div class=\"sen-eng\" data-v-2c51ff5d>","</div>");
        ALexampleSentence.replaceAll(e->e.replaceAll("<[^>]*>",""));
        ALexampleSentence.replaceAll(e->e.replaceAll("'","’"));

        ALexampleSentenceMean=reGetMid(webpag,"<div class=\"sen-ch\" data-v-2c51ff5d>","</div>");
        ALexampleSentenceMean.replaceAll(e->e.replaceAll("<[^>]*>",""));
        ALexampleSentenceMean.replaceAll(e->e.replaceAll("'","’"));

        ALphrase2=reGetMid(webpag,"(<a class=\"point\" data-v-61ce6cc7 data-v-534bdaa5>)|(<span class=\"phr_trans\" data-v-534bdaa5>)","(</a>)|(</span>)");
        ALphrase2.replaceAll(e->e.replaceAll("(<[^>]*>)|(<span class=)|(</l)",""));
        ALphrase2.replaceAll(e->e.replaceAll("'","’"));

        CacheLab=reGetMid(webpag,"<span class=\"phonetic\" "+data+">","</span>");
        out.println(">>>CacheLab"+CacheLab);
        ALpronunciation.add("0");
        for(String i : CacheLab)ALpronunciation.addAll(reGetMid(i,">","<").stream()
                .map(s -> s.replaceAll("[><]", ""))
                .collect(Collectors.toList()));


        AL2STR();
    }


    private void setEn(){
        String data="data-v-d0c74fb2";
        ArrayList<String>CacheLab;

        CacheLab=reGetMid(webpag,"<li class=\"word-exp\" "+data+">","</li>");
        for(String i : CacheLab)ALmeans.add(StringOP.splitHtmlTagsContent(i)
                .stream().reduce("", (partialResult, element) -> partialResult + element));


        CacheLab=reGetMid(webpag,"<ul class=\"word-wfs-less\" "+data,"</li></ul>");
        for(String i : CacheLab)ALtense.addAll(StringOP.splitHtmlTagsContent(i));


        CacheLab=reGetMid(webpag,"<span class=\"exam_type-value\" "+data,"</span>");
        for(String i : CacheLab)ALstage.addAll(StringOP.splitHtmlTagsContent(i));


        ALphrase1=reGetMid(webpag,"(<a class=\"point\" data-v-61ce6cc7>)|(<p class=\"sen-phrase\">)","(</a>)|(</p>)");
        ALphrase1.replaceAll(e->e.replaceAll("(<[^>]*>)|(<p class=)|(</l)",""));
        ALphrase1.replaceAll(e->e.replaceAll("'","’"));

        ALexampleSentence=reGetMid(webpag,"<div class=\"sen-eng\" data-v-2c51ff5d>","</div>");
        ALexampleSentence.replaceAll(e->e.replaceAll("<[^>]*>",""));
        ALexampleSentence.replaceAll(e->e.replaceAll("'","’"));

        ALexampleSentenceMean=reGetMid(webpag,"<div class=\"sen-ch\" data-v-2c51ff5d>","</div>");
        ALexampleSentenceMean.replaceAll(e->e.replaceAll("<[^>]*>",""));
        ALexampleSentenceMean.replaceAll(e->e.replaceAll("'","’"));

        ALphrase2=reGetMid(webpag,"(<a class=\"point\" data-v-61ce6cc7 data-v-534bdaa5>)|(<span class=\"phr_trans\" data-v-534bdaa5>)","(</a>)|(</span>)");
        ALphrase2.replaceAll(e->e.replaceAll("(<[^>]*>)|(<span class=)|(</l)",""));
        ALphrase2.replaceAll(e->e.replaceAll("'","’"));

        CacheLab=reGetMid(webpag,"<div class=\"phone_con\" data-v-39fab836>","</a></div></div></div></div><!----></div></div><!----><!----></div></div>");
        for(String i : CacheLab)ALpronunciation.addAll(StringOP.splitHtmlTagsContent(i));

        AL2STR();

//    img=re(webpag,"<img data-v-91beca9c=\"\" src=\"[^(class=\"pic_dict\">)]*class=\"pic_dict\">");//从整页截取
//    img=img.replaceAll("((<img data-v-91beca9c=\"\" src=\")|( class=\"pic_dict\">))" ,"");

//        downLoadFromUrl df=new downLoadFromUrl("http://dict.youdao.com/dictvoice?type=0&audio="+word,word+"-英.mp3",pronunciationsLoad);
//        downLoadFromUrl df1=new downLoadFromUrl("http://dict.youdao.com/dictvoice?type=1&audio="+word,word+"-美.mp3",pronunciationsLoad);

    }
    public void getWordPage() throws IOException {
        Clear();
        word=word.replace(" ","%20");
        String load="https://www.youdao.com/result?word="+ URLEncoder.encode(word, "UTF-8")+"&lang="+lang;
//        load="https://www.youdao.com/result?word=%E6%85%8C%E3%81%97%E3%81%84&lang=ja";
        out.println("url>>>"+load);
        URL url= new URL(load);
        URLConnection conn=url.openConnection();//连接网络
        //创建对象去读取数据
        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String line;
        StringBuilder webpagsb=new StringBuilder();
        while((line=br.readLine())!=null){//每次读取一行
            webpagsb.append(line).append("\n");
        }
        br.close();
        webpag=webpagsb.toString();
        out.println("爬取成功"+word+lang+"=="+load);
//        wjcz.savePage(webpag,"E:\\学习产生的资料\\Sublime Text\\爬虫web.html");
    }

    public static void main(String[] args) throws IOException {
        {
            WordPackage wp = new WordPackage(null, "話", "ja");
            wp.getWordPage();
            fileIO fileIO = new fileIO();
            fileIO.write("E:\\StudyData\\IDEA\\totooProject\\VueCommunication\\src\\main\\java\\com\\totoo\\vuecommunication\\tool\\getWordPage.html", wp.webpag);
            wp.setmsg();
            wp.showTheWord();
        }{
            WordPackage wp=new WordPackage(null,"sujet de conversation","fr");
            wp.getWordPage();
            fileIO fileIO=new fileIO();
            fileIO.write("E:\\StudyData\\IDEA\\totooProject\\VueCommunication\\src\\main\\java\\com\\totoo\\vuecommunication\\tool\\getWordPage.html",wp.webpag);
            wp.setmsg();
            wp.showTheWord();
        }
    }
}
