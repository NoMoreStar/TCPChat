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
        Gestore g;
        Boolean chiusura;
public ClientChat(){
        serverAddress = "localhost";
        port = 2000;
        out = null;
        in = null;
        chiusura = true;
}

public void Connetti() throws IOException{
        connection = new Socket(serverAddress, port);
        g = new Gestore(connection,"server");
        System.out.println("Connessione aperta"); 
        g.Menu();
}
public void Invio() throws IOException{
        int scelta;
        while(chiusura == true){
        Boolean appMenu = g.Comunica();
        if(appMenu == true){
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
        else
            g.Ricevi();
        }
    }
    public void Chiusura(){
            try {
                if (connection!=null)
                    {
                        connection.close();
                        System.out.println("Connessione chiusa!");
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
