import { ClienteServicoDtoListaComponent } from './cliente-servico-dto-lista/cliente-servico-dto-lista.component'; 
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from 'src/app/layout/layout.component';

const routes: Routes = [
    { path: 'cliente-servico-dto', component: LayoutComponent, children:[
        {path: 'historico', component: ClienteServicoDtoListaComponent},
        { path: '', redirectTo : '/cliente-servico-dto/historico', pathMatch: 'full'}
        ]}      
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class ClienteServicoDtoRoutingModule { }