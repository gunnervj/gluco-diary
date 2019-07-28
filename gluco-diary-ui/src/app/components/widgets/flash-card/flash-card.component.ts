import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { GlucoRecordingService } from 'src/app/services/gluco.service';
import { DatePipe } from '@angular/common';
import { DailyReading } from 'src/app/models/daily-reading';
import { GlucoseReading } from 'src/app/models/glucose-reading';
import { count } from 'rxjs/operators';
import { Recording } from 'src/app/models/gluco-service-model/recording';
import { ServiceError } from 'src/app/models/service-error';

@Component({
  selector: 'app-flash-card',
  templateUrl: './flash-card.component.html',
  styleUrls: ['./flash-card.component.css']
})
export class FlashCardComponent implements OnInit {
  todaysReading: DailyReading = new DailyReading();
  isBroken = false;
  todaysAverage = 0;
  public errorList: ServiceError[] = [];

  constructor(private glucoseRecordingService: GlucoRecordingService,
              private datepipe: DatePipe,
              private authService: AuthService) { }

  public getUserName(): string {
    return JSON.parse(this.authService.getLoggedInUser()).name;
  }

  ngOnInit() {
    this.getData();
  }

  private getData() {
    this.glucoseRecordingService.getRecordingsForADate(this.datepipe.transform(new Date(Date.now()), 'yyyy-MM-dd')).subscribe(
      res => {
        this.processResponse(res);
        this.calculateAverage();
      },
      (err) => {
        this.errorList = JSON.parse(err);
        if ( this.errorList ) {
          for (const error of this.errorList) {
            if ( error.code !== 0 ) {
              this.isBroken = true;
            }
          }
        }
      }
    );
  }


  private processResponse(res: Recording[]) {

    for (const recording of res) {
      if (recording) {
        const dailyReading = new DailyReading();
        dailyReading.date = this.datepipe.transform(recording.date, 'yyyy-MM-dd');
        const glucoseReadingMorning = new GlucoseReading();
        const glucoseReadingAfterNoon = new GlucoseReading();
        const glucoseReadingNight = new GlucoseReading();
        for (const level of recording.levels) {
          if (level.frequency === 'Morning') {
            if (level.measurementType === 'Pre_Meal') {
              glucoseReadingMorning.beforeFood = level.level;
            } else {
              glucoseReadingMorning.afterFood = level.level;
            }
          }
          if (level.frequency === 'Afternoon') {
            if (level.measurementType === 'Pre_Meal') {
              glucoseReadingAfterNoon.beforeFood = level.level;
            } else {
              glucoseReadingAfterNoon.afterFood = level.level;
            }
          }
          if (level.frequency === 'Night') {
            if (level.measurementType === 'Pre_Meal') {
              glucoseReadingNight.beforeFood = level.level;
            } else {
              glucoseReadingNight.afterFood = level.level;
            }
          }
        }
        dailyReading.afternoon = glucoseReadingAfterNoon;
        dailyReading.morning = glucoseReadingMorning;
        dailyReading.night = glucoseReadingNight;
        this.todaysReading = dailyReading;
      }
    }
  }
  private calculateAverage() {
    let counter = 0;
    if (this.todaysReading.morning.afterFood) {
      counter++;
      this.todaysAverage = this.todaysAverage + this.todaysReading.morning.afterFood;
    }
    if (this.todaysReading.morning.beforeFood) {
      counter++;
      this.todaysAverage = this.todaysAverage + this.todaysReading.morning.beforeFood;
    }
    if (this.todaysReading.afternoon.afterFood) {
      counter++;
      this.todaysAverage = this.todaysAverage + this.todaysReading.afternoon.afterFood;
    }
    if (this.todaysReading.afternoon.beforeFood) {
      counter++;
      this.todaysAverage = this.todaysAverage + this.todaysReading.afternoon.beforeFood;
    }
    if (this.todaysReading.night.afterFood) {
      counter++;
      this.todaysAverage = this.todaysAverage + this.todaysReading.night.afterFood;
    }
    if (this.todaysReading.night.beforeFood) {
      counter++;
      this.todaysAverage = this.todaysAverage + this.todaysReading.night.beforeFood;
    }
    this.todaysAverage = Math.floor(this.todaysAverage / counter);
  }
}
