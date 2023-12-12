import { Component, OnInit } from '@angular/core';
import { ClienteServicoDtoService } from '../../../services/cliente-servico-dto.service';
import { ClienteServicoDto } from '../cliente-servico-dto'
import { Router } from '@angular/router';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
pdfMake.vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-cliente-servico-dto-lista',
  templateUrl: './cliente-servico-dto-lista.component.html',
  styleUrls: ['./cliente-servico-dto-lista.component.css']
})
export class ClienteServicoDtoListaComponent implements OnInit {

  historicoDeServicos: ClienteServicoDto[] = [];
  message: string;

  constructor(
    private clienteServicoDtoService: ClienteServicoDtoService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.historicoDeServicos = this.clienteServicoDtoService.getHistorico();
  
    const servicosVazios = this.historicoDeServicos.every(servico => {
      return (
        !servico.servicoId &&
        !servico.servicoDescricao &&
        !servico.servicoValor &&
        !servico.servicoData
      );
    });
  
    if (servicosVazios) {
      this.message = "Nenhum serviço registrado.";      
    } else {
    const total = this.historicoDeServicos.map(servico => servico.servicoValor).reduce((a, b) => a + b, 0);

    this.historicoDeServicos[this.historicoDeServicos.length - 1].servicoTotal = total;    
    }
  }  

  gerarPDF() {
    const servicosRealizados = this.historicoDeServicos.map(servico => [
      servico.servicoDescricao,
      servico.servicoValor.toFixed(2),
      servico.servicoData
    ]);

    const docDefinition = {
      content: [
        { text: 'Histórico do Cliente', style: 'header', alignment: 'center' },

        { text: 'Informações Cadastrais:', style: 'subheader' },

        { text: 'Nome do Cliente: ' + this.historicoDeServicos[0]?.clienteNome },
        { text: 'CPF do Cliente: ' + this.historicoDeServicos[0]?.clienteCpf },
        { text: 'Endereço do Cliente: ' + this.historicoDeServicos[0]?.clienteEndereco },
        { text: 'Telefone do Cliente: ' + this.historicoDeServicos[0]?.clienteTelefone },
        { text: 'Data de Cadastro do Cliente: ' + this.historicoDeServicos[0]?.clienteDataCadastro },
        { text: '\n\n' },
        { text: 'Serviços Realizados:', style: 'subheader' },
        {
          table: {
            headerRows: 1,
            widths: ['*', 'auto', 'auto'],
            body: [
              ['Descrição', 'Valor', 'Data',],
              ...servicosRealizados,                  
              [
                'Total',
                this.historicoDeServicos[this.historicoDeServicos.length - 1].servicoTotal.toFixed(2),
                this.historicoDeServicos[this.historicoDeServicos.length - 1].servicoData
              ]
            ]
          }
        }
      ],
      styles: {
        header: {
          fontSize: 18,
          bold: true,
          margin: [0, 0, 0, 10]
        },
        subheader: {
          fontSize: 14,
          bold: true,
          margin: [0, 10, 0, 5]
        }
      }
    };  
    pdfMake.createPdf(docDefinition).open();
  }

  voltarParaListagem() {
    this.router.navigate(['/clientes/lista'])
  }
}
