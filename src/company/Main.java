package company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class Main {

       public static void main(String[] args) throws Exception {
        String clientSentence;
        String modifiedSentence;
        String ipAddress;
        int tcpPort = 4901;
        /*System.out.println("Introduzca el número de puerto TCP: ");
            BufferedReader selPort=new BufferedReader(new InputStreamReader(System.in));
            tcpPort=selPort.read();*/
           ipAddress = InetAddress.getLocalHost().getHostAddress();
           try {
               ServerSocket serverSocket = new ServerSocket(tcpPort);
                   while (true) {
                       System.out.println("----------------------------------------------------------------------------------------");
                       System.out.println("La dirección IP del servidor es: " + ipAddress);
                       System.out.println("Servidor TCP en línea (Puerto: " + tcpPort + ")");
                       Socket connectionSocket = serverSocket.accept();
                       BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                       DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                       clientSentence = inFromClient.readLine();
                       modifiedSentence = clientSentence.toUpperCase() + "\n";
                       System.out.println("Datagrama TCP Procesado: " + clientSentence);
                       outToClient.writeBytes(modifiedSentence);

                       Socket clientSocket = new Socket("127.0.0.1", 4900);

                       DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                       BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                       outToServer.writeBytes(clientSentence + "\r\n");
                       System.out.println("Mensaje enviado");

                       modifiedSentence = inFromServer.readLine();
                       System.out.println("Respuesta del Servidor: " + modifiedSentence);
                       clientSocket.close();
                       System.out.println("Desea continuar? [T/F]: ");
                       BufferedReader selection = new BufferedReader(new InputStreamReader(System.in));
                       String sel = selection.readLine().toUpperCase();
                        if(sel.equalsIgnoreCase("F")) {
                               break;
                           }
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
    }
}
