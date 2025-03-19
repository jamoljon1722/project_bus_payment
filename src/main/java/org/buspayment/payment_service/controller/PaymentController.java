package org.buspayment.payment_service.controller;

import jakarta.servlet.http.HttpSession;
import org.buspayment.payment_service.repository.PaymentRepository;
import org.buspayment.payment_service.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pay")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/success")
    public String showSuccessPage(HttpSession session) {
        Boolean isPaid = (Boolean) session.getAttribute("isPaid");
        if (isPaid == null || !isPaid) {
            return "redirect:/pay/payment";
        }
        session.removeAttribute("isPaid");
        return "success";
    }

    @GetMapping("/payment")
    public String showPaymentPage() {
        return "payment";
    }

    @PostMapping
    public String processPayment(@RequestParam String cardNumber,
                                 @RequestParam String pinCode,
                                 Model model,
                                 HttpSession session) {
        String result = paymentService.processPayment(cardNumber, pinCode);

        if (result.equals("SUCCESS")) {
            session.setAttribute("isPaid", true);
            return "redirect:/pay/success";
        }

        model.addAttribute("message", result);
        return "payment";
    }
}
