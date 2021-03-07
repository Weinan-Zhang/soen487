package com.soen487.rest.project.service.book.controller;

import com.soen487.rest.project.service.book.model.ws.*;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.FaultMessageResolver;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;


public class BookChangLogClient extends WebServiceGatewaySupport {
    public BookChangeLogResponse bookChangeLog(LogType logType, long bid){
        BookChangeLogRequest request = new BookChangeLogRequest();
        request.setLogType(logType);
        request.setBid(bid);

        BookChangeLogResponse response = (BookChangeLogResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        return response;
    }

    public GetLogResponse getLog(XMLGregorianCalendar stime, XMLGregorianCalendar etime, LogType logType){
        GetLogRequest request = new GetLogRequest();
        request.setFromDateTime(stime);
        request.setToDateTime(etime);
        request.setLogType(logType);
        GetLogResponse response = (GetLogResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        return response;
    }
}
