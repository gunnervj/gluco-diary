import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';

import { HistoryService } from 'src/app/services/glucose-history.service';
import { DailyReading } from 'src/app/models/daily-reading';
import { GlucoRecordingService } from 'src/app/services/gluco.service';
import { GlucoseReading } from 'src/app/models/glucose-reading';

@Component({
  selector: 'app-record-history',
  templateUrl: './record-history.component.html',
  styleUrls: ['./record-history.component.css']
})
export class RecordHistoryComponent implements OnInit {
  readings: DailyReading[] = [];
  isError = false;

  constructor( private historyService: HistoryService,
               private glucoseRecordingService: GlucoRecordingService,
               public datepipe: DatePipe ) { }

  ngOnInit() {
    this.getData();
  }


  private getData() {
    this.glucoseRecordingService.getAllRecordings().subscribe(res => {
      console.log( JSON.stringify(res) );
      for (const recording of res) {
        if (recording) {
          const dailyReading = new DailyReading();
          dailyReading.date = this.datepipe.transform(recording.date, 'yyyy-MM-dd');
          const glucoseReadingMorning = new GlucoseReading();
          const glucoseReadingAfterNoon = new GlucoseReading();
          const glucoseReadingNight = new GlucoseReading();
          for ( const level of recording.levels ) {
            if ( level.frequency === 'Morning' ) {
              if ( level.measurementType === 'Pre_Meal') {
                glucoseReadingMorning.beforeFood = level.level;
              } else {
                glucoseReadingMorning.afterFood = level.level;
              }
            }
            if ( level.frequency === 'Afternoon' ) {
              if ( level.measurementType === 'Pre_Meal') {
                glucoseReadingAfterNoon.beforeFood = level.level;
              } else {
                glucoseReadingAfterNoon.afterFood = level.level;
              }
            }
            if ( level.frequency === 'Night' ) {
              if ( level.measurementType === 'Pre_Meal') {
                glucoseReadingNight.beforeFood = level.level;
              } else {
                glucoseReadingNight.afterFood = level.level;
              }
            }
          }
          dailyReading.afternoon = glucoseReadingAfterNoon;
          dailyReading.morning = glucoseReadingMorning;
          dailyReading.night = glucoseReadingNight;
          this.readings.push(dailyReading);
        }
      }
    },
      (err) => {
        this.isError = true;
      });
  }

}
