# DelFlix API 

## Descrição
DelFlix é um projeto de demonstração desenvolvido usando Spring Boot. Esta aplicação gerencia um catálogo de DVDs e utiliza MySQL como banco de dados.

## Tecnologias Utilizadas
- Java 21
- Spring Boot 3.2.5
- Maven
- MySQL
- ModelMapper
- Mockito
- SpringDoc OpenAPI

## Pré-requisitos
- JDK 21
- Maven 3.8.1 ou superior
- MySQL 8.0 ou superior

## Configuração do Ambiente

### 1. Clonar o Repositório
```sh
git clone https://github.com/seu-usuario/delflix.git
```

### 2. Configurar o Banco de Dados
Crie um banco de dados MySQL e configure as credenciais no arquivo application.yml.
No atual arquivo de configuração existe as configurações padrão do MySQL

```yaml

spring:
    datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:portaBanco/nomeDataBase?useTimezone=true@serverTimeZone=UTC
        username: seuUsuario
        password: senha
    jpa: 
        hibernate:
            ddl-auto: update
        properties:
            hibernate:
                dialect: org.hibernate.dialect.MySQL8Dialect
        show-sql: false
```
### 3. Build e Execução da Aplicação
Para construir e executar a aplicação, utilize os seguintes comandos:
```sh
mvn clean install
mvn spring-boot:run
```
### Para parar a aplicação use o comando
```sh
ctrl + C
```
### Para executar os testes unitários use o comando
```sh
mvn test
```
## Documentação da API
A documentação da API está disponível através do SpringDoc OpenAPI. Após iniciar a aplicação, acesse:
```sh
http://localhost:8080/swagger-ui.html
```
## Estrutura do Projeto
A estrutura do projeto é organizada da seguinte forma:
``` css
src/
├── main/
│   ├── java/
│   │   └── br/
│   │       └── com/
│   │           └── delflix/
|   |               ├── api/
|   |                    └── controller/
│   │               ├── application/
|   |                     └── useCase/
|   |                     └── utils/
|   |                     └── validator/
|   |               ├── delflix/
│   │               ├── domain/
|   |                   └── entity
|   |                   └── repository
|   |                   └── service
│   │               ├── infrastructure/
|   |                   └── configuration
|   |                   └── repository
│   │               └── shared/
│   └── resources/
│       ├── application.yml
│       └── static/
```
### Explicando a arquitetura
O sistema foi desenvolvido utilizando a arquitetura em camadas, separando em 6 camadas principais, sendo elas:
- API -> Camada mais externa da aplicação, onde os contém os endpoints
- Application -> Camada intermediária, que contém os useCase, fazendo a ligação da camada API com a DOMAIN. Nela estão algumas validações e a chamada dos services
- Domain -> Camada principal do sistema: ela contém a lógica de negócio (services - seguindo o conceito de DDD), bem como contém as interfaces de acesso ao banco de dados. Essa camada também contém as entidades que refletem o banco de dados.
- Infra -> Camada para utilização de serviços externos a aplicação. Aqui está toda lógica para conexão com banco de dados e configuração de bibliotecas como o modelMapper e o Swagger
- Shared -> Essa camada contém classes que são compartilhadas por todas as demais camdas. Ela armazena classes de requests e response para a aapi. Além disso, é nessa camada que foi feita a configuração da exceção personalizada.

#### Para a validação dos objetos de requests nos métodos POST e PUT foi utlilizado a biblioteca Fluent Validation ([Acesse a documentação](https://mvallim.github.io/java-fluent-validator/))
#### Para a mapping entre entidades, foi utilizado a biblioteca modelMapper [Acesse a documentação](https://modelmapper.org/)
### Para a geração de dados para a simulação nos testes foi utilizado a biblioteca Faker [Acesse a documentação](https://github.com/DiUS/java-faker)

## Sobre os endpoints 
![image](https://github.com/amadeu100401/delBank-delFlix/assets/54649985/475070c5-f885-4e59-80c0-1dfe97f428e5)

1. No controller de Dvds temos:
    - PUT {/update/{identifier}} -> Endpoint para alteração de dados dos dvs já cadastrados na base
    - POST {/register} -> Endpoint para cadastro de novos dvds
    - GET {/dvd} -> Endpoint para obter a lista de todos os dvds cadastrados
    - GET {/dvd/{identifier}} -> Endpoint para recuperar as infomrações de um dvd específico
    - DELETE {/disable-dvd} -> Endpoint para poder excluir logicamente o dvd
    - DELETE {/delete{identifier}} -> Endpoint para pode excluir o dvd da base de dados
2. No controller de Rent temos:
    - POST {/rent-dvd} -> Endpoint para poder alugar um dvd da base
    - PUT {/rent-dvd} -> Endpoint para pode devolver o dvd alugado
