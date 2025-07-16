import { Component } from '@angular/core';
import { ActivatedRoute, RouterOutlet } from '@angular/router';
import { Auth } from './service/auth';
import { Login } from "./components/login/login";
import { PublicContent } from "./components/public-content/public-content";
import { PrivateContent } from "./components/private-content/private-content";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [Login, PublicContent, PrivateContent, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'FrontEnd';

  componentToShow: string = "welcome";

  constructor(private http: Auth, private route: ActivatedRoute) {}

  ngOnInit(): void {
    console.log("app loaded")
    console.log(this.route)
    this.route.queryParams
      .subscribe(params => {
        if (params["code"] !== undefined) {
          this.http.getToken(params["code"]).subscribe(result => {
            if (result === true) {
              this.componentToShow = "private";
            } else {
              this.componentToShow = "public";
            }
          });
        }
      }
    );
  }
}
