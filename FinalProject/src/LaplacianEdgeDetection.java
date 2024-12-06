import java.awt.image.BufferedImage;
// Laplacian 邊緣檢測
public class LaplacianEdgeDetection {

    /**
     * 使用 Laplacian 核心對圖片進行邊緣檢測
     *
     * @param image 原始圖片（假設為灰階圖片）
     * @return 邊緣檢測後的 BufferedImage
     */
    public static BufferedImage laplacianEdgeDetection(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage edgeImage = new BufferedImage(width, height, image.getType());

        // Laplacian 核心
        int[][] laplacianKernel = {
            {0, -1, 0},
            {-1, 4, -1},
            {0, -1, 0}
        };

        // 應用 Laplacian 邊緣檢測
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int sum = 0;

                // 遍歷 3x3 區域並計算卷積
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int pixel = image.getRGB(x + kx, y + ky) & 0xff; // 只取灰階值
                        sum += pixel * laplacianKernel[ky + 1][kx + 1];
                    }
                }

                // 限制結果在 [0, 255] 範圍內
                sum = Math.min(255, Math.max(0, sum));

                // 將結果轉換為 RGB 格式
                int newPixel = (sum << 16) | (sum << 8) | sum; // 灰階值轉換為 RGB
                edgeImage.setRGB(x, y, newPixel);
            }
        }

        return edgeImage;
    }
}
