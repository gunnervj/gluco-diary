import { Component, OnInit } from '@angular/core';
import { AddRecordingRequest } from 'src/app/models/gluco-service-model/recording-request';
import { GlucoRecordingService } from 'src/app/services/gluco.service';
import { ServiceError } from 'src/app/models/service-error';

import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-reading',
  templateUrl: './add-reading.component.html',
  styleUrls: ['./add-reading.component.css']
})
export class AddReadingComponent implements OnInit {
  public readingDate: Date;
  public frequency: string;
  public beforeAfter: string;
  public reading: number;
  public errorList: ServiceError[] = [];
  public isSuccess = false;
  public isError = false;
  public today: Date;
  constructor(private glucoRecordingService: GlucoRecordingService, private messageService: MessageService, private router: Router) { }

  ngOnInit() {
    this.today = new Date(Date.now());
    this.readingDate = this.today;
  }

  public saveReading() {
    console.log(this.readingDate);
    console.log(this.reading);
    this.glucoRecordingService
      .recordGlucose(this.reading, this.frequency, this.beforeAfter, this.readingDate)
      .subscribe(
        (res) => {
          this.isSuccess = true;
          this.messageService.add({severity: 'success', summary: 'Added New Reading.'});
          if (localStorage.getItem('isAuthenticated') === 'true') {
            this.router.navigate(['dashboard']);
          }
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
