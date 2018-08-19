

<#assign basepackage = project.basepackage>
<#assign className = entity.name>
<#assign classNameLower = className?uncap_first> 
    <#list project.entities as entity>
alter table ${entity.tableName} AUTO_INCREMENT=100000;
 	</#list>
 