# falsework

#### 项目介绍
1. 通用脚手架，用于代码生成
2. 生成的代码不限语言（java、C#、html、js）
3. 每个人都可以定制化自己的模块文件，然后快速生成脚手架
4. 架构师必备的脚手架工具，通过从数据库读取表，然后生成从数据库脚本、后端、前端、运维的一整套CRUD流程

#### 软件架构
freemarker

#### 使用说明
1. 配置
```
        /******* DB配置 ******/
        JdbcParam jdbcParam = new JdbcParam();                           
        jdbcParam.setUrl( "jdbc:mysql://127.0.0.1:3306/kf_db?useUnicode=true&characterEncoding=utf8" );
        jdbcParam.setDriver( "com.mysql.jdbc.Driver" );
        jdbcParam.setUsername( "root" );
        jdbcParam.setPassword( "root" );

        /******* Project配置 ******/
        Project proj = new Project();
        proj.setName( "test" );// 工程名
        proj.setBasepackage( "com.xm.test" );// 包名
        proj.setGroupId( "com.xm.test" );// 组名
        proj.setDatabaseName( "mysql" );// project要用的数据库类型。mysql,oracle
        proj.setPortalKey( "test" );// 门户标识
        proj.setPortalCnName( "管理后台" );// 门户名称
        proj.setSeqIdPrefix( "1000" ); //初始数据的ID前缀,这个一定要填写，请查询系统管理portal表，设置前缀值，比如1600
        proj.setStaticPathName( "test" );

        /******* 输入输出路径配置 ********/
        String templateRoot = "I:\\git\\self\\falsework\\falsework-template\\template"; //修改为你自己的本地模板路径
        String outputRoot = "I:\\git\\self\\autocode";
```
2. 运行factory的main

