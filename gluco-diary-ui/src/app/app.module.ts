import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { RegisterComponent } from './components/register/register.component';
import { CardComponent } from './components/widgets/card/card.component';
import { FlashCardComponent } from './components/widgets/flash-card/flash-card.component';
import { HomeDashboardComponent } from './components/home-dashboard/home-dashboard.component';
import { GlucoseSummaryCardComponent } from './components/widgets/glucose-summary-card/glucose-summary-card.component';
import { BottomToolbarComponent } from './components/bottom-toolbar/bottom-toolbar.component';
import { ProfileComponent } from './components/profile/profile.component';
import { LoginComponent } from './components/login/login.component';
import { AddReadingComponent } from './components/add-reading/add-reading.component';

import { TableModule } from 'primeng/table';
import { CalendarModule } from 'primeng/calendar';
import { ChartModule } from 'primeng/chart';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {ToastModule} from 'primeng/toast';
import { MessageService } from 'primeng/api';

import { DailyReportComponent } from './components/widgets/graphs/daily-report/daily-report.component';
import { RecordHistoryComponent } from './components/widgets/record-history/record-history.component';

import { LoadingScreenService } from './loading-screen/loading-screen.service';
import { HistoryService } from './services/glucose-history.service';

import { AppHttpInterceptor } from './services/interceptors/app-http.interceptor';
import { HttpErrorInterceptor } from './services/interceptors/http-error.interceptor';
import { LoadingScreenInterceptor } from './loading-screen/loading.interceptor';

import { MonthlyAvgReportComponent } from './components/widgets/graphs/monthly-avg-report/monthly-avg-report.component';
import { NotificationComponent } from './components/widgets/notification/notification.component';
import { LandingComponent } from './landing/landing.component';
import { LoadingScreenComponent } from './loading-screen/loading-screen.component';
import { AuthService } from './services/auth.service';
import { AuthGuardService } from './services/auth-guard.service';
import { GlucoRecordingService } from './services/gluco.service';
import { BrokenWidgetComponent } from './components/widgets/broken-widget/broken-widget.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RegisterComponent,
    CardComponent,
    FlashCardComponent,
    HomeDashboardComponent,
    GlucoseSummaryCardComponent,
    BottomToolbarComponent,
    ProfileComponent,
    LoginComponent,
    AddReadingComponent,
    DailyReportComponent,
    RecordHistoryComponent,
    MonthlyAvgReportComponent,
    NotificationComponent,
    LandingComponent,
    LoadingScreenComponent,
    BrokenWidgetComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    CalendarModule,
    ChartModule,
    TableModule,
    ScrollPanelModule,
    FormsModule,
    ToastModule
  ],
  providers: [
    HistoryService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true
    },
    LoadingScreenService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoadingScreenInterceptor,
      multi: true
    },
    {
      provide : HTTP_INTERCEPTORS,
      useClass: AppHttpInterceptor,
      multi   : true,
    },
    AuthService,
    AuthGuardService,
    GlucoRecordingService,
    MessageService,
    DatePipe
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
