package org.practice.lld.paymentadapter;

public class Main {
    public static void main(String[] args) {

        PaymentAdapter adapter = AdapterFactory.getPaymentAdapter("STRIPE");

        if(adapter != null){
            adapter.initiatePayment(new PaymentRequest("gdfe001", 1000, "ABCDEFG"));
        }

    }
}

class AdapterFactory{
    private AdapterFactory() {
    }

    public static PaymentAdapter getPaymentAdapter(String processor){
        return switch (processor) {
            case "PAYPAL" -> new PaypalPaymentAdapter();
            case "STRIPE" -> new StripeAdaptor();
            default -> null;
        };
    }
}

class PaymentRequest{
    String id;
    int amount;
    String userDetails;

    public PaymentRequest(String id, int amount, String userDetails) {
        this.id = id;
        this.amount = amount;
        this.userDetails = userDetails;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getUserDetails() {
        return userDetails;
    }
}

interface PaymentAdapter{
    boolean initiatePayment(PaymentRequest request);
}

class PaypalPaymentAdapter implements PaymentAdapter{

    PaypalPaymentInterface paypalPayment;

    @Override
    public boolean initiatePayment(PaymentRequest request) {
        String temp = paypalPayment.intitiatePayment(request.getId(), request.getAmount(), request.getUserDetails());
        int checkRetries = 3;
        try{
            while (checkRetries >= 3 && paypalPayment.checkStatus(temp)){
                Thread.sleep(1000);
                checkRetries--;
            }
        } catch (RuntimeException | InterruptedException e) {
            throw new RuntimeException(e);
        }
//        inititate a reversal just in case
        return false;
    }
}

class StripeAdaptor implements PaymentAdapter{

    StripeInterface stripeInterface;

    @Override
    public boolean initiatePayment(PaymentRequest request) {
        return  stripeInterface.pay(new RequestDetails());
    }
}

interface PaypalPaymentInterface{
    String intitiatePayment(String id, int amount, String userDetails);
    boolean checkStatus(String payId);
}

interface StripeInterface{
    boolean pay(RequestDetails details);
}

class RequestDetails{

}
