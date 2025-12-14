package ma.saifdine.hd.bdccaiapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BdccAiAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdccAiAppApplication.class, args);
        System.out.println("✅ Application démarrée avec succès!");
        System.out.println("📝 Accédez à l'API via: http://localhost:8080");
        System.out.println("💬 Testez le chat: http://localhost:8080/api/chat?message=Bonjour");    }

}
