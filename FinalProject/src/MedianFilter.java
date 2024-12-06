import java.awt.image.BufferedImage;
import java.util.Arrays;
// 中值濾波
public class MedianFilter {

    /**
     * 對圖片應用中值濾波
     *
     * @param image 原始圖片
     * @return 濾波後的 BufferedImage
     */
    public static BufferedImage medianFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage filteredImage = new BufferedImage(width, height, image.getType());

        int[] pixelArray = new int[9]; // 3x3 區域的像素值

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int index = 0;

                // 取得 3x3 區域內的像素值
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        int rgb = image.getRGB(x + dx, y + dy) & 0xff; // 只取灰階值
                        pixelArray[index++] = rgb;
                    }
                }

                // 排序 3x3 像素值，取得中值
                Arrays.sort(pixelArray);
                int median = pixelArray[4]; // 中值是排序後的第 5 個元素（索引 4）

                // 設定新像素值
                int newPixel = (median << 16) | (median << 8) | median; // 灰階值轉換為 RGB
                filteredImage.setRGB(x, y, newPixel);
            }
        }

        return filteredImage;
    }
}
