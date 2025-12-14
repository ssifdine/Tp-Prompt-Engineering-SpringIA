# 🤖 Chat IA avec Ollama

Application de chat avec intelligence artificielle locale utilisant Spring Boot et Angular.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-green)
![Angular](https://img.shields.io/badge/Angular-17-red)
![Java](https://img.shields.io/badge/Java-17-orange)

## 📸 Aperçu

Chat moderne avec un modèle d'IA local (Llama 2, Mistral, etc.) qui sauvegarde automatiquement l'historique des conversations.

![Interface Chat](pictures/image1.png)


![Interface Chat](pictures/image2.png)


![Interface Chat](pictures/image3.png)



## ✨ Fonctionnalités

- 💬 Chat en temps réel avec IA
- 📚 Historique des conversations
- 🎨 Interface moderne et responsive
- 🎛️ Choix du modèle et de la température
- ⚡ Affichage du temps de réponse

## 🚀 Installation Rapide

### 1. Installer Ollama

**Windows:**
```bash
winget install Ollama.Ollama
```

**Mac:**
```bash
brew install ollama
```

**Linux:**
```bash
curl -fsSL https://ollama.ai/install.sh | sh
```

### 2. Télécharger un modèle
```bash
ollama serve
ollama pull llama3.2
```

### 3. Backend Spring Boot
```bash
cd bdcc-ai-app
mvn spring-boot:run
```
Le backend démarre sur **http://localhost:8080**

### 4. Frontend Angular
```bash
cd bdcc-chat-angular
npm install
ng serve
```
Le frontend s'ouvre sur **http://localhost:4200**

## 📁 Structure du Projet

```
bdcc-ai-app/                    # Backend Spring Boot
├── src/main/java/
│   ├── config/                 # Configuration Ollama
│   ├── controller/             # API REST
│   ├── service/                # Logique métier
│   ├── entity/                 # Entités JPA
│   └── repository/             # Accès base de données
└── pom.xml

bdcc-chat-angular/              # Frontend Angular
├── src/app/
│   ├── models/                 # Interfaces TypeScript
│   ├── services/               # Services HTTP
│   ├── app.component.ts        # Composant principal
│   └── app.component.html      # Template
└── package.json
```

## 🎯 Utilisation

1. Démarrez Ollama: `ollama serve`
2. Lancez le backend: `mvn spring-boot:run`
3. Lancez le frontend: `ng serve`
4. Ouvrez http://localhost:4200
5. Commencez à chatter !

## 🔌 API Backend

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/chat?message={text}` | Envoi simple |
| POST | `/api/chat` | Envoi avec options |
| GET | `/api/history` | Voir l'historique |
| DELETE | `/api/history` | Effacer l'historique |
| GET | `/api/health` | État de l'API |

### Exemple POST
```json
{
  "message": "Bonjour",
  "model": "llama3.2",
  "temperature": 0.7
}
```

## ⚙️ Configuration

### Backend (`application.properties`)
```properties
# Ollama
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=llama2

# Base de données
spring.datasource.url=jdbc:h2:mem:ollama_db
spring.h2.console.enabled=true

# CORS
spring.web.cors.allowed-origins=http://localhost:4200
```

### Changer le modèle
Dans `app.component.ts`:
```typescript
availableModels = [
  { value: 'llama3.2', label: 'Llama 3.2' },
  { value: 'quen3', label: 'Quen 3' },
  { value: 'gamma', label: 'Gamma' }
];
```

## 🐛 Problèmes Fréquents

**Ollama ne répond pas:**
```bash
ollama serve
```

**Modèle introuvable:**
```bash
ollama pull llama3.2
```

**Erreur CORS:**
Vérifiez `spring.web.cors.allowed-origins` dans `application.properties`

**Backend ne démarre pas:**
```bash
mvn clean install
```

## 🛠️ Technologies

**Backend:**
- Spring Boot 3.4.0
- Spring AI 1.0.0-M5
- H2 Database
- Lombok

**Frontend:**
- Angular 17
- TypeScript
- RxJS

**IA:**
- Ollama
- Llama 3.2 / Quen 3

## 📦 Build Production

**Backend:**
```bash
mvn clean package
java -jar target/bdcc-ai-app-0.0.1-SNAPSHOT.jar
```

**Frontend:**
```bash
ng build --production
```

## 👨‍💻 Auteur

**Saif Dine HD**
- GitHub: [@votre-username](https://github.com/votre-username)

## 📝 Licence

MIT License

## 🙏 Ressources

- [Spring AI](https://docs.spring.io/spring-ai/reference/)
- [Ollama](https://ollama.ai/)
- [Angular](https://angular.io/)

---

⭐ N'oubliez pas de mettre une étoile si ce projet vous aide !
