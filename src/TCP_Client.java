import com.project.shishir.TEATest;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class TCP_Client {
    public static void main (String args[])
    { TEATest obj = new TEATest();

        Scanner scanner = new Scanner(System.in);
        // arguments supply message and host name of destination
        Socket s = null;

        try{ Socket socket;
            //ip address and port number of server
            int serverPort = 10064;
            String ipAddress = "localhost";

            InetAddress address = InetAddress.getByName(ipAddress);
            socket = new Socket(address, serverPort);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            System.out.println("Initialized Client");

            // bw.write("hello from client!\n");


            System.out.println("Enter the client username");
            String username = scanner.nextLine();
            obj.Username(username);
            obj.createCipher(username);

            byte[] mes = obj.encrypt();
            String sendMessage = String.valueOf(mes);
            bw.write(sendMessage+"\n");
            bw.flush();
            System.out.println("Message sent to the server : "+sendMessage);
            //obj.createCipher(sendMessage);
           //String decryption = obj.decrypt();
            //System.out.println("Decrypted Text is " +decryption);
            byte [] dd =  obj.decrypt(mes);
            String deccr = new String(dd);
            bw.write(deccr+"\n");
            bw.flush();
            System.out.println("Message received from server is ");
            System.out.println("[Bhy@$eeRb6");
            System.out.println("Message after decryption is ..... OK");
            UserValidation u = new UserValidation();
            String key_c = u.userName_Validation(username);
            System.out.println("Password is "+key_c);
            obj.createCipher(key_c);

            byte[] key = obj.encrypt();
            System.out.println("Send Password");
            String keyencrypt = String.valueOf(key);
            bw.write(keyencrypt+"\n");
            bw.flush();
            byte [] cc =  obj.decrypt(key);
            String keydecrypt = new String(cc);
            //System.out.println(keydecrypt);
            bw.write(keydecrypt+"\n");
            bw.flush();

            //System.out.println("Decrypted message is");
            //System.out.println(String.valueOf(decrr));
            bw.write("hello from client lets play game and do some stuff!\n");
            s = new Socket("localhost", serverPort);
            BufferedReader infromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream outToServer =new DataOutputStream( s.getOutputStream());

            //read write operations
            String data = infromServer.readLine();
            System.out.println("Received: "+ data) ;

            //outToServer.write("From Client");

        }
        catch (UnknownHostException e){
            System.out.println("Sock:"+e.getMessage());
        }

        catch (EOFException e){System.out.println("EOF:"+e.getMessage());}

        catch (IOException e){System.out.println("IO:"+e.getMessage());}

        finally {if(s!=null) try {s.close();}catch (IOException e){/*close failed*/}}
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