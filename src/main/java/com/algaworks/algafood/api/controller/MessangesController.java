package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.MessageResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mensagens")
public class MessangesController {

    @GetMapping
    public List<MessageResponse> get(){
        return lista();
    }

    private List<MessageResponse> lista() {
        var data = new ArrayList<MessageResponse>();
        for(int i = 0; i <  10000; i++){
            data.add(new MessageResponse("Fabricio", "teste " + i));
        }

        return data;
    }
}
