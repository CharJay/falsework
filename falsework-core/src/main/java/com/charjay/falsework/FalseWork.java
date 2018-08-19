package com.charjay.falsework;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.charjay.falsework.bean.Project;
import com.charjay.falsework.util.CustomFileUtils;
import com.charjay.falsework.bean.Entity;
import com.charjay.falsework.bean.JdbcParam;
import com.charjay.falsework.bean.Table;
import com.charjay.falsework.util.MetadataUtils;

public class FalseWork {
    
    /**
     * 项目信息
     */
    private Project project;
    /**
     * DB连接参数
     */
    private JdbcParam    jdbcParam;
    /**
     * 模板文件夹路径
     */
    private String       templateDir;
    private String       templateMethodDir;
                         
    private IGenerator   generator    = new SimpleGenerator();
                                      
    public static String rootDir      = System.getProperty( "user.home" ) + File.separator + "com/charjay/falsework";

                                      
    private String[]     ignoreRegexs;
                         
    public FalseWork(Project project, JdbcParam jdbcParam, String templateDir ) {
        this.project = project;
        this.jdbcParam = jdbcParam;
        this.templateDir = templateDir;
    }

    /**
     * 执行生成器之前备份旧的代码
     * @param backupDir
     * @throws IOException
     */
    public void backup( String backupDir ) throws IOException {
        File file = new File( backupDir );
        if (file.exists()) {
            File[] odir = file.listFiles( new FilenameFilter() {
                
                @Override
                public boolean accept( File dir, String name ) {
                    return name.startsWith( project.getName() );
                }
            } );
            for (File dir : odir) {
                CustomFileUtils.copyDirectory( dir, new File( rootDir + File.separator + "backup" + File.separator
                        + dir.getName() + new SimpleDateFormat( "yyMMddHHmmss" ).format( new Date() ) ), new FileFilter() {
                            
                            @Override
                            public boolean accept( File pathname ) {
                                if (!pathname.exists()) {
                                    return false;
                                }
                                if (pathname.isDirectory()) {
                                    return !pathname.getName().equals( "target" ) && !pathname.getName().startsWith( "." );// 排除.开头的文件夹和target文件夹
                                }
                                if (pathname.isFile()) {
                                    return !pathname.getName().startsWith( "." );// 排除.开头的文件
                                }
                                return false;
                            }
                        } );
            }
        }
    }

    /**
     * 执行生成器
     * @param outputPath
     * @throws Exception
     */
    public void exe( String outputPath ) throws Exception {
        generator.generate( project, templateDir, outputPath, ignoreRegexs );
    }

    /**
     * 从数据库pull表模型(表名模糊匹配pull，额外要pull的表，要排除的表)
     * @param likes 模糊匹配
     * @param include 额外要pull的表
     * @param exclude 要排除的表
     * @throws SQLException
     */
    public void pullTables( List<String> likes, List<String> include, List<String> exclude ) throws SQLException {
        List<Table> tables = MetadataUtils.findTables( jdbcParam, likes, include, exclude );
        
        List<Entity> entities = new ArrayList<Entity>();
        
        for (Table t : tables) {
            Entity e = new Entity( t );
            entities.add( e );
        }
        project.setEntities( entities );
        
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject( Project project ) {
        this.project = project;
    }
    
    public JdbcParam getJdbcParam() {
        return jdbcParam;
    }
    
    public void setJdbcParam( JdbcParam jdbcParam ) {
        this.jdbcParam = jdbcParam;
    }
    
    public String getTemplateDir() {
        return templateDir;
    }
    
    public void setTemplateDir( String templateDir ) {
        this.templateDir = templateDir;
    }
    
    public IGenerator getGenerator() {
        return generator;
    }
    
    public void setGenerator( IGenerator generator ) {
        this.generator = generator;
    }
    
    public String[] getIgnoreRegexs() {
        return ignoreRegexs;
    }
    
    public void setIgnoreRegexs( String[] ignoreRegexs ) {
        this.ignoreRegexs = ignoreRegexs;
    }
    
    public String getTemplateMethodDir() {
        return templateMethodDir;
    }
    
    public void setTemplateMethodDir( String templateMethodDir ) {
        this.templateMethodDir = templateMethodDir;
    }
    
}
