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
import com.framework.core.db.agent.CoreDaoAgent;

@Component
public class SysDaoAgent extends CoreDaoAgent {

}