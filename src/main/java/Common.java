import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author：你的友好邻居
 * @Package：PACKAGE_NAME
 * @Project：ADBShell
 * @name：Common
 * @Date：2023/6/19 17:20
 * @Filename：Common
 */
public class Common {
    public static String getMessage(String command)throws IOException, InterruptedException{
        String RESULT = null;

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
            while((line=reader.readLine())!=null){
                sb.append(line).append("\n");
            }
            process.waitFor();
            // 等待命令执行完成并获取返回值
            RESULT=sb.toString();

        return RESULT;

    }
}
