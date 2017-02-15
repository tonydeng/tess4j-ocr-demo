# TESS4J OCR Demo

使用Tesseract + GraphicsMagick进行简单的OCR处理

## 验证码识别

### 基本原理

![CAPTCHA](src/test/resources/2.jpg)

* 使用 `gm` 对验证码图片进行降噪

```bash
gm convert 2.jpg +profile '*' -threshold '22%' test_2.jpg
```

* 对降噪之后的验证码图片进行识别

```bash
tesseract test_2.jpg  -psm 7 r
```

* 查看结果

```bash
cat r.txt
ZLQE
```

### Java实现（tess4j + im4java）

代码实现：[OCR](src/main/java/com/github/tonydeng/tesseract/OCR.java)

测试方法：[OCRTest](src/test/java/com/github/tonydeng/tesseract/OCRTest.java)

# Tesseract OCR使用

参见之前写的一篇Blog，[利用Tesseract图片文字识别初探](https://tonydeng.github.io/2016/07/28/on-the-use-of-tesseract-picture-text-recognition/)，对Tesseract的安装和使用详细介绍。