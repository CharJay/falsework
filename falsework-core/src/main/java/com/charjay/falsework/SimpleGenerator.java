package com.charjay.falsework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.charjay.falsework.bean.Project;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.charjay.falsework.bean.Entity;
import com.charjay.falsework.bean.Module;
import com.charjay.falsework.bean.ModuleMethod;
import com.charjay.falsework.util.FreemarkerHelper;
import com.charjay.falsework.util.FileCompare;
import freemarker.template.TemplateException;

public class SimpleGenerator implements IGenerator {
    
    private static final Logger logger = LoggerFactory.getLogger( SimpleGenerator.class );

    
    @Override
    public void generate(Project project, String templateDir, String outputPath, String[] ignoreRegexs ) throws Exception {
        templateDir = templateDir.replace( "\\", "/" );
        outputPath = outputPath.replace( "\\", "/" );
        File rootDir = new File( templateDir );
        List<String> leftFiles = new ArrayList<>();
        List<String> dirFiles = new ArrayList<>();
        Map<String, String> stringTs = new HashMap<>();
        separate( rootDir, stringTs, dirFiles, leftFiles, templateDir );
        
        FreemarkerHelper helper = FreemarkerHelper.getInstance();
        try {
            helper.register( templateDir, stringTs );
        } catch (IOException e1) {
            logger.error( "注册freemarker conf出错", e1 );
            throw e1;
        }
        
        Map<String, Object> model = new HashMap<>();
        model.put( "project", project );
        
        List<Entity> entities = project.getEntities();
        for (Entity ent : entities) {
            logger.info( "-------------------start output for {}. ----------------------------", ent.getName() );
            model.put( "entity", ent );
            String basePackage = project.getBasepackage();
            String dirPackage = basePackage.replaceAll( "\\.", "/" );
            String processResult = null;
            for (String dir : dirFiles) { // 创建文件夹
                try {
                    processResult = helper.process2Str( templateDir, "dir_" + dir, model );
                    if (processResult != null) {
                        processResult = processResult.replaceAll( basePackage, dirPackage );
                        new File( outputPath + processResult ).mkdirs();
                    }
                } catch (IOException | TemplateException e) {
                    logger.error( "访问模板文件出错", e );
                    throw e;
                }
            }

            for (String f : leftFiles) {
                if (ignoreRegexs != null && ignoreRegexs.length >= 1) {
                    for (String reg : ignoreRegexs) {
                        if (f.matches( reg )) {
                            logger.info( "根据{}排除模板{}", reg, f );
                            continue;
                        }
                    }
                }
                try {
                    processResult = helper.process2Str( templateDir, "file_" + f, model );
                    
                    String fpath = outputPath + processResult;
                    fpath = fpath.replaceAll( basePackage, dirPackage );
                    //System.out.println( templateDir+" "+f );
                    processResult = helper.process2Str( templateDir, f, model );
                    String outStr = processResult;
                    File ff = new File( fpath );
                    if (ff.isFile()) {
                        String oldContentStr = null;
                        try (InputStream is = new FileInputStream( ff )) {
                            oldContentStr = IOUtils.toString( is, "UTF-8" );
                        }
                        if (processResult.equals( oldContentStr )) {
                            continue;
                        }
                        logger.info( "检查模板一致性：{}", f );
                        boolean templateEqual = FileCompare.check( processResult, oldContentStr );
                        if (!templateEqual) {
                            logger.warn( "旧文件的模板部分（{}）与当前模板（{}）不一致", fpath, templateDir + f );
                        }
                        outStr = FileCompare.mergeContent( processResult, oldContentStr );
                    }
                    try (FileWriter fw = new FileWriter( fpath )) {
                        fw.write( outStr );
                    }
                } catch (IOException | TemplateException e) {
                    logger.error( "访问模板文件出错", e );
                    throw e;
                }
            }
        }
        
        for (Entity ent : project.getDeletedEntities()) {
            model.put( "entity", ent );
            String basePackage = project.getBasepackage();
            String dirPackage = basePackage.replaceAll( "\\.", "/" );
            String processResult = null;
            for (String dir : dirFiles) { // 删除文件夹
                if (dir.contains( "${entity" ))
                    try {
                        processResult = helper.process2Str( templateDir, "dir_" + dir, model );
                        if (processResult != null) {
                            processResult = processResult.replaceAll( basePackage, dirPackage );
                            File _f = new File( outputPath + processResult );
                            if (_f.isDirectory())
                                FileUtils.deleteDirectory( _f );
                        }
                    } catch (IOException | TemplateException e) {
                        logger.error( "访问模板文件出错", e );
                        throw e;
                    }
            }
            
            for (String f : leftFiles) {
                if (f.contains( "${entity" ))
                    try {
                        processResult = helper.process2Str( templateDir, "file_" + f, model );
                        String fpath = outputPath + processResult;
                        fpath = fpath.replaceAll( basePackage, dirPackage );
                        File ff = new File( fpath );
                        
                        if (ff.isFile())
                            ff.delete();
                            
                    } catch (IOException | TemplateException e) {
                        logger.error( "访问模板文件出错", e );
                        throw e;
                    }
            }
        }
        
    }


    /**
     * 循环目录文件，进行分类
     * @param file
     * @param stringTs
     * @param dirFiles
     * @param leftFiles
     * @param remove
     */
    private void separate( File file, Map<String, String> stringTs, List<String> dirFiles, List<String> leftFiles,
                           String remove ) {
        String path = file.getAbsolutePath().replaceAll( "\\\\", "/" ).replace( remove, "" );
        if (file.isFile()) {
            stringTs.put( "file_" + path, path );
            leftFiles.add( path );
        } else if (file.isDirectory()) {
            stringTs.put( "dir_" + path, path );
            dirFiles.add( path );

            File[] subFiles = file.listFiles();
            for (File f : subFiles) {
                separate( f, stringTs, dirFiles, leftFiles, remove );
            }
        }
    }
}
