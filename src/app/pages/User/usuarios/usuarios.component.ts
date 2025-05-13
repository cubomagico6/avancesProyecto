import { AfterViewInit, Component, OnInit } from '@angular/core';
import { usuarioResponseDto } from '../../../model/usuario';
import { UsuariosService } from '../../../services/usuarios.service';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-usuarios',
  imports: [],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css'
})
export class UsuariosComponent implements OnInit{
  //¿Se podra arreglar más o así esta bien?
  usuarios: usuarioResponseDto[]=[];
  mensajeExito: string = '';
  mensajeError: string = '';
  timeoutRef: any;

  constructor(private usuarioService:UsuariosService, private router:Router) {}
  ngOnInit():void{
    //cada que se recargue este componente se vuelve a listar
    this.cargarUsuarios();
  }
  cargarUsuarios(){
    this.usuarioService.listar().subscribe({
      next: (data)=>{
        this.usuarios=data;
      },
      error: (e)=>{
        console.log(e);
      }
    });
  }

  eliminarUsuario(id: number){
    this.usuarioService.eliminar(id).subscribe({
      next: (data)=> {
        //opcional un mensaje de exito
        this.cargarUsuarios()},
      error: (error)=>{
      console.log(error);
      //opcional mostrar un mensaje de error
    }
  });
  }
  editarUsuario(id:number){
    this.router.navigate(['/panel/usuarios/editar', id]);
  }
  crearUsuario(){
    this.router.navigate(['/panel/usuarios/crear']);
  }
}
