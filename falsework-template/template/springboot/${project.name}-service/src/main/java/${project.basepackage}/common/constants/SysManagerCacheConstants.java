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
package ${basepackage}.common.constants;

import com.framework.core.constants.CoreCacheConstants;

public class SysManagerCacheConstants extends CoreCacheConstants {
    
    
}
