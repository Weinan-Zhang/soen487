package com.soen487.rest.project.service.book.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soen487.rest.project.repository.core.configuration.ReturnCode;
import com.soen487.rest.project.service.book.controller.GatewayProxy;
import com.soen487.rest.project.service.book.controller.RepositoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    RepositoryProxy repositoryProxy;
    @Autowired
    GatewayProxy gatewayProxy;
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<com.soen487.rest.project.repository.core.entity.Category>> retriveCategories(){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse response = this.gatewayProxy.getCategoryList();
        if(response.getCode()>=200 && response.getCode()<=299){
            List<com.soen487.rest.project.repository.core.entity.Category> categoriesMap = (List<com.soen487.rest.project.repository.core.entity.Category>) response.getPayload();
            ObjectMapper mapper = new ObjectMapper();
            List<com.soen487.rest.project.repository.core.entity.Category> categories = mapper.convertValue(categoriesMap, new TypeReference<List<com.soen487.rest.project.repository.core.entity.Category>>() { });
            return com.soen487.rest.project.repository.core.configuration.ServiceResponse.success(ReturnCode.SUCCESS, categories);
        }
        throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.FAILURE);
    }
}
