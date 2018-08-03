package com.example.ubantu.coursaide_admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TimeTable_MT17015 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseStudent;
    ArrayList<String> sub=new ArrayList<>();
    public ArrayList<String> thisSem=new ArrayList<>();
    private DatabaseReference dbtt;
    private TextView tv1,tv2,tv3,tv4,tv5;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.viewprof) {
            startActivity(new Intent(this,View_Profile_MT17015.class));
            finish();
            return true;
        }
        if (id == R.id.sem_course) {
            startActivity(new Intent(this,SemesterCourses_MT17015.class));
            finish();
            return true;
        }
        if(id==R.id.ta_courses){
            Intent I = new Intent(this, TaCourses_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        /*if(id==R.id.get_time_table){
            Intent I = new Intent(this, TimeTable_MT17015.class);
            startActivity(I);
            return true;
        }*/
        if(id==R.id.settings){
            Intent I = new Intent(this, Settings_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        if(id==R.id.Logout){
            FirebaseAuth.getInstance().signOut();
            Intent I = new Intent(this, MainActivity_MT17015.class);
            startActivity(I);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        tv1=(TextView)findViewById(R.id.tvMon);
        tv2=(TextView)findViewById(R.id.tvTue);
        tv3=(TextView)findViewById(R.id.tvWed);
        tv4=(TextView)findViewById(R.id.tvThu);
        tv5=(TextView)findViewById(R.id.tvFri);
        mAuth= FirebaseAuth.getInstance();
        String email=mAuth.getCurrentUser().getEmail();
        databaseStudent = FirebaseDatabase.getInstance().getReference("Student_MT17015");
        Query Stutt = databaseStudent.orderByChild("username").equalTo(email);
        Stutt.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null)
                {
                    Toast.makeText(getApplicationContext(), "Invalid User!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    for (DataSnapshot Snapshot : dataSnapshot.getChildren())
                    {
                        Student_MT17015 stu=Snapshot.getValue(Student_MT17015.class);
                        sub=stu.getSemsubjects();
                    }
                    //Log.d("size:",Integer.toString(sub.size()));
                    if(sub.size()==1 && sub.get(0).equals("None"))
                    {
                        Toast.makeText(getApplicationContext(), "Subjects Not stored!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        final ArrayList<String> Mon=new ArrayList<String>();
                        final ArrayList<String> mStartTime=new ArrayList<String>();
                        final ArrayList<String> mEndTime=new ArrayList<String>();
                        final ArrayList<String> Tue=new ArrayList<String>();
                        final ArrayList<String> tuStartTime=new ArrayList<String>();
                        final ArrayList<String> tuEndTime=new ArrayList<String>();
                        final ArrayList<String> Wed=new ArrayList<String>();
                        final ArrayList<String> wStartTime=new ArrayList<String>();
                        final ArrayList<String> wEndTime=new ArrayList<String>();
                        final ArrayList<String> Thu=new ArrayList<String>();
                        final ArrayList<String> thStartTime=new ArrayList<String>();
                        final ArrayList<String> thEndTime=new ArrayList<String>();
                        final ArrayList<String> Fri=new ArrayList<String>();
                        final ArrayList<String> frStartTime=new ArrayList<String>();
                        final ArrayList<String> frEndTime=new ArrayList<String>();
                        final ArrayList<String> slots=new ArrayList<>();
                        slots.add("8");
                        slots.add("9");
                        slots.add("10");
                        slots.add("11");
                        slots.add("12");
                        slots.add("1");
                        slots.add("2");
                        slots.add("3");
                        slots.add("4");
                        slots.add("5");
                        slots.add("6");
                        slots.add("7");
                        final ArrayList<String> subMon=new ArrayList<String>();
                        final ArrayList<String> subTue=new ArrayList<String>();
                        final ArrayList<String> subWed=new ArrayList<String>();
                        final ArrayList<String> subThu=new ArrayList<String>();
                        final ArrayList<String> subFri=new ArrayList<String>();
                        for(int i=0;i<12;i++)
                        {
                            subMon.add("0");
                            subTue.add("0");
                            subWed.add("0");
                            subThu.add("0");
                            subFri.add("0");
                        }
                        for(int i=0;i<sub.size();i++) {
                            final int pos = i;
                            //Log.d("Tag2", sub.get(i));
                            dbtt = FirebaseDatabase.getInstance().getReference("Time_Table/" + sub.get(i));
                            dbtt.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                        String slotday = Snapshot.getKey();
                                        Slot_MT17015 slot = Snapshot.getValue(Slot_MT17015.class);
                                        //Log.d("Tag2", slotday);
                                        if (slotday.equals("Mon")) {
                                            Mon.add(sub.get(pos));
                                            mStartTime.add(slot.getStartTime());
                                            mEndTime.add(slot.getEndTime());
                                            String arr[]=slot.getStartTime().split(":");
                                            int ind=slots.indexOf(arr[0]);
                                            subMon.set(ind,sub.get(pos));
                                        } else if (slotday.equals("Tue")) {
                                            Tue.add(sub.get(pos));
                                            //days.add("Tue");
                                            tuStartTime.add(slot.getStartTime());
                                            tuEndTime.add(slot.getEndTime());
                                            String arr[]=slot.getStartTime().split(":");
                                            int ind=slots.indexOf(arr[0]);
                                            subTue.set(ind,sub.get(pos));
                                        } else if (slotday.equals("Wed")) {
                                            Wed.add(sub.get(pos));
                                            //days.add("Wed");
                                            wStartTime.add(slot.getStartTime());
                                            wEndTime.add(slot.getEndTime());
                                            String arr[]=slot.getStartTime().split(":");
                                            int ind=slots.indexOf(arr[0]);
                                            subWed.set(ind,sub.get(pos));
                                        } else if (slotday.equals("Thu")) {
                                            Thu.add(sub.get(pos));
                                            //days.add("Thu");
                                            thStartTime.add(slot.getStartTime());
                                            thEndTime.add(slot.getEndTime());
                                            String arr[]=slot.getStartTime().split(":");
                                            int ind=slots.indexOf(arr[0]);
                                            subThu.set(ind,sub.get(pos));
                                        } else if (slotday.equals("Fri")) {
                                            Fri.add(sub.get(pos));
                                            //days.add("Fri");
                                            frStartTime.add(slot.getStartTime());
                                            frEndTime.add(slot.getEndTime());
                                            String arr[]=slot.getStartTime().split(":");
                                            int ind=slots.indexOf(arr[0]);
                                            subFri.set(ind,sub.get(pos));
                                        }
                                    }
                                    if(pos==sub.size()-1)
                                    {
                                        String tt="Monday\n";
                                        if(Mon.size()>0)
                                        {
                                            for(int j=0;j<subMon.size();j++)
                                            {
                                                if(subMon.get(j).equals("0"))
                                                    continue;
                                                else
                                                {
                                                    int ind=Mon.indexOf(subMon.get(j));
                                                    tt=tt+mStartTime.get(ind)+"-"+mEndTime.get(ind)+": "+Mon.get(ind)+"\n";
                                                }
                                            }
                                        }
                                        tv1.setText(tt);
                                        tt="";
                                        tt="Tuesday\n";
                                        if(Tue.size()>0)
                                        {
                                            for(int j=0;j<subTue.size();j++)
                                            {
                                                if(subTue.get(j).equals("0"))
                                                    continue;
                                                else
                                                {
                                                    Log.d("Sub:",subTue.get(j));
                                                    int ind=Tue.indexOf(subTue.get(j));
                                                    tt=tt+tuStartTime.get(ind)+"-"+tuEndTime.get(ind)+": "+Tue.get(ind)+"\n";
                                                }
                                            }
                                        }
                                        tv2.setText(tt);
                                        tt="";
                                        tt="Wednesday\n";
                                        if(Wed.size()>0)
                                        {
                                            for(int j=0;j<subWed.size();j++)
                                            {
                                                if(subWed.get(j).equals("0"))
                                                    continue;
                                                else
                                                {
                                                    int ind=Wed.indexOf(subWed.get(j));
                                                    tt=tt+wStartTime.get(ind)+"-"+wEndTime.get(ind)+": "+Wed.get(ind)+"\n";
                                                }
                                            }
                                        }
                                        tv3.setText(tt);
                                        tt="";
                                        tt="Thursday\n";
                                        if(Thu.size()>0)
                                        {
                                            for(int j=0;j<subThu.size();j++)
                                            {
                                                if(subThu.get(j).equals("0"))
                                                    continue;
                                                else
                                                {
                                                    int ind=Thu.indexOf(subThu.get(j));
                                                    tt=tt+thStartTime.get(ind)+"-"+thEndTime.get(ind)+": "+Thu.get(ind)+"\n";
                                                }
                                            }
                                        }
                                        tv4.setText(tt);
                                        tt="";
                                        tt="Friday\n";
                                        if(Fri.size()>0)
                                        {
                                            for(int j=0;j<subFri.size();j++)
                                            {
                                                if(subFri.get(j).equals("0"))
                                                    continue;
                                                else
                                                {
                                                    Log.d("Sub:",subFri.get(j));
                                                    int ind=Fri.indexOf(subFri.get(j));
                                                    tt=tt+frStartTime.get(ind)+"-"+frEndTime.get(ind)+": "+Fri.get(ind)+"\n";
                                                }
                                            }
                                        }
                                        tv5.setText(tt);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
