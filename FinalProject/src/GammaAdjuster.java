import java.awt.image.BufferedImage;
// Gamma 調整
public class GammaAdjuster {

    /**
     * 調整圖片的 Gamma 值
     *
     * @param image 灰階圖片
     * @param gamma Gamma 調整值（建議範圍 > 0）
     * @return 調整後的 BufferedImage
     */
    public static BufferedImage adjustGamma(BufferedImage image, double gamma) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage gammaImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (int) (Math.pow(((rgb >> 16) & 0xff) / 255.0, gamma) * 255);
                int g = (int) (Math.pow(((rgb >> 8) & 0xff) / 255.0, gamma) * 255);
                int b = (int) (Math.pow((rgb & 0xff) / 255.0, gamma) * 255);

                // 確保值在 0-255 之間
                r = Math.min(Math.max(r, 0), 255);
                g = Math.min(Math.max(g, 0), 255);
                b = Math.min(Math.max(b, 0), 255);

                int newPixel = (r << 16) | (g << 8) | b;
                gammaImage.setRGB(x, y, newPixel);
            }
        }

        return gammaImage;
    }
}
