# MicroWorkPay - Sistema de Gestão de Pagamentos e Recursos Humanos

![Visão do Projeto](/img/img.png)

## Visão Geral

O **MicroWorkPay** é um sistema baseado em arquitetura de microsserviços, projetado para gerenciar trabalhadores, usuários, folhas de pagamento e controle de autenticação/segurança. Utiliza tecnologias modernas como **Spring Boot, Spring Cloud, OAuth2 e JWT**, proporcionando uma solução **simples, escalável e modular**.

---

## Arquitetura do Sistema

O sistema segue uma arquitetura de **microsserviços**, onde cada serviço é responsável por uma funcionalidade específica. A comunicação entre os microsserviços ocorre via **APIs REST** ou mecanismos assíncronos (**mensageria**).

### Microsserviços:

- **hr-worker**  
  📌 Responsável pela gestão dos funcionários.  
  **Funcionalidades:**
  - Cadastro e consulta de informações de trabalhadores.
  - Exposição de dados para outros serviços (ex.: cálculo de pagamento).

- **hr-payroll**  
  📌 Gerenciamento da folha de pagamento.  
  **Funcionalidades:**
  - Cálculo da folha de pagamento com base nos dados de `hr-worker`.
  - Integração futura com serviços financeiros para pagamentos.

- **hr-user**  
  📌 Gerenciamento de usuários do sistema.  
  **Funcionalidades:**
  - Cadastro de usuários e credenciais.
  - Integração com `hr-oauth` para autenticação segura.

- **hr-oauth**  
  📌 Gerenciamento da autenticação e segurança.  
  **Funcionalidades:**
  - Implementa **OAuth2** e **JWT** para autenticação e proteção de APIs.
  - Geração e validação de tokens de acesso.

- **hr-api-gateway-zuul**  
  📌 API Gateway, responsável pelo roteamento de requisições.  
  **Funcionalidades:**
  - Validação inicial de autenticação e tokens.
  - Exposição única dos serviços para o cliente.

- **hr-config-server** *(Opcional)*  
  📌 Centraliza as configurações dos microsserviços.  
  **Funcionalidades:**
  - Gerencia configurações usando **Spring Cloud Config**.
  - Permite mudanças sem necessidade de reiniciar os serviços.

---

## Tecnologias Utilizadas

- **Java 11** - Linguagem principal do sistema.
- **Spring Boot & Spring Cloud** - Base para os microsserviços.
- **Spring Security** - Controle de autenticação e autorização (**OAuth2 + JWT**).
- **Eureka (Spring Cloud Netflix)** - Registro e descoberta de serviços (**Service Discovery**).
- **Spring Cloud Zuul** - Roteamento e API Gateway.
- **Lombok** - Redução de código boilerplate.
- **Banco de Dados:**
  - **PostgreSQL** - Armazenamento relacional.
- **Docker** *(Opcional)* - Para conteinerizar os serviços.

---

## Fluxo de Comunicação

1. O cliente faz uma requisição para o `hr-api-gateway-zuul`.
2. O API Gateway valida a autenticação e roteia para o microsserviço apropriado.
3. Cada microsserviço processa sua lógica e comunica-se com outros serviços, se necessário.
4. A resposta é retornada para o cliente via API Gateway.

---

## Configuração Local

### **Pré-requisitos**
- Java 11+
- Maven
- PostgreSQL
- Docker *(opcional para containers)*
- Git

### **Passos para configurar o projeto:**

1. **Clone o repositório principal:**
   ```sh
   git clone https://github.com/seu-usuario/microworkpay.git
   cd microworkpay
   ```

2. **Configure o banco de dados:**
  - Crie os bancos de dados para os microsserviços (`hr-worker`, `hr-user`, etc.).
  - Edite os arquivos de configuração (`application.properties` ou `application.yml`) e ajuste as credenciais:
    ```properties
    spring.datasource.url= url-do-banco
    spring.datasource.username= usuario-do-banco
    spring.datasource.password= senha-do-banco
    ```

3. **Compile os serviços:**  
   Execute o comando Maven em cada microsserviço:
   ```sh
   mvn clean install
   ```

4. **Inicie os serviços:**
  - Certifique-se de iniciar primeiro o **hr-config-server**, depois os outros serviços.
  - Para executar um serviço:
    ```sh
    java -jar target/nome-do-servico.jar
    ```

---

## Endpoints Disponíveis

### **Endpoints Públicos**

- **Autenticação (`hr-oauth`)**:
  ```http
  POST /oauth/token
  Body: { "username": "admin", "password": "admin" }
  ```

### **Endpoints Protegidos**

- **Consultar trabalhadores (`hr-worker`)**:
  ```http
  GET /workers/{id}
  ```

- **Gerar folha de pagamento (`hr-payroll`)**:
  ```http
  POST /payment/{workerId}
  ```

> **⚠️ Atenção:** Para acessar endpoints protegidos, inclua o token JWT no cabeçalho da requisição:
> ```http
> Authorization: Bearer {jwt_token}
> ```
