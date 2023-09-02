/*Rocca Claudio
 * Matricola 1020395
 * claudio.rocca@studio.unibo.it
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Esercizio1{
    /*Per avere maggiore probabilità che si verifichino delle collisioni
     * tra le coppie già nella tabella e nelle 300 coppie nuovamente generate
     * abbassare la variabile boundK a 100000
     */

    public static void main(String[] args){
        
        HashTable tabella= new HashTable();


        tabella.popola();
        

        /*Scommentare per ottenere la struttura dati stampata

        for(ArrayList<Coppia> list : tabella.getTabella()){
            System.out.print("[ ");
            for(Coppia c : list){
                System.out.print(c.toString() + " ; ");
            }
            System.out.println("]");

        }*/

        tabella.size();

        tabella.prova();
    

    }
}

class HashTable{
    private ArrayList<ArrayList<Coppia>> tabella= new ArrayList<ArrayList<Coppia>>();
    private Random random;
    private int boundK;
    private int boundNp;
    private int fattore;

    //Costruttore della struttura dati
    HashTable(){

        //per avere NMA=10, modificare il fattore a 19
        this.fattore=9;
        this.random=new Random(1020395);
        this.boundK=1000000000;
        this.boundNp=700;
        
        
        this.tabella= new ArrayList<ArrayList<Coppia>>(10000/fattore);
        for(int i=0;i<10000/fattore;i++){

            tabella.add(new ArrayList<Coppia>(fattore));
        }
        
    }

    public ArrayList<ArrayList<Coppia>> getTabella(){
        return this.tabella;
    }

    public Random getRandom(){
        return this.random;
    }

    //popola la tabella di coppie random
    //dopo aver verificato che la chiave non sia già presente
    public void popola(){
        for(int i=0;i<10000;i++){
            int k=random.nextInt(boundK);
            int index=k/(boundK/(10000/fattore));
            
            if(verifica(k)[0]==-1){
                
                int np=random.nextInt(boundNp);
                this.tabella.get(index).add(new Coppia(k, np));
                
            }
            

        }
    }

    //ad indice 0 ritorna -1 se la chiave non è presente
    //ad indice 1 ritorna il numero di accessi effettuati
    //se trova la chiave
    public int[] verifica(int k){
        
        int[] esito={-1, 0};
        int index=k/(boundK/(10000/fattore));
        ArrayList<Coppia> arrayDaVerificare=this.tabella.get(index);

        for(Coppia c : arrayDaVerificare){
            if(c.getKey()==k){
                esito[0]=1;
                break;
            }
            esito[1]++;

        }

            return esito;

        }

    //genera 300 coppie casuali per testare il NMA
    //e stampa le informazioni su un file chiamato "Output Esercizio1"
    public void prova(){

        Random r=new Random(7105419);


        String nomeFile="Output Esercizio1.txt";
        File file=new File(nomeFile);
        try{
            FileWriter fw = new FileWriter(file);
        
            int hit=0;
            int accessi=0;
            int NMA=0;
        
            for(int i=0;i<1000;i++){
                int key=r.nextInt(boundK);
                int index=key/(boundK/(10000/9));

                int[] esito=verifica(key);

                //se la chiave non è presente inserisce la coppia
                if(esito[0]==-1){
                
                    int np=random.nextInt(boundNp);
                    this.tabella.get(index).add(new Coppia(key, np));

                }

                //se la chiave è presente la stampa sul file
                //assieme al numero di accessi effettuati
                else{
                    hit++;
                    accessi+=esito[1]+1;

                    String s=key + " - " + esito[1] + "\n";
                    fw.write(s);
                    fw.flush();
                
                }

            }
            //stampa NMA
            if(hit!=0){
                NMA=accessi/hit;
                fw.write("NMA: " + NMA);
                fw.flush();
            }
        }

    catch(IOException e){
            System.out.println("Non è stato possibile creare il file");
        }
    }

    //calcola il numero di coppie effettivamente presenti nella struttura
    public void size(){
        int i=0;
        for(ArrayList<Coppia> lista : this.tabella){
            for(Coppia c : lista){
                i++;
            }
        }
        System.out.println("La struttura dati contiene " + i + " elementi");

    }



}

class Coppia{
    private int k;
    private int np;
    Coppia(int k, int np){
        this.k=k;
        this.np=np;
    }

    public int getKey(){
        return this.k;
    }
    
    public void setNp(int np){
        this.np=np;
    }
    public String toString(){
        return this.k + " - " + this.np;
    }

}





    

