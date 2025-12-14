package ma.saifdine.hd.bdccaiapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO pour les réponses de chat
 * Représente les données renvoyées au client
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    // Réponse de l'IA
    private String response;

    // Modèle utilisé
    private String model;

    // Timestamp de la réponse
    private LocalDateTime timestamp;

    // Temps de traitement en ms
    private Long responseTime;

    // ID du message sauvegardé
    private Long messageId;
}
