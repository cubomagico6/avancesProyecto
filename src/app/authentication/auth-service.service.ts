import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { authDto } from '../model/AuthUser/authdto';
import { dtoJwt } from '../model/AuthUser/dtoJwt';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  private apiUrl='http://localhost:8080/api/';
  constructor(private http:HttpClient, private router:Router) {}

  login(dtoAuth: authDto):Observable<dtoJwt>{
    return this.http.post<dtoJwt>(`${this.apiUrl}`+"login",dtoAuth).pipe(
      tap(response=> {
        this.saveToken(response.token); //guarda el token automaticamente
        console.log(response.token);
      })
    )
  }

    logout():void{ //utilizar
    localStorage.removeItem('token');// eliminar
    this.router.navigate(['/login']); // 🔄 Redirige a la página de login
  }

  saveToken(token:string):void{
    localStorage.setItem('token',token);
  }

  getToken(): string | null{
    return localStorage.getItem('token');
  }

  isAuthenticated():boolean{
    return !!this.getToken(); //retorna true si el token existe
  }
}
