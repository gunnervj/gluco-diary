import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { GlucoseHistory } from '../models/glucose-history';
import { DailyReading } from '../models/daily-reading';
import { GlucoseReading } from '../models/glucose-reading';

@Injectable({providedIn: 'root'})
export class HistoryService {
    history: GlucoseHistory;
    constructor(private http: HttpClient) {

    }

    getHistoryReadings() {

        this.history = new GlucoseHistory();

        const dailyReadin1 = new DailyReading();
        dailyReadin1.date = '01/01/2019' ;

        const readingMorning = new GlucoseReading();
        readingMorning.afterFood = 121;
        readingMorning.beforeFood = 96;
        dailyReadin1.morning = readingMorning;

        const readingAfternoon = new GlucoseReading();
        readingAfternoon.afterFood = 121;
        readingAfternoon.beforeFood = 96;
        dailyReadin1.afternoon = readingAfternoon;

        const readingNight = new GlucoseReading();
        readingNight.afterFood = 121;
        readingNight.beforeFood = 96;
        dailyReadin1.night = readingNight;

        this.history.readings.push(dailyReadin1);

        const dailyReadin2 = new DailyReading();
        dailyReadin2.date = '01/02/2019' ;

        const readingMorning1 = new GlucoseReading();
        readingMorning1.afterFood = 121;
        readingMorning1.beforeFood = 96;
        dailyReadin2.morning = readingMorning1;

        const readingAfternoon1 = new GlucoseReading();
        readingAfternoon1.afterFood = 121;
        readingAfternoon1.beforeFood = 96;
        dailyReadin2.afternoon = readingAfternoon1;

        const readingNight1 = new GlucoseReading();
        readingNight1.afterFood = 121;
        readingNight1.beforeFood = 96;
        dailyReadin2.night = readingNight1;

        this.history.readings.push(dailyReadin2);

        return this.history;
    }
}
