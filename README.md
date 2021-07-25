# Desenvolvimento de testes unitários para validar uma API REST de gerenciamento de estoques de Games

## Digital Innovation One: Bootcamp GFT START #2 Java

Projeto desenvolvido com instruções de [Rodrigo Peleias](https://github.com/rpeleias) na trilha de estudo do Bootcamp GFT START #2 Java da [Digital Innovation One](https://digitalinnovation.one/).

Link da base utilizada neste projeto: [rpeleias/beer_api_digital_innovation_one](https://github.com/rpeleias/beer_api_digital_innovation_one)

Neste projeto foram realizadas algumas modificações e inclusões, a fim de forçar a manutenção no código e testes, as alterações mais importantes foram:
* O produto foi alterado de "cerveja" para "game", necessitando de revisão e refatoração do código;

* Foi adicionada a coluna "min" na entidade Games para permitir conferência de estoque mínimo;

* Nova exception para tratar quando quantidade for menor que o estoque mínimo;

* Adicionada busca (GET) por id (originalmente só existia busca por nome);

* Todas as adições e modificações tiveram testes criados. 

  


Versões utilizadas no desenvolvimento:
* Java 16.0.1;

* Maven 3.6.3;

* IntelliJ IDEA Community Edition 2021.1.3;

* Principais frameworks para testes unitários em Java: JUnit, Mockito e Hamcrest. 




Este projeto foi desenvolvido com foco em:
* Desenvolvimento de testes unitários para validação de funcionalidades básicas: criação, listagem, consulta por nome e exclusão de games.
* Test Driven Development (TDD): apresentação e exemplo prático em duas funcionalidades importantes: incremento e decremento do número de games no estoque.



Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```

Para executar a suíte de testes desenvolvida, execute o seguinte comando:

```shell script
mvn clean test
```

Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

```
http://localhost:8080/api/v1/games
```



Para testar manualmente, os métodos HTTP da Game API (_POST, GET, DELETE e PATCH_), ou seja, realizar teste de interface do usuário, foi incluído o arquivo "Game API.postman_collection.json" na pasta "postman" com coleção que pode ser importada pelo aplicativo [Postman](https://www.postman.com/).