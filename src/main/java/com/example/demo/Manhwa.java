package com.example.demo;

public class Manhwa {
 
    
    private Long id;

    private String titulo;
    private String autor;
    private String genero;
    private String status;
    private String descricao;
    private Double nota; 
    private String capa; // URL da imagem da capa

    // Getters e Setters 
  
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    } 
     
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    } 
     
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    } 
     
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    } 
     
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    } 
     
    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    } 

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

}
