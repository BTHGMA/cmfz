package com.baizhi.util;

import io.goeasy.GoEasy;

public class GoEasyUtils {
    public static void pushMessage(String channel,String content) {
        GoEasy goEasy = new GoEasy("https://rest-hangzhou.goeasy.io", "BC-fa47a684441642728a87d4adbadbde8d");
        goEasy.publish(channel, content);
    }
}
