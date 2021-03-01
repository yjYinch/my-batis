package com.zyj.batis.parsing;

/**
 * @author: zhangyijun
 * @date: created in 17:14 2021/2/28
 * @description 普通记号解析器，处理#{}和${}参数
 */
public class GenericTokenParser {
    /**
     * 开始标记
     */
    private final String openToken;
    /**
     * 结束标记
     */
    private final String closeToken;

    private final TokenHandler handler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    /**
     * 解析sql
     * 例如
     *      原语句text = select id from user where name = #{name} and age = #{age}
     *      输出sql = select id from user where name = ? and age = ?
     *      最后保存占位符中的值
     *      注意：sql语句中不能有转义符，因为该方法没有写支持转义符的操作
     * @param text sql语句
     * @return 编译的sql语句
     */
    public String parse(String text) {
        StringBuilder builder = new StringBuilder();
        if (text != null && text.length() > 0) {
            //  判断是否有开始标记
            int start = text.indexOf(openToken, 0);
            if (start == -1) {
                return text;
            }
            int offset = 0;
            while (start > -1) {
                builder.append(text.substring(offset, start));
                offset = start + openToken.length();
                // 判断结束标记
                int end = text.indexOf(closeToken, offset);
                if (end != -1) {
                    // #{}里面的值
                    String content = text.substring(offset, end);
                    builder.append(handler.handlerToken(content));
                    offset = end + closeToken.length();
                }
                start = text.indexOf(openToken, offset);
            }

        }
        return builder.toString();
    }

}
