import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { ServicoPrestado } from '../servico-prestado/servicoPrestado';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ServicoPrestadoBusca } from '../servico-prestado/servico-prestado-lista/servicoPrestadoBusca';

@Injectable({
  providedIn: 'root'
})
export class ServicoPrestadoService {

  apiUrl: string = environment.apiUrlBase + "/api/servicos-prestados"

  constructor(private http: HttpClient) { }

  salvar(servicoPrestado: ServicoPrestado) : Observable<ServicoPrestado>{   
    return this.http.post<ServicoPrestado>(this.apiUrl, servicoPrestado)
  }

  buscar(nome?: string, cpf?: string, mes?: number): Observable<ServicoPrestadoBusca[]> {
    let httpParams = new HttpParams();
    if (nome) {
      httpParams = httpParams.set("nome", nome);
    }
    if (cpf) {
      httpParams = httpParams.set("cpf", cpf);
    }
    if (mes !== undefined && mes !== null) {
      httpParams = httpParams.set("mes", mes.toString());
    }
    const url = this.apiUrl + "?" + httpParams.toString();
    return this.http.get<any>(url);
}
}
