import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class App {
    public static void main(String[] args) throws Exception {
        // 載入原始圖像
        BufferedImage originalImage = ImageIO.read(new File("input.jpg"));

        // 1. 灰階處理
        BufferedImage grayImage = toGrayScale(originalImage);
        ImageIO.write(grayImage, "jpg", new File("grayImage.jpg"));

        // 2. 負片處理
        BufferedImage negativeImage = toNegative(grayImage);
        ImageIO.write(negativeImage, "jpg", new File("negativeImage.jpg"));

        // 3. Gamma 調整 (Gamma < 1)
        BufferedImage gammaLessImage = adjustGamma(grayImage, 0.5);
        ImageIO.write(gammaLessImage, "jpg", new File("gammaLessImage.jpg"));

        // 4. Gamma 調整 (Gamma > 1)
        BufferedImage gammaMoreImage = adjustGamma(grayImage, 2.0);
        ImageIO.write(gammaMoreImage, "jpg", new File("gammaMoreImage.jpg"));

        // 5. 對比拉伸
        BufferedImage contrastStretchedImage = contrastStretch(grayImage);
        ImageIO.write(contrastStretchedImage, "jpg", new File("contrastStretchedImage.jpg"));

        // 6. 添加椒鹽雜訊
        BufferedImage noisyImage = addSaltAndPepperNoise(grayImage, 0.05);
        ImageIO.write(noisyImage, "jpg", new File("noisyImage.jpg"));

        // 7. 中值濾波
        BufferedImage medianFilteredImage = medianFilter(noisyImage);
        ImageIO.write(medianFilteredImage, "jpg", new File("medianFilteredImage.jpg"));

        // 8. 最大值濾波
        BufferedImage maxFilteredImage = maxFilter(noisyImage);
        ImageIO.write(maxFilteredImage, "jpg", new File("maxFilteredImage.jpg"));

        // 9. Laplacian 邊緣檢測
        BufferedImage laplacianImage = laplacianEdgeDetection(grayImage);
        ImageIO.write(laplacianImage, "jpg", new File("laplacianImage.jpg"));

        // 10. OTSU 二值化 (Bonus)
        BufferedImage otsuImage = otsuBinarization(grayImage);
        ImageIO.write(otsuImage, "jpg", new File("otsuImage.jpg"));
    }

    // 灰階轉換
    public static BufferedImage toGrayScale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                int gray = (r + g + b) / 3;
                int newPixel = (gray << 16) | (gray << 8) | gray;
                grayImage.setRGB(x, y, newPixel);
            }
        }
        return grayImage;
    }

    // 負片轉換
    public static BufferedImage toNegative(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage negativeImage = new BufferedImage(width, height, image.getType());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = 255 - ((rgb >> 16) & 0xff);
                int g = 255 - ((rgb >> 8) & 0xff);
                int b = 255 - (rgb & 0xff);
                int newPixel = (r << 16) | (g << 8) | b;
                negativeImage.setRGB(x, y, newPixel);
            }
        }
        return negativeImage;
    }

    // Gamma 調整
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
                int newPixel = (r << 16) | (g << 8) | b;
                gammaImage.setRGB(x, y, newPixel);
            }
        }
        return gammaImage;
    }

    // 對比拉伸
    public static BufferedImage contrastStretch(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage stretchedImage = new BufferedImage(width, height, image.getType());
        int minGray = 255, maxGray = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gray = (image.getRGB(x, y) & 0xff);
                if (gray < minGray) minGray = gray;
                if (gray > maxGray) maxGray = gray;
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gray = (image.getRGB(x, y) & 0xff);
                int stretched = (gray - minGray) * 255 / (maxGray - minGray);
                int newPixel = (stretched << 16) | (stretched << 8) | stretched;
                stretchedImage.setRGB(x, y, newPixel);
            }
        }
        return stretchedImage;
    }

    // 添加椒鹽雜訊
    public static BufferedImage addSaltAndPepperNoise(BufferedImage image, double noiseLevel) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage noisyImage = new BufferedImage(width, height, image.getType());
        Random rand = new Random();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (rand.nextDouble() < noiseLevel) {
                    int newPixel = rand.nextBoolean() ? 0x000000 : 0xFFFFFF;
                    noisyImage.setRGB(x, y, newPixel);
                } else {
                    noisyImage.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }
        return noisyImage;
    }

    // 中值濾波
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
                java.util.Arrays.sort(pixelArray);
                int median = pixelArray[4]; // 中值是排序後的第 5 個元素（索引 4）
    
                // 設定新像素值
                int newPixel = (median << 16) | (median << 8) | median; // 灰階值轉換為 RGB
                filteredImage.setRGB(x, y, newPixel);
            }
        }
    
        return filteredImage;
    }
    

    // 最大值濾波
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
                for (int[] offset : kernel) {
                    int dx = x + offset[0];
                    int dy = y + offset[1];
                    int pixel = image.getRGB(dx, dy) & 0xff;
                    if (pixel > maxPixel) {
                        maxPixel = pixel;
                    }
                }
                int newPixel = (maxPixel << 16) | (maxPixel << 8) | maxPixel;
                filteredImage.setRGB(x, y, newPixel);
            }
        }
    
        return filteredImage;
    }
    

    // Laplacian 邊緣檢測
    public static BufferedImage laplacianEdgeDetection(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage edgeImage = new BufferedImage(width, height, image.getType());
    
        int[][] laplacianKernel = {
            {0, -1, 0},
            {-1, 4, -1},
            {0, -1, 0}
        };
    
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int sum = 0;
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int pixel = image.getRGB(x + kx, y + ky) & 0xff;
                        sum += pixel * laplacianKernel[ky + 1][kx + 1];
                    }
                }
                sum = Math.min(255, Math.max(0, sum));
                int newPixel = (sum << 16) | (sum << 8) | sum;
                edgeImage.setRGB(x, y, newPixel);
            }
        }
    
        return edgeImage;
    }
    

    // OTSU 二值化
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

