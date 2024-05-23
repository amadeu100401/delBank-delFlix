# DelFlix API - Desafio DelBank

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
- JDK 21 [SITE Oracle](https://www.oracle.com/br/java/technologies/downloads/)
- Maven 3.8.1 ou superior [Site MAVEN](https://maven.apache.org/download.cgi)
- MySQL 8.0 ou superior [Site do MySQL](https://www.mysql.com/downloads/)

## Configuração do Ambiente

### 1. Clonar o Repositório
```sh
git clone https://github.com/amadeu100401/delBank-delFlix.git
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
- Shared -> Essa camada contém classes que são compartilhadas por todas as demais camadas. Ela armazena classes de requests e response para a api. Além disso, é nessa camada que foi feita a configuração da exceção personalizada.

### Explicando bibliotecas utilizadas
#### Para a validação dos objetos de requests nos métodos POST e PUT foi utlilizado a biblioteca Fluent Validation [Acesse a documentação](https://mvallim.github.io/java-fluent-validator/))
#### Para a mapping entre entidades, foi utilizado a biblioteca modelMapper [Acesse a documentação](https://modelmapper.org/)
#### Para a geração de dados para a simulação nos testes foi utilizado a biblioteca Faker [Acesse a documentação](https://github.com/DiUS/java-faker)

## Sobre os endpoints 
![image](https://github.com/amadeu100401/delBank-delFlix/assets/54649985/33087ba7-96b1-4da7-abc9-78d3c8eaada8)

1. No controller de Dvds temos:
    - PUT {/update/{identifier}} -> Endpoint para alteração de dados dos dvs já cadastrados na base
    - PUT {/activate-dvd/{identifier}} -> Endpoint para ativar um dvd
    - POST {/register} -> Endpoint para cadastro de novos dvds
    - GET {/dvd} -> Endpoint para obter a lista de todos os dvds cadastrados
    - GET {/dvd/{identifier}} -> Endpoint para recuperar as infomrações de um dvd específico
    - DELETE {/disable-dvd} -> Endpoint para poder excluir logicamente o dvd
    - DELETE {/delete{identifier}} -> Endpoint para pode excluir o dvd da base de dados
2. No controller de Rent temos:
    - POST {/rent-dvd} -> Endpoint para poder alugar um dvd da base
    - PUT {/rent-dvd} -> Endpoint para pode devolver o dvd alugado

PS: O identifier de cada dvd é um GUID gerado randomicamente no momento da criação do objeto. Foi optado esse caminho a expor o id do dado no banco.
      
## Postman
Foi disponibilizado junto ao repositório uma collection no postman para testes, caso prefira.
### Como utilizar
### Como Importar uma Collection no Postman
## Passo 1: Abrir o Postman
Certifique-se de que o Postman está instalado e aberto em seu computador. Se você ainda não tem o Postman, você pode baixá-lo [aqui](https://www.postman.com/downloads/).

## Passo 2: Acessar a Opção de Importação

1. **Clique em "Importar":**
   - No canto superior esquerdo do Postman, clique no botão "Import".

## Passo 3: Selecionar o Arquivo ou Link

2. **Escolha a Origem:**
   - Você pode importar uma collection a partir de um arquivo no seu computador ou através de um link. 
   - Para importar a partir de um arquivo, clique na aba "File" e depois em "Choose Files". Navegue até o local onde o arquivo da collection (.json) está salvo e selecione-o.
   - Para importar a partir de um link, clique na aba "Link" e insira o URL da collection. 

## Passo 4: Importar a Collection

3. **Importar:**
   - Depois de selecionar o arquivo ou inserir o link, clique no botão "Import". O Postman irá processar o arquivo ou link e importar a collection para o seu ambiente.

Para utilizar o serviço pelo postman basta subir a aplicação, colocar a url base na variável de ambiente da collection.
É possível rodar a collection como um todo pois os requests possuem testes de validação
