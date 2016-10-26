package com.fibelatti.accedomemory.models;

public class HighScore {
    private Long id;
    private String playerName;
    private Integer playerScore;

    public HighScore() {
        this(null, null, null);
    }

    public HighScore(String playerName, Integer playerScore) {
        this(null, playerName, playerScore);
    }

    public HighScore(Long id, String playerName, Integer playerScore) {
        this.id = id;
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getPlayerScore() {
        return playerScore;
    }

    public String getPlayerScoreString() {
        return playerScore.toString();
    }

    public void setPlayerScore(Integer playerScore) {
        this.playerScore = playerScore;
    }
}
