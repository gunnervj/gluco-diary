import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable()
export class AuthGuardService implements CanActivate {
    isUserAuthenticated = false;
    constructor(private auth: AuthService, private router: Router) {}

    canActivate(): boolean {
      const source = localStorage.getItem('source');
      if (source === 'login') {
        localStorage.removeItem('source');
        return true;
      } else {
        this.auth.checkToken().subscribe(
        (res) => {
            this.isUserAuthenticated = true;
            if ( !this.isUserAuthenticated ) {
              this.router.navigate(['login']);
              return false;
            }
          }
        );
        return true;
     }
    }
}
