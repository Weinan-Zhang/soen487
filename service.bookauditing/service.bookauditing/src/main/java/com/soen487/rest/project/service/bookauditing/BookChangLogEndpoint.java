package com.soen487.rest.project.service.bookauditing;

import com.soen487.rest.project.service.bookauditing.exception.ServiceFaultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.DatatypeConverter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Endpoint
public class BookChangLogEndpoint {
    private static final String NAMESPACE_URI = "bookauditing.service.project.rest.soen487.com";
    @Autowired
    private RepositoryProxy repositoryProxy;
    @Autowired
    private ModelMapper modelMapper;


    @PayloadRoot(namespace=NAMESPACE_URI, localPart="bookChangeLogRequest")
    @ResponsePayload
    public BookChangeLogResponse bookLog(@RequestPayload BookChangeLogRequest request){
        BookChangeLogResponse response = new BookChangeLogResponse();
        com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> proxyResponse = null;
        Long lid = -1l;
        if(request.getLogType()==LogType.CREATE){
            proxyResponse = this.repositoryProxy.bookLogAdd(request.getBid());
        }
        else if(request.getLogType()==LogType.DELETE){
            proxyResponse = this.repositoryProxy.bookLogDelete(request.getBid());
        }
        else {
            proxyResponse = this.repositoryProxy.bookLogUpdate(request.getBid());
        }

        if(proxyResponse.getCode()>=200 && proxyResponse.getCode()<300){
            lid = proxyResponse.getPayload();
        }
        else {
            String errorMessage = "ERROR";
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setStatusCode("LOG_TRANSACTION_FAILURE");
            serviceStatus.setMessage(proxyResponse.getMessage());

            throw new ServiceFaultException(errorMessage, serviceStatus);
        }
        response.setLid(lid);
        return response;
    }

    @PayloadRoot(namespace=NAMESPACE_URI, localPart="getLogRequest")
    @ResponsePayload
    public GetLogResponse getBookLogs(@RequestPayload GetLogRequest request){
        GetLogResponse response = new GetLogResponse();
        com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<com.soen487.rest.project.repository.core.entity.BookChangeLog>> proxyResponse = null;

        if(request.getFromDateTime()==null && request.getToDateTime()==null && request.getLogType()==null){
            proxyResponse = this.repositoryProxy.listLogs();
        }
        else{
            Timestamp fromTimestamp = request.getFromDateTime()!=null ? new Timestamp(request.getFromDateTime().toGregorianCalendar().getTimeInMillis()) : new Timestamp(new Date(0L).getTime());
            Timestamp toTimestamp = request.getToDateTime()!=null ? new Timestamp(request.getToDateTime().toGregorianCalendar().getTimeInMillis()) : new Timestamp(new Date().getTime());
            LogType logTypeRequest = request.getLogType()!=null ? request.getLogType() : LogType.ALL;
            com.soen487.rest.project.repository.core.configuration.LogType logTypeTarget = com.soen487.rest.project.repository.core.configuration.LogType.ALL;

            if(logTypeRequest.value().equals("CREATE")){
                logTypeTarget = com.soen487.rest.project.repository.core.configuration.LogType.CREATE;
            }
            else if(logTypeRequest.value().equals("DELETE")){
                logTypeTarget = com.soen487.rest.project.repository.core.configuration.LogType.DELETE;
            }
            else if(logTypeRequest.value().equals("MODIFY")){
                logTypeTarget = com.soen487.rest.project.repository.core.configuration.LogType.MODIFY;
            }
            else if(logTypeRequest.value().equals("ALL")){
                logTypeTarget = com.soen487.rest.project.repository.core.configuration.LogType.ALL;
            }
            proxyResponse = this.repositoryProxy.listLogsByTimeAndType(fromTimestamp, toTimestamp, logTypeTarget);
        }


        if(proxyResponse.getCode()<200 || proxyResponse.getCode()>299){
            String errorMessage = "ERROR";
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setStatusCode("NOT_FOUND");
            serviceStatus.setMessage(proxyResponse.getMessage());

            throw new ServiceFaultException(errorMessage, serviceStatus);
        }
        List<BookChangeLogDto> logDtos = proxyResponse.getPayload().stream().map(this::convertToDto).collect(Collectors.toList());
        response.logDtos = logDtos;
        return response;
    }

    private BookChangeLogDto convertToDto(com.soen487.rest.project.repository.core.entity.BookChangeLog log) {
        BookChangeLogDto logDto = modelMapper.map(log, BookChangeLogDto.class);
        return logDto;
    }
}
