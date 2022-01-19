package com.rahul.codmloadoutstats.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {

    public static final Logger logger = LogManager.getLogger(TestController.class);

    @GetMapping("/")
    public ResponseEntity<String> welcome(){
        JSONObject obj = new JSONObject();
        obj.put("message","This is welcome page Test !!! Rahul");
        return ResponseEntity.ok().body(obj.toString());
    }

    @GetMapping("/apiHealth")
    public ResponseEntity<String> apiHealth(){
        JSONObject object = new JSONObject();
        object.put("message","API is Up and Running!!");
        object.put("timestamp",new Date());
        return ResponseEntity.ok().body(object.toString());
    }

    @GetMapping("/echoHeader")
    public ResponseEntity<String> echo(@RequestHeader MultiValueMap<String, String> headers){
        JSONObject resp = new JSONObject();
        headers.forEach((k,v) -> resp.put(k,v));
        return ResponseEntity.ok().body(resp.toString());
    }

    @PostMapping(value = "/buildMap", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> buildMap(@RequestPart("file") MultipartFile file, @RequestParam("keyIdx") int keyIdx, @RequestParam("valIdx") int valIdx,  @RequestParam("sheetIdx") int sheetIdx) throws IOException {

        logger.info(String.format(" File Name : [%s], File Size : [%s]", file.getName(),file.getSize()));
        InputStream inputStream = file.getInputStream();

        logger.info(String.format("KeyIdx [%s], ValIdx [%s] & sheetIdx [%s]", keyIdx,valIdx,sheetIdx));

        JSONObject object = buildMapFromExcel(inputStream, keyIdx, valIdx, sheetIdx);

        if(object.has("status")){
            return ResponseEntity.badRequest().body(object.toString());
        }else {
            return ResponseEntity.ok().body(object.toString());
        }

    }

    static JSONObject buildMapFromExcel(InputStream inputStream,int col1, int col2,int sheetIdx){

        try{
            JSONObject result = new JSONObject();

            Workbook excelWorkBook = new XSSFWorkbook(inputStream);

            Sheet sheet = excelWorkBook.getSheetAt(sheetIdx);
            logger.info("Sheet Name : " + sheet.getSheetName());

            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();


            for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                Cell key = row.getCell(col1);
                key.setCellType(CellType.STRING);
                Cell val = row.getCell(col2);
                val.setCellType(CellType.STRING);
                if(result.has(key.getStringCellValue().trim())){
                    result.getJSONArray(key.getStringCellValue().trim()).put(val.getStringCellValue().trim());
                }else {
                    result.put(key.getStringCellValue().trim(),new JSONArray().put(val.getStringCellValue().trim()));
                }
            }
            return result;
        }catch (Exception e){
            logger.error("Exception Occured : " + e.getMessage());
            e.printStackTrace();
            return new JSONObject().put("status","failed").put("message","Exception Occurred : " + e.getMessage());
        }
    }
}
