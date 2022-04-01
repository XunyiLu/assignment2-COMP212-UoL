import java.net.*;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Arrays;

public class Server {


    public static int[] strToInt(String s) {
        String[] strings = new String[s.length()]; //strings数组：用于记录数字
        int i, k = 0, count = 0;
        for (i = 0; i < strings.length; i++) { //如果是数字则将其记录到strings数组中
            if (s.charAt(i) != ' ') {
                if (strings[k] == null) //如果是第一个计数的数字则直接赋值
                    strings[k] = String.valueOf(s.charAt(i));
                else //否则直接添加到其后面
                    strings[k] = strings[k] + s.charAt(i);
            } else { //如果是第一次出现的空格，则k+1，这一次空格(可能有多个连续空格)后面出现的数字存储到strings的下一个元素中
                if (s.charAt(i - 1) != ' ')
                    k++;
            }
        }
        for (i = 0; i < strings.length; i++) {
            if (strings[i] != null)
                count++;
        }
        int[] a = new int[count];
        for (i = 0; i < count; i++) {
            a[i] = Integer.parseInt(strings[i]);
        }
        return a;
    }

    public static void main(String[] args) throws IOException {

        int portNumber;

        if (args.length < 1) {
            System.out.println("Warming: You have provided no arguments\nTrying to connect to the default port.");
            portNumber = 8000;
        } else if (args.length == 1) {
            portNumber = Integer.parseInt(args[0]);
        } else {
            System.out.println("Warning: You have provided > 1 arguments\nTrying with the first argument");
            portNumber = Integer.parseInt(args[0]);
        }


        while (true) {
            try (
                    ServerSocket myServerSocket = new ServerSocket(portNumber);
                    Socket aClientSocket = myServerSocket.accept();
                    PrintWriter output = new PrintWriter(aClientSocket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(aClientSocket.getInputStream()));
            ) {

                System.out.println("Server says: Connect successfully");
                long startTime = System.currentTimeMillis();
                System.out.println("Server Start time is " + startTime);
//                String inputLine;
                int[] S = null;
                String inputLine = input.readLine();
                System.out.println("Server says: I received client's input(String): " + inputLine);
                S = strToInt(inputLine);
                System.out.println("Server says: I turn the String into int array: ");
                for(int i = 0; i < S.length; i++){
                    System.out.print(S[i] + " ");
                }
                System.out.println("");
//                System.out.println("This is the original input sequence " + inputLine);
                String op = input.readLine();
                int operation = Integer.parseInt(op);
                System.out.println("Server says: I received client's operation " + operation);


//                System.out.println("This is the operation " + op );
                String mo = input.readLine();
                int mode = Integer.parseInt(mo);
                System.out.println("Server says: I received client's mode " + mode);


                //接下来要多线程操作这个S
                System.out.println("Server says: Now i start the method DataGathering. ");
                DataGathering d = new DataGathering(mode, operation, S);

                int[] result;
                result = d.getOperatedS();
                System.out.println("Server says: I received operated sequence(int) from DataGathering and it is ");
                for(int i = 0; i < result.length; i++){
                    System.out.print(result[i] + " ");
                }
                System.out.println("");

                String finalResult = Arrays.toString(result);
                System.out.println("Server says: now I have transfer operated sequence from int to string");
                System.out.println(finalResult);


                long endTime = System.currentTimeMillis();
                System.out.println("Server says: End time is " + endTime);
                long exeTime = Math.abs(endTime - startTime);
//                System.out.println("exetime is " + exeTime);
                String finalTime = String.valueOf(exeTime);
                System.out.println("Server says: End time is " + finalTime);
//                System.out.println("Attention!!!!!!!! final time " + finalTime + "milis");
//                System.out.println("final result is " + finalResult + "  final time is " + finalTime);
                output.println("Final result is " + finalResult + " Final time is " + finalTime + "millis");
                System.out.println("Server says: I have sent the operated sequence and time to Client.");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InterruptedIOException e) {
                e.getStackTrace();
            }
        }
    }
}
