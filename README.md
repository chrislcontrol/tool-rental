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
## List of Non-Functional Requirements:
 - RNF01 : The software must be executed locally on user's computer, with no need of wifi connection.
 - RNF02 : The software should have an easy-to-use and intuitive interface suitable for non-technical 
users like your great uncle.
 - RNF03 : The software must guarantee the security of registered data, protecting users' personal
and financial information.
 - RNF04 : The system must integrate effectively and reliably with Google Calendar, 
ensuring correct sharing of event reminders.
### Credits: 
```
Christian Silva (RA: 1072322888)
Francesco Ghisi (RA: 1072325009)
Lucas Roberto da Rosa (RA: 10724111936), 10724111936, 10724111936
Nicholas da Silva (RA: 1072320766)
Vinícius Goulart Alves (RA: 1072320937)
```
