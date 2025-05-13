import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AuthServiceService } from '../../authentication/auth-service.service';
import { Router } from '@angular/router';
import { JsonPipe } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, JsonPipe],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

    formularioLogin!: FormGroup;
    constructor(public authService:AuthServiceService, public router:Router) {}

    ngOnInit(): void {
      this.formularioLogin=new FormGroup({
        username: new FormControl(''), 
        password: new FormControl('')
      });
  }

  login(){
    console.log(this.formularioLogin.value);
    this.authService.login(this.formularioLogin.value).subscribe({
      next: (data)=>{
        this.router.navigate(['/panel']);
        this.formularioLogin.reset();
      },
      error: (e)=>{
        console.log('Error en login',e);
      }
    })
  }
}
