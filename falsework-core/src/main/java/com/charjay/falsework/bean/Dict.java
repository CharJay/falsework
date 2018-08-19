package com.charjay.falsework.bean;

/**
 * 数据字典
 */
public class Dict {

    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典类ID
     */
    private String classId;
    /**
     * 字典类名
     */
    private String className;
    /**
     * 条目ID
     */
    private String entryId;
    /**
     * 条目名称
     */
    private String entryName;

    public Dict() {
        super();
    }

    public Dict(String classId, String className, String entryId, String entryName) {
        super();
        this.classId = classId;
        this.className = className;
        this.entryId = entryId;
        this.entryName = entryName;
    }

    public Dict(String dictType, String dictName, String classId, String className, String entryId, String entryName) {
        super();
        this.dictType = dictType;
        this.dictName = dictName;
        this.classId = classId;
        this.className = className;
        this.entryId = entryId;
        this.entryName = entryName;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

}
