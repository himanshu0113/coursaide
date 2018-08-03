package com.example.ubantu.coursaide_admin;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Upload_Courses_MT17015 extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private StorageReference sref;
    private static final int SPREADSHEET_REQUEST=123;
    Button choose1,upload1;
    private Uri ttpath;
    private DatabaseReference users,admins;
    private DatabaseReference databaseCourses,databaseBucket,databaseSpec;
    private static final int REQUEST_PERMISSIONS = 1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload__courses);
        choose1 = (Button) findViewById(R.id.browse_button2);
        upload1 = (Button) findViewById(R.id.upload_button2);
        choose1.setOnClickListener(this);
        upload1.setOnClickListener(this);
        sref= FirebaseStorage.getInstance().getReference();
        users= FirebaseDatabase.getInstance().getReference("countAdmins");
        admins= FirebaseDatabase.getInstance().getReference("Admins");
        databaseBucket= FirebaseDatabase.getInstance().getReference("Bucket_MT17015");
        databaseSpec= FirebaseDatabase.getInstance().getReference("Specialisation");
        databaseCourses = FirebaseDatabase.getInstance().getReference("Courses_MT17015");
        // navigate();
//
//        if (ActivityCompat.checkSelfPermission(Upload_Courses_MT17015.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
//                    REQUEST_PERMISSIONS);
//        }
    }
    private void fileChooser()
    {
        Intent i=new Intent();
        i.setType("text/csv");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Choose the file to be uploaded"),SPREADSHEET_REQUEST);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ttUploader() throws FileNotFoundException
    {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},10);
                return;
            }
        }*/
        String id = DocumentsContract.getDocumentId(ttpath);
        Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

        String path = null;
        String[] projection = { "_data" };

        Cursor cursor = getApplicationContext().getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow("_data");
        if (cursor.moveToFirst()) {
            path = cursor.getString(column_index);
        }
        cursor.close();

        File file = new File(path);

        FileInputStream fis=new FileInputStream(file);
        DataInputStream in=new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        try {
            String line;
            String line2;
            int count=0;
            while ((line = br.readLine()) != null) {
                count++;
                if(count>1)
                {
                    String[] row = line.split(",");
                    //Toast.makeText(this,line, Toast.LENGTH_SHORT).show();
                    final Courses_MT17015 course = new Courses_MT17015();
                    course.setCname(row[0]);
                    course.setCcode(row[1]);
                    course.setCbuck(row[2]);
                    course.setCspec(row[3]);
                    course.setCbranch(row[4]);
                    course.setCredits(row[5]);
                    Query crs = databaseCourses.orderByChild("cname").equalTo(row[0]);
                    crs.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue()==null)
                            {
                                String key = databaseCourses.push().getKey();
                                databaseCourses.child(key).setValue(course);
                            }
                            else
                            {
                                for (DataSnapshot Snapshot : dataSnapshot.getChildren())
                                {
                                    String key=Snapshot.getKey();
                                    Snapshot.getRef().removeValue();
                                    databaseCourses.child(key).setValue(course);
                                }
                                //String key = databaseCourses.push().getKey();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    if(!row[2].equals("None"))
                    {
                        final Bucket_MT17015 bucket=new Bucket_MT17015();
                        bucket.setbName(row[2]);
                        bucket.setBranchName(row[4]);
                        Query bkt = databaseBucket.orderByChild("bName").equalTo(row[2]);
                        bkt.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getValue()==null)
                                {
                                    String key1 = databaseBucket.push().getKey();
                                    databaseBucket.child(key1).setValue(bucket);
                                }
                                else
                                {
                                    for (DataSnapshot Snapshot : dataSnapshot.getChildren())
                                    {
                                        String key=Snapshot.getKey();
                                        Snapshot.getRef().removeValue();
                                        databaseBucket.child(key).setValue(bucket);
                                    }
                                    //String key1 = databaseBucket.push().getKey();

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    if(!row[3].equals("None"))
                    {
                        final Specialisations_MT17015 spec=new Specialisations_MT17015();
                        spec.setSpecialisation(row[3]);
                        spec.setBranch(row[4]);
                        Query spc = databaseSpec.orderByChild("specialisation").equalTo(row[3]);
                        spc.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getValue()==null)
                                {
                                    String key2 = databaseSpec.push().getKey();
                                    databaseSpec.child(key2).setValue(spec);
                                }
                                else
                                {
                                    for (DataSnapshot Snapshot : dataSnapshot.getChildren())
                                    {
                                        String key=Snapshot.getKey();
                                        Snapshot.getRef().removeValue();
                                        databaseSpec.child(key).setValue(spec);
                                    }
                                    //String key2 = databaseSpec.push().getKey();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                }
                else
                    continue;
            }
            Toast.makeText(this, "Courses_MT17015 Added", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(this, "Permission function!", Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case 1: {
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission denied to access your location.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SPREADSHEET_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            ttpath = data.getData();
            Toast.makeText(this, String.valueOf(ttpath), Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if(v==choose1) {
            fileChooser();
        }
        else
        {
            try {
                ttUploader();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "Return", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu,menu);
        return  true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.update_menu){
            Intent I = new Intent(Upload_Courses_MT17015.this, UpdateMenu_MT17015.class);
            startActivity(I);


            return true;
        }
        if (id==R.id.upload_menu ){

            Intent I = new Intent(Upload_Courses_MT17015.this, Upload_TT_MT17015.class);
            startActivity(I);
            return true;
        }
        if (id==R.id.uploadc_menu){
            return true;
        }
        //Log-out functionality.
        if (id==R.id.logout_menu){
            FirebaseAuth.getInstance().signOut();
            Intent I = new Intent(Upload_Courses_MT17015.this, Admin_login_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if (id==R.id.new_admin_menu){
            Intent I = new Intent(Upload_Courses_MT17015.this, AddAdmin_MT17015.class);
            startActivity(I);
            return true;
        }
        if (id==R.id.change_pass_menu){
            Intent I = new Intent(Upload_Courses_MT17015.this, ChangePassword_MT17015.class);
            startActivity(I);
            return true;
        }
        if (id==R.id.del_account){
            new AlertDialog.Builder(this)
                    .setTitle("Delete Account")
                    .setMessage("All information will be lost if you delete your account. Are you sure you want to delete your account?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            users.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() == null)
                                        Toast.makeText(getApplicationContext(), "Could not get data!", Toast.LENGTH_SHORT).show();
                                    else {
                                        Admin_Count_MT17015 adm=new Admin_Count_MT17015();
                                        for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                            adm=Snapshot.getValue(Admin_Count_MT17015.class);
                                        }
                                        long count=adm.getAdmcount();
                                        if((count-1)>0)
                                        {
                                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(getApplicationContext(),"Account deleted successfully!",Toast.LENGTH_SHORT).show();
                                                        FirebaseAuth.getInstance().signOut();
                                                        startActivity(new Intent(getApplicationContext(), MainActivity_MT17015.class));
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(getApplicationContext(),"Account could not be deleted!",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                            for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                                Snapshot.getRef().removeValue();
                                            }
                                            count--;
                                            adm.setAdmcount(count);
                                            String id = users.push().getKey();
                                            users.child(id).setValue(adm);
                                            String email=user.getEmail();
                                            Query remAdm = admins.orderByChild("name").equalTo(email);
                                            remAdm.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                                        Snapshot.getRef().removeValue();
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"Account could not be deleted!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
            return true;
        }

        return true;


    }
}
