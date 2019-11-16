package com.example.yoursretront;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

//shearprefrenc
//https://www.youtube.com/watch?v=wDI-uCv27UU
//https://www.youtube.com/watch?v=EM2x33g4syY
//https://www.youtube.com/watch?v=b5wzySeFVRk&t=1571s    google sign in
//https://www.youtube.com/watch?v=md3eVaRzdIM       password eye
public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button login;
    TextView textViewSignup,eye;
    private String TAG = "Mainactivity";
    FirebaseAuth firebaseAuth;
    CheckBox savelogincheckbox;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "Blood";
    Boolean savelogin;
    SignInButton sign_in_button;
    GoogleSignInClient mGooglesSignInClient;
    private int RC_SIGN_IN=1;
    ProgressBar progressBar2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            sharedPreferences =getSharedPreferences("Blood",MODE_PRIVATE);
            String a=(sharedPreferences.getString("my_username",null));
            if(a!=null)
            {
                Intent i=new Intent(MainActivity.this,Start_page.class);
                startActivity(i);
            }
            email = findViewById(R.id.email);
            progressBar2 = findViewById(R.id.progressBar2);
            password = findViewById(R.id.password);
            sign_in_button = findViewById(R.id.sign_in_button);
            login = findViewById(R.id.buttonLogin);
            eye = findViewById(R.id.eye);
            textViewSignup = findViewById(R.id.textViewSignup);
progressBar2.setVisibility(View.INVISIBLE);
            savelogincheckbox = findViewById(R.id.checkBox);
            sharedPreferences=getSharedPreferences(mypreference, MODE_PRIVATE);
            // Configure Google Sign In
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGooglesSignInClient= GoogleSignIn.getClient(this,gso);
            firebaseAuth = FirebaseAuth.getInstance();
            eye.setVisibility(View.GONE);
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );

            sign_in_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sign_in_button();
                }
            });
            password.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (password.getText().length() > 0) {
                        eye.setVisibility(View.VISIBLE);
                    } else {
                        eye.setVisibility(View.GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            eye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(eye.getText()=="SHOW")
                    {
                        eye.setText("HIDE");
                        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        password.setSelection(password.length());
                    }
                    else
                    { eye.setText("SHOW");
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                        password.setSelection(password.length());
                    }
                }
            });

            textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signin();
            }
        });
    }
    private void sign_in_button() {
        Intent signInIntent = mGooglesSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void Signin() {
        Log.d(TAG, "sign method:");
        final String eeemail, pppassword;
        eeemail = email.getText().toString();
        pppassword = password.getText().toString();
        if (TextUtils.isEmpty(eeemail)) {
            Toast.makeText(MainActivity.this, "enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pppassword)) {
            Toast.makeText(MainActivity.this, "enter ppassword", Toast.LENGTH_SHORT).show();
            return;
        }

        if (savelogincheckbox.isChecked()) {
            firebaseAuth.signInWithEmailAndPassword(eeemail, pppassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "sucessfull", Toast.LENGTH_SHORT).show();
                        sharedPreferences=getSharedPreferences("Blood", MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("my_username",eeemail);
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, Start_page.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "wrong username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "please Check your check box", Toast.LENGTH_SHORT).show();
        }
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
    @Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e);
            // ...
        }
    }
}
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        progressBar2.setVisibility(View.VISIBLE);
        Toast.makeText(MainActivity.this, "Pleas Wait your Internet is Slow", Toast.LENGTH_SHORT).show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "you are not able to login", Toast.LENGTH_SHORT).show();
                            //  updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        GoogleSignInAccount acct=GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (acct !=null)
        {
            String personName=acct.getDisplayName();
            String persongivenName=acct.getGivenName();
            String personfamilyName=acct.getFamilyName();
            final String personEmail=acct.getEmail();
            String personId=acct.getId();
            String persontoken=acct.getIdToken();
            Uri uri= acct.getPhotoUrl();

            // Toast.makeText(Register.this, "sucessfull", Toast.LENGTH_SHORT).show();
            Upload upload=new Upload(personEmail,persontoken,null,personfamilyName,null);
            // Auth user name ki id get krvane ka liya;
            FirebaseDatabase.getInstance().getReference("Users").child
                    (FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(MainActivity.this, "Registeration  sucessfull", Toast.LENGTH_SHORT).show();
                    sharedPreferences=getSharedPreferences("Blood", MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    progressBar2.setVisibility(View.GONE);
                    editor.putString("my_username",personEmail);
                    editor.commit();
                    Intent i=new Intent(MainActivity.this,Start_page.class);
                    startActivity(i);
                }
            });
        }
        else
        {
            Toast.makeText(this, "alredy have account", Toast.LENGTH_SHORT).show();
        }

    }
}