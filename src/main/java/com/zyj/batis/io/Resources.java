package com.zyj.batis.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: zhangyijun
 * @date: created in 14:42 2021/2/28
 * @description 读取文件获得输入流
 */
public class Resources {

    private Resources(){
    }

    /**
     * 通过获得类加载器加载类路径下的配置文件mybatis-config.xml
     * @param resource
     * @return
     * @throws IOException
     */
    public static InputStream getResourcesAsStream(String resource) throws IOException {
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(resource);
        if (resourceAsStream == null){
            throw new IOException("Could not find resource " + resource);
        }
        return resourceAsStream;
    }
}
