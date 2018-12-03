package com.dbit.messagebroadcasttutorial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.gsm.SmsMessage;

@SuppressWarnings("deprecation")
public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String messageReceived = "";
		String senderPhoneNumber = "";
		String displayName = "";
		String ContactName = "";

		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				messageReceived += msgs[i].getMessageBody().toString();
				messageReceived += "\n";
				senderPhoneNumber = msgs[i].getOriginatingAddress();
				// Resolving contact names.
				Uri lookupUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(senderPhoneNumber));
				Cursor c = context.getContentResolver().query(lookupUri,
						new String[] { ContactsContract.Data.DISPLAY_NAME }, null, null, null);
				if (c != null && c.getCount() > 0) {
					try {
						c.moveToFirst();
						displayName = c.getString(0);
						ContactName = displayName;

					} catch (Exception e) {

					} finally {
						c.close();
					}
				} else {
					ContactName = senderPhoneNumber;
				}
			}

			Intent i = new Intent("broadCastName");
			i.putExtra("ContactName", ContactName.toString());
			i.putExtra("Message", messageReceived.toString());
			context.sendBroadcast(i);

		}

	}

}
