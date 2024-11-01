package com.example.youIncorporadora.controller;

import com.example.youIncorporadora.enity.Corretor;
import com.example.youIncorporadora.service.BuscaCorretoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/corretores")
public class BuscaCorretoresController {

    @Autowired
    private BuscaCorretoresService buscaCorretoresService;

    @PostMapping("/busca-fonetica")
    public List<String> buscaFoneticamente(@RequestBody List<Corretor> corretores) {
        List<String> nomes = corretores.stream()
                .map(Corretor::getApelido__c)
                .collect(Collectors.toList());
        return buscaCorretoresService.buscaFoneticamente(nomes);
    }

    @PostMapping("/busca-incremental")
    public ResponseEntity<List<String>> buscaIncremental(@RequestParam String termo, @RequestBody List<Corretor> corretores) {
        List<String> nomes = corretores.stream()
                .map(Corretor::getApelido__c)
                .collect(Collectors.toList());

        List<String> resultados = buscaCorretoresService.buscaIncremental(termo, nomes);

        if (resultados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList("Nenhum nome encontrado para o termo: " + termo));
        }

        return ResponseEntity.ok(resultados);
    }

}
