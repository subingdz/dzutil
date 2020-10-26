package cn.subingdz.dzutil.pdfutil;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 用于PDF转图片的处理类
 *
 * @author subingdz
 * @since 1.0.1
 */
public class PDFToImage {

    private static String BASE64_START = "base64;";

    private static String SEPA = File.separator;

    private static String COIN = ".";

    private static BASE64Decoder decoder = new BASE64Decoder();

    private static BASE64Encoder encoder = new BASE64Encoder();

    /**
     * 将pdf转成图片
     * 由传入pdf的base64值，还原成pdf，再通过pdfbox转换成图片文件file
     * @param pdfBase64
     * @param imageName
     * @param imageType
     * @return
     */
    public static void pdfBase64ToImage(String pdfBase64 , String imageName , String imageType , String path) throws IOException {
        byte[] pdfByte = removeBase64Start(pdfBase64);
        PDDocument pddocument = PDDocument.load(new File("C:\\Users\\sby54\\Desktop\\pdfjs\\pdfjs.pdf"));
        File parentPath = new File(path);
        if (!parentPath.exists()){
            parentPath.mkdirs();
        }
        StringBuilder sb = new StringBuilder(parentPath.getPath());
        sb.append(SEPA).append(imageName).append(COIN).append(imageType);
        System.out.println(sb.toString());
        File imageFile = new File(sb.toString());
        ByteArrayInputStream bais = new ByteArrayInputStream(pdfByte);
        BufferedInputStream bis = new BufferedInputStream(bais);

        FileOutputStream fos = new FileOutputStream(imageFile);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        byte[] buffer = new byte[1024];
        int length = bis.read(buffer);
        while (length != -1){
            bos.write(buffer,0,length);
            length = bis.read(buffer);
        }
        bos.flush();
//        pddocument.save(bos);
        bis.close();
        fos.close();
        bos.close();
    }


    /**
     * Description: 将base64编码内容转换为Pdf
     * @param  base64Content base64编码内容，文件的存储路径（含文件名）
     */
    public static void base64StringToPdf(String base64Content,String filePath) throws  Exception{
        BASE64Decoder decoder = new BASE64Decoder();
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            byte[] bytes = decoder.decodeBuffer(base64Content);//base64编码内容转换为字节数组
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
            bis = new BufferedInputStream(byteInputStream);
            File file = new File(filePath);
            File path = file.getParentFile();
            if(!path.exists()){
                path.mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while(length != -1){
                bos.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
//            closeStream(bis, fos, bos);
            bis.close();
            fos.close();
            bos.close();
        }
    }

    private static byte[] removeBase64Start(String base64) throws IOException {
//        int base64StartIndex = base64.indexOf(BASE64_START);
//        if (base64StartIndex != -1){
//            base64 = base64.substring(base64StartIndex + BASE64_START.length());
//        }
        return decoder.decodeBuffer(base64);
    }

}
