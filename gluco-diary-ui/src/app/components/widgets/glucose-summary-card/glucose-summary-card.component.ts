import { Component, OnInit } from '@angular/core';
import { GlucoRecordingService } from 'src/app/services/gluco.service';
import { DatePipe } from '@angular/common';
import { DailyReading } from 'src/app/models/daily-reading';
import { GlucoseReading } from 'src/app/models/glucose-reading';
import { ServiceError } from 'src/app/models/service-error';

@Component({
  selector: 'app-glucose-summary-card',
  templateUrl: './glucose-summary-card.component.html',
  styleUrls: ['./glucose-summary-card.component.css']
})
export class GlucoseSummaryCardComponent implements OnInit {
  todaysReading: DailyReading = new DailyReading();
  public errorList: ServiceError[] = [];
  isBroken = false;
  constructor(private glucoseRecordingService: GlucoRecordingService,
              public datepipe: DatePipe) { }

  ngOnInit() {
    this.getData();
  }

  private getData() {
    this.glucoseRecordingService.getRecordingsForADate(this.datepipe.transform(new Date(Date.now()), 'yyyy-MM-dd')).subscribe(res => {
      console.log(JSON.stringify(res));
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
    },
      (err) => {
        this.errorList = JSON.parse(err);
        if ( this.errorList ) {
          for (const error of this.errorList) {
            console.log(error);
            if ( error.code === 0 ) {
              this.isBroken = true;
            }
          }
        }
      });
  }
}
