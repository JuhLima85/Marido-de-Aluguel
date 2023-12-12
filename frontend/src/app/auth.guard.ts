import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree,  Router} from '@angular/router';
import { AuthService } from './services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate { 

  constructor(
    private authService: AuthService,
    private router: Router
  ){ }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {

    const autenticado = this.authService.isAutenticado();

    if(autenticado){
      return true;
    }else{
      this.router.navigate(['/login'])
      return false;
    }
  }  
}
