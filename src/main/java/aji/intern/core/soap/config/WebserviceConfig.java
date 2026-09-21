package aji.intern.core.soap.config;

import aji.intern.core.soap.controller.WebserviceEndpoint;
import aji.intern.core.soap.interceptor.RequestIdInterceptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

import java.util.List;

@Configuration
@EnableWs
public class WebserviceConfig implements WsConfigurer {

    /*
     * Interceptor
     * */
    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(new RequestIdInterceptor());
    }

    /*
     * Endpoint url mappings
     * */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    /*
     * Wsdl definition
     * */
    @Bean(name = "account")
    public DefaultWsdl11Definition accountWsdl(CommonsXsdSchemaCollection accountSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("Account");
        definition.setLocationUri("/Account");
        definition.setTargetNamespace(WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE);
        definition.setSchemaCollection(accountSchema);
        return definition;
    }

    @Bean(name = "card")
    public DefaultWsdl11Definition cardWsdl(CommonsXsdSchemaCollection cardSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("Card");
        definition.setLocationUri("/Card");
        definition.setTargetNamespace(WebserviceEndpoint.NAMESPACE_CARD_SERVICE);
        definition.setSchemaCollection(cardSchema);
        return definition;
    }

    @Bean
    public CommonsXsdSchemaCollection accountSchema() {
        CommonsXsdSchemaCollection schema = new CommonsXsdSchemaCollection(new ClassPathResource("account.xsd"));

        schema.setInline(true);

        return schema;
    }

    @Bean
    public CommonsXsdSchemaCollection cardSchema() {
        CommonsXsdSchemaCollection schema = new CommonsXsdSchemaCollection(new ClassPathResource("card.xsd"));

        schema.setInline(true);

        return schema;
    }
}
