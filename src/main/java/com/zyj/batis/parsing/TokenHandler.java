package com.zyj.batis.parsing;

/**
 * @author: zhangyijun
 * @date: created in 17:05 2021/2/28
 * @description 占位符解析器定义
 */
public interface TokenHandler {
    /**
     * 处理记号
     * @param content
     * @return
     */
    String handlerToken(String content);
}
