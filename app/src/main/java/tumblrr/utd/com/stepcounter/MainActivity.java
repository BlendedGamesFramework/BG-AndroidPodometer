package tumblrr.utd.com.stepcounter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


import static androidx.core.app.ActivityCompat.startActivityForResult;
import static com.firebase.ui.auth.AuthUI.*;

/*Application: StepCounter
*
* This application keeps a counts of steps taken.
 * Reports back step count along with avg speed of your walking, calories burnt, time taken.
  * A graphical and list view representation of your step count history to keep a track record*/

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private static String GET_ID_PLAYER_URL = "http://164.90.156.141:3010/player_by_email/";

    public static final int REQUEST_CODE = 43256;

    private static final int REQUEST_WRITE_STORAGE = 112;

    List<IdpConfig> provider = Arrays.asList(
            new IdpConfig.FacebookBuilder().build(),
            new IdpConfig.GoogleBuilder().build(),
            new IdpConfig.EmailBuilder().build()
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setting opening screen to activity main layout
        setContentView(R.layout.activity_main);
        //Checking for writing permission
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        //If write permission is not allowed request user to allow
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    System.out.println(user);
                    Log.i("user",user.toString());
                    Toast.makeText(MainActivity.this,"Inicio de sesion exitosa",Toast.LENGTH_SHORT).show();
                    String FINAL_URL = GET_ID_PLAYER_URL+user.getEmail();
                    System.out.println(FINAL_URL);
                    Log.i("finalUrl",FINAL_URL);
                    new RetrieveFeedTask().execute(FINAL_URL);

                }
                else{
                    startActivityForResult(getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(provider)
                    .setIsSmartLockEnabled(false)
                    .build(),REQUEST_CODE
                    );
                }
            }
        };

        //On screen button listener
        buttonClickListener();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }

    /*Method: buttonClickListener()
    * Handling Button click listener on activity main layout
    * Author : Abhilash Gudasi
     */
    private void buttonClickListener(){

        Button start = (Button) findViewById(R.id.button_start);
        Button track = (Button) findViewById(R.id.button_track);
        Button help = (Button) findViewById(R.id.button_help);
        Button exit = (Button) findViewById(R.id.button_exit);

        //Start count Button onclick listener
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent intentPlay = new Intent(getApplicationContext(), StepDetectorActivity.class);
                intentPlay.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentPlay);
            }
        });

        //Track steps Button onclick listener
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent intentScore = new Intent(getApplicationContext(), TrackStepsActivity.class);
                intentScore.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentScore);
            }
        });

        //Help Button onclick listener
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent intentHelp = new Intent(getApplicationContext(), HelpActivity.class);
                intentHelp.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentHelp);
            }
        });

        //Exit App Button onclick listener
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                logout();
                /*
                Intent intentExit = new Intent(Intent.ACTION_MAIN);
                intentExit.addCategory(Intent.CATEGORY_HOME);
                intentExit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentExit);

                 */
            }
        });
    }

    public void logout(){
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this,"Sesion cerrada", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*
    Method to handle permission requests
    Author : Paras Bansal
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted or use the features what required the permission
                    Toast.makeText(MainActivity.this, "The app was allowed to access storage", Toast.LENGTH_LONG).show();
                } else
                {
                    Toast.makeText(MainActivity.this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}
