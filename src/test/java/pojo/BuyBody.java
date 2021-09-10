package pojo;

public class BuyBody {

    String symbol;
    int qty;

    public BuyBody(String s, int q) {
        this.symbol = s;
        this.qty = q;

    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
