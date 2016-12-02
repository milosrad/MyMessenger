package com.example.user.mymessenger.activity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mymessenger.R;
import com.squareup.picasso.Picasso;

/**
 * Created by cubesschool8 on 6/13/16.
 */
public class SmsWindow extends Activity {
    private EditText mText;
    private TextView smsTitle, smsText, textMessage;
    private ImageView image;
    private Button mButtonSend, mButtonInbox, mbuttonAddContact;
    private String sender;
    private String sms;
    private LinearLayout linearInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_window2);

        mText = (EditText) findViewById(R.id.editCardText);
        smsText = (TextView) findViewById(R.id.textViewText);
        smsTitle = (TextView) findViewById(R.id.textViewTitle);
        image = (ImageView) findViewById(R.id.image);
        mButtonSend = (Button) findViewById(R.id.buttonSend);
        textMessage = (TextView) findViewById(R.id.textMessage);
        mButtonInbox = (Button) findViewById(R.id.buttonInbox);
        mbuttonAddContact = (Button) findViewById(R.id.buttonContact);


        Intent i = getIntent();
        sms = i.getStringExtra("poruka");
        sender = i.getStringExtra("posiljalac");
        smsText.setText(sms);

        smsTitle.setText(getContactName(sender));
         getPhotoUri(fetchContactIdFromPhoneNumber(sender));

        Picasso.with(this).load( getPhotoUri(fetchContactIdFromPhoneNumber(sender))).into(image);


        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sender != null) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(sender, null, mText.getText().toString(), null, null);
                    Toast.makeText(getApplicationContext(), R.string.toast_text, Toast.LENGTH_SHORT).show();
                    mText.setText(" ");
                } else {
                    Toast.makeText(getApplicationContext(), "Dodajte broj", Toast.LENGTH_SHORT).show();

                }

            }
        });

        mButtonInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(sendIntent);
            }
        });


        mbuttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);

                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, sender);
                startActivity(intent);


                /*Intent intentAdd = new Intent();
                intentAdd.setAction(Intent.ACTION_VIEW);
                intentAdd.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivity(intentAdd);*/
            }
        });


    }

    public String getContactName(String number) {
        mbuttonAddContact.setEnabled(false);
        String name = null;
        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = this.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);

        try {
            if (cur.moveToFirst()) {

                name = cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                return name;


            }
        } finally {
            if (cur != null)
                cur.close();
            mbuttonAddContact.setEnabled(true);
        }
        return number;
    }

    public Uri getPhotoUri(String id) {
        try {
            Cursor cur = getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "=" + id + " AND "
                            + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
                    null);
            if (cur != null) {
                if (!cur.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long
                .parseLong(id));
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }

    public String fetchContactIdFromPhoneNumber(String phoneNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = this.getContentResolver().query(uri,
                new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID },
                null, null, null);

        String contactId = "";

        if (cursor.moveToFirst()) {
            do {
                contactId = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.PhoneLookup._ID));
            } while (cursor.moveToNext());
        }

        return contactId;
    }


}
