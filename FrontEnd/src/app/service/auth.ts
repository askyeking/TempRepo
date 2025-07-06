import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Token } from '../models/token';
import { Message } from '../models/message';

@Injectable({
  providedIn: 'root'
})
export class Auth {

  token: string = "";

  constructor(private http: HttpClient) { }

  private baseUrl: string = "http://localhost:8080";
  private messagesUrl: string = this.baseUrl + "/public/messages"
  private privateMessagesUrl: string = this.baseUrl + "/messages"
  private loginUrl: string = this.baseUrl + "/auth/url"

  getMessages(): Observable<Message> {
    return this.http.get<any>(this.messagesUrl);
  }

  getLoginUrl(): Observable<any> {
    return this.http.get<any>(this.loginUrl);
  }

  getPrivateMessages(): Observable<any> {
    return this.http.get<any>(this.privateMessagesUrl, {headers: new HttpHeaders({"Authorization": "Bearer " + this.token})});
  }

  getToken(code: string): Observable<boolean> {
    return this.http.get<Token>("http://localhost:8080/auth/callback?code=" + code, {observe: "response"})
      .pipe(map((response: HttpResponse<Token>) => {
        if (response.status === 200 && response.body !== null) {
          this.token = response.body.token;
          return true;
        } else {
          return false;
        }
      }));
  }
}
