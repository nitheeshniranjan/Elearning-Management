package com.example.demo.controller;

import com.example.demo.dto.PaymentVerificationRequest;
import com.example.demo.entity.Payment;
import com.example.demo.service.PaymentService;
import com.razorpay.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Payment paymentRequest) {
        try {
            Order order = paymentService.createOrder(paymentRequest);
            return ResponseEntity.ok()
                    .body(new java.util.HashMap<>() {{
                        put("razorpayOrderId", order.get("id"));
                        put("amount", order.get("amount"));
                        put("currency", order.get("currency"));
                    }});
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating Razorpay order: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody PaymentVerificationRequest request) {
        try {
            String orderId = request.getRazorpayOrderId();
            String paymentId = request.getRazorpayPaymentId();
            String signature = request.getRazorpaySignature();

            String secret = ""; // Replace with actual secret
            String data = orderId + "|" + paymentId;

            String generatedSignature = hmacSHA256(data, secret);

            if (generatedSignature.equals(signature)) {
                return ResponseEntity.ok("Payment verified successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error verifying payment");
        }
    }

    private String hmacSHA256(String data, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes());

        // Convert to HEX (NOT Base64)
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getPayment(@PathVariable Long orderId) {
        Payment payment = paymentService.getPaymentDetails(orderId);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
