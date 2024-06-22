import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nick: ");
        var nick = scanner.nextLine();
        out.println(nick);

        Thread t = new Thread(() -> {
            while(true){
                try{
                    var response = in.readLine();
                    if(!response.equals("")){
                        System.out.println(response);
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }

            }
        });

        t.start();

        Thread tr = new Thread(() -> {
            while(true){
                var message = scanner.nextLine();
                out.println(message);
            }
        });

        tr.start();

    }
}