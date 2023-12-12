import { ServicoPrestadoService } from './services/servico-prestado.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS  } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { TemplateModule } from './template/template.module';
import { HomeComponent } from './home/home.component'
import { ClientesModule } from './clientes/clientes.module';
import {ClientesService} from './services/clientes.service';
import { ServicoPrestadoModule } from './servico-prestado/servico-prestado.module';
import { ClienteServicoDtoService } from './services/cliente-servico-dto.service';
import { ClienteServicoDtoModule } from './shared/cliente-servico-dto/cliente-servico-dto.module';
import { LoginComponent } from './login/login.component';
import { LayoutComponent } from './layout/layout.component';
import { AuthService } from './services/auth.service';
import { TokenInterceptor } from './token.interceptor';
import { CredenciaisRoutingModule } from './login/update-credenciais/credenciais-routing.module'
import { CredenciaisModule } from './login/update-credenciais/credenciais.module'

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    LayoutComponent, 
    
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    TemplateModule, 
    ClientesModule,
    FormsModule,
    ServicoPrestadoModule,
    ClienteServicoDtoModule,
    CredenciaisRoutingModule,
    CredenciaisModule,       
  ],
  providers: [    
    ClientesService,
    ServicoPrestadoService,
    ClienteServicoDtoService,
    AuthService,  
    {     
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    } 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
