package com.soen487.rest.project.service.bookauditing;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;

@FeignClient("repository.implementation")
@RibbonClient(name="repository.implementation")
public interface RepositoryProxy {
    @PostMapping("book/log/add")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> bookLogAdd(@Valid @Pattern(regexp = "\\d+") @RequestParam(name="bid") long bid);
    @PostMapping("book/log/delete")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> bookLogDelete(@Valid @Pattern(regexp = "\\d+") @RequestParam(name="bid") long bid);
    @PostMapping("book/log/update")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long>bookLogUpdate(@Valid @Pattern(regexp = "\\d+") @RequestParam(name="bid") long bid);
    @GetMapping("log/list")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<com.soen487.rest.project.repository.core.entity.BookChangeLog>> listLogs();
    @PostMapping("log/list")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<com.soen487.rest.project.repository.core.entity.BookChangeLog>>
    listLogsByTimeAndType(@RequestParam(name="stime") Timestamp stime,
                          @RequestParam(name="etime") Timestamp etime,
                          @RequestParam(name="logType") com.soen487.rest.project.repository.core.configuration.LogType logType);
}
