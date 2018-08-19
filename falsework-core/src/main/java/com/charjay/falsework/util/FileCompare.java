package com.charjay.falsework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCompare {
    
    //package 开头，判断是java文件。合并import , 找到第一个public结束
    
    //u-insert#sdd#
    //aabbcc
    //u-insert ->sdd->\r\n //aabbcc\r\n // 
    
    // u-insert#aaa#aabbccddu-insert#->aaa->aabbccdd
    
    //如果old内容是空的，new里是非空的，用new替换old的数据
    
    //2016-2-17 by qsd
    //固有类中的垂直增量，先普通增量，后垂直增量，保证垂直增量中的普通增量数据不会被覆盖。
    // auto-insert##
    // auto-insert-method#getXXXX#
    // auto-insert-method#
    // auto-insert-method#getXyyy#
    // auto-insert-method#
    // auto-insert##
    
    private static Logger log                             = LoggerFactory.getLogger( FileCompare.class );

    
    /**
     * @param newContent 新生成的文件
     * @param oldContent 旧有文件
     */
    public static String mergeContent( String newContent, String oldContent ) {
        if(oldContent==null || oldContent.trim().length()==0) return newContent;
        if(oldContent.contains( "private String testStr2")){
            System.out.println("=====================");
            System.out.println(newContent);
            System.out.println(oldContent);
        }
        StringBuffer newcc = new StringBuffer();
        
        ////java文件  import
        if(newContent.startsWith( "package" )){
            if(!oldContent.startsWith( "package" )){
                log.error( "oldContent package  no found! " );
            }else{
                Map<String, String> importMap = new HashMap<String, String>();
                StringBuffer newEnd = new StringBuffer();
                
                int newPublicIndex = newContent.indexOf( "public" );
                if(newPublicIndex == -1 ){
                    throw new RuntimeException("oldContent.indexOf( 'public' ) == -1");
                }
                
                String newHead = newContent.substring( 0 , newPublicIndex  );
                String newHeads[] = newHead.split( "\r|\n|(\r\n)" );
                for (int i = 0; i < newHeads.length; i++) {
                    if(i==0) {
                        newcc.append( newHeads[i] ).append( "\n" ).append( "\n" );
                    } else if(newHeads[i].trim().startsWith( "import" )){
                        importMap.put( newHeads[i].replaceAll( "\\s", "" ), newHeads[i] );
                    } else if(newHeads[i].trim().length()!=0){
                        newEnd.append( newHeads[i] ).append( "\n" );
                    }
                }
                newContent = newContent.substring( newPublicIndex );
                
                
                int oldPublicIndex = oldContent.indexOf( "public" );
                if(oldPublicIndex == -1 ){
                    throw new RuntimeException("oldContent.indexOf( 'public' ) == -1");
                }
                
                String oldHead = oldContent.substring( 0 , oldContent.indexOf( "public" ) );
                String oldHeads[] = oldHead.split( "\r|\n|(\r\n)" );
                for (int i = 1; i < oldHeads.length; i++) {
                    if(oldHeads[i].trim().startsWith( "import" )){
                        importMap.put( oldHeads[i].replaceAll( "\\s", "" ), oldHeads[i] );
                    }
                }
                oldContent = oldContent.substring( oldPublicIndex );
                
                //在第三行放入oldtmp的import
                Iterator<Entry<String, String>> it = importMap.entrySet().iterator();
                while(it.hasNext()){
                    Entry<String, String> en = it.next();
                    newcc.append( en.getValue() ).append( "\n" );
                }
                
                //放入剩余new的import内容
                newcc.append("\n").append( newEnd );
            }
        }

        //
        Map<String, String> newUInsertContent1 = new HashMap<String, String>();
        String[] newUinsertSplit1 = newContent.split( "u-insert#" );
        List<String> tagInspect = new ArrayList<>();
        for (int i = 1; i < newUinsertSplit1.length; i+=2) {
            int tmpIndex = newUinsertSplit1[i].indexOf( "#" );
//            if(tmpIndex == newUinsertSplit1[i].length() - 1){
//                continue;
//            }
            String tag = newUinsertSplit1[i].substring( 0 , tmpIndex );
            // tag inspect
            if(tagInspect.indexOf(tag) != -1) {
                log.error( "模板中u-insert#{}#重复,请检查!!!!!!", tag );
                throw new RuntimeException("u-insert#重复");
            }
            tagInspect.add(tag);
            
            String ifvar = newUinsertSplit1[i].substring(tmpIndex+1);
            int ifvar_rn = ifvar.lastIndexOf( "\n" );
            if(ifvar_rn != -1){
                ifvar = ifvar.substring( 0, ifvar_rn );
            }
            if(!ifvar.matches( "\\s*(#|//|(\\*/\\s*/\\*)|(-->\\s*<!--))?\\s*" )){//正则表示：两边可以有空白，中间可以是"#","//","-->可以有空白<!--","*/可以有空白/*"
                newUInsertContent1.put( tag, newUinsertSplit1[i] );
            }
        }
        
        //u-insert#xxx#
        //u-insert#
        //把old的东西取出来，放入new中
        Map<String, String> oldUInsertContent = new HashMap<String, String>();
        String[] oldUinsertSplit = oldContent.split( "u-insert#" );
        for (int i = 1; i < oldUinsertSplit.length; i+=2) {
            int tmpIndex = oldUinsertSplit[i].indexOf( "#" );
//            if(tmpIndex == oldUinsertSplit[i].length() - 1){
//                continue;
//            }
            String tag;
            try {
                tag = oldUinsertSplit[i].substring( 0 , tmpIndex );
            } catch (Exception e) {
                e.printStackTrace();
                log.error( "旧文件内的标签被错误的改动或出现重复标签！！！！！！！！！！" );
                continue;
            }
            
            //如果old内容是空的，new里是非空的，用new替换old的数据
            String ifvar = oldUinsertSplit[i].substring(tmpIndex+1);
            int ifvar_rn = ifvar.lastIndexOf( "\n" );
            if(ifvar_rn != -1){
                ifvar = ifvar.substring( 0, ifvar_rn );
            }
            if(ifvar.matches( "\\s*(#|//|(\\*/\\s*/\\*)|(-->\\s*<!--))?\\s*" ) && newUInsertContent1.get( tag ) != null){
                oldUinsertSplit[i] = newUInsertContent1.get( tag );
            }
            
            oldUInsertContent.put( tag, "u-insert#"+oldUinsertSplit[i]+"u-insert#" );
        }
        
        String[] newUinsertSplit = newContent.split( "u-insert#" );
        for (int i = 0; i < newUinsertSplit.length; i++) {
            if(i%2==0){
                newcc.append( newUinsertSplit[i] );
            }else{
                int tmpIndex = newUinsertSplit[i].indexOf( "#" );
                String tag = newUinsertSplit[i].substring( 0 , tmpIndex );
                
                String tmp = oldUInsertContent.get( tag );
                newcc.append( tmp == null?  "u-insert#"+newUinsertSplit[i]+"u-insert#" :tmp );
            }
        }
        
        return newcc.toString();
    }

    /**
     * @param newContent 新生成的文件
     * @param oldContent 旧有文件
     * @return 返回内容，比对整合后新文件的数据
     */
    public static boolean check( String newContent, String oldContent ) {
        
        if(oldContent==null || oldContent.trim().length()==0) return true;
        
        StringBuffer oldcc = new StringBuffer();
        StringBuffer newcc = new StringBuffer();
        
        //import
        if(newContent.startsWith( "package" )){
            
            if(!oldContent.startsWith( "package" )){
                log.error( "oldContent package  no found!" );
                return false;
            }else{
                int oldPublicIndex = oldContent.indexOf( "public" );
                if(oldPublicIndex == -1 ){
                    throw new RuntimeException("oldContent.indexOf( 'public' ) == -1");
                }
                
                
                String oldHead = oldContent.substring( 0 , oldPublicIndex );
                String oldHeads[] = oldHead.split( "\r|\n|(\r\n)" );
                for (int i = 0; i < oldHeads.length; i++) {
                    if(!oldHeads[i].trim().startsWith( "import" )){
                        oldcc.append( oldHeads[i].replaceAll( "\\s", "" ) );
                    }
                }
                oldContent = oldContent.substring( oldPublicIndex );
                
                
                int newPublicIndex = newContent.indexOf( "public" );
                if(newPublicIndex == -1 ){
                    throw new RuntimeException("newContent.indexOf( 'public' ) == -1");
                }
    
                String newHead = newContent.substring( 0 , newPublicIndex  );
                String newHeads[] = newHead.split( "\r|\n|(\r\n)" );
                for (int i = 0; i < newHeads.length; i++) {
                    if(!newHeads[i].trim().startsWith( "import" )){
                        newcc.append( newHeads[i].replaceAll( "\\s", "" ) );
                    }
                }
                newContent = newContent.substring( newPublicIndex );
            
            }
        }
        
        //u-insert#xxx#
        //u-insert#
        String[] newUinsertSplit = newContent.split( "u-insert#" );
        for (int i = 0; i < newUinsertSplit.length; i+=2) {
            newcc.append( newUinsertSplit[i] );
        }
        
        String[] oldUinsertSplit = oldContent.split( "u-insert#" );
        for (int i = 0; i < oldUinsertSplit.length; i+=2) {
            oldcc.append( oldUinsertSplit[i] );
        }
        
       
        if (newcc.toString().equals( oldcc.toString() )){
            log.info( "[旧文件与模板一致] " );
            return true;
        } else {
            log.warn( "[警告！！ 文件与母板不一致] " );
            return false;
        }
        
    }
    
    public static void main( String[] args ) {
        boolean b = "".matches( "\\s*(#|//|(\\*/\\s*/\\*)|(-->\\s*<!--))?\\s*" );
        System.out.println(b);
    }
}
