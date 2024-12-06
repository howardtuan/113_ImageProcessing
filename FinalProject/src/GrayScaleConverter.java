import java.awt.image.BufferedImage;
// 灰階轉換
public class GrayScaleConverter {

    /**
     * 將圖片轉換為灰階
     *
     * @param image 原始圖片
     * @return 轉換後的灰階 BufferedImage
     */
    public static BufferedImage toGrayScale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF; // 紅色通道
                int g = (rgb >> 8) & 0xFF;  // 綠色通道
                int b = rgb & 0xFF;         // 藍色通道
                int gray = (r + g + b) / 3; // 計算灰階值
                int newPixel = (gray << 16) | (gray << 8) | gray; // 灰階值轉換為 RGB 格式
                grayImage.setRGB(x, y, newPixel);
            }
        }

        return grayImage;
    }
}