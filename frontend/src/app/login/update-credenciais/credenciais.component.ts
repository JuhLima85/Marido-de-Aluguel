import { Component, OnInit } from '@angular/core';
import { Usuario } from '../usuario';
import { Updatedto } from './update-dto';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-credenciais',
  templateUrl: './credenciais.component.html',
  styleUrls: ['./credenciais.component.css']
})
export class CredenciaisComponent {

  usuarioParaAtualizar: Usuario;
  dadosAtualizacao: Updatedto;
  role: string;   
  mensagemSucesso: string;
  errors: string[];
  usuarioLogado: any; 

  constructor(   
    private authService: AuthService,
    private router : Router
  ) { 
    this.usuarioParaAtualizar = new Usuario();
    this.dadosAtualizacao = new Updatedto();
  }

  onSubmit(){   
    this.atualizar();    
  }
  
  cancelarCadastro(event){
    event.preventDefault();       
    this.router.navigate(['/home']); 
  }  

  atualizar(){   
  this.dadosAtualizacao.userExistenteLogin = this.authService.getUsuarioAutenticado().nomeUsuario;   
   this.dadosAtualizacao.userAtualRole = this.authService.getUsuarioAutenticado().perfil;      

    this.authService.atualizar(this.dadosAtualizacao).subscribe(      
      (response) => {               
          this.authService.encerrarSessao();
          this.router.navigate(['/login']);         
      },
      (errorResponse) => {          
        this.mensagemSucesso = null;   
        this.errors = errorResponse.error.errors;  
      }
    );
  }  

  ngOnInit(): void {
    this.usuarioLogado = this.authService.getUsuarioAutenticado().nomeUsuario;
    this.role = this.authService.getUsuarioAutenticado().perfil;
    this.dadosAtualizacao.userId = this.authService.getUsuarioAutenticado().idUsuario;
  }
}
