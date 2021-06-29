package com.itegra.auditcom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itegra.auditcom.domain.NotaFiscalEntradaDTO;
import com.itegra.auditcom.web.rest.AugmenterResource;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfiguration {

    private final Logger log = LoggerFactory.getLogger(AugmenterResource.class);

    //@Scheduled(cron = "0 15 10 15 * ?")
    @Scheduled(fixedRate = 50000)
    public void scheduleFixedRateTask() {
        log.info("Fixed rate task - " + System.currentTimeMillis() / 1000);

        MinioClient minioClient = getBuild();
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket("notas-json").build());

        List<NotaFiscalEntradaDTO> lstNotas = new ArrayList<>();

        for (Result<Item> result : results) {
            try {
                Item item = result.get();
                InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket("notas-json").object(item.objectName()).build());
                byte[] buf = new byte[16384];
                int bytesRead;
                while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
                    String json = new String(buf, 0, bytesRead, StandardCharsets.UTF_8);
                    ObjectMapper mapper = new ObjectMapper();
                    NotaFiscalEntradaDTO notaFiscalEntradaDTO = mapper.readValue(json, NotaFiscalEntradaDTO.class);
                    notaFiscalEntradaDTO.setAugment("data-augment-ok");
                    lstNotas.add(notaFiscalEntradaDTO);
                }
                // Close the input stream.
                stream.close();
            } catch (Exception e) {
                log.error(String.valueOf(e));
            }
        }
    }

    private MinioClient getBuild() {
        return MinioClient.builder().endpoint("http://127.0.0.1:9000").credentials("minioadmin", "minioadmin").build();
    }
}
