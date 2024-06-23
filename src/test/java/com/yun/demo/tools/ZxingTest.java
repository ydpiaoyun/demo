package com.yun.demo.tools;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ZxingTest {

    // 生成QR码的方法
    public void generateQRCode(String data, int width, int height, String filePath) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符编码
            hints.put(EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H); // 错误纠正级别
            hints.put(EncodeHintType.MARGIN, 1); // 二维码边距

            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

            // 创建BufferedImage对象来表示QR码
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            // 将QR码保存到文件
            File qrCodeFile = new File(filePath);
            ImageIO.write(image, "png", qrCodeFile);

            log.info("QR码已生成并保存到: {}" ,filePath);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test
    public void testGenerateQRCode() {
        String data = "https://todoitbo.fun"; // 要存储在QR码中的数据,可以是任何字符串
        int width = 300; // QR码的宽度（像素）
        int height = 300; // QR码的高度（像素）
        String filePath = "qrcode.png"; // 生成的QR码文件的保存路径
        generateQRCode(data, width, height, filePath);
    }

    // 生成条形码的方法
    public void generateBarcode(String data, int width, int height, String filePath) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符编码

            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.CODE_128, width, height, hints);

            // 创建BufferedImage对象来表示条形码
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0 : 0xFFFFFF); // 生成黑色条和白色背景的条形码
                }
            }
            // 将条形码保存到文件
            File barcodeFile = new File(filePath);
            ImageIO.write(image, "png", barcodeFile);
            log.info("条形码已生成并保存到: {}", filePath);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test
    public void testGenerateBarcode() {
        String data = "12345";
        int width = 200;
        int height = 100;
        String filePath = "barcode.png";
        generateBarcode(data, width, height, filePath);
    }
}