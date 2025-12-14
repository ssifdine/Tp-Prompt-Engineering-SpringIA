import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Message} from '../../models/message.model';
import {ChatService} from '../../services/chat-service';
import {ChatRequest} from '../../models/chat-request.model';
import {FormsModule} from '@angular/forms';
import {DatePipe, NgClass, NgForOf} from '@angular/common';

@Component({
  selector: 'app-chat-ia',
  imports: [
    FormsModule,
    NgClass,
    DatePipe,
    NgForOf
  ],
  templateUrl: './chat-ia.html',
  styleUrl: './chat-ia.css',
})
export class ChatIA implements OnInit {

  title = 'Angular Chat with Ollama';

  // Référence au conteneur de messages pour auto-scroll
  @ViewChild('messagesContainer') private messagesContainer!: ElementRef;

  // Messages de la conversation
  messages: Message[] = [];

  // Message en cours de saisie
  userMessage: string = '';

  // État du chargement
  isLoading: boolean = false;

  // Historique des messages (depuis le backend)
  chatHistory: any[] = [];

  // Affichage de l'historique
  showHistory: boolean = false;

  // Configuration du modèle
  selectedModel: string = 'llama2';
  temperature: number = 0.7;

  // Modèles disponibles
  availableModels = [
    { value: 'qwen3', label: 'Qwen 3 (Polyvalent / Raisonnement)' },
    { value: 'gemma3:1b', label: 'Gemma 3 1B (Léger & Rapide)' },
    { value: 'llama3.2', label: 'Llama 3.2 (Avancé)' }
  ];


  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    // Charger l'historique au démarrage
    this.loadHistory();

    // Message de bienvenue
    this.addSystemMessage('Bienvenue ! Posez-moi vos questions. 🤖');
  }

  /**
   * Envoie un message au backend
   */
  sendMessage(): void {
    // Vérifier que le message n'est pas vide
    if (!this.userMessage.trim()) {
      return;
    }

    // Ajouter le message de l'utilisateur à l'interface
    this.addUserMessage(this.userMessage);

    // Préparer la requête
    const request: ChatRequest = {
      message: this.userMessage,
      model: this.selectedModel,
      temperature: this.temperature
    };

    // Sauvegarder le message pour l'effacer après
    const currentMessage = this.userMessage;
    this.userMessage = '';

    // Activer l'état de chargement
    this.isLoading = true;

    // Envoyer la requête au backend
    this.chatService.sendMessage(request).subscribe({
      next: (response) => {
        // Ajouter la réponse de l'IA
        this.addAiMessage(response.response, response.responseTime);
        this.isLoading = false;

        // Recharger l'historique
        this.loadHistory();
      },
      error: (error) => {
        console.error('Erreur lors de l\'envoi du message:', error);
        this.addSystemMessage('❌ Erreur: Impossible de contacter le serveur. Vérifiez que Ollama et le backend sont démarrés.');
        this.isLoading = false;
      }
    });
  }

  /**
   * Ajoute un message utilisateur à l'interface
   */
  addUserMessage(content: string): void {
    this.messages.push({
      content: content,
      isUser: true,
      timestamp: new Date()
    });
    this.scrollToBottom();
  }

  /**
   * Ajoute un message de l'IA à l'interface
   */
  addAiMessage(content: string, responseTime?: number): void {
    const message: Message = {
      content: content,
      isUser: false,
      timestamp: new Date()
    };

    if (responseTime) {
      message.responseTime = responseTime;
    }

    this.messages.push(message);
    this.scrollToBottom();
  }

  /**
   * Ajoute un message système à l'interface
   */
  addSystemMessage(content: string): void {
    this.messages.push({
      content: content,
      isUser: false,
      isSystem: true,
      timestamp: new Date()
    });
    this.scrollToBottom();
  }

  /**
   * Scroll automatique vers le bas
   */
  scrollToBottom(): void {
    setTimeout(() => {
      if (this.messagesContainer) {
        this.messagesContainer.nativeElement.scrollTop =
          this.messagesContainer.nativeElement.scrollHeight;
      }
    }, 100);
  }

  /**
   * Charge l'historique depuis le backend
   */
  loadHistory(): void {
    this.chatService.getHistory().subscribe({
      next: (history) => {
        this.chatHistory = history;
      },
      error: (error) => {
        console.error('Erreur lors du chargement de l\'historique:', error);
      }
    });
  }

  /**
   * Affiche/Cache l'historique
   */
  toggleHistory(): void {
    this.showHistory = !this.showHistory;
    if (this.showHistory) {
      this.loadHistory();
    }
  }

  /**
   * Efface la conversation actuelle
   */
  clearConversation(): void {
    if (confirm('Voulez-vous vraiment effacer la conversation actuelle ?')) {
      this.messages = [];
      this.addSystemMessage('Conversation effacée. 🗑️');
    }
  }

  /**
   * Efface tout l'historique (backend)
   */
  clearHistory(): void {
    if (confirm('Voulez-vous vraiment effacer TOUT l\'historique ?')) {
      this.chatService.clearHistory().subscribe({
        next: () => {
          this.chatHistory = [];
          this.addSystemMessage('Historique effacé. 🗑️');
        },
        error: (error) => {
          console.error('Erreur lors de l\'effacement:', error);
        }
      });
    }
  }

  /**
   * Gestion de la touche Enter
   */
  onKeyPress(event: KeyboardEvent): void {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.sendMessage();
    }
  }
}
