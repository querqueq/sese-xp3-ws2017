# Devops

## Workflow

* Checkout current master branch and create a new feature branch 
  (starting with "f_")
* make changes and commits in that branch
* if nessesary rebase the master back into your feature branch to 
  keep it up to date
* push branch and create a pull request to master
* wait for the automatic tools to test your pull request
  * if some CI steps report problems, correct them, recommit and push.
* If a commit is technically sound (all automatic checks green) it has to be
  reviewed by another contributor. If the contributor detects some problems
  he will request a change, otherwise he'll approve the change.
* After successful approval, you can finally merge the branch.

> Github workflow description: [Github workflow](https://guides.github.com/introduction/flow/)

## Basic development 

> IMPORTANT: We use [LOMBOK](https://projectlombok.org/) to generate easy POJO's.
For this to work you need to enable Annotation Processing.
(https://stackoverflow.com/questions/24006937/)

```shell

## Install dependencies

mvn clean install

## Build app and run tests

mvn package
```

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