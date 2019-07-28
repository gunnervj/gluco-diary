import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../models/user';

import { environment } from '../../environments/environment';
import { Login } from '../models/login';
import { LoginResponse } from '../models/login-response';
import { retry, catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({providedIn: 'root'})
export class UserService {

    constructor(private httpClient: HttpClient, private authService: AuthService) {    }

    public registerUser(user: User) {
        return this.httpClient
            .post(environment.user_base_url + environment.user_api_register, JSON.stringify(user));
    }


    public login(login: Login): Observable<LoginResponse> {
        return this.httpClient
            .post<LoginResponse>(environment.user_base_url + environment.user_api_login, JSON.stringify(login));
    }

    public userProfile(): Observable<User> {
        return this.httpClient.get<User>(environment.user_base_url + environment.user_profile);
    }

    public logoff() {
        this.httpClient
            .patch(environment.user_base_url + environment.user_logout, '')
            .pipe(
                retry(1)
            );
        this.authService.logOff();
    }

}
