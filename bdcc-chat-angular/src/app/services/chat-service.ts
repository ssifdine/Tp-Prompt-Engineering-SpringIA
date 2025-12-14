import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {ChatRequest} from '../models/chat-request.model';
import {Observable} from 'rxjs';
import {ChatResponse} from '../models/chat-response.model';

@Injectable({
  providedIn: 'root',
})
export class ChatService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  /**
   * Envoie un message au backend (POST)
   * @param request Requête contenant le message et les options
   * @returns Observable de la réponse
   */
  sendMessage(request: ChatRequest): Observable<ChatResponse> {
    return this.http.post<ChatResponse>(`${this.apiUrl}/chat`, request);
  }

  /**
   * Envoie un message simple (GET - pour tests rapides)
   * @param message Message à envoyer
   * @returns Observable de la réponse (string)
   */
  sendSimpleMessage(message: string): Observable<string> {
    const params = new HttpParams().set('message', message);
    return this.http.get(`${this.apiUrl}/chat`, {
      params,
      responseType: 'text'
    });
  }

  /**
   * Récupère l'historique complet
   * @returns Observable du tableau d'historique
   */
  getHistory(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/history`);
  }

  /**
   * Récupère l'historique récent (10 derniers messages)
   * @returns Observable du tableau d'historique
   */
  getRecentHistory(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/history/recent`);
  }

  /**
   * Efface tout l'historique
   * @returns Observable de confirmation
   */
  clearHistory(): Observable<string> {
    return this.http.delete(`${this.apiUrl}/history`, { responseType: 'text' });
  }

  /**
   * Vérifie la santé de l'API
   * @returns Observable du statut
   */
  checkHealth(): Observable<string> {
    return this.http.get(`${this.apiUrl}/health`, { responseType: 'text' });
  }

}
