package org.practice.lld.mock.atm_system;

public class Main {
    public static void main(String[] args) {

    }
}

class User{
    int id;
    String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Card{
    int id;
    String cardNo;
    BankAccount account;

    public Card(int id, String cardNo, BankAccount account) {
        this.id = id;
        this.cardNo = cardNo;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public BankAccount getAccount() {
        return account;
    }
}

class BankAccount{
    int id;
    User user;
    long balanceAmount;
    int pin;

    public BankAccount(int id, User user, long balanceAmount, int pin) {
        this.id = id;
        this.user = user;
        this.balanceAmount = balanceAmount;
        this.pin = pin;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public long getBalanceAmount() {
        return balanceAmount;
    }

    public int getPin() {
        return pin;
    }

    public void setBalanceAmount(long balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}

class BankService{

}

enum AtmState{
    IDLE_STATE, HAS_CARD , ENTER_PIN, ENTER_AMOUNT, PERFORM_TRANSACTION
}

/**
 * IDLE_STATE => Insert Card, CardLess Transaction
 * HAS_CARD => selectTransaction(),
 * ENTER_PIN => acceptPin();
 * ENTER_AMOUNT (conditional) => acceptAmount();
 * PERFORM_TRANSACTION => (removeCard());
 *
 * {
 *     BankAccount;
 *
 * }
 *
 * */

enum TransactionType{
    CASH_WITHDRAW, BALANCE_ENQUIRY;
}

//enum AccountType{
//    SAVING, CURRENT;
//}

class Atm{
    Context context;
    AtmStateInterface stateInterface;

    public Atm() {
        this.context = new Context();
//
    }

    public AtmStateInterface getStateInterface() {
        return stateInterface;
    }

    public void setStateInterface(AtmStateInterface stateInterface) {
        this.stateInterface = stateInterface;
    }
}

class Context{
    BankAccount account;
    TransactionType transactionType;
    boolean isPinVerified;

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public boolean isPinVerified() {
        return isPinVerified;
    }

    public void setPinVerified(boolean pinVerified) {
        isPinVerified = pinVerified;
    }
}

interface AtmStateInterface{
    BankAccount insertCard(Atm atm, Card card);
    TransactionType selectTransaction(Atm atm, TransactionType transactionType);
    boolean acceptPin(Atm atm, int pin);
    void acceptAmount(Atm atm, long amount);
    void removeCard(Atm atm);
    void completion(Atm atm);
}

abstract class AtmStateImpl implements AtmStateInterface{
    @Override
    public BankAccount insertCard(Atm atm, Card card) {
        System.out.println("Operation not supported here...");
        return null;
    }

    @Override
    public TransactionType selectTransaction(Atm atm, TransactionType transactionType) {
        System.out.println("Operation not supported here...");
        return null;
    }

    @Override
    public boolean acceptPin(Atm atm, int pin) {
        System.out.println("Operation not supported here...");
        return false;
    }

    @Override
    public void acceptAmount(Atm atm, long amount) {
        System.out.println("Operation not supported here...");
    }

    @Override
    public void removeCard(Atm atm) {
        System.out.println("Operation not supported here...");
    }

    @Override
    public void completion(Atm atm) {
        System.out.println("Operation not supported here...");
    }
}

class AtmIdleState extends AtmStateImpl{
    @Override
    public BankAccount insertCard(Atm atm, Card card) {
        return super.insertCard(atm, card);
    }

    @Override
    public TransactionType selectTransaction(Atm atm, TransactionType transactionType) {
        return super.selectTransaction(atm, transactionType);
    }

    @Override
    public boolean acceptPin(Atm atm, int pin) {
        return super.acceptPin(atm, pin);
    }

    @Override
    public void acceptAmount(Atm atm, long amount) {
        super.acceptAmount(atm, amount);
    }

    @Override
    public void removeCard(Atm atm) {
        super.removeCard(atm);
    }

    @Override
    public void completion(Atm atm) {
        super.completion(atm);
    }
}