package com.frescuratotal.erp.sales.view;

import com.frescuratotal.erp.sales.model.Customer;
import com.frescuratotal.erp.sales.model.Invoice;
import com.frescuratotal.erp.sales.model.Sale;
import com.frescuratotal.erp.sales.model.SaleDetail;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import com.lowagie.text.Font;

public class InvoicePdf {

    public String generarFactura(Invoice factura, Sale venta, Customer cliente) throws Exception {
        File carpeta = new File("facturas");
        carpeta.mkdirs();
        String ruta = "facturas/" + factura.getNumeroFactura() + ".pdf";

        Document documento = new Document();
        PdfWriter.getInstance(documento, new FileOutputStream(ruta));
        documento.open();
        Font titulo = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font negrita = new Font(Font.HELVETICA, 10, Font.BOLD);
        Font normal = new Font(Font.HELVETICA, 10, Font.NORMAL);


        documento.add(new Paragraph("FRESCURA TOTAL S.A.S.", titulo));
        documento.add(new Paragraph("NIT: 900123456-7", normal));
        documento.add(new Paragraph("Calle 123 #45-67 - Bogota", normal));
        documento.add(new Paragraph("Tel: (601) 234-5678", normal));

        String fecha = factura.getFechaEmision().toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        documento.add(new Paragraph(" "));
        documento.add(new Paragraph("FACTURA ELECTRONICA DE VENTA No. " + factura.getNumeroFactura(), negrita));
        documento.add(new Paragraph("Fecha de emision: " + fecha, normal));

        documento.add(new Paragraph(" "));
        documento.add(new Paragraph("DATOS DEL CLIENTE", negrita));
        documento.add(new Paragraph("Nombre: " + cliente.getNombre(), normal));
        documento.add(new Paragraph("Documento: " + cliente.getDocumento(), normal));
        documento.add(new Paragraph("Tipo: " + cliente.getNombreTipo(), normal));
        documento.add(new Paragraph("Direccion: " + cliente.getDireccion(), normal));
        documento.add(new Paragraph("Telefono: " + cliente.getTelefono(), normal));
        documento.add(new Paragraph("Email: " + cliente.getEmail(), normal));

        documento.add(new Paragraph(" "));
        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);

        tabla.addCell(new Phrase("Producto", negrita));
        tabla.addCell(new Phrase("Lote", negrita));
        tabla.addCell(new Phrase("Cant.", negrita));
        tabla.addCell(new Phrase("Precio", negrita));
        tabla.addCell(new Phrase("Desc.", negrita));
        tabla.addCell(new Phrase("IVA", negrita));
        tabla.addCell(new Phrase("Total", negrita));

        for (SaleDetail detalle : venta.getDetalles()) {
            tabla.addCell(new Phrase(detalle.getNombreProducto(), normal));
            tabla.addCell(new Phrase(detalle.getNumeroLote(), normal));
            tabla.addCell(new Phrase("" + detalle.getCantidadVendida(), normal));
            tabla.addCell(new Phrase("" + detalle.getPrecioUnitario(), normal));
            tabla.addCell(new Phrase("" + detalle.getMontoDescuento(), normal));
            tabla.addCell(new Phrase("" + detalle.getMontoImpuesto(), normal));
            tabla.addCell(new Phrase("" + detalle.getTotalLinea(), normal));
        }
        documento.add(tabla);

      
        documento.add(new Paragraph(" "));
        Paragraph subtotal = new Paragraph("Subtotal: " + factura.getSubtotal(), normal);
        subtotal.setAlignment(Element.ALIGN_RIGHT);
        documento.add(subtotal);

        Paragraph descuentos = new Paragraph("Descuentos: " + factura.getTotalDescuento(), normal);
        descuentos.setAlignment(Element.ALIGN_RIGHT);
        documento.add(descuentos);

        Paragraph iva = new Paragraph("IVA (19%): " + factura.getMontoImpuesto(), normal);
        iva.setAlignment(Element.ALIGN_RIGHT);
        documento.add(iva);

        Paragraph total = new Paragraph("TOTAL A PAGAR: " + factura.getMontoTotal(), titulo);
        total.setAlignment(Element.ALIGN_RIGHT);
        documento.add(total);

       
        documento.add(new Paragraph(" "));
        documento.add(new Paragraph("CUFE:", negrita));
        documento.add(new Paragraph(factura.getCufe(), normal));

        BitMatrix matriz = new QRCodeWriter().encode(factura.getEnlaceQr(), BarcodeFormat.QR_CODE, 150, 150);
        ByteArrayOutputStream bytesQr = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matriz, "PNG", bytesQr);
        Image imagenQr = Image.getInstance(bytesQr.toByteArray());
        documento.add(imagenQr);

        documento.close();
        return ruta;
    }
}
