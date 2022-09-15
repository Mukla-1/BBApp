package de.cau.inf.se.sopro.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import dagger.hilt.android.AndroidEntryPoint;
import de.cau.inf.se.sopro.ApiViewModel;
import de.cau.inf.se.sopro.BuildConfig;
import de.cau.inf.se.sopro.LoginActivity;
import de.cau.inf.se.sopro.MainActivity;
import de.cau.inf.se.sopro.R;
import de.cau.inf.se.sopro.model.GeoData;

@AndroidEntryPoint
public class CommentingFragment extends Fragment {

    private ApiViewModel dashboardViewModel;
    private NavController navController;
    Button button_send;
    EditText text_comment;
    TextView rply;

    Long subprojectID;
    Long commentID;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Api stuff setup
        dashboardViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        dashboardViewModel.get_commentCreationSuccess().observe(getViewLifecycleOwner(),b -> onCommentingSucc(b));

        // Connect to the Nav Controller
        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        this.navController = navHostFragment.getNavController();

        View view2 = inflater.inflate(R.layout.fragment_commenting,container,false);

        button_send = view2.findViewById(R.id.button_send);
        button_send.setOnClickListener(view -> onSendButtonClicked());
        text_comment = view2.findViewById(R.id.text_comment_send);
        rply = view2.findViewById(R.id.reply_text);

        // setup click listener
        rply.setOnClickListener( b -> onReplyTextClicked());

        // Get Important Information from the Bundle
        Bundle b = getArguments();
        if(b.get("commentID") == null){
            subprojectID = b.getLong("subprojectID");
            commentID = 0L;
        }else{
            commentID = b.getLong("commentID");
            subprojectID = b.getLong("subprojectID");
        }


        return view2;

    }

    protected  void onReplyTextClicked() {
        if (!rply.getText().toString().isEmpty()){
            // Reset text
            rply.setText("");
            // Reset Var
            commentID = 0L;
        }
    }

    public void setReplyComment(String author,Long id){
        rply.setText("Antworten auf \n" + author);
        commentID = id;
        text_comment.requestFocus();
    }

    // Return to the specified Fragment
    protected void onSendButtonClicked(){
        // Get Text
        String txt = text_comment.getText().toString();
        // Check if empty
        if(txt.isEmpty()){
            text_comment.setHint("Dein Kommentar war zu inhaltslos");
            return;
        }
        // Send the Comment
        dashboardViewModel.createComment(subprojectID,commentID,txt);
    }
    protected void onCommentingSucc(boolean succ){
        // Refresh if success
        if(succ){
            // Clear Textbox and change default text
            text_comment.setText("");
            text_comment.setHint("Ihr Kommentar wurde hochgeladen");
            // Lad einfach das Fragment neu, updated auch
            Bundle payload = new Bundle();
            payload.putLong("subprojectID",subprojectID);
            navController.navigate(R.id.navigation_subproject_overview,payload);
        }else{
            text_comment.setText("");
            text_comment.setHint("Bei der Erstellung des Kommentars trat ein Fehler auf. Schreib alles nochmal!");
        }


    }



}