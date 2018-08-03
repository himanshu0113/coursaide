package com.example.ubantu.coursaide_admin;

/**
 * Created by ubantu on 4/8/18.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class SemesterCourse_fragment_clash_check_MT17015 extends Fragment  {

    private Button mAddButton;
    private Button mClashButton;
    private FloatingActionButton add_fab;
    private int mDynamicCourseAddCount;
    private ArrayList<AutoCompleteTextView> dynids = new ArrayList<AutoCompleteTextView>();
    private DatabaseReference dbtt,dbtt2;
    private String clashes2;
    private int flag=0;
    private int t;
    private LinearLayout parentLinearLayout;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseStudent;
    private ArrayList<String> Ccourses=new ArrayList<String>();
    private ArrayList<String> Courses2=new ArrayList<String>();
    private ArrayList<String> clashsub = new ArrayList<String>();
    private ArrayList<String> subclash = new ArrayList<String>();
    private ArrayList<String> clashtime = new ArrayList<String>();
    private ArrayList<String> clashday = new ArrayList<String>();
    private AlertDialog.Builder build;

    private static final String[] COUNTRIES = new String[] { // THIS IS THE LIST OF ALL COURSES
            "Graduate Algorithms", "Wireless Networks", "Mobile Computing", "Machine Learning", "FPP"
            , "RM", "PGM", "AIOT"
    };

    public SemesterCourse_fragment_clash_check_MT17015() {
        mDynamicCourseAddCount=0;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.semester_course_fragment_clash_check, container, false);


        add_fab= (FloatingActionButton)rootView.findViewById(R.id.add_course_fab);
        mAddButton=(Button)rootView.findViewById(R.id.clasg_check_button);
        mClashButton=(Button)rootView.findViewById(R.id.clasg_check_button2);
        final LinearLayout parentLinearLayout= rootView.findViewById(R.id.parent_Linear_Layout);
        mAuth = FirebaseAuth.getInstance();
        build = new AlertDialog.Builder(getActivity());
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(mDynamicCourseAddCount<10) {

                    final View child_view = inflater.inflate(R.layout.field, null);
                    child_view.setId(mDynamicCourseAddCount);
                    Button del_Button = (Button) child_view.findViewById(R.id.dynamic_button);
                    final AutoCompleteTextView dynamic_cname = (AutoCompleteTextView) child_view.findViewById(R.id.dynamic_text);
                    del_Button.setId(mDynamicCourseAddCount);
                    dynamic_cname.setId(mDynamicCourseAddCount);
                    dynamic_cname.setAdapter(new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,COUNTRIES));
                    dynids.add(dynamic_cname);
                    parentLinearLayout.addView(child_view);
                    mDynamicCourseAddCount += 1;
                    del_Button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            parentLinearLayout.removeView(child_view);
                            mDynamicCourseAddCount-=1;
                            dynids.remove(dynamic_cname);
                        }
                    });

                }
                else {
                    Toast.makeText( getContext(),"You can only enter 10 courses",Toast.LENGTH_SHORT).show();
                }


            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                t=0;
                final ArrayList<String> Mon = new ArrayList<String>();
                final ArrayList<String> mStartTime = new ArrayList<String>();
                final ArrayList<String> mEndTime = new ArrayList<String>();
                final ArrayList<String> Tue = new ArrayList<String>();
                final ArrayList<String> tuStartTime = new ArrayList<String>();
                final ArrayList<String> tuEndTime = new ArrayList<String>();
                final ArrayList<String> Wed = new ArrayList<String>();
                final ArrayList<String> wStartTime = new ArrayList<String>();
                final ArrayList<String> wEndTime = new ArrayList<String>();
                final ArrayList<String> Thu = new ArrayList<String>();
                final ArrayList<String> thStartTime = new ArrayList<String>();
                final ArrayList<String> thEndTime = new ArrayList<String>();
                final ArrayList<String> Fri = new ArrayList<String>();
                final ArrayList<String> frStartTime = new ArrayList<String>();
                final ArrayList<String> frEndTime = new ArrayList<String>();

                for (int i = 0; i < dynids.size(); i++) {
                    Ccourses.add(dynids.get(i).getText().toString());
                }
                dbtt2 = FirebaseDatabase.getInstance().getReference("Time_Table");
                dbtt2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (int t = 0; t < Ccourses.size(); t++) {
                            String curcourse = Ccourses.get(t);
                            for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                String subject = Snapshot.getKey();
                                Log.d("subject:", subject);
                                if (subject.contains(curcourse) && !(Ccourses.contains(subject)) && (subject.contains("Tut") || subject.contains("Lab")) && !(subject.equals(curcourse))) {
                                    {
                                        Ccourses.add(subject);
                                        Log.d("Lab Added:", subject);
                                    }
                                }
                            }
                        }
                        for (int j = 0; j < Ccourses.size(); j++) {
                            final String cname = Ccourses.get(j);
                            Log.d("Tag", "Time_Table/" + cname);
                            final ArrayList<String> days = new ArrayList<String>();
                            dbtt = FirebaseDatabase.getInstance().getReference("Time_Table/" + cname);
                            //Query remspec = databaseCourses.orderByChild("cname").equalTo(cname);
                            dbtt.addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                                String slotday = Snapshot.getKey();
                                                Slot_MT17015 slot = Snapshot.getValue(Slot_MT17015.class);
                                                Log.d("Tag2", slotday);
                                                if (slotday.equals("Mon")) {
                                                    Mon.add(cname);
                                                    Log.d("Tag3", String.valueOf(Mon.size()));
                                                    //days.add("Mon");
                                                    mStartTime.add(slot.getStartTime());
                                                    mEndTime.add(slot.getEndTime());
                                                } else if (slotday.equals("Tue")) {
                                                    Tue.add(cname);
                                                    //days.add("Tue");
                                                    tuStartTime.add(slot.getStartTime());
                                                    tuEndTime.add(slot.getEndTime());
                                                } else if (slotday.equals("Wed")) {
                                                    Wed.add(cname);
                                                    //days.add("Wed");
                                                    wStartTime.add(slot.getStartTime());
                                                    wEndTime.add(slot.getEndTime());
                                                } else if (slotday.equals("Thu")) {
                                                    Thu.add(cname);
                                                    //days.add("Thu");
                                                    thStartTime.add(slot.getStartTime());
                                                    thEndTime.add(slot.getEndTime());
                                                } else if (slotday.equals("Fri")) {
                                                    Fri.add(cname);
                                                    //days.add("Fri");
                                                    frStartTime.add(slot.getStartTime());
                                                    frEndTime.add(slot.getEndTime());
                                                }
                                            }
                                            for (int j = 0; j < Ccourses.size(); j++) {
                                                for (int k = 0; k < Ccourses.size(); k++) {
                                                    if (Mon.contains(Ccourses.get(j)) && Mon.contains(Ccourses.get(k)) && j != k) {
                                                        int ind1 = Mon.indexOf(Ccourses.get(j));
                                                        int ind2 = Mon.indexOf(Ccourses.get(k));
                                                        String s1, s2, e1, e2;
                                                        s1 = mStartTime.get(ind1);
                                                        s2 = mStartTime.get(ind2);
                                                        e1 = mEndTime.get(ind1);
                                                        e2 = mEndTime.get(ind2);
                                                        float fs1, fs2, fe1, fe2;
                                                        if (s1.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fs1 = (float) (Integer.parseInt(s1));
                                                            if (fs1 < 8.0)
                                                                fs1 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fs1));
                                                        } else {
                                                            String[] ss1 = s1.split(":");
                                                            fs1 = (float) (Integer.parseInt(ss1[0]));
                                                            if (fs1 < 8.0)
                                                                fs1 += 12.0;
                                                            fs1 += (((float) (Integer.parseInt(ss1[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fs1));
                                                        }
                                                        if (s2.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fs2 = (float) (Integer.parseInt(s2));
                                                            if (fs2 < 8.0)
                                                                fs2 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fs2));
                                                        } else {
                                                            String[] ss2 = s2.split(":");
                                                            fs2 = (float) (Integer.parseInt(ss2[0]));
                                                            if (fs2 < 8.0)
                                                                fs2 += 12.0;
                                                            fs2 += (((float) (Integer.parseInt(ss2[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fs2));
                                                        }
                                                        if (e1.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fe1 = (float) (Integer.parseInt(e1));
                                                            if (fe1 < 8.0)
                                                                fe1 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fe1));
                                                        } else {
                                                            String[] es1 = e1.split(":");
                                                            fe1 = (float) (Integer.parseInt(es1[0]));
                                                            if (fe1 < 8.0)
                                                                fe1 += 12.0;
                                                            fe1 += (((float) (Integer.parseInt(es1[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fe1));
                                                        }
                                                        if (e2.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fe2 = (float) (Integer.parseInt(e2));
                                                            if (fe2 < 8.0)
                                                                fe2 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fe2));
                                                        } else {
                                                            String[] es2 = e2.split(":");
                                                            fe2 = (float) (Integer.parseInt(es2[0]));
                                                            if (fe2 < 8.0)
                                                                fe2 += 12.0;
                                                            fe2 += (((float) (Integer.parseInt(es2[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fe2));
                                                        }
                                                        if ((fs2 >= fs1 && fs2 < fe1) || (fs1 >= fs2 && fs1 < fe2)) {
                                                            if (clashsub.indexOf(Ccourses.get(k) + "     " + Ccourses.get(j)) == -1) {
                                                                clashsub.add(Ccourses.get(j) + "     " + Ccourses.get(k));
                                                                subclash.add(Ccourses.get(j));
                                                                subclash.add(Ccourses.get(k));
                                                                clashtime.add(s1 + "-" + e1 + "     " + s2 + "-" + e2);
                                                                clashday.add("Monday");
                                                            }
                                                        }

                                                    }
                                                    if (Tue.contains(Ccourses.get(j)) && Tue.contains(Ccourses.get(k)) && j != k) {
                                                        int ind1 = Tue.indexOf(Ccourses.get(j));
                                                        int ind2 = Tue.indexOf(Ccourses.get(k));
                                                        String s1, s2, e1, e2;
                                                        s1 = tuStartTime.get(ind1);
                                                        s2 = tuStartTime.get(ind2);
                                                        e1 = tuEndTime.get(ind1);
                                                        e2 = tuEndTime.get(ind2);
                                                        float fs1, fs2, fe1, fe2;
                                                        if (s1.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fs1 = (float) (Integer.parseInt(s1));
                                                            if (fs1 < 8.0)
                                                                fs1 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fs1));
                                                        } else {
                                                            String[] ss1 = s1.split(":");
                                                            fs1 = (float) (Integer.parseInt(ss1[0]));
                                                            if (fs1 < 8.0)
                                                                fs1 += 12.0;
                                                            fs1 += (((float) (Integer.parseInt(ss1[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fs1));
                                                        }
                                                        if (s2.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fs2 = (float) (Integer.parseInt(s2));
                                                            if (fs2 < 8.0)
                                                                fs2 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fs2));
                                                        } else {
                                                            String[] ss2 = s2.split(":");
                                                            fs2 = (float) (Integer.parseInt(ss2[0]));
                                                            if (fs2 < 8.0)
                                                                fs2 += 12.0;
                                                            fs2 += (((float) (Integer.parseInt(ss2[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fs2));
                                                        }
                                                        if (e1.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fe1 = (float) (Integer.parseInt(e1));
                                                            if (fe1 < 8.0)
                                                                fe1 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fe1));
                                                        } else {
                                                            String[] es1 = e1.split(":");
                                                            fe1 = (float) (Integer.parseInt(es1[0]));
                                                            if (fe1 < 8.0)
                                                                fe1 += 12.0;
                                                            fe1 += (((float) (Integer.parseInt(es1[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fe1));
                                                        }
                                                        if (e2.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fe2 = (float) (Integer.parseInt(e2));
                                                            if (fe2 < 8.0)
                                                                fe2 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fe2));
                                                        } else {
                                                            String[] es2 = e2.split(":");
                                                            fe2 = (float) (Integer.parseInt(es2[0]));
                                                            if (fe2 < 8.0)
                                                                fe2 += 12.0;
                                                            fe2 += (((float) (Integer.parseInt(es2[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fe2));
                                                        }
                                                        if ((fs2 >= fs1 && fs2 < fe1) || (fs1 >= fs2 && fs1 < fe2)) {
                                                            if (clashsub.indexOf(Ccourses.get(k) + "     " + Ccourses.get(j)) == -1) {
                                                                clashsub.add(Ccourses.get(j) + "     " + Ccourses.get(k));
                                                                subclash.add(Ccourses.get(j));
                                                                subclash.add(Ccourses.get(k));
                                                                clashtime.add(s1 + "-" + e1 + "     " + s2 + "-" + e2);
                                                                clashday.add("Tuesday");
                                                            }
                                                        }

                                                    }
                                                    if (Wed.contains(Ccourses.get(j)) && Wed.contains(Ccourses.get(k)) && j != k) {
                                                        int ind1 = Wed.indexOf(Ccourses.get(j));
                                                        int ind2 = Wed.indexOf(Ccourses.get(k));
                                                        String s1, s2, e1, e2;
                                                        s1 = wStartTime.get(ind1);
                                                        s2 = wStartTime.get(ind2);
                                                        e1 = wEndTime.get(ind1);
                                                        e2 = wEndTime.get(ind2);
                                                        float fs1, fs2, fe1, fe2;
                                                        if (s1.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fs1 = (float) (Integer.parseInt(s1));
                                                            if (fs1 < 8.0)
                                                                fs1 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fs1));
                                                        } else {
                                                            String[] ss1 = s1.split(":");
                                                            fs1 = (float) (Integer.parseInt(ss1[0]));
                                                            if (fs1 < 8.0)
                                                                fs1 += 12.0;
                                                            fs1 += (((float) (Integer.parseInt(ss1[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fs1));
                                                        }
                                                        if (s2.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fs2 = (float) (Integer.parseInt(s2));
                                                            if (fs2 < 8.0)
                                                                fs2 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fs2));
                                                        } else {
                                                            String[] ss2 = s2.split(":");
                                                            fs2 = (float) (Integer.parseInt(ss2[0]));
                                                            if (fs2 < 8.0)
                                                                fs2 += 12.0;
                                                            fs2 += (((float) (Integer.parseInt(ss2[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fs2));
                                                        }
                                                        if (e1.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fe1 = (float) (Integer.parseInt(e1));
                                                            if (fe1 < 8.0)
                                                                fe1 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fe1));
                                                        } else {
                                                            String[] es1 = e1.split(":");
                                                            fe1 = (float) (Integer.parseInt(es1[0]));
                                                            if (fe1 < 8.0)
                                                                fe1 += 12.0;
                                                            fe1 += (((float) (Integer.parseInt(es1[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fe1));
                                                        }
                                                        if (e2.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fe2 = (float) (Integer.parseInt(e2));
                                                            if (fe2 < 8.0)
                                                                fe2 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fe2));
                                                        } else {
                                                            String[] es2 = e2.split(":");
                                                            fe2 = (float) (Integer.parseInt(es2[0]));
                                                            if (fe2 < 8.0)
                                                                fe2 += 12.0;
                                                            fe2 += (((float) (Integer.parseInt(es2[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fe2));
                                                        }
                                                        if ((fs2 >= fs1 && fs2 < fe1) || (fs1 >= fs2 && fs1 < fe2)) {
                                                            if (clashsub.indexOf(Ccourses.get(k) + "     " + Ccourses.get(j)) == -1) {
                                                                clashsub.add(Ccourses.get(j) + "     " + Ccourses.get(k));
                                                                subclash.add(Ccourses.get(j));
                                                                subclash.add(Ccourses.get(k));
                                                                clashtime.add(s1 + "-" + e1 + "     " + s2 + "-" + e2);
                                                                clashday.add("Wednesday");
                                                            }
                                                        }

                                                    }
                                                    if (Thu.contains(Ccourses.get(j)) && Thu.contains(Ccourses.get(k)) && j != k) {
                                                        int ind1 = Thu.indexOf(Ccourses.get(j));
                                                        int ind2 = Thu.indexOf(Ccourses.get(k));
                                                        String s1, s2, e1, e2;
                                                        s1 = thStartTime.get(ind1);
                                                        s2 = thStartTime.get(ind2);
                                                        e1 = thEndTime.get(ind1);
                                                        e2 = thEndTime.get(ind2);
                                                        float fs1, fs2, fe1, fe2;
                                                        if (s1.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fs1 = (float) (Integer.parseInt(s1));
                                                            if (fs1 < 8.0)
                                                                fs1 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fs1));
                                                        } else {
                                                            String[] ss1 = s1.split(":");
                                                            fs1 = (float) (Integer.parseInt(ss1[0]));
                                                            if (fs1 < 8.0)
                                                                fs1 += 12.0;
                                                            fs1 += (((float) (Integer.parseInt(ss1[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fs1));
                                                        }
                                                        if (s2.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fs2 = (float) (Integer.parseInt(s2));
                                                            if (fs2 < 8.0)
                                                                fs2 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fs2));
                                                        } else {
                                                            String[] ss2 = s2.split(":");
                                                            fs2 = (float) (Integer.parseInt(ss2[0]));
                                                            if (fs2 < 8.0)
                                                                fs2 += 12.0;
                                                            fs2 += (((float) (Integer.parseInt(ss2[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fs2));
                                                        }
                                                        if (e1.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fe1 = (float) (Integer.parseInt(e1));
                                                            if (fe1 < 8.0)
                                                                fe1 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fe1));
                                                        } else {
                                                            String[] es1 = e1.split(":");
                                                            fe1 = (float) (Integer.parseInt(es1[0]));
                                                            if (fe1 < 8.0)
                                                                fe1 += 12.0;
                                                            fe1 += (((float) (Integer.parseInt(es1[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fe1));
                                                        }
                                                        if (e2.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fe2 = (float) (Integer.parseInt(e2));
                                                            if (fe2 < 8.0)
                                                                fe2 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fe2));
                                                        } else {
                                                            String[] es2 = e2.split(":");
                                                            fe2 = (float) (Integer.parseInt(es2[0]));
                                                            if (fe2 < 8.0)
                                                                fe2 += 12.0;
                                                            fe2 += (((float) (Integer.parseInt(es2[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fe2));
                                                        }
                                                        if ((fs2 >= fs1 && fs2 < fe1) || (fs1 >= fs2 && fs1 < fe2)) {
                                                            if (clashsub.indexOf(Ccourses.get(k) + "     " + Ccourses.get(j)) == -1) {
                                                                clashsub.add(Ccourses.get(j) + "     " + Ccourses.get(k));
                                                                subclash.add(Ccourses.get(j));
                                                                subclash.add(Ccourses.get(k));
                                                                clashtime.add(s1 + "-" + e1 + "     " + s2 + "-" + e2);
                                                                clashday.add("Thursday");
                                                            }
                                                        }

                                                    }
                                                    if (Fri.contains(Ccourses.get(j)) && Fri.contains(Ccourses.get(k)) && j != k) {
                                                        int ind1 = Fri.indexOf(Ccourses.get(j));
                                                        int ind2 = Fri.indexOf(Ccourses.get(k));
                                                        String s1, s2, e1, e2;
                                                        s1 = frStartTime.get(ind1);
                                                        s2 = frStartTime.get(ind2);
                                                        e1 = frEndTime.get(ind1);
                                                        e2 = frEndTime.get(ind2);
                                                        float fs1, fs2, fe1, fe2;
                                                        if (s1.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fs1 = (float) (Integer.parseInt(s1));
                                                            if (fs1 < 8.0)
                                                                fs1 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fs1));
                                                        } else {
                                                            String[] ss1 = s1.split(":");
                                                            fs1 = (float) (Integer.parseInt(ss1[0]));
                                                            if (fs1 < 8.0)
                                                                fs1 += 12.0;
                                                            fs1 += (((float) (Integer.parseInt(ss1[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fs1));
                                                        }
                                                        if (s2.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fs2 = (float) (Integer.parseInt(s2));
                                                            if (fs2 < 8.0)
                                                                fs2 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fs2));
                                                        } else {
                                                            String[] ss2 = s2.split(":");
                                                            fs2 = (float) (Integer.parseInt(ss2[0]));
                                                            if (fs2 < 8.0)
                                                                fs2 += 12.0;
                                                            fs2 += (((float) (Integer.parseInt(ss2[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fs2));
                                                        }
                                                        if (e1.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fe1 = (float) (Integer.parseInt(e1));
                                                            if (fe1 < 8.0)
                                                                fe1 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fe1));
                                                        } else {
                                                            String[] es1 = e1.split(":");
                                                            fe1 = (float) (Integer.parseInt(es1[0]));
                                                            if (fe1 < 8.0)
                                                                fe1 += 12.0;
                                                            fe1 += (((float) (Integer.parseInt(es1[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fe1));
                                                        }
                                                        if (e2.indexOf(":") == -1) {
                                                            //String[] ss1=s1.split()
                                                            fe2 = (float) (Integer.parseInt(e2));
                                                            if (fe2 < 8.0)
                                                                fe2 += 12.0;
                                                            Log.d("Tag3", String.valueOf(fe2));
                                                        } else {
                                                            String[] es2 = e2.split(":");
                                                            fe2 = (float) (Integer.parseInt(es2[0]));
                                                            if (fe2 < 8.0)
                                                                fe2 += 12.0;
                                                            fe2 += (((float) (Integer.parseInt(es2[1]))) / 60.0);
                                                            Log.d("Tag3", String.valueOf(fe2));
                                                        }
                                                        if ((fs2 >= fs1 && fs2 < fe1) || (fs1 >= fs2 && fs1 < fe2)) {
                                                            if (clashsub.indexOf(Ccourses.get(k) + "     " + Ccourses.get(j)) == -1) {
                                                                clashsub.add(Ccourses.get(j) + "     " + Ccourses.get(k));
                                                                subclash.add(Ccourses.get(j));
                                                                subclash.add(Ccourses.get(k));
                                                                clashtime.add(s1 + "-" + e1 + "     " + s2 + "-" + e2);
                                                                clashday.add("Friday");
                                                            }
                                                        }

                                                    }

                                                }
                                            }
                                            View v = getLayoutInflater().inflate(R.layout.clashdialog, null);
                                            TextView clasher = (TextView) v.findViewById(R.id.clashview);
                                            if(!(clasher.getText().toString().equals("")))
                                                clasher.setText("");
                                            Button savebutton = (Button) v.findViewById(R.id.button3);
                                            for (int l = 0; l < Ccourses.size(); l++) {
                                                if (subclash.indexOf(Ccourses.get(l)) == -1) {
                                                    Spannable word = new SpannableString("\n" + Ccourses.get(l));
                                                    word.setSpan(new ForegroundColorSpan(Color.GREEN), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                    if (clasher.getText().toString().equals(""))
                                                        clasher.setText(word);
                                                    else
                                                        clasher.append(word);
                                                }
                                            }
                                            //String clashes="";
                                            for (int l = 0; l < clashsub.size(); l++) {
                                                String clashes = "";
                                                Log.d("Tag1", clashsub.get(l));
                                                Log.d("Tag2", clashday.get(l));
                                                Log.d("Tag3", clashtime.get(l));
                                                clashes += "\n" + clashday.get(l) + "\n" + clashtime.get(l) + "\n" + clashsub.get(l);
                                                Spannable word = new SpannableString(clashes);
                                                word.setSpan(new ForegroundColorSpan(Color.RED), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                if (clasher.getText().toString().equals(""))
                                                    clasher.setText(word);
                                                else if(clasher.getText().toString().contains(word))
                                                {
                                                    //Do Nothing
                                                }
                                                else
                                                    clasher.append(word);
                                                Toast.makeText(getActivity(), clashsub.get(l), Toast.LENGTH_SHORT).show();
                                            }
                                            clasher.setTypeface(clasher.getTypeface(), Typeface.BOLD);
                                            clasher.setTextSize(20);
                                            final int noclash;
                                            if (clashsub.size() == 0)
                                                noclash = 1;
                                            else
                                                noclash = 0;
                                            Log.d("Tag7", String.valueOf(clashsub.size()));
                                            savebutton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (noclash == 1) {
                                                        final ArrayList<String> semsub;
                                                        semsub = Courses2;
                                                        String email = mAuth.getCurrentUser().getEmail();
                                                        databaseStudent = FirebaseDatabase.getInstance().getReference("Student_MT17015");
                                                        Query StuDis = databaseStudent.orderByChild("username").equalTo(email);
                                                        StuDis.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                                                    Student_MT17015 stu = Snapshot.getValue(Student_MT17015.class);
                                                                    stu.setSemsubjects(semsub);
                                                                    String id2 = Snapshot.getKey();
                                                                    databaseStudent.child(id2).setValue(stu);
                                                                    Toast.makeText(getActivity(), "Subjects Saved!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }


                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                    } else {
                                                        Toast.makeText(getActivity(), "Cannot save clashing choices!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                            build.setTitle("Clashes");
                                            build.setView(v);
                                            build.setCancelable(false);
                                            build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Courses2.removeAll(Courses2);
                                                }
                                            });
                                            build.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Courses2.removeAll(Courses2);
                                                    dialog.cancel();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            //handle databaseError
                                        }
                                    });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }
        });
        mClashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Dialog box:", "Created");
                try {
                    AlertDialog alertDialog = build.create();
                    //if()
                    alertDialog.show();
                    Courses2.addAll(Ccourses);
                    Ccourses.removeAll(Ccourses);
                    clashsub.removeAll(clashsub);
                    clashtime.removeAll(clashtime);
                    clashday.removeAll(clashday);
                    subclash.removeAll(subclash);
                    Log.d("Ccourses.size()=",String.valueOf(Ccourses.size()));
                }
                catch (IllegalStateException e)
                {
                    Toast.makeText(getActivity(), "Click on 'GET CLASHES' before proceeding", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }
}