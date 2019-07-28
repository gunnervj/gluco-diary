import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { ServiceError } from 'src/app/models/service-error';
import { Login } from 'src/app/models/login';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public login: Login;
  public isError = false;
  public errorList: ServiceError[] = [];
  public isSuccess = false;

  constructor(private userService: UserService, public router: Router, public authService: AuthService) { }

  ngOnInit() {
    this.login = new Login();
    if (this.authService.getUserAuthStatus()) {
      this.router.navigate(['dashboard']);
    }
  }

  public doLoginUser() {
    localStorage.removeItem('token');
    this.isError = false;
    console.log('Login user');
    this.userService
      .login(this.login)
      .subscribe(
        (resp) => {
          this.isSuccess = true;
          this.authService.setLoggedInUser(resp.token.toString(), 'login');
          this.router.navigate(['dashboard']);
          this.getUserProfile();
        },
        (err) => {
          this.isError = true;
          this.errorList = JSON.parse(err);
          window.scrollTo(0, 0);
        }
      );
  }

  private getUserProfile() {
    this.userService.userProfile().subscribe(
      (user) => {
        this.authService.setLoggedInUserProfile(JSON.stringify(user));
      },
      (err) => {
        this.authService.logOff();
        this.router.navigate(['login']);
      }
    );
  }
}
