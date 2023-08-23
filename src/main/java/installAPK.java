import java.io.IOException;

/**
 * @author Latitude_X
 * @date 2023/7/7 15:27
 */

/*
 * 下载apk————>启动apk
 *
 * */
public class installAPK {
    /**
     * 安装apk
     */
    public static void installAPK(String SN){

        try {
            String path="E:\\GPUTool\\app-debug.apk";    //apk相对路径

            // 构建启动APK的命令
            String[] command = {"adb", "-s",SN,"install", path};

            // 创建进程并执行命令
            Process process = new ProcessBuilder(command).start();

            // 等待进程执行完成
            process.waitFor();


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 启动apk
     */
    public static void startAPK(String SN){

        try {
            // 构建启动APK的命令
            String[] command = {"adb", "-s",SN,"shell", "am","start","-n","com.example.myapplication/.fun02.MainActivity"};

            // 创建进程并执行命令
            Process process = new ProcessBuilder(command).start();

            // 等待进程执行完成
            process.waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }


    }
}
