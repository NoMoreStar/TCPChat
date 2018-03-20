/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Manuel Antonini
 */
public class ClientChat {
        Socket connection;
        String serverAddress;
        int port;
        DataOutputStream out;
        DataInputStream in;
        Gestore g;                      //Classe gestore per la creazione di tutti i metodi
        Boolean chiusura;               //Variabile per la gestione della chiusura degli stream
public ClientChat(){
        serverAddress = "localhost";
        port = 2000;                    //Porta utilizzata per la comunicazione 
        out = null;                     
        in = null;
        chiusura = true;                //Client online = true / client offline = false
}

public void Connetti() throws IOException{
        connection = new Socket(serverAddress, port);                   //Richiesta della connessione
        g = new Gestore(connection,"server");                           //Condivisione del socket connection alla classe Gestore
        System.out.println("Connessione aperta"); 
        g.Menu();                                                       //Prima apertura del menù
}
public void Invio() throws IOException{
    try{
        int scelta;
        while(chiusura == true){
        Boolean appMenu = g.Comunica();         //Variabile booleana per controllare se viene scritto un metodo
        if(appMenu == true){
            scelta = g.scelta();
            if(scelta == 1)
                chiusura = g.End();
        }
        else
            g.Ricevi();
        }
    }catch(IOException e){
        System.out.println("Chisura");
        connection.close();                       //Chiusura del client se la comunicazione viene interrotta
    }
    }
    public void Chiusura(){
            try {
                if (connection!=null)
                    {
                        connection.close();
                        System.out.println("Connessione chiusa!");        //Chiusura della connessione
                    }
                }
                catch(IOException e){
                    System.err.println("Errore nella chiusura della connessione!");
                }
        }
public static void main(String[] args) throws IOException {
        ClientChat client = new ClientChat();
        client.Connetti();
        client.Invio();
        client.Chiusura();
}
}
