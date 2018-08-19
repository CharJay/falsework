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
package ${basepackage}.ctrl.crud;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xm.core.utils.bean.ListDataWrap;
import com.xm.core.utils.bean.Page;
import com.xm.core.utils.bean.RetMsg;
import com.xm.core.utils.helper.BusinessException;

import ${basepackage}.common.agent.SysServiceAgent;
import ${basepackage}.param.*;
import ${basepackage}.pojo.*;
import ${basepackage}.vo.*;

@Api(value = "${className}CrudCtrl")
@RestController
@RequestMapping("crud/${classNameLower}")
public class ${className}CrudCtrl {

    private static final Logger    logger = LoggerFactory.getLogger(${className}CrudCtrl.class);

    @Autowired
    private SysServiceAgent sysServiceAgent;

    @RequiresPermissions("${classNameLower}_create")
    @ApiOperation(value = "create", httpMethod = "POST")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public RetMsg<Long> create(HttpServletRequest request, ${className}Param param){
    	${className}Pojo pojo=new ${className}Pojo();
    	try {
			BeanUtils.copyProperties(pojo, param);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error("",e);
		}
        Number num = sysServiceAgent.quickCrudService.create(pojo);
        return RetMsg.success(num.longValue());
    }
    
    @RequiresPermissions("${classNameLower}_deleteById")
    @ApiOperation(value = "deleteById", httpMethod = "POST")
    @RequestMapping( value = "deleteById", method = RequestMethod.POST )
    public RetMsg<Object> deleteById(HttpServletRequest request, Long seqId) throws Exception {
    	${className}Pojo pojo = new ${className}Pojo();
    	pojo.setIsDel(1);
    	pojo.setSeqId(seqId);
    	int ret = sysServiceAgent.quickCrudService.updateById( pojo  );
    	if(ret == 0) {
    		throw new BusinessException("删除失败");
    	}
    	return RetMsg.success();
    }
    
    @RequiresPermissions("${classNameLower}_updateById")
    @ApiOperation(value = "updateById", httpMethod = "POST")
    @RequestMapping( value = "updateById", method = RequestMethod.POST )
    public RetMsg<Object> updateById(HttpServletRequest request,Long seqId, ${className}Param param) throws Exception {
    	${className}Pojo pojo=new ${className}Pojo();
    	try {
			BeanUtils.copyProperties(pojo, param);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error("",e);
		}
    	int ret = sysServiceAgent.quickCrudService.updateById( pojo);
    	if(ret == 0) {
    		throw new BusinessException("更新失败");
    	}
    	return RetMsg.success();
    }

    @RequiresPermissions("${classNameLower}_readById")
    @ApiOperation(value = "readById", httpMethod = "POST")
    @RequestMapping(value = "readById", method = {RequestMethod.POST,RequestMethod.GET})
    public RetMsg<${className}Vo> readById(HttpServletRequest request, Long seqId) throws Exception {
    	${className}Pojo pojo = sysServiceAgent.quickCrudService.getById(${className}Pojo.class, seqId);
    	${className}Vo vo=new ${className}Vo();
    	try {
    		BeanUtils.copyProperties(vo, pojo);
    	} catch (IllegalAccessException | InvocationTargetException e) {
    		logger.error("",e);
    	}
        return RetMsg.success(vo);
    }

    @RequiresPermissions("${classNameLower}_count")
    @ApiOperation(value = "count", httpMethod = "GET")
    @RequestMapping(value = "count", method = RequestMethod.GET)
    public RetMsg<Long> count(HttpServletRequest request, ${className}Param param)  {
    	${className}Pojo pojo=new ${className}Pojo();
    	try {
			BeanUtils.copyProperties(pojo, param);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error("",e);
		}
    	long cnt = sysServiceAgent.quickCrudService.count(${className}Pojo.class,pojo);
        return RetMsg.success(cnt);
    }
    
    @RequiresPermissions("${classNameLower}_readList")
    @ApiOperation(value = "readList", httpMethod = "POST")
    @RequestMapping(value = "readList", method = RequestMethod.POST)
    public RetMsg<ListDataWrap<${className}Pojo>> readList(HttpServletRequest request, ${className}Param param)  {
    	${className}Pojo pojo=new ${className}Pojo();
    	try {
			BeanUtils.copyProperties(pojo, param);
			pojo.setIsDel(0);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error("",e);
		}
    	Page page=new Page(5000, 1);//限制，不全部查询
    	ListDataWrap<${className}Pojo> listDataWrap = sysServiceAgent.quickCrudService.getList(${className}Pojo.class, pojo, page," seq_id desc");
        return RetMsg.success(listDataWrap);
    }

    @RequiresPermissions("${classNameLower}_readAndCount")
    @ApiOperation(value = "readAndCount", httpMethod = "POST")
    @RequestMapping(value = "readAndCount", method = RequestMethod.POST)
    public RetMsg<ListDataWrap<${className}Pojo>> readAndCount(HttpServletRequest request, ${className}Param param, Page page)  {
    	${className}Pojo pojo=new ${className}Pojo();
    	try {
			BeanUtils.copyProperties(pojo, param);
			pojo.setIsDel(0);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error("",e);
		}
    	ListDataWrap<${className}Pojo> listDataWrap = sysServiceAgent.quickCrudService.getList(${className}Pojo.class, pojo, page," seq_id desc");
        return RetMsg.success(listDataWrap);
    }

}
