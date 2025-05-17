package com.example.demo.service;

import com.example.demo.entity.Payment;
import com.example.demo.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Value("${razorpay.key.id}")
    private String razorpayKey;

    @Value("${razorpay.secret.key}")
    private String razorpaySecret;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Order createOrder(Payment payment) throws Exception {
        RazorpayClient client = new RazorpayClient(razorpayKey, razorpaySecret);

        JSONObject options = new JSONObject();
        options.put("amount", payment.getAmount() * 100); // Razorpay expects amount in paise
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());
        options.put("payment_capture", 1); // auto-capture

        Order order = client.orders.create(options);

        payment.setRazorpayOrderId(order.get("id"));
        payment.setCurrency(order.get("currency"));
        payment.setReceipt(order.get("receipt"));
        payment.setOrderStatus("CREATED");
        paymentRepository.save(payment);

        return order;
    }

    public void savePaymentDetails(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        Payment payment = paymentRepository.findByRazorpayOrderId(razorpayOrderId);
        if (payment != null) {
            payment.setRazorpayPaymentId(razorpayPaymentId);
            payment.setRazorpaySignature(razorpaySignature);
            payment.setOrderStatus("PAID");
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);
        }
    }

    public Payment getPaymentDetails(Long orderId) {
        return paymentRepository.findById(orderId).orElse(null);
    }
}
