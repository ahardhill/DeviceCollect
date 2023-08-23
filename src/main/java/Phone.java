import java.util.Arrays;

public class Phone {
    public int phoneType;                   //1为Android 2为iOS
    public String productType =null;         //版本类
    public String productVersion =null;      //详细版本
    public String productModel =null;        //设备型号
    public String cpuBrand =null;            //CPU品牌
    public String cpuType =null;             //CPU型号
    public String cpuHz =null;               //CPU主频
    public String cpuCore =null;             //CPU核心数
    public String cpuAbi =null;              //CPU架构
    public String cpuHardWare =null;         //CPUHardware
    public String cpuAbiMore =null;         //CPU架构-详细
    public String abilistStr =null;        //支持架构
    public String ram =null;                  //RAM
    public String size =null;                 //分辨率
    public String rom =null;                  //ROM
    public String deviceType =null;          //设备类型  手机/平板
    public String deviceName =null;          //设备名
    public String gpuBrand =null;            //GPU品牌
    public String gpuRender =null;           //GPU渲染器
    public String gpuVersion =null;          //GPU版本
    public String mac =null;                  //MAC地址
    public String udid =null;                 //UDID
    public String netFormat =null;            //网络制式
    public String fullScreen =null;          //全面屏
    public String deviceBrand =null;         //品牌

    public String GPU_Version=null;         //OpenGL ES版本号

    public String GPU_Repo=null;            //GPU厂商

    public String GPU_Renderer=null;        // GPU渲染器

    public String GPU_Extensions=null;      // GPU扩展
    public String sn =null;

    public Phone(){}

    @Override
    public String toString() {
        return "Phone{" +
                "phoneType=" + phoneType +
                ", productType='" + productType + '\'' +
                ", productVersion='" + productVersion + '\'' +
                ", productModel='" + productModel + '\'' +
                ", cpuBrand='" + cpuBrand + '\'' +
                ", cpuType='" + cpuType + '\'' +
                ", cpuHz='" + cpuHz + '\'' +
                ", cpuCore='" + cpuCore + '\'' +
                ", cpuAbi='" + cpuAbi + '\'' +
                ", cpuHardWare='" + cpuHardWare + '\'' +
                ", cpuAbiMore='" + cpuAbiMore + '\'' +
                ", abilistStr='" + abilistStr + '\'' +
                ", ram='" + ram + '\'' +
                ", size='" + size + '\'' +
                ", rom='" + rom + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", gpuBrand='" + gpuBrand + '\'' +
                ", gpuRender='" + gpuRender + '\'' +
                ", gpuVersion='" + gpuVersion + '\'' +
                ", mac='" + mac + '\'' +
                ", udid='" + udid + '\'' +
                ", netFormat='" + netFormat + '\'' +
                ", fullScreen='" + fullScreen + '\'' +
                ", deviceBrand='" + deviceBrand + '\'' +
                ", GPU_Version='" + GPU_Version + '\'' +
                ", GPU_Repo='" + GPU_Repo + '\'' +
                ", GPU_Renderer='" + GPU_Renderer + '\'' +
                ", GPU_Extensions='" + GPU_Extensions + '\'' +
                ", sn='" + sn + '\'' +
                '}';
    }
}
