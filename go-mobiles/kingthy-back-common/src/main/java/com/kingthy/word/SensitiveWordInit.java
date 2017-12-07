package com.kingthy.word;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author xumin
 * @Description: 始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 * @DATE Created by 10:52 on 2017/5/17.
 * @Modified by:
 */
public class SensitiveWordInit {

    public HashMap sensitiveWordMap;

    public Map initKeyWord(Set<String> keyWordSet){
        try {

            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();    //关键字
            nowMap = sensitiveWordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if(wordMap != null){        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                }
                else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String,String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }
}
