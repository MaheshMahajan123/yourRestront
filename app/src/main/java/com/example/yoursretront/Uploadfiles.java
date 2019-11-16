package com.example.yoursretront;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
public class Uploadfiles extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private CheckBox nonveg,drinks,Swapping;
    private EditText mEditTextFileName,price,discription;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef,nonmStorageRef,mdrinksstorage;
    private DatabaseReference mDatabaseRef,nonmDatabaseRef,mdrinksdatabase;
    private StorageTask mUploadTask;
    private String TAG = "Upload Files";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadfiles);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);

        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        discription = findViewById(R.id.discription);
        price = findViewById(R.id.price);
             nonveg = findViewById(R.id.nonveg);
        drinks = findViewById(R.id.drinks);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        //nonmStorageRef = FirebaseStorage.getInstance().getReference("Nonveg");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
   //    nonmDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         String Name=mEditTextFileName.getText().toString();
         String Price=price.getText().toString();
         String Dription=discription.getText().toString();
                if(Name.equals(""))
                {
                    Toast.makeText(Uploadfiles.this, "Enter swappingitem  name", Toast.LENGTH_SHORT).show();
                }
                else if(Price.equals(""))
                {
                    Toast.makeText(Uploadfiles.this, "Enter swappingitem enter price", Toast.LENGTH_SHORT).show();
                }
                else if(Dription.equals(""))
                {
                    Toast.makeText(Uploadfiles.this, "Enter swappingitem discription", Toast.LENGTH_SHORT).show();
                }
                else if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Uploadfiles.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else if(drinks.isChecked() && nonveg.isChecked())
                {
                    Toast.makeText(Uploadfiles.this, "Drink not type of Non-veg Choos correct checkbox", Toast.LENGTH_SHORT).show();
                }
                else if(drinks.isChecked() && nonveg.isChecked()&& Swapping.isChecked())
                {
                    Toast.makeText(Uploadfiles.this, "Choos any one correct checkbox", Toast.LENGTH_SHORT).show();
                }
                else if(drinks.isChecked())
                {
                   drinks();
                }
               else if (nonveg.isChecked())
                {
                    nonveg();
                }
               else if(Swapping.isChecked())
                {
                    swappingcard();
                }
                else
                {
                    uploadFile();
                }
            }
        });

    }



    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(Uploadfiles.this, "Upload successful", Toast.LENGTH_LONG).show();

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(Uploadfiles.this, "uploading to db", Toast.LENGTH_SHORT).show();
                                    Uploadfil upload = new Uploadfil(mEditTextFileName.getText().toString(),uri.toString(), price.getText().toString(),discription.getText().toString());
                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(upload);
                                    mEditTextFileName.setText("");
                                    price.setText("");
                                    discription.setText("");


                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Uploadfiles.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });

        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////
    private void drinks() {
        mdrinksstorage = FirebaseStorage.getInstance().getReference("Drinks");
        mdrinksdatabase = FirebaseDatabase.getInstance().getReference("Drinks");
        if (mImageUri != null) {
            final StorageReference fileReference = mdrinksstorage.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(Uploadfiles.this, "Upload successful", Toast.LENGTH_LONG).show();

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(Uploadfiles.this, "uploading to db", Toast.LENGTH_SHORT).show();
                                    Uploadfil upload = new Uploadfil(mEditTextFileName.getText().toString(), uri.toString(), price.getText().toString(), discription.getText().toString());
                                    String uploadId = mdrinksdatabase.push().getKey();
                                    mdrinksdatabase.child(uploadId).setValue(upload);
                                    mEditTextFileName.setText("");
                                    price.setText("");
                                    discription.setText("");

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Uploadfiles.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void swappingcard() {
        mdrinksstorage = FirebaseStorage.getInstance().getReference("Drinks");
        mdrinksdatabase = FirebaseDatabase.getInstance().getReference("Swapping");
        if (mImageUri != null) {
            final StorageReference fileReference = mdrinksstorage.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(Uploadfiles.this, "Upload successful", Toast.LENGTH_LONG).show();

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(Uploadfiles.this, "uploading to db", Toast.LENGTH_SHORT).show();
                                    Uploadfil upload = new Uploadfil(mEditTextFileName.getText().toString(), uri.toString(), price.getText().toString(), discription.getText().toString());
                                    String uploadId = mdrinksdatabase.push().getKey();
                                    mdrinksdatabase.child(uploadId).setValue(upload);
                                    mEditTextFileName.setText("");
                                    price.setText("");
                                    discription.setText("");

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Uploadfiles.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }
    }
        //////////////////////////////////////////////////////////////////////////////////
    private void nonveg()
    {
        nonmStorageRef = FirebaseStorage.getInstance().getReference("Nonveg");
        nonmDatabaseRef = FirebaseDatabase.getInstance().getReference("Nonveg");
        if (mImageUri != null) {
            final StorageReference fileReference = nonmStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(Uploadfiles.this, "Upload successful", Toast.LENGTH_LONG).show();

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(Uploadfiles.this, "uploading to db", Toast.LENGTH_SHORT).show();
                                    Uploadfil upload = new Uploadfil(mEditTextFileName.getText().toString(),uri.toString(), price.getText().toString(),discription.getText().toString());
                                    String uploadId = nonmDatabaseRef.push().getKey();
                                    nonmDatabaseRef.child(uploadId).setValue(upload);
                                    mEditTextFileName.setText("");
                                    price.setText("");
                                    discription.setText("");

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Uploadfiles.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });


        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}