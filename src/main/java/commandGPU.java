import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Latitude_X
 * @date 2023/7/11 10:51
 */
public class commandGPU {
    public static String getMessage(String command){
        String RESULT = null;
        try {
            // 创建ProcessBuilder对象，并指定要执行的CMD命令
            ProcessBuilder processBuilder1 = new ProcessBuilder("cmd.exe", "/c", command);
            // 设置工作目录（可选）
            //processBuilder.directory("C:\\"); // 将工作目录设置为C盘根目录

            // 启动进程并执行CMD命令
            Process process = processBuilder1.start();
            // 读取CMD命令的输出
            String line;
            StringBuffer sb=new StringBuffer();  //暂存输出字符串并且处理
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // 定义正则表达式匹配ENDPOS字段
            String pattern = "\\bENDPOS\\b";
            Pattern endposPattern = Pattern.compile(pattern);

            while((line=reader.readLine())!=null){
                // 使用正则表达式匹配ENDPOS字段
                Matcher matcher = endposPattern.matcher(line);
                if (matcher.find()) {
                    break;
                }

                sb.append(line).append("\n");
                //System.out.println(line);
            }
            reader.close();
            process.destroy();

            // 等待命令执行完成并获取返回值
            RESULT=sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return RESULT;

    }
}
