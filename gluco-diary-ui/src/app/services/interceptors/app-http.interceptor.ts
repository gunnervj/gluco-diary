import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class AppHttpInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      if ( null != localStorage.getItem('token') ) {
          const bearer = 'Bearer ' + localStorage.getItem('token');
          req = req.clone({
            setHeaders: {
              'Content-Type'  : 'application/json; charset=utf-8',
              'Accept'        : 'application/json',
              'Authorization' : `${bearer}`
            },
          });
        } else {
          req = req.clone({
                setHeaders: {
                  'Content-Type'  : 'application/json; charset=utf-8',
                  'Accept'        : 'application/json'
                },
          });
      }
      return next.handle(req);
    }
}
