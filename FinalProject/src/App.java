import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class App {
    public static void main(String[] args) throws Exception {
        // 載入原始圖像
        BufferedImage originalImage = ImageIO.read(new File("input.jpg"));

        // 1. 灰階處理
        BufferedImage grayImage = GrayScaleConverter.toGrayScale(originalImage);
        ImageIO.write(grayImage, "jpg", new File("grayImage.jpg"));

        // 2. 負片處理
        BufferedImage negativeImage = NegativeImageConverter.toNegative(grayImage);
        ImageIO.write(negativeImage, "jpg", new File("negativeImage.jpg"));

        // 3. Gamma 調整 (Gamma < 1)
        BufferedImage gammaLessImage = GammaAdjuster.adjustGamma(grayImage, 0.5);
        ImageIO.write(gammaLessImage, "jpg", new File("gammaLessImage.jpg"));

        // 4. Gamma 調整 (Gamma > 1)
        BufferedImage gammaMoreImage = GammaAdjuster.adjustGamma(grayImage, 2.0);
        ImageIO.write(gammaMoreImage, "jpg", new File("gammaMoreImage.jpg"));

        // 5. 對比拉伸
        BufferedImage contrastStretchedImage = ContrastStretch.contrastStretch(grayImage);
        ImageIO.write(contrastStretchedImage, "jpg", new File("contrastStretchedImage.jpg"));

        // 6. 添加椒鹽雜訊
        BufferedImage noisyImage = SaltAndPepperNoise.addSaltAndPepperNoise(gammaLessImage, 0.05);
        ImageIO.write(noisyImage, "jpg", new File("noisyImage.jpg"));

        // 7. 中值濾波
        BufferedImage medianFilteredImage = MedianFilter.medianFilter(noisyImage);
        ImageIO.write(medianFilteredImage, "jpg", new File("medianFilteredImage.jpg"));

        // 8. Laplacian 邊緣檢測
        BufferedImage laplacianImage = LaplacianEdgeDetection.laplacianEdgeDetection(contrastStretchedImage);
        ImageIO.write(laplacianImage, "jpg", new File("laplacianImage.jpg"));

        // 9. 最大值濾波
        BufferedImage maxFilteredImage = MaxFilter.maxFilter(laplacianImage);
        ImageIO.write(maxFilteredImage, "jpg", new File("maxFilteredImage.jpg"));

        // 10. OTSU 二值化 (Bonus)
        BufferedImage otsuImage = OtsuBinarization.otsuBinarization(gammaMoreImage);
        ImageIO.write(otsuImage, "jpg", new File("otsuImage.jpg"));
    }
}

