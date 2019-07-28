import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';

import { UserService } from '../../services/user.service';
import { ServiceError } from '../../models/service-error';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  public user: User;
  public allowSignup = false;
  public isError = false;
  public errorList: ServiceError[] = [];
  public isSuccess = false;

  constructor(private registerService: UserService, private router: Router) { }

  ngOnInit() {
    this.user = new User();
    this.user.gender = '';
    if (localStorage.getItem('isAuthenticated') === 'true') {
      this.router.navigate(['dashboard']);
    }
  }

  public onRegisterUser() {
    this.isError = false;
    console.log('Registeting user');
    this.registerService
          .registerUser(this.user)
          .subscribe(
              (res) => {
                this.isSuccess = true;
              },
              (err) => {
                this.isError = true;
                console.log(err);
                this.errorList = JSON.parse(err);
                console.log(this.errorList);
                window.scrollTo(0, 0);
              }
          );
  }

}
