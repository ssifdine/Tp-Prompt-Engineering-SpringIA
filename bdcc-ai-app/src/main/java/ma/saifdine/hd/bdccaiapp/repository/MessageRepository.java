package ma.saifdine.hd.bdccaiapp.repository;

import ma.saifdine.hd.bdccaiapp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour gérer les opérations CRUD sur les messages
 * Spring Data JPA génère automatiquement les implémentations
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Trouve tous les messages après une certaine date
    List<Message> findByTimestampAfter(LocalDateTime date);

    // Trouve les messages par modèle utilisé
    List<Message> findByModel(String model);

    // Trouve les 10 derniers messages
    List<Message> findTop10ByOrderByTimestampDesc();
}