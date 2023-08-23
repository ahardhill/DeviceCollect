import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    private final static String excel2003L =".xls";    //2003- 版本的excel
    private final static String excel2007U =".xlsx";  //2007版本

    public ExcelUtils() {
    }


    public static Map<String,List<String>> getOtherData(String workbook, String sheetname) throws Exception {

        File file = new File(workbook);

        Workbook wb = Workbook.getWorkbook(file);
        Sheet sheet = wb.getSheet(sheetname);
        Map<String, List<String>> header = new HashMap();

        for(int row = 1; row < sheet.getRows(); ++row) {
            Cell cell=sheet.getCell(0,row);
            String key=cell.getContents();
            List<String> val=new LinkedList<String>();
            for(int col = 1; col < sheet.getColumns(); ++col) {
                StringBuffer sf=new StringBuffer(sheet.getCell(col,0).getContents());
                sf.append(':');
                String name = sheet.getCell(col, row).getContents();
                sf.append(name);
                val.add(sf.toString());
            }
            header.put(key,val);
        }
        wb.close();
        return header;
    }
}
