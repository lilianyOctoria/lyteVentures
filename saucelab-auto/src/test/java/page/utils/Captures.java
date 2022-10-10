package page.utils;


import driver.driverFactory;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.cropper.indent.BlurFilter;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public final class Captures {
    public static void takeSimpleFullScreenShot() throws IOException {
        Screenshot bi = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(50)).takeScreenshot(driverFactory.getWebDriver());
        createFolderIfDoesNotExist((new File(Captures.getSimpleCapturePath())).getParent());
        File compressedImageFile = new File(Captures.getSimpleCapturePath());
        OutputStream outputStream = new FileOutputStream(compressedImageFile);
        ImageIO.write(bi.getImage(), "png", outputStream);
        //Reporter.addScreenCaptureFromPath(System.getProperty("user.dir")+File.separator+ compressedImageFile);
    }

    public static void takeScreenShot(WebDriver drv, String filepath) {
        try {
            WebElement body = drv.findElement(By.xpath("//html"));
            Dimension currentDimension = new Dimension(drv.manage().window().getSize().width,
                    drv.manage().window().getSize().height);
            drv.manage().window().setSize(new Dimension(body.getSize().getWidth(), body.getSize().getHeight() + 550));
            File scrFile = ((TakesScreenshot) drv).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(filepath));
            drv.manage().window().setSize(currentDimension);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void takeScreenShot(WebDriver drv, WebElement elm, String filepath) {
        try {
            Screenshot sc;
            createFolderIfDoesNotExist((new File(filepath)).getParent());
            try {
                sc = new AShot().imageCropper(new IndentCropper().addIndentFilter(new BlurFilter())).takeScreenshot(drv,
                        elm);
            } catch (Exception e) {
                try {
                    sc = new AShot().imageCropper(new IndentCropper().addIndentFilter(new BlurFilter()))
                            .takeScreenshot(drv, elm);
                } catch (Exception e2) {
                    sc = new AShot().takeScreenshot(drv);
                }

            }

            compressAndSaveImage(sc.getImage(), filepath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void compressAndSaveImage(BufferedImage image, String filePath) {
        try {

            File compressedImageFile = new File(filePath);
            OutputStream outputStream = new FileOutputStream(compressedImageFile);

            Iterator<ImageWriter> writers = ImageIO
                    .getImageWritersByFormatName(FilenameUtils.getExtension(filePath).toLowerCase());
            ImageWriter imageWriter = writers.next();

            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            imageWriter.setOutput(imageOutputStream);
            ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(0.8f);

            int iw = image.getWidth();
            int ih = image.getHeight();
            BufferedImage newimage = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < iw; x++) {
                for (int y = 0; y < ih; y++) {
                    newimage.setRGB(x, y, image.getRGB(x, y));
                }
            }

            imageWriter.write(null, new IIOImage(newimage, null, null), imageWriteParam);

            outputStream.close();
            imageOutputStream.close();
            imageWriter.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SaveImageToFile(BufferedImage bufferedImage, String filePath) throws Exception {
        File outputfile = new File(filePath);
        ImageIO.write(bufferedImage, FilenameUtils.getExtension(filePath).toLowerCase(), outputfile);
    }

    public static BufferedImage decodeToImage(byte[] imageBytes) {
        BufferedImage image = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void createFolderIfDoesNotExist(String FolderPath) {
        File theDir = new File(FolderPath);
        if (!theDir.exists()) {
            boolean result = false;
            try {
                theDir.mkdirs();
                result = true;
            } catch (SecurityException se) {
                // handle it
            }
            if (result) {
                System.out.println("DIR created");
            }
        }
    }

    public static void embedFolderImagesToScenario(String FolderPath, Scenario scenario) {
        File folder = new File(FolderPath);
        File[] files = folder.listFiles();
        Arrays.sort(files);
        for (final File fileEntry : files) {
            embedScreenShotToScenario(fileEntry, scenario);
        }
    }

    public static void embedScreenShotToScenario(File file, Scenario scenario) {
        try {
            FileInputStream in;
            in = new FileInputStream(file);
            String mimeType = (FilenameUtils.getExtension(file.getName()) == "png") ? "image/png" : "image/jpeg";
            scenario.attach(IOUtils.toByteArray(in), mimeType,"");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static String getSimpleCapturePath() {
        return getSimpleCapturePathWithoutExtention() + ".jpg";
    }

    public static String getSimpleCapturePathWithoutExtention() {
        return settings.getCurrentScreenCaptureFolderPath()
                + (new SimpleDateFormat("yyyyMMdd HH-mm-ss")).format(new Date());
    }

    public static void deleteAllScreenShots(String folderPath) throws IOException {
        FileUtils.deleteDirectory(new File(folderPath));
    }

}
