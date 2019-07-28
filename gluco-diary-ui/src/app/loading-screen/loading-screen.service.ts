import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LoadingScreenService {

  private isLoading = false;
  loadingStatus = new Subject<boolean>();

  get loading(): boolean {
    return this.isLoading;
  }

  set loading(value) {
    this.isLoading = value;
    this.loadingStatus.next(value);
  }

  startLoading() {
    console.log('setting loading to true');
    this.loading = true;
  }

  stopLoading() {
    this.loading = false;
  }

}
