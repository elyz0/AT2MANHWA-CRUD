package com.example.demo;
 
import java.io.File;
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManhwaServiceJson {

    private final ObjectMapper mapper = new ObjectMapper(); 
    private final File arquivo = new File("manhwas.json");
 
    //Pesquisar TypeReference 
    
    public List<Manhwa> listarTodos() { 
         
        if (!arquivo.exists()) return new ArrayList<>(); 

        try {
            return Arrays.asList(mapper.readValue(arquivo, Manhwa[].class));
        }  
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Manhwa buscarPorId(Long id) {
        return listarTodos() 
        .stream() 
        .filter(m -> m.getId().equals(id)) 
        .findFirst() 
        .orElse(null);
    }

    public Manhwa inserirOuAtualizar(Manhwa manhwa) {
        List<Manhwa> manhwas = new ArrayList<>(listarTodos()); 

        if (manhwa.getId() == null) { 
            // Gera novo ID
            long newId = manhwas.stream().mapToLong(m -> m.getId()).max().orElse(0L) + 1;
            manhwa.setId(newId);
            manhwas.add(manhwa);
        } else { 
            // Atualiza existente
            manhwas.removeIf(m -> m.getId().equals(manhwa.getId()));
            manhwas.add(manhwa);
        } 

        salvarArquivo(manhwas);
        return manhwa;
    }

    public void deletarPorId(Long id) {
        List<Manhwa> manhwas = new ArrayList<>(listarTodos());
        manhwas.removeIf(m -> m.getId().equals(id));
        salvarArquivo(manhwas);
    }

    private void salvarArquivo(List<Manhwa> manhwas) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, manhwas);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        //especificar quais exceções realmente podem ocorrer (melhor opçõs)
    }
}

