# Auth
## Serviço REST responsável por autenticar os usuários e validar os tokens de usuário


### Pré-requisitos
1. Instalar Java 17 ou superior (JDK), configurar no eclipse (Windows > Preferences > Java > Instaled JREs) e nas variáveis de ambiente. Link https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html
2. Baixar o Eclipse:https://www.eclipse.org/downloads/packages/release/2021-03/r/eclipse-ide-enterprise-java-and-web-developers
3. Baixar o jar do Lombok. Abrir o jar, buscar o diretório do eclipse e clicar em "Install/Update": https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.18

### Testando a aplicação pelo Postman:
1. Login (dados abaixo de exemplo, na sequência a explicação de como obter essas informações): 
	
	1. Método: Post
	2. URL: http://localhost:8081/login
	3. Body:
```
{
    "login":"duducordeiro",
    "password":"123456"
}
```
	4.Resultado: Body com a String para autenticação

1. Buscar todos os logins : 
	
	1. Método: Get
	2. URL: localhost:8081/api/usuario/listarTodos
	3. Header:
		3.1: Authorization
		3.2: [resultado/body  da chamada Login]
	4.Resultado/Body:
```
[
    {
        "id": 1,
        "login": "duducordeiro",
        "password": "$2a$10$7QhRBik/EzEktidoKyidH.QvFaUiVU22l6F2qCsiZFk0phSfMEI9e"
    }
]
```

