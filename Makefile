all: install

install: 
	mvn install

release:
	mvn release:prepare -DignoreSnapshots=true -Dmaven.test.skip=true -Dresume=false

