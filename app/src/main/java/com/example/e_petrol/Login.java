package com.example.e_petrol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    Button sign_button,login_button;
    FirebaseAuth mfirebase;
    TextView login_mail,login_pass;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mfirebase= FirebaseAuth.getInstance();

        login_mail=findViewById(R.id.login_mail);
        login_pass=findViewById(R.id.login_pass);
        login_button= findViewById(R.id.login_button);
        sign_button=findViewById(R.id.sign_button);

        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
        mAuthStateListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser= mfirebase.getCurrentUser();
                if(mFirebaseUser!=null){
                    Toast.makeText(Login.this,"Giriş yapıldı.",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(Login.this,"Lütfen giriş yapın.",Toast.LENGTH_SHORT).show();

                }
            }
        };

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }



            public void login(){
                if (!validate()) {
                    onLoginFailed();
                    return;
                }
                login_button.setEnabled(false);



                String email=login_mail.getText().toString();
                String pwd=login_pass.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                        R.style.AppTheme_Popup);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                mfirebase.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(Login.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){

                                    new android.os.Handler().postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    // On complete call either onLoginSuccess or onLoginFailed
                                                    onLoginFailed();
                                                    // onLoginFailed();
                                                    progressDialog.dismiss();
                                                }
                                            }, 3000);

                                    return;

                                }
                                else{
                                    new android.os.Handler().postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    // On complete call either onLoginSuccess or onLoginFailed
                                                    onLoginSuccess();
                                                    // onLoginFailed();
                                                    progressDialog.dismiss();
                                                }
                                            }, 3000);
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Intent intent=new Intent(Login.this, General.class);
        startActivity(intent);
        login_button.setEnabled(true);
        finish();
    }
    public void onLoginFailed() {

        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        login_button.setEnabled(true);


    }

    public boolean validate() {
        boolean valid = true;

        String email = login_mail.getText().toString();
        String password = login_pass.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            login_mail.setError("enter a valid email address");
            valid = false;
        } else {
            login_mail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            login_pass.setError("between 4 and 15 alphanumeric characters");
            valid = false;
        } else {
            login_pass.setError(null);
        }

        return valid;
    }





}

