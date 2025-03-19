package org.buspayment.payment_service;

import com.google.zxing.WriterException;
import org.buspayment.payment_service.service.QRCodeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class PaymentServiceApplication {

    public static void main(String[] args) throws IOException, WriterException {
        QRCodeService.generateQRCode();
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}
