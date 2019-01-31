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
        String replaceString = command.replaceAll("\\s","");
        replaceString = replaceString.replaceAll("[^0-9.]", "");
        int[] atta = new int[replaceString.length()];
        for (int i = 0; i < replaceString.length(); i++)
            atta[i] = Character.getNumericValue(replaceString.charAt(i)) - 1;
        return atta;
    }

    public static int getWinner (String command) {
        String replaceString = command.replaceAll("\\s","");
        replaceString = replaceString.replaceAll("[^0-9.]", "");
        return Character.getNumericValue(replaceString.charAt(replaceString.length() - 1));
    }
}
