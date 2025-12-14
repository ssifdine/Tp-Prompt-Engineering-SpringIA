package ma.saifdine.hd.bdccaiapp.controller;

import lombok.RequiredArgsConstructor;
import ma.saifdine.hd.bdccaiapp.dto.ChatRequest;
import ma.saifdine.hd.bdccaiapp.dto.ChatResponse;
import ma.saifdine.hd.bdccaiapp.entity.Message;
import ma.saifdine.hd.bdccaiapp.service.OllamaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Contrôleur REST pour exposer les endpoints de l'API Ollama
 * Gère toutes les requêtes HTTP pour le chat avec l'IA
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permet les requêtes depuis n'importe quelle origine
public class OllamaController {

    private final OllamaService ollamaService;

    /**
     * Endpoint simple pour tester l'API
     * GET /api/chat?message=Bonjour
     */
    @GetMapping("/chat")
    public ResponseEntity<String> simpleChat(@RequestParam String message) {
        String response = ollamaService.chat(message);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint avancé avec historique et options
     * POST /api/chat
     * Body: { "message": "Bonjour", "model": "llama3.2", "temperature": 0.7 }
     */
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        ChatResponse response = ollamaService.chatWithHistory(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de streaming pour réponses en temps réel
     * GET /api/chat/stream?message=Raconte-moi une histoire
     * Retourne un flux de texte (Server-Sent Events)
     */
    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestParam String message) {
        return ollamaService.chatStream(message);
    }

    /**
     * Récupère l'historique complet des conversations
     * GET /api/history
     */
    @GetMapping("/history")
    public ResponseEntity<List<Message>> getHistory() {
        List<Message> history = ollamaService.getHistory();
        return ResponseEntity.ok(history);
    }

    /**
     * Récupère les conversations récentes
     * GET /api/history/recent
     */
    @GetMapping("/history/recent")
    public ResponseEntity<List<Message>> getRecentHistory() {
        List<Message> history = ollamaService.getRecentHistory();
        return ResponseEntity.ok(history);
    }

    /**
     * Supprime l'historique
     * DELETE /api/history
     */
    @DeleteMapping("/history")
    public ResponseEntity<String> clearHistory() {
        ollamaService.clearHistory();
        return ResponseEntity.ok("Historique supprimé avec succès");
    }

    /**
     * Endpoint de santé pour vérifier que l'API fonctionne
     * GET /api/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("✅ API is running!");
    }
}