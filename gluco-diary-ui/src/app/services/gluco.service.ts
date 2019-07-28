import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';

import { AuthService } from './auth.service';
import { AddRecordingRequest } from '../models/gluco-service-model/recording-request';
import { SugarLevel } from '../models/gluco-service-model/sugar-level';
import { Recording } from '../models/gluco-service-model/recording';

@Injectable({providedIn: 'root'})
export class GlucoRecordingService {

    constructor(private httpClient: HttpClient, private authService: AuthService) {    }


    public recordGlucose(level: number, frequency: string, measurementType: string, date: Date) {
        const request = new AddRecordingRequest();
        request.date = date;
        const sugarlevel = new SugarLevel();
        sugarlevel.frequency = frequency;
        sugarlevel.level = level;
        sugarlevel.measurementType = measurementType;
        request.sugarLevel = sugarlevel;
        console.log(JSON.stringify(request));
        return this.httpClient.post(environment.blood_glucose, JSON.stringify(request));
    }

    public getAllRecordings(): Observable<Recording[]> {
        return this.httpClient.get<Recording[]>(environment.blood_glucose);
    }

    public getRecordingsForADate(date: string): Observable<Recording[]> {
            return this.httpClient.get<Recording[]>(environment.blood_glucose + '?date=' + date);
    }

}
