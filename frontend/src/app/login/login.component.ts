import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Usuario } from './usuario';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
 
  usuario: Usuario; 
  perfis: string[]; 
  cadastrando: boolean = false;  
  mensagemSucesso: string;
  errors: string[];
  
  constructor(
    private router : Router,
    private authService:  AuthService
  ) {
    this.usuario = new Usuario();
    this.perfis = ["USER", "ADMIN"]
   }   

  onSubmit(){   
    this.login(); 
  }

  retornarAoPortofolio() {    
    const url = 'https://codedeving.netlify.app/';
    window.open(url);
  }

  exibirformCadastro(event){    
    this.mensagemSucesso = '';
    this.errors = null;
    event.preventDefault();
    const link = event.target;  
    this.cadastrando = true;          
  }

  cancelarCadastro(event){
    event.preventDefault();
    this.cadastrando = false;    
  }

  registrar() {      
  this.authService.registrar(this.usuario)    
      .subscribe(
        response => {         
          this.mensagemSucesso = "Usuário registrado com sucesso. Efetue o login.";      
          this.cadastrando = false; 
          this.usuario.login = '';
          this.usuario.password = '';
          this.usuario.role = '';         
          this.errors = null;
        },
        errorResponse => {                   
          this.mensagemSucesso = null;           
          this.errors = errorResponse.error.errors;               
        }
      );
  }
  
  login() {  
    this.authService.login(this.usuario).subscribe(
      response => {          
        if (localStorage.getItem('token')) {               
          this.router.navigate(['/home']);
        }
      },
      errorResponse => {        
        this.mensagemSucesso = null; 
        this.errors = ['Usuário e/ou senha incorreto(s).'];
      }
    );
  }  
}
