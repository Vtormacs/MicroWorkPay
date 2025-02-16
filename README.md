## Visão Geral do Projeto

![Visão do Projeto](/img/img.png)


# MicroWorkPay - Sistema de Gestão de Pagamentos e Recursos Humanos

**MicroWorkPay** é um sistema baseado em arquitetura de microsserviços, projetado para gerenciar trabalhadores, usuários, folhas de pagamento e controle de autenticação/segurança. Ele utiliza tecnologias modernas como Spring Boot, Spring Cloud, OAuth2 e JWT, proporcionando uma solução simples, escalável e modular.

---

## **Arquitetura do Sistema**

O sistema utiliza uma arquitetura de **microsserviços**, onde cada serviço é responsável por uma funcionalidade específica. A comunicação entre os microsserviços ocorre via APIs REST ou mecanismos assíncronos (mensageria).

### **Microsserviços**:

- #### **hr-worker**
    - Responsável pela gestão dos funcionários.
    - Funcionalidades:
        - Cadastro e consulta de informações de trabalhadores.
        - Exposição de dados relacionados aos funcionários para outros serviços (ex.: cálculo de pagamento).

- #### **hr-payroll**
    - Responsável pelo gerenciamento da folha de pagamento.
    - Funcionalidades:
        - Realiza o cálculo de folhas de pagamento com base nos dados de `hr-worker`.
        - Integração futura com serviços financeiros para pagamentos.

- #### **hr-user**
    - Gerencia os dados de usuários do sistema.
    - Funcionalidades:
        - Cadastro de usuários e credenciais.
        - Integração com o serviço `hr-oauth` para autenticação segura.

- #### **hr-oauth**
    - Gerencia a segurança e autenticação do sistema.
    - Funcionalidades:
        - Implementa **OAuth2** e **JWT** para autenticar usuários e proteger os serviços REST.
        - Geração e validação de tokens de acesso.

- #### **hr-api-gateway-zuul**
    - Atua como **API Gateway**, responsável por roteamento de requisições dos clientes para os serviços apropriados.
    - Funcionalidades:
        - Validação inicial de segurança (autenticação e tokens).
        - Exposição única dos serviços para o cliente.

- #### **hr-config-server** *(Opcional, se implementado)*
    - Centraliza as configurações de todos os microsserviços.
    - Use o **Spring Cloud Config** para alterar configurações dos serviços sem necessidade de reiniciá-los.

---

## **Principais Tecnologias Utilizadas**

- **Java 11**: Linguagem principal do sistema.
- **Spring Boot & Spring Cloud**: Base principal para a implementação dos microsserviços.
- **Spring Security**: Controle de autenticação e autorização (OAuth2 + JWT).
- **Eureka (Spring Cloud Netflix)**: Registro e descoberta de serviços (Service Discovery).
- **Spring Cloud Zuul**: Roteamento e API Gateway.
- **Lombok**: Simplifica código, reduzindo boilerplate.
- **Banco de Dados**:
    - **MySQL/PostgreSQL** para armazenamento relacional.
    - **Redis** (opcional): Cache distribuído.
- **Docker** *(opcional)*: Para conteinerizar os serviços.

---

## **Fluxo de Comunicação**

1. Um cliente realiza uma requisição para o `hr-api-gateway-zuul`.
2. O API Gateway valida a autenticação e roteia a requisição para o microsserviço apropriado.
3. Cada microsserviço processa sua lógica de negócio e, quando necessário, comunica-se com outros microsserviços.
4. A resposta é retornada, passando pelo API Gateway até o cliente.

---

## **Configuração Local**

### **Pré-requisitos**
- Java 11+
- Maven
- MySQL ou PostgreSQL
- Docker *(opcional para containers)*
- Git

### **Passos para configurar o projeto:**

1. **Clone o repositório principal:**
```shell script
git clone https://github.com/seu-usuario/microworkpay.git
   cd microworkpay
```

