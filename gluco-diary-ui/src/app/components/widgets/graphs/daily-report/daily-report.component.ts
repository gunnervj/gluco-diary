import { Component, OnInit } from '@angular/core';
import { DailyReading } from 'src/app/models/daily-reading';
import { DatePipe } from '@angular/common';
import { GlucoRecordingService } from 'src/app/services/gluco.service';
import { GlucoseReading } from 'src/app/models/glucose-reading';
import { ServiceError } from 'src/app/models/service-error';



@Component({
  selector: 'app-daily-report',
  templateUrl: './daily-report.component.html',
  styleUrls: ['./daily-report.component.css']
})
export class DailyReportComponent implements OnInit {
  todaysReading: DailyReading = new DailyReading();
  data: any;
  beforeFood = [];
  afterFood = [];
  isBroken = false;
  public errorList: ServiceError[] = [];
  
  constructor(private glucoseRecordingService: GlucoRecordingService,
              public datepipe: DatePipe) {
  }

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

      this.beforeFood.push(this.todaysReading.morning.beforeFood);
      this.beforeFood.push(this.todaysReading.afternoon.beforeFood);
      this.beforeFood.push(this.todaysReading.night.beforeFood);



      this.afterFood.push(this.todaysReading.morning.afterFood);
      this.afterFood.push(this.todaysReading.afternoon.afterFood);
      this.afterFood.push(this.todaysReading.night.afterFood);
      console.log(this.beforeFood);
      this.enableGraphDataSet();
    },
      (err) => {
        this.errorList = JSON.parse(err);
        if ( this.errorList ) {
          for (const error of this.errorList) {
            if ( error.code === 0 ) {
              this.isBroken = true;
            }
          }
        }
      });
  }

  public enableGraphDataSet() {
    this.data = {
      labels: ['Morning', 'After-Noon', 'Night'],
      datasets: [
          {
              label: 'Blood Glucose Level - Before Food',
              data: this.beforeFood,
              fill: false,
              borderColor: '#e74c3c'
          },
          {
              label: 'Recommended Level - Before Food',
              data: [95, 95, 95],
              fill: false,
              borderColor: '#3498db'
          },
          {
            label: 'Blood Glucose Level - After Food',
            data: this.afterFood,
            fill: false,
            borderColor: '#f39c12'
        },
        {
            label: 'Recommended Level - After Food',
            data: [120, 120, 120],
            fill: false,
            borderColor: '#34495e'
        }
      ]
    }
  }

}
