package com.ebhumi.nayo.ebiiling.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InvoiceController  {

    @Autowired
    private PdfUtility pdfUtility;
    @RequestMapping (method = RequestMethod.POST, path = "/generateInvoice")
    public ResponseEntity<Map<String,String>> generateInvoice(@RequestBody List<InvoiceProduct> invoiceProduct,@RequestParam int companyId, @RequestParam int partyCompanyId )throws IOException, DocumentException {

        //process and create a pdf here.
        System.out.println(invoiceProduct);
        //TODO -- remove hard coded suplier companyId
        String filename =  pdfUtility.generatePdf(invoiceProduct, 1,partyCompanyId); // 6 means generate invoice for Jabong
        File file = new File(filename);
        Map<String,String> map = new HashMap<>();
        map.put("filename",file.getName());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
    @RequestMapping (method = RequestMethod.GET, path = "/getPdf")
    public void getPdf(HttpServletRequest request, HttpServletResponse response, @RequestParam String fileName) {


        String dataDirectory = request.getServletContext().getContextPath();
        Path file = Paths.get(dataDirectory, fileName);
        if (Files.exists(file))
        {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
