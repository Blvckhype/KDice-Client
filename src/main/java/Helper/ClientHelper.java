package Helper;

import Model.Client;
import Model.Field;
import Model.Game;
import Model.Point;

import java.io.IOException;
import java.util.*;

public class ClientHelper {

    private static final String ERROR = "ERROR\n";
    private static final String OK = "OK\n";

    public String validateLogin(String response, String nickname, Scanner in, Client client) throws IOException {
        if (response.equals(ERROR)) {
            do {
                System.out.print(response);
                nickname = in.nextLine();
                client.sendMessage(nickname + "\n");
                response = client.readMessage();
            } while (!response.equals(OK));
        }
        return response;
    }

    public String generateAttack(Client client, Game game) {
        String command = "ATAK ";
        int cubeDifference = 0;
        Field[][] myBoard = game.getBoard();
        //ArrayList<Point> myFields = getMyFields(client, game);
        ArrayList<Point> attackOptions = new ArrayList<>();
        ArrayList<Point> moveOptions = new ArrayList<>();
        for (Point point : getMyFields(client, game)) {
            if (point.getX() == 0 || point.getX() == 4) {
                if (point.getX() == 0) {
                    if (myBoard[point.getX() + 1][point.getY()].getOwner() != 0 &&
                            myBoard[point.getX() + 1][point.getY()].getOwner() != client.getId()) {
                        cubeDifference = myBoard[point.getX() + 1][point.getY()].getCubeCount() - myBoard[point.getX()][point.getY()].getCubeCount();
                        if (cubeDifference <= 0)
                            attackOptions.add(new Point(point.getX() + 1, point.getY(), cubeDifference, point));
                    } else if (myBoard[point.getX() + 1][point.getY()].getOwner() == 0 && myBoard[point.getX() + 1][point.getY()].getCubeCount() == 0)
                        moveOptions.add(new Point(point.getX() + 1, point.getY(), point));
                } else {
                    if (myBoard[point.getX() - 1][point.getY()].getOwner() != 0 &&
                            myBoard[point.getX() - 1][point.getY()].getOwner() != client.getId()) {
                        cubeDifference = myBoard[point.getX() - 1][point.getY()].getCubeCount() - myBoard[point.getX()][point.getY()].getCubeCount();
                        if (cubeDifference <= 0)
                            attackOptions.add(new Point(point.getX() - 1, point.getY(), cubeDifference, point));
                    } else if (myBoard[point.getX() - 1][point.getY()].getOwner() == 0 && myBoard[point.getX() - 1][point.getY()].getCubeCount() == 8)
                        moveOptions.add(new Point(point.getX() - 1, point.getY(), point));
                }
            }
            if (point.getY() >= 1 && point.getY() <= 3) {
                if (myBoard[point.getX()][point.getY() + 1].getOwner() != 0 &&
                        myBoard[point.getX()][point.getY() + 1].getOwner() != client.getId()) {
                    cubeDifference = myBoard[point.getX()][point.getY() + 1].getCubeCount() - myBoard[point.getX()][point.getY()].getCubeCount();
                    if (cubeDifference <= 0)
                        attackOptions.add(new Point(point.getX(), point.getY() + 1, cubeDifference, point));
                } else if (myBoard[point.getX()][point.getY() + 1].getOwner() == 0 && myBoard[point.getX()][point.getY() + 1].getCubeCount() == 8)
                    moveOptions.add(new Point(point.getX(), point.getY() + 1, point));
                if (myBoard[point.getX()][point.getY() - 1].getOwner() != 0 &&
                        myBoard[point.getX()][point.getY() - 1].getOwner() != client.getId()) {
                    cubeDifference = myBoard[point.getX()][point.getY() - 1].getCubeCount() - myBoard[point.getX()][point.getY()].getCubeCount();
                    if (cubeDifference <= 0)
                        attackOptions.add(new Point(point.getX(), point.getY() - 1, cubeDifference, point));
                } else if (myBoard[point.getX()][point.getY() - 1].getOwner() == 0 && myBoard[point.getX()][point.getY() - 1].getCubeCount() == 8)
                    moveOptions.add(new Point(point.getX(), point.getY() - 1, point));
            }
            if (point.getY() == 0 || point.getY() == 4) {
                if (point.getY() == 0) {
                    if (myBoard[point.getX()][point.getY() + 1].getOwner() != 0 &&
                            myBoard[point.getX()][point.getY() + 1].getOwner() != client.getId()) {
                        cubeDifference = myBoard[point.getX()][point.getY() + 1].getCubeCount() - myBoard[point.getX()][point.getY()].getCubeCount();
                        if (cubeDifference <= 0)
                            attackOptions.add(new Point(point.getX(), point.getY() + 1, cubeDifference, point));
                    } else if (myBoard[point.getX()][point.getY() + 1].getOwner() == 0 && myBoard[point.getX()][point.getY() + 1].getCubeCount() == 8)
                        moveOptions.add(new Point(point.getX(), point.getY() + 1, point));
                } else if (point.getY() == 4) {
                    if (myBoard[point.getX()][point.getY() - 1].getOwner() != 0 &&
                            myBoard[point.getX()][point.getY() - 1].getOwner() != client.getId()) {
                        cubeDifference = myBoard[point.getX()][point.getY() - 1].getCubeCount() - myBoard[point.getX()][point.getY()].getCubeCount();
                        if (cubeDifference <= 0)
                            attackOptions.add(new Point(point.getX(), point.getY() - 1, cubeDifference, point));
                    } else if (myBoard[point.getX()][point.getY() - 1].getOwner() == 0 && myBoard[point.getX()][point.getY() - 1].getCubeCount() == 8)
                        moveOptions.add(new Point(point.getX(), point.getY() - 1, point));
                }
            }
            if (point.getX() >= 1 && point.getX() <= 3) {
                if (myBoard[point.getX() - 1][point.getY()].getOwner() != 0 &&
                        myBoard[point.getX() - 1][point.getY()].getOwner() != client.getId()) {
                    cubeDifference = myBoard[point.getX() - 1][point.getY()].getCubeCount() - myBoard[point.getX()][point.getY()].getCubeCount();
                    if (cubeDifference <= 0) {
                        attackOptions.add(new Point(point.getX() - 1, point.getY(), cubeDifference, point));
                    }
                } else if (myBoard[point.getX() - 1][point.getY()].getOwner() == 0 && myBoard[point.getX() - 1][point.getY()].getCubeCount() == 8)
                    moveOptions.add(new Point(point.getX() - 1, point.getY(), point));
                if (myBoard[point.getX() + 1][point.getY()].getOwner() != 0 &&
                        myBoard[point.getX() + 1][point.getY()].getOwner() != client.getId()) {
                    cubeDifference = myBoard[point.getX() + 1][point.getY()].getCubeCount() - myBoard[point.getX()][point.getY()].getCubeCount();
                    if (cubeDifference <= 0) {
                        attackOptions.add(new Point(point.getX() + 1, point.getY(), cubeDifference, point));
                    }
                } else if (myBoard[point.getX() + 1][point.getY()].getOwner() == 0 && myBoard[point.getX() + 1][point.getY()].getCubeCount() == 0)
                    moveOptions.add(new Point(point.getX() + 1, point.getY(), point));
            }
        }

        Point point = getBestToAttack(attackOptions, moveOptions);
        if(point != null) {
            return command + point.getSourcePoint().getX() + " " + point.getSourcePoint().getY() + " " + point.getX() + " " + point.getY();
        } else {
            return "PASS";
        }
    }

    private Point getBestToAttack(ArrayList<Point> attacks, ArrayList<Point> moves) {
        int position = -1;
        int max = 0;
        int i = 0;
        for (Point point : attacks) {
            if (point.getCubes() >= 0 && point.getCubes() > max) {
                position = i;
                max = point.getCubes();
            }
            i++;
        }
        if (position != -1)
            return attacks.get(position);

        if (moves.size() >= 1)
            return moves.get(new Random().nextInt((moves.size()-1) + 1));
        else
            return null;
    }


    private ArrayList<Point> getMyFields(Client client, Game game) {
        ArrayList<Point> myFields = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (game.getBoard()[i][j].getOwner() == client.getId()) {
                    myFields.add(new Point(i, j, game.getBoard()[i][j].getCubeCount()));
                }
            }
        }
        return myFields;
    }
}


