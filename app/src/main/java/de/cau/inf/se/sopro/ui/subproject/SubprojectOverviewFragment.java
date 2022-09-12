package de.cau.inf.se.sopro.ui.subproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import org.osmdroid.util.GeoPoint;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.SubprojectInfoItem;
import de.cau.inf.se.sopro.ui.home.MapFragment;

/**
 * Klasse für die SubprojectOverView
 * User können folgendes: Titel sehen, Bild sehen, Karte sehen, Infotext lesen, voten, Kommentare benutzen.
 */
@AndroidEntryPoint
public class SubprojectOverviewFragment extends Fragment {


    private NavController navController;
    private ApiViewModel requestViewModel;
    private Long id;
    private SubprojectInfoItem subbi;
    private View view;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //sichtbar machen
        view = inflater.inflate(R.layout.fragment_subproject_overview, container, false);

        //übergebene subprojectID festigen
        Bundle bundi = this.getArguments();
        id = bundi.getLong("subprojectID");

        //api für anfragen nach außen
        requestViewModel =
                new ViewModelProvider(this).get(ApiViewModel.class);


        //subprojektifo anfragen und verfügbar machen
        requestViewModel.getSubprojectInfo(id);
        requestViewModel.get_subprojectInfo().observe(getViewLifecycleOwner(), subprojectInfoItem -> {
            subbi = subprojectInfoItem;
            this.upDateSubbi();

        });





        //für Nachricht an Benutzer, dass Vote abgegeben wurde
        final TextView txtview = view.findViewById(R.id.textView3);
        txtview.setText("Hier können Sie Ihr Vote für das Unterprojekt abgeben!");

        // für ein Unterprojekt voten
        Button button = (Button) view.findViewById(R.id.vote_button);
        button.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {

                requestViewModel.voteSubproject(id);
                updateText("Ihr Vote wurde abgeschickt!");
                requestViewModel.get_subprojectVoteSuccess().observe(getViewLifecycleOwner(), votesucces -> {
                    /**
                    //vote ist durchgegangen oder nicht
                    if (votesucces){
                        updateText("Sie haben erfolgreich für das Projekt gevotet!");
                    }
                    else {
                        updateText("Etwas ist beim voten schiefgelaufen! :(");
                    }*/
                });


            }
        });

       return view;


    }

    private void updateText(String txt){
        final TextView txtview = view.findViewById(R.id.textView3);
        txtview.setText(txt);

    }

    private void upDateSubbi() {
        //Beschriftung einfügen
        final TextView txtview2 = view.findViewById(R.id.textView2);
        txtview2.setText(subbi.getSubprojectDescription());


        // Subprojekttitel einfügen
        final TextView title = view.findViewById(R.id.subtitle);
        title.setText(subbi.getSubprojectName());

        //navigieren
        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = navHostFragment.getNavController();

        // Bild einfügen
        ImageView view2 = view.findViewById(R.id.imageView);
        Glide.with(this).load(subbi.getSubprojectPictureURL()).into(view2);

        //map at the bottom
        MapFragment frg = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_in_spov);
        frg.setUp(17, new GeoPoint(subbi.getSubprojectGeoData().getLatitude(), subbi.getSubprojectGeoData().getLongitude()), new de.cau.inf.se.sopro.model.GeoData[]{subbi.getSubprojectGeoData()});





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
