/*Rocca Claudio
 * Matricola 1020395
 * claudio.rocca@studio.unibo.it
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Esercizio5{
    public static void main(String[] args){

        int numeroNodi=12;

        /*Il grafo è rappresentato come una matrice che contiene le distanze tra i nodi
         * Nella i-esima riga della matrice sono contenute le distanze dal nodi i
         * a tutti gli altri nodi
         * Nell'elemento grafo[i][j] è contenuta la distanza del nodo i dal nodo j
         */
        double[][] grafo= new double[numeroNodi][numeroNodi];
        int[][] predecessori=new int[numeroNodi][numeroNodi];


        //imposta le distanze tra i nodi a infinito
        //tranne quelle sulla diagonale principale della matrice
        //che rappresentano le distanze tra un nodo e il nodo stesso
        for(int k=0;k<numeroNodi;k++){

            for(int j=0;j<numeroNodi;j++){
                if(k==j){
                    grafo[k][j]=0;
                }
                else{
                    grafo[k][j]=Double.MAX_VALUE;
                }        
            }
        }
        
        File file=new File(args[0]);
        
        //Legge il file e riempe la matrice con i pesi degli archi del grafo
        try{
            Scanner scan=new Scanner(file);
            Scanner scanner=new Scanner(file);
            HashMap<String, Integer> stringToId=new HashMap<String, Integer>();
            int i=0;
            while(i<12){
                String s=scan.nextLine();
                String[] tokens=s.split(" ");
                stringToId.put(tokens[0], i);
                
                i++;
                scanner.nextLine();
            }

            //Faccio saltare la riga vuota agli scanner
            scan.nextLine();
            scanner.nextLine();

            //utilizzo il secondo scanner per salvare i pesi e trovare il massimo
            double pesoMax=0;
            double[] pesi=new double[15];
            i=0;
            while(i<15){
                String s=scanner.nextLine();
            
                String[] ts=s.split(" ");
                if(Double.parseDouble(ts[5])>pesoMax){
                    pesoMax=Double.parseDouble(ts[5]);
                }

            pesi[i]=Double.parseDouble(ts[5]);
            i++;
            }

            i=0;

            //riporta i pesi all'interno della matrice
            while(i<15){
            String s=scan.nextLine();
            
            String[] ts=s.split(" ");
            int sorgente=stringToId.get(ts[2]);
            int destinazione=stringToId.get(ts[3]);

            grafo[sorgente][destinazione]=pesoMax/Double.parseDouble(ts[5]);
            grafo[destinazione][sorgente]=pesoMax/Double.parseDouble(ts[5]);

            i++;

            }
            
            floydWarshall(grafo, predecessori);
            System.out.println("----------------------------");

            for(int k=0;k<12;k++){
                System.out.println("-----------------------");
                System.out.println("s    d   dist.path");
                    
                for(int j=0;j<12;j++){
                    System.out.print(k + "   " + j + " | ");
                    stampaCammino(predecessori, k, j);
                    System.out.println();
                }
            
            }
        }

        catch(FileNotFoundException e){
            System.out.println("Errore nella lettura del file");
        }


    }


    //utilizza l'algoritmo di FLoyd Warshall per trovare
    //i percorsi più brevi tra i nodi
    public static void floydWarshall(double[][] distanze, int[][] predecessori){

        //riempe la matrice dei predecessori
        //costo computazionale O(n^2)
        for (int i=0;i<12;i++) {
            for (int j=0;j<12;j++) {
                //Se il predecessore del nodo è il nodo stesso
                //imposto il predecessore a 0
                if (i==j){
                    
                    predecessori[i][j]=0;  
                }
                //Se la distanza è ancora infinito
                //il nodo non ha predecessori
                else if(distanze[i][j]==Double.MAX_VALUE) {
                    
                    predecessori[i][j]=-1;  
                } 
                else {
                    //inserisco i come predecessore
                    predecessori[i][j]=i;   
                }
            }
        }


        //trova le distanze minime tra i nodi
        //Costo computazionale: O(n^3)
        /*Funzionamento dell'algoritmo di Floyd Warshall:
         * cercando la distanza minima tra due nodi i,j
         * si controllano tutti gli altri nodi k del grafo
         * per trovare un cammino più breve da i a j
         * che passa per k.
         * Nel caso lo si trovi, la distanza minima viene sostituita
         * con la distanza appena trovata
         */
        for (int k=0;k<12;k++) {
            for (int i=0;i<12;i++) {
                for (int j=0;j<12;j++) {
                    if (distanze[i][k]!=Double.MAX_VALUE &&
                        distanze[k][j]!=Double.MAX_VALUE &&
                        distanze[i][k]+distanze[k][j]<distanze[i][j]) {
                        distanze[i][j]=distanze[i][k]+distanze[k][j];
                        predecessori[i][j]=predecessori[k][j];
                    }
                }
            }
        }


    }

    //stampa i predecessori di ogni nodo
    //costo computazionale: O(p)
    //dove p è il numero di predecessori di un nodo
    public static void stampaCammino(int[][] predecessori,
     int sorgente, int destinazione)
     {
        if (sorgente==destinazione) {
            System.out.print(destinazione+"->");
        } else {
            stampaCammino(predecessori, sorgente, predecessori[sorgente][destinazione]);
            System.out.print(destinazione + "->");
        }
    }

    

}
    

