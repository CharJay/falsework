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
package ${basepackage}.param;


/**
 * ${entity.tableName}
 */
public class ${className}Param{
    

    <#list propertyMap?keys as property>
    /**
     * ${propertyMap[property].comment!""}
     */
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
