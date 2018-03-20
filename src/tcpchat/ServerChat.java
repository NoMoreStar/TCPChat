/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Manuel Antonini
 */
public class ServerChat {
        int port;
        ServerSocket sSocket;
        Socket connection;
        DataInputStream in;
        DataOutputStream out;
        Gestore g;                  //Classe gestore per la creazione di tutti i metodi
        Boolean chiusura;           //Variabile per la gestione della chiusura degli stream
    public ServerChat(){
        port=2000;                  //Porta utilizzata per la comunicazione
        chiusura = true;            //Server online = true / server offline = false
        
    }
    
    public void Accendi() throws IOException{
         sSocket = new ServerSocket(port);
        System.out.println("In attesa di connessioni!");
    }
    public void Connetti() throws IOException{
        connection = sSocket.accept();
        System.out.println("Connessione stabilita!");
        g = new Gestore(connection,"client");
        g.Menu();
    }
    public void Comunica() throws IOException{
    try{
        while(chiusura == true){
            g.Ricevi();
            int scelta;
            Boolean appMenu = g.Comunica();             //Variabile booleana per controllare se viene scritto un metodo
            if(appMenu == true){
                scelta = g.scelta();
                if(scelta == 1)
                    chiusura = g.End();
            }
        }
    }catch(IOException e){
        System.out.println("Chisura");              //Chiusura del server se la comunicazione viene interrotta
        sSocket.close();
    }
    }
    public void Chiusura(){
        try {
                if (sSocket!=null) 
                    sSocket.close();
            } catch (IOException ex) {
                System.err.println("Errore nella chiusura della connessione!");
            }
            System.out.println("Connessione chiusa!");
    }
    public static void main(String[] args) throws IOException {
        ServerChat server = new ServerChat();
        server.Accendi();
        server.Connetti();
        server.Comunica();
        server.Chiusura();
}
    
}
