import { Injectable } from '@angular/core';
import { ClienteServicoDto } from '../shared/cliente-servico-dto/cliente-servico-dto'; 

@Injectable({
  providedIn: 'root'
})

export class ClienteServicoDtoService {
  
  private localStorageKey = 'historicoDeServicos';

  setHistorico(historicoDeServicos: ClienteServicoDto[]): void {
    localStorage.setItem(this.localStorageKey, JSON.stringify(historicoDeServicos));
  }

  getHistorico(): ClienteServicoDto[] | null {
    const historicoDeServicosJson = localStorage.getItem(this.localStorageKey);      
    if (historicoDeServicosJson) {
           return JSON.parse(historicoDeServicosJson);
    }
    return null;
  }  
}
