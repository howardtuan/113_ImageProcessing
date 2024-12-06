import java.awt.image.BufferedImage;
import java.util.Random;
// 添加椒鹽雜訊
public class SaltAndPepperNoise {

    /**
     * 向圖片添加椒鹽雜訊
     *
     * @param image      原始圖片
     * @param noiseLevel 雜訊比例（範圍 0 到 1）
     * @return 添加雜訊後的 BufferedImage
     */
    public static BufferedImage addSaltAndPepperNoise(BufferedImage image, double noiseLevel) {
        if (noiseLevel < 0 || noiseLevel > 1) {
            throw new IllegalArgumentException("雜訊比例必須在 0 和 1 之間");
        }

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage noisyImage = new BufferedImage(width, height, image.getType());
        Random rand = new Random();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (rand.nextDouble() < noiseLevel) {
                    // 隨機添加黑點（鹽）或白點（椒）
                    int newPixel = rand.nextBoolean() ? 0x000000 : 0xFFFFFF;
                    noisyImage.setRGB(x, y, newPixel);
                } else {
                    // 保留原始像素
                    noisyImage.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }

        return noisyImage;
    }
}
