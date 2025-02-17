# MicroWorkPay - Sistema de Gestão de Pagamentos e Recursos Humanos

![Visão do Projeto](/img/img.png)

## Visão Geral

O **MicroWorkPay** é um sistema baseado em arquitetura de microsserviços, projetado para gerenciar trabalhadores, usuários, folhas de pagamento e controle de autenticação/segurança. Utiliza tecnologias modernas como **Spring Boot, Spring Cloud, OAuth2 e JWT**, proporcionando uma solução **simples, escalável e modular**.

---

## Arquitetura do Sistema

O sistema segue uma arquitetura de **microsserviços**, onde cada serviço é responsável por uma funcionalidade específica. A comunicação entre os microsserviços ocorre via **APIs REST**.

### Microsserviços:

- **hr-worker** - Gestão de trabalhadores
- **hr-payroll** - Cálculo de folha de pagamento
- **hr-user** - Gerenciamento de usuários
- **hr-oauth** - Autenticação e segurança (OAuth2 + JWT)
- **hr-api-gateway-zuul** - API Gateway para roteamento
- **hr-config-server** *(Opcional)* - Configuração centralizada

---

## Tecnologias Utilizadas

- **Java 11**
- **Spring Boot & Spring Cloud**
- **Spring Security** (OAuth2 + JWT)
- **Eureka (Service Discovery)**
- **Spring Cloud Zuul (API Gateway)**
- **PostgreSQL**
- **Docker** *(Opcional)*

---

## Importação no Postman

Para facilitar os testes, há arquivos disponíveis para importação no **Postman**:

📂 Arquivos disponíveis:
- `postman/MicroWorkPay.postman_collection.json` → Contém todos os endpoints da API.
- `postman/MicroWorkPay.postman_environment.json` → Contém variáveis para facilitar os testes.

### **Importação no Postman**
1. Abra o **Postman**.
2. Vá para **File → Import**.
3. Selecione os arquivos `.json` mencionados acima.
4. Configure o ambiente importado para os testes.

---

## **Documentação dos Endpoints**

### **Autenticação (`hr-oauth`)**
- **Buscar usuário por e-mail**
  ```http
  GET /api/users/search?email={email}
  ```
  - **Parâmetro:** `email` (String)
  - **Retorno:** Dados do usuário correspondente

---

### **Gerenciamento de Usuários (`hr-user`)**
- **Buscar usuário por ID**
  ```http
  GET /api/users/{id}
  ```
  - **Parâmetro:** `id` (UUID)
  - **Retorno:** Dados do usuário correspondente

- **Buscar usuário por e-mail**
  ```http
  GET /api/users/search?email={email}
  ```
  - **Parâmetro:** `email` (String)
  - **Retorno:** Dados do usuário correspondente

---

### **Gerenciamento de Pagamentos (`hr-payroll`)**
- **Gerar folha de pagamento**
  ```http
  GET /api/payments/{workerId}/days/{days}
  ```
  - **Parâmetros:**
    - `workerId` (UUID) → ID do trabalhador
    - `days` (Integer) → Número de dias trabalhados
  - **Retorno:** Informações do pagamento

- **Fallback de pagamento (em caso de erro no serviço principal)**
  ```http
  GET /api/payments/{workerId}/days/{days}
  ```
  - **Retorno alternativo:** Pagamento padrão com salário `0.0`

---

### **Gerenciamento de Trabalhadores (`hr-worker`)**
- **Buscar todos os trabalhadores**
  ```http
  GET /api/workers
  ```
  - **Retorno:** Lista de todos os trabalhadores

- **Buscar trabalhador por ID**
  ```http
  GET /api/workers/{id}
  ```
  - **Parâmetro:** `id` (UUID)
  - **Retorno:** Dados do trabalhador

- **Configuração do serviço (`Spring Cloud Config`)**
  ```http
  GET /api/workers/configs
  ```
  - **Retorno:** Sem conteúdo (`204`), mas exibe as configurações nos logs

---

## **Autenticação e Segurança**
- O sistema utiliza **OAuth2 + JWT** para autenticação.
- Após login, é necessário enviar o token JWT no cabeçalho:
  ```http
  Authorization: Bearer {jwt_token}
  ```

---

## **Execução dos Serviços**
### **Pré-requisitos**
- Java 11
- Maven
- PostgreSQL
- Docker *(Opcional para containers)*

### **Passos para configurar e rodar o projeto**
1. **Clone o repositório:**
   ```sh
   git clone https://github.com/seu-usuario/microworkpay.git
   cd microworkpay
   ```

2. **Configure o banco de dados:**
  - Crie os bancos de dados para os microsserviços (`hr-worker`, `hr-user`, etc.).
  - Edite `application.properties` para definir as credenciais do banco.

3. **Compile os serviços:**
   ```sh
   mvn clean install
   ```

4. **Inicie os serviços:**
   ```sh
   java -jar target/hr-worker.jar
   java -jar target/hr-user.jar
   java -jar target/hr-payroll.jar
   ```
