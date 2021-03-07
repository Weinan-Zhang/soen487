package com.soen487.rest.project.service.book.interceptor;

import com.soen487.rest.project.repository.core.configuration.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.soap.*;


public class SoapClientInterceptor implements ClientInterceptor {
    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        //Your custom exception handling logic goes here.
//        logger.info("intercepted a fault.");
        WebServiceMessage message = messageContext.getResponse();
        SaajSoapMessage saajSoapMessage = (SaajSoapMessage)message;
        SOAPMessage soapMessage = saajSoapMessage.getSaajMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        try {
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
            SOAPBody soapBody = soapEnvelope.getBody();
            SOAPFault soapFault = soapBody.getFault();

//            logger.error(String.format("Error occurred while invoking web service - %s ",soapFault.getFaultString()));
//            throw new RuntimeException(String.format("Error occurred while invoking web service - %s ",soapFault.getFaultString()));
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.FAILURE.getCode(), soapFault.getFaultString());
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException { }
}
