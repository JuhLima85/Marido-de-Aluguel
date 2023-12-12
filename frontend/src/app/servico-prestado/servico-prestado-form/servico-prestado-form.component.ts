import { ClientesService } from '../../services/clientes.service';
import { Cliente } from './../../clientes/cliente';
import { Component, OnInit } from '@angular/core';
import { ServicoPrestado } from '../servicoPrestado';
import { ServicoPrestadoService } from '../../services/servico-prestado.service';

@Component({
  selector: 'app-servico-prestado-form',
  templateUrl: './servico-prestado-form.component.html',
  styleUrls: ['./servico-prestado-form.component.css']
})
export class ServicoPrestadoFormComponent implements OnInit {

  clientes: Cliente[] = []
  servico: ServicoPrestado
  sucesso: boolean = false
  erros: String[]

  constructor(
    private clienteService: ClientesService,
    private service: ServicoPrestadoService,    
  ) { 
    this.servico = new ServicoPrestado();
  }

  ngOnInit(): void {
    this.clienteService
    .buscarClientes()
    .subscribe( resposta => this.clientes = resposta);
  }

  onSubmit() {
    this.service
      .salvar(this.servico)
      .subscribe( response => {
        this.sucesso = true;
        this.erros = null;
        this.servico = new ServicoPrestado(); 
      }, errorResponse => {
         this.erros = errorResponse.error.errors; 
         this.sucesso = false; 
      })
  }

  applyDateMask(event: any): void {
    const input = event.target;
    const value = input.value.replace(/\D/g, '');

    if (value.length <= 8) {
      input.value = value
        .replace(/(\d{2})(\d)/, '$1/$2')
        .replace(/(\d{2})(\d)/, '$1/$2');
    } else {
      input.value = value.substring(0, 8)
        .replace(/(\d{2})(\d)/, '$1/$2')
        .replace(/(\d{2})(\d)/, '$1/$2');
    }
  }
}
