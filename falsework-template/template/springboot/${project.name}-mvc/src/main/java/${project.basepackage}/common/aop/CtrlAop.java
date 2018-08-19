<#assign basepackage = project.basepackage>
<#assign className = entity.name>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xm.core.utils.aop.CtrlAopAbs;
import ${basepackage}.common.constants.AopConstants;

/** 
 * 拦截器： 
 */  
@Aspect  
@Component  
public class CtrlAop extends CtrlAopAbs {  
    private static final Logger logger = LoggerFactory.getLogger(CtrlAop.class);  
      
//    @Value("${r'$'}{spring.profiles}")  
//    private String env;  
      
    /** 
     * 定义拦截规则：拦截com.xjj.web.controller包下面的所有类中，有@RequestMapping注解的方法。 
     */  
    @Pointcut(AopConstants.AOP_MVC_CTRL_DATA)  
    public void controllerMethodPointcut(){}  
      
    /** 
     * 拦截器具体实现 
     * @param pjp 
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。） 
     */  
    @Around("controllerMethodPointcut()") //指定拦截器规则；也可以直接把“execution(* com.xjj.........)”写进这里  
    public Object interceptor(ProceedingJoinPoint pjp){  
        return super.interceptorInvoke( pjp );

    }  
      
} 