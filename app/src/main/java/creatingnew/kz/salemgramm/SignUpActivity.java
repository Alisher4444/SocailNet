package creatingnew.kz.salemgramm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    private static final int PROFILE_IMAGE = 1;
    private Button logInBtn;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signUpButton;
    private ImageView profilePicImageView;
    private ProgressDialog dialog;
    private BackendlessFile backendlessFile;
    private Profile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        logInBtn = (Button) findViewById(R.id.logInBtnSignUpActivity);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        signUpButton = (Button) findViewById(R.id.SignUpBtnSignUpActivity);
        profilePicImageView = (ImageView) findViewById(R.id.profilePicImageView);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogInClick();
            }
        });
        
        
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });

        profilePicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageViewClick();
            }
        });


    }


    private void onProfileImageViewClick() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooser = Intent.createChooser(getIntent,"Select Image");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooser,PROFILE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PROFILE_IMAGE){
            if(resultCode == RESULT_OK){
                Bitmap bitmap = null;

                if(data !=null){
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    profilePicImageView.setImageBitmap(bitmap);

                    dialog = new ProgressDialog(this);
                    dialog.setTitle("Uploading picture");
                    dialog.setMessage("Please, wait");
                    dialog.show();


                    final BackendlessUser user = Backendless.UserService.CurrentUser();

                    Backendless.Files.Android.upload(bitmap, Bitmap.CompressFormat.JPEG, 100,
                            "profile", "profiles",
                            new AsyncCallback<BackendlessFile>() {
                                @Override
                                public void handleResponse(BackendlessFile response) {
                                    Log.d("SignUp","upload photo");
                                    backendlessFile = response;
                                    if(dialog.isShowing()){
                                        dialog.dismiss();
                                    }
                                    profile =  new Profile(user,backendlessFile.getFileURL());
                                    dialog = new ProgressDialog(SignUpActivity.this);
                                    dialog.setTitle("Uploading photo");
                                    dialog.setMessage("Please, wait");
                                    dialog.show();

                                    Backendless.Persistence.of(Profile.class).save(profile, new AsyncCallback<Profile>() {
                                        @Override
                                        public void handleResponse(Profile response) {

                                            if(dialog.isShowing()){
                                                dialog.dismiss();
                                            }

                                        }

                                        @Override
                                        public void handleFault(BackendlessFault fault) {
                                            Log.e("SignUp","failed to save post" + fault.getMessage());
                                        }
                                    });

                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Log.e("SignUp","failed " + fault.getMessage());
                                }
                            }
                    );



                }



            }
        }


    }

    private void onClickSignUp() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();



        BackendlessUser user = new  BackendlessUser();
        user.setProperty("name",username);
        user.setPassword(password);


        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText(SignUpActivity.this,"Registered, Please, login.",Toast.LENGTH_SHORT ).show();
                finish();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("SignUp","failed to register" + fault.getMessage());
            }
        });

    }

    private void onLogInClick() {
        finish();
    }
}
