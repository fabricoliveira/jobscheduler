# Job Scheduler


## Objetivo

O objetivo desse algoritmo é organizar a prioridade de execução de um Agendador de Tarefas(Job).

## Como utilizar o programa

Você deve colar no arquivo input.txt, que está na pasta raíz do projeto, um Array de Json com o seguinte formato:
    
    Janela de execução: 2019-11-10 09:00:00 até 2019-11-11 12:00:00
    [
      {
        "ID": Long,
        "Descrição": String, 
        "Data Máxima de conclusão": LocalDateTime formatado -> yyyy-MM-dd HH:mm:ss, 
        "Tempo estimado": Long formatado -> 2 horas,
      },
    ]

Para executar ou debugar o programa, você deve rodar como "Spring Boot App"

#### ATENÇÃO:
##### - As chaves ID, Descrição, Data Máxima de conclusão e Tempo estimado, devem estar dentro das aspas duplas.
##### - Todas as aspas simples serão removidas.
\
O programa apresentará como resultado um Array contendo Arrays de Inteiros, com o seguinte formato:

    [
      [Long, Long],
      [Long] 
    ]

Os valores Long nos arrays, correspondem ao ID da Tarefa (Job).

Cada array interno, estará organizado de forma que as somas do Tempo de Execução, não ultrapasse 8 horas.

#### O resultado será apresentado no Console da sua IDE e também estará no arquivo output.txt na pasta raíz do projeto.

## Tecnologias utilizadas

- O programa foi desenvolvido utilizando tecnologias baseadas em Java.

- O framework utilizado foi o Spring Boot, por facilitar o desenvolvimento de aplicações standalone e pela sua simplificação com a Inversão de Controle.

- Para a Gerenciar as Dependências e realizar o Build do programa, está sendo utilizado o Maven.

- Para os Testes Unitários, está sendo utilizado o Junit.

- Para o Versionamento do código, está sendo utilizado o GIT.

- SonarQube para analisar a qualidade do código.

#### Considerações:

- O programa foi desenvolvido com o Spring Boot na versão 2.3.1.
- O programa foi desenvolvido com o Java na versão 11.
- O programa funciona somente com as versões do Java 8 e posteriores.
- Caso você necessite utilizar a versão, o arquivo pom.xml localizado na pasta raíz do projeto, 
  deve ser o trecho abaixo e mudar o número 11 para a versão desejada:

        <properties>
            <java.version>11</java.version>
        </properties>

- É necessário instalar o Lombok na sua IDE. O Lombok está sendo utilizado para a gerar os Getters, 
  Setters, Construtores, toString, Equals and HashCodes das classes.
  Para mais informações, acesse o site do [Projeto Lombok](https://projectlombok.org/).
