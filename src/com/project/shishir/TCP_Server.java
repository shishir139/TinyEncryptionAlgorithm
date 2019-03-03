package com.project.shishir;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by shish on 4/8/2017.
 */


public class TCP_Server {



    public  static Socket socket;
    BufferedReader buffer;

    public static void main (String args[]) {

        TEATest obj = new TEATest();

        ServerSocket listenSocket=null;

        try{ //port number
            int serverPort = 10064;
            listenSocket = new ServerSocket(serverPort);

            // listenSocket = new ServerSocket(serverPort);


            System.out.println("Server up and waiting for clients");
            Socket clientSocket = listenSocket.accept();

            System.out.println("Connected!");

            Connection c = new Connection(clientSocket);

            while(true)

            {     //Reading the message from the client
                socket = listenSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                //buffer = new BufferedReader(isr);



            }


        } catch(IOException e) {System.out.println("Listen :"+e.getMessage());}
        finally{try {
            listenSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }}
    }
}

class UserValidation {

    File myFile=new File("E:/AOS_Project_3/UserDetails.txt");

    String key_client;
    String userName_Validation(String un)
    {
        //Method to find the username
        String username=un;
        String[] list;
        String line;

        try{
            File myFile=new File("E:/AOS_Project_3/UserDetails.txt");
            FileReader fileReader= new FileReader(myFile);
            BufferedReader reader=new BufferedReader(fileReader);

            while((line= reader.readLine())!=null)
            {
                list=line.split(" ");
                if(list[0].trim().equals(username.trim()))
                {
                    key_client=list[1];
                }

            }
        }
        catch(Exception e)
        {
            System.out.println("Reading File:"+e.getMessage());
        }
        return key_client;
        //If the user name is found the corresponding key is returned

    }
    String Validation(String un,String id)
    {//Method to validate the username and StudentID pair
        String username=un,sid =id,match="null";
        try
        {

            File myFile=new File("E:/AOS_Project_3/UserDetails.txt");
            FileReader fileReader= new FileReader(myFile);
            BufferedReader reader=new BufferedReader(fileReader);
            String[] list;
            String line;
            while((line=reader.readLine())!=null)
            {

                list=line.split(" ");

                if((list[0].trim().equals(username.trim()))&&(list[1].trim().equals(id.trim())))
                {

                    match="ok";
                    break;
                }
            }
        }catch(Exception e)
        {
            System.out.println("Exception");
        }

        return match;
    }
}



class Connection extends Thread {
    BufferedReader infromClient;
    DataOutputStream outToClient;
    Socket clientSocket;

    public Connection (Socket aClientSocket)
    {
        try {
            clientSocket = aClientSocket;
            infromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToClient =new DataOutputStream( clientSocket.getOutputStream());
            this.start();
        } catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
    }

    public void run()
    {
        try { // an echo server

            TEATest obj = new TEATest();
            System.out.println("username is received ");
            System.out.println("Encrypted text received is");


            System.out.println(infromClient.readLine());
            System.out.println("Decryption now");
            String user = infromClient.readLine();
            System.out.println(user);
            System.out.println("Password received");
            String pwd = infromClient.readLine();
            System.out.println(pwd);
            System.out.println("Password after decryption ");
            String decrypt = infromClient.readLine();
            System.out.println(decrypt);

            UserValidation u = new UserValidation();
            String result = u.Validation(user,decrypt);
            System.out.println(result);
//            Generate_News gn = new Generate_News();
            String news = null;
            if(result =="ok") {
                    String username=user;
                    String[] list;
                    String line;

                    File myFile=new File("E:/AOS_Project_3/UserDetails.txt");
                    FileReader fileReader= new FileReader(myFile);
                    BufferedReader reader=new BufferedReader(fileReader);

                    while((line= reader.readLine())!=null)
                    {
                        list=line.split(" ");
                        if(list[0].trim().equals(username.trim()))
                        {
                            news=list[2];
                        }

                    }
                System.out.println("News" +news);
                }


        } catch(EOFException e) {System.out.println("EOF:"+e.getMessage());
        } catch(IOException e) {System.out.println("IO:"+e.getMessage());
        } finally { try {clientSocket.close();}catch (IOException e){/*close failed*/}}
    }
}
