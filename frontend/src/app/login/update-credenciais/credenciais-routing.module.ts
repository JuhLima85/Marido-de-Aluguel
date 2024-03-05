import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from '../../layout/layout.component';
import { AuthGuard } from '../../auth.guard'
import { CredenciaisComponent } from './credenciais.component';

const routes: Routes = [
  {
    path: 'credenciais',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'atualizar', component: CredenciaisComponent },
      { path: '', redirectTo: '/credenciais/atualizar', pathMatch: 'full' }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CredenciaisRoutingModule { }
