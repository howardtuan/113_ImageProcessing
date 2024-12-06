import java.awt.image.BufferedImage;
// OTSU 二值化
public class OtsuBinarization {

    /**
     * 使用 OTSU 方法進行圖片二值化
     *
     * @param image 原始圖片（假設為灰階圖片）
     * @return 二值化後的 BufferedImage
     */
    public static BufferedImage otsuBinarization(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage binaryImage = new BufferedImage(width, height, image.getType());

        // 計算灰階直方圖
        int[] histogram = new int[256];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gray = image.getRGB(x, y) & 0xff;
                histogram[gray]++;
            }
        }

        // 計算總像素數量
        int totalPixels = width * height;

        // 計算最佳閾值
        int sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += i * histogram[i];
        }

        int sumB = 0, weightB = 0, weightF = 0;
        double maxVariance = 0.0;
        int threshold = 0;

        for (int t = 0; t < 256; t++) {
            weightB += histogram[t];
            if (weightB == 0) continue;

            weightF = totalPixels - weightB;
            if (weightF == 0) break;

            sumB += t * histogram[t];
            double meanB = sumB / (double) weightB;
            double meanF = (sum - sumB) / (double) weightF;

            double variance = weightB * weightF * Math.pow(meanB - meanF, 2);
            if (variance > maxVariance) {
                maxVariance = variance;
                threshold = t;
            }
        }

        // 二值化處理
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gray = image.getRGB(x, y) & 0xff;
                int binary = (gray > threshold) ? 255 : 0;
                int newPixel = (binary << 16) | (binary << 8) | binary;
                binaryImage.setRGB(x, y, newPixel);
            }
        }

        return binaryImage;
    }
}
