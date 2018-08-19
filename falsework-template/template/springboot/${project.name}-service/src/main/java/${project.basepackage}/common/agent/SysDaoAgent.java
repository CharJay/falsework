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
package ${basepackage}.common.agent;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xm.core.utils.agent.CoreDaoAgent;
import ${basepackage}.dao.*;

@Component
public class SysDaoAgent extends CoreDaoAgent {
    
    <#assign entities=project.entities >
    <#list entities as enti>
    @Resource
    public ${enti.name}Dao ${enti.name?uncap_first}Dao;
    </#list>
    //u-insert#other1#
    //u-insert#
}