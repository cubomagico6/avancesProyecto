import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { usuarioRequestDto,usuarioResponseDto } from '../model/usuario';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {
  constructor(private http:HttpClient) { }
  private apiUrl="http://localhost:8080/api/user";
  //verificar bien
  crear(usuario:usuarioRequestDto):Observable<usuarioResponseDto>{
    return this.http.post<usuarioResponseDto>(`${this.apiUrl}`,usuario);
  }

  actualizar(id:number, usuario:usuarioRequestDto):Observable<usuarioResponseDto>{
    return this.http.put<usuarioResponseDto>(`${this.apiUrl}/${id}`,usuario);
  }

  listar():Observable<usuarioResponseDto[]>{
    return this.http.get<usuarioResponseDto[]>(`${this.apiUrl}`);
  }

  buscar(id:number):Observable<usuarioResponseDto>{
    return this.http.get<usuarioResponseDto>(`${this.apiUrl}/${id}`);
  }

  eliminar(id:number):Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
