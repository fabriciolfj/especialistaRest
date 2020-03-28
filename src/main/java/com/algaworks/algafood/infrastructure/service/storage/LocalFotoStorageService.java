package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.bouncycastle.util.StoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try{
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));

        } catch (Exception e) {
            throw new StorageException("Não foi possivel armazenar arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            throw new StoreException("Não foi possível excluir arquivo.", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path path  = getArquivoPath(nomeArquivo);
            return FotoRecuperada.builder().inputStream(Files.newInputStream(path)).build();
        } catch (Exception e) {
            throw new StoreException("Falha ao recuperar o arquivo " + nomeArquivo, e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties
                .getLocal()
                .getDiretorioFotos()
                .resolve(Path.of(nomeArquivo));
    }
}
