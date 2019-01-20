package Helper;

public class ResponseParser {

    public static int[] parseBoardInfo(String command) {
        command = command.substring(command.indexOf(" ") + 1);
        String[] boardInfo = command.split(" ");
        int[] board = new int[boardInfo.length];
        for (int i = 0 ; i < boardInfo.length ; i++)
            board[i] = Integer.parseInt(boardInfo[i]);
        return board;
    }

    public static int[] getAttackInfo(String command) {
        command = command.substring(command.indexOf(" ") + 1);
        String[] values = command.split(" ");
        int[] atta = new int[values.length];
        for (int i = 0 ; i < values.length ; i++) {
            atta[i] = Integer.parseInt(values[i]) - 1;
        }
        return atta;
    }

    public static int getWinner (String command) {
        command = command.substring(command.indexOf(" ") + 1);
        String[] values = command.split(" ");
        return Integer.parseInt(values[values.length - 1]);
    }
}
