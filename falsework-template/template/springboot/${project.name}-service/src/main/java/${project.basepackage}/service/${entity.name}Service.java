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
package ${basepackage}.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.framework.core.db.bean.ListDataWrap;
import com.framework.core.db.bean.Page;
import ${basepackage}.common.agent.SysDaoAgent;
import ${basepackage}.pojo.${className}Pojo;

@Service
public class ${className}Service {

    private static final Logger logger = LoggerFactory.getLogger(${className}Service.class);

    @Resource
    private SysDaoAgent  sysDaoAgent;
//    @Cacheable( cacheNames = SysManagerCacheConstants.CACHE_10SEC, key="#this.getClass().getSimpleName() +'findAll'+ #${className}Name" )
//    @TargetDataSource("ds1")

    /**
     * 创建
     * @param pojo
     * @return 
     *      自增id
     * @throws Exception
     */
    @Transactional
    public Long create(${className}Pojo pojo) throws Exception {
        Number num = sysDaoAgent.quickCrudDao.create(pojo);
        return num.longValue();
    }

    @Transactional
    public int updateById(Long id, ${className}Pojo pojo) throws Exception {
        pojo.setSeqId(id);
        return sysDaoAgent.quickCrudDao.updateById(pojo);
    }

    @Transactional
    public ${className}Pojo readById(Long id) throws Exception {
        return sysDaoAgent.quickCrudDao.getById(${className}Pojo.class, id);
    }

    @Transactional
    public Long count(${className}Pojo pojo) throws Exception {
        return sysDaoAgent.quickCrudDao.count(${className}Pojo.class, pojo);
    }

    @Transactional
    public List<${className}Pojo> readList(${className}Pojo pojo) throws Exception {
        return sysDaoAgent.quickCrudDao.getList(${className}Pojo.class, pojo);
    }

    @Transactional
    public ListDataWrap<${className}Pojo> readAndCount(${className}Pojo pojo, Page page) throws Exception {
        return sysDaoAgent.quickCrudDao.getList(${className}Pojo.class, pojo, page);
    }

}