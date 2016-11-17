package creatingnew.kz.salemgramm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {


    private Button signUpBtn;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Backendless.initApp(this,Const.APP_ID,Const.CLIENT_KEY,Const.VERSION);

        signUpBtn = (Button) findViewById(R.id.loginActivitySignUpBtn);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        logInButton = (Button) findViewById(R.id.loginActivityLogBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpClick();
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogInClick();
            }
        });

    }

    private void onLogInClick() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Backendless.UserService.setCurrentUser(response);
                enterApp2();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("Login","failed to login" + fault.getMessage());
            }
        });




    }

    private void enterApp2() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }



    private void onSignUpClick() {
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
}
