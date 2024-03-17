package domain.user;

public class Winner extends Player {

    private String name;
    private double bettingMoney;

    public Winner(String name, double bettingMoney) {
        super(name, bettingMoney);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getBettingMoney() {
        return bettingMoney;
    }
}
