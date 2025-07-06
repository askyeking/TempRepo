import { Component } from '@angular/core';
import { Auth } from '../../service/auth';
import { Message } from '../../models/message';

@Component({
  selector: 'app-public-content',
  imports: [],
  templateUrl: './public-content.html',
  styleUrl: './public-content.css'
})
export class PublicContent {

   content: string = "";

  constructor(private http: Auth) {}

  ngOnInit(): void {
    this.http.getMessages().subscribe((data: Message) => this.content = data.message);

  }

}
