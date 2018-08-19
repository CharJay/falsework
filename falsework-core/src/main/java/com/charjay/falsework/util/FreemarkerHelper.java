package com.charjay.falsework.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class FreemarkerHelper {
    
    private static FreemarkerHelper          instance = null;
    
    private final Map<String, Configuration> confs    = new HashMap<String, Configuration>(); ;
    
    private FreemarkerHelper() {
    }
    
    public static FreemarkerHelper getInstance() {
        if (instance == null) {
            instance = new FreemarkerHelper();
        }
        return instance;
    }
    
    /**
     * @param root
     * @param templateName
     *            模板名称默认为根目录之后的路径
     * @throws IOException
     * @throws TemplateException
     */
    public String process2Str( String root, String templateName, Map<String, Object> dataModel ) throws IOException,
            TemplateException {
        if (root == null) {
            throw new NullPointerException( "模板根目录不能为null" );
        }
        if (templateName == null) {
            throw new NullPointerException( "模板名称不能为null" );
        }
        
        Configuration conf = confs.get( root );
        if (conf == null) {
            throw new IllegalStateException( "该root未注册控制器" );
        }
        
        StringWriter out = new StringWriter();
        conf.getTemplate( templateName ).process( dataModel, out );
        return out.toString();
    }
    
    public void register( String root, Map<String, String> stringTemplates ) throws IOException {
        
        List<File> files = new ArrayList<File>();
        files.add( new File( root ) );
        Configuration conf = newFreeMarkerConfiguration( files, "UTF-8", stringTemplates );
        confs.put( root, conf );
    }
    
    // //
    private static Configuration newFreeMarkerConfiguration( List<File> templateRootDirs, String defaultEncoding,
            Map<String, String> stringTemplate ) throws IOException {
        Configuration conf = new Configuration( Configuration.VERSION_2_3_22 );
        List<TemplateLoader> templateLoaders = new ArrayList<>();
        for (int i = 0; i < templateRootDirs.size(); i++) {
            templateLoaders.add( new FileTemplateLoader( templateRootDirs.get( i ) ) );
        }
        if (stringTemplate != null && !stringTemplate.isEmpty()) {
            StringTemplateLoader stl = new StringTemplateLoader();
            for (Map.Entry<String, String> e : stringTemplate.entrySet()) {
                stl.putTemplate( e.getKey(), e.getValue() );
            }
            templateLoaders.add( stl );
        }
        MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader( templateLoaders.toArray( new TemplateLoader[0] ) );
        
        conf.setTemplateLoader( multiTemplateLoader );
        conf.setNumberFormat( "###############" );
        conf.setBooleanFormat( "yes, no" );
        conf.setDefaultEncoding( defaultEncoding );
        
        // String autoIncludes = new File(new File(templateName).getParent(),"macro.include").getPath();
        // List<String> availableAutoInclude = FreemarkerHelper.getAvailableAutoInclude(conf, Arrays.asList("macro.include",autoIncludes));
        // conf.setAutoIncludes(availableAutoInclude);
        // GLogger.info("[set Freemarker.autoIncludes]"+availableAutoInclude+" for templateName:"+templateName);
        
        // List<String> autoIncludes = getParentPaths(templateName,"macro.include");
        // List<String> availableAutoInclude = FreemarkerHelper.getAvailableAutoInclude(conf,autoIncludes);
        // conf.setAutoIncludes(availableAutoInclude);
        // CustomLogUtils.debug(FreemarkerHelper.class, "set Freemarker.autoIncludes:"+availableAutoInclude+" for templateName:"+templateName+" autoIncludes:"+autoIncludes);
        return conf;
    }
    
    public static void main( String[] args ) throws Exception {
        List<File> files = new ArrayList<File>();
        files.add( new File( "D:\\temp\\src" ) );
        Map<String, String> stringTemplate = new HashMap<String, String>();
        stringTemplate.put( "src", "${src}" );
        Configuration conf = newFreeMarkerConfiguration( files, "UTF-8", stringTemplate );
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put( "src", "abc" );
        StringWriter w = new StringWriter();
        conf.getTemplate( "src" ).process( dataModel, w );
        w.append( "\n" );
        conf.getTemplate( "src\\main\\java\\com\\base\\core\\ctrl\\BaseSerServiceHelper.java" ).process( dataModel, w );
        System.out.println( w.toString() );
        
    }
}
