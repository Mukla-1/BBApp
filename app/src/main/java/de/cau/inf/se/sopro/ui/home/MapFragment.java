package de.cau.inf.se.sopro.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import de.cau.inf.se.sopro.BuildConfig;
import de.cau.inf.se.sopro.LoginActivity;
import de.cau.inf.se.sopro.MainActivity;
import de.cau.inf.se.sopro.R;


public class MapFragment extends Fragment {

    private NavController navController;
    MapView map = null;
    Button button_back;

    Integer returnAction;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Connect to the Nav Controller
        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_login);
        this.navController = navHostFragment.getNavController();

        //load/initialize the osmdroid configuration, this can be done
        Context ctx = ((LoginActivity)getActivity()).getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        // This is necessary to....set Relevant User Agent i think...programming is hard man
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map
        //setContentView(R.layout.activity_main);
        View view2 = inflater.inflate(R.layout.fragment_map,container,false);

        button_back = view2.findViewById(R.id.button_back);
        button_back.setOnClickListener(view -> onReturnButtonClicked());

        // Set up the Map and Configure Controls
        map = (MapView) view2.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);



        // get passed in arguments
        Bundle b = this.getArguments();
        returnAction = (Integer) b.get("returnAction");

        // Somehow do Setup
        IMapController mapController = map.getController();
        if(b.get("startZoom") == null){
            mapController.setZoom(12);
        }else{
            mapController.setZoom((int) b.get("startZoom"));
        }
        GeoPoint startPoint = new GeoPoint((Float) b.get("startLatitude"),(Float) b.get("startLongitude"));
        mapController.setCenter(startPoint);






        return view2;

    }
    // Return to the specified Fragment
    protected void onReturnButtonClicked(){
        Bundle payload = new Bundle();
        this.navController.navigate(returnAction, payload);
    }

    // Create Markers on the Map
    private void addMarkers(GeoPoint[] ps,String[] ts){
        int l = ps.length;
        // Check if they have the same Size
        if(l == ts.length){
            for (int i = 0;i < l;i++){
                Marker marker = new Marker(map);
                marker.setPosition(ps[i]);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setTitle(ts[i]);
                map.getOverlays().add(marker);
            }
        }
        return;
    }

    // I just copied this, dont change pls
    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

}
