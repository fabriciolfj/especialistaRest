package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class CadastroFotoProdutoService {

    private final ProdutoRepository produtoRepository;
    private final FotoStorageService fotoStorageService;
    String nomeArquivoExistente;

    public FotoProduto findById(Long produtoId, Long restauranteId ) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Foto do produto não encontrada"));
    }

    public FotoRecuperada findFoto(String nomeArquivo) {
        return fotoStorageService.recuperar(nomeArquivo);
    }

    @Transactional
    public void delete(Long produtoId, Long restauranteId) {
       produtoRepository.findFotoById(restauranteId, produtoId).map(foto -> {
           produtoRepository.delete(foto);
           fotoStorageService.remover(foto.getNomeArquivo());
           return foto;
       });

    }

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream inputStream) {
        var restauranteId = foto.getRestauranteId();
        var produtoId = foto.getProduto().getId();
        var novoNomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
        nomeArquivoExistente = null;

        produtoRepository.findFotoById(restauranteId, produtoId)
                .map(f -> {
                    nomeArquivoExistente = f.getNomeArquivo();
                    produtoRepository.delete(f);
                    fotoStorageService.remover(f.getNomeArquivo());
                    return f;
                });

        foto.setNomeArquivo(novoNomeArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        fotoStorageService.substituir(nomeArquivoExistente, NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .contentType(foto.getContentType())
                .inputStream(inputStream)
                .build());

        return foto;
    }


}
