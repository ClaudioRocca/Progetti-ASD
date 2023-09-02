/*Rocca Claudio
 * Matricola 1020395
 * claudio.rocca@studio.unibo.it
 */

import java.io.File;
import java.util.Scanner;

public class Esercizio3{

    public static void main(String[] args){

        //questo programma utilizza il formato di file dato nella consegna
        //con le linee di commenti
        //senza righe vuote tra una informazione e l'altra

        //Costo computazionale lettura da file: O(n)
        //con n numero di dipendenti
        File listaDipendenti=new File(args[0]);
        try{
            Scanner scan=new Scanner(listaDipendenti);
            scan.nextLine();

            //leggo la prima riga del file, contenente il numero di dipendenti
            int numeroDipendenti=Integer.parseInt(scan.nextLine());
            double[] dipendenti=new double[numeroDipendenti];
            scan.nextLine();

            

            //popolo un vettore con i dati sui dipendenti
            int i=0;
            while(i<numeroDipendenti){
                dipendenti[i]=Double.parseDouble(scan.nextLine());
                i++;
            }
            //faccio saltare la riga del commento allo scanner
            scan.nextLine();

            double soglia=Double.parseDouble(scan.nextLine());
            scan.close();

            //se il valore soglia è minore del valore minimo del vettore
            //o maggiore del valore massimo del vettore
            //non è necessario nessun controllo sui dati
            if(soglia<dipendenti[0]){
                System.out.println("Dipendenti premiati: "  + dipendenti.length);
            }
            else if(soglia>dipendenti[dipendenti.length-1]){
                System.out.println("Dipendenti premiati: 0" );

            }
            else
            //inizia la ricerca
                ricercaValoriMinoriDi(0, i-1, soglia, dipendenti);
        }
        catch(Exception e){
            System.out.println("Errore nella lettura del file");
        }

    }

    //trova i valori maggiori della soglia in modo ricorsivo
    
    public static void ricercaValoriMinoriDi(int estremoInferiore,
     int estremoSuperiore, double soglia, double[] dipendenti){
        

        //il metodo si arresta quando non ci sono piè elementi
        //tra i due estremi
        if(estremoSuperiore==estremoInferiore+1){

            //caso che serve a evitare errori quando entrambi gli estremi
            //sono uguali al valore soglia
            if(dipendenti[estremoSuperiore]==dipendenti[estremoInferiore]){
                System.out.println("Dipendenti premiati: 0");
            }
            else
                System.out.println("Dipendenti premiati: " 
                + (dipendenti.length-estremoSuperiore));
        }

        else{

            //divido il vettore a metà
            int m=Math.round(estremoInferiore+estremoSuperiore)/2;

            //cerco nella metà più "grande" se la soglia è > del punto medio
            if(soglia>=dipendenti[m]){
                ricercaValoriMinoriDi(m, estremoSuperiore, soglia, dipendenti);
            }
            //cerco nella metà più "piccola" se la soglia è < del punto medio
            else if(soglia<dipendenti[m]){
                ricercaValoriMinoriDi(estremoInferiore, m, soglia, dipendenti);
            }
    }   

        
    }
        
}


