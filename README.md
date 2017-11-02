# Hotel Management Tool

Student project for the lecture: "Seminar aus Software Engineering WS2017"
from the Technical University  in Vienna.

[![Build Status](https://travis-ci.org/sese-xp3-ws2017/sese-xp3-ws2017.svg?branch=master)](https://travis-ci.org/sese-xp3-ws2017/sese-xp3-ws2017)
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

## Architecture

Project based on Spring Framework. In detail we're using the following Spring modules:
 * [Spring Boot](https://projects.spring.io/spring-boot): For easy application Bootstraping
 * [Spring Data](https://projects.spring.io/spring-data): Abstraction layer for JPA for easy ORM operations.
 * [Spring MVC](https://projects.spring.io/spring-webflow): jsp based web frontend.
 * [Spring Security](https://projects.spring.io/spring-security): Security Layer (self explanatory)

## Best Practices and Guidelines

see [codestyle.md](docs/codestyle.md)

## Dev setup

see [devops.md](docs/devops.md)

## Project management process

see [projectmanagement.md](docs/projectmanagement.md)