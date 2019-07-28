import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ServiceError } from '../../models/service-error';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

export class HttpErrorInterceptor implements HttpInterceptor {

    constructor(private router: Router, private authService: AuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let errorList: ServiceError[] = [];
        console.log('Inside Handler');
        return next.handle(request)
                    .pipe(
                        catchError((error: HttpErrorResponse) => {
                            console.log(error.status);
                            if (error.error instanceof ErrorEvent) {
                                const errorDet = new ServiceError();
                                errorDet.code = 0;
                                errorDet.message = 'Something went wrong. Please try after sometime.';
                                errorList.push(errorDet);
                            } else {
                                if ( error.status === 403 ) {
                                    this.authService.logOff();
                                    this.router.navigate(['login']);
                                } else if ( error.status === 400 ) {
                                    console.log(JSON.stringify(error.error.errors));
                                    errorList = error.error.errors;
                                } else if ( error.status === 404 ) {
                                    console.log(JSON.stringify(error.error.errors));
                                    errorList = error.error.errors;
                                } else {
                                    const errorDet = new ServiceError();
                                    errorDet.code = 0;
                                    errorDet.message = 'Something went wrong . Please try after sometime.';
                                    errorList.push(errorDet);
                                }

                                return throwError(JSON.stringify(errorList));
                            }
                        })
                    );
    }
}
