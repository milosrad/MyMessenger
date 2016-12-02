package com.example.user.mymessenger.broadcastreceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.user.mymessenger.activity.MainActivity;
import com.example.user.mymessenger.R;
import com.example.user.mymessenger.activity.MainActivity;

/**
 * Created by User on 13.6.2016.
 */
public class CustomBroadcastReceiver extends BroadcastReceiver {

    private SmsManager sms = SmsManager.getDefault();




    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        String sendNum="";
        String message="";


        try{

            if(bundle!=null){

                final Object[] pdusObj = (Object[]) bundle.get("pdus");


                for (int i=0; i<pdusObj.length;i++){

                    SmsMessage currentmessage= SmsMessage.createFromPdu((byte[])pdusObj[i]);

                    String phoneNumber= currentmessage.getDisplayOriginatingAddress();

                    sendNum= phoneNumber;

                    message= currentmessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ sendNum + "; message: " + message);

                //    Toast.makeText(context,"senderNum:"+ sendNum + "; message: " + message,Toast.LENGTH_LONG).show();

                    //   sms.sendTextMessage(sendNum,null,"Ne smaraj",null,null);




                }


                NotificationCompat.Builder mBuilder=
                        new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.newmessage)
                        .setContentTitle("Stigla Vam je nova SMS poruka od: "+ sendNum)
                        .setContentText(message);

                mBuilder.setAutoCancel(true);

                PendingIntent resultPendingIntent;

                Intent resultIntent = new Intent(context,MainActivity.class);

                resultIntent.putExtra("senderNum",sendNum);

                resultIntent.putExtra("sendersmessage",message);

                resultPendingIntent= PendingIntent.getActivity(context,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                mBuilder.setContentIntent(resultPendingIntent);

                int mNotificationId=001;


                NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                mNotifyMgr.notify(mNotificationId,mBuilder.build());




            }

        } catch (Exception e){

            Log.e("SmsReceiver","Exception smsReceiver"+e);

        }

        Toast.makeText(context,"Novi SMS je upravo stigao",Toast.LENGTH_LONG).show();

    }


}

