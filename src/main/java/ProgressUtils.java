import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProgressUtils {
    public static String getInfo(String cmd) throws IOException, InterruptedException {


        Process process = Runtime.getRuntime().exec(cmd);
        //读取输入流
        InputStream inputStream = process.getInputStream();
        //将字节流转成字符流
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        //字符缓冲区
        char[] c = new char[1024];
        int len = -1;
        StringBuffer sf=new StringBuffer();
        while ((len = inputStreamReader.read(c)) != -1) {
            String s = new String(c, 0, len);
            sf.append(s);
        }
        inputStream.close();
        inputStreamReader.close();
        process.waitFor();
        return sf.toString();
    }
}
