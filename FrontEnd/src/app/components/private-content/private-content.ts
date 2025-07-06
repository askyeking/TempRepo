import { Component } from '@angular/core';
import { Auth } from '../../service/auth';
import { Message } from '../../models/message';

@Component({
  selector: 'app-private-content',
  imports: [],
  templateUrl: './private-content.html',
  styleUrl: './private-content.css'
})
export class PrivateContent {


  content: string = "";

  constructor(private http: Auth) {}

  ngOnInit(): void {
    this.http.getPrivateMessages().subscribe((data: Message) => this.content = data.message);
  }

}
