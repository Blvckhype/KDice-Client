package com.example;

import Helper.GameHelper;
import Helper.ResponseParser;
import Model.Client;
import Model.Game;

import java.net.Socket;
import java.util.Random;

public class App {

    private static Socket socket;
    private static Client client;
    private static Game game;
    private static final String ERROR = "ERROR\n";
    private static final String OK = "OK\n";

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 8888);
            client = new Client(socket);
            System.out.print(client.readMessage()); //POLACZONO
            client.sendMessage(generateNickname());
            String response = client.readMessage();
//            response = client.getClientHelper().validateLogin(response, nickname, in, client);
            System.out.print(response);
            response = client.readMessage();
            System.out.print(response);
            client.setId(Long.parseLong(response.substring((response.indexOf(" ") + 1), response.lastIndexOf(" "))));
            game = new Game();
            GameHelper gameHelper = new GameHelper();
            gameHelper.updateBoard(client, game);

            //noinspection InfiniteLoopStatement
            while ( true ) {

                String message = client.readMessage();
                System.out.print(message);
                int[] attackInfo;
                String attackResult = "", attack = "";
                if (message.equals("TWOJ RUCH\n")) {
                    do {
                        attack = gameHelper.generateAttack(client, game);
                        client.sendMessage(attack);
                        System.out.print(attack);
                        if (!attack.equals("PASS\n"))
                            client.readMessage(); // ATTACK INFO
                        String serverResponse = client.readMessage();
                        System.out.println(serverResponse); // OK LUB ERROR
                        if (serverResponse.equals(ERROR))
                            break;
                        if (!attack.equals("PASS\n")) {
                            String attackResponse = client.readMessage();
                            System.out.print("WYNIK1 : " + attackResponse); // WYNIK
                            attackInfo = ResponseParser.getAttackInfo(attack);
                            gameHelper.resolveAttack(game, attackInfo, ResponseParser.getWinner(attackResult));
                        }
                    } while(!attack.equals("PASS\n"));

                }
                if (message.startsWith("ATAK")) {
                    attack = client.readMessage(); //ATTACK Kiedy nie atakujesz
                    attackInfo = ResponseParser.getAttackInfo(attack);
                    attackResult = client.readMessage(); // WYNIK kiedy nie atakujesz
                    System.out.print("ARARARA: " + attackResult);
                    gameHelper.resolveAttack(game, attackInfo, ResponseParser.getWinner(attackResult)); // SPRAWDZIC
                }
                if (message.equals("KONIEC RUNDY\n")) {
                    gameHelper.updateBoard(client, game);
                }
                if (message.startsWith("TURA")) {
                    game = new Game();
                    gameHelper.updateBoard(client, game);
                }
//                if (message.startsWith("WYNIK")) {
//                    System.out.println(message);
//                    break;
//                }
            }

        } catch (Exception ex) {
            System.out.println("Client Error : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String generateNickname() {
        return "LOGIN " + "Tomek-" + new Random().nextInt(15) + "\n";
    }

}
