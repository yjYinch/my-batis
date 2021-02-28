package com.zyj.batis.parsing;

import com.zyj.batis.mapping.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangyijun
 * @date: created in 17:10 2021/2/28
 * @description
 */
public class ParameterMappingTokenHandler implements TokenHandler{

    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    @Override
    public String handlerToken(String content) {
        parameterMappingList.add(buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content){
        return new ParameterMapping(content);
    }

    public List<ParameterMapping> getParameterMappingList(){
        return parameterMappingList;
    }
}
