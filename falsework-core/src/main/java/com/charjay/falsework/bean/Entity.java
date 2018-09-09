package com.charjay.falsework.bean;

import com.charjay.falsework.util.CustomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Entity {
    
    private String                          name;
                                            
    private String                          comment    = "";
                                                       
    private String                          tableName;
                                            
    private Table                           table;
                                            
    private LinkedHashMap<String, Property> properties = new LinkedHashMap<>();
                                            
    private Map<String, Object>             tags       = Collections.emptyMap();
                                                       
    private boolean                         tree;                                // 代表属性结构，会自动产生树字段
    private boolean                         map;
    private boolean                         news;
    private String                          propOfName = "name";                 // 一般名称对应的属性名

    
    /**
     * 包含的属性（若为null则是所有）
     */
    private List<String> includeProperty;
    /**
     * 排除的属性
     */
    private List<String> excludeProperty;
    
    
    public Entity() {
        super();
    }
    
    @Deprecated
    public Entity( Table table ) {
        super();
        this.table = table;
        setComment( table.getComment() );
        setTableName( table.getSqlName() );
        
        LinkedHashMap<String, Column> columns = table.getColumnsMap();
        LinkedHashMap<String, Property> props = new LinkedHashMap<String, Property>();
        Set<String> keyset = columns.keySet();
        
        for (String key : keyset) {
            Column c = columns.get( key );
            
            Property p = new Property();
            p.setColumn( c );
            p.setColumnName( c.getSqlName() );
            p.setComment( c.getComment() == null ? "" : c.getComment() );
            p.setJavaType( c.getJavaType() );
            p.setIdentity( c.isPkcol());
   
            props.put( p.getName(), p );
        }
        setProperties( props );
    }
    
    public Entity( Table table, String name, String comment, List<String> include, List<String> exclude ) {
        this(table, name, include, exclude);
        this.comment = comment;
    }

    /**
     * @param table 对应table
     * @param name 实体名称，tableName2entityName，如ScUser2teacher
     * @param include
     *          包含字段；（null: 所有字段）
     * @param exclude
     *          排除字段
     */
    public Entity( Table table, String name, List<String> include, List<String> exclude ) {
        super();
        this.name = name;
        this.includeProperty = include;
        this.excludeProperty = exclude;
        
        this.table = table;
        setComment( table.getComment() );
        setTableName( table.getSqlName() );
        
        LinkedHashMap<String, Property> props = new LinkedHashMap<String, Property>();
        LinkedHashMap<String, Column> columns = table.getColumnsMap();
        
        Set<String> keyset;
        if(include == null) {
            keyset = columns.keySet();
        } else {
            keyset = new HashSet<String>(include);
            keyset.addAll(Arrays.asList("SEQ_ID", "CREATE_TIME", "CREATE_USER_ID", "CREATE_USER_NAME", "UPDATE_TIME",
                    "UPDATE_USER_ID", "UPDATE_USER_NAME", "IS_DEL", "DATA_ORDER"));
        }
        for (String key : keyset) {
            Column c = columns.get( key.toUpperCase().trim() );
            if(c == null) {
                throw new RuntimeException("column:" + key + "不存在");
            }
            if(exclude != null && exclude.contains( key )) {
                // exclude
                continue;
            }
            Property p = new Property();
            p.setColumn( c );
            p.setColumnName( c.getSqlName() );
            p.setComment( c.getComment() == null ? "" : c.getComment() );
            p.setJavaType( c.getJavaType() );
            p.setIdentity( c.isPkcol());
   
            props.put( p.getName(), p );
        }
        
        setProperties( props );
    }

    public Entity addProp(Property prop){
        properties.put( prop.getName(), prop );
        return this;
    }
    /**
     * 移除属性
     *
     * @param columnNames
     */
    public void removeProps( String... columnNames ){
        Iterator<Property> it = properties.values().iterator();
        while (it.hasNext()){
            Property prop = it.next();
            for (String columnName : columnNames) {
                if (columnName.equalsIgnoreCase( prop.getColumnName() ))
                    it.remove();
            }
        }
    }
    /**
     * 保留属性，移除其他属性
     *
     * @param columnNames
     */
    public void retainProps( String... columnNames ){
        Iterator<Property> it = properties.values().iterator();
        ite:while(it.hasNext()){
            Property prop = it.next();
            for (String columnName : columnNames) {
                if (columnName.equalsIgnoreCase( prop.getColumnName() ))
                    continue ite;
            }
            it.remove();
        }
    }
                                            

    
    public Property findPropertyByColumnName( String columnName ) {
        for (Property prop : properties.values()) {
            if (columnName.equalsIgnoreCase( prop.getColumnName() ))
                return prop;
        }
        return null;
    }
    public Property fbc( String columnName ) {
        Property ret = findPropertyByColumnName( columnName );
        if(ret==null){
            throw new RuntimeException(" entity: "+this.getName()+" columnName:"+ columnName+" 未找到");
        }
        return ret;
    }
    
    public List<Property> findPropertyUnFetch4List() {
        List<Property> list = new ArrayList<>();
        for (Property prop : properties.values()) {
            if (!prop.isFetchForList())
                list.add( prop );
        }
        return list;
    }

    public String getNameFirstLower() {
        if (name == null && tableName != null) {
            name = CustomStringUtils.camelCase4Class( tableName );
        }
        return (new StringBuilder()).append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).toString();
    }
    /**
     * 未设定时默认取对应表名的驼峰式
     *
     * @return
     */
    public String getName() {
        if (name == null && tableName != null) {
            return CustomStringUtils.camelCase4Class( tableName );
        }
        return name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public String getComment() {
        if (comment == null || comment.trim().length() == 0)
            comment = getName();
        return comment;
    }
    
    public void setComment( String comment ) {
        if (comment == null || comment.trim().length() == 0)
            comment = getName();
        this.comment = comment;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName( String tableName ) {
        this.tableName = tableName;
    }
    
    public Table getTable() {
        return table;
    }
    
    public void setTable( Table table ) {
        this.table = table;
    }
    
    public LinkedHashMap<String, Property> getProperties() {
        return properties;
    }
    
    public void setProperties( LinkedHashMap<String, Property> properties ) {
        this.properties = properties;
    }
    
    public Map<String, Object> getTags() {
        return tags;
    }
    
    public void setTags( Map<String, Object> tags ) {
        this.tags = tags;
    }
    
    public boolean isTree() {
        return tree;
    }
    
    public void setTree( boolean tree ) {
        this.tree = tree;
    }
    
    public String getPropOfName() {
        return propOfName;
    }
    
    public void setPropOfName( String propOfName ) {
        this.propOfName = propOfName;
    }
    

    public List<String> getIncludeProperty() {
        return includeProperty;
    }

    public List<String> getExcludeProperty() {
        return excludeProperty;
    }

    public boolean isMap() {
        return map;
    }

    public void setMap(boolean map) {
        this.map = map;
    }

    public void setIncludeProperty(List<String> includeProperty) {
        this.includeProperty = includeProperty;
    }

    public void setExcludeProperty(List<String> excludeProperty) {
        this.excludeProperty = excludeProperty;
    }

    public boolean isNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
    }
    
}
