import java.awt.image.BufferedImage;
// 最大值濾波
public class MaxFilter {

    /**
     * 對圖片應用最大值濾波
     *
     * @param image 原始圖片
     * @return 濾波後的 BufferedImage
     */
    public static BufferedImage maxFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage filteredImage = new BufferedImage(width, height, image.getType());

        int[][] kernel = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 0}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
        };

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int maxPixel = 0;

                // 掃描 3x3 區域內的像素，找到最大值
                for (int[] offset : kernel) {
                    int dx = x + offset[0];
                    int dy = y + offset[1];
                    int pixel = image.getRGB(dx, dy) & 0xff; // 只取灰階值
                    if (pixel > maxPixel) {
                        maxPixel = pixel;
                    }
                }

                // 設定新像素值
                int newPixel = (maxPixel << 16) | (maxPixel << 8) | maxPixel; // 灰階值轉換為 RGB
                filteredImage.setRGB(x, y, newPixel);
            }
        }

        return filteredImage;
    }
}
