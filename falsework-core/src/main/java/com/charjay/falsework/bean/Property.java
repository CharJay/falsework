package com.charjay.falsework.bean;

import com.charjay.falsework.util.CustomStringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class  Property {
    
    private String  name;
                    
    private String  label;
                    
    private String  javaType;
                    
    private String  comment;
                    
    private boolean identity;
                    
    private String  columnName;
                    
    private Column  column;
    /**
     * 字典类型
     */
    private String  dictType;
    /**
     * 字典类ID
     */
    private String  classId;
    /**
     * 在列表查询时是否从DB提取该字段的数据
     */
    private boolean fetchForList   = true;
    private boolean dictShowChoice = true;  // 是否出现请选择
    private String  dictDefValue   = null;  // 默认值，null表示没有默认值
                                   
    private boolean becomeLeftTree = false; // 作为左侧树
                                   
    private Valid   valid          = null;
                                   
    public Property appendValid( Valid valid ) {
        this.valid = valid;
        return this;
    }
    
    private boolean use = false; // 是否使用
    
    public boolean isUse() {
        return use;
    }
    
    public void setUse( boolean use ) {
        this.use = use;
    }
    
    public Property appendBecomeLeftTree( boolean becomeLeftTree ) {
        this.becomeLeftTree = becomeLeftTree;
        return this;
    }
    
    public Property appendFetchForList( boolean isFetch ) {
        this.fetchForList = isFetch;
        return this;
    }
    
    private Map<String, Object> tags = Collections.emptyMap();
    
    public Property setDict( String dictType, String classId ) {
        this.dictType = dictType;
        this.classId = classId;
        return this;
    }
    
    public Property setDict( String dictType, String classId, boolean dictShowChoice, String dictDefValue ) {
        this.dictType = dictType;
        this.classId = classId;
        this.dictShowChoice = dictShowChoice;
        this.dictDefValue = dictDefValue;
        return this;
    }
    
    public Property appendComment( String comment ) {
        this.comment = comment;
        return this;
    }
    
    public Property appendLabel( String label ) {
        this.label = label;
        return this;
    }
    
    //
    /**
     * 未设定时默认取对应字段名的驼峰式
     *
     * @return
     */
    public String getName() {
        if (name == null && columnName != null) {
            return CustomStringUtils.camelCase4Field( columnName );
        }
        return name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public String getLabel() {
        return label == null ? getComment() : label;
    }
    
    public void setLabel( String label ) {
        this.label = label;
    }
    
    public String getJavaType() {
        return javaType;
    }
    
    public String getSimpleType() {
        return javaType.substring( javaType.lastIndexOf( "." ) + 1 );
    }
    
    public void setJavaType( String javaType ) {
        this.javaType = javaType;
    }
    
    public String getComment() {
        return comment == null ? name : comment;
    }
    
    public void setComment( String comment ) {
        this.comment = comment;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public void setColumnName( String columnName ) {
        this.columnName = columnName;
    }
    
    public Column getColumn() {
        return column;
    }
    
    public void setColumn( Column column ) {
        this.column = column;
    }
    
    public boolean isFetchForList() {
        return fetchForList;
    }
    
    public void setFetchForList( boolean fetchForList ) {
        this.fetchForList = fetchForList;
    }
    
    public boolean isIdentity() {
        return identity;
    }
    
    public void setIdentity( boolean identity ) {
        this.identity = identity;
    }
    
    public String getDictType() {
        return dictType;
    }
    
    public void setDictType( String dictType ) {
        this.dictType = dictType;
    }
    
    public String getClassId() {
        return classId;
    }
    
    public void setClassId( String classId ) {
        this.classId = classId;
    }
    
    public Map<String, Object> getTags() {
        return tags;
    }
    
    public void setTags( Map<String, Object> tags ) {
        this.tags = tags;
    }
    
    public boolean isDictShowChoice() {
        return dictShowChoice;
    }
    
    public void setDictShowChoice( boolean dictShowChoice ) {
        this.dictShowChoice = dictShowChoice;
    }
    
    public String getDictDefValue() {
        return dictDefValue;
    }
    
    public void setDictDefValue( String dictDefValue ) {
        this.dictDefValue = dictDefValue;
    }

    
    // --------------------文件系统界面的元素------------------// begin
    
    private int    fileType         = -1;                  // -1什么都不是，1图片，2文件
    private String fileAllowExt     = "'jpg', 'png','gif'";
    private String fileFixIconUrl   = "";                  // 固定的图片预览图url(优先级比class高)
    private String fileFixIconClass = "";                  // 固定的图片预览图class
    private int    fileMaxCount     = 1;                   // 0 文件个数，不限制个数
                                  
    public Property appendFileType( int fileType ) {
        this.fileType = fileType;
        return this;
    }
    
    public Property appendFileAllowExt( String ext ) {
        this.fileAllowExt = ext;
        return this;
    }
    
    public Property appendFileFixIconUrl( String iconUrl ) {
        this.fileFixIconUrl = iconUrl;
        return this;
    }
    
    public Property appendFileFixIconClass( String iconClass ) {
        this.fileFixIconClass = iconClass;
        return this;
    }
    
    public Property appendFileMaxCount( int count ) {
        this.fileMaxCount = count;
        return this;
    }
    
    public int getFileType() {
        return fileType;
    }
    
    public void setFileType( int fileType ) {
        this.fileType = fileType;
    }
    
    public String getFileAllowExt() {
        return fileAllowExt;
    }
    
    public void setFileAllowExt( String fileAllowExt ) {
        this.fileAllowExt = fileAllowExt;
    }
    
    public String getFileFixIconUrl() {
        return fileFixIconUrl;
    }
    
    public void setFileFixIconUrl( String fileFixIconUrl ) {
        this.fileFixIconUrl = fileFixIconUrl;
    }
    
    public String getFileFixIconClass() {
        return fileFixIconClass;
    }

    public void setFileFixIconClass(String fileFixIconClass) {
        this.fileFixIconClass = fileFixIconClass;
    }

    public int getFileMaxCount() {
        return fileMaxCount;
    }
    
    public void setFileMaxCount( int fileMaxCount ) {
        this.fileMaxCount = fileMaxCount;
    }
    
    // --------------------文件系统界面的元素------------------// end
    // --------------------富文本系统界面的元素------------------// begin
    
    private boolean richEditor = false;
    
    public Property appendRichEditor( boolean richEditor ) {
        this.setRichEditor( richEditor );
        return this;
    }
    
    public boolean isRichEditor() {
        return richEditor;
    }
    
    public void setRichEditor( boolean richEditor ) {
        this.richEditor = richEditor;
    }
    
    public boolean isBecomeLeftTree() {
        return becomeLeftTree;
    }
    
    public void setBecomeLeftTree( boolean becomeLeftTree ) {
        this.becomeLeftTree = becomeLeftTree;
    }
    
    public Valid getValid() {
        return valid;
    }
    
    public void setValid( Valid valid ) {
        this.valid = valid;
    }
    
    // --------------------文件系统界面的元素------------------// end
    

    
}
