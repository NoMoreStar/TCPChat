package tcpchat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manuel Antonini
 */
public class Gestore{
    BufferedReader tastiera;
    Socket connection;
    DataInputStream in;
    DataOutputStream out;
    String inser;
    Boolean stato;
    String nome; //Nome comunicante
    String salva;
    public Gestore(Socket s,String nome){
        tastiera = new BufferedReader(new InputStreamReader(System.in));
        stato = true;
        connection = s;         //Passaggio del Socket
        this.nome=nome;
        inser = "";
        salva = inser;
        try {
            in = new DataInputStream(connection.getInputStream());   
            out = new DataOutputStream(connection.getOutputStream());                       //Inizializzazione degli stream di input e output
        } catch (IOException ex) {
            Logger.getLogger(Gestore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void Menu(){
        System.out.println("/autore per aggiornare il nome dell'utente");
        System.out.println("/nonlinea per impostare lo stato offline");
        System.out.println("/inlinea per impostare lo stato online");
        System.out.println("/smile per inviare un'emoji");
        System.out.println("/like per inviare un pollicione in su");
        System.out.println("/echo per inviare lo stesso messaggio");
        System.out.println("/end per chiudere la connessione di chi la richiede \n\n");
    }
    public void Ricevi() throws IOException{
        System.out.println("Il " + nome + " ha detto: "+ in.readUTF());             //Metodo per la ricevuta dei messaggi
    }
    public Boolean Comunica() throws IOException{                       //Metodo per l'invio dei messaggio con controllo per la scrittura dei metodi
                inser = tastiera.readLine();
                if(inser.charAt(0) == '/'){
                   return true; 
                }
                else{                                               
                salva = inser;
                out.writeUTF(inser);
                out.flush();
                }
                return false;    
        }
    public void Autore(){               //Metodo per il cambio di autore - Da sistemare
        String stringa = null;
        try {
            System.out.println("Inserisci il nome utente");
            stringa=tastiera.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Gestore.class.getName()).log(Level.SEVERE, null, ex);
        }
        //nome = stringa;
    }
    public void CambiaStato(){          //Metodo per il cambio dello stato - Da sistemare
        if(stato==true){
            stato=false;
            System.out.println("Sei Offline");
        }
        else
        {
            stato=true;
            System.out.println("Sei Online");
        }
    }
    public void Smile(){            //Metodo per l'invio dello smile - Funzionante
        inser = ":smile:";
        try {
            out.writeUTF(inser);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Gestore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void Like(){             //Metodo per l'invio del Like - Funzionante
         inser = ":like:";
        try {
            out.writeUTF(":Like:");
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Gestore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void Echo(){             //Metodo per il reinvio della frase scritta precedentemente - Funzionante
        try {
            out.writeUTF(salva);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Gestore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Boolean End(){               //Metodo per la chiusura della connessione - Funzionante
        return false;  
    }
    public int scelta(){                //Metodo per controllare quale altro metodo viene scelto
        if(inser.equals("/help"))
            this.Menu();
        if(inser.equals("/autore"))
            this.Autore();
        if(inser.equals("/nonlinea"))
            this.CambiaStato();
        if(inser.equals("/inlinea"))
            this.CambiaStato();
        if(inser.equals("/smile"))
            this.Smile();
        if(inser.equals("/like"))
            this.Like();
        if(inser.equals("/echo"))
            this.Echo();
        if(inser.equals("/end"))
            return 1;
    return 0;
    }
}
