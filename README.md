# Marido de Aluguel

Este projeto é uma aplicação Spring boot e Angular dedicada ao controle e cadastro de clientes e serviços prestados.

URL da Aplicação: https://maridodealuguel.netlify.app/login

## Inicializar Aplicação
```bash
## Navegue até o diretório "docker"
cd docker

## Inicie os serviços definidos no arquivo docker-compose.yaml
docker-compose up
```

<br>

## Sobre a API 

A API foi desenvolvida utilizando o framework Spring Boot e segue o padrão MVC (Model-View-Controller). O objetivo é realizar o controle e cadastro de clientes e serviços prestados. Implementa um sistema de segurança que utiliza tokens JWT (JSON Web Token) para proteger os endpoints, garantindo autenticação e autorização. Além disso, possui testes de integração, garantindo a confiabilidade e o correto funcionamento das funcionalidades implementadas.

### Tecnologias Utilizadas
- Java 11
- Spring Boot
- Maven
- RestFUL API
- Token JWT (JSON Web Token)
- Banco de Dados MySQL
- Banco de Dados em Memória H2 (utilizado para testes)
- JUnit 5 e Mockito (para testes de integração)
- Docker

### Testes de Integração da API 
Os testes foram implementados utilizando o framework JUnit 5 para asserções e estruturação, e o Mockito para simulação de comportamentos e objetos.

![Testes](https://github.com/JuhLima85/Marido_de_Aluguel-ANGULAR/assets/89745459/5631514d-4e17-4eac-b3e2-84537fc03dfe)

<br>

## Sobre a Aplicação Angular

A aplicação integra recursos essenciais, como um  sistema de login para autenticação de usuários, proteção de rotas utilizando o AuthGuard, e um Interceptor HTTP para garantir a inclusão adequada do token de autenticação em todas as requisições.

## Tela Login
![Login](https://github.com/JuhLima85/Marido_de_Aluguel-ANGULAR/assets/89745459/3ca76698-563c-4ee6-a101-3928ef7d42e6)

## Tela Criar Usuário
![Criar Usuario](https://github.com/JuhLima85/Marido_de_Aluguel-ANGULAR/assets/89745459/b1d414d0-07e9-428f-8cd5-ab6bc789917b)

## Tela Configuração
![Configuracao](https://github.com/JuhLima85/Marido_de_Aluguel-ANGULAR/assets/89745459/1dc15035-1610-4b9d-be24-1e966b68ef28)

## Tela Home
![Home](https://github.com/JuhLima85/Marido_de_Aluguel-ANGULAR/assets/89745459/f0e3b05c-d10c-4a62-b371-880f693156ce)

## Tela Clientes
![Clientes](https://github.com/JuhLima85/Marido_de_Aluguel-ANGULAR/assets/89745459/85dc0e65-079c-4331-be12-ec013bad1545)

## Tela Edição de Cliente
![Edicao_Clientes](https://github.com/JuhLima85/Marido_de_Aluguel-ANGULAR/assets/89745459/497b736c-e3bb-4502-9234-87b2bee7890e)

## Tela Novo Cliente
![Novo_Cliente](https://github.com/JuhLima85/Marido_de_Aluguel-ANGULAR/assets/89745459/440ca1fb-c1f9-418f-b4df-5dd158b9805f)

## Tela Histórico de Cliente
![Historico](https://github.com/JuhLima85/Marido_de_Aluguel-ANGULAR/assets/89745459/a748ef19-ce77-4f5c-b7ab-dbe29348a458)

## Tela de Serviços
![Servicos](https://github.com/JuhLima85/Marido_de_Aluguel-ANGULAR/assets/89745459/86ce442b-3d19-4a33-a9d6-03c914b37d5e)

# Tela Novo Serviço
![Novo_Servicos](https://github.com/JuhLima85/Marido_de_Aluguel-ANGULAR/assets/89745459/d3e391c8-e412-4b56-94a4-787aa573a855)

# Autora
Juliana Lima

[![Linkedin](https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/feed/?trk=guest_homepage-basic_nav-header-signin)
[![WhatsApp](https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo=whatsapp&logoColor=white)](https://contate.me/Juliana-Lima)
[![Portfólio](https://img.shields.io/badge/Portf%C3%B3lio-%E2%9C%88%EF%B8%8F-lightgrey?style=for-the-badge)](https://codedeving.netlify.app/)


