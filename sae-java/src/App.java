public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Livre livre = new Livre(123456789, "Java Programming", 500, "2023-10-01", 29.99);
        System.out.println(livre.toString());
        
    }
}
/** 
javac -cp "lib/*" -d bin src/*.java test/*.java
java -cp "bin:lib/*" org.junit.runner.JUnitCore ClassificationTest ClientTest LivreTest 
*/
