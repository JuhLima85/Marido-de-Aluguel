export interface ClienteServicoDto {
    clienteId: number;
    clienteNome: string;
    clienteCpf: string;
    clienteDataCadastro: string;
    clienteEndereco: string;
    clienteTelefone: string;
    servicoId: number;
    servicoDescricao: string;
    servicoValor: number;
    servicoTotal?: number; 
    servicoData: string;
}
