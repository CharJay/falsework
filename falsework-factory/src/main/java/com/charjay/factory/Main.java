package com.charjay.factory;

import com.charjay.falsework.FalseWork;
import com.charjay.falsework.bean.JdbcParam;
import java.util.Arrays;
import com.charjay.falsework.bean.Project;
import java.io.File;

import java.util.ArrayList;

public class Main {
    public static void main( String[] args ) throws Exception {
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
        proj.setSeqIdPrefix( "1000" ); //初始数据的ID前缀
        proj.setStaticPathName( "test" );

        /******* 输入输出路径配置 ********/
        String templateRoot = "I:\\git\\self\\falsework\\falsework-template\\template"; //修改为你自己的本地模板路径
        String outputRoot = "I:\\git\\self\\autocode";
        /******* FalseWork ********/
        String templateDir = templateRoot + File.separator + "springboot";
        FalseWork t = new FalseWork( proj, jdbcParam, templateDir );
        /****** 选择model获取方式 ********/
        t.pullTables( new ArrayList<String>(), Arrays.asList( "t_%" ), null );// 从数据库pull表模型(表名模糊匹配pull，额外要pull的表，要排除的表)

        /******* 定义输出路径 *********/
        String outputPath = outputRoot + File.separator + proj.getName()+File.separator+"server-code";// 约定以项目名作为开始目录。自定义根目录+项目名
        t.exe( outputPath );// 生成基于数据库表的模板
        System.out.println( "OK，输出路径："+outputPath );
    
    }
}
