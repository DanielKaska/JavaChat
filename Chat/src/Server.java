import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Server {
    public static void main(String[] args) throws IOException{
        try{
            ServerSocket server = new ServerSocket(8080);
            List<Socket> clients = new ArrayList<Socket>();
            Map<Socket, String> map = new HashMap<Socket, String>();

            while(true){
                Thread t = new Thread(() -> {
                    try{
                        Socket client = server.accept();

                        var in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                        var nick = in.readLine();
                        System.out.println(nick + " dołączył do czatu");
                        map.put(client, nick);
                        clients.add(client);

                        String input;

                        while((input = in.readLine()) != null){

                            for(var c : clients){
                                var out = new PrintWriter(c.getOutputStream(), true);

                                out.println(map.get(client) + ": " + input);
                            }

                        }
                    }catch(Exception ex){
                        System.out.println(ex.getMessage());
                    }

                });

                t.start();
            }


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
