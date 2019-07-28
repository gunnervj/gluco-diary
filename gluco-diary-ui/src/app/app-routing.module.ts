import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './components/register/register.component';
import { HomeDashboardComponent } from './components/home-dashboard/home-dashboard.component';
import { ProfileComponent } from './components/profile/profile.component';
import { LoginComponent } from './components/login/login.component';
import { AddReadingComponent } from './components/add-reading/add-reading.component';
import { RecordHistoryComponent } from './components/widgets/record-history/record-history.component';
import { LandingComponent } from './landing/landing.component';
import { AuthGuardService as AuthGuard } from './services/auth-guard.service';

const routes: Routes = [
 { path: '', component: LandingComponent },
 { path: 'register', component: RegisterComponent },
 { path: 'login', component: LoginComponent },
 { path: 'dashboard', component: HomeDashboardComponent, canActivate: [AuthGuard]  },
 { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
 { path: 'add', component: AddReadingComponent, canActivate: [AuthGuard] },
 { path: 'history', component: RecordHistoryComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
