import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CredenciaisRoutingModule } from './credenciais-routing.module';
import { CredenciaisComponent } from './credenciais.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [CredenciaisComponent],
  imports: [
    CommonModule,
    CredenciaisRoutingModule,
    FormsModule,
  ],
  exports: [CredenciaisRoutingModule]
})

export class CredenciaisModule { }
