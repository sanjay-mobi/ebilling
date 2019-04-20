package com.ebhumi.nayo.ebiiling.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

@Controller
public class InvoiceController  {

    @Autowired
    private PdfUtility pdfUtility;
    @RequestMapping (method = RequestMethod.POST, path = "/generateInvoice")
    public String generateInvoice(@RequestBody List<InvoiceProduct> invoiceProduct)throws IOException, DocumentException {

        //process and create a pdf here.
        System.out.println(invoiceProduct);
        pdfUtility.generatePdf(invoiceProduct, 5,6); // 6 means generate invoice for Jabong
        return "";
    }
}
