#javac -cp "/usr/share/java/mariadb-java-client.jar" -d bin src/*.java
#javac -cp "/usr/share/java/mariadb-java-client.jar" -d bin Javafx/*.java
#java -cp "/usr/share/java/mariadb-java-client.jar:bin" App
#TestLibrairie
javac -d bin -cp lib/junit-4.13.2.jar:lib/hamcrest-2.2.jar src/*.java
java -cp bin:lib/junit-4.13.2.jar:lib/hamcrest-2.2.jar org.junit.runner.JUnitCore src.TestLibrairie

#Execution de l'application JavaFX
javac -d bin -cp "lib/junit-4.13.2.jar:lib/hamcrest-2.2.jar:bin" src/*.java javafx/*.java --module-path lib/ --add-modules javafx.controls,javafx.fxml
java --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls,javafx.fxml -cp "bin:lib/mariadb-java-client-3.3.3.jar" javafx.Connexion
