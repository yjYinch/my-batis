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
    private String openToken;
    /**
     * 结束标记
     */
    private String closeToken;

    private TokenHandler handler;

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
     *
     * @param text
     * @return
     */
    public String parse(String text) {
        StringBuilder builder = new StringBuilder();
        if (text != null && text.length() > 0) {
            // 不考虑转义符号
            // 1. 判断是否有开始标记
            int start = text.indexOf(openToken, 0);
            if (start == -1) {
                return text;
            }
            int offset = 0;
            while (start > -1) {
                builder.append(text.substring(offset, start));
                offset = start + openToken.length();
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

//    /**
//     * select id from user where name = #{name}
//     * 1. 解析#{}和${}
//     * 2. 调用ParameterMappingTokenHandler的handlerToken方法处理结果
//     *
//     * @param text
//     * @return
//     */
//    public String parse(String text) {
//        if (text == null || text.isEmpty()) {
//            return "";
//        }
//        int offset = 0;
//        // int indexOf(String str, int fromIndex) : 返回指定字符在字符串中从指定位置开始后第一次出现的索引，如果没有，就返回-1
//        // 占位符位置的开始索引
//        int start = text.indexOf(openToken, offset);
//        // 当没有占位符时
//        if (start == -1) {
//            return text;
//        }
//        // 把text转为数组，然后找出占位符的变量，存储下来，然后将占位符中的变量替换成？
//        // 把text转成字符串数组src,并且定义默认偏移量offset=0,存储最终需要返回的字符串的变量builder
//        // text变量中占位符对应的变量名expression。判断start是否大于-1(即text中是否存在openToken)，如果存在则执行如下代码
//        char[] src = text.toCharArray();
//        final StringBuilder builder = new StringBuilder();
//        StringBuilder expression = null;
//        while (start > -1) {
//            //判断一下 ${ 前面是否是转义字符\，如果有，就不作为openToken处理
//            if (start > 0 && src[start - 1] == '\\') {
//
//                builder.append(src, offset, start - offset - 1).append(openToken);
//                offset = start + openToken.length();
//            } else {
//                if (expression == null) {
//                    expression = new StringBuilder();
//                } else {
//                    expression.setLength(0);
//                }
//                builder.append(src, offset, start - offset);
//                offset = start + openToken.length();
//                int end = text.indexOf(closeToken, offset);
//                while (end > -1) {
//                    if (end > offset && src[end - 1] == '\\') {
//                        expression.append(src, offset, end - offset - 1).append(closeToken);
//                    } else {
//                        expression.append(src, offset, end - offset);
//                        offset = end + closeToken.length();
//                        break;
//                    }
//                }
//
//                if (end == -1){
//                    builder.append(src, start, src.length - start);
//                    offset = src.length;
//                } else {
//                    builder.append(handler.handlerToken(expression.toString()));
//                    offset = end + closeToken.length();
//                }
//            }
//            start = text.indexOf(openToken, offset);
//        }
//        return builder.toString();
//    }


}
