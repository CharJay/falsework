package com.charjay.factory;

import com.charjay.falsework.FalseWork;
import com.charjay.falsework.bean.JdbcParam;
import com.charjay.falsework.bean.Project;
import com.charjay.falsework.util.CustomConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Wbc {
    public static void main( String[] args ) throws Exception {
        CustomConfig.load("conf.properties");
        /******* DB配置 ******/
        JdbcParam jdbcParam = new JdbcParam();                           
        jdbcParam.setUrl( CustomConfig.getString("jdbc.url") );
        jdbcParam.setDriver( CustomConfig.getString("jdbc.driver") );
        jdbcParam.setUsername( CustomConfig.getString("jdbc.username") );
        jdbcParam.setPassword( CustomConfig.getString("jdbc.password") );

        /******* Project配置 ******/
        Project proj = new Project();
        proj.setName( "wbc" );// 工程名
        proj.setBasepackage( "com.xm.wbc" );// 包名
        proj.setGroupId( "com.xm.wbc" );// 组名
        proj.setDatabaseName( "mysql" );// project要用的数据库类型。mysql,oracle
        proj.setPortalKey( "wbc" );// 门户标识
        proj.setPortalCnName( "管理后台" );// 门户名称
        proj.setSeqIdPrefix( "1000" ); //初始数据的ID前缀
        proj.setStaticPathName( "wbc" );

        /******* 输入输出路径配置 ********/
        String templateRoot = CustomConfig.getString("template.input"); //修改为你自己的本地模板路径
        String outputRoot = CustomConfig.getString("template.output");
        /******* FalseWork ********/
        String templateDir = templateRoot + File.separator + "springboot";
        FalseWork t = new FalseWork( proj, jdbcParam, templateDir );
        /****** 选择model获取方式 ********/
        t.pullTables( new ArrayList<String>(), Arrays.asList( "wbc_%" ), null );// 从数据库pull表模型(表名模糊匹配pull，额外要pull的表，要排除的表)

        /******* 定义输出路径 *********/
        String outputPath = outputRoot + File.separator + proj.getName()+File.separator+proj.getName()+"-parent";// 约定以项目名作为开始目录。自定义根目录+项目名
        t.exe( outputPath );// 生成基于数据库表的模板
        System.out.println( "OK，输出路径："+outputPath );
    
    }
}
