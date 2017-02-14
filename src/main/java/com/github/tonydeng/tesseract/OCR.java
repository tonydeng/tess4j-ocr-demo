package com.github.tonydeng.tesseract;

import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.lang3.StringUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


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
        op.addRawArgs("-threshold", "22%");
        op.addImage(dest);
        log.info("op:'{}'", op);
        ConvertCmd cmd = new ConvertCmd(true);
        try {
            cmd.run(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String ocrImg(File img) throws TesseractException {
        Tesseract1 teaser = new Tesseract1();
        teaser.setDatapath(System.getenv("TESSDATA_PREFIX"));
        return teaser.doOCR(img).trim();
    }
}
