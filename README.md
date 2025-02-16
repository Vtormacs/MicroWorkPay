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

- #### **hr-config-server**
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
    - **PostgreSQL** para armazenamento relacional.
- **Docker**: Para conteinerizar os serviços.

---

## **Fluxo de Comunicação**

1. Um cliente realiza uma requisição para o `hr-api-gateway-zuul`.
2. O API Gateway valida a autenticação e roteia a requisição para o microsserviço apropriado.
3. Cada microsserviço processa sua lógica de negócio e, quando necessário, comunica-se com outros microsserviços.
4. A resposta é retornada, passando pelo API Gateway até o cliente.

---

## **Configuração Local**

### **Pré-requisitos**
- Java 11
- Maven
- PostgreSQL
- Docker
- Git

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

---

# Criando e testando containers Docker
![Visão geral do containers docker](/img/docker.png)

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
docker run -p 8888:8888 --name hr-config-server --network hr-net -e GITHUB_USER=Vtormacs -e GITHUB_PASS= hr-config-server:v1
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
docker run -p 8761:8761 --name hr-eureka-server --network hr-net hr-eureka-server:v1
```

## hr-worker
```
FROM openjdk:11
VOLUME /tmp
ENTRYPOINT ["java","-jar","/hr-worker.jar"]
```
```
mvnw clean package -DskipTests
docker build -t hr-worker:v1 .
docker run -P --network hr-net hr-worker:v1
```

---

## **Configuração do Repositório de Configurações**

O projeto utiliza o repositório [MS-MicroWorkPay-Configs](https://github.com/Vtormacs/MS-MicroWorkPay-Configs) para armazenar as configurações dos microsserviços. O `hr-config-server` se conecta a esse repositório para carregar as configurações necessárias, como credenciais de banco de dados e outras propriedades.

Certifique-se de que o repositório de configurações esteja configurado corretamente e acessível para o `hr-config-server`.

---

## **Executando o Projeto**

1. Clone o repositório do projeto:
   ```
   git clone https://github.com/seu-usuario/MicroWorkPay.git
   ```

2. Navegue até o diretório do projeto:
   ```
   cd MicroWorkPay
   ```

3. Execute os comandos Docker para criar e iniciar os containers dos bancos de dados (PostgreSQL) e dos microsserviços.

4. Utilize o `mvnw clean package` para empacotar cada microsserviço e, em seguida, crie as imagens Docker com os comandos fornecidos acima.

5. Inicie os microsserviços na ordem correta:
    - `hr-config-server`
    - `hr-eureka-server`
    - `hr-worker`
    - `hr-user`
    - `hr-oauth`
    - `hr-api-gateway-zuul`
    - `hr-payroll`

6. Acesse os serviços através do API Gateway (`hr-api-gateway-zuul`) e utilize os endpoints conforme necessário.

---

## **Observações**

- O Flyway é utilizado para migrações de banco de dados, garantindo que as tabelas sejam criadas e populadas automaticamente ao iniciar os microsserviços.
- Certifique-se de que as portas necessárias estejam disponíveis e que os containers Docker estejam corretamente conectados à rede `hr-net`.