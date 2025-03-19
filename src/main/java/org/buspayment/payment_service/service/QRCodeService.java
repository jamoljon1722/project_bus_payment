package org.buspayment.payment_service.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QRCodeService {

    private static final String QR_CODE_PATH = "src/main/resources/static/qrcode.png";
    private static final String PAY_URL = "http://192.168.100.31:8080/pay/payment"; // Botga payment web pageni yoziwimiza kere


    public static void generateQRCode() throws WriterException, IOException {
        int width = 300;
        int height = 300;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(PAY_URL, BarcodeFormat.QR_CODE, width, height, hints);

        Path path = FileSystems.getDefault().getPath(QR_CODE_PATH);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        System.out.println("✅ QR kod yaratildi: " + QR_CODE_PATH);
    }
}
