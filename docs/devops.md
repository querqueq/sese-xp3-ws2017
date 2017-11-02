# Devops

> IMPORTANT: We use [LOMBOK](https://projectlombok.org/) to generate easy POJO's.
For this to work you need to enable Annotation Processing.
(https://stackoverflow.com/questions/24006937/)

```shell

## Install dependencies

mvn clean install

## Build app and run tests

mvn package
```

All merge requests are automatically build via travis-ci. Currently missing is automatic CD, code-analysis and coverage analysis.

### Folder Structure

```
at.ac.tuwien.student.sese2017.xp
        +- hotelmanagement
           +- Application.java
           |
           +- config
           |   +- Settings.java
           |   +- JPASettings.java
           |
           +- domain
           |   +- dao
           |   |   +- Customer.java
           |   |
           |   +- repository
           |   |   +- CustomerRepository.java
           |   |
           |   ... to be extended
           |
           +- service
           |   +- CustomerService.java
           |
           +- web
           |   +- CustomerController.java
           |
           +- util
              +- SomeStaticClass.java
```

If something is missing, this folder structure will be updated.