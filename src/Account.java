public class Account {
    private String accountNo;
    private String holderName;
    private double balance;

    public Account(String accountNo, String holderName, double balance) {
        this.accountNo = accountNo;
        this.holderName = holderName;
        this.balance = balance;
    }

    public String getAccountNo() { return accountNo; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
}