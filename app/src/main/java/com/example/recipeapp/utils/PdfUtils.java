package com.example.recipeapp.utils;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;
import java.io.OutputStream;

public class PdfUtils {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void generatePdfFromText(Context context, String text, String fileName) {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName + ".pdf");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        Uri uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
        if (uri != null) {
            try {
                OutputStream outputStream = contentResolver.openOutputStream(uri);
                if (outputStream != null) {
                    // Initialize the PDF writer
                    PdfWriter writer = new PdfWriter(outputStream);

                    // Create a PDF document
                    PdfDocument pdfDocument = new PdfDocument(writer);


                    // Create a document
                    Document document = new Document(pdfDocument);
                    Log.e("TAGP", "generatePdfFromText: "+pdfDocument );
                    Log.e("TAGP", "generatePdfFromText: "+document );

                    // Add the text to the document
                    document.add(new Paragraph(text));

                    // Close the document
                    document.close();

                    // Show a toast or notification indicating successful PDF generation and download
                    Toast.makeText(context, "PDF generated and saved in Download Folder", Toast.LENGTH_SHORT).show();
                } else {
                    // Show a toast or notification indicating an error accessing the output stream
                    Toast.makeText(context, "Failed to access output stream", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Show a toast or notification indicating an error during PDF generation
                Toast.makeText(context, "Failed to generate PDF", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show a toast or notification indicating an error creating the file
            Toast.makeText(context, "Failed to create file", Toast.LENGTH_SHORT).show();
        }

    }

}

