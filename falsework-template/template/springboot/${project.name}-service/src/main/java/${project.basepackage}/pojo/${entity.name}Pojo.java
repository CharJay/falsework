<#assign basepackage = project.basepackage>
<#assign className = entity.name>
<#assign classNameLower = className?uncap_first>
<#assign propertyMap = entity.properties>
<#list propertyMap?keys as property>
<#if propertyMap[property].identity>
<#assign identity = propertyMap[property].name>
<#assign identityJavaType = propertyMap[property].javaType> 
</#if>
</#list>
<#if !identity??>
<#assign identity = "seqId">
<#assign identityJavaType = "java.lang.Long"> 
</#if>
package ${basepackage}.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ${entity.tableName}
 */
@Entity
@Table(name="${entity.tableName}")
//@DynamicUpdate
//@DynamicInsert
//@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class ${className}Pojo implements Serializable{
    
    private static final long serialVersionUID = 1L;

    <#list propertyMap?keys as property>
    /**
     * ${propertyMap[property].comment!""}
     */
    <#if propertyMap[property].name="seqId">
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    </#if>
    private ${propertyMap[property].javaType} ${propertyMap[property].name};
    </#list>
    
    <#list propertyMap?keys as property>
    <#assign propertyGetName = propertyMap[property].name?cap_first> 
    public ${propertyMap[property].javaType} get${propertyGetName}(){
        return this.${propertyMap[property].name};
    }
    
    public void set${propertyGetName}(${propertyMap[property].javaType} ${propertyMap[property].name}){
        this.${propertyMap[property].name} = ${propertyMap[property].name};
    }
    </#list>
    
}
