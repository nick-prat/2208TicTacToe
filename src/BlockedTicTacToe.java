public class BlockedTicTacToe {

    private char gameBoard[][];
    private int inline, maxLevels;

    public BlockedTicTacToe(int boardSize, int inline, int maxLevels) {
        gameBoard = new char[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                gameBoard[i][j] = ' ';
            }
        }
        this.inline = inline;
        this.maxLevels = maxLevels;
    }

    public TTTDictionary createDictionary() {
        return new TTTDictionary(5987);
    }

    public int repeatedConfig(TTTDictionary configurations) {
        TTTRecord record = configurations.get(digestGameBoard());
        if(record == null) {
            return -1;
        } else {
            return record.getScore();
        }
    }

    public void insertConfig(TTTDictionary configurations, int score, int level) {
        try {
            configurations.put(new TTTRecord(digestGameBoard(), score, level));
        } catch (DuplicatedKeyException e) {
            e.printStackTrace();
        }
    }

    public void storePlay(int row, int col, char symbol) {
        gameBoard[row][col] = symbol;
    }

    public boolean squareIsEmpty(int row, int col) {
        return gameBoard[row][col] == ' ';
    }

    public boolean wins(char symbol) {
        for(int i = 0; i < gameBoard.length; i++) {
            for(int j = 0; j < gameBoard[i].length; j++) {
                if(gameBoard[i][j] != symbol)
                    continue;
                if(scanWinOnSlot(symbol, i ,j))
                    return true;
            }
        }
        return false;
    }

    public boolean isDraw() {
        return !wins('x') && !wins('o') && !digestGameBoard().contains(" ");
    }

    public int evalBoard() {
        if(wins('x')) return 3;
        if(wins('o')) return 0;
        if(!digestGameBoard().contains(" ")) return 1;
        return 2;
    }

    private String digestGameBoard() {
        StringBuilder config = new StringBuilder();
        for(char[] gameBoardRow : gameBoard) {
            for(char slot : gameBoardRow) {
                config.append(slot);
            }
        }
        return config.toString();
    }

    private boolean gameBoardCompareSafe(char symbol, int row, int col) {
        return row >= gameBoard.length && row < gameBoard.length && col >= gameBoard[0].length && col < gameBoard[0].length && gameBoard[row][col] == symbol;
    }

    private boolean scanWinDir(char symbol, int row, int col, int rowd, int cold) {
        int backDist = 0, forwardDist = 0;
        boolean backCont = true, forwardCont = true;
        for(int i = 1; i < inline; i++) {
            if(backCont && gameBoardCompareSafe(symbol, row - rowd * i, col - cold * i))
                backDist++;
            else
                backCont = false;

            if(forwardCont && gameBoardCompareSafe(symbol, row + rowd * i, col + cold * i))
                forwardDist++;
            else
                forwardCont = false;

            if(1 + backDist + forwardDist == inline)
                return true;
        }
        return false;
    }

    private boolean scanWinOnSlot(char symbol, int row, int col) {
        for(int i = -1; i <= 1; i++) {
            if(row + i < 0 || row + i >= gameBoard.length)
                continue;
            for(int j = -1; j <= 1; j++) {
                if(j + col < 0 || j + col >= gameBoard[row].length)
                    continue;
                if(scanWinDir(symbol, row, col, i, j))
                    return true;
            }
        }
        return false;
    }

}
