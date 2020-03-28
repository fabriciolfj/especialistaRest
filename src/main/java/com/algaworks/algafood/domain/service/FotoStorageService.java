package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);
    void remover(String nomeArquivo);
    FotoRecuperada recuperar(String nomeArquivo);

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if(nomeArquivoAntigo != null) {
            this.remover(nomeArquivoAntigo);
        }
    }

    @Getter
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
        private String contentType;
    }

    @Getter
    @Builder
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean temURl() {
            return url != null;
        }

        public boolean temInputStream() {
            return inputStream != null;
        }
    }
}
