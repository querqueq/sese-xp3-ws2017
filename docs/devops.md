# Devops

## Workflow

* Checkout current master branch and create a new feature branch 
  (starting with "f_")
* make changes and commits in that branch
* if necessary rebase the master back into your feature branch to 
  keep it up to date
* push branch and create a pull request to master
* wait for the automatic tools to test your pull request
  * if some CI steps report problems, correct them, recommit and push.
* If a commit is technically sound (all automatic checks green) it has to be
  reviewed by another contributor. If the contributor detects some problems
  he will request a change, otherwise he'll approve the change.
* After successful approval, you can finally merge the branch.

> Github workflow description: [Github workflow]

## CI/CD
Every push to a branch and every pull request will be build by travis-ci.
Every change in master is automatically deployed to the dev heroku instance after
build.
 
Dev Heroku: [![Heroku](http://heroku-badge.herokuapp.com/?app=sese-xp-ws2017-dev&style=flat&svg=1)](https://sese-xp-ws2017-dev.herokuapp.com)

All tagged releases are build and:
* Released to GitHUB releases
* Deployed to prod heroku: [![Heroku](http://heroku-badge.herokuapp.com/?app=sese-xp-ws2017-dev&style=flat&svg=1)](https://sese-xp3-ws2017.herokuapp.com)

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



[Github workflow]: https://guides.github.com/introduction/flow/