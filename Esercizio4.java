/*Rocca Claudio
 * Matricola 1020395
 * claudio.rocca@studio.unibo.it
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Esercizio4{
    public static void main(String[] args){
        File f=new File(args[0]);

        //Costo computazionale lettura da file: O(n+m)
        //dove n ed m sono i numeri di nodi e di archi
        
        Nodo[] nodes=leggiFile(f);

        for(int i=0;i<nodes.length;i++){
            System.out.println("-----------------------");
            System.out.println("s     d");


            //per trovare la distanza da un nodo sorgente a tutti gli altri nodi
            //il costo computazionale è O(n-1*m)
            bellmanFordProva(nodes, nodes[i]);
            
        }

    }

    //metodo che crea un array di nodi con i relativi archi
    //funzionante solo con 12 nodi e 15 archi

    /*legge in input il file dato nella consegna senza le righe di commenti
     * e con una riga vuota tra le informazioni dei nodi e quelle degli archi
     * ESEMPIO FILE UTILIZZATO: 
    ATLAM5 ( -84.3833 33.75 )
    ATLAng ( -85.50 34.50 )
    CHINng ( -87.6167 41.8333 )
    DNVRng ( -105.00 40.75 )
    HSTNng ( -95.517364 29.770031 )
    IPLSng ( -86.159535 39.780622 )
    KSCYng ( -96.596704 38.961694 )
    LOSAng ( -118.25 34.05 )
    NYCMng ( -73.9667 40.7833 )
    SNVAng ( -122.02553 37.38575 )
    STTLng ( -122.30 47.60 )
    WASHng ( -77.026842 38.897303 )
    (
    ATLAM5_ATLAng ( ATLAng ATLAM5 ) 9920.00 0.00 0.00 0.00 ( 40000.00 133.00 )
    ATLAng_HSTNng ( HSTNng ATLAng ) 9920.00 0.00 0.00 0.00 ( 40000.00 1081.00 )
    ATLAng_IPLSng ( IPLSng ATLAng ) 2480.00 0.00 0.00 0.00 ( 40000.00 591.00 )
    ATLAng_WASHng ( WASHng ATLAng ) 9920.00 0.00 0.00 0.00 ( 40000.00 901.00 )
    CHINng_IPLSng ( IPLSng CHINng ) 9920.00 0.00 0.00 0.00 ( 40000.00 260.00 )
    CHINng_NYCMng ( NYCMng CHINng ) 9920.00 0.00 0.00 0.00 ( 40000.00 1147.00 )
    DNVRng_KSCYng ( KSCYng DNVRng ) 9920.00 0.00 0.00 0.00 ( 40000.00 745.00 )
    DNVRng_SNVAng ( SNVAng DNVRng ) 9920.00 0.00 0.00 0.00 ( 40000.00 1516.00 )
    DNVRng_STTLng ( STTLng DNVRng ) 9920.00 0.00 0.00 0.00 ( 40000.00 1573.00 )
    HSTNng_KSCYng ( KSCYng HSTNng ) 9920.00 0.00 0.00 0.00 ( 40000.00 1028.00 )
    HSTNng_LOSAng ( LOSAng HSTNng ) 9920.00 0.00 0.00 0.00 ( 40000.00 2196.00 )
    IPLSng_KSCYng ( KSCYng IPLSng ) 9920.00 0.00 0.00 0.00 ( 40000.00 903.00 )
    LOSAng_SNVAng ( SNVAng LOSAng ) 9920.00 0.00 0.00 0.00 ( 40000.00 505.00 )
    NYCMng_WASHng ( WASHng NYCMng ) 9920.00 0.00 0.00 0.00 ( 40000.00 336.00 )
    SNVAng_STTLng ( STTLng SNVAng ) 9920.00 0.00 0.00 0.00 ( 40000.00 1138.00 )
    )
     */

    /*legge il file in input e crea un vettore di nodi
     * ogni nodo ha come attributo una lista degli archi che partono da quel nodo
     */
    public static Nodo[] leggiFile(File f){
        Nodo[] nodi= new Nodo[12];

    
    try{

        Scanner scan=new Scanner(f);
        Scanner scanner=new Scanner(f);

        //utilizzo una mappa per modificare i nomi dei nodi in numeri interi
        Map<String, Integer> stringToId=new HashMap<String, Integer>();
        

        String s="";
        int i=0;

        //utilizzo un secondo scanner, che mi servirà per trovare il peso max
        //degli archi
        String throwaway="";
        while(i<12){
            s=scan.nextLine();
            scanner.nextLine();
            
            String[] tokensNodi=s.split(" ");
            //associo ad ogni nodo un id
            stringToId.put(tokensNodi[0], i);
            nodi[i]= new Nodo(i);
            i++;
        }

        i=0;
        //faccio saltare la riga vuota a scan e scanner
        scan.nextLine();
        scanner.nextLine();

        //utilizzo il secondo scanner per salvare i pesi e trovare il massimo
        double pesoMax=0;
        double[] pesi=new double[15];
        while(i<15){
            throwaway=scanner.nextLine();
            
            String[] ts=throwaway.split(" ");
            if(Double.parseDouble(ts[5])>pesoMax){
                pesoMax=Double.parseDouble(ts[5]);
            }

            pesi[i]=Double.parseDouble(ts[5]);
            i++;
        }

        i=0;
    
        //crea gli archi e li aggiunge ai rispettivi nodi
        while(i<15){
            s=scan.nextLine();
            String[] tokensArchi=s.split(" ");
            //salvo il peso dell'arco come il rapporto tra
            //il peso massimo delle connessioni e il suo peso
            double pesoArco=pesoMax/Double.parseDouble(tokensArchi[5]);

            Arco a= new Arco(stringToId.get(tokensArchi[2]),
            stringToId.get(tokensArchi[3]),
            pesoArco);

            

            //creo anche l'arco con la direzione opposta
            Arco aReverse= new Arco(stringToId.get(tokensArchi[3]),
            stringToId.get(tokensArchi[2]),
            pesoArco);


            //aggiungo ai nodi i propri archi in partenza
            int nodoDaModificare=stringToId.get(tokensArchi[2]);
            nodi[nodoDaModificare].getCollegamenti().add(a);

            int nodoDaModificareReverse=stringToId.get(tokensArchi[3]);
            nodi[nodoDaModificareReverse].getCollegamenti().add(aReverse);

            i++;
        }

        scan.close();
        scanner.close();

    }
    catch(FileNotFoundException e){
        System.out.println("Errore nella lettura del file");
    }
    
    return nodi;
    }


    //applica l'algoritmo di Bellman Ford ad un array di nodi
    //partendo da un nodo sorgente trova i percorsi più brevi
    //per raggiungere tutti gli altri nodi
    public static int[] bellmanFordProva(Nodo[] nodi, Nodo nodoSorgente){

        //imposto le distanze dei nodi ad infinito
        //tranne quella del nodo sorgente che è 0
        double[] distanze= new double[12];
        Arrays.fill(distanze, Double.MAX_VALUE);
        distanze[nodoSorgente.getId()]=0.0;


        //utilizzo un altro array per vedere se alla fine di un ciclo le distanze sono cambiate
        //se non sono cambiate posso smettere di iterare

        //il controllo mi consente di effettuare meno iterazioni
        //ma ha un costo pari al numero di nodi del grafo 
        double[] check=new double[12];
        Arrays.fill(check, Double.MAX_VALUE);
        check[nodoSorgente.getId()]=0.0;

        //riempo l'array dei predecessori con -1
        int[] predecessori=new int[12];
        Arrays.fill(predecessori,-1);

        for (int i=1;i<12;i++) {  
            for (int j=0;j<12;j++) {
                for (Arco collegamento : nodi[j].getCollegamenti()) {

                    int u=collegamento.getSorgente();
                    int v=collegamento.getDestinazione();
                    double peso=collegamento.getPeso();

                    if (distanze[u]+peso<distanze[v]) {
                        distanze[v] = distanze[u] + peso;
                        predecessori[v]=u;
                    }
                }
            }

            if(Arrays.equals(distanze, check)){
                break;
            }

        }
            
        for (int j=0; j<distanze.length;j++) {
                printShortestPath(predecessori, j);
            }

        return predecessori;

            
        }

        //stampa i percorsi più brevi nel formato desiderato
        public static void printShortestPath(int[] predecessori, int destinazione) {
        ArrayList<Integer> path = new ArrayList<Integer>();
        int current = destinazione;

        //va avanti finchè non trova un nodo senza predecessori
        while (current != -1) {
            path.add(current);
            //imposta il nodo attuale come il predecessore
            // del nodo analizzato in precedenza
            current = predecessori[current];
        }


        //inverto l'ordine della lista in modo da avere l'ordine dei nodi
        //stampato correttamente
        Collections.reverse(path);
        
        System.out.print(path.get(0) + "    " + destinazione + ": "
                   + (path.size()-1) + "|");

        for (int node : path) {
            if(node==destinazione){
                System.out.print(node);

            }
            else
                System.out.print(node+"->");
        }
        System.out.println();
    }

        

    }

    class Nodo{
        private int id;
        private ArrayList<Arco> collegamenti;

        Nodo(int id){
            this.id=id;
            this.collegamenti=new ArrayList<Arco>();
        }

        public int getId(){
            return this.id;
        }

        public ArrayList<Arco> getCollegamenti(){
            return this.collegamenti;
        }

    }

    class Arco{
        private int sorgente;
        private int destinazione;
        private double peso;

        Arco(int sorgente, int destinazione, double peso){
            this.sorgente=sorgente;
            this.destinazione = destinazione;
            this.peso = peso;
        }

        public int getSorgente(){
            return this.sorgente;
        }

        public int getDestinazione(){
            return this.destinazione;
        }

        public double getPeso(){
            return this.peso;
        }

        public String toString(){
            return this.sorgente + " - " + this.destinazione + " - " + this.peso;
        }
    }
