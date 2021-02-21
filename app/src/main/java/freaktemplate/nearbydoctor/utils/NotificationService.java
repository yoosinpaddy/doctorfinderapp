package freaktemplate.nearbydoctor.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import android.util.Log;


import java.util.Random;

import freaktemplate.nearbydoctor.R;


public class NotificationService extends Service {
	private NotificationManager mManager;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		this.getApplicationContext();
		Context context = getApplicationContext();
		// Getting Notification Service
		/*mManager = (NotificationManager) this.getApplicationContext()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Intent intent1 = new Intent(this.getApplicationContext(), Home.class);

		Notification notification = new Notification();
		Context context = getApplicationContext();
		String notificationTitle = "Restaurant";
		String notificationText = "Your table is Booked";
		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1,
				PendingIntent.FLAG_UPDATE_CURRENT);

		Notification.Builder builder = new Notification.Builder(context).setContentIntent(pendingNotificationIntent)
				.setSmallIcon(R.drawable.ic_launcher).setContentTitle(notificationTitle)
				.setContentText(notificationText);
		notification = builder.build();
		
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		builder.setAutoCancel(true);

		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		notification.sound = uri;
		mManager.notify(0, notification);*/
		
		Random r = new Random();
		int i1 = r.nextInt(100 - 1) + 1;

		int icon = R.mipmap.ic_launcher;
		long when = System.currentTimeMillis();
		
		Log.d("when", ""+when);
		String title = context.getString(R.string.app_name);
		/*NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		@SuppressWarnings("deprecation")
		Notification notification = new Notification();

		String title = context.getString(R.string.app_name);

		Intent notificationIntent = new Intent(context, Home.class);
		// set intent so it does not start a new activity
		
		// notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		// Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent1 = PendingIntent.getActivity(context, i1, notificationIntent, 0);

		Notification.Builder builder = new Notification.Builder(context).setContentIntent(intent1).setSmallIcon(icon)
				.setContentTitle(title).setContentText(getString(R.string.app_name));
		builder.setAutoCancel(true);

		notification = builder.build();
		// notification.setLatestEventInfo(context, notificationText,
		// notificationTitle, pendingNotificationIntent);
		// notification.flags = Notification.FLAG_AUTO_CANCEL;

		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		notification.sound = uri;
		// notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);
*/
		NotificationManager notificationManager1 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			CharSequence name = "my_channel";
			String Description = "This is my channel";
			int importance = NotificationManager.IMPORTANCE_HIGH;
			NotificationChannel mChannel = new NotificationChannel(""+i1, name, importance);
			mChannel.setDescription(Description);
			mChannel.enableLights(true);
			mChannel.setLightColor(Color.RED);
			mChannel.enableVibration(true);
			mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
			mChannel.setShowBadge(false);
			notificationManager1.createNotificationChannel(mChannel);
		}

		NotificationCompat.Builder mBuilder1 = new NotificationCompat.Builder(this,""+i1)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(title).setSubText(title)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
						R.mipmap.ic_launcher))
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);

		notificationManager1.notify(123, mBuilder1.build());

		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		// Logger.error("Alam Services Destroyed");
		super.onDestroy();
	}

}
