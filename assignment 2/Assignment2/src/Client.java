import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.net.InetSocketAddress;

public class Client {


    public static int[]  generateArray(int len,int max){
        int[] arr=new int[len];
        for(int i=0;i<arr.length;i++){
            arr[i]=(int)(Math.random()*max);
        }
        return arr;
    }

    public static void main(String[] args) throws IOException {


        if (args.length != 2){
            System.err.println("Usage: java HelloClient <hostname> <port numebr>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);



        try (
                Socket myClientSocket = new Socket(hostName,portNumber);
                PrintWriter output = new PrintWriter(myClientSocket.getOutputStream(),true);
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); //from user
                BufferedReader input = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
        ) {

            myClientSocket.setSoTimeout(5000);

            int len = 10;
            int max = 10;
            int[] arr = generateArray(len, max);

            String sequence;
            System.out.println("------------------------------------------------------------");
            System.out.println("|Input a sequence of natural numbers which divided by space.|");
            System.out.println("------------------------------------------------------------");
            sequence = stdIn.readLine();

//             sequence = Arrays.toString(arr);
//             sequence = sequence.substring(1, sequence.length()-1);
//            sequence = sequence.replace(",", " ");

            System.out.println("Client says: It is the sequence(String) I input to Server: " + sequence);
            output.println(sequence);


            String operation;
            System.out.println("---------------------------");
            System.out.println("|Input operation you want.|");
            System.out.println("|          1. 2^x         |");
            System.out.println("|          2. x^2         |");
            System.out.println("|          3. sqrt(x)     |");
            System.out.println("|          4. log(x)      |");
            System.out.println("---------------------------");
            operation = stdIn.readLine();
            System.out.println("Client says: I input operation = " + operation + " to the server");
            output.println(operation);


            String mode;
            System.out.println("--------------------------------------------------------");
            System.out.println("|                Choose a mode you want.                |");
            System.out.println("| 1.uniformly dividing the sequence S across the threads|");
            System.out.println("|    2.one thread for each one of these numbers         | \n |and 1 thread for all the remaining numbers|");
            System.out.println("--------------------------------------------------------");
            mode = stdIn.readLine();
            output.println(mode);
            System.out.println("Client says: I input mode = " + mode + " to the Server");


            Thread.sleep(3000);
                System.out.println("Client says: I received operated sequence and operation time from Server");
                String gg = input.readLine();
                System.out.println(gg);




        }catch (UnknownHostException e){
            System.err.println("Don't know about the host " + hostName);
            System.exit(1);
        }catch (IOException e){
            System.err.println("Couldn't get I/O for the conncetion to " + hostName);
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
