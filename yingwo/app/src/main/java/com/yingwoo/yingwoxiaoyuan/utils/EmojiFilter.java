package com.yingwoo.yingwoxiaoyuan.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FJS0420 on 2016/9/10.
 */
public class EmojiFilter implements Filter {

    @Override
    public  String filter(String str) {

        if(str.trim().isEmpty()){
            return str;
        }
        String pattern="[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        String reStr="";
        Pattern emoji= Pattern.compile(pattern);
        Matcher emojiMatcher=emoji.matcher(str);
        str=emojiMatcher.replaceAll(reStr);
        return str;
    }

}