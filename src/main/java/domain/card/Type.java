package domain.card;

public enum Type {
    SPADE("스페이드"),
    DIAMOND("다이아몬드"),
    HEART("하트"),
    CLUB("클로버");

    private String korName;

    Type(String korName) {
        this.korName = korName;
    }

    public String getKorName() {
        return korName;
    }
}
