import { Component, OnInit } from '@angular/core';

import { Cliente } from '../cliente'
import { ClientesService} from '../../services/clientes.service'
import { Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-clientes-form',
  templateUrl: './clientes-form.component.html',
  styleUrls: ['./clientes-form.component.css']
})
export class ClientesFormComponent implements OnInit {
  
  cliente: Cliente;
  sucesso: boolean = false;
  erros: String[];
  id: number;  
  
  constructor( 
    private service : ClientesService,
    private router : Router,
    private activatedRoute : ActivatedRoute) { 
    this.cliente = new Cliente();
  }

  ngOnInit(): void {
    let params = this.activatedRoute.params  
    params.subscribe( urlParams => {
      this.id = urlParams['id'];
      if(this.id){
        this.service
      .buscarClientePorId(this.id)
      .subscribe(
        response => this.cliente = response,
        errorResponse => this.cliente = new Cliente()
        )
      }      
      })        
  }

  voltarParaListagem(){
    this.router.navigate(['/clientes/lista'])
  }
  
    onSubmit(){         
      if(this.id){
        this.service
          .atualizar(this.cliente)
          .subscribe(response => {
            this.sucesso = true;
            this.erros = null;
          }, erroResponse => {          
            this.erros = ['Erro ao atualizar o cliente.']})
  
      }else{
        this.service
        .salvar(this.cliente)
        .subscribe( response => {
          this.sucesso = true;
          this.erros = null;
          this.cliente = response;
        }, errorResponse => {                           
          this.erros = errorResponse.error.errors;  
           this.sucesso = false; 
        })
      }    
      }

      formatarCPF() {
        if (this.cliente.cpf) {           
            let cpfSemMascara = this.cliente.cpf.replace(/\D/g, '');           
            this.cliente.cpf = cpfSemMascara;    
           
            if (cpfSemMascara.length > 3 && cpfSemMascara.length <= 6) {
                this.cliente.cpf = `${cpfSemMascara.substring(0, 3)}.${cpfSemMascara.substring(3)}`;
            } else if (cpfSemMascara.length > 6 && cpfSemMascara.length <= 9) {
                this.cliente.cpf = `${cpfSemMascara.substring(0, 3)}.${cpfSemMascara.substring(3, 6)}.${cpfSemMascara.substring(6)}`;
            } else if (cpfSemMascara.length > 9) {
                this.cliente.cpf = `${cpfSemMascara.substring(0, 3)}.${cpfSemMascara.substring(3, 6)}.${cpfSemMascara.substring(6, 9)}-${cpfSemMascara.substring(9)}`;
            }
        }
    }    

    formatarTelefone() {
        if (this.cliente.telefone) {            
            let telefoneSemMascara = this.cliente.telefone.replace(/\D/g, '');    
           
            if (telefoneSemMascara.length <= 2) {
                this.cliente.telefone = telefoneSemMascara;
            } else if (telefoneSemMascara.length <= 6) {
                this.cliente.telefone = `(${telefoneSemMascara.substring(0, 2)}) ${telefoneSemMascara.substring(2)}`;
            } else {
                this.cliente.telefone = `(${telefoneSemMascara.substring(0, 2)}) ${telefoneSemMascara.substring(2, 6)}-${telefoneSemMascara.substring(6)}`;
            }
        }
    }        
}
