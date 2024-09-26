package Helper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.javatpoint.model.Product;

public class Helper {
	
	public static boolean checkExcelFormat(MultipartFile file) {
		
		String contentType=file.getContentType();
		if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static List<Product>convertToListExcelOfProduct(InputStream is){
		
		List<Product> list =new ArrayList<>();
		
		try {
			XSSFWorkbook workbook=new XSSFWorkbook(is);
			XSSFSheet sheet=workbook.getSheet("data");
			
			int rowNumber=0;
			Iterator<Row> iterator =sheet.iterator();
			
			while(iterator.hasNext()) {
				Row row=iterator.next();
				
				if(rowNumber==0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cells=row.iterator();
				int cid=0;
				
				Product p=new Product();
				while(cells.hasNext()) {
					
					Cell cell=cells.next();
					switch (cid) {
						case 0:
							p.setProductId((int)cell.getNumericCellValue());
							break;
						case 1:
							p.setProductName(cell.getStringCellValue());
							break;
						case 2:
							p.setProductPrice(cell.getStringCellValue());
							break;
						case 3:
							p.setProductDesc(cell.getStringCellValue());
							break;
						default:
							break;
								
					
					}
					cid++;
				}
				list.add(p);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
		
	}

}
