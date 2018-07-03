package com.mattia.yari.innovaservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.core.content.FileProvider;

public class ContactUs extends Activity{

    private View view;
    private Context context;
    private PackageManager pm;
    private final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;

    public ContactUs(){}

    public ContactUs(View view){
        this.view = view;
        this.context = view.getContext();
        this.pm = context.getPackageManager();
    }


    //method to send Whatsapp message
    public void onClickWhatsApp() {
        String phone = view.getResources().getString(R.string.phone);
        try {
            Uri uri = Uri.parse("smsto:" + phone);
            Intent waIntent = new Intent(Intent.ACTION_SENDTO, uri);
            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");
            context.startActivity(waIntent);

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "WhatsApp non Installato", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    //method to send an email
    public void onClickEmailSend() {
        String to = view.getResources().getString(R.string.email);
        String subject = "Richiesta intervento";
        Uri uri = Uri.parse("mailto:" + to);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        //emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (emailIntent.resolveActivity(pm) != null) {
            context.startActivity(emailIntent);
        }
        else{
            Toast.makeText(context, "Non è installata nessuna app per inviare email", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    //methodo to send a photo
    public void onClickPhotoSend(Activity activity) {
      this.takePicture(activity);
      Uri photo = Uri.parse(mCurrentPhotoPath);
      //this.onActivityResult(REQUEST_TAKE_PHOTO, RESULT_OK, activity.getIntent());

    }

    public void takePicture(Activity activity){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(pm) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.getMessage();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.mattia.yari.innovaservice.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            Uri photoURI = data.getData();


            String to = view.getResources().getString(R.string.email);
            String subject = "Richiesta preventivo";
            Uri uri = Uri.parse("mailto:" + to);

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            //emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("application/image");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
            if (emailIntent.resolveActivity(pm) != null) {
                context.startActivity(emailIntent);
            }
            else{
                Toast.makeText(context, "Non è installata nessuna app per inviare email", Toast.LENGTH_SHORT)
                        .show();
            }
        }}


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public String getmCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    private File createPublicImageFile() throws IOException {
        // Create a path where we will place our picture in the user's
        // public pictures directory.  Note that you should be careful about
        // what you place here, since the user often manages these files.  For
        // pictures and other media owned by the application, consider
        // Context.getExternalMediaDir().
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        //File file = new File(path, imageFileName);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                path      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}

