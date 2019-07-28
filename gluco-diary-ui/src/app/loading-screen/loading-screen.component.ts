import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { LoadingScreenService } from './loading-screen.service';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-loading-screen',
  templateUrl: './loading-screen.component.html',
  styleUrls: ['./loading-screen.component.css']
})
export class LoadingScreenComponent implements OnInit, OnDestroy {
  loading = false;
  loadingSubscription: Subscription;

  constructor(private loadingScreenService: LoadingScreenService) { }

  ngOnInit() {
    console.log('init');
    this.loadingSubscription = this.loadingScreenService
                                    .loadingStatus
                                    .subscribe((value) => {
                                      console.log('subscription =>' + value);
                                      this.loading = value;
                                    });
  }

  ngOnDestroy() {
    this.loadingSubscription.unsubscribe();
  }


}
