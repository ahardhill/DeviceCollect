import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author：你的友好邻居
 * @Package：PACKAGE_NAME
 * @Project：ADBShell
 * @name：getInformations
 * @Date：2023/6/19 17:20
 * @Filename：getInformations
 */
public class AndroidGetInfo {

    public static Phone transInfo(String SN,Phone phonetest) throws IOException, InterruptedException {
        phonetest.phoneType =1;
        phonetest.productVersion =getVersionSon(SN);
        phonetest.cpuHz =getCPUHz(SN);
        phonetest.cpuCore =getCPUNum(SN);
        phonetest.ram =getRAM(SN);
        phonetest.size =getSize(SN);
        phonetest.fullScreen =isFullScreen(SN);
        phonetest.rom =getROM(SN);
        phonetest.deviceType =getDeviceType(SN);
        phonetest.deviceBrand =getDevice_Brand(SN);
        phonetest.deviceName =getDevice_Name(SN);
        phonetest.productModel =getDevice_Model(SN);
        phonetest.cpuAbi =getCPU_Abi(SN);
        phonetest.cpuHardWare =getCPUHardware(SN)[0];
        phonetest.cpuBrand =getCPUHardware(SN)[1];
        phonetest.cpuType =getCPUHardware(SN)[2];
        phonetest.cpuAbiMore =getCPU_Abi_More(SN);
        phonetest.abilistStr =getAbilist_Str(SN);
        phonetest.mac =getMAC(SN);
        phonetest.GPU_Version = getGPU(SN).get(0);
        phonetest.GPU_Repo = getGPU(SN).get(1);
        phonetest.GPU_Renderer = getGPU(SN).get(2);
        phonetest.GPU_Extensions = getGPU(SN).get(3);

        return phonetest;
    }
    /**
     *
     * @return SN
     */
    /*public static String setSN() throws IOException, InterruptedException {
        String SN = Common.getMessage("adb devices").trim();
        String res=null;
        if(SN!=""){
            res=SN.split("\n")[1].split("\\s+")[0];
        }
        //System.out.println(SN);
        return res;
    }*/


    /**
     *
     * @return CPU子版本
     */
    public static String getVersionSon(String SN) throws IOException, InterruptedException {
        String command="adb -s "+SN+" shell getprop ro.build.version.release";
        String VersionSon = Common.getMessage(command).trim();
        //System.out.println(VersionSon);
        return VersionSon;
    }

    /**
     *
     * @return CPU主频
     */
    public static String getCPUHz(String SN) throws IOException, InterruptedException {

        String command="adb -s "+SN+" shell cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
        String CPUHz = Common.getMessage(command).trim();
        DecimalFormat df=new DecimalFormat("0.00");   //除法保留
        String res=null;
        if(CPUHz!=""){
            int number=Integer.parseInt(CPUHz);
            res=df.format((double)number/1000000);
        }
        res=res+"GHz";
        //System.out.println(res);
        return res;
    }

    /**
     *
     * @return CPU核心数
     */
    public static String getCPUNum(String SN) throws IOException, InterruptedException {

        String command="adb -s "+SN+" shell cat /sys/devices/system/cpu/present";
        String CPUNum=Common.getMessage(command).trim();
        String res=null;
        if(CPUNum!=""){
            int number=Character.getNumericValue(CPUNum.charAt(CPUNum.length()-1))+1;
            res=String.valueOf(number)+"核";
        }
        //System.out.println(res);
        return res;
    }

    /**
     *
     * @return RAM
     */
    public static  String getRAM(String SN) throws IOException, InterruptedException {

        String command="adb -s "+SN+" shell cat /proc/meminfo";
        String RAM=Common.getMessage(command).trim();  //待匹配字符串
        String regex="MemTotal:\\s+(\\d+)";    //匹配规则
        String res=null;

        Pattern pattern=Pattern.compile(regex);    //编译正则表达式

        Matcher matcher = pattern.matcher(RAM);    //创建Matcher

        if(matcher.find()){
            res=matcher.group(1);
            double num=Math.round(Double.valueOf(res)/(1024*1024));
            res=String.valueOf(num)+"G";
        }
        //System.out.println(res);
        return res;
    }

