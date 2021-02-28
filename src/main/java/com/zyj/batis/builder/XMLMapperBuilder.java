package com.zyj.batis.builder;

import com.zyj.batis.entity.Configuration;
import com.zyj.batis.entity.MappedStatement;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author: zhangyijun
 * @date: created in 16:02 2021/2/28
 * @description
 */
public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析XxxMapper.xml配置文件
     *
     * @param in
     */
    public void parseMapper(InputStream in) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(in);
            // 获取根节点<Mapper>
            Element root = document.getRootElement();
            // 获取对应mapper的类路径
            String namespace = root.attributeValue("namespace");
            // 获得子标签<select>
            List<Element> selectNodeList = root.selectNodes("//select");
            // 遍历，然后将mapper中的属性赋值给Configuration
            for (Element el : selectNodeList) {
                MappedStatement mappedStatement = new MappedStatement();
                String id = el.attributeValue("id");
                String paramType = el.attributeValue("paramType");
                String resultType = el.attributeValue("resultType");
                String sql = el.getTextTrim();
                mappedStatement.setId(id);
                mappedStatement.setParamType(paramType);
                mappedStatement.setResultType(resultType);
                mappedStatement.setSql(sql);
                String keyStatementId = namespace + "." + id;
                this.configuration.getMappedStatements().put(keyStatementId, mappedStatement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
