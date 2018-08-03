package com.example.ubantu.coursaide_admin;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Upload_TT_MT17015 extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private StorageReference sref;
    private static final int SPREADSHEET_REQUEST=123;
    Button choose1,upload1;
    private Uri ttpath;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseStudent;
    private DatabaseReference users,admins,databaseCourses,databaseCourses2,databaseCourses3,databaseCourses4,databaseCourses5 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__tt);
        choose1 = (Button) findViewById(R.id.browse_button);
        upload1 = (Button) findViewById(R.id.upload_button);
        choose1.setOnClickListener(this);
        upload1.setOnClickListener(this);
        sref= FirebaseStorage.getInstance().getReference();
        users= FirebaseDatabase.getInstance().getReference("countAdmins");
        admins= FirebaseDatabase.getInstance().getReference("Admins");
        //databaseCourses = FirebaseDatabase.getInstance().getReference("TimeTablebTmp");
        databaseCourses2 = FirebaseDatabase.getInstance().getReference("TimeTable_MT17015");
        databaseCourses3 = FirebaseDatabase.getInstance().getReference("TimeTable_MT17015");
        databaseCourses4 = FirebaseDatabase.getInstance().getReference("TimeTable_MT17015");
        databaseCourses5 = FirebaseDatabase.getInstance().getReference("TimeTable_MT17015");
        // navigate();

        mAuth= FirebaseAuth.getInstance();
        String email=mAuth.getCurrentUser().getEmail();
        databaseStudent = FirebaseDatabase.getInstance().getReference("Admins");
        Query StuDis = databaseStudent.orderByChild("name").equalTo(email);
        StuDis.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(getApplicationContext(), "Invalid Username and Password!!", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    //startActivity(new Intent(getApplicationContext(), MainActivity_MT17015.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void ttChooser()
    {
        Intent i=new Intent();
        i.setType("text/csv");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Choose the file to be uploaded"),SPREADSHEET_REQUEST);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ttUploader() throws FileNotFoundException
    {

        /*if(ttpath!=null) {
            final ProgressDialog pd=new ProgressDialog(this);
            pd.setTitle("Uploading........");
            pd.show();
            StorageReference ttRef = sref.child("Time_Table/TT1.xlsx");

            ttRef.putFile(ttpath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Upload Complete!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double percentage=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            pd.setMessage("Uploaded : "+(int)percentage+" %");
                        }
                    })
            ;
        }
        else
        {
            Toast.makeText(this, "No file chosen!", Toast.LENGTH_SHORT).show();
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

        ArrayList<String> time=new ArrayList<String>();
        ArrayList<String> mon=new ArrayList<String>();
        ArrayList<String> mStartTime=new ArrayList<String>();
        ArrayList<String> mEndTime=new ArrayList<String>();
        ArrayList<String> tue=new ArrayList<String>();
        ArrayList<String> tuStartTime=new ArrayList<String>();
        ArrayList<String> tuEndTime=new ArrayList<String>();
        ArrayList<String> wed=new ArrayList<String>();
        ArrayList<String> wStartTime=new ArrayList<String>();
        ArrayList<String> wEndTime=new ArrayList<String>();
        ArrayList<String> thurs=new ArrayList<String>();
        ArrayList<String> thStartTime=new ArrayList<String>();
        ArrayList<String> thEndTime=new ArrayList<String>();
        ArrayList<String> fri=new ArrayList<String>();
        ArrayList<String> frStartTime=new ArrayList<String>();
        ArrayList<String> frEndTime=new ArrayList<String>();
        try {
            String line;
            String day="";
            String prevday=null;
            int count=0;
            String prevline=null;
            while ((line = br.readLine()) != null) {
                if(count==0)
                {
                    String[] tmprow=line.split(",");
                    Log.d("Name",tmprow[0]);
                    if(tmprow[0].equals("Btech 3rd Year-Btech 4th Year-Mtech 1st year-Mtech 2nd Year & PhD-Winter Semester 2018"))
                        databaseCourses=FirebaseDatabase.getInstance().getReference("TimeTablePG");
                    else if(tmprow[0].equals("BTech Ist Year(CSE+CSAM) - Section A - Winter Semester 2018"))
                        databaseCourses=FirebaseDatabase.getInstance().getReference("TimeTableUG1");
                    else if(tmprow[0].equals("BTech 2nd Year (CSE)-Winter Semester 2018"))
                        databaseCourses=FirebaseDatabase.getInstance().getReference("TimeTableUG2");
                    else if(tmprow[0].equals("BTech 2nd Year (CSAM)-Winter Semester 2018"))
                        databaseCourses=FirebaseDatabase.getInstance().getReference("TimeTableUG2CSAM");
                    else
                        databaseCourses=FirebaseDatabase.getInstance().getReference("Time_Table_Miscellaneous");
                    databaseCourses3=FirebaseDatabase.getInstance().getReference("Time_Table");

                }
                count++;
                String[] row = line.split(",");
                if(count>2)
                {
                    if(!(row.length==0) && !row[0].equals(""))
                        day=row[0];
                    if(day.equals("MON"))
                    {
                        for(int i=1;i<row.length;i++)
                        {
                            if(row[i].equals("LUNCH"))
                            {
                                continue;
                            }
                            if(!row[i].equals(""))
                            {
                                if((i<=row.length-2) && row[i-1].equals("") && row[i+1].equals(""))
                                {
                                    //databaseCourses = FirebaseDatabase.getInstance().getReference("TimeTable_MT17015/"+day);
                                    Map<String, String> map = new HashMap<>();
                                    //Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                    Log.d("Tag",row[i]);
                                    mon.add(row[i]);
                                    final String tmp=row[i];
                                    String[] t=time.get(i).split("-");
                                    mStartTime.add(t[0]);
                                    Log.d("Tag2",t[0]);
                                    if((i<=row.length-3) && !row[i+2].equals(""))
                                        i=i+2;
                                    else {
                                        i=i+1;
                                        while(row[i].equals(""))
                                            i++;
                                    }
                                    String[] t1=time.get(i).split("-");
                                    mEndTime.add(t1[1]);
                                    Log.d("Tag3",t1[1]);
                                    map.put(t[0]+"-"+t1[1],tmp);
                                    final DatabaseReference dbref=databaseCourses.child(t[0]+"-"+t1[1]);

                                }
                                else if((i<=row.length-3) && !row[i+1].equals("") && !row[i+2].equals(""))
                                {
                                    //Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                    mon.add(row[i]);
                                    Log.d("Tag",row[i]);
                                    String[] t=time.get(i).split("-");
                                    mStartTime.add(t[0]);
                                    Log.d("Tag4",t[0]);
                                    if(time.get(i+3).equals("&"))
                                    {
                                        String[] t1=time.get(i+2).split("-");
                                        mEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    else
                                    {
                                        String[] t1=time.get(i+3).split("-");
                                        mEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    mon.add(row[i+2]);
                                    Log.d("Tag",row[i+2]);
                                    mStartTime.add(t[0]);
                                    Log.d("Tag4",t[0]);
                                    if(time.get(i+3).equals("&"))
                                    {
                                        String[] t1=time.get(i+2).split("-");
                                        mEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    else
                                    {
                                        String[] t1=time.get(i+3).split("-");
                                        mEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    i=i+2;
                                    while(row[i].equals(""))
                                        i++;

                                }

                                else if(count>3)
                                {
                                    String[] prevrow = prevline.split(",");
                                    if((i<=row.length-3) && day.equals(prevday) && !row[i+1].equals("") && row[i+2].equals("") && !prevrow[i+1].equals("") && !prevrow[i+2].equals(""))
                                    {
                                        mon.add(row[i]);
                                        Log.d("Tag",row[i]);
                                        Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                        String[] t=time.get(i).split("-");
                                        Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag6",t[0]);
                                        mStartTime.add(t[0]);
                                        if(time.get(i+3).equals("&"))
                                        {
                                            String[] t1=time.get(i+2).split("-");
                                            Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                            Log.d("Tag7",t1[1]);
                                            mEndTime.add(t1[1]);
                                        }
                                        else
                                        {
                                            String[] t1=time.get(i+3).split("-");
                                            Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                            Log.d("Tag7",t1[1]);
                                            mEndTime.add(t1[1]);
                                        }

                                        i=i+2;
                                        while(row[i].equals(""))
                                            i++;
                                        i--;
                                    }
                                    else if((i<=row.length-3) && day.equals(prevday) && !row[i+1].equals("") && row[i+2].equals("") )
                                    {
                                        mon.add(row[i]);
                                        Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag",row[i]);
                                        String[] t=time.get(i).split("-");
                                        Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag8",t[0]);
                                        mStartTime.add(t[0]);
                                        i++;
                                        while(row[i].equals(""))
                                        {
                                            i++;
                                        }
                                        String[] t1=time.get(i).split("-");
                                        Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag9",t1[1]);
                                        mEndTime.add(t1[1]);
                                    }
                                }
                            }
                        }
                    }
                    else if(day.equals("TUE"))
                    {
                        for(int i=1;i<row.length;i++)
                        {
                            if(!row[i].equals(""))
                            {
                                if((i<=row.length-2) && row[i-1].equals("") && row[i+1].equals(""))
                                {
                                    tue.add(row[i]);
                                    Log.d("Tag",row[i]);
                                    //Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                    String[] t=time.get(i).split("-");
                                    tuStartTime.add(t[0]);
                                    Log.d("Tag2",t[0]);
                                    //Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                    i=i+1;
                                    while(row[i].equals(""))
                                        i++;
                                    String[] t1=time.get(i).split("-");
                                    tuEndTime.add(t1[1]);
                                    Log.d("Tag3",t1[1]);
                                    //Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                }
                                else if((i<=row.length-3) && !row[i+1].equals("") && !row[i+2].equals(""))
                                {
                                    //Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                    tue.add(row[i]);
                                    Log.d("Tag",row[i]);
                                    String[] t=time.get(i).split("-");
                                    tuStartTime.add(t[0]);
                                    Log.d("Tag4",t[0]);
                                    if(time.get(i+3).equals("&"))
                                    {
                                        String[] t1=time.get(i+2).split("-");
                                        tuEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    else
                                    {
                                        String[] t1=time.get(i+3).split("-");
                                        tuEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    tue.add(row[i+2]);
                                    Log.d("Tag",row[i+2]);
                                    tuStartTime.add(t[0]);
                                    Log.d("Tag4",t[0]);
                                    if(time.get(i+3).equals("&"))
                                    {
                                        String[] t1=time.get(i+2).split("-");
                                        tuEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    else
                                    {
                                        String[] t1=time.get(i+3).split("-");
                                        tuEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }

                                    i=i+2;
                                    while(row[i].equals(""))
                                        i++;

                                }
                                else if(count>3)
                                {
                                    String[] prevrow = prevline.split(",");
                                    if((i<=row.length-3) && day.equals(prevday) && !row[i+1].equals("") && row[i+2].equals("") && !prevrow[i+1].equals("") && !prevrow[i+2].equals(""))
                                    {
                                        tue.add(row[i]);
                                        Log.d("Tag",row[i]);
                                        Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                        String[] t=time.get(i).split("-");
                                        Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag6",t[0]);
                                        tuStartTime.add(t[0]);
                                        if(time.get(i+3).equals("&"))
                                        {
                                            String[] t1=time.get(i+2).split("-");
                                            Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                            Log.d("Tag7",t1[1]);
                                            tuEndTime.add(t1[1]);
                                        }
                                        else
                                        {
                                            String[] t1=time.get(i+3).split("-");
                                            Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                            Log.d("Tag7",t1[1]);
                                            tuEndTime.add(t1[1]);
                                        }

                                        i=i+2;
                                        while(row[i].equals(""))
                                            i++;
                                        i--;
                                    }
                                    else if((i<=row.length-3) && day.equals(prevday) && !row[i+1].equals("") && row[i+2].equals("") )
                                    {
                                        tue.add(row[i]);
                                        Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag",row[i]);
                                        String[] t=time.get(i).split("-");
                                        Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag8",t[0]);
                                        tuStartTime.add(t[0]);
                                        i++;
                                        while(row[i].equals(""))
                                        {
                                            i++;
                                        }
                                        String[] t1=time.get(i).split("-");
                                        Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag9",t1[1]);
                                        tuEndTime.add(t1[1]);
                                    }
                                }
                            }
                        }
                    }
                    else if(day.equals("WED"))
                    {
                        for(int i=1;i<row.length;i++)
                        {
                            if(!row[i].equals("Faculty Meeting") && !row[i].equals(""))
                            {
                                if((i<=row.length-2) && row[i-1].equals("") && row[i+1].equals(""))
                                {
                                    wed.add(row[i]);
                                    Log.d("Tag",row[i]);
                                    //Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                    String[] t=time.get(i).split("-");
                                    wStartTime.add(t[0]);
                                    Log.d("Tag2",t[0]);
                                    //Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                    i=i+1;
                                    while(row[i].equals(""))
                                        i++;
                                    String[] t1=time.get(i).split("-");
                                    wEndTime.add(t1[1]);
                                    Log.d("Tag3",t1[1]);
                                    //Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                }
                                else if((i<=row.length-3) && !row[i+1].equals("") && !row[i+2].equals(""))
                                {
                                    //Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                    wed.add(row[i]);
                                    Log.d("Tag",row[i]);
                                    String[] t=time.get(i).split("-");
                                    wStartTime.add(t[0]);
                                    Log.d("Tag4",t[0]);
                                    if(time.get(i+3).equals("&"))
                                    {
                                        String[] t1=time.get(i+2).split("-");
                                        wEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    else
                                    {
                                        String[] t1=time.get(i+3).split("-");
                                        wEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    wed.add(row[i+2]);
                                    Log.d("Tag",row[i+2]);
                                    wStartTime.add(t[0]);
                                    Log.d("Tag4",t[0]);
                                    if(time.get(i+3).equals("&"))
                                    {
                                        String[] t1=time.get(i+2).split("-");
                                        wEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    else
                                    {
                                        String[] t1=time.get(i+3).split("-");
                                        wEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    Log.d("i=",String.valueOf(i));
                                    i=i+2;
                                    while(row[i].equals(""))
                                        i++;
                                    //i--;
                                    Log.d("i=",String.valueOf(i));
                                }
                                else if(count>3)
                                {
                                    String[] prevrow = prevline.split(",");
                                    if((i<=row.length-3) && day.equals(prevday) && !row[i+1].equals("") && row[i+2].equals("") && !prevrow[i+1].equals("") && !prevrow[i+2].equals(""))
                                    {
                                        wed.add(row[i]);
                                        Log.d("Tag",row[i]);
                                        Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                        String[] t=time.get(i).split("-");
                                        Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag6",t[0]);
                                        wStartTime.add(t[0]);
                                        if(time.get(i+3).equals("&"))
                                        {
                                            String[] t1=time.get(i+1).split("-");
                                            Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                            Log.d("Tag7",t1[1]);
                                            wEndTime.add(t1[1]);
                                        }
                                        else
                                        {
                                            String[] t1=time.get(i+2).split("-");
                                            Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                            Log.d("Tag7",t1[1]);
                                            wEndTime.add(t1[1]);
                                        }

                                        i=i+2;
                                        while(row[i].equals(""))
                                            i++;
                                        i--;
                                    }
                                    else if((i<=row.length-3) && day.equals(prevday) && !row[i+1].equals("") && row[i+2].equals("") )
                                    {
                                        wed.add(row[i]);
                                        Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag",row[i]);
                                        String[] t=time.get(i).split("-");
                                        Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag8",t[0]);
                                        wStartTime.add(t[0]);
                                        i++;
                                        while(row[i].equals(""))
                                        {
                                            i++;
                                        }
                                        String[] t1=time.get(i).split("-");
                                        Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag9",t1[1]);
                                        wEndTime.add(t1[1]);
                                    }
                                }
                            }
                        }
                    }
                    else if(day.equals("THU"))
                    {
                        for(int i=1;i<row.length;i++)
                        {
                            if(!row[i].equals(""))
                            {
                                if(row[i].equals("Seminar"))
                                {
                                    i++;
                                    while(row[i].equals(""))
                                        i++;
                                }
                                if((i<=row.length-2) && row[i-1].equals("") && row[i+1].equals(""))
                                {
                                    thurs.add(row[i]);
                                    Log.d("Tag",row[i]);
                                    //Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                    String[] t=time.get(i).split("-");
                                    thStartTime.add(t[0]);
                                    Log.d("Tag2",t[0]);
                                    //Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                    i=i+1;
                                    while(row[i].equals(""))
                                        i++;
                                    String[] t1=time.get(i).split("-");
                                    thEndTime.add(t1[1]);
                                    Log.d("Tag3",t1[1]);
                                    //Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                }
                                else if((i<=row.length-3) && !row[i+1].equals("") && !row[i+2].equals(""))
                                {
                                    //Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                    thurs.add(row[i]);
                                    Log.d("Tag",row[i]);
                                    String[] t=time.get(i).split("-");
                                    thStartTime.add(t[0]);
                                    Log.d("Tag4",t[0]);
                                    if(time.get(i+3).equals("&"))
                                    {
                                        String[] t1=time.get(i+2).split("-");
                                        thEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    else
                                    {
                                        String[] t1=time.get(i+3).split("-");
                                        thEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    i=i+2;
                                    thurs.add(row[i]);
                                    Log.d("Tag",row[i]);
                                    thStartTime.add(t[0]);
                                    Log.d("Tag4",t[0]);
                                    if(time.get(i+1).equals("&"))
                                    {
                                        String[] t1=time.get(i).split("-");
                                        thEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    else
                                    {
                                        String[] t1=time.get(i+1).split("-");
                                        thEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    Log.d("i=",String.valueOf(i));
                                    /*i=i+3;
                                    while(row[i].equals(""))
                                        i++;
                                    i--;*/
                                }
                                else if(count>3)
                                {
                                    String[] prevrow = prevline.split(",");
                                    if((i<=row.length-3) && day.equals(prevday) && !row[i+1].equals("") && row[i+2].equals("") && !prevrow[i+1].equals("") && !prevrow[i+2].equals(""))
                                    {
                                        thurs.add(row[i]);
                                        Log.d("Tag",row[i]);
                                        Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                        String[] t=time.get(i).split("-");
                                        Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag6",t[0]);
                                        thStartTime.add(t[0]);
                                        if(time.get(i+3).equals("&"))
                                        {
                                            String[] t1=time.get(i+2).split("-");
                                            Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                            Log.d("Tag7",t1[1]);
                                            thEndTime.add(t1[1]);
                                        }
                                        else
                                        {
                                            String[] t1=time.get(i+3).split("-");
                                            Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                            Log.d("Tag7",t1[1]);
                                            thEndTime.add(t1[1]);
                                        }

                                        i=i+2;
                                        while(row[i].equals(""))
                                            i++;
                                        i--;
                                    }
                                    else if((i<=row.length-3) && day.equals(prevday) && !row[i+1].equals("") && row[i+2].equals("") )
                                    {
                                        thurs.add(row[i]);
                                        Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag",row[i]);
                                        String[] t=time.get(i).split("-");
                                        Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag8",t[0]);
                                        thStartTime.add(t[0]);
                                        i++;
                                        while(row[i].equals(""))
                                        {
                                            i++;
                                        }
                                        String[] t1=time.get(i).split("-");
                                        Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag9",t1[1]);
                                        thEndTime.add(t1[1]);
                                    }
                                }
                            }
                        }
                    }
                    else if(day.equals("FRI"))
                    {
                        for(int i=1;i<row.length;i++)
                        {
                            if(!row[i].equals(""))
                            {
                                if((i<=row.length-2) && row[i-1].equals("") && row[i+1].equals(""))
                                {
                                    fri.add(row[i]);
                                    Log.d("Tag",row[i]);
                                    //Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                    String[] t=time.get(i).split("-");
                                    frStartTime.add(t[0]);
                                    Log.d("Tag2",t[0]);
                                    //Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                    i=i+1;
                                    while(row[i].equals(""))
                                        i++;
                                    String[] t1=time.get(i).split("-");
                                    frEndTime.add(t1[1]);
                                    Log.d("Tag3",t1[1]);
                                    //Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                }
                                else if((i<=row.length-3) && !row[i+1].equals("") && !row[i+2].equals(""))
                                {
                                    //Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                    fri.add(row[i]);
                                    Log.d("Tag",row[i]);
                                    String[] t=time.get(i).split("-");
                                    frStartTime.add(t[0]);
                                    Log.d("Tag4",t[0]);
                                    if(time.get(i+3).equals("&"))
                                    {
                                        String[] t1=time.get(i+2).split("-");
                                        frEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    else
                                    {
                                        String[] t1=time.get(i+3).split("-");
                                        frEndTime.add(t1[1]);
                                        Log.d("Tag5",t1[1]);
                                    }
                                    fri.add(row[i+2]);
                                    Log.d("Tag",row[i+2]);
                                    frStartTime.add(t[0]);
                                    Log.d("Tag100",t[0]);
                                    if(time.get(i+3).equals("&"))
                                    {
                                        String[] t1=time.get(i+2).split("-");
                                        frEndTime.add(t1[1]);
                                        Log.d("Tag101",t1[1]);
                                    }
                                    else
                                    {
                                        String[] t1=time.get(i+3).split("-");
                                        frEndTime.add(t1[1]);
                                        Log.d("Tag102",t1[1]);
                                    }
                                    i=i+2;
                                    while(row[i].equals(""))
                                        i++;

                                }
                                else if(count>3)
                                {
                                    String[] prevrow = prevline.split(",");
                                    if((i<=row.length-3) && day.equals(prevday) && !row[i+1].equals("") && row[i+2].equals("") && !prevrow[i+1].equals("") && !prevrow[i+2].equals(""))
                                    {
                                        fri.add(row[i]);
                                        Log.d("Tag",row[i]);
                                        Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                        String[] t=time.get(i).split("-");
                                        Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag6",t[0]);
                                        frStartTime.add(t[0]);
                                        if(time.get(i+3).equals("&"))
                                        {
                                            String[] t1=time.get(i+2).split("-");
                                            Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                            Log.d("Tag7",t1[1]);
                                            frEndTime.add(t1[1]);
                                        }
                                        else
                                        {
                                            String[] t1=time.get(i+3).split("-");
                                            Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                            Log.d("Tag7",t1[1]);
                                            frEndTime.add(t1[1]);
                                        }
                                        i=i+2;
                                        while(row[i].equals(""))
                                            i++;
                                        i--;
                                        //Log.d("i:",String.valueOf(i)+row[i]);
                                    }
                                    else if((i<=row.length-3) && day.equals(prevday) && !row[i+1].equals("") && row[i+2].equals("") )
                                    {
                                        fri.add(row[i]);
                                        Toast.makeText(this,row[i], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag",row[i]);
                                        String[] t=time.get(i).split("-");
                                        Toast.makeText(this,t[0], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag8",t[0]);
                                        frStartTime.add(t[0]);
                                        i++;
                                        while(row[i].equals(""))
                                        {
                                            i++;
                                        }
                                        String[] t1=time.get(i).split("-");
                                        Toast.makeText(this,t1[1], Toast.LENGTH_SHORT).show();
                                        Log.d("Tag9",t1[1]);
                                        frEndTime.add(t1[1]);
                                    }
                                }
                            }
                        }
                    }
                    prevline=line;
                    //Toast.makeText(this,prevline.toString(), Toast.LENGTH_LONG).show();
                    prevday=day;
                }
                else if(count==2){
                    time.add("&");
                    for(int i=1;i<row.length;i++)
                    {
                        if(!row[i].equals(""))
                        {
                            time.add(row[i]);
                        }
                        else
                        {
                            time.add("&");
                        }
                    }
                }
                else
                    continue;
            }
            //databaseCourses = FirebaseDatabase.getInstance().getReference("TimeTableTmp");
            for(int m=0;m<mon.size();m++)
            {
                //DatabaseReference dbref=databaseCourses.child(mStartTime.get(m)+"-"+mEndTime.get(m));
                DatabaseReference dbref=databaseCourses.child(mon.get(m));
                DatabaseReference dbref3=databaseCourses3.child(mon.get(m));
                //String id2=dbref.push().getKey();
                Map<String, String> map = new HashMap<>();
                map.put("StartTime",mStartTime.get(m));
                map.put("EndTime",mEndTime.get(m));
                dbref.child("Mon").setValue(map);
                dbref3.child("Mon").setValue(map);
                //dbref.child(Integer.toString(m)).setValue(mon.get(m));
            }

            //databaseCourses2 = FirebaseDatabase.getInstance().getReference("TimeTable_MT17015/Tue");
            for(int t=0;t<tue.size();t++)
            {
                //DatabaseReference dbref=databaseCourses2.child(tuStartTime.get(t)+"-"+tuEndTime.get(t));
                DatabaseReference dbref=databaseCourses.child(tue.get(t));
                DatabaseReference dbref3=databaseCourses3.child(tue.get(t));
                String id2=dbref.push().getKey();
                Map<String, String> map = new HashMap<>();
                map.put("StartTime",tuStartTime.get(t));
                map.put("EndTime",tuEndTime.get(t));
                dbref.child("Tue").setValue(map);
                dbref3.child("Tue").setValue(map);
                //  dbref.child(Integer.toString(t)).setValue(tue.get(t));
            }
            // databaseCourses3 = FirebaseDatabase.getInstance().getReference("TimeTable_MT17015/Wed");
            for(int w=0;w<wed.size();w++)
            {
              /*  DatabaseReference dbref=databaseCourses3.child(wStartTime.get(w)+"-"+wEndTime.get(w));
                String id2=dbref.push().getKey();
                dbref.child(Integer.toString(w)).setValue(wed.get(w));*/
                DatabaseReference dbref=databaseCourses.child(wed.get(w));
                DatabaseReference dbref3=databaseCourses3.child(wed.get(w));
                String id2=dbref.push().getKey();
                Map<String, String> map = new HashMap<>();
                map.put("StartTime",wStartTime.get(w));
                map.put("EndTime",wEndTime.get(w));
                dbref.child("Wed").setValue(map);
                dbref3.child("Wed").setValue(map);
            }
            // databaseCourses4 = FirebaseDatabase.getInstance().getReference("TimeTable_MT17015/Thu");
            for(int th=0;th<thurs.size();th++)
            {
                /*DatabaseReference dbref=databaseCourses4.child(thStartTime.get(th)+"-"+thEndTime.get(th));
                String id2=dbref.push().getKey();
                dbref.child(Integer.toString(th)).setValue(thurs.get(th));*/
                DatabaseReference dbref=databaseCourses.child(thurs.get(th));
                DatabaseReference dbref3=databaseCourses3.child(thurs.get(th));
                String id2=dbref.push().getKey();
                Map<String, String> map = new HashMap<>();
                map.put("StartTime",thStartTime.get(th));
                map.put("EndTime",thEndTime.get(th));
                dbref.child("Thu").setValue(map);
                dbref3.child("Thu").setValue(map);
            }
            //databaseCourses5 = FirebaseDatabase.getInstance().getReference("TimeTable_MT17015/Fri");
            for(int f=0;f<fri.size();f++)
            {
              /*  DatabaseReference dbref=databaseCourses5.child(frStartTime.get(f)+"-"+frEndTime.get(f));
                String id2=dbref.push().getKey();
                dbref.child(Integer.toString(f)).setValue(fri.get(f));*/
                Log.d("Friday values",fri.get(f));
                DatabaseReference dbref=databaseCourses.child(fri.get(f));
                DatabaseReference dbref3=databaseCourses3.child(fri.get(f));
                String id2=dbref.push().getKey();
                Map<String, String> map = new HashMap<>();
                map.put("StartTime",frStartTime.get(f));
                map.put("EndTime",frEndTime.get(f));
                dbref.child("Fri").setValue(map);
                dbref3.child("Fri").setValue(map);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SPREADSHEET_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            ttpath=data.getData();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if(v==choose1)
        {
            ttChooser();
        }
        else if(v==upload1)
        {
            try {
                ttUploader();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
            Intent I = new Intent(Upload_TT_MT17015.this, UpdateMenu_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if (id==R.id.uploadc_menu){
            Intent I = new Intent(Upload_TT_MT17015.this, Upload_Courses_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if (id==R.id.upload_menu ){

            return true;

        }
        //Log-out functionality.
        if (id==R.id.logout_menu){
            FirebaseAuth.getInstance().signOut();
            Intent I = new Intent(Upload_TT_MT17015.this, Admin_login_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if (id==R.id.new_admin_menu){
            Intent I = new Intent(Upload_TT_MT17015.this, AddAdmin_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if (id==R.id.change_pass_menu){
            Intent I = new Intent(Upload_TT_MT17015.this, ChangePassword_MT17015.class);
            startActivity(I);
            finish();
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
                                                        startActivity(new Intent(getApplicationContext(), Admin_login_MT17015.class));
                                                        finish();
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