import java.awt.image.BufferedImage;
// 對比拉伸
public class ContrastStretch {

    /**
     * 對圖片進行對比拉伸
     *
     * @param image 原始圖片
     * @return 進行對比拉伸後的 BufferedImage
     */
    public static BufferedImage contrastStretch(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage stretchedImage = new BufferedImage(width, height, image.getType());

        int minGray = 255, maxGray = 0;

        // 找到灰度值的最小值和最大值
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gray = (image.getRGB(x, y) & 0xff);
                if (gray < minGray) minGray = gray;
                if (gray > maxGray) maxGray = gray;
            }
        }

        // 防止最大值和最小值相等導致除以零
        if (maxGray == minGray) {
            System.err.println("圖片的灰度值範圍為 0，無法進行對比拉伸。");
            return image;
        }

        // 進行對比拉伸
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gray = (image.getRGB(x, y) & 0xff);
                int stretched = (gray - minGray) * 255 / (maxGray - minGray);

                // 確保新像素值範圍在 [0, 255]
                stretched = Math.min(Math.max(stretched, 0), 255);

                int newPixel = (stretched << 16) | (stretched << 8) | stretched; // 灰階像素
                stretchedImage.setRGB(x, y, newPixel);
            }
        }

        return stretchedImage;
    }
}
