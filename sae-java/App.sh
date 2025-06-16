#javac -cp "/usr/share/java/mariadb-java-client.jar" -d bin src/*.java
#java -cp "/usr/share/java/mariadb-java-client.jar:bin" App
javac -d bin src/*.java src/controleur/*.java --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls
java -cp ./bin:/usr/share/java/mariadb-java-client.jar --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls Executer