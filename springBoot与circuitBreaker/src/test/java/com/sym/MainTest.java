package com.sym;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author shenyanming
 * Created on 2020/7/22 10:15
 */
public class MainTest {

    @Test
    public void test01() throws Exception {
        String url = "http://mmbiz.qpic.cn/mmbiz_png/IibdiajIPJZc7LOkJo6TASKVC98X13kfMiceWZjPJKa04CmLLIS89MaFqW6hrqfJEicosicp7ePO5Owfs8sN987Xh9w/0";
        URL url1 = new URL(url);
        URLConnection urlConnection = url1.openConnection();
        //urlConnection.setDoOutput(true);
        InputStream inputStream = urlConnection.getInputStream();

        BufferedImage bufferedImage = ImageIO.read(inputStream);
        System.out.println(bufferedImage.getWidth());
        System.out.println(bufferedImage.getHeight());


    }
}
