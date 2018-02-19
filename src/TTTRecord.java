public class TTTRecord {

    private String config;
    private int score, level;

    public TTTRecord(String config, int score, int level) {
        this.config = config;
        this.score = score;
        this.level = level;
    }

    public String getConfiguration() {
        return config;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

}
