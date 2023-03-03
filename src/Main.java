import Importar.*;
import LleiHondt.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int resposta;
        Scanner scan = new Scanner(System.in);

        System.out.print("-------------------------------\n" +
                           "1 - Importar dades a la BBDD   \n" +
                           "2 - Calcular llei D'Hondt      \n"+
                           "3 - Sortir del programa        \n" +
                           "-------------------------------\n" +
                           ">   ");

        resposta = scan.nextInt();

        while (resposta >= 4 || resposta <= 0){
            System.out.println("Introdueix un número vàlid");
            System.out.print(">   ");
            resposta = scan.nextInt();
        }
        if (resposta == 1 ){
            ImportarComunitatsAutonomes.main();
            System.out.println();
            ImportarProvincies.main();
            System.out.println();
            ImportarMunicipis.main();
            System.out.println();
            ImportarPartitsPolitics.main();
            System.out.println();
            ImportarPersones.main();
            System.out.println();
            ImportarEleccionsMunicipis.main();
            System.out.println();
            ImportarCandidats.main();
            System.out.println();
            ImportarVotsCa.main();
            System.out.println();
            ImportarVotsProvincials.main();
            System.out.println();
            ImportarVotsMunicipal.main();

        } else if (resposta == 2) {
            LleiHondt.main();
        } else if(resposta == 3){
            System.out.println("Fins a la propera!");
            System.exit(1);
        }else System.out.println("Error !");
    }
}
