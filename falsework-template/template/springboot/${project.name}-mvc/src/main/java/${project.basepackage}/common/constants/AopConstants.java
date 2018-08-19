<#assign basepackage = project.basepackage>
package ${basepackage}.common.constants;


public class AopConstants {
    
    public final static String PRJ_PACKAGE="${basepackage}";
    
    public final static String AOP_MVC_CTRL_DATA="execution(* "+PRJ_PACKAGE+".ctrl.data..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)";
    
    public final static String AOP_HTTPCLIENT_SERVICE="execution(* com.core.utils.remote..*(..)) ";
}
