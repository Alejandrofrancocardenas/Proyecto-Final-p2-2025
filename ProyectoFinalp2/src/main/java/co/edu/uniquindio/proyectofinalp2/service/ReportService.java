package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.Rate;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

public class ReportService {

    private static ReportService instance;
    public static ReportService getInstance() {
        if (instance == null) {
            instance = new ReportService();
        }
        return instance;
    }

    // metodo para gnerar archivo CSV con la informacion de los envios
    public void generateCsvReport(List<Shipment> shipments, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ID,Usuario,Zona,Estado,Precio,Fecha\n");

            for (Shipment s : shipments) {
                writer.write(String.format("%s,%s,%s,%s,%.2f,%s,\n",
                        s.getShipmentId(),
                        s.getUser().getFullname(),
                        s.getZone(),
                        s.getStatus(),
                        s.getRate().getBasePrice(),
                        s.getCreationDate()
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException("error al generar el CSV: " + e.getMessage());
        }
    }

    // metodo para generrar un archivo pdf
    public void generatePdfReport(List<Shipment> shipments, String filePath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph("Reporte de Envíos"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.addCell("ID");
            table.addCell("Usuario");
            table.addCell("Zona");
            table.addCell("Estado");
            table.addCell("Precio");

            for (Shipment s : shipments) {
                table.addCell(s.getShipmentId());
                table.addCell(s.getUser().getFullname());
                table.addCell(s.getZone());
                table.addCell(s.getStatus().toString());
                table.addCell(String.valueOf(s.getRate().getBasePrice()));
            }

            document.add(table);
            document.close();
            System.out.println("✅ Reporte PDF generado: " + filePath);

        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF: " + e.getMessage());
        }
    }
}
