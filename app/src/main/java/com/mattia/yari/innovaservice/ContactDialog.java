package com.mattia.yari.innovaservice;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ContactDialog extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_us_bottom_sheet, container, false);
        final ContactUs contactUs = new ContactUs(view);
        final Activity activity = getActivity();

        //select call
        TableRow call_row = view.findViewById(R.id.call_row);
        call_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "+393391418005";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        //select whatsapp
        TableRow wa_row = view.findViewById(R.id.whatsapp_row);
        wa_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactUs.onClickWhatsApp();
            }
        });

        //select email
        TableRow email_row = view.findViewById(R.id.email_row);
        email_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactUs.onClickEmailSend();
            }
        });

        //select Photo
        TableRow photo_row = view.findViewById(R.id.photo_row);
        photo_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactUs.onClickPhotoSend(activity);

            }
        });


        return view;
    }






}
