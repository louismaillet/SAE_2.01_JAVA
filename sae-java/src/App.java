public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        
    }
}
/** 
javac -cp "lib/*" -d bin src/*.java test/*.java
java -cp "bin:lib/*" org.junit.runner.JUnitCore ClassificationTest ClientTest LivreTest 
*/
