/**
 * Modèle représentant un message dans l'interface
 */
export interface Message {
  content: string;
  isUser: boolean;
  timestamp: Date;
  isSystem?: boolean;
  responseTime?: number;
}
