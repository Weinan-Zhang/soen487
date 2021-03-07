package com.soen487.rest.project.service.book.configuration;

import com.soen487.rest.project.service.book.controller.BookChangLogClient;
import com.soen487.rest.project.service.book.interceptor.SoapClientInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

@Configuration
public class BookChangLogConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.soen487.rest.project.service.book.model.ws");
        return marshaller;
    }

    @Bean
    public BookChangLogClient bookChangLogClient(Jaxb2Marshaller marshaller) {
        BookChangLogClient client = new BookChangLogClient();
        client.setDefaultUri("http://localhost:8802/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        ClientInterceptor[] interceptors = new ClientInterceptor[] {interceptor()};
        client.setInterceptors(interceptors);
        return client;
    }

    @Bean
    public SoapClientInterceptor interceptor() {
        return new SoapClientInterceptor();
    }

}
