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
package ${basepackage};

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.xm.core.utils.config.datasource.dynamic.DynamicDataSourceRegister;

/**
 * 启动程序
 */
@SpringBootApplication
@Import({DynamicDataSourceRegister.class})//注册动态多数据源
@ComponentScan(basePackages={"${basepackage}", "com.xm.core"})
public class App {
    //u-insert#other1#

    //u-insert#
	public static void main(String[] args) {
	    
		SpringApplication.run(App.class, args);
	}
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addConnectorCustomizers(new TomcatConnectorCustomizer() {

			@Override
			public void customize(Connector arg0) {
				Http11NioProtocol handler = (Http11NioProtocol) arg0.getProtocolHandler();
				// 设置最大连接数
				handler.setMaxConnections(2000);
				// 设置最大线程数
				handler.setMaxThreads(2000);
				handler.setConnectionTimeout(30000);

			}
		});
		return tomcat;
	}

}
