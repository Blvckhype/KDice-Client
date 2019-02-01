package com.example;

import Helper.GameHelper;
import Helper.ResponseParser;
import Model.Client;
import Model.Game;

import java.net.Socket;
import java.net.SocketException;

public class App {

    public static void main(String[] args) {
        String response = "";
        try {
            Socket socket = new Socket("10.100.6.80", 9999);
            Client client = new Client(socket);
            System.out.println(client.readMessage());
            client.sendMessage("LOGIN s434739\n");
            System.out.println(client.readMessage()); //OK
            response = client.readMessage();
            System.out.println(response);
            client.setId(Long.parseLong(response.substring((response.indexOf(" ") + 1), response.lastIndexOf(" "))));
            Game game = new Game();
            GameHelper gameHelper = new GameHelper();
            gameHelper.updateBoard(client, game);
            String message;

            //noinspection InfiniteLoopStatement
            while (true) {

                message = "";
                message = client.readMessage(); // ODEBRANIE GLOWNEGO KOMUNIKATU

                int[] attackInfo;
                String attackResult = "", attack = "";
                if (message.startsWith("TWOJ")) {
                    do {
                        attack = gameHelper.generateAttack(client, game);
                        client.sendMessage(attack); //WYSYLAM ATAK
                        if (attack.startsWith("ATAK")) {
                            System.out.print("MOJ ATAK TO: " + client.readMessage());   //WYSWIETLAM WYGENEROWANY ATAK
                            System.out.println("ODPOWIEDZ PO: " + client.readMessage()); // OK LUB ERROR
                            String attackResponse = client.readMessage(); //ODBIERAM WYNIK ATAKU
                            System.out.println("WYNIK MOJEGO ATAKU: " + attackResponse); // WYSWIETLAM WYNIK ATAKU
                            attackInfo = ResponseParser.getAttackInfo(attack);
                            int winner = ResponseParser.getWinner(attackResponse);
                            gameHelper.resolveAttack(game, attackInfo, winner);
                        } else {
                            System.out.print("MOJ PASS: " + attack);
                            System.out.println("ODPOWIEDZ PO PASS: " + client.readMessage());
                        }
                    } while (!attack.startsWith("PAS")); //ATAKUJ POKI NIE BEDZIE PASS
                    System.out.println("ZAKONCZYLEM RUCH");
                }
                if (message.startsWith("ATAK")) {
                    System.out.println("KTOS WYKONAL: " + message);
                    attackInfo = ResponseParser.getAttackInfo(message);
                    attackResult = client.readMessage(); // ODEBRANIE WYNIKU ATAKU NIE W SWOIM RUCHU
                    System.out.println("WYNIK CZYJEGOS ATAKU: " + attackResult);
                    int winner = ResponseParser.getWinner(attackResult);
                    gameHelper.resolveAttack(game, attackInfo, winner);
                }
                if (message.startsWith("KONIEC")) {
                    System.out.println(message); // WYSWIETLENIE KOMUNIKATU KONIEC RUNDY
                    gameHelper.updateBoard(client, game);   // ODEBRANIE KOMUNIKATU PLANSZA I ZAKUTALIZOWANIE PLANSZY
                }
                if (message.startsWith("TUR")) {
                    System.out.println(message); // WYSWIETLENIE KOMUNIKATU KONCA TURY
                    if (!message.substring(message.indexOf(" ") + 1, message.lastIndexOf(" ")).equals("10")) {
                        game = new Game();  // WYLOSOWANIE NOWEJ PLANSZY
                        gameHelper.updateBoard(client, game);  // ODEBRANIE I ZAKTUALIZOWANIE PLANSZY
                    }
                }
                if (message.startsWith("WYNI")) {
                    System.out.println(message); //ODERBANIE KOMUNIKATU KONCA
                    client.getSocket().close();
                    break;
                }
            }
        } catch (SocketException sockEx) {
            System.out.println();
        } catch (Exception ex) {
            System.out.println("Client Error : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