    /**
     *
     * @return 分辨率
     */
    public static String getSize(String SN) throws IOException, InterruptedException {
        String command="adb -s "+SN+" shell wm size";
        String Size=Common.getMessage(command).trim();  //待匹配字符串
        String res=null;

        if(Size!=""){
            res=Size.split(":")[1].trim();
        }
        //System.out.println(res);
        return res;
    }

    /**
     *
     * @return 全面屏
     */
    public static String isFullScreen(String SN) throws IOException, InterruptedException {

        String command="adb -s "+SN+" shell wm size";
        String Size=Common.getMessage(command).trim().split("\n")[0].trim();  //待匹配字符串
        String res=null;
        if(Size!=""){
            res=Size.split(":")[1].trim();
            Double a=Double.valueOf(res.split("x")[0]);
            Double b=Double.valueOf(res.split("x")[1]);
            if(Math.round(a/b)>=2||Math.round(b/a)>=2){
                res="是";
            }else {
                res="否";
            }
        }
        //System.out.println(res);
        return res;
    }

    /**
     *
     * @return ROM
     */
    public static String getROM(String SN) throws IOException, InterruptedException {

        String command="adb -s "+SN+" shell df -h /data";
        String ROM=Common.getMessage(command).trim();  //待匹配字符串
        String res=null;

        if(ROM!=""){
            String[] split = ROM.split("\n");
            String[] split1 = split[split.length - 1].split("\\s+");
            res=split1[1];  //匹配内存大小，不完整
            String regex="\\d+\\.?\\d*";    //匹配数字正则表达式
            Pattern compile = Pattern.compile(regex);
            Matcher matcher = compile.matcher(res);  //正则匹配
            if(matcher.find()){
                Double num=Double.valueOf(matcher.group(0));
                if(num<=16){
                    num=16.0;
                }else if(num>16&&num<=32){
                    num=32.0;
                }else if(num>32&&num<=64){
                    num=64.0;
                }else if(num>64&&num<=128){
                    num=128.0;
                }else if(num>128&&num<=256){
                    num=256.0;
                }else if(num>256&&num<=512){
                    num=512.0;
                }else if(num>512&&num<=1024){
                    num=1024.0;
                }
                res=String.valueOf(num)+"G";
            }
        }
        //System.out.println(res);
        return res;
    }

    /**
     *
     * @return 设备类型
     */
    public static String getDeviceType(String SN) throws IOException, InterruptedException {

        String command01="adb -s "+SN+" shell wm size";
        String command02="adb -s "+SN+" shell wm density";
        String Size=Common.getMessage(command01).trim().split("\n")[0].trim();  //分辨率
        String Density=Common.getMessage(command02).trim().split("\n")[0].trim();  //像素密度
        String res="无法判断设备类型";
        //System.out.println(res);
        if(Size!=""&&Density!=""){

            String Den=Density.split(":")[1].trim();
            Double D = Double.valueOf(Den);    //像素密度


            String S=Size.split(":")[1].trim();
            Double a=Double.valueOf(S.split("x")[0])/D;    //长/宽  英寸
            Double b=Double.valueOf(S.split("x")[1])/D;    //长/宽  英寸

            Double x=Math.pow(a,2);
            Double y=Math.pow(b,2);

            Double Inches=Math.sqrt(x+y);    //设备尺寸   英寸
            if(Inches>=7.5){
                res="平板";
            }else {
                res="手机";
            }
            //System.out.println(Inches);
        }

        return res;
    }

    /**
     *
     * @return 品牌
     */
    public static String getDevice_Brand(String SN) throws IOException, InterruptedException {
        String command="adb -s "+SN+" shell getprop ro.product.brand";
        String Device_Brand=Common.getMessage(command).trim();  //待匹配字符串
        //System.out.println(Device_Brand);
        return Device_Brand;
    }

