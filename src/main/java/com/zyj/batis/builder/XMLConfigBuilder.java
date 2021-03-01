package com.zyj.batis.builder;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ha.DataSourceCreator;
import com.zyj.batis.entity.Configuration;
import com.zyj.batis.io.Resources;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author: zhangyijun
 * @date: created in 15:09 2021/2/28
 * @description 解析mybatis-config.xml封装成Configuration实体类
 */
public class XMLConfigBuilder {


    private Configuration configuration;

    public XMLConfigBuilder(){
        this.configuration = new Configuration();
    }

    /**
     * 解析配置文件，采用DOM4J解析xml
     * @param in
     * @return
     */
    @SuppressWarnings("unchecked")
    public Configuration parseConfig(InputStream in){
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(in);
            // 获取<Configuration>, configuration是根节点
            Element rootElement = document.getRootElement();
            // 获取<dataSource>的配置
            List<Element> list = rootElement.selectNodes("//property");

            Properties properties = new Properties();
            for (Element el : list) {
                properties.put(el.attributeValue("name"), el.attributeValue("value"));
            }
            //使用druid数据源连接池, 并赋值属性
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(properties.getProperty("driver"));
            druidDataSource.setUrl(properties.getProperty("url"));
            druidDataSource.setUsername(properties.getProperty("username"));
            druidDataSource.setPassword(properties.getProperty("password"));
            // 赋值给Configuration
            configuration.setDataSource(druidDataSource);

            // 解析<mappers>标签，<mapper>标签 --> 得到XxxMapper.xml路径
            List<Element> mappersNodes = rootElement.selectNodes("//mappers");
            for (Element mappersNode : mappersNodes) {
                List<Element> list1 = mappersNode.selectNodes("//mapper");
                for (Element element : list1) {
                    // 获取XxxMapper.xml的路径，这里面默认是类路径
                    String mapperXmlPath = element.attributeValue("resource");
                    InputStream mapperXmlPathStream = Resources.getResourcesAsStream(mapperXmlPath);
                    // 解析"XxxMapper.xml"配置文件
                    new XMLMapperBuilder(this.configuration).parseMapper(mapperXmlPathStream);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configuration;
    }
}
