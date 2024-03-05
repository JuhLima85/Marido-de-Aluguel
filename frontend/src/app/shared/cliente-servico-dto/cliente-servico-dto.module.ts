import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ClienteServicoDtoRoutingModule } from './cliente-servico-dto-routing.module';
import { ClienteServicoDtoListaComponent } from './cliente-servico-dto-lista/cliente-servico-dto-lista.component';

@NgModule({
  declarations: [
    ClienteServicoDtoListaComponent
  ],
  imports: [
    CommonModule,
    ClienteServicoDtoRoutingModule,
    FormsModule
  ], exports: [
    ClienteServicoDtoListaComponent
  ]
})
export class ClienteServicoDtoModule { } 
