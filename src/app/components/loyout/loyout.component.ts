import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { AuthServiceService } from '../../authentication/auth-service.service';

@Component({
  selector: 'app-loyout',
  imports: [RouterLink,RouterOutlet],
  templateUrl: './loyout.component.html',
  styleUrl: './loyout.component.css'
})
export class LoyoutComponent {
  constructor(private authService:AuthServiceService) {}
  //considerar servicios esenciales para el header
  // y tambien la reactividad en caso ser necesario
  //por mientras añadir estructura html

  logout(){
    this.authService.logout();
  }
}
