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
        Gestore g;
        Boolean chiusura;
    public ServerChat(){
        port=2000;
        chiusura = true;
        
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
        while(chiusura == true){
            g.Ricevi();
            int scelta;
            Boolean appMenu = g.Comunica();
            if(appMenu==true){
                scelta = g.scelta();
                if(scelta == 1)
                    g.Menu();
                if(scelta == 2)
                    g.Autore();
                if(scelta == 3)
                    g.CambiaStato();
                if(scelta == 4)
                    g.CambiaStato();
                if(scelta == 5)
                    g.Smile();
                if(scelta == 6)
                    g.Like();
                if(scelta == 7)
                    g.Echo();
                if(scelta == 8)
                    chiusura = g.End();
            }
        }
    }
    public void Chiusura(){
        try {
                if (sSocket!=null) sSocket.close();
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
