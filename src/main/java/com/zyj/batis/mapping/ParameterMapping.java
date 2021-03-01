package com.zyj.batis.mapping;

/**
 * @author: zhangyijun
 * @date: created in 17:07 2021/2/28
 * @description 参数映射，参数中属性值#{}中的具体值
 */
public class ParameterMapping {
    private final String content;

    public ParameterMapping(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }
}
