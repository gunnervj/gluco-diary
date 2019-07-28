import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { LoadingScreenService } from './loading-screen.service';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { Injectable } from '@angular/core';

@Injectable()
export class LoadingScreenInterceptor implements HttpInterceptor {
    activeRequests = 0;

    constructor(private loadingScreenService: LoadingScreenService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      console.log('Inside Loading Interceptor');
      if (this.activeRequests === 0) {
        console.log('initiating loading screen');
        this.loadingScreenService.startLoading();
      }
      this.activeRequests++;
      return next.handle(request).pipe(
        finalize(() => {
          this.activeRequests--;
          if (this.activeRequests === 0) {
            console.log('stopping loading screen');
            this.loadingScreenService.stopLoading();
          }
        })
      );
    }
}
