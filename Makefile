build-package:
	mvn clean package

execute: .build-package:
	java -cp "target/tool-rental-1.0-SNAPSHOT.jar:target/lib/*" tool.rental.app.App

