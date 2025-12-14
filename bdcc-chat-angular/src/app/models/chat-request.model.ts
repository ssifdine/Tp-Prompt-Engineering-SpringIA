/**
 * Modèle pour les requêtes au backend
 */
export interface ChatRequest {
  message: string;
  model?: string;
  temperature?: number;
}
