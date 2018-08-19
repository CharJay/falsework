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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xm.core.utils.agent.CoreServiceAgent;
import ${basepackage}.service.*;

@Component
public class SysServiceAgent extends CoreServiceAgent  {
    
    <#assign entities=project.entities >
    <#list entities as enti>
    @Autowired
    public ${enti.name}Service ${enti.name?uncap_first}Service;
    </#list>
    //u-insert#other1#
    //u-insert#
}
