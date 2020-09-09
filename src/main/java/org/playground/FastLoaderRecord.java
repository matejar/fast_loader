package org.playground;

public class FastLoaderRecord {
    private MatchId matchId;
    private Integer marketId;
    private String outcomeId;
    private String specifiers;

    public FastLoaderRecord(MatchId matchId, Integer marketId, String outcomeId, String specifiers) {
        this.matchId = matchId;
        this.marketId = marketId;
        this.outcomeId = outcomeId;
        this.specifiers = specifiers;
    }

    public MatchId getMatchId() {
        return matchId;
    }

    public void setMatchId(MatchId matchId) {
        this.matchId = matchId;
    }

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    public String getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(String outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getSpecifiers() {
        return specifiers;
    }

    public void setSpecifiers(String specifiers) {
        this.specifiers = specifiers;
    }
}
