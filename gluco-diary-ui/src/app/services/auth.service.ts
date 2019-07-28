import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ServiceError } from '../models/service-error';
import { User } from '../models/user';

import { environment } from '../../environments/environment';
import { Login } from '../models/login';
import { UserService } from './user.service';

@Injectable({providedIn: 'root'})
export class AuthService {

    constructor(private httpClient: HttpClient) {    }

    public checkToken(): Observable<User> {
        const token = localStorage.getItem('token');
        return this.httpClient
                    .get<User>(environment.user_base_url + environment.user_validate_token);
    }

    public logOff() {
        localStorage.removeItem('token');
        localStorage.removeItem('source');
        localStorage.removeItem('isAuthenticated');
    }

    public setLoggedInUser(token: string, referer: string) {
        localStorage.setItem('token', token);
        localStorage.setItem('source', referer);
        localStorage.setItem('isAuthenticated', 'true');
    }

    public setLoggedInUserProfile( user: string) {
        localStorage.setItem('loggedin-user', JSON.stringify(user));
    }

    public getLoggedInUser() {
        return JSON.parse(localStorage.getItem('loggedin-user'));
    }


    public getUserAuthStatus() {
        return localStorage.getItem('isAuthenticated') === 'true';
    }

}
