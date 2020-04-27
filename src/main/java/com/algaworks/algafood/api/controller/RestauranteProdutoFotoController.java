package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroFotoProdutoService;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    private final CadastroFotoProdutoService fotoProdutoService;
    private final CadastroProdutoService produtoService;
    private final FotoProdutoModelAssembler assembler;

    @CheckSecurity.RESTAURANTE.GERENCIAR_FUNCIONAMENTO
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
        var produto = produtoService.findById(produtoId, restauranteId);
        MultipartFile arquivo = fotoProdutoInput.getArquivo();
        var foto = criarFotoProduto(fotoProdutoInput, produto, arquivo);

        return assembler.toModel(fotoProdutoService.salvar(foto, arquivo.getInputStream()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoModel findFotoProdutoRestaurante(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
        return assembler.toModel(fotoProdutoService.findById(produtoId, restauranteId));
    }

    @DeleteMapping
    public void deleteFoto(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
        fotoProdutoService.delete(produtoId, restauranteId);
    }

    @GetMapping
    public ResponseEntity<?> findFoto(@PathVariable Long produtoId, @PathVariable Long restauranteId, @RequestHeader(name = "Accept") String acceptHeader) {
        try {
            var produto = fotoProdutoService.findById(produtoId, restauranteId);
            var foto = fotoProdutoService.findFoto(produto.getNomeArquivo());
            var mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);
            var mediaTypeFoto = MediaType.parseMediaType(produto.getContentType());

            verificarCompatibilidadeMediaType(mediaTypeAceitas, mediaTypeFoto);

            if(foto.temURl()){
                return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, foto.getUrl()).build();
            }

            return ResponseEntity.ok().body(new InputStreamResource(foto.getInputStream()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void verificarCompatibilidadeMediaType(List<MediaType> mediaTypeAceitas, MediaType mediaTypeFoto) throws HttpMediaTypeNotAcceptableException {

        var compativel = mediaTypeAceitas.stream().anyMatch(media -> media.isCompatibleWith(mediaTypeFoto));

        if(!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
        }
    }

    private FotoProduto criarFotoProduto(@Valid FotoProdutoInput fotoProdutoInput, Produto produto, MultipartFile arquivo) {
        var foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());
        return foto;
    }
}
