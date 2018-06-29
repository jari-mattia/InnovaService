package com.mattia.yari.innovaservice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.fragment.app.DialogFragment;

public class ContactDialog extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_us_bottom_sheet, container, false);

        TableRow call_row = view.findViewById(R.id.call_row);
        call_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "+393391418005";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        TableRow wa_row = view.findViewById(R.id.whatsapp_row);
        wa_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickWhatsApp(view);
            }
        });

                return view;
    }

    public void onClickWhatsApp(View view) {

        Context context = getContext();
        PackageManager pm =  context.getPackageManager();
        try {
            String number = "+393755070555";
            Uri uri = Uri.parse("smsto:" + number);
            Intent waIntent = new Intent(Intent.ACTION_SENDTO, uri);
            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");
            startActivity(waIntent);

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }

}
