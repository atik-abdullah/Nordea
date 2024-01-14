# Table of contents
- [Instruction to run the project](#section1)  
    - [Verify port](#subsection1.1)
    - [Project directory structure](#subsection1.2)
    - [1st approach to start and stop the project](#subsection1.3)
    - [2nd approach to start and stop the project](#subsection1.4)
    - [Troubleshooting](#subsection1.5)
    - [Note](#subsection1.6)
- [Instruction to use the project](#section2)
    - [The REST api endpoints static source](#subsection2.1)
    - [The REST api endpoints third party source](#subsection2.2)
    - [Open the client app](#subsection2.3)
    - [Note](#subsection2.4)


## Instruction to run the project <a name="section1"></a>

### Verify port <a name="subsection1.1">

Verify that port **8080** and **8081** are not being used by any other service:

    lsof -i :8080
    lsof -i :8081

Stop the services if there are any:
    
    kill -9 PID

### The project directory sturcture <a name="subsection1.2">

```
Nordea
├── README.md
├── backend
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
├── docker-compose.yaml
└── frontend
    ├── Dockerfile
    ├── frontend
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── src
```
Nordea is the root directory. (Nordea-main when downloaded/cloned from Github)

### 1st approach to start and stop the project <a name="subsection1.3">

Requirement: [Java](#subsection1.6) installed on the system

Run following:

    cd Nordea/backend
    
    // For Linux/Mac/Unix
    ./mvnw spring-boot:run              
    
    // For Windows
    .\mvnw.cmd spring-boot:run              

From another terminal, run:

    cd Nordea/frontend
    
    //For Linux/Mac/Unix
    ./mvnw spring-boot:run              
    
    // For Windows            
    .\mvnw.cmd spring-boot:run          

To stop the services, press Ctrl+C in the respective directory

### 2nd approach to start and stop the project <a name="subsection1.4">

Requirment: Docker installed on the system

Run following:

    // Change to the project root directory
    cd Nordea
    docker compose up

To stop the services, press Ctrl+C and then run:

    docker compose down

### Troubleshooting <a name="subsection1.5">

If you need to change the **docker-compose.yaml** and run have the effect applied, run as follows:

    docker-compose build --no-cache
    docker compose up

### Note <a name="subsection1.6">

- The apps were built with **openjdk version "17.0.5" 2022-10-18** java version present on the system.

- Inside the mvnw.cmd file's **DOWNLOAD_URL** has been changed from https to http. Otherwise the maven wrapper had issues running on Windows.

- The **frontend** folder inside the **frontend** project is a folder that holds syling files required by Vaadin. The name of the folder is conincidentally same as the project name.

## Instruction to use the project <a name="section2"></a>

### The REST api endpoints static source: <a name="subsection2.1">

Note: Currently only three countries - Finland, England and Sweden

- To retrieve all countries:  
    <http://localhost:8081/v1/countries>

- To retrieve one country e.g Finland:  
    http://localhost:8081/v1/countries/Finland

- It is case sensitive, so the following won't return and show customized error response:  
    http://localhost:8081/v1/countries/finland

### The REST api endpoints third party source: <a name="subsection2.2">

- To retrieve all countries:  
    http://localhost:8081/v1/countries-from-restcountries

- To retrieve one country e.g. China:  
    http://localhost:8081/v1/countries-from-restcountries/China
    
- Also works with country name including space in the URL (and it is not case sensitive), e.g.  
    [http://localhost:8081/v1/countries-from-restcountries/united states](http://localhost:8081/v1/countries-from-restcountries/united%20states)

- Supply random text instead of a country name will show a customized error response with detail information to hint the cause of the problem, e.g.  
    http://localhost:8081/v1/countries-from-restcountries/asdf

### Open the client app <a name="subsection2.3">

- Go to: http://localhost:8080

### Note <a name="subsection2.4">
- The endpoint returning all countries will show **two attributes** (name and country_code). 
- Endpoints returning one country will show **four attributes** (name, country_code, capital, population). 
- Make sure the front end app is opened in one tab at a time. The current session handling will show wrong message on the popup notification if there are multiple sessions running at the same time.