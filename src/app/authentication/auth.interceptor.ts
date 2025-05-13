import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthServiceService } from './auth-service.service';

const EXCLUDED_URLS=['/api/login', '/api/register']; //etc rutas que no requieren interceptarse

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  //adjuntará el token en cada peticion
  const authService=inject(AuthServiceService);
  const token=authService.getToken();
  const shouldAttachToken = token && !EXCLUDED_URLS.some(url => req.url.includes(url));
  if (shouldAttachToken) {
    const clonedReq=req.clone({
      setHeaders:{
        Authorization:`Bearer ${token}`
      }
    });
    return next(clonedReq);
  }
  return next(req);
};