2. **Configure o banco de dados:**
    - Crie os bancos de dados necessários para os microsserviços (`hr-worker`, `hr-user`, etc.).
    - Edite o arquivo de configuração para cada microsserviço, ajustando o acesso ao banco:
        - `application.properties` ou `application.yml`.
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hr-worker
       spring.datasource.username=usuario
       spring.datasource.password=senha
```

3. **Configuração de Segurança (JWT):**
    - Atualize o segredo utilizado para assinar os tokens, adicionado no `hr-oauth` e `hr-api-gateway-zuul`:
```properties
jwt.secret=SUA_SECRET_KEY
```

4. **Compile os serviços:**
   Execute o comando Maven em cada serviço para gerar os artefatos:
```shell script
mvn clean install
```

5. **Inicie os serviços:**
    - Certifique-se de que o **hr-config-server** está rodando, depois inicie os outros serviços em sua ordem.
    - Cada serviço pode ser iniciado executando:
```shell script
java -jar target/nome-do-servico.jar
```

---

## **Exemplo de Endpoints**

### **Endpoints Públicos**

- **Autenticação (hr-oauth):**
```
POST /oauth/token
  Body: { "username": "admin", "password": "admin" }
```

### **Endpoints Protegidos**

- **Recursos de Trabalhadores (hr-worker):**
```
GET /workers/{id}
```
- **Recursos de Pagamento (hr-payroll):**
```
POST /payment/{workerId}
```

> A autenticação (JWT) é necessária para acessar esses endpoints. Adicione o Header na requisição:
> ```
> Authorization: Bearer {jwt_token}
> ```


# Criando e testando containers Docker
## Comandos Docker
#### Criar uma rede Docker
```
docker network create <nome-da-rede>
```
#### Baixar imagem do Dockerhub
```
docker pull <nome-da-imagem:tag>
```
#### Ver imagens
```
docker images
```
#### Rodar um container de uma imagem
```
docker run -p <porta-externa>:<porta-interna> --name <nome-do-container> --network <nome-da-rede> <nome-da-imagem:tag> 
```
#### Listar containers
```
docker ps
docker ps -a
```
#### Acompanhar logs do container em execução
```
docker logs -f <container-id>
```
## Criar rede docker para sistema hr
```
docker network create hr-net
```
## Postgresql
```
docker pull postgres:16-alpine

docker run -d \
  --name hr-worker-pg16 \
  --network hr-net \
  -e POSTGRES_PASSWORD=root \
  -e POSTGRES_DB=hr-worker \
  -p 5432:5432 \
  postgres:16-alpine

docker run -d \
  --name hr-user-pg16 \
  --network hr-net \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=root \
  -e POSTGRES_DB=hr-user \
  -p 5433:5432 \
  postgres:16-alpine

```
## hr-config-server
```
FROM openjdk:11
VOLUME /tmp
EXPOSE 8888
ADD ./target/hr-config-server-0.0.1-SNAPSHOT.jar hr-config-server.jar
ENTRYPOINT ["java","-jar","/hr-config-server.jar"]
``` 
```
mvnw clean package
docker build -t hr-config-server:v1 .
docker run hr-config-server:v1 -p 8888:8888 --name hr-config-server --network hr-net -e GITHUB_USER=Vtormacs -e GITHUB_PASS=
```
## hr-eureka-server
```
FROM openjdk:11
VOLUME /tmp
EXPOSE 8761
ADD ./target/hr-eureka-server-0.0.1-SNAPSHOT.jar hr-eureka-server.jar
ENTRYPOINT ["java","-jar","/hr-eureka-server.jar"]
``` 
```
mvnw clean package
docker build -t hr-eureka-server:v1 .
docker run hr-eureka-server:v1 -p 8761:8761 --name hr-eureka-server --network hr-net
```
## hr-worker
```
FROM openjdk:11
VOLUME /tmp
ADD ./target/hr-worker-0.0.1-SNAPSHOT.jar hr-worker.jar
ENTRYPOINT ["java","-jar","/hr-worker.jar"]
``` 
```
mvnw clean package -DskipTests
docker build -t hr-worker:v1 .
docker run hr-worker:v1 -P --network hr-net
```
