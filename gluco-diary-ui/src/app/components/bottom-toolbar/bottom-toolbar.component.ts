import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-bottom-toolbar',
  templateUrl: './bottom-toolbar.component.html',
  styleUrls: ['./bottom-toolbar.component.css']
})
export class BottomToolbarComponent implements OnInit {
  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

}
