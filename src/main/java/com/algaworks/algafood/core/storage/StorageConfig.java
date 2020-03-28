package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.LocalFotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {

    private final StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "algaworks.storage.tipo", havingValue = "s3")
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta());

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(storageProperties.getS3().getRegiao())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if (StorageProperties.TipoStorage.S3.equals(storageProperties.getTipo())) {
            return new S3FotoStorageService();
        }

        return new LocalFotoStorageService();
    }
}
