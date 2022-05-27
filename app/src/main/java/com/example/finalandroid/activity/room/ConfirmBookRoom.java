package com.example.finalandroid.activity.room;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.finalandroid.R;
import com.example.finalandroid.activity.review.ReviewHotelAcitivity;
import com.example.finalandroid.api.ApiService;
import com.example.finalandroid.custom.ProgressDialogCustom;
import com.example.finalandroid.model.User;
import com.example.finalandroid.model.UserRoom;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import android.net.Uri;
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


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmBookRoom extends AppCompatActivity {

    private Room room;
    private Hotel hotel;
    private BookRoomOfUser bookRoomOfUser;
    private TextView timeNhan, timeTra, nameHotel, adressHotel, nameRoom, userName, phoneUser, idPrice;
    private SqliteHelper sqliteHelper;
    private User user;
    private Button btnBookRoom;
    private ProgressDialogCustom progressDialogCustom;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_book_room);
        context = this;
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
        progressDialogCustom = new ProgressDialogCustom(this);
        btnBookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRoom us = new UserRoom();
                us.setIdUser(user.getId());
                us.setIdRoom(room.getIdRoom());
                us.setDateFrom(bookRoomOfUser.getTimeNhan());
                us.setDateTo(bookRoomOfUser.getTimeTra());
                us.setIsPayMent("0");
                us.setIsDelete("0");
                progressDialogCustom.show();
                insertUserRoom(us);
                //createPdf();
            }
        });
    }

    private void insertUserRoom(UserRoom us) {
        ApiService.apiService.bookRoom(us, user.getAccessToken()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                progressDialogCustom.hide();
                if(response.body() != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("ZoZoy thông báo");
                    builder.setMessage("Bạn đã đặt thành công phòng: " + room.getName() + "- khách sạn: " + hotel.getName());
                    builder.setIcon(R.drawable.ic_baseline_done_24);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("ZoZoy thông báo");
                            builder.setMessage("Bạn có muốn in hóa đơn hay không ?");
                            builder.setIcon(R.drawable.ic_baseline_mobile_screen_share_24);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    createPdf();
                                }
                            });
                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(context, ReviewHotelAcitivity.class);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                progressDialogCustom.hide();
            }
        });
    }

    private void createPdf() {
        try {
            String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File file = new File(pdfPath, hotel.getName() + "_"  + room.getName() +
                    System.currentTimeMillis() + "_" + ".pdf");
            PdfWriter fileWriter = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(fileWriter);
            Document document = new Document(pdfDocument);
            pdfDocument.setDefaultPageSize(PageSize.A7);
            document.setBorder(Border.NO_BORDER);
            document.setMargins(0, 0, 0, 0);

            Drawable d = getDrawable(R.drawable.confirm);
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();

            ImageData imageData = ImageDataFactory.create(bitmapData);
            Image image = new Image(imageData).setWidth(100).setHeight(100).setHorizontalAlignment(HorizontalAlignment.CENTER);

            Paragraph confirmBody = new Paragraph("Zozoy xác nhận đặt phòng").setBold().setTextAlignment(TextAlignment.CENTER);
            Paragraph name = new Paragraph(user.getName() + "").setTextAlignment(TextAlignment.CENTER).setFontSize(12);;

            Paragraph goup = new Paragraph(hotel.getName()).setTextAlignment(TextAlignment.CENTER).setFontSize(20);
            Paragraph location = new Paragraph(hotel.getAddress()).setTextAlignment(TextAlignment.CENTER).setFontSize(12);
            Paragraph timeFrom = new Paragraph("Giờ nhận: " +bookRoomOfUser.getTimeNhan()).setTextAlignment(TextAlignment.CENTER).setFontSize(12);

            BarcodeQRCode qrCode = new BarcodeQRCode(user.getId() + "\n" + hotel.getId() + "\n" + room.getIdHotel());
            PdfFormXObject qrCodeObject = qrCode.createFormXObject(ColorConstants.BLACK, pdfDocument);
            Image qrCodeImage = new Image(qrCodeObject).setWidth(80).setHorizontalAlignment(HorizontalAlignment.CENTER);

            document.add(image);
            document.add(name);
            document.add(confirmBody);
            document.add(goup);
            document.add(location);
            document.add(timeFrom);
            document.add(qrCodeImage);

            document.close();
            Toast.makeText(getApplicationContext(), "Đặt phòng thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(pdfPath + hotel.getName() + "_"  + room.getName() +
                    System.currentTimeMillis() + "_" + ".pdf"), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Lỗi in hóa đơn", Toast.LENGTH_SHORT).show();
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