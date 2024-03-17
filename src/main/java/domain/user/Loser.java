package domain.user;

public class Loser extends Player {

    private String name;
    private double bettingMoney;

    public Loser(String name, double bettingMoney) {
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
