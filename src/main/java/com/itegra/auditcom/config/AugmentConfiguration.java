package com.itegra.auditcom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itegra.auditcom.domain.NotaFiscalEntradaDTO;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class AugmentConfiguration {

    private final Logger log = LoggerFactory.getLogger(AugmentConfiguration.class);

    private static final String AUGMENTER = "notas-augmenter";

    private static final String NOTAS = "notas-json";

    //@Scheduled(cron = "0 15 10 15 * ?")
    @Scheduled(fixedDelayString = "PT15M")
    public void scheduleFixedDelayTask() {
        log.info("Scheduled Fixed rate task - " + System.currentTimeMillis() / 1000);

        MinioClient minioClient = getBuild();

        createBucket(minioClient);

        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(NOTAS).maxKeys(100).build());

        for (Result<Item> result : results) {
            try {
                Item item = result.get();
                InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(NOTAS).object(item.objectName()).build());
                byte[] buf = new byte[16384];
                int bytesRead;
                while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
                    String json = new String(buf, 0, bytesRead, StandardCharsets.UTF_8);
                    ObjectMapper mapper = new ObjectMapper();
                    NotaFiscalEntradaDTO notaFiscalEntradaDTO = mapper.readValue(json, NotaFiscalEntradaDTO.class);
                    // 1 Passo - Comunicao via feign para a api dos Clientes -> Auditcom
                    // 2 Passo - colocar permitall() dentro do Servico (/api/empresa/cliente)
                    // 3 passo - Codificar o NotaFiscalEntradaDTO com o DTO dos Clientes - Empresas
                    // 4 Passo - Criar um metodo que vai fazer o augment
                    augmentData(notaFiscalEntradaDTO);
                    notaFiscalEntradaDTO.setAugment("data-augment-ok");
                    augmentBucket(minioClient, notaFiscalEntradaDTO);
                }
                stream.close();
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(NOTAS).object(item.objectName()).build());
            } catch (Exception e) {
                log.error(String.valueOf(e));
            }
        }
    }

    private void augmentData(NotaFiscalEntradaDTO notaFiscalEntradaDTO) {}

    private void augmentBucket(MinioClient minioClient, NotaFiscalEntradaDTO notaFiscalEntradaDTO)
        throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ObjectMapper mapper = new ObjectMapper();
        File fileJson = new File("entrada.json");
        mapper.writeValue(fileJson, notaFiscalEntradaDTO);

        UploadObjectArgs.Builder builderJson = UploadObjectArgs
            .builder()
            .bucket(AUGMENTER)
            .object(notaFiscalEntradaDTO.getId() + ".json")
            .filename(fileJson.toString());

        minioClient.uploadObject(builderJson.build());
    }

    private void createBucket(MinioClient minioClient) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(AUGMENTER).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(AUGMENTER).build());
            }
        } catch (
            ErrorResponseException
            | InsufficientDataException
            | InternalException
            | InvalidKeyException
            | InvalidResponseException
            | IOException
            | NoSuchAlgorithmException
            | ServerException
            | XmlParserException e
        ) {
            e.printStackTrace();
        }
    }

    private MinioClient getBuild() {
        return MinioClient.builder().endpoint("http://127.0.0.1:9000").credentials("minioadmin", "minioadmin").build();
    }
}
