package Models;

public class Transaction {
    public int id, user_id;
    public double amount;
    public String type;

    public Transaction(int id, int user_id, double amount, String type) {
        this.id = id;
        this.user_id = user_id;
        this.amount = amount;
        this.type = type;
    }
}
