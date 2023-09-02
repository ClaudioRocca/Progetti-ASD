/*Rocca Claudio
 * Matricola 1020395
 * claudio.rocca@studio.unibo.it
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Esercizio2{
    public static void main(String[] args){

        File file=new File(args[0]);

        try{
            //utilizzo lo scanner per trovare il numero di righe del file
            //e di conseguenza il numero di coppie
            Scanner scan=new Scanner(file);
            int i=0;
            while(scan.hasNextLine()){

                String t=scan.nextLine();
                i++;

            }

            ListaCoppie lista=new ListaCoppie(new Coppia[i]);

            Scanner s=new Scanner(file);
            i=0;

            //utilizza un secondo scanner per leggere il file
            // e riempire l'array di coppie
            while(s.hasNextLine()){

                String str=s.nextLine();
                String[] tokens=str.split(" ");

                Coppia c= new Coppia(Integer.parseInt(tokens[0]), tokens[1]);
                lista.getLista()[i]=c;

                i++;
            }
            scan.close();

            lista.trovaCoppieCompreseTra(Integer.parseInt(args[1]),
             Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
            }
            
            catch(FileNotFoundException e){
            System.out.println("Errore nella lettura del file");
        }
    }
        

}


class Coppia{
    private int x;
    private String q;

    public Coppia(int x, String q){
        this.x=x;
        this.q=q;
    }

    public int getX(){
        return this.x;
    }

    public String getQ(){
        return this.q;
    }

    public String toString(){
        return "" + this.x + " - " + this.q;
    }

}

class ListaCoppie{
    private Coppia[] lista;

    ListaCoppie(Coppia[] lista){
        this.lista=lista;
    }

    public Coppia[] getLista(){
        return this.lista;
    }

    //trova le coppie che soddisfano i requisiti richiesti
    
    public void trovaCoppieCompreseTra(int a, int b, int s, int c){

        ArrayList<Coppia> coppieCompreseTraAB= new ArrayList<Coppia>();
         ArrayList<Coppia> coppieMaggioriDiC= new ArrayList<Coppia>();


         //aggiunge le coppie che soddisfano il punto 1 ad un array
        for(Coppia cp : this.lista){

            if(cp.getX()>=a && cp.getX()<=b && cp.getQ().length()<=s){
                coppieCompreseTraAB.add(cp);
            }
        }

        for(Coppia cp : coppieCompreseTraAB){

            System.out.println(cp.toString());

        }

        System.out.println("---------------");

        //aggiunge le coppie che soddisfano il punto 2 ad un array
        for(Coppia cp : this.lista){
            if(cp.getX()>=c){
                coppieMaggioriDiC.add(cp);
            }
        }

        for(Coppia cp : coppieMaggioriDiC){
            System.out.println(cp.toString());
        }

        Scanner scan=new Scanner(System.in);

        //Implementazione del menù che consente di scegliere
        //la prossima istruzione da effettuare
        System.out.println(
            "Scegliere il comando per continuare: "
            + "\n1 - Stampa nuovamente le coppie con a<X<b e lunghezza<s"
            + "\n 2 - Stampa nuovamente le coppie con X>c"
            + "\n 3 - Termina l'esecuzione");
            
        int input=scan.nextInt();

        while(input!=3){

            switch(input){

                case 1:
                    for(Coppia cp : coppieCompreseTraAB){
                        System.out.println(cp.toString());
                    }
                    System.out.println("---------------");
                    break;

                case 2:
                    for(Coppia cp : coppieMaggioriDiC){
                        System.out.println(cp.toString());
                    }
                    System.out.println("---------------");
                    break;
                    
                default:
                    System.out.println("Input non corretto, riprovare");
                    break;

        }

        System.out.println("Scegliere il comando per continuare: "
         + "\n1 - Stampa nuovamente le coppie con a<X<b e lunghezza<s"
         + "\n 2 - Stampa nuovamente le coppie con X>c"
        + "\n 3 - Termina l'esecuzione");
        
        input=scan.nextInt();

        System.out.println("---------------"); 
    }


}
}

