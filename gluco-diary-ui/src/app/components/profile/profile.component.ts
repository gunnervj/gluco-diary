import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { ServiceError } from 'src/app/models/service-error';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  public user: User;
  constructor(private userService: UserService, private router: Router, private authService: AuthService) { }

  ngOnInit() {
    this.user = new User();
    this.user.name = 'Vijay';
    this.getUserName();
  }

  public getUserName() {
    this.user = JSON.parse(this.authService.getLoggedInUser());
  }

  public logOff() {
    console.log('Logging off');
    this.userService.logoff();
    this.router.navigate(['']);
  }

}
