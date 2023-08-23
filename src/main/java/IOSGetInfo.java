import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class IOSGetInfo {

    public static Phone transInfo(String user, Phone phonetest) throws IOException {

        try {
            //拼接用户命令
            String[] cmd = new String[2];
            StringBuffer sf = new StringBuffer();

            //命令1
            String sf1 = "ideviceinfo -u ";
            sf1=sf1+user;
            cmd[0] = sf1.toString().substring(0,sf1.length());

            //命令2
            String sf2 = "ideviceinfo -q com.apple.disk_usage -u ";
            sf2=sf2+user;
            cmd[1] = sf2.toString().substring(0,sf2.length());


            //获取返回的信息
            String s1=ProgressUtils.getInfo(cmd[0]);
            String s2=ProgressUtils.getInfo(cmd[1]);

            sf.append(s1);
            sf.append(s2);

            phonetest.phoneType =2;

            phonetest.cpuAbi ="ARM64";

            phonetest.deviceBrand ="Apple";

            //读取信息赋值
            String[] arr = sf.toString().split("\r\n");
            Map<String, Object> map = new TreeMap<String, Object>();
            for (int i = 0; i < arr.length; i++) {
                String[] str = arr[i].split(" ");
                if (str.length >= 2) {
                    String key = str[0].substring(0, str[0].length());
                    String val = str[1];
                    map.put(key, val);
                } else {
                    map.put(str[0], null);
                }
            }
            if (map.containsKey("DeviceClass:")) {
                String deviceType = (String) map.get("DeviceClass:");
                if(deviceType == "iPhone"){
                    phonetest.deviceType = "手机";
                }else{
                    phonetest.deviceType = "平板";
                }
            }


            if (map.containsKey("ProductType:")) {
                phonetest.productType = (String) map.get("ProductType:");

                //根据ProductType确定手机型号ProductModel


                //根据ProductModel从Excel中找寻所需信息并通过反射赋值
                Class<?> cls = phonetest.getClass();
                String file = "IOS_info_Mapping.xls";
                Map<String, List<String>> header = ExcelUtils.getOtherData(file, "detail");
                if (header.containsKey(phonetest.productType)) {
                    List<String> list = header.get(phonetest.productType);
                    Iterator var = list.iterator();
                    while (var.hasNext()) {
                        String s = (String) var.next();
                        String[] array = s.split(":");
                        Field field = cls.getDeclaredField(array[0]);
                        if (array.length > 1) {
                            String content = array[1];
                            field.set(phonetest, content);
                        }

                    }
                }
            }

            if (map.containsKey("ProductVersion:")) {
                phonetest.productVersion = (String) map.get("ProductVersion:");
            }

            if (map.containsKey("DeviceName:")) {
                phonetest.deviceName = (String) map.get("DeviceName:");
            }

            if (map.containsKey("UniqueDeviceID:")) {
                phonetest.udid = (String) map.get("UniqueDeviceID:");
            }
            if (map.containsKey("WiFiAddress:")) {
                phonetest.mac = (String) map.get("WiFiAddress:");
            }

            if (map.containsKey("TotalDiskCapacity:")) {
                String ans = (String) map.get("TotalDiskCapacity:");
                ans = ans.substring(0, ans.length() - 9);
                ans = ans + 'G';
                phonetest.rom = ans;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return phonetest;
    }
}












