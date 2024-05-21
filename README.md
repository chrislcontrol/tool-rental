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
## List of Functional Requirements:
 - RF01 : The system must permit the user register a new friend.
 - RF02 : The system must permit the user register a new tool.
 - RF03 : The system must permit the user register a rent.
 - RF04 : The system must have a report of how much the user spent.
 - RF05 : The software must generate a report with all registered tools, informing the description
  and value of each one, as well as the total expenditure on acquiring the tools.
 - RF06 : The system must have a report with all actives loans.
 - RF07 : The system must have a report with all loans made.
 - RF08 : The system must show if the friend of the last loan still have pendencies.
### Credits: 
```
Christian Silva (RA: 1072322888)
Lucas Roberto da Rosa (RA: 10724111936)
Nicholas da Silva (RA: 1072320766)
```