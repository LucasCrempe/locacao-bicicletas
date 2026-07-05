# Sistema de Locação de Bicicletas (API e MVC)

Projeto referente às Atividades T6 e T7. Funciona como o servidor principal da aplicação, fornecendo tanto as telas para o usuário final quanto a API REST para consumo externo.

## Funcionalidades e Requisitos
- **MVC:** Telas renderizadas via Thymeleaf, CRUD completo de Clientes e Locadoras com controle de acesso (Spring Security) e validações robustas (Bean Validation). Tratamento de exceções para integridade relacional. Internacionalização (PT/EN).
- **REST API:** Endpoints públicos (`/clientes`, `/locadoras`, `/locacoes`) que retornam dados em JSON. Configuração via Jackson para não expor as senhas dos usuários nas requisições GET.

## Tecnologias
- Java 17, Spring Boot 3.1.5 (Web, Data JPA, Security)
- H2 Database (Memória)
- Thymeleaf + Bootstrap

## Execução e Banco de Dados

**Para rodar a aplicação:**
mvn clean spring-boot:run
O sistema rodará na porta 8080.

**Acesso ao Banco H2:**
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:locacaodb
- Usuário: sa
- Senha: (deixar em branco)

**Admin:**
- Usuário Administrador (Gerado na inicialização):
- Login: admin@admin.com
- Senha: admin
