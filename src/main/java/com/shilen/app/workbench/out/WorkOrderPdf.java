package com.shilen.app.workbench.out;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.shilen.app.workbench.model.wo.WoQty;
import com.shilen.app.workbench.model.wo.WorkOrder;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author gregpreston
 */
@SuppressWarnings("deprecation")
public class WorkOrderPdf {
    
    
   // public static final String DEST = "work_order.pdf";
  //  public static final String WO = "B0000000000001";
    
    public static final String FS_XS = "xs";
    public static final String FS_SM = "sm";
    public static final String FS_M = "m";
    public static final String FS_L = "l";
    public static final String FS_XL = "xl";
    
    public static final float TABLE_WIDTH = 555f;
    
    public WorkOrderPdf() {
        
        
    }
    
    
    public void build(HttpServletResponse response, WorkOrder wo, List<WoQty> woqty) throws Exception {
        
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(response.getOutputStream()));
        
        Document doc = new Document(pdfDocument);
        doc.setMargins(20f, 20f, 20f, 20f);
          
        //PageSize ps = PageSize.LETTER;
        PdfPage pdfPage1 = pdfDocument.addNewPage(); 
  
        
        
        drawMakeReady(pdfPage1, pdfDocument, wo);
        drawBarcode( pdfPage1, pdfDocument, wo);
        PageOneHeader(doc, wo, woqty);
        PageOne(doc);

        
        PdfPage pdfPage2 = pdfDocument.addNewPage(); 
        drawBarcode( pdfPage2, pdfDocument, wo);
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        PageTwoHeader(doc,wo);
        PageTwo(doc);
        
