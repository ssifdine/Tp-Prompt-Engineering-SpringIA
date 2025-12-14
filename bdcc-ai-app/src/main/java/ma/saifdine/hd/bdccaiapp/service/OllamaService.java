package ma.saifdine.hd.bdccaiapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.saifdine.hd.bdccaiapp.dto.ChatRequest;
import ma.saifdine.hd.bdccaiapp.dto.ChatResponse;
import ma.saifdine.hd.bdccaiapp.entity.Message;
import ma.saifdine.hd.bdccaiapp.repository.MessageRepository;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service principal pour gérer les interactions avec Ollama
 * Gère le chat, l'historique et les conversations
 */
@Service
@RequiredArgsConstructor // Génère un constructeur avec les champs final
@Slf4j // Active les logs avec lombok
public class OllamaService {

    private final ChatModel chatModel;
    private final MessageRepository messageRepository;

    /**
     * Envoie un message simple à Ollama et retourne la réponse
     * @param userMessage Le message de l'utilisateur
     * @return La réponse de l'IA
     */
    public String chat(String userMessage) {
        log.info("📨 Message reçu: {}", userMessage);

        // Créer un prompt avec le message
        Prompt prompt = new Prompt(new UserMessage(userMessage));

        // Appeler le modèle et obtenir la réponse
        org.springframework.ai.chat.model.ChatResponse response = chatModel.call(prompt);

        String aiResponse = response.getResult().getOutput().getContent();
        log.info("🤖 Réponse générée: {}", aiResponse);

        return aiResponse;
    }

    /**
     * Envoie un message avec options avancées et sauvegarde l'historique
     * @param request La requête contenant le message et les options
     * @return ChatResponse avec toutes les informations
     */
    public ChatResponse chatWithHistory(ChatRequest request) {
        long startTime = System.currentTimeMillis();

        log.info("📨 Traitement du message: {}", request.getMessage());

        // Construire les options si spécifiées
        OllamaOptions.Builder optionsBuilder = OllamaOptions.builder();

        if (request.getModel() != null) {
            optionsBuilder.model(request.getModel());
        }
        if (request.getTemperature() != null) {
            optionsBuilder.temperature(request.getTemperature());
        }

        OllamaOptions options = optionsBuilder.build();

        // Créer le prompt avec les options
        Prompt prompt = new Prompt(new UserMessage(request.getMessage()), options);

        // Appeler le modèle
        org.springframework.ai.chat.model.ChatResponse aiResponse = chatModel.call(prompt);
        String responseText = aiResponse.getResult().getOutput().getContent();

        // Calculer le temps de réponse
        long responseTime = System.currentTimeMillis() - startTime;

        // Sauvegarder dans la base de données
        Message message = new Message();
        message.setUserMessage(request.getMessage());
        message.setAiResponse(responseText);
        message.setModel(request.getModel() != null ? request.getModel() : "llama2");
        message.setResponseTime(responseTime);
        message.setTimestamp(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        log.info("✅ Message sauvegardé avec ID: {}", savedMessage.getId());
        log.info("⏱️ Temps de réponse: {}ms", responseTime);

        // Construire la réponse
        return ChatResponse.builder()
                .response(responseText)
                .model(message.getModel())
                .timestamp(message.getTimestamp())
                .responseTime(responseTime)
                .messageId(savedMessage.getId())
                .build();
    }

    /**
     * Récupère l'historique complet des conversations
     * @return Liste de tous les messages
     */
    public List<Message> getHistory() {
        log.info("📚 Récupération de l'historique");
        return messageRepository.findAll();
    }

    /**
     * Récupère les N derniers messages
     * @return Liste des 10 derniers messages
     */
    public List<Message> getRecentHistory() {
        log.info("📚 Récupération des messages récents");
        return messageRepository.findTop10ByOrderByTimestampDesc();
    }

    /**
     * Streaming de réponse (pour les réponses en temps réel)
     * @param userMessage Le message de l'utilisateur
     * @return Flux de tokens de réponse
     */
    public Flux<String> chatStream(String userMessage) {
        log.info("🌊 Démarrage du streaming pour: {}", userMessage);

        Prompt prompt = new Prompt(new UserMessage(userMessage));

        // Stream la réponse token par token
        return chatModel.stream(prompt)
                .map(response -> response.getResult().getOutput().getContent());
    }

    /**
     * Supprime l'historique complet
     */
    public void clearHistory() {
        log.warn("🗑️ Suppression de l'historique");
        messageRepository.deleteAll();
    }
}