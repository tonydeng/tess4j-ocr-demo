package com.github.tonydeng.tesseract;

import com.cim120.scu.file.FileUtils;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tonydeng on 2017/2/14.
 */
public class OCRTest extends BaseTest {

    private final String path = this.getClass().getClassLoader().getResource("").getPath();
    private static List<String> imgs = Arrays.asList("1", "2", "3", "4");

    @Test
    public void testProcessImgByImageIO() {
        imgs.forEach(
                i -> {
                    Assert.assertTrue(FileUtils.isNotEmpty(Paths.get(path, i + ".jpg")));

                    OCR.processImgByImageIO(Paths.get(path, i + ".jpg").toString(),
                            Paths.get(path, "t_" + i + ".jpg").toString());
                }
        );
    }

    @Test
    public void testProcessImg() {
        imgs.forEach(
                i -> {
                    Assert.assertTrue(FileUtils.isNotEmpty(Paths.get(path, i + ".jpg")));

                    OCR.processImg(Paths.get(path, i + ".jpg").toString(),
                            Paths.get(path, "test_" + i + ".jpg").toString());
                }
        );

    }

    @Test
    public void testOCRImg() throws TesseractException {
        imgs.forEach(
                i -> {
                    String result = null;
                    try {
                        OCR.processImg(Paths.get(path, i + ".jpg").toString(), Paths.get(path, "test_" + i + ".jpg").toString());
                        result = OCR.ocrImg(Paths.get(path, "test_" + i + ".jpg").toFile());
                    } catch (TesseractException e) {
                        e.printStackTrace();
                    }
                    log.info("result:'{}'", result);
                }
        );

    }
}