    /**
     *
     * @return 设备名
     */
    public static String getDevice_Name(String SN) throws IOException, InterruptedException {
        String command="adb -s "+SN+" shell getprop ro.product.name";
        String Device_Name=Common.getMessage(command).trim();  //待匹配字符串
        //System.out.println(Device_Name);
        return Device_Name;
    }

    /**
     *
     * @return 设备型号
     */
    public static String getDevice_Model(String SN) throws IOException, InterruptedException {
        String command="adb -s "+SN+" shell getprop ro.product.model";
        String Device_Model=Common.getMessage(command).trim();  //待匹配字符串
        //System.out.println(Device_Model);
        return Device_Model;
    }

    /**
     *
     * @return CPU架构
     */
    public static String getCPU_Abi(String SN)throws IOException, InterruptedException{
        String command="adb -s "+SN+" shell getprop ro.product.cpu.abi";
        String CPU_Abi=Common.getMessage(command).trim();  //待匹配字符串
        //System.out.println(CPU_Abi);
        return CPU_Abi;
    }

    /**
     *
     * @return CPUHardware_0 CPU品牌_1 CPU型号_2
     */
    public static String[] getCPUHardware(String SN)throws IOException, InterruptedException{
        String command="adb -s "+SN+" shell cat /proc/cpuinfo";
        String CPU_Abi=Common.getMessage(command).trim();  //待匹配字符串
        String regex="Hardware\\s*:\\s*(.*)";   //正则
        String[] res=new String[3];

        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(CPU_Abi);

        if(matcher.find()){
            String group = matcher.group(1);
            String[] split = group.split("\\s+");
            res[0] = group;
            res[1] = split[0];
            res[2] = split[split.length-1];
        }
        for (String re : res) {
            //System.out.println(re);
        }
        return res;
    }

    /**
     *
     * @return CPU架构
     */
    public static String getCPU_Abi_More(String SN)throws IOException, InterruptedException{
        String command="adb -s "+SN+" shell cat /proc/cpuinfo";
        String CPU_Abi_More=Common.getMessage(command).trim();  //待匹配字符串
        CPU_Abi_More=CPU_Abi_More.split("\n")[0].split(":")[1].trim();
        String res=null;
        if(!CPU_Abi_More.equals("0")){
            res=CPU_Abi_More;
        }
        //System.out.println(CPU_Abi_More);
        return res;
    }

    /**
     *
     * @return 支持架构
     */
    public static String getAbilist_Str(String SN)throws IOException, InterruptedException{
        String command="adb -s "+SN+" shell getprop ro.product.cpu.abilist";
        String Abilist_Str=Common.getMessage(command).trim();  //待匹配字符串
//        String[] res = Abilist_Str.split(",");
//
//        for (String re : res) {
//            //System.out.println(re);
//        }
        return Abilist_Str;

    }


    /**
     *
     * @return MAC
     */
    public static String getMAC(String SN)throws IOException, InterruptedException{
        String command="adb -s "+SN+" shell ip addr show wlan0";
        String MAC=Common.getMessage(command).trim();  //待匹配字符串
        String regex="link/ether\\s+([^\\s]+)";
        String res=null;

        Pattern pattern=Pattern.compile(regex);
        Matcher matcher = pattern.matcher(MAC);
        if(matcher.find()){
            res=matcher.group(1).trim();
        }
        //System.out.println(res);
        return res;
    }

    /**
     *
     * @return GPU
     */
    public static HashMap<Integer,String> getGPU(String SN)throws IOException, InterruptedException {
        installAPK.installAPK(SN);   //安装apk

        installAPK.startAPK(SN);    //启动apk

        HashMap<Integer,String> res=new HashMap<>();
        String command="adb -s "+SN+ " logcat -s" +  " ES01";
        String GPUMess = commandGPU.getMessage(command).trim();

        //System.out.println(GPUMess);

        String[] split = GPUMess.split("\n");
        int GPUNum=0;
        for (int i = 0; i < split.length; i++) {
            if (!split[i].contains("beginning of")) {
                String[] sb = split[i].split(":");
                res.put(GPUNum,sb[sb.length-1]);
                GPUNum++;
            }
        }
        return res;
    }
}
