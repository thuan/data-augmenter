package com.itegra.auditcom.service;

import com.itegra.auditcom.domain.NotaFiscalEntradaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "input-service")
public interface RequestQueueInputService {
    @RequestMapping(method = RequestMethod.POST, value = "/api/queue/entry", consumes = "application/json")
    ResponseEntity<Void> addInput(@RequestBody NotaFiscalEntradaDTO notaFiscalEntradaDTO);
}
