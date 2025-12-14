package ma.saifdine.hd.bdccaiapp.config;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration pour le modèle Ollama
 * Cette classe configure la connexion avec Ollama et définit les options du modèle
 */
@Configuration
public class OllamaConfig {

    // URL de base d'Ollama (par défaut localhost:11434)
    @Value("${spring.ai.ollama.base-url:http://localhost:11434}")
    private String baseUrl;

    // Modèle à utiliser (llama2, mistral, codellama, etc.)
    @Value("${spring.ai.ollama.chat.options.model:llama2}")
    private String model;

    // Température pour la génération (0.0 = déterministe, 1.0 = créatif)
    @Value("${spring.ai.ollama.chat.options.temperature:0.7}")
    private Double temperature;

    /**
     * Crée et configure l'API Ollama
     * @return Instance de OllamaApi configurée
     */
    @Bean
    public OllamaApi ollamaApi() {
        return new OllamaApi(baseUrl);
    }

    /**
     * Crée le modèle de chat Ollama avec les options configurées
     * @param ollamaApi L'API Ollama configurée
     * @return Instance de OllamaChatModel prête à l'emploi
     */
    @Bean
    public OllamaChatModel ollamaChatModel(OllamaApi ollamaApi) {
        // Configuration des options du modèle (nouvelle méthode)
        OllamaOptions options = OllamaOptions.builder()
                .model(model)
                .temperature(temperature)
                .build();

        // Utilisation du builder pour créer le modèle
        return OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(options)
                .build();
    }
}