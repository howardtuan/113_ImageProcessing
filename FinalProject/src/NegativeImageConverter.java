import java.awt.image.BufferedImage;
// 負片轉換
public class NegativeImageConverter {

    /**
     * 將圖片轉換為負片
     *
     * @param image 原始圖片
     * @return 負片轉換後的 BufferedImage
     */
    public static BufferedImage toNegative(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage negativeImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = 255 - ((rgb >> 16) & 0xff); // 反轉紅色通道
                int g = 255 - ((rgb >> 8) & 0xff);  // 反轉綠色通道
                int b = 255 - (rgb & 0xff);         // 反轉藍色通道
                int newPixel = (r << 16) | (g << 8) | b; // 重新組合 RGB
                negativeImage.setRGB(x, y, newPixel);    // 設定新的像素值
            }
        }

        return negativeImage;
    }
}