/**
 * Modèle pour les réponses du backend
 */
export interface ChatResponse {
  response: string;
  model: string;
  timestamp: string;
  responseTime: number;
  messageId: number;
}
