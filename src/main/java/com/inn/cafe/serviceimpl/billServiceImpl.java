package com.inn.cafe.serviceimpl;
import com.inn.cafe.POJO.product;
import com.inn.cafe.constants.userConstants;
import com.inn.cafe.service.billService;
import com.inn.cafe.utils.cafeUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.inn.cafe.constants.userConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.inn.cafe.POJO.bill;
import com.inn.cafe.JWT.JWTfilter;
import com.inn.cafe.dao.billDao;

@Slf4j

@Service
public class billServiceImpl implements billService{
    @Autowired
    JWTfilter JWTfilter;

    @Autowired
    billDao billDao;



    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
           String filename;
           if(validateRequestMap(requestMap))
           {
               if(requestMap.containsKey("isGenerate")&& !(Boolean) requestMap.get("isGenerate"))
               {
                   filename=(String) requestMap.get("uuid");
               }else {
                   filename= cafeUtils.getUuid();
                   requestMap.put("uuid",filename);
                   insertBill(requestMap);
               }
               String data = "Name: "+requestMap.get("name") + "\n"+
                       "Contact Number: "+ requestMap.get("contactnumber")+
                       "\n" + "Email: " + requestMap.get("email") + "\n" +
                       "Payment method: " + requestMap.get("paymentmetnod");

               Document document=new Document();
               PdfWriter.getInstance( document,new FileOutputStream(userConstants.STORE_LOCATION+"//"+filename+".pdf"));
               document.open();
               setRectangleInPdf(document);
               Paragraph chunk = new Paragraph("Cafe Management System",getFront("Header"));
               document.add(chunk);
               Paragraph paragraph = new Paragraph(data+"\n \n", getFront("Data"));
               document.add(paragraph);

               PdfPTable table = new PdfPTable(5);
               table.setWidthPercentage(100);
                  addTableHeader(table);
               JSONArray jsonArray =cafeUtils.getJsonArrayString((String) requestMap.get("productdetails"));
               for(int i=0;i<jsonArray.length();i++)
               {
                   addRows(table,cafeUtils.getMapFromJson(jsonArray.getString(i)));
               }
               document.add(table);
               Paragraph footer = new Paragraph("total :"+requestMap.get("total")+"\n"+"" +
                       "Thank you for visiting.Please visit again!",getFront("Data"));
               document.add(footer);
               document.close();
               return new ResponseEntity<>("uuid :"+" "+filename,HttpStatus.OK);


           }
       return cafeUtils.getResponseEntity("required data not found", HttpStatus.BAD_REQUEST);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<bill>> getbills() {
       List<bill> list=new ArrayList<>();
       if(JWTfilter.isAdmin()){
           list = billDao.getAllBills();
        }else
       {  list=billDao.getCurrentUserBill("ghada");}
       return new ResponseEntity<>(list,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<byte[]> getpdf(Map<String,Object> requestMap) {
       try{
           byte[] byteArray= new byte[0];
           if(!requestMap.containsKey("uuid") && validateRequestMap(requestMap))
               return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST);
               String filepath = userConstants.STORE_LOCATION+"/"+(String) requestMap.get("uuid")+".pdf";
             if(cafeUtils.isFileExist(filepath))
             {
                 byteArray=getbytearray(filepath);
                 return new ResponseEntity<>(byteArray,HttpStatus.OK);
             }
           else{
               requestMap.put("isGenerate",false);
               generateReport(requestMap);
               byteArray=getbytearray(filepath);
                 return new ResponseEntity<>(byteArray,HttpStatus.OK);

             }
       }catch (Exception ex){ex.printStackTrace();}
        return null;}

    @Override
    public ResponseEntity<String> deleteBills(Integer id) {
        if (JWTfilter.isAdmin()) {
            Optional<bill> optional = billDao.findById(id);
            if (!optional.isEmpty()) { billDao.deleteById(id);
                return cafeUtils.getResponseEntity("product deleted successfully", HttpStatus.OK);

            }
            else
                return cafeUtils.getResponseEntity("product not found", HttpStatus.BAD_REQUEST);

        }else {
            return cafeUtils.getResponseEntity(userConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

        }
    }


    private byte[] getbytearray(String filepath) throws Exception  {
        File intialfile=new File(filepath);
        InputStream targetStream = new FileInputStream((intialfile));
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray  ;

    }


    private void addRows(PdfPTable table, Map<String, Object> data) {

        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));


    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Name","Category","Quantity","Price","Total")
                .forEach(columnTitle ->{
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header)   ;        });
    }

    private Font getFront(String type) {
       switch (type){
           case "Header":
               Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
               headerFont.setStyle(Font.BOLD);
               return headerFont;
           case "Data":
               Font DataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
               DataFont.setStyle(Font.BOLD);
               return DataFont;
           default:
               return new Font();

       }
    }

    private void setRectangleInPdf(Document document) {
        Rectangle rect=new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(3);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);

        try {
            document.add(rect);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertBill(Map<String, Object> requestMap) {
        try {
            bill bill = new bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setContactnumber((String) requestMap.get("contactnumber"));
            bill.setPaymentmethod((String) requestMap.get("paymentmethod"));
            bill.setTotal(String.valueOf(Integer.parseInt((String) requestMap.get("total"))));
            bill.setProductdetails((String) requestMap.get("productdetails"));
            bill.setCreatedby((String) requestMap.get("createdby"));

            billDao.save(bill);
        }
        catch(Exception ex)
        { ex.printStackTrace();}
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {

        return requestMap.containsKey("name")&&
                requestMap.containsKey("contactnumber")
                &&requestMap.containsKey("email")
                && requestMap.containsKey("paymentmethod")
             && requestMap.containsKey("productdetails")
             && requestMap.containsKey("total");


    }
}
