package com.dbit.messagebroadcasttutorial;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView notify, sendernum, msg, fromtext, msgtext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		notify = (TextView) findViewById(R.id.Notify);
		sendernum = (TextView) findViewById(R.id.SenderNum);
		msg = (TextView) findViewById(R.id.Message);
		msgtext = (TextView) findViewById(R.id.MessageText);
		fromtext = (TextView) findViewById(R.id.FromText);

		registerReceiver(broadcastReceiver, new IntentFilter("broadCastName"));

	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle b = intent.getExtras();

			String Name = b.getString("ContactName");
			String message = b.getString("Message");
			notify.setText("New message received.");
			fromtext.setText("From:");
			msgtext.setText("Message:");
			sendernum.setText(Name);
			msg.setText(message);

		}
	};

}
