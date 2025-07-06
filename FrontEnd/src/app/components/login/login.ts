import { Component } from '@angular/core';
import { Auth } from '../../service/auth';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

    url: string = "";

  constructor(private auth: Auth) {}

  ngOnInit(): void {
     this.auth.getLoginUrl().subscribe((data: any) => {
      this.url = data.url;
      console.log(data);
      console.log(this.url);
    });
  }
}
