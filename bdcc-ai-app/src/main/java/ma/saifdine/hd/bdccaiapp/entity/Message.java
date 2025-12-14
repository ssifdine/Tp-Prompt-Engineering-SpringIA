package ma.saifdine.hd.bdccaiapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entité représentant un message dans la conversation
 * Stocke l'historique des échanges avec l'IA
 */
@Entity
@Data // Génère getters, setters, toString, equals, hashCode
@NoArgsConstructor // Constructeur sans arguments
@AllArgsConstructor // Constructeur avec tous les arguments
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Message envoyé par l'utilisateur
    @Column(columnDefinition = "TEXT", nullable = false)
    private String userMessage;

    // Réponse générée par l'IA
    @Column(columnDefinition = "TEXT")
    private String aiResponse;

    // Modèle utilisé pour la réponse
    @Column(length = 50)
    private String model;

    // Date et heure de la conversation
    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Temps de réponse en millisecondes
    private Long responseTime;

    /**
     * Méthode appelée avant la persistance
     * Initialise automatiquement le timestamp
     */
    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}