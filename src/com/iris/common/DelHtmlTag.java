package com.iris.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DelHtmlTag {
    // 定义script的正则表达式
    private static final String regEx_script = "<script[^]*?>[\\s\\S]*?<\\/script>";
    // 定义style的正则表达式
    private static final String regEx_style = "<style^]*?>[\\s\\S]*?<\\/style>";
    // 定义html的正则表达式
    private static final String regEx_html = "<[^>]+>";

    private static final String regEx_notbr = "<(?!\\/?br\\/?.+?>)[^<>]*>";
    private static final String regEx_br = "<br/>";
    private static final String regEx_p = "<p>";
    private static final String regExp = "</p>";
    public static String delHtmlTag(String htmlStr) {
        Pattern p_html = Pattern.compile(regEx_p, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        Pattern br_html = Pattern.compile(regEx_br, Pattern.CASE_INSENSITIVE);
        Matcher mbr_html = br_html.matcher(htmlStr);
        htmlStr = mbr_html.replaceAll(""); //过滤html标签

        Pattern pp_html = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
        Matcher mpp_html = pp_html.matcher(htmlStr);
        htmlStr = mpp_html.replaceAll("\n"); //过滤html标签

        htmlStr = filterDecode(htmlStr);
        return htmlStr.trim();
    }

    public static String filterDecode(String htmlStr) {
        htmlStr = htmlStr.replaceAll("&amp;", "&")
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&apos;", "\'")
                .replaceAll("&quot;", "\"")
                .replaceAll("&nbsp;", " ")
                .replaceAll("&copy;", "@")
                .replaceAll("&reg;", "?");
        return htmlStr;
    }
}
