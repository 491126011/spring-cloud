package com.platform.utils;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.UUID;

public class PosterUtils {

    /**
     *
     * @param posterTitle 海报名称
     * @param backgroundImg 背景图
     * @param qrCodeImg 二维码图
     * @param saveLocation 保存地址
     * @return
     * @throws Exception
     */
    public static String createPoster(String posterTitle, File backgroundImg ,File qrCodeImg, String saveLocation) throws Exception {
        long nowTime = System.currentTimeMillis();

        //1、背景图片输入流
        FileInputStream fis = new FileInputStream(backgroundImg);
        //2、背景图片对象
        Image srcImg = ImageIO.read(fis);
        //3、创建画布，根据背景图片的宽高
        BufferedImage bufferedImage = new BufferedImage(
                //宽度
                srcImg.getWidth(null),
                //高度
                srcImg.getHeight(null),
                //图片类型
                BufferedImage.TYPE_INT_RGB);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        Graphics2D g = bufferedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        g.drawImage(ImageIO.read(qrCodeImg), 340, 720, 175, 175, null);

        g.dispose();
        //8、得到输出流
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
        String encodeStr = Base64.encode(new String(os.toByteArray()));
        //保存为图片文件
        FileUtils.writeByteArrayToFile(new File(saveLocation + nowTime + ".jpg"), os.toByteArray());
        //10、关闭输入输出流
        fis.close();
        os.close();
        return encodeStr;

    }

}
