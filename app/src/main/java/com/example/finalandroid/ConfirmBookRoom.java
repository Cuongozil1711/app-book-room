package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalandroid.dal.SqliteHelper;
import com.example.finalandroid.model.BookRoomOfUser;
import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.Room;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmBookRoom extends AppCompatActivity {

    private Room room;
    private Hotel hotel;
    private BookRoomOfUser bookRoomOfUser;
    private TextView timeNhan, timeTra, nameHotel, adressHotel, nameRoom, userName, phoneUser, idPrice;
    private SqliteHelper sqliteHelper;
    private User user;
    private Button btnBookRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_book_room);
        getIntentView();
        getInitView();
        sqliteHelper = new SqliteHelper(this);
        user = sqliteHelper.getUser();
        timeNhan.setText(bookRoomOfUser.getTimeNhan());
        timeTra.setText(bookRoomOfUser.getTimeTra());
        nameHotel.setText(hotel.getName());
        adressHotel.setText(hotel.getAddress());
        nameRoom.setText(room.getName());
        userName.setText(user.getName());
        phoneUser.setText(user.getPhone());
        idPrice.setText(room.getPrice());

        btnBookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPdf();
            }

        });
    }

    private void createPdf() {
        try {
            String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            SimpleDateFormat sp = new SimpleDateFormat("yyyy/mm/dd");
            File file = new File(pdfPath, hotel.getName() + "_"  + room.getName() + "_" + ".pdf");
            OutputStream ops = new FileOutputStream(file);
            PdfWriter fileWriter = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(fileWriter);
            Document document = new Document(pdfDocument);

            pdfDocument.setDefaultPageSize(PageSize.A6);
            document.setMargins(0, 0, 0, 0);

            Drawable d = getDrawable(R.drawable.confirm);
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();

            ImageData imageData = ImageDataFactory.create(bitmapData);
            Image image = new Image(imageData).setWidth(100).setHeight(100).setHorizontalAlignment(HorizontalAlignment.CENTER);

            Paragraph confirmBody = new Paragraph("ZoZoy App Confirm").setBold().setTextAlignment(TextAlignment.CENTER);

            Paragraph goup = new Paragraph(hotel.getName()).setTextAlignment(TextAlignment.CENTER).setFontSize(20);

            Paragraph location = new Paragraph(hotel.getAddress()).setTextAlignment(TextAlignment.CENTER).setFontSize(12);

            float[] width = {100f, 100f};
            Table table = new Table(width);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addCell(new Cell().add(new Paragraph("Name")));
            table.addCell(new Cell().add(new Paragraph(user.getName())));

            table.addCell(new Cell().add(new Paragraph("Phone")));
            table.addCell(new Cell().add(new Paragraph(user.getPhone())));

            table.addCell(new Cell().add(new Paragraph("Room Name")));
            table.addCell(new Cell().add(new Paragraph(room.getName())));

            table.addCell(new Cell().add(new Paragraph("Room Number")));
            table.addCell(new Cell().add(new Paragraph(room.getFloor())));

            table.addCell(new Cell().add(new Paragraph("Room Price")));
            table.addCell(new Cell().add(new Paragraph(room.getPrice())));

            table.addCell(new Cell().add(new Paragraph("DateTime")));
            table.addCell(new Cell().add(new Paragraph(bookRoomOfUser.getTimeNhan() + "=>" +  bookRoomOfUser.getTimeNhan())));

            BarcodeQRCode qrCode = new BarcodeQRCode(user.getName() + "\n" + user.getPhone() + "\n" + hotel.getId() + "\n" + room.getIdHotel());
            PdfFormXObject qrCodeObject = qrCode.createFormXObject(ColorConstants.BLACK, pdfDocument);
            Image qrCodeImage = new Image(qrCodeObject).setWidth(80).setHorizontalAlignment(HorizontalAlignment.CENTER);

            document.add(image);
            document.add(confirmBody);
            document.add(goup);
            document.add(location);
            document.add(table);
            document.add(qrCodeImage);

            document.close();
            Toast.makeText(getApplicationContext(), "Đặt phòng thành công", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Lỗi đặt phòng", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(ex);
        }

    }

    private void getInitView() {
        timeNhan = findViewById(R.id.timeNhan);
        timeTra = findViewById(R.id.timeTra);
        nameHotel = findViewById(R.id.nameHotel);
        adressHotel = findViewById(R.id.adressHotel);
        nameRoom = findViewById(R.id.nameRoom);
        userName = findViewById(R.id.userName);
        phoneUser = findViewById(R.id.phoneUser);
        idPrice = findViewById(R.id.idPrice);
        btnBookRoom = findViewById(R.id.btnBookRoom);
    }

    private void getIntentView() {
        room = (Room) getIntent().getSerializableExtra("room");
        hotel = (Hotel) getIntent().getSerializableExtra("hotel");
        bookRoomOfUser = (BookRoomOfUser) getIntent().getSerializableExtra("roomOfUser");
    }


}