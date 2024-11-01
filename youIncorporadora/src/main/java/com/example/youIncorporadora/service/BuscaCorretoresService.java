package com.example.youIncorporadora.service;

import org.apache.commons.codec.language.Metaphone;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuscaCorretoresService {

    public List<String> buscaFoneticamente(List<String> nomes) {
        Metaphone metaphone = new Metaphone();
        return nomes.stream()
                .map(metaphone::metaphone)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> buscaIncremental(String termo, List<String> nomes) {
        if (termo == null || termo.isEmpty()) {
            System.out.println("Termo de busca não pode ser vazio.");
            return Collections.emptyList();
        }

        Metaphone metaphone = new Metaphone();
        String termoCodificado = metaphone.metaphone(termo);

        List<String> resultados = nomes.stream()
                .filter(nome -> nome != null && !metaphone.metaphone(nome).equals(termoCodificado)) // Barrando nomes com a mesma codificação
                .filter(nome -> nome != null && nome.toLowerCase().contains(termo.toLowerCase())) // Busca os que contêm o termo
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            resultados = nomes.stream()
                    .filter(nome -> nome != null && !metaphone.metaphone(nome).equals(termoCodificado)) // Barrando nomes com a mesma codificação
                    .filter(nome -> nome != null && metaphone.metaphone(nome).contains(termoCodificado)) // Busca foneticamente
                    .collect(Collectors.toList());
        }

        if (resultados.isEmpty() || resultados.stream().noneMatch(nome -> nome.equalsIgnoreCase(termo))) {
            System.out.println("Nenhum nome encontrado para o termo: " + termo);
        }

        return resultados;
    }




}
