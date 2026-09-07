package com.totoo.system.tool;

import java.util.*;
import java.util.stream.Collectors;

public class WordStatistics {
    public String context;
    public String[] split;
    public HashMap<String, Integer> statistics;
    public WordStatistics(String context){
        this.context=context;
    }
//    public WordStatistics Split(String split_token){
//        split=context.split(split_token);
//        return this;
//    }
    public WordStatistics Split(String split_token){
        context=context.toLowerCase();
        context = context.replaceAll("(?<![a-zA-Z])[" + split_token + "]|[^a-zA-Z" + split_token + "]", split_token);
        split = context.split(split_token+"+");
        System.out.println(">>>WordStatistics>>>Split"+ Arrays.toString(split));
        Arrays.stream(split).filter(Objects::nonNull).toArray(String[]::new);
        return this;
}

    public WordStatistics Statistics(){
        statistics=new HashMap<String,Integer>();
        for(String w : split){
            if(statistics.get(w)==null){
                statistics.put(w,1);
            }else{
                statistics.put(w,statistics.get(w)+1);
            }
        }
        return this;
    }
    //按词性分类统计词频
    public WordStatistics StatisticsWithClassify(){
        statistics=new HashMap<String,Integer>();
        String[] classify=WordLemmatization.get(split);
//        System.out.println(classify);

        for(String w : classify){
            if(w==null)continue;
            if(w.equals(""))continue;
//            System.out.println(">>>"+w);
            if(statistics.get(w)==null){
                statistics.put(w,1);
            }else{
                statistics.put(w,statistics.get(w)+1);
            }
        }
        return this;
    }

    public WordStatistics getTop(int limit){
        Map<String, Integer> top20 = statistics.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        statistics = (HashMap<String, Integer>) top20;
        return this;
    }

    public class getKeysValuesResult{
        List<String> keys;
        List<Integer> values;
        getKeysValuesResult(){}
        getKeysValuesResult(List<String> key, List<Integer> value){
            this.keys = key;
            this.values = value;
        }
    }
public Object getKeysValues(){
    List<String> keys = new ArrayList<>(statistics.size());
    List<Integer> values = new ArrayList<>(statistics.size());

    for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
        keys.add(entry.getKey());
        values.add(entry.getValue());
    }

    return new getKeysValuesResult(keys, values);
}


    public  TreeMap SortByWord(){
        return new TreeMap(statistics);
    }
    public TreeMap SortByCount(){
        TreeMap<Integer, ArrayList<String>> cache=new TreeMap();
        for(String w:statistics.keySet()){
            int count=statistics.get(w);
            if(cache.get(count)==null)cache.put(count,new ArrayList<String>());
            cache.get(count).add(w);
        }
        return cache;
    }


    public static WordStatistics Init(String context){
        return new WordStatistics(context);
    }

}



