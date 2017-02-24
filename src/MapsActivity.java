package inovationlabs.location;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity
  extends FragmentActivity
  implements GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener
{
  private static final String PROX_ALERT_INTENT = "com.inovationlabs.location";
  MarkerOptions b = new MarkerOptions();
  Circle c;
  boolean cir = false;
  MarkerOptions cp = new MarkerOptions();
  Marker cu;
  LatLng currentPosition;
  Marker d1;
  LatLng dest;
  boolean first = true;
  private GoogleMap mMap;
  boolean markerClicked = false;
  boolean prox = false;
  TextView t1;
  
  private void dispdist(LatLng paramLatLng)
  {
    float[] arrayOfFloat = new float[3];
    this.t1.setText(paramLatLng.toString());
    try
    {
      double d2 = paramLatLng.latitude;
      double d3 = paramLatLng.longitude;
      if (this.currentPosition != null)
      {
        Log.d("\nClient: ", "[distance],CP not null");
        Location.distanceBetween(this.currentPosition.latitude, this.currentPosition.longitude, d2, d3, arrayOfFloat);
        float f = arrayOfFloat[0] / 1000.0F;
        Toast.makeText(getApplicationContext(), "Distance: " + f + " kms", 0).show();
      }
      return;
    }
    catch (Exception paramLatLng)
    {
      Log.d("Exception:", "Exception in dispdist *CurrentPosition " + paramLatLng.fillInStackTrace());
      return;
    }
    finally
    {
      Log.d("\nClient:", "Distance Calculated and displayed");
    }
  }
  
  private void drawCircle(LatLng paramLatLng)
  {
    CircleOptions localCircleOptions = new CircleOptions();
    localCircleOptions.center(paramLatLng);
    localCircleOptions.radius(500.0D);
    localCircleOptions.strokeColor(-65536);
    localCircleOptions.fillColor(1090453504);
    localCircleOptions.strokeWidth(2.0F);
    if (!this.cir)
    {
      this.c = this.mMap.addCircle(localCircleOptions);
      this.cir = true;
    }
    for (;;)
    {
      Log.d("\nClient: ", "Circle Drawn");
      return;
      try
      {
        this.c.setCenter(paramLatLng);
      }
      catch (Exception paramLatLng)
      {
        Log.d("\nException", "Can't set circle position");
      }
    }
  }
  
  private void setUpMap()
  {
    Log.d("\nClient:", "Map set up");
    this.mMap.setMapType(1);
    this.mMap.setBuildingsEnabled(true);
    this.mMap.setMyLocationEnabled(true);
    Object localObject = (LocationManager)getSystemService("location");
    String str = ((LocationManager)localObject).getBestProvider(new Criteria(), true);
    LocationListener local1 = new LocationListener()
    {
      public void onLocationChanged(Location paramAnonymousLocation)
      {
        MapsActivity.this.showCurrentLocation(paramAnonymousLocation);
      }
      
      public void onProviderDisabled(String paramAnonymousString)
      {
        Toast.makeText(MapsActivity.this.getApplicationContext(), "GPS Disabled", 1).show();
      }
      
      public void onProviderEnabled(String paramAnonymousString)
      {
        Toast.makeText(MapsActivity.this.getApplicationContext(), "GPS Enabled", 0).show();
      }
      
      public void onStatusChanged(String paramAnonymousString, int paramAnonymousInt, Bundle paramAnonymousBundle) {}
    };
    this.t1.setText("Loading Maps...");
    ((LocationManager)localObject).requestLocationUpdates(str, 8000L, 20.0F, local1);
    localObject = ((LocationManager)localObject).getLastKnownLocation(str);
    if (localObject != null) {
      showCurrentLocation((Location)localObject);
    }
  }
  
  private void setUpMapIfNeeded()
  {
    if (this.mMap == null)
    {
      this.mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(2131492948)).getMap();
      if (this.mMap != null) {
        setUpMap();
      }
    }
  }
  
  private void showCurrentLocation(Location paramLocation)
  {
    Log.d("\nClient:", "Updated current location...");
    this.currentPosition = new LatLng(paramLocation.getLatitude(), paramLocation.getLongitude());
    if (this.first)
    {
      this.cu = this.mMap.addMarker(this.cp.position(this.currentPosition).snippet("Lat: " + paramLocation.getLatitude() + ", Lng: " + paramLocation.getLongitude()).icon(BitmapDescriptorFactory.fromResource(2130837587)).flat(false).title("I'm here!"));
      paramLocation = new CameraPosition.Builder().target(this.currentPosition).zoom(18.0F).bearing(0.0F).tilt(50.0F).build();
      this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(paramLocation));
      this.first = false;
      return;
    }
    this.cu.setPosition(this.currentPosition);
    this.cu.setSnippet("Lat: " + paramLocation.getLatitude() + ", Lng: " + paramLocation.getLongitude());
    this.cu.setIcon(BitmapDescriptorFactory.fromResource(2130837587));
    this.cu.setFlat(false);
    this.cu.setTitle("I'm here!");
  }
  
  protected void addProximityAlerts(double paramDouble1, double paramDouble2)
  {
    Log.d("\nClient:", "Into proximity alert function");
    try
    {
      LatLng localLatLng = new LatLng(paramDouble1, paramDouble2);
      LocationManager localLocationManager = (LocationManager)getSystemService("location");
      boolean bool = this.prox;
      if (!bool) {
        try
        {
          Object localObject = new IntentFilter("com.inovationlabs.location");
          registerReceiver(new ProximityReceiver(), (IntentFilter)localObject);
          localObject = new Intent("com.inovationlabs.location");
          ((Intent)localObject).putExtra("Proximity", "GeoFence");
          localLocationManager.addProximityAlert(paramDouble1, paramDouble2, 500.0F, -1L, PendingIntent.getBroadcast(this, 0, (Intent)localObject, 268435456));
          drawCircle(localLatLng);
          Toast.makeText(getApplicationContext(), "Added proximity alert", 0).show();
          this.prox = true;
          Log.d("\nClient:", "Proximity added");
          return;
        }
        catch (Exception localException1)
        {
          for (;;)
          {
            Log.d("Exception:", "Exception in add proximity alert " + localException1.fillInStackTrace());
          }
        }
      }
      localLocationManager.removeProximityAlert(PendingIntent.getBroadcast(this, 0, new Intent("com.inovationlabs.location"), 268435456));
    }
    catch (Exception localException2)
    {
      Log.d("\nException:", "Exception in add proximity");
      return;
    }
    Toast.makeText(getApplicationContext(), "Removed last proximity alert", 0).show();
    this.prox = false;
    Log.d("\nClient:", "Previous proximity removed");
    addProximityAlerts(paramDouble1, paramDouble2);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968598);
    this.t1 = ((TextView)findViewById(2131492949));
    try
    {
      setUpMapIfNeeded();
      this.mMap.setOnMapClickListener(this);
      this.mMap.setOnMapLongClickListener(this);
      this.mMap.setOnMarkerDragListener(this);
      return;
    }
    catch (Exception paramBundle)
    {
      for (;;)
      {
        Log.d("Exception: ", "Exception in OnCreate");
      }
    }
  }
  
  public void onMapClick(LatLng paramLatLng)
  {
    Log.d("\nClient:", "Map clicked");
    this.t1.setText(paramLatLng.toString());
  }
  
  public void onMapLongClick(LatLng paramLatLng)
  {
    this.t1.setText(paramLatLng.toString());
    Log.d("\nClient:", "Map Long clicked");
    for (;;)
    {
      try
      {
        if (this.markerClicked) {
          continue;
        }
        this.d1 = this.mMap.addMarker(this.b.position(paramLatLng).icon(BitmapDescriptorFactory.fromResource(2130837586)).title("Destination Radius").snippet("Geodata: " + paramLatLng.toString()).flat(false).draggable(true));
      }
      catch (Exception localException2)
      {
        Log.d("Exception:", "Exception on Map long click");
        continue;
        this.d1.setPosition(paramLatLng);
        this.d1.setSnippet("Geodata: " + paramLatLng.toString());
        dispdist(paramLatLng);
        continue;
      }
      try
      {
        dispdist(paramLatLng);
        this.markerClicked = true;
        addProximityAlerts(paramLatLng.latitude, paramLatLng.longitude);
        return;
      }
      catch (Exception localException1)
      {
        Log.d("Exception:", "Map Long click point is null,check stacktrace " + localException1.fillInStackTrace());
      }
    }
  }
  
  public void onMarkerDrag(Marker paramMarker)
  {
    this.c.setVisible(false);
    Log.d("\nClient:", "Marker Dragged");
    this.dest = paramMarker.getPosition();
    this.t1.setText(this.dest.toString());
    addProximityAlerts(this.dest.latitude, this.dest.longitude);
  }
  
  public void onMarkerDragEnd(Marker paramMarker)
  {
    this.c.setVisible(true);
    Log.d("\nClient:", "Marker Drag End");
    try
    {
      this.dest = paramMarker.getPosition();
      paramMarker.setSnippet("Geodata: " + this.dest.toString());
      if (this.dest != null) {
        dispdist(this.dest);
      }
      addProximityAlerts(this.dest.latitude, this.dest.longitude);
      return;
    }
    catch (Exception paramMarker)
    {
      for (;;)
      {
        Log.d("Exception:", "Exception on Marker Drag End");
      }
    }
  }
  
  public void onMarkerDragStart(Marker paramMarker)
  {
    this.c.setVisible(false);
    try
    {
      this.dest = paramMarker.getPosition();
      if (this.dest != null) {
        this.t1.setText(this.dest.toString());
      }
      return;
    }
    catch (Exception paramMarker)
    {
      Log.d("Exception:", "Exception on Marker Drag Start");
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    try
    {
      if (this.currentPosition != null)
      {
        Location localLocation = new Location("position");
        localLocation.setLatitude(this.currentPosition.latitude);
        localLocation.setLongitude(this.currentPosition.longitude);
        showCurrentLocation(localLocation);
        return;
      }
      setUpMap();
      return;
    }
    catch (Exception localException)
    {
      Log.d("Exception: ", "Exception in OnResume");
    }
  }
}


/* Location:              C:\Program Files\Android\Projects\dex2jar-2.0\classes-dex2jar.jar!\inovationlabs\location\MapsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
