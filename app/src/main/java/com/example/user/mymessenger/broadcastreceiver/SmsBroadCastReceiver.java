package com.example.user.mymessenger.broadcastreceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.user.mymessenger.R;
import com.example.user.mymessenger.activity.MainActivity;

/**
 * Created by cubesschool8 on 6/13/16.
 */
public class SmsBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();


                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    Intent newIntent = new Intent(context, MainActivity.class);
                    newIntent.putExtra("message", message);
                    newIntent.putExtra("posiljalac", senderNum);



                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context).setTicker("Poruka od :" + senderNum + message)
                            .setContentText(message)
                            .setContentTitle(senderNum)
                            .setAutoCancel(true)
                            .setSmallIcon(R.mipmap.sms)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(message));




                    notificationBuilder.setContentIntent(pendingIntent);
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(123, notificationBuilder.build());





                }
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }
}
