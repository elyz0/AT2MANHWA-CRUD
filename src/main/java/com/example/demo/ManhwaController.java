package com.example.demo;
 
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@RequestMapping("/manhwa") 
@CrossOrigin(origins = "*")
public class ManhwaController { 

    private final ManhwaServiceJson service = new ManhwaServiceJson();
 
    //(GET /manhwa)
    @GetMapping
    public List<Manhwa> getAll() {
        return service.listarTodos();
    }
 
    //(GET /manhwa/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Manhwa> getById(@PathVariable Long id) {
        Manhwa m = service.buscarPorId(id);
        return m != null ? ResponseEntity.ok(m) : ResponseEntity.notFound().build();
    }
 
    //(POST /manhwa)
    @PostMapping
    public Manhwa create(@RequestBody Manhwa manhwa) {
        return service.inserirOuAtualizar(manhwa);
    }
 
    //(DELETE /manhwa/{id})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deletarPorId(id);
    }
   
    //(PUT /manhwa/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Manhwa> update(@PathVariable Long id, @RequestBody Manhwa manhwa) {
        if (service.buscarPorId(id) == null) return ResponseEntity.notFound().build();
        manhwa.setId(id);
        return ResponseEntity.ok(service.inserirOuAtualizar(manhwa));
    }
}
