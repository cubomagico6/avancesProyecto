import { Routes } from '@angular/router';
import { InicioComponent } from './pages/inicio/inicio.component';
import { LoginComponent } from './pages/login/login.component';
import { LoyoutComponent } from './components/loyout/loyout.component';
import { authGuard, preventLoggedInAccessGuard } from './authentication/auth.guard';
import { UsuariosComponent } from './pages/User/usuarios/usuarios.component';
import { UsuariosFormulariosComponent } from './pages/User/usuarios-formularios/usuarios-formularios.component';

export const routes: Routes = [
  // Login route
    { path: 'login', component: LoginComponent, canActivate:[preventLoggedInAccessGuard] },
  // Panel (layout + secciones), cuando el usuario navega a /panel se carga el loyoutC
    {
    path: 'panel',
    component: LoyoutComponent,
    canActivate: [authGuard], //sin auth nisiquiera cargará las rutas hijas
    children: [
        { path: '', redirectTo: 'inicio', pathMatch: 'full' }, //en /panel/"raiz" se carga inicio como ContenidoPrincip
        { path: 'inicio', component: InicioComponent }, //mejorar esta parte pues aun esta vacia
      // CRUD de usuarios
        { path: 'usuarios', component: UsuariosComponent },
        { path: 'usuarios/crear', component: UsuariosFormulariosComponent },
        { path: 'usuarios/editar/:id', component: UsuariosFormulariosComponent }
    ]
    },

  // Ruta raíz redirige al login
    { path: '', redirectTo: 'login', pathMatch: 'full' },
  // Wildcard por si la ruta no existe
    { path: '**', redirectTo: 'login' }
];
