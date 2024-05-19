# tool-rental
A software development for graduation subject


## Requirements:
```
- SDK 21
- SQL Lite 3
- Maven
```

## Run project:

```
- set main class as: src.main.java.tool.rental.app.App
- run main class (don´t forget to build with Maven dependencies)
```

## Workflow:
Presentation(Frame) -> Controller(Domain) -> UseCase(Domain) -> Repository(Domain.Infra.DB)
Presentation(Frame) <- Controller(Domain) <- UseCase(Domain) <- Repository(Domain.Infra.DB)

## Responsibilities:
- Presentation(Frame): Interact with user to get inputs and display outputs.
####
- Controller(Domain): Parse user's input data, select the correct Use Case to process and then 
return to user the correct Frame according to Use Case response.
####
- UseCase(Domain): Process input data and store in database.
####
- Repository(Domain.Infra.DB): Database adapter that abstracts database's queries and dialect.
  * Repository's methods use to return subclasses of Model, witch is an database's entity abstraction for Java class.

### Credits: 
```
Christian Silva (RA: 1072322888)
Lucas Roberto da Rosa (RA: 10724111936)
Nicholas da Silva (RA: 1072320766)
```