package com.itegra.auditcom.service;

import com.itegra.auditcom.domain.NotaFiscalEntradaDTO;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class QueueInputService {

    private RequestQueueInputService requestQueueInputService;

    @Inject
    public QueueInputService(RequestQueueInputService requestQueueInputService) {
        this.requestQueueInputService = requestQueueInputService;
    }

    public void addInput(NotaFiscalEntradaDTO notaFiscalEntradaDTO) {
        requestQueueInputService.addInput(notaFiscalEntradaDTO);
    }
}
