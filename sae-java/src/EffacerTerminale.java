// Voici la seule partie du code qui a été faite avec IA, Car impossible de trouver de la doc
package src;

public class EffacerTerminale {
    public static void clearConsole() {
        try {
            // Exécute la commande 'clear' sur les systèmes Unix/Linux
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("Impossible de nettoyer le terminal : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Avant le nettoyage...");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        clearConsole();
        System.out.println("Après le nettoyage !");
    }
}
