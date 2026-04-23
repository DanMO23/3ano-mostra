package com.backend.pi.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.pi.backend.model.*;
import com.backend.pi.backend.repository.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/mapa")
@CrossOrigin(origins = "*") // Para não ter problema com o React
public class MapaController {

    @Autowired
    private ArcoRepository arcoRepository;

    @Autowired
    private ProgressoZonaRepository progressoZonaRepository;

    @Autowired
    private InventarioAlunoRepository inventarioAlunoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ItemInventarioRepository itemInventarioRepository;

    @GetMapping("/zonas")
    public ResponseEntity<List<Arco>> getZonas() {
        return ResponseEntity.ok(arcoRepository.findAll());
    }

    @GetMapping("/progresso/{alunoId}")
    public ResponseEntity<Map<String, Object>> getProgressoAluno(@PathVariable Long alunoId) {
        List<ProgressoZona> progresso = progressoZonaRepository.findByAlunoId(alunoId);
        List<InventarioAluno> inventario = inventarioAlunoRepository.findByAlunoId(alunoId);

        Map<String, Object> response = new HashMap<>();
        response.put("zonasCompletadas", progresso);
        response.put("inventario", inventario);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/progresso/{alunoId}/zona/{arcoId}/concluir")
    public ResponseEntity<?> concluirZona(@PathVariable Long alunoId, @PathVariable Long arcoId) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(alunoId);
        Optional<Arco> arcoOpt = arcoRepository.findById(arcoId);

        if (alunoOpt.isPresent() && arcoOpt.isPresent()) {
            ProgressoZona progresso = new ProgressoZona(alunoOpt.get(), arcoOpt.get(), true);
            progressoZonaRepository.save(progresso);
            return ResponseEntity.ok("Zona concluída com sucesso.");
        }
        return ResponseEntity.badRequest().body("Aluno ou Zona não encontrados.");
    }

    @PostMapping("/progresso/{alunoId}/item/{itemId}/adicionar")
    public ResponseEntity<?> adicionarItem(@PathVariable Long alunoId, @PathVariable Long itemId) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(alunoId);
        Optional<ItemInventario> itemOpt = itemInventarioRepository.findById(itemId);

        if (alunoOpt.isPresent() && itemOpt.isPresent()) {
            InventarioAluno inv = new InventarioAluno(alunoOpt.get(), itemOpt.get());
            inventarioAlunoRepository.save(inv);
            return ResponseEntity.ok("Item adicionado ao inventário.");
        }
        return ResponseEntity.badRequest().body("Aluno ou Item não encontrados.");
    }
}
