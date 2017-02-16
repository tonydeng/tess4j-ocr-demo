package com.github.tonydeng.tesseract;

import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.lang3.StringUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by tonydeng on 2017/2/14.
 */
public class OCR {
    private static final Logger log = LoggerFactory.getLogger(OCR.class);

    public static void processImg(String src, String dest) {
        if (StringUtils.isEmpty(src)
                || StringUtils.isEmpty(dest)) {
            return;
        }
        IMOperation op = new IMOperation();
        op.addImage(src);
        op.p_profile("*");
//        op.colorspace("gray");  // 灰度处理
        op.addRawArgs("-threshold", "22%");
//        op.addRawArgs("-lat", "10x10-5%");  //局部自适应阈值，非常关键的参数

        op.addImage(dest);
        log.info("op:'{}'", op);
        ConvertCmd cmd = new ConvertCmd(true);
        try {
            cmd.run(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void processImgByImageIO(String src, String dest) {
        try {
            BufferedImage image = ImageIO.read(new File(src));
            int graySum = 0;
            int num = 0;
            //灰度，灰度值=0.3R+0.59G+0.11B
            for (int y = image.getMinY(); y < image.getHeight(); y++) {
                for (int x = image.getMinX(); x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    Color color = new Color(rgb);
                    int gray = (int) (0.3 * color.getRed() + 0.59
                            * color.getGreen() + 0.11 * color.getBlue());
                    graySum = gray + graySum;
                    Color newColor = new Color(gray, gray, gray);
                    image.setRGB(x, y, newColor.getRGB());
                    num++;
                }
            }
            //灰度反转
            for (int y = image.getMinY(); y < image.getHeight(); y++) {
                for (int x = image.getMinX(); x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
                    Color newColor = new Color(255 - color.getRed(), 255 - color
                            .getGreen(), 255 - color.getBlue());
                    image.setRGB(x, y, newColor.getRGB());
                }
            }
            //二值化，取图片的平均灰度作为阈值，低于该值的全都为0，高于该值的全都为255
            for (int y = image.getMinY(); y < image.getHeight(); y++) {
                for (int x = image.getMinX(); x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
                    int value = 255 - color.getBlue();
                    if (value > (graySum / num)) {
                        Color newColor = new Color(0, 0, 0);
                        image.setRGB(x, y, newColor.getRGB());
                    } else {
                        Color newColor = new Color(255, 255, 255);
                        image.setRGB(x, y, newColor.getRGB());
                    }
                }
            }

            ImageIO.write(image, "jpg", new File(dest));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String ocrImg(File img) throws TesseractException {
        Tesseract1 teaser = new Tesseract1();
        teaser.setDatapath(System.getenv("TESSDATA_PREFIX"));
        return teaser.doOCR(img).trim();
    }
}
