# Hotel Management Tool

Student project for the lecture: "Seminar aus Software Engineering WS2017"
from the Technical University  in Vienna.

[![Build Status](https://travis-ci.com/lkerck/sese-xp3-ws2017.svg?token=KupCS9kWrzEcfVJw5K7C&branch=master)](https://travis-ci.com/lkerck/sese-xp3-ws2017)
[![Coverage Status](https://coveralls.io/repos/github/sese-xp3-ws2017/sese-xp3-ws2017/badge.svg?branch=ENV_CI_CD_env)](https://coveralls.io/github/sese-xp3-ws2017/sese-xp3-ws2017?branch=ENV_CI_CD_env)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=at.ac.tuwien.student.sese2017.xp:hotelmanagement)](https://sonarcloud.io/dashboard/index/at.ac.tuwien.student.sese2017.xp:hotelmanagement)

## Dev Setup

> IMPORTANT: We use [LOMBOK](https://projectlombok.org/) to generate easy POJO's.
For this to work you need to enable Annotation Processing.
(https://stackoverflow.com/questions/24006937/)

```shell
# Install dependencies
mvn clean install

# Build app and run tests
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



## Best Practices and Guidelines
Important for extreme programming is the use of "best practice" development style.
The following rules are our mandatory style guides:

* **Git workflow**: Basically create feature branch and submit a merge request.
  [GitHub tutorial](https://guides.github.com/introduction/flow/)
* **Merge guidelines**: Following properties are mandatory for each merge:
  * Code needs to build in Travis.
  * Needs to be reviewed by another developer.
  * Needs at least ??% code coverage.
  * Style guide quota.
> TODO: Add quotas

* **Java code style and layout**: Basically the
  [Google Java style](https://google.github.io/styleguide/javaguide.html)
  with the following additions:
  * Interfaces begin with an I (e.g.: ICharacter)
  * Comments: As many as necessary to make the code easily readable.
  * Curly braces can be omitted on guard clauses. Only allowed on top of a block
    and when no else statement present. After single line guard clauses an empty
    line has to follow.
    ```java
    // Google default
    public void setX(String x) {
      if(x == null) {
        return;
      }
      if(x.isEmpty()) {
        throw new Exception();
      }
      ...
    }

    // Also allowed (and preferred)
    public void setX(String x) {
      if(x == null) return;
      if(x.isEmpty()) throw new Exception();

      ...
    }

    // not allowed
    public void setX(String x) {
      if(x == null) return;
      else x.trim();
      ...
    }

    // not allowed
    public void setX(String x) {
      ...
      ResultSet rs = stmt.query();
      if (rs == null) return;
      ...
    }

    // Also allowed (new block)
    public void setX(String x) {
      ...
      try (Statement stmt = con.createStatement()) {
        if(stmt == null) return;

        ...
      } catch (SQLException e) {
        ...
    }
    ```


## Architecture
Project based on Spring Framework. In detail we're using the following Spring modules:
 * [Spring Boot](https://projects.spring.io/spring-boot): For easy application Bootstraping
 * [Spring Data](https://projects.spring.io/spring-data): Abstraction layer for JPA for easy ORM operations.
 * [Spring MVC](https://projects.spring.io/spring-webflow): jsp based web frontend.
 * [Spring Security](https://projects.spring.io/spring-security): Security Layer (self explanatory)
