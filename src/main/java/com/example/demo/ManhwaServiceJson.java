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
     
    //Sanitizar aqui tbm porque o json pode ter sido editado manualmente
    public List<Manhwa> listarTodos() { 
         
        if (!arquivo.exists()) return new ArrayList<>(); 

        try {
            Manhwa[] manhwaArray = mapper.readValue(arquivo, Manhwa[].class);
            return sanitizeList(Arrays.asList(manhwaArray));
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
 
        sanitizeManhwa(manhwa); 

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
        List<Manhwa> manhwas = listarTodos();
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
     
    private void sanitizeManhwa(Manhwa m) {
        if (m.getTitulo() != null) {
            m.setTitulo(m.getTitulo().trim());
        }
        if (m.getAutor() != null) {
            m.setAutor(m.getAutor().trim());
        }
        if (m.getGenero() != null) {
            m.setGenero(m.getGenero().trim().toLowerCase());
        }
        if (m.getStatus() != null) {
            m.setStatus(m.getStatus().trim().toLowerCase());
        }
        if (m.getDescricao() != null) {
            m.setDescricao(m.getDescricao().trim());
        }
        if (m.getCapa() != null) {
            m.setCapa(m.getCapa().trim());
        }

        // Nota deve estar entre 0.0 e 10.0
        if (m.getNota() != null) {
            double nota = m.getNota();
            if (nota < 0.0) m.setNota(0.0);
            if (nota > 10.0) m.setNota(10.0);
        }
    } 
      
    private boolean isValid(Manhwa m) {
    return m != null
        && m.getId() == null || m.getId() >= 0 // ID pode ser null se for novo
        && m.getTitulo() != null && !m.getTitulo().isBlank();
    }

    private List<Manhwa> sanitizeList(List<Manhwa> manhwas) {
    List<Manhwa> list = new ArrayList<>();
    for (Manhwa m : manhwas) {
        if (isValid(m)) {
            sanitizeManhwa(m); // ou ManhwaSanitizer.sanitize(m);
            list.add(m);
        }
    }
    return list;
}


}

