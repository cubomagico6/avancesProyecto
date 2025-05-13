import { Component, OnInit } from '@angular/core';
import {  FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuariosService } from '../../../services/usuarios.service';
import { ActivatedRoute, Router } from '@angular/router';
import { usuarioRequestDto, usuarioResponseDto } from '../../../model/usuario';

@Component({
  selector: 'app-usuarios-formularios',
  imports: [ReactiveFormsModule],
  templateUrl: './usuarios-formularios.component.html',
  styleUrl: './usuarios-formularios.component.css'
})
export class UsuariosFormulariosComponent implements OnInit {
  formulario!: FormGroup;
  modoEditar = false;
  idUsuario?: number;

  constructor(
    private usuarioService: UsuariosService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

ngOnInit(): void {
  this.modoEditar = !!this.route.snapshot.paramMap.get('id');

  this.formulario = new FormGroup({
    nombre: new FormControl('', Validators.required),
    correo: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', Validators.required),
    rol: new FormControl('', Validators.required)
  });

  if (!this.modoEditar) {
    this.formulario.addControl('password', new FormControl('', Validators.required));
  }

  if (this.modoEditar) {
    this.idUsuario = +this.route.snapshot.paramMap.get('id')!;
    this.usuarioService.buscar(this.idUsuario).subscribe({
      next: (data) => {
        this.formulario.patchValue({
          nombre: data.nombre,
          correo: data.correo,
          username: data.username,
          rol: data.rol
        });
      },
      error: (e) => {
        console.error('Error al cargar usuario', e);
      }
    });
  }
}

  guardar(): void {
    if (this.formulario.invalid) return;

    const formValue = this.formulario.value;
    console.log(formValue);
    
    // Armamos el objeto para enviar (usuarioRequestDto)
    const usuario: usuarioRequestDto = {
      nombre: formValue.nombre,
      correo: formValue.correo,
      username: formValue.username,
      password: formValue.password,
      rol: formValue.rol
    };

    if (this.modoEditar && this.idUsuario) {
      this.usuarioService.actualizar(this.idUsuario, usuario).subscribe({
        next: (data) => {
          console.log('Usuario actualizado con éxito:', data);
          this.router.navigate(['/panel/usuarios']);
        },
        error: (e) => {
          console.error('Error al actualizar usuario', e);
        }
      });
    } else {
      this.usuarioService.crear(usuario).subscribe({
        next: (data) => {
          console.log('Usuario creado con éxito:', data);
          this.router.navigate(['/panel/usuarios']);
        },
        error: (e) => {
          console.error('Error al crear usuario', e);
        }
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/panel/usuarios']);
  }
}
