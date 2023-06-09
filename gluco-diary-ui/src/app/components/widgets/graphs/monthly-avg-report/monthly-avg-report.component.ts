import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-monthly-avg-report',
  templateUrl: './monthly-avg-report.component.html',
  styleUrls: ['./monthly-avg-report.component.css']
})
export class MonthlyAvgReportComponent implements OnInit {
  data: any;
  constructor() { 
    this.data = {
    labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    datasets: [
        {
            label: 'After Food',
            backgroundColor: '#42A5F5',
            borderColor: '#1E88E5',
            data: [65, 59, 80, 81, 56, 55, 40]
        },
        {
            label: 'Before Food',
            backgroundColor: '#9CCC65',
            borderColor: '#7CB342',
            data: [28, 48, 40, 19, 86, 27, 90]
        }
     ]
    }

  }

  ngOnInit() {
    
  }

}
