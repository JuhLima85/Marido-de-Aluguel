import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Usuario } from '../login/usuario';
import { Updatedto } from '../login/update-credenciais/update-dto'
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {  
  
  apiURL: string = environment.apiUrlBase + "/api/auth"   
  jwtHelperService: JwtHelperService = new JwtHelperService();

  constructor(
    private http: HttpClient  
  ) { }

  obterToken(){
    const tokenString = localStorage.getItem('token')    
    if(tokenString){      
      return tokenString;
    }
    return null;
  }

  encerrarSessao(){
    localStorage.removeItem('token') 
  }
  
  getUsuarioAutenticado(){
    const token = this.obterToken();
    if(token){
      const decodedToken: any = jwtDecode(token);     
      return {        
        idUsuario: decodedToken.userId,
        nomeUsuario: decodedToken.sub,
        perfil: decodedToken.perfil 
      };
    }    
    return null;
  }

  isAutenticado() : boolean {
    const token = this.obterToken();
    if(token){
      const isExpirado = this.jwtHelperService.isTokenExpired(token)
      return !isExpirado;
    }
    return false;
  }

  registrar(usuario: Usuario): Observable<any> {     
    return this.http.post<any>(`${this.apiURL}/register`, usuario)   
      .pipe(
        catchError((errorResponse: HttpErrorResponse) => {             
          return throwError(errorResponse); 
        })
      );
  }

  atualizar(dadosAtualizacao: Updatedto): Observable<any> {        
    return this.http.put<any>(`${this.apiURL}/update`, dadosAtualizacao)
      .pipe(
        catchError((errorResponse: HttpErrorResponse) => {          
          return throwError(errorResponse);
        })
      );
  }  

  login(usuario: any): Observable<any> {
    return this.http.post<any>(`${this.apiURL}/login`, usuario)
      .pipe(
        map(response => {
          const token = response.token;
          if (!token) {
            console.error('Token não encontrado na resposta da API.');
          } else {            
            localStorage.setItem('token', token);
          }
          return response;
        })
      );
  }  
}