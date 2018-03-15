/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        connection = s;
        this.nome=nome;
        inser = "";
        salva = inser;
        try {
            in = new DataInputStream(connection.getInputStream());   
            out = new DataOutputStream(connection.getOutputStream());
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
        System.out.println("Il " + nome + " ha detto: "+ in.readUTF());
    }
    public Boolean Comunica() throws IOException{
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
    public void Autore(){
        String stringa = null;
        try {
            System.out.println("Inserisci il nome utente");
            stringa=tastiera.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Gestore.class.getName()).log(Level.SEVERE, null, ex);
        }
        //nome = stringa;
    }
    public void CambiaStato(){
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
    public void Smile(){
        inser = ":smile:";
        try {
            out.writeUTF(inser);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Gestore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void Like(){
         inser = ":like:";
        try {
            out.writeUTF(":Like:");
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Gestore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void Echo(){
        try {
            out.writeUTF(salva);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Gestore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Boolean End(){
        return false;  
    }
    public int scelta(){
        if(inser.equals("/help"))
            return 1;
        if(inser.equals("/autore"))
            return 2;
        if(inser.equals("/nonlinea"))
            return 3;
        if(inser.equals("/inlinea"))
            return 4;
        if(inser.equals("/smile"))
            return 5;
        if(inser.equals("/like"))
            return 6;
        if(inser.equals("/echo"))
            return 7;
        if(inser.equals("/end"))
            return 8;
    return 0;
    }
}
