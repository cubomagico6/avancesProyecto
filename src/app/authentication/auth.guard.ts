import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthServiceService } from './auth-service.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService=inject(AuthServiceService);
  const router=inject(Router);
  if (authService.isAuthenticated()) {
    return true;
  } //rutas hijas
  return router.createUrlTree(['/login']);
  //tener en cuenta el canActivate: [authGuard] en el route
};
export const preventLoggedInAccessGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthServiceService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    return router.createUrlTree(['/panel']); // Redirige al panel si ya está autenticado
  }

  return true; // Permite el acceso a la ruta /login si no está autenticado
};
//Agregar otro authGuard?
