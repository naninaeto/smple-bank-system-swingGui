public class Transaction {
    private String accountNo;
    private String type;
    private double amount;

    public Transaction(String accountNo, String type, double amount) {
        this.accountNo = accountNo;
        this.type = type;
        this.amount = amount;
    }
}