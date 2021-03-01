# my-batis

此项目是一个简单的查询功能的mybatis框架：

主要流程：

	1. 加载配置文件`mybatis-config.xml`生成输入流
 	2. `SqlSessionFactoryBuilder`创建`SqlSessionFactory`，解析配置文件`mybatis-config.xml`和关联的`XxxMapper.xml`
 	3. `SqlSessionFactory`调用openSession()方法生成SqlSession对象
 	4. 创建mapper代理对象
 	5. 反射调用mapper方法，最终去调用`selectList`方法
 	6. 封装结果集