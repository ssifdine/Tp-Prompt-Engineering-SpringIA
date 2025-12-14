package ma.saifdine.hd.bdccaiapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour les requêtes de chat
 * Représente les données envoyées par le client
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    // Message de l'utilisateur
    private String message;

    // Modèle à utiliser (optionnel)
    private String model;

    // Température pour la génération (optionnel)
    private Double temperature;
}
