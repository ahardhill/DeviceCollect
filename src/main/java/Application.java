import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;


public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {

        while(true){
            HashSet<String> IOS_Set=new HashSet<String>();
            HashSet<String> Android_Set=new HashSet<String>();
            List<Phone> list = new ArrayList<>();
            Date before=new Date();
            Date now;
            long t=0;
            while(t<=10){                    //600s清空重新循环set
                String cmd1="idevice_id -l";
                String s1= ProgressUtils.getInfo(cmd1);
                if(s1.length()!=0){
                    String[] arr1=s1.split("\r\n");
                    for(int i=0;i<arr1.length;i++){
                        if(!IOS_Set.contains(arr1[i])){
                            System.out.println("IOS设备");
                            System.out.println(s1.toString());
                            IOS_Set.add(arr1[i]);
                            Phone phoneInfo=new Phone();
                            phoneInfo= IOSGetInfo.transInfo(arr1[i],phoneInfo);
                            System.out.println(phoneInfo);
                            list.add(phoneInfo);
                        }
                    }
                }
                String s2 = Common.getMessage("adb devices").trim();
                String[] arr2=s2.split("\n");
                if(arr2.length>1){
                    for(int i=1;i<arr2.length;i++) {
                        String deviceNum = arr2[i].split("\\s+")[0];   //SN
                        String hasPower = arr2[i].split("\\s+")[1];   //判断是否有权利

                        if (hasPower.equals("unauthorized")) {
                            continue;
                        }
                        if (!Android_Set.contains(deviceNum)) {
                            System.out.println("Android设备");
                            System.out.println(deviceNum);
                            Android_Set.add(deviceNum);
                            Phone phoneInfo = new Phone();
                            phoneInfo.sn =deviceNum;
                            phoneInfo = AndroidGetInfo.transInfo(deviceNum,phoneInfo);
                            System.out.println(phoneInfo);
                            list.add(phoneInfo);
                        }
                    }
                }
                now = new Date();
                t = (now.getTime() - before.getTime())/1000;
            }
            for(Phone phone : list){
                ObjectMapper obj = new ObjectMapper();
                String sendMsgParamsVoStr = obj.writeValueAsString(phone);
                try {
                    Map<String, Object> response = PostPhoneInfo.doPost(sendMsgParamsVoStr);
                    int code = (int) response.get("status");
                    if (code != 200) {
                        System.out.println("response code: " + code);
                    }
                    String result = (String) response.get("message");
                    System.out.println("response: " + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }
}

