package com.github.ong.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class VideoFrameUtil {
    public static boolean getFrame(String framePath, String videoPath){
        File targetFile = new File(framePath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        File file2 = new File(videoPath);
        log.info("check is Video {}", videoPath);
        if (!isVideo(videoPath)) {
            log.info("path is not video {}", videoPath);
            return false;
        }

        try {
            if (file2.getParentFile().exists()) {
                FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file2);
                ff.start();
                int ftp = ff.getLengthInFrames();
                int flag=0;
                Frame frame = null;
                while (flag <= ftp) {
                    //获取帧
                    frame = ff.grabImage();
                    //过滤前3帧，避免出现全黑图片
                    if ((flag>3)&&(frame != null)) {
                        break;
                    }
                    flag++;
                }
                if(ImageIO.write(FrameToBufferedImage(frame), "jpg", targetFile)) {
                    ff.close();
                    ff.stop();
                    log.info("输出图片成功！");
                    return true;
                }else {
                    ff.close();
                    ff.stop();
                    log.info("输出图片失败！");
                    return false;
                }
            }else{
                log.info("路径内容错误！{}", file2.getParentFile());
                return false;
            }
        } catch (Exception e ) {
            log.info("get frame exception {}", ExceptionUtils.getStackTrace(e));
            return false;
        }
    }

    private static RenderedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }

    public static boolean isVideo(String path) {
        //设置视频后缀
        List<String> typeList = new ArrayList<String>();
        typeList.add("mp4");
        typeList.add("flv");
        typeList.add("avi");
        typeList.add("rmvb");
        typeList.add("rm");
        typeList.add("wmv");
        //获取文件名和后缀
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        for(String type : typeList) {
            //统一为大写作比较
            if(type.toUpperCase().equals(suffix.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
