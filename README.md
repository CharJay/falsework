# falsework

#### 项目介绍
1. 通用脚手架，用于代码生成
2. 生成的代码不限语言（java、C#、html、js）
3. 每个人都可以定制化自己的模块文件，然后快速生成脚手架
4. 架构师必备的脚手架工具，通过从数据库读取表，然后生成从数据库脚本、后端、前端、运维的一整套CRUD流程

#### 项目结构
1. falsework-core 脚手架核心程序
2. falsework-factory 脚手架工厂
3. falsework-template 脚手架模板

#### 软件架构
freemarker,spring

#### 使用说明
1. 配置

```java
CustomConfig.load("conf.properties");
/******* DB配置 ******/
JdbcParam jdbcParam = new JdbcParam();                           
jdbcParam.setUrl( CustomConfig.getString("jdbc.url") );
jdbcParam.setDriver( CustomConfig.getString("jdbc.driver") );
jdbcParam.setUsername( CustomConfig.getString("jdbc.username") );
jdbcParam.setPassword( CustomConfig.getString("jdbc.password") );

/******* Project配置 ******/
Project proj = new Project();
proj.setName( "test" );// 工程名
proj.setBasepackage( "com.xm.test" );// 包名
proj.setGroupId( "com.xm.test" );// 组名
proj.setDatabaseName( "mysql" );// project要用的数据库类型。mysql,oracle
proj.setPortalKey( "test" );// 门户标识
proj.setPortalCnName( "管理后台" );// 门户名称
proj.setSeqIdPrefix( "1000" ); //初始数据的ID前缀
proj.setStaticPathName( "test" );

/******* 输入输出路径配置 ********/
String templateRoot = CustomConfig.getString("template.input"); //修改为你自己的本地模板路径
String outputRoot = CustomConfig.getString("template.output");
/******* FalseWork ********/
String templateDir = templateRoot + File.separator + "springboot";
FalseWork t = new FalseWork( proj, jdbcParam, templateDir );
/****** 选择model获取方式 ********/
t.pullTables( new ArrayList<String>(), Arrays.asList( "t_%" ), null );// 从数据库pull表模型(表名模糊匹配pull，额外要pull的表，要排除的表)

/******* 定义输出路径 *********/
String outputPath = outputRoot + File.separator + proj.getName()+File.separator+proj.getName()+"-parent";// 约定以项目名作为开始目录。自定义根目录+项目名
t.exe( outputPath );// 生成基于数据库表的模板
System.out.println( "OK，输出路径："+outputPath );
```
2. 运行falsework-factory的main

