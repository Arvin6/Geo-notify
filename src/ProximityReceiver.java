package inovationlabs.location;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

class ProximityReceiver
  extends BroadcastReceiver
{
  private static final int NOTIFICATION_ID = 1000;
  
  private Notification createNotification()
  {
    Object localObject2 = null;
    for (;;)
    {
      try
      {
        Object localObject1 = new Notification();
        Log.d("\nProximity Exception", "Exception in Creating notification " + ((Exception)localObject2).fillInStackTrace());
      }
      catch (Exception localException2)
      {
        try
        {
          ((Notification)localObject1).icon = 2130837588;
          ((Notification)localObject1).when = System.currentTimeMillis();
          ((Notification)localObject1).flags |= 0x10;
          ((Notification)localObject1).flags |= 0x1;
          ((Notification)localObject1).defaults |= 0x2;
          ((Notification)localObject1).defaults |= 0x4;
          ((Notification)localObject1).ledARGB = -1;
          ((Notification)localObject1).ledOnMS = 1500;
          ((Notification)localObject1).ledOffMS = 1500;
          Log.d("\nProximity", "Notification sent");
          return (Notification)localObject1;
        }
        catch (Exception localException1)
        {
          for (;;) {}
        }
        localException2 = localException2;
        localObject1 = localObject2;
        localObject2 = localException2;
      }
    }
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Log.d("\nProximity Receiver", "\n\n*****On Receiving end*****\n");
    NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, MapsActivity.class), 0);
    Notification localNotification = createNotification();
    try
    {
      if (Boolean.valueOf(paramIntent.getBooleanExtra("entering", false)).booleanValue())
      {
        Log.d("Proximity", "entering");
        localNotification.setLatestEventInfo(paramContext, "Proximity Alert!", "You are near your point of interest.", localPendingIntent);
      }
      for (;;)
      {
        localNotificationManager.notify(1000, localNotification);
        return;
        Log.d("Proximity", "exiting");
        localNotification.setLatestEventInfo(paramContext, "Proximity Alert", "Exiting Proximity Region..", localPendingIntent);
      }
      return;
    }
    catch (Exception paramContext)
    {
      Log.d("\nProximity Exception", "Exception on  the receiving end " + paramContext.fillInStackTrace());
    }
  }
}


/* Location:              C:\Program Files\Android\Projects\dex2jar-2.0\classes-dex2jar.jar!\inovationlabs\location\ProximityReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */