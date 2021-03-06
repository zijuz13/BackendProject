package com.huajun123.biz;

import com.huajun123.config.ImageConfigurations;
import org.apache.commons.lang.StringUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.xml.ws.soap.Addressing;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
@EnableConfigurationProperties(ImageConfigurations.class)
public class ImageConverter {
    private static final Logger LOGGER= LoggerFactory.getLogger(ImageConverter.class);
    @Autowired
    private ImageConfigurations configurations;
    public String convert(String videoPath){
        Frame frame = null;
        //构造器支持InputStream，可以直接传MultipartFile.getInputStream()
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath);
        //指定第几帧
        try {

            //开始播放
            fFmpegFrameGrabber.start();
            fFmpegFrameGrabber.setFrameNumber(1);
            fFmpegFrameGrabber.setFormat("mp4");
            //获取指定第几帧的图片
            frame = fFmpegFrameGrabber.grabImage();
        } catch (FrameGrabber.Exception e) {
            LOGGER.error("Grabbing First Frame Failed, detailed information: {}",e.getMessage());
        }
        //如果没有获取贞
        if(!Optional.ofNullable(frame).isPresent()){
            return null;
        }
        String fileName=videoPath.substring(0,videoPath.lastIndexOf("."))+ ".jpg";
        String newFileName=fileName.substring(fileName.lastIndexOf("/")+1);
        String path=configurations.getDockerPath();
        File file1=new File(path);
        if(!file1.exists()){
            file1.mkdirs();
        }
        File outPut = new File(path+"/"+newFileName);
        try {
            ImageIO.write(FrameToBufferedImage(frame), "jpg", outPut);
        } catch (IOException e) {
            LOGGER.error("Image IO failed {}",e);
        }
        return newFileName;
    }
    private BufferedImage FrameToBufferedImage(Frame frame) {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }
}
