package com.itegra.auditcom.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itegra.auditcom.domain.NotaFiscalEntradaDTO;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AugmenterResource controller
 */
@RestController
@RequestMapping("/api/nota-fiscal")
public class AugmenterResource {

    private final Logger log = LoggerFactory.getLogger(AugmenterResource.class);

    /**
     * POST augmenter
     *
     * Schedule pegar de 100 em 100 durante 5 minutos
     */
    @PostMapping("/augmenter")
    public List<NotaFiscalEntradaDTO> augmenter()
        throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // entrar minio
        String accessKey = "minioadmin";
        String secretKey = "minioadmin";

        MinioClient minioClient = MinioClient.builder().endpoint("http://127.0.0.1:9000").credentials(accessKey, secretKey).build();

        // criar bucket notas-json-aug

        // percorrer notas-json

        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket("notas-json").build());

        List<NotaFiscalEntradaDTO> lstNotas = new ArrayList<>();

        for (Result<Item> result : results) {
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
        }

        return lstNotas;
    }
}
