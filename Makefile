.DEFAULT_GOAL := build-run

run:
	java -jar ./target/JavaFundamentals7-1.0-SNAPSHOT-jar-with-dependencies.jar

test:
	./mvnw test
#	java -jar ~/.m2/repository/org/junit/platform/junit-platform-console-standalone/1.4.0-M1/junit-platform-console-standalone-1.4.0-M1.jar

clean:
	rm -rf ./target

build-run: build run

build: 
	./mvnw clean package

update:
	./mvnw versions:update-properties versions:display-plugin-updates