      //  InsertData(pdfPage1, pdfDocument, wo );
  
        
        pdfDocument.close();
    
    }
    
    protected void drawBarcode(PdfPage pdfPage, PdfDocument pdfDocument, WorkOrder wo) throws Exception {
        
        PdfCanvas canvas = new PdfCanvas(pdfPage);
        Barcode128 code128 = new Barcode128(pdfDocument);
        code128.setCode(wo.getId() + "");
        code128.setCodeType(Barcode128.CODE128);
        code128.setBarHeight(50f);
        PdfFormXObject xObject = code128.createFormXObject(ColorConstants.BLACK, ColorConstants.BLACK, pdfDocument);
        canvas.addXObject(xObject, 450, 740);
        
        
       
        
        
        
          
    }
    
    protected void drawMakeReady(PdfPage pdfPage, PdfDocument pdfDocument, WorkOrder wo) throws Exception {
           
        
        Canvas textCanvas = new Canvas(new PdfCanvas(pdfPage), pdfDocument, pdfPage.getMediaBox());
        
        PdfCanvas canvas = new PdfCanvas(pdfPage);  
        Color color = ColorConstants.BLACK;
        
        canvas.setColor(color, true); 
        canvas.setStrokeColor(color);
        
        canvas.circle(120, 700, 90); 
        canvas.circle(120, 700, 10); 
        
        textCanvas.showTextAligned(getParagraph("SHILEN",FS_XL), 120, 760, TextAlignment.CENTER);
        
        canvas.rectangle(80, 735, 80, 20);  // Caliber 
        textCanvas.showTextAligned(getParagraph("CALIBER",FS_XS), 120, 725, TextAlignment.CENTER);
        
        canvas.rectangle(98, 625, 40, 20);  // Rate of Twist 
        textCanvas.showTextAligned(getParagraph("RATE OF TWIST",FS_XS), 120, 647, TextAlignment.CENTER);
        
        canvas.rectangle(60, 650, 30, 20);  // No of Grooves
        textCanvas.showTextAligned(getParagraph("NO OF GROOVES",FS_XS), 77, 672, TextAlignment.CENTER);
        
        canvas.rectangle(155, 705, 30, 20);  // Rifling
        textCanvas.showTextAligned(getParagraph("RIFLING",FS_XS), 170, 727, TextAlignment.CENTER);
         
        canvas.rectangle(160, 680, 30, 20);  // Steel
        textCanvas.showTextAligned(getParagraph("STEEL",FS_XS), 175, 670, TextAlignment.CENTER);
        
        
        Paragraph p = new Paragraph(wo.getCaliber() )
                .setFontColor( ColorConstants.BLACK)
                .setFontSize(14)
                .setBold()
                .setFont( PdfFontFactory.createFont(FontConstants.COURIER) );  

        // +21, +2
        textCanvas.showTextAligned(p, 122, 739, TextAlignment.CENTER);
        
        p = new Paragraph(wo.getTwist())
                .setFontColor( ColorConstants.BLACK)
                .setFontSize(14)
                .setBold()
                .setFont( PdfFontFactory.createFont(FontConstants.COURIER) );       
        textCanvas.showTextAligned(p, 116, 627, TextAlignment.CENTER);

        p = new Paragraph(wo.getRifling())
                .setFontColor( ColorConstants.BLACK)
                .setFontSize(14)
                .setBold()
                .setFont( PdfFontFactory.createFont(FontConstants.COURIER) );       
        textCanvas.showTextAligned(p, 171, 707, TextAlignment.CENTER);         

        p = new Paragraph(wo.getSteeltype_id())
                .setFontColor( ColorConstants.BLACK)
                .setFontSize(14)
                .setBold()
                .setFont( PdfFontFactory.createFont(FontConstants.COURIER) );       
        textCanvas.showTextAligned(p, 176, 682, TextAlignment.CENTER);  

        p = new Paragraph(wo.getGroove())
                .setFontColor( ColorConstants.BLACK)
                .setFontSize(14)
                .setBold()
                .setFont( PdfFontFactory.createFont(FontConstants.COURIER) );       
        textCanvas.showTextAligned(p, 76, 653, TextAlignment.CENTER);        
          
        canvas.stroke();  
            
    }
    
    /*
    protected void InsertData(PdfPage pdfPage, PdfDocument pdfDocument, WorkOrder data ) throws Exception {
        
       //  Canvas textCanvas = new Canvas(new PdfCanvas(pdfPage), pdfDocument, pdfPage.getMediaBox());
         
         Paragraph p = new Paragraph(data.getCaliber() )
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setBold()
                         .setFont( PdfFontFactory.createFont(FontConstants.COURIER) );  
         
         // +21, +2
     //    textCanvas.showTextAligned(p, 122, 739, TextAlignment.CENTER);
         
         p = new Paragraph(data.getTwist())
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setBold()
                         .setFont( PdfFontFactory.createFont(FontConstants.COURIER) );       
    //     textCanvas.showTextAligned(p, 116, 627, TextAlignment.CENTER);

         p = new Paragraph(data.getRifling())
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setBold()
                         .setFont( PdfFontFactory.createFont(FontConstants.COURIER) );       
    //     textCanvas.showTextAligned(p, 171, 707, TextAlignment.CENTER);         
        
         p = new Paragraph(data.getSteeltype_id())
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setBold()
                         .setFont( PdfFontFactory.createFont(FontConstants.COURIER) );       
    //     textCanvas.showTextAligned(p, 176, 682, TextAlignment.CENTER);  
         
         p = new Paragraph(data.getGroove())
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setBold()
                         .setFont( PdfFontFactory.createFont(FontConstants.COURIER) );       
     //    textCanvas.showTextAligned(p, 76, 653, TextAlignment.CENTER);         
    }    
    */
 
    protected void PageOneHeader(Document doc, WorkOrder wo, List<WoQty> woqty) throws Exception {
        
        
        // We add the cell to a table:
        Table _t = new Table(2);
        _t.setMarginLeft(210f);
        _t.setMarginTop(30f);
        
        _t.addCell( new Cell().add(getParagraph("WORK ORDER NO:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph(wo.getId() + "",FS_XL) ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("DATE:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph(wo.getScreated(),FS_SM) ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        
        doc.add(_t);
               
        _t = new Table(4);
        _t.setMarginLeft(210f);
        _t.setMarginTop(35f);
        
    
        for ( WoQty wq : woqty ) {
        
        
        Paragraph p = new Paragraph(wq.getQty() + "")
            .setFontColor( ColorConstants.BLACK)
            .setFontSize(14)
            .setBold()
            .setFont( PdfFontFactory.createFont(FontConstants.COURIER));


        _t.addCell( new Cell().add(getParagraph("QUANTITY:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setHeight(15f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add( p.setTextAlignment(TextAlignment.CENTER) ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        
        p = new Paragraph( wq.getLength() + "" )
            .setFontColor( ColorConstants.BLACK)
            .setFontSize(14)
            .setBold()
            .setFont( PdfFontFactory.createFont(FontConstants.COURIER));
        
        _t.addCell( new Cell().add(getParagraph("AT:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add( p.setTextAlignment(TextAlignment.CENTER) ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
  
        }
        
/*
        p = new Paragraph(wo.getQty30() + "") 
                .setFontColor( ColorConstants.BLACK)
                .setFontSize(14)
                .setBold()
                .setFont( PdfFontFactory.createFont(FontConstants.COURIER));


            _t.addCell( new Cell().add(getParagraph("QUANTITY:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setHeight(15f).setBorder(Border.NO_BORDER));
            _t.addCell( new Cell().add( p.setTextAlignment(TextAlignment.CENTER) ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
            
            p = new Paragraph("30")
                .setFontColor( ColorConstants.BLACK)
                .setFontSize(14)
                .setBold()
                .setFont( PdfFontFactory.createFont(FontConstants.COURIER));
            
            _t.addCell( new Cell().add(getParagraph("AT:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setBorder(Border.NO_BORDER));
            _t.addCell( new Cell().add( p.setTextAlignment(TextAlignment.CENTER) ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        
            
            
       p = new Paragraph( wo.getQty32() + "") 
                    .setFontColor( ColorConstants.BLACK)
                    .setFontSize(14)
                    .setBold()
                    .setFont( PdfFontFactory.createFont(FontConstants.COURIER));


                _t.addCell( new Cell().add(getParagraph("QUANTITY:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setHeight(15f).setBorder(Border.NO_BORDER));
                _t.addCell( new Cell().add( p.setTextAlignment(TextAlignment.CENTER) ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
                
                p = new Paragraph("32")
                    .setFontColor( ColorConstants.BLACK)
                    .setFontSize(14)
                    .setBold()
                    .setFont( PdfFontFactory.createFont(FontConstants.COURIER));
                
                _t.addCell( new Cell().add(getParagraph("AT:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setBorder(Border.NO_BORDER));
                _t.addCell( new Cell().add( p.setTextAlignment(TextAlignment.CENTER) ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
            
  */          
        
        Paragraph  p = new Paragraph(wo.getHeat_name())
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setBold()
                         .setFont( PdfFontFactory.createFont(FontConstants.COURIER) );       
 
        
        _t.addCell( new Cell().add(getParagraph("MATERIAL CODE:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setHeight(15f).setWidth(100f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell(1,3).add( p ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
      
        _t.addCell( new Cell().add(getParagraph("NAME:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setHeight(15f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add( new Paragraph() ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("MAKE-UP DATE:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add( new Paragraph() ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
           
        doc.add(_t);
       
    }
    

      
    
    protected void PageTwoHeader(Document doc, WorkOrder wo) throws Exception {
        
        //Document doc = new Document(pdfDocument);
        //doc.setMargins(20f, 20f, 20f, 20f);
        
        // We add the cell to a table:
        Table _t = new Table(2);
        _t.setMarginLeft(210f);
        _t.setMarginTop(30f);
        
        _t.addCell( new Cell().add(getParagraph("WORK ORDER NO:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph(wo.getId()+"",FS_XL) ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("DATE:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph(wo.getScreated(),FS_SM) ).setWidth(100f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        
        doc.add(_t);
        
        BufferedImage _bufferedImage = generate("http://wwww.google.com");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ImageIO.write( _bufferedImage, "jpg", baos );
    	baos.flush();
    	byte[] imageInByte = baos.toByteArray();
    	baos.close();
    	
    //	Image image = _bufferedImage;
        
        
        ImageData imageData = ImageDataFactory.create(imageInByte);
        
        Image image = new Image(imageData);
        image.setFixedPosition(50,  725);
        
        doc.add(image);
        
      
        
        
     //   canvas.addImage(imageData, 200f, 200f, true);
        
        
    }
    
    protected void PageTwo (Document doc) throws Exception {
        
        doc.add(new LineSeparator(new DottedLine()).setMarginTop(25f));
            
        Paragraph p = new Paragraph("CHECK-IN:")
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setFont( PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD) );
        
        doc.add(p);
        
        Table _t = new Table(4); 
        
        _t.addCell( new Cell().add(getParagraph("NAME:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(60f).setHeight(15f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(200f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("DATE:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
      
        doc.add(_t);
        
        _t = new Table(6);
        _t.setWidth(TABLE_WIDTH);
        _t.setMarginTop(10f);
        
        _t.addCell( new Cell(1,2).add(getParagraph("BEFORE LAP CHECK-IN:",FS_L).setTextAlignment(TextAlignment.LEFT) ).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell(1,4).add(getParagraph("BORE GAUGE:",FS_L).setTextAlignment(TextAlignment.LEFT).setPaddingLeft(40f) ).setBorder(Border.NO_BORDER));  
           
        _t.addCell( new Cell().add(getParagraph("GROOVE DIAMETER:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setHeight(15f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(110f));
        _t.addCell( new Cell().add(getParagraph("BLUE",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("YELLOW",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("GREEN",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("RED",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));
 
        doc.add(_t);
        _t = new Table(6);
        _t.setWidth(TABLE_WIDTH);
        _t.setMarginTop(5f);        
        
        _t.addCell( new Cell().add(getParagraph("GROOVE DIAMETER:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setHeight(15f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(110f));
        _t.addCell( new Cell().add(getParagraph("BLUE",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("YELLOW",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("GREEN",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("RED",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));

        doc.add(_t);
        _t = new Table(6);
        _t.setWidth(TABLE_WIDTH);
        _t.setMarginTop(5f);        
        
        _t.addCell( new Cell().add(getParagraph("GROOVE DIAMETER:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setHeight(15f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(110f));
        _t.addCell( new Cell().add(getParagraph("BLUE",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("YELLOW",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("GREEN",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("RED",FS_M).setTextAlignment(TextAlignment.CENTER) ).setWidth(86.25f).setBorder(Border.NO_BORDER));
       
        doc.add(_t);
        
        doc.add(new LineSeparator(new DottedLine()).setMarginTop(25f));
        

            
        p = new Paragraph("LAP:")
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setFont( PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD) );
        
        doc.add(p);   


        _t = new Table(6); 
        _t.setWidth(TABLE_WIDTH);
        _t.setMarginTop(1f);
        
        
        Cell c = new Cell();
        c.add( getParagraph("HONE / PRELAP",FS_L) );
        c.add( getParagraph("(Circle One)",FS_SM) );
        c.setTextAlignment(TextAlignment.CENTER).setWidth(100f).setBorder(Border.NO_BORDER);        

        
        _t.addCell( new Cell().add(getParagraph("RECIPE:",FS_M).setTextAlignment(TextAlignment.LEFT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("NAME:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setVerticalAlignment(VerticalAlignment.MIDDLE).setWidth(50f).setHeight(15f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(110f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("DATE:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setVerticalAlignment(VerticalAlignment.MIDDLE).setWidth(50f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(110f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell(c);
        
        doc.add(_t);
        

        
 
  
        _t = new Table(5); 
        _t.setWidth(TABLE_WIDTH);
        _t.setMarginTop(10f);
        
        _t.addCell( new Cell().add(getParagraph("STROKE COUNT:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(75f));
        _t.addCell( new Cell().add(getParagraph("RELOAD AT:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(75f));
        _t.addCell( new Cell().add(getParagraph("  NOTES:",FS_M).setTextAlignment(TextAlignment.LEFT) ).setWidth(245f).setBorder(Border.NO_BORDER));
        
        doc.add(_t);
        
        _t = new Table(5); 
        _t.setWidth(TABLE_WIDTH);
        _t.setMarginTop(5f);
        
        _t.addCell( new Cell().add(getParagraph("STROKE COUNT:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(75f));
        _t.addCell( new Cell().add(getParagraph("RELOAD AT:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(75f));
        _t.addCell( new Cell().add( new Paragraph() ).setBorder(Border.NO_BORDER).setWidth(245f));
        
        doc.add(_t);
        
        _t = new Table(5); 
        _t.setWidth(TABLE_WIDTH);
        _t.setMarginTop(5f);
        
        _t.addCell( new Cell().add(getParagraph("STROKE COUNT:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(100f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(75f));
        _t.addCell( new Cell().add(getParagraph("RELOAD AT:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(75f));
        _t.addCell( new Cell().add( new Paragraph() ).setBorder(Border.NO_BORDER).setWidth(245f));
        
        doc.add(_t);
        
        _t = new Table(6);   
        _t.setWidth(TABLE_WIDTH);
        _t.setMarginTop(5f);
         
        _t.addCell( new Cell().add(getParagraph("NAME:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("DATE STARTED:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("TIME:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        
             
        _t.addCell( new Cell().add( new Paragraph() ).setWidth(60f).setBorder(Border.NO_BORDER).setHeight(20f));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(125f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("DATE ENDED:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("TIME:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        
        doc.add(_t);


        doc.add(new LineSeparator(new DottedLine()).setMarginTop(5f));
            
        p = new Paragraph("INSPECTION & GRADING:")
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setFont( PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD) );
        
        doc.add(p);
        
        _t = new Table(4); 
        
        _t.addCell( new Cell().add(getParagraph("NAME:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(60f).setHeight(15f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(200f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("DATE:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
      
        doc.add(_t);
        
         _t = new Table(6);  
        _t.setMarginTop(10f);
        _t.setWidth(TABLE_WIDTH);
        
        _t.addCell( new Cell().add(getParagraph("GROOVE DIAMETER:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(92.5f));
        _t.addCell( new Cell().add(getParagraph("AIR GAUGE UNIFORMITY:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(92.5f));
        _t.addCell( new Cell().add(getParagraph("AIR GAUGE UNIFORMITY:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()  ).setWidth(92.5f));
        
        doc.add(_t);
     
        _t = new Table(6);  
        _t.setMarginTop(5f);
        _t.setWidth(TABLE_WIDTH);
        
        _t.addCell( new Cell().add(getParagraph("GROOVE DIAMETER:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(92.5f));
        _t.addCell( new Cell().add(getParagraph("AIR GAUGE UNIFORMITY:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(92.5f));
        _t.addCell( new Cell().add(getParagraph("AIR GAUGE UNIFORMITY:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()  ).setWidth(92.5f));
        
        doc.add(_t);        

        _t = new Table(6);  
        _t.setMarginTop(5f);
        _t.setWidth(TABLE_WIDTH);
        
        _t.addCell( new Cell().add(getParagraph("GROOVE DIAMETER:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(92.5f));
        _t.addCell( new Cell().add(getParagraph("AIR GAUGE UNIFORMITY:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(92.5f));
        _t.addCell( new Cell().add(getParagraph("AIR GAUGE UNIFORMITY:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()  ).setWidth(92.5f));
        
        doc.add(_t);

        _t = new Table(6);  
        _t.setMarginTop(10f);
        _t.setWidth(TABLE_WIDTH);
        
        _t.addCell( new Cell().add(getParagraph("SELECT MATCH:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(92.5f).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("MATCH:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(92.5f).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("REJECT:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(92.5f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()  ).setWidth(92.5f).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(.5f)));
        
        doc.add(_t);
        
        doc.add(new LineSeparator(new DottedLine()).setMarginTop(5f));
            
        p = new Paragraph("NOTES:")
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setFont( PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD) );
        
        doc.add(p);
        
        
    }
    
    protected void PageOne (Document doc) throws Exception {
        
       // Document doc = new Document(pdfDocument);
      //  doc.setMargins(20f, 20f, 20f, 20f);
      //  PageSize ps = PageSize.LETTER;
        
        doc.add(new LineSeparator(new DottedLine()).setMarginTop(20f));
            
        Paragraph p = new Paragraph("RECIPE")
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setFont( PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD) );
        
        doc.add(p);
        
        // We add the cell to a table:
        Table _t = new Table(6);   
        _t.setWidth(TABLE_WIDTH);
         
        _t.addCell( new Cell().add(getParagraph("DRILL:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setHeight(20f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(125f));
        _t.addCell( new Cell().add(getParagraph("REAM:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(125f));
        _t.addCell( new Cell().add(getParagraph("BUTTON:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(125f));
        
        doc.add(_t);
        
        _t = new Table(3);
        _t.setMarginTop(5f);
        _t.setWidth(TABLE_WIDTH);

        _t.addCell( new Cell().add(getParagraph("NOTES:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(370f).setHeight(20f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("BUTTON SIZE:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(120f));
          
        doc.add(_t);  
        
        doc.add(new LineSeparator(new DottedLine()).setMarginTop(25f));
        
        /* Drill / Ream */
        p = new Paragraph("DRILL/REAM")
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(14)
                         .setFont( PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD) );

        doc.add(p);
             
        _t = new Table(6); 
        _t.setWidth(TABLE_WIDTH);

        _t.addCell( new Cell().add(getParagraph("NAME:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setHeight(15f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(200f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("DATE STARTED:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("TIME:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));

        doc.add(_t);
               
        _t = new Table(6);  
        _t.setMarginTop(5f);
        _t.setWidth(TABLE_WIDTH);
        
        _t.addCell( new Cell().add(getParagraph("REQUIRED HOLE SIZE:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(125f));
        _t.addCell( new Cell().add(getParagraph("MEASURED DIAMETER OF REAM:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(110f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(125f));
        _t.addCell( new Cell().add(getParagraph("INITIALS:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()  ).setWidth(75f));
        
        doc.add(_t);
        
        _t = new Table(6);  
        _t.setMarginTop(5f);
        _t.setWidth(TABLE_WIDTH);

        _t.addCell( new Cell().add( new Paragraph() ).setBorder(Border.NO_BORDER).setWidth(60f));
        _t.addCell( new Cell().add( new Paragraph() ).setBorder(Border.NO_BORDER).setWidth(125f));
        _t.addCell( new Cell().add(getParagraph("MEASURED DIAMETER OF REAM/PRE-LAP:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(110f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()  ).setWidth(125f));
        _t.addCell( new Cell().add(getParagraph("INITIALS:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()  ).setWidth(75f));
        
        doc.add(_t);
        
        _t = new Table(6);  
        _t.setMarginTop(5f);
        _t.setWidth(TABLE_WIDTH);
        
        _t.addCell( new Cell().add( new Paragraph() ).setBorder(Border.NO_BORDER).setWidth(60f));
        _t.addCell( new Cell().add( new Paragraph() ).setBorder(Border.NO_BORDER).setWidth(125f));
        _t.addCell( new Cell().add(getParagraph("MEASURED DIAMETER PRE-LAP:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(110f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()  ).setWidth(125f));
        _t.addCell( new Cell().add(getParagraph("INITIALS:",FS_SM).setTextAlignment(TextAlignment.RIGHT) ).setWidth(60f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph() ).setWidth(75f));
        
        doc.add(_t);   
        
        /* Hone / Prelap */
        
        _t = new Table(4);  
        _t.setMarginTop(10f);
        
        Cell c = new Cell();
        c.add( getParagraph("HONE / PRELAP",FS_L) );
        c.add( getParagraph("(Circle One)",FS_SM) );
        c.setTextAlignment(TextAlignment.CENTER).setWidth(200f).setBorder(Border.NO_BORDER);        
        _t.addCell(c);
         
        _t.addCell( new Cell().add(getParagraph("# OF STROKES:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(75f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add( new Paragraph() ).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(.5f)));
        
        c = new Cell();
        c.add( getParagraph("150 / 180",FS_L) );
        c.add( getParagraph("(Circle One)",FS_SM) );
        c.setTextAlignment(TextAlignment.CENTER).setWidth(200f).setBorder(Border.NO_BORDER);
        _t.addCell(c);

        doc.add( _t );
        
        /* Scrap */ 
         _t = new Table(7); 
         _t.setMarginTop(10f);

        _t.addCell( new Cell().add( getParagraph("SCRAP:",FS_L)).setWidth(50f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("DRILL MARKS:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(200f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("OFF-CENTER:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("BAD FINISH:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        
        _t.addCell( new Cell(1,3).add(new Paragraph()).setWidth(250f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(getParagraph("DATE ENDED:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
        _t.addCell( new Cell().add(getParagraph("TIME:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
        _t.addCell( new Cell().add(new Paragraph()).setWidth(125f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));

        doc.add(_t);
        
        doc.add(new LineSeparator(new DottedLine()).setMarginTop(10f));
                 
          /* Rifling */ 
         _t = new Table(5); 
         _t.setMarginTop(5f);
         
         _t.addCell( new Cell().add( getParagraph("RIFLING:",FS_L)).setWidth(50f).setBorder(Border.NO_BORDER));
         _t.addCell( new Cell().add(getParagraph("NAME:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
         _t.addCell( new Cell().add(new Paragraph()).setWidth(200f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
         _t.addCell( new Cell().add(getParagraph("DATE:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
         _t.addCell( new Cell().add(new Paragraph()).setWidth(200f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));

         _t.addCell( new Cell().add( new Paragraph()).setWidth(50f).setBorder(Border.NO_BORDER));
         _t.addCell( new Cell().add(getParagraph("MEASURED TWIST:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
         _t.addCell( new Cell().add(new Paragraph()).setWidth(200f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
         _t.addCell( new Cell().add(getParagraph("SINE BAR SETTING:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
         _t.addCell( new Cell().add(new Paragraph()).setWidth(200f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));         
         
         doc.add(_t);
         
         
          /* Stress */ 
         _t = new Table(6); 
         _t.setMarginTop(20f);
         
         c = new Cell(2,1);
         c.add( getParagraph("BIG BLUE / LITTLE BLUE",FS_L) );
         c.add( getParagraph("(Circle One)",FS_SM) );
         c.setTextAlignment(TextAlignment.CENTER).setWidth(250f).setBorder(Border.NO_BORDER);
        
         
         _t.addCell( new Cell(2,1).add( getParagraph("STRESS RELIEVE:",FS_M)).setWidth(50f).setBorder(Border.NO_BORDER));
         _t.addCell( new Cell().add(getParagraph("NAME:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
         _t.addCell( new Cell().add(new Paragraph()).setWidth(175f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
         _t.addCell( new Cell().add(getParagraph("DATE:",FS_SM).setTextAlignment(TextAlignment.LEFT) ).setWidth(50f).setBorder(Border.NO_BORDER));
         _t.addCell( new Cell().add(new Paragraph()).setWidth(175f).setBorder(Border.NO_BORDER).setBorderBottom( new SolidBorder(.5f)));
         _t.addCell(c);
         doc.add(_t);
         
        doc.add(new LineSeparator(new DottedLine()));
        
        doc.add( getParagraph("NOTES:",FS_L));
       
        
        


         
         
        
    }
    
    
    protected Paragraph getParagraph( String s, String size ) throws Exception {
      
        Paragraph p = null;
        
        
        if ( size.equals("xs") ) {
      
            p = new Paragraph(s)
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(6)
                         .setFont( PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD) );
            
        } else if ( size.equals("sm")) {
                    
            p = new Paragraph(s)
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(8)
                         .setFont( PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA) );                    
        
        } else if ( size.equals("m")) {
                    
            p = new Paragraph(s)
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(10)
                         .setFont( PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD) );                    
        
        } else if ( size.equals("l")) {
                    
            p = new Paragraph(s)
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(12)
                         .setFont( PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD) );                    
        
        } else if ( size.equals("xl")) {
                    
            p = new Paragraph(s)
                         .setFontColor( ColorConstants.BLACK)
                         .setFontSize(18)
                         .setFont( PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD) );                    
        
            

        }
        
        return p;
      
  }  
    
    
    
    protected BufferedImage generate( String url ) throws Exception {

		int size = 100;

		Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
		hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		
		// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
		hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
 
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, size, size, hintMap);
		int CrunchifyWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
 
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(java.awt.Color.WHITE);
		graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
		graphics.setColor(java.awt.Color.BLACK);
 
		for (int i = 0; i < CrunchifyWidth; i++) {
			for (int j = 0; j < CrunchifyWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
				
		return image;
	}
    
}

