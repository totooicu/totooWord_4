package com.totoo.system.tool;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;
/**
 * 这个类展示了如何使用Stanford CoreNLP进行词形还原。
 */
public class WordLemmatization {
    public static String[] get(String[] words) {
        String[] ans=new String[words.length];
        // 设置Stanford CoreNLP的属性，指定使用的工具：分词、句子切分、词性标注和词形还原
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma");

        // 创建Stanford CoreNLP的处理管道
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        int ii=0;
        // 遍历单词列表，对每个单词进行词形还原处理
        for (String word : words) {
            // 创建一个Annotation对象，并填充单词文本
            Annotation annotation = new Annotation(word);

            // 使用pipeline对Annotation进行处理，进行词性标注和词形还原
            pipeline.annotate(annotation);

            // 获取处理后的句子列表
            List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

            // 检查是否成功找到句子
            if (sentences == null) {
//                System.err.println("No sentences found in the input.");
                continue; // 如果没有找到句子，跳过当前单词的处理
            }

            // 遍历句子中的每个单词，获取并打印词形还原结果
            for (CoreMap sentence : sentences) {

                for (CoreMap token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                    String originalWord = token.get(CoreAnnotations.TextAnnotation.class);
                    String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                    String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);

                    ans[ii]=lemma;

                }
            }
            ii+=1;
        }
        return ans;
    }

    public static void main(String[] args) {
        String[] words = {"input", "inputs", "inputted", "output", "outputs", "result", "take", "taken", "real", "reals", "reis", "really"};
        String[] ans=get(words);
        for(int i=0;i<ans.length;i++){
            System.out.println(ans[i]);
        }
    }
}

