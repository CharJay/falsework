package com.charjay.falsework.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Project {
    
    /**
     * 工程名
     */
    private String              name            = "example";
    /**
     * 包名
     */
    private String              basepackage     = "com.example";
    /**
     * 子包名
     */
    private String              subpackage      = "";//如果不设置，将使用basepackage
    
    /**
     * 命名空间；分组名
     */
    private String              groupId         = "com.example";
    /**
     * 数据库名称。mysql,oracle,db2,sqlserver...
     */
    private String              databaseName    = "mysql";
                                                
    private Map<String, Object> tags            = Collections.emptyMap();
                                                
    private String              portalKey;
    private String              dictType;
    private String              dictName;
    private String              portalCnName;                                         // 中文名称
    private String              seqIdPrefix;                                          // seqId的前缀，用于sql脚本生成
    private String              webPathName;                                          // web站点
    private String              serverPathName;                                       // server站点
    private String              staticPathName;                                       // 静态资源站点
                                
    /**
     * 实体集合
     */
    private List<Entity>        entities;
    /**
     * 引用的数据库表集合
     */
    private Set<Table>         tables;
    /**
     * 删除的实体集合
     */
    private List<Entity>        deletedEntities = new ArrayList<>();
                                                
    /**
     * 新增方法集合
     */
    private List<Module>        modules;
    
    private List<Dict>          dictList = new ArrayList<>();
       
    /**
     * 不建议使用，建议使用findEntityByName（根据entityName查询entity）
     * @param tableName
     * @return
     */
    @Deprecated
    public Entity findEntityByTableName( String tableName ) {
        for (Entity e : entities) {
            if (tableName.equalsIgnoreCase( e.getTableName() ))
                return e;
        }
        return null;
    }
    
    /**
     * 根据表名查询所有实体的集合
     * @param tableName
     * @return  List<Entity>
     */
    public List<Entity> findEntitiesByTableName( String tableName ) {
        List<Entity> tmp = new ArrayList<>();
        for (Entity e : entities) {
            if (tableName.equalsIgnoreCase( e.getTableName() ))
                tmp.add(e);
        }
        return tmp;
    }
    
    /**
     * 根据entityName查询entity
     * @param entityName
     * @return
     */
    public Entity findEntityByName( String entityName ) {
        for (Entity e : entities) {
            if (entityName.equalsIgnoreCase( e.getName() ) || entityName.equalsIgnoreCase( e.getTableName() ))
                return e;
        }
        return null;
    }

    public Project delEntity( String... names ) {
        for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext();) {
            Entity entity = iterator.next();
            for (String name : names) {
                if (name.equalsIgnoreCase( entity.getTableName() ) || name.equalsIgnoreCase( entity.getName() )) {
                    iterator.remove();
                    deletedEntities.add( entity );
                }
            }
        }
        return this;
    }
    
    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    }
    
    public String getBasepackage() {
        return basepackage;
    }
    
    public void setBasepackage( String basepackage ) {
        this.basepackage = basepackage;
    }
    
    public String getGroupId() {
        return groupId;
    }
    
    public void setGroupId( String groupId ) {
        this.groupId = groupId;
    }
    
    public List<Entity> getEntities() {
        return entities;
    }
    
    public void setEntities( List<Entity> entities ) {
        this.entities = entities;
    }
    
    public String getDatabaseName() {
        return databaseName;
    }
    
    public void setDatabaseName( String databaseName ) {
        this.databaseName = databaseName;
    }
    
    public Map<String, Object> getTags() {
        return tags;
    }
    
    public void setTags( Map<String, Object> tags ) {
        this.tags = tags;
    }
    
    public String getPortalKey() {
        return portalKey;
    }
    
    public void setPortalKey( String portalKey ) {
        this.portalKey = portalKey;
    }
    
    public String getDictType() {
        return dictType;
    }
    
    public void setDictType( String dictType ) {
        this.dictType = dictType;
    }
    
    public String getPortalCnName() {
        if (portalCnName == null || portalCnName.trim().length() == 0)
            portalCnName = getPortalKey();
        return portalCnName;
    }
    
    public void setPortalCnName( String portalCnName ) {
        this.portalCnName = portalCnName;
    }
    
    public String getSeqIdPrefix() {
        return seqIdPrefix;
    }
    
    public void setSeqIdPrefix( String seqIdPrefix ) {
        this.seqIdPrefix = seqIdPrefix;
    }
    
    public String getWebPathName() {
        if (webPathName == null || webPathName.trim().length() == 0)
            webPathName = name + "-web";
        return webPathName;
    }
    
    public void setWebPathName( String webPathName ) {
        this.webPathName = webPathName;
    }
    
    public String getServerPathName() {
        if (serverPathName == null || serverPathName.trim().length() == 0)
            serverPathName = name + "-server";
        return serverPathName;
    }
    
    public void setServerPathName( String serverPathName ) {
        this.serverPathName = serverPathName;
    }
    
    public String getStaticPathName() {
        if (staticPathName == null || staticPathName.trim().length() == 0)
            staticPathName = name + "-webs";
        return staticPathName;
    }
    
    public void setStaticPathName( String staticPathName ) {
        this.staticPathName = staticPathName;
    }
    
    public List<Module> getModules() {
        return modules;
    }
    
    public void setModules( List<Module> modules ) {
        this.modules = modules;
    }
    
    public List<Entity> getDeletedEntities() {
        return deletedEntities;
    }
    
    public void setDeletedEntities( List<Entity> deletedEntities ) {
        this.deletedEntities = deletedEntities;
    }

    public String getSubpackage() {
        if(subpackage==null || subpackage.trim().length()==0) {
            this.subpackage = this.basepackage;
        }
        return subpackage;
    }

    public void setSubpackage( String subpackage ) {
        this.subpackage = subpackage;
    }

    public List<Dict> getDictList() {
        return dictList;
    }

    public void setDictList(List<Dict> dictList) {
        this.dictList = dictList;
    }

    public String getDictName() {
        if(dictName == null || "".equals( dictName )) {
            this.dictName = this.portalCnName;
        }
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }
    
    public Project appendDict(Dict dict) {
        dictList.add(dict);
        return this;
    }

    public Set<Table> getTables() {
        tables = new HashSet<>();
        if(entities != null && entities.size() > 0) {
            for (Entity e : entities) {
                tables.add( e.getTable() );
            }
        }
        return tables;
    }
    public String getNameUpper() {
        return portalKey.substring(0, 1).toUpperCase() + portalKey.substring(1);
    }
    public String getNameLower() {
        return portalKey.substring(0, 1).toLowerCase() + portalKey.substring(1);
    }
}
