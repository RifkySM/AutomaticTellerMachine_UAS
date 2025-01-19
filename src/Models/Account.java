package Models;

import java.text.NumberFormat;
import java.util.Locale;

public class Account {
    public int id, user_id;
    public double balance;
    public String accountNumber;

    public Account(int id, int user_id, double balance, String accountNumber) {
        this.id = id;
        this.user_id = user_id;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public String getBalance() {
        NumberFormat currencyFormater = NumberFormat.getInstance(new Locale("id", "ID"));
        return "Rp." + currencyFormater.format(balance);
    }
}
