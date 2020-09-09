package org.playground;

public class MatchId {
    private String matchId;
    private String[] partsOfMatchId;

    public MatchId(String matchId) {
        this.matchId = matchId;
        this.partsOfMatchId = matchId.split(":");
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String[] getPartsOfMatchId() {
        return partsOfMatchId;
    }

    public void setPartsOfMatchId(String[] partsOfMatchId) {
        this.partsOfMatchId = partsOfMatchId;
    }
}
