package com.mallan.yujeongran.icebreaking.review.enums;

public enum GameType {

    LIAR_GAME("라이어 게임"),
    BALANCE_GAME("밸런스 게임"),
    OPEN_QUESTION_GAME("오픈 퀘스천 게임"),
    COIN_TRUTH_GAME("동전 진실 게임");

    private final String korean;

    GameType(String korean){
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }

}
