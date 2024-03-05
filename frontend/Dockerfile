# Dockerfile para o Projeto Angular
FROM node:18 as builder

WORKDIR /app
COPY . .

# Instalação de dependências e construção do projeto Angular
RUN npm install --force
RUN npm run build

# Etapa final
FROM nginx:alpine
COPY --from=builder /app/dist/clientes-app /usr/share/nginx/html

# Configuração para ouvir na porta 4200
EXPOSE 4200

# Configuração para alterar o arquivo de configuração do Nginx para ouvir na porta 4200. Isso porque por padrão é a 8080, mas como estou usando a 8080 para api do back, 
# Precisei colocar a do angular para a 4200, então se quiser rodar direto na 8080 não precisa dessa linha, apenas alterar o expose para 80
RUN sed -i -e 's/80/4200/g' /etc/nginx/conf.d/default.conf