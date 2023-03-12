package id.tpenam.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import id.tpenam.dto.FileFormDTO;
import id.tpenam.model.Item;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ImportService {
    @Transactional
    public Response importExcel(FileFormDTO request) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.file);
        XSSFWorkbook workbook = new XSSFWorkbook(byteArrayInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        sheet.removeRow(sheet.getRow(0));

        List<Item> toPersist = new ArrayList<>();
        for (Row row : sheet) {
            Item item = new Item();
            item.name = row.getCell(0).getStringCellValue();
            item.count = (int) row.getCell(1).getNumericCellValue();
            item.price = (int) row.getCell(2).getNumericCellValue();
            item.type = row.getCell(3).getStringCellValue();
            item.description = row.getCell(4).getStringCellValue();
            toPersist.add(item);
        }
        Item.persist(toPersist);
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

    @Transactional
    public Response importCSV(FileFormDTO request) throws IOException, CsvValidationException {

        //create object array input

        File file = File.createTempFile("temp", "");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(request.file);

        CSVReader reader = new CSVReader(new FileReader(file));
        String[] nextLine;
        reader.skip(1);
        List<Item> toPersist = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Item item = new Item();
            item.name = nextLine[0].trim();
            item.count = nextLine.length;
            item.price = nextLine.length;
            item.type = nextLine[3].trim();
            item.description = nextLine[4].trim();
            toPersist.add(item);
        }
        Item.persist(toPersist);
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }
}
