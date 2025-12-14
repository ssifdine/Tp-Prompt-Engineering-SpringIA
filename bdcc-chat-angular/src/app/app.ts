import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ChatIA} from './components/chat-ia/chat-ia';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ChatIA],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'bdcc-chat-angular';
}
