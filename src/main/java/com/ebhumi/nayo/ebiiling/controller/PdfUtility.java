package com.ebhumi.nayo.ebiiling.controller;

import com.ebhumi.nayo.ebiiling.dao.Address;
import com.ebhumi.nayo.ebiiling.dao.CompanyMaster;
import com.ebhumi.nayo.ebiiling.repository.CompanyMasterRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class PdfUtility {

    @Autowired
    private CompanyMasterRepository companyMasterRepository;

    private DecimalFormat df = new DecimalFormat("#.00");
    @Transactional
    public String generatePdf(List<InvoiceProduct> productList, int suplierCompanyId, int partyCompanyId) throws IOException, DocumentException {

        CompanyMaster partyCompanyMaster = companyMasterRepository.findOne(partyCompanyId); // party company
        CompanyMaster bhumiCompanyMaster = companyMasterRepository.findOne(suplierCompanyId);
        Set<Address> addressList =  bhumiCompanyMaster.getAddresses();
        Address bhumiRegisteredAddress=null,bhumiBranchWarehouseAddress=null,partyBillingAddress=null,partyShippinAddress=null;
        String bhumiGstNumber = bhumiCompanyMaster.getGstNumber();
        if(bhumiGstNumber == null || !bhumiCompanyMaster.isGstApplicable()){
            bhumiGstNumber="";
        }
        String partyGstNumber = partyCompanyMaster.getGstNumber();
        if(partyGstNumber == null || !partyCompanyMaster.isGstApplicable()){
            partyGstNumber = "";
        }
        String buyerOrderNo = productList.get(0).getBuyerOrderNumber();
        String buyerOrderDate =productList.get(0).getBuyerOrderDate();
        String  invoiceIssueDate = productList.get(0).getDateOfInvoice();
        String invoiceNumber = bhumiCompanyMaster.getInvoiceNumberPrefix() + bhumiCompanyMaster.getInvoiceCounter();
        bhumiCompanyMaster.setInvoiceCounter(bhumiCompanyMaster.getInvoiceCounter()+1);
        companyMasterRepository.save(bhumiCompanyMaster);


        Iterator<Address> addressIterator = addressList.iterator();
        while (addressIterator.hasNext()){
            Address address = addressIterator.next();
            if(address.getAddressType().equalsIgnoreCase("shipping")){
                bhumiRegisteredAddress = address;
            }
            if(address.getAddressType().equalsIgnoreCase("billing")){
                bhumiBranchWarehouseAddress = address;
            }
        }

        addressList =  partyCompanyMaster.getAddresses();
        addressIterator = addressList.iterator();
        while (addressIterator.hasNext()){
            Address address = addressIterator.next();
            if(address.getAddressType().equalsIgnoreCase("shipping")){
                partyShippinAddress = address;
            }
            if(address.getAddressType().equalsIgnoreCase("billing")){
                partyBillingAddress = address;
            }
        }

        Font bold = new Font(Font.FontFamily.HELVETICA  , 15, Font.BOLD);
        Font bold2 = new Font(Font.FontFamily.HELVETICA  , 12, Font.BOLD);
       // Font font2 = new Font(Font.FontFamily.COURIER    , 15);
        Font font = new Font(Font.FontFamily.HELVETICA, 8);

        final Document document = new Document();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        String fileName =  invoiceNumber +".pdf" ;
        fileName = fileName.replace("/" ,"_");

        File file = new File(fileName);

       // file.getParentFile().mkdirs();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));

        Rectangle one = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        document.setPageSize(one);
        document.setMargins(15, 15, 30, 30);
        document.open();
        document.setPageSize(one);

        // bhumi address Table -------- start
        PdfPTable bhumiRegisteredAddressTable  = new PdfPTable(2);
        bhumiRegisteredAddressTable.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        bhumiRegisteredAddressTable.setWidthPercentage(100);
        bhumiRegisteredAddressTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        bhumiRegisteredAddressTable.setWidths(new int[]{1, 1});
       // bhumiRegisteredAddressTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPTable registeredAddressTable = new PdfPTable(1);
        registeredAddressTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        registeredAddressTable.addCell(new Phrase(bhumiRegisteredAddress.getAddress1(),font));
        registeredAddressTable.addCell(new Phrase(bhumiRegisteredAddress.getAddress2(),font));
        registeredAddressTable.addCell(new Phrase(bhumiRegisteredAddress.getCity()
                +", "+bhumiRegisteredAddress.getState()
                +", IN - "+bhumiRegisteredAddress.getPinCode(),font));
        registeredAddressTable.addCell(new Phrase("GSTIN No."+ bhumiGstNumber,font));


        PdfPTable warehouseAddressTable = new PdfPTable(1);
        warehouseAddressTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        warehouseAddressTable.addCell(new Phrase(bhumiBranchWarehouseAddress.getAddress1(),font));
        warehouseAddressTable.addCell(new Phrase(bhumiBranchWarehouseAddress.getAddress2(),font));
        warehouseAddressTable.addCell(new Phrase(bhumiBranchWarehouseAddress.getCity()
                +", "+bhumiBranchWarehouseAddress.getState()
                +", IN - "+bhumiBranchWarehouseAddress.getPinCode(),font));


        PdfPCell cell = new PdfPCell();
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(bhumiCompanyMaster.getName(),bold));

        bhumiRegisteredAddressTable.addCell(cell);
        bhumiRegisteredAddressTable.addCell(new Phrase("Registered Address :-",font));
        bhumiRegisteredAddressTable.addCell(new Phrase("Branch Cum Warehouse:-",font));
        bhumiRegisteredAddressTable.addCell(registeredAddressTable);
        bhumiRegisteredAddressTable.addCell(warehouseAddressTable);

        // bhumi address Table -------- end


        PdfPTable invoiceDetailsTable = new PdfPTable(2);
       // invoiceDetailsTable.getDefaultCell().setBorder(Rectangle.TOP);
        invoiceDetailsTable.setWidthPercentage(100);
        invoiceDetailsTable.setWidths(new int[]{1,1});
        invoiceDetailsTable.addCell(new Phrase("Invoice No:- ",bold2));
        invoiceDetailsTable.addCell(new Phrase(invoiceNumber,font));
        invoiceDetailsTable.addCell(new Phrase("Date of Issue of Invoice:",font));
        invoiceDetailsTable.addCell(new Phrase(invoiceIssueDate,font));
        PdfPCell cell14 = new PdfPCell();
        cell14.setColspan(2);
        cell14.setPhrase(new Phrase(" "));
        invoiceDetailsTable.addCell(cell14);
        invoiceDetailsTable.addCell(new Phrase("Buyer Order No.",font));
        invoiceDetailsTable.addCell(new Phrase(buyerOrderNo,font));
        invoiceDetailsTable.addCell(new Phrase("Order Date :",font));
        invoiceDetailsTable.addCell(new Phrase(buyerOrderDate,font));
        invoiceDetailsTable.addCell(new Phrase(""));
        invoiceDetailsTable.addCell(new Phrase(""));



        PdfPTable partyRegisteredAddressTable  = new PdfPTable(2);
        partyRegisteredAddressTable.setWidthPercentage(100);
        partyRegisteredAddressTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        partyRegisteredAddressTable.setWidths(new int[]{1, 1});
        // bhumiRegisteredAddressTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPTable billingAddressTable = new PdfPTable(1);
        billingAddressTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        billingAddressTable.addCell(new Phrase(partyBillingAddress.getAddress1(),font));
        billingAddressTable.addCell(new Phrase(partyBillingAddress.getAddress2(),font));
        billingAddressTable.addCell(new Phrase(partyBillingAddress.getCity()
                +", "+partyBillingAddress.getState()
                +", IN - "+partyBillingAddress.getPinCode(),font));
        billingAddressTable.addCell(new Phrase("GSTIN:"+ partyGstNumber,font));


        PdfPTable shippingAddressTable = new PdfPTable(1);
        shippingAddressTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        shippingAddressTable.addCell(new Phrase(partyShippinAddress.getAddress1(),font));
        shippingAddressTable.addCell(new Phrase(partyShippinAddress.getAddress2(),font));
        shippingAddressTable.addCell(new Phrase(partyShippinAddress.getCity()
                +", "+partyShippinAddress.getState()
                +", IN - "+partyShippinAddress.getPinCode(),font));
        shippingAddressTable.addCell(new Phrase("GSTIN:"+ partyGstNumber,font));

        partyRegisteredAddressTable.addCell(new Phrase("BILLING ADDRESS",font));
        partyRegisteredAddressTable.addCell(new Phrase("SHIPPING ADDRESS",font));
        partyRegisteredAddressTable.addCell(billingAddressTable);
        partyRegisteredAddressTable.addCell(shippingAddressTable);


        // product data table
        PdfPTable productTable = new PdfPTable(14);
        productTable.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        productTable.setWidthPercentage(100);
        productTable.setWidths(new float[]{0.8f,2.5f,2.5f,1,2,2,2,1,2,1,2,1,2,2});

        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell.setPhrase(new Phrase("S.N.",font));
        pdfPCell.setRowspan(2);
        productTable.addCell(pdfPCell);


        PdfPCell pdfPCell1 = new PdfPCell();
        pdfPCell1.setRowspan(2);
        pdfPCell1.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell1.setPhrase(new Phrase("Product Description",font));
        productTable.addCell(pdfPCell1);

        PdfPCell pdfPCell2 = new PdfPCell();
        pdfPCell2.setRowspan(2);
        pdfPCell2.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell2.setPhrase(new Phrase("HSN/SAC Code",font));
        productTable.addCell(pdfPCell2);

        PdfPCell pdfPCell3 = new PdfPCell();
        pdfPCell3.setRowspan(2);
        pdfPCell3.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell3.setPhrase(new Phrase("QTY",font));
        productTable.addCell(pdfPCell3);

        PdfPCell pdfPCell4 = new PdfPCell();
        pdfPCell4.setRowspan(2);
        pdfPCell4.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell4.setPhrase(new Phrase("Rate/Pce",font));
        productTable.addCell(pdfPCell4);

        PdfPCell pdfPCell5 = new PdfPCell();
        pdfPCell5.setRowspan(2);
        pdfPCell5.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell5.setPhrase(new Phrase("TOTAL SALE",font));
        productTable.addCell(pdfPCell5);

        PdfPCell pdfPCell6 = new PdfPCell();
        pdfPCell6.setRowspan(2);
        pdfPCell6.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell6.setPhrase(new Phrase("TAXABLE VALUE",font));
        productTable.addCell(pdfPCell6);

        PdfPCell pdfPCell7 = new PdfPCell();
        pdfPCell7.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell7.setColspan(2);
        pdfPCell7.setPhrase(new Phrase("CGST",font));
        productTable.addCell(pdfPCell7);

        PdfPCell pdfPCell8 = new PdfPCell();
        pdfPCell8.setColspan(2);
        pdfPCell8.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell8.setPhrase(new Phrase("SGST",font));
        productTable.addCell(pdfPCell8);


        PdfPCell pdfPCell9 = new PdfPCell();
        pdfPCell9.setColspan(2);
        pdfPCell9.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell9.setPhrase(new Phrase("IGST.",font));

        productTable.addCell(pdfPCell9);

        PdfPCell pdfPCell10 = new PdfPCell();
        pdfPCell10.setRowspan(2);
        pdfPCell10.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell10.setPhrase(new Phrase("Amount",font));
        productTable.addCell(pdfPCell10);


        PdfPCell pdfPCell11 = new PdfPCell();
        pdfPCell11.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        pdfPCell11.setPhrase(new Phrase("%",font));
        productTable.addCell(pdfPCell11);

        PdfPCell pdfPCell12 = new PdfPCell();
        pdfPCell12.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell12.setPhrase(new Phrase("AMOUNT",font));
        productTable.addCell(pdfPCell12);

        PdfPCell pdfPCell13 = new PdfPCell();
        pdfPCell13.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        pdfPCell13.setPhrase(new Phrase("%",font));
        productTable.addCell(pdfPCell13);

        PdfPCell pdfPCell14 = new PdfPCell();
        pdfPCell14.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell14.setPhrase(new Phrase("AMOUNT",font));
        productTable.addCell(pdfPCell14);

        PdfPCell pdfPCell15 = new PdfPCell();
        pdfPCell15.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell15.setPhrase(new Phrase("%",font));
        productTable.addCell(pdfPCell15);

        PdfPCell pdfPCell16 = new PdfPCell();
        pdfPCell16.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        pdfPCell16.setPhrase(new Phrase("AMOUNT",font));
        productTable.addCell(pdfPCell16);

        // fill the data in the product table row by row for each record ..
        int count =0;
        int totalQuanity =0;
        double totalSale =0;
        double totaltaxableValue = 0;
        double totalCGST =0;
        double totalSGST = 0;
        double totalAmount =0;
        double totalIGST =0;

        for(InvoiceProduct invoiceProduct : productList){
            count++;
            PdfPCell pdfPCell17 = new PdfPCell();
            pdfPCell17.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
            pdfPCell17.setPhrase(new Phrase(Integer.toString(count),font));
            productTable.addCell(pdfPCell17);

            PdfPCell pdfPCell18 = new PdfPCell();
            pdfPCell18.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
            pdfPCell18.setPhrase(new Phrase(invoiceProduct.getProductMaster().getStyleCode(),font));
            productTable.addCell(pdfPCell18);

            PdfPCell pdfPCell19 = new PdfPCell();
            pdfPCell19.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell19.setPhrase(new Phrase(invoiceProduct.getProductMaster().getHsnCode(),font));
            productTable.addCell(pdfPCell19);

            PdfPCell pdfPCell20 = new PdfPCell();
            pdfPCell20.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            pdfPCell20.setPhrase(new Phrase(invoiceProduct.getTotalQuantity(),font));
            productTable.addCell(pdfPCell20);

            //  -- TODO -
            //rate per pc
            PdfPCell pdfPCell21 = new PdfPCell();
            pdfPCell21.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            pdfPCell21.setPhrase(new Phrase(Double.toString(invoiceProduct.getProductMaster().getCostPrice()),font));
            productTable.addCell(pdfPCell21);

            //total sale
            double costPrice = invoiceProduct.getProductMaster().getCostPrice();
            double saleValue = Double.parseDouble(df.format(Integer.parseInt(invoiceProduct.getTotalQuantity()) * costPrice ));
            double taxableValue = saleValue;
            PdfPCell pdfPCell22 = new PdfPCell();
            pdfPCell22.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            pdfPCell22.setPhrase(new Phrase(Double.toString(taxableValue  ) ,font ));
            productTable.addCell(pdfPCell22);



            PdfPCell pdfPCell23 = new PdfPCell();
            pdfPCell23.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            pdfPCell23.setPhrase(new Phrase(Double.toString(taxableValue),font));
            productTable.addCell(pdfPCell23);

            double gst =5;
            if(costPrice>1000){
                gst=12;
            }
            //same state of  suplier and party
            if(bhumiBranchWarehouseAddress.getPinCode().equals(partyShippinAddress.getPinCode())){

                //cgst
                PdfPCell pdfPCell24 = new PdfPCell();
                pdfPCell24.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell24.setPhrase(new Phrase(Double.toString(gst/2 ),font));
                productTable.addCell(pdfPCell24);

                double cgst = (saleValue * gst )/200 ; //2.5% 0r 6%
                cgst = Double.parseDouble(df.format(cgst));

                PdfPCell pdfPCell25 = new PdfPCell();
                pdfPCell25.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell25.setPhrase(new Phrase(Double.toString(cgst),font) );
                productTable.addCell(pdfPCell25);

                //sgst
                double sgst = cgst;
                PdfPCell pdfPCell26 = new PdfPCell();
                pdfPCell26.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell26.setPhrase(new Phrase(Double.toString(gst/2) ,font));
                productTable.addCell(pdfPCell26);


                PdfPCell pdfPCell27 = new PdfPCell();
                pdfPCell27.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell27.setPhrase(new Phrase(Double.toString(sgst) ,font));
                productTable.addCell(pdfPCell27);

                // setting IGST as empty
                PdfPCell pdfPCell28 = new PdfPCell();
                pdfPCell28.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell28.setPhrase(new Phrase(" "));
                productTable.addCell(pdfPCell28);


                PdfPCell pdfPCell29 = new PdfPCell();
                pdfPCell29.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell29.setPhrase(new Phrase(" ") );
                productTable.addCell(pdfPCell29);
                totalCGST += cgst;
                totalSGST += sgst;


            }else { // different state hence whole tax goes in IGST

                //cgst
                PdfPCell pdfPCell24 = new PdfPCell();
                pdfPCell24.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell24.setPhrase(new Phrase(" "));
                productTable.addCell(pdfPCell24);

                PdfPCell pdfPCell25 = new PdfPCell();
                pdfPCell25.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell25.setPhrase(new Phrase(" " ) );
                productTable.addCell(pdfPCell25);

                //sgst

                PdfPCell pdfPCell26 = new PdfPCell();
                pdfPCell26.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell26.setPhrase(new Phrase(" ") );
                productTable.addCell(pdfPCell26);


                PdfPCell pdfPCell27 = new PdfPCell();
                pdfPCell27.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell27.setPhrase(new Phrase(" " ) );
                productTable.addCell(pdfPCell27);

                // setting IGST as empty
                PdfPCell pdfPCell28 = new PdfPCell();
                pdfPCell28.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell28.setPhrase(new Phrase(Double.toString(gst),font) );
                productTable.addCell(pdfPCell28);

                double igst = ( saleValue * gst )/100; // total 12 % or 5%
                igst = Double.parseDouble(df.format(igst));

                PdfPCell pdfPCell29 = new PdfPCell();
                pdfPCell29.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                pdfPCell29.setPhrase(new Phrase(Double.toString(igst),font) );
                productTable.addCell(pdfPCell29);

                totalIGST += igst;


            }
            // total amount cost price  + gst
            double amount = taxableValue + ((taxableValue * gst )/100) ;
            amount = Double.parseDouble(df.format(amount));

            PdfPCell pdfPCell291 = new PdfPCell();
            pdfPCell291.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            pdfPCell291.setPhrase(new Phrase(Double.toString(amount),font) );
            productTable.addCell(pdfPCell291);

            totalQuanity += Integer.parseInt( invoiceProduct.getTotalQuantity() ) ;
            totalSale += saleValue;
            totaltaxableValue += taxableValue;
            totalAmount += amount;


        }
        PdfPCell pdfPCell1111 = new PdfPCell();
        pdfPCell1111.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPCell1111.setColspan(14);
        pdfPCell1111.setPhrase(new Phrase("",font));
        productTable.addCell(pdfPCell1111);
        // put the final or total sum values at the end of table .
        PdfPCell pdfPCell111 = new PdfPCell();
        pdfPCell111.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPCell111.setColspan(2);
        pdfPCell111.setPhrase(new Phrase("Total",font));
        productTable.addCell(pdfPCell111);

       // productTable.addCell(new Phrase(" "));  // because of col span

        productTable.addCell(new Phrase(" "));
        productTable.addCell(new Phrase(Integer.toString(totalQuanity),font));
        productTable.addCell(new Phrase(" "));
        productTable.addCell(new Phrase(Double.toString(totalSale),font));
        productTable.addCell(new Phrase(Double.toString(totaltaxableValue),font));
        productTable.addCell(new Phrase(" "));
        if(totalCGST ==0){
            productTable.addCell(new Phrase(" "));
        }else {
            productTable.addCell(new Phrase(Double.toString(totalCGST),font));
        }

        productTable.addCell(new Phrase(" "));

        if(totalSGST ==0){
            productTable.addCell(new Phrase(" "));
        }else {
            productTable.addCell(new Phrase(Double.toString(totalSGST),font));
        }

        productTable.addCell(new Phrase(" "));
        if(totalIGST ==0){
            productTable.addCell(new Phrase(" "));
        }else {
            productTable.addCell(new Phrase(Double.toString(totalIGST),font));
        }

        totalAmount= Double.parseDouble(df.format(totalAmount));
        productTable.addCell(new Phrase(Double.toString(totalAmount),font));




        PdfPTable pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setWidths(new int[]{6, 4});

        pdfPTable.addCell(bhumiRegisteredAddressTable);
        pdfPTable.addCell(invoiceDetailsTable);
        pdfPTable.addCell(new Phrase("  "));
        pdfPTable.addCell(new Phrase("  "));
        PdfPCell cell3 = new PdfPCell();
        cell3.setColspan(2);
        cell3.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell3.setPhrase(new Phrase(" TAX INVOICE  ",bold2));
        pdfPTable.addCell(cell3);

        PdfPCell cell2 = new PdfPCell();
        cell2.setColspan(2);
        cell2.addElement(partyRegisteredAddressTable);
        pdfPTable.addCell(cell2);

        PdfPCell cell4 = new PdfPCell();
        cell4.setColspan(2);
        cell4.addElement(productTable);

        pdfPTable.addCell(cell4);

        PdfPCell cell115 = new PdfPCell();
        cell115.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        cell115.setPhrase(new Phrase(" "));
        pdfPTable.addCell(cell115);

        PdfPCell cell5 = new PdfPCell();
        cell5.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell5.setPhrase(new Phrase("Summary"));
        pdfPTable.addCell(cell5);
        pdfPTable.addCell(new Phrase(" "));

        PdfPTable gstDetailTable = new PdfPTable(2);
        gstDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);//----------change
        PdfPCell pdfPCell17 = new PdfPCell();
        pdfPCell17.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPCell17.setPhrase(new Phrase("Total Invoice Value",font));
        gstDetailTable.addCell(pdfPCell17);

        PdfPCell pdfPCell18 = new PdfPCell();
        pdfPCell18.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        pdfPCell18.setPhrase(new Phrase(Double.toString(totaltaxableValue),font));
        gstDetailTable.addCell(pdfPCell18);

        PdfPCell pdfPCell19 = new PdfPCell();
        pdfPCell19.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPCell19.setPhrase(new Phrase("Total Taxable Value",font));
        gstDetailTable.addCell(pdfPCell19);

        PdfPCell pdfPCell20 = new PdfPCell();
        pdfPCell20.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        pdfPCell20.setPhrase(new Phrase(Double.toString(totaltaxableValue),font));
        gstDetailTable.addCell(pdfPCell20);

        PdfPCell pdfPCell21 = new PdfPCell();
        pdfPCell21.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPCell21.setPhrase(new Phrase("Total CGST",font));
        gstDetailTable.addCell(pdfPCell21);

        PdfPCell pdfPCell22 = new PdfPCell();
        pdfPCell22.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        if(totalCGST ==0){
            pdfPCell22.setPhrase(new Phrase(" "));
        }else {
            pdfPCell22.setPhrase(new Phrase(Double.toString(totalCGST),font));
        }
        gstDetailTable.addCell(pdfPCell22);

        PdfPCell pdfPCell24 = new PdfPCell();
        pdfPCell24.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPCell24.setPhrase(new Phrase("Total SGST",font));
        gstDetailTable.addCell(pdfPCell24);

        PdfPCell pdfPCell23 = new PdfPCell();
        pdfPCell23.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        if(totalSGST == 0){
            pdfPCell22.setPhrase(new Phrase(" "));
        }else {
            pdfPCell23.setPhrase(new Phrase(Double.toString(totalSGST),font));
        }
        gstDetailTable.addCell(pdfPCell23);

        PdfPCell pdfPCell26 = new PdfPCell();
        pdfPCell26.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPCell26.setPhrase(new Phrase("Total IGST",font));
        gstDetailTable.addCell(pdfPCell26);

        PdfPCell pdfPCell27 = new PdfPCell();
        pdfPCell27.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        if(totalIGST ==0){
            pdfPCell27.setPhrase(new Phrase(" "));
        }else {
            pdfPCell27.setPhrase(new Phrase(Double.toString(totalIGST),font));
        }
        gstDetailTable.addCell(pdfPCell27);




        PdfPCell pdfPCell29 = new PdfPCell();
        pdfPCell29.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPCell29.setPhrase(new Phrase("Grand Total",font));
        gstDetailTable.addCell(pdfPCell29);

        PdfPCell pdfPCell28 = new PdfPCell();
        pdfPCell28.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        pdfPCell28.setPhrase(new Phrase(Double.toString(totalAmount),font));
        gstDetailTable.addCell(pdfPCell28);




        PdfPTable pdfPTable1 = new PdfPTable(2);
        pdfPTable1.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPTable1.setWidthPercentage(100);
        pdfPTable1.setWidths( new int [] {2,3});
        pdfPTable1.addCell(new Phrase("Total Value of Goods in Rs.(In Words):",bold2));
        String totalValueInWord = new NumberToWord().convertDecimlToWord(totalAmount);
        pdfPTable1.addCell(new Phrase(totalValueInWord,bold2));

        PdfPCell pdfPCell25 = new PdfPCell();
        pdfPCell25.setColspan(2);
        pdfPCell25.addElement(pdfPTable1);



        PdfPTable pdfPTable2 = new PdfPTable(1);
        pdfPTable2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        pdfPTable2.setWidthPercentage(100);
       // pdfPTable2.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPTable2.addCell(new Phrase("M V No : ",font));
        pdfPTable2.addCell(new Phrase("Mode of Transport",font));
        pdfPTable2.addCell(new Phrase("Transporter Name ",font));
        pdfPTable2.addCell(new Phrase("Agent Name: ",font));
        pdfPTable2.addCell(new Phrase("Rate /Kg  : ",font));
        pdfPTable2.addCell(new Phrase("Delivery Lead Time : ",font));

        PdfPCell pdfPCell30 = new PdfPCell();
        pdfPCell30.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPCell30.addElement(pdfPTable2);


        PdfPTable pdfPTable5 = new PdfPTable(2);
        pdfPTable5.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        pdfPTable5.setWidthPercentage(100);

       // pdfPTable5.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPTable5.addCell(new Phrase("  "));
        pdfPTable5.addCell(new Phrase(" ",font));
        pdfPTable5.addCell(new Phrase("Account Number ",font));
        pdfPTable5.addCell(new Phrase("107905501124",font));
        pdfPTable5.addCell(new Phrase("Bank Name: ",font));
        pdfPTable5.addCell(new Phrase("ICICI BANK",font));
        pdfPTable5.addCell(new Phrase("Branch : ",font));
        pdfPTable5.addCell(new Phrase("BALLABHGARAH, FARIDABAAD",font));
        pdfPTable5.addCell(new Phrase("IFS Code ",font));
        pdfPTable5.addCell(new Phrase("ICIC0001079",font));
        pdfPTable5.addCell(new Phrase(" "));
        pdfPTable5.addCell(new Phrase(" "));

        PdfPCell pdfPCell31 = new PdfPCell();
        pdfPCell31.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPCell31.addElement(pdfPTable5);

        PdfPTable pdfPTable3 = new PdfPTable(2);
        pdfPTable3.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        pdfPTable3.setWidthPercentage(100);
        pdfPTable3.setWidths(new int[]{4,3});
        pdfPTable3.addCell(pdfPCell30);
        pdfPTable3.addCell(pdfPCell31);

        PdfPCell pdfPCell32 = new PdfPCell();
        pdfPCell32.setColspan(2);
     //   pdfPCell32.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        pdfPCell32.addElement(pdfPTable3);


        pdfPTable.addCell(gstDetailTable);
        pdfPTable.addCell(pdfPCell25);
       // pdfPTable.addCell(pdfPTable3);
        pdfPTable.addCell(pdfPCell32);


        document.add(pdfPTable);
        document.close();

        return fileName;

    }
}



