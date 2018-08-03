package com.example.ubantu.coursaide_admin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ubantu on 4/11/18.
 */

public class SemesterCourse_fragment_recomm_MT17015 extends Fragment {
    private FloatingActionButton add_fab;
    private TextView mMaxCredits;
    private TextView mSplCredits;
    private TextView mBuckCredits;
    private FirebaseUser user;
    private String uemail;
    private String mStudentBranch;
    private String mStudentSpecialization;
    private ArrayList<String> mStudentDoneCourses;
    private int mCredits, mBCourses, mSCourses, mCourses;

    private ArrayList<String> Pcourses=new ArrayList<String>();
    private ArrayList<Courses_MT17015> Scourses=new ArrayList<Courses_MT17015>();
    private ArrayList<Courses_MT17015> Bcourses=new ArrayList<Courses_MT17015>();
    private ArrayList<Courses_MT17015> commonCourses;
    private ArrayList<Courses_MT17015> allCourses=new ArrayList<Courses_MT17015>();

    private ArrayList<String> combination = new ArrayList<>();
    private ArrayList<ArrayList<String>> list = new ArrayList<>();

    private DatabaseReference buckDatabase, cDatabase, userDatabase;

    private ArrayList<AutoCompleteTextView> dynids = new ArrayList<AutoCompleteTextView>();

    //for clash checking
    private DatabaseReference dbtt, dbtt2;

    private int mDynamicCourseAddCount;
    private Button result;

    private LinearLayout parentLinearLayout;

    private static final String[] COUNTRIES = new String[] { // THIS IS THE LIST OF ALL COURSES
            "Graduate Algorithms", "Wireless Networks", "Mobile Computing", "Machine Learning", "Research Methods"
    };

    public SemesterCourse_fragment_recomm_MT17015() {
        mDynamicCourseAddCount=0;
    }


    private void swapFragment(){

        Bundle bundle = new Bundle();

        SemesterCourse_fragment_recomm_result_MT17015 result_frag = new SemesterCourse_fragment_recomm_result_MT17015();

        if(list!=null){
            bundle.putSerializable("list", list);
            result_frag.setArguments(bundle);
        }

        android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        ft.remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.recom_container)).commit();
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.recom_container, result_frag,"findThisFragment")
                .addToBackStack(null)
                .commit();



    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.semester_course_fragment_recomm, container, false);

        add_fab= (FloatingActionButton) rootView.findViewById(R.id.add_course_ib);
        final LinearLayout parentLinearLayout= rootView.findViewById(R.id.parent_Linear_Layout);
        result= (Button)rootView.findViewById(R.id.sc_result_button);

        mMaxCredits = (TextView) rootView.findViewById(R.id.maxCredits);
        mBuckCredits = (TextView) rootView.findViewById(R.id.buckCredits);
        mSplCredits = (TextView) rootView.findViewById(R.id.splCredits);

        cDatabase = FirebaseDatabase.getInstance().getReference("Courses_MT17015");
        userDatabase = FirebaseDatabase.getInstance().getReference("Student_MT17015");

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            uemail = user.getEmail();
            userDatabase.orderByChild("username").equalTo(uemail).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Student_MT17015 mStudent = dataSnapshot.getValue(Student_MT17015.class);
                    mStudentBranch = mStudent.getBranch();
                    mStudentSpecialization = mStudent.getSpecialization();
                    mStudentDoneCourses = mStudent.getSubjects();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        //database reading




        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(mDynamicCourseAddCount<3) {

                    final View child_view = inflater.inflate(R.layout.field, null);
                    child_view.setId(mDynamicCourseAddCount);
                    Button del_Button = (Button) child_view.findViewById(R.id.dynamic_button);


                    AutoCompleteTextView dynamic_cname = (AutoCompleteTextView) child_view.findViewById(R.id.dynamic_text);
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


                        }
                    });

                }
                else {
                    Toast.makeText( getContext(),"You can only enter 3 courese",Toast.LENGTH_SHORT).show();
                }


            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCredits = Integer.parseInt(mMaxCredits.getText().toString());
                mCourses = mCredits/4;
                mBCourses = Integer.parseInt(mBuckCredits.getText().toString())/4;
                mSCourses = Integer.parseInt(mSplCredits.getText().toString())/4;

                //check class between preffered courses and move ahead

                //reading preffered courses
                for(int i=0;i<dynids.size();i++)
                {
                    String pcourse = dynids.get(i).getText().toString();
                    if(!Pcourses.contains(pcourse))
                        Pcourses.add(pcourse);
                }

                Log.v("AFTER READING", Pcourses.toString());

                cDatabase.orderByChild("cbranch").equalTo(mStudentBranch).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
//                            Log.v("Courses_MT17015 from database", snapshot.toString());
                            Courses_MT17015 c = snapshot.getValue(Courses_MT17015.class);
//                            Courses_MT17015 c = null;
                            if (c != null && !mStudentDoneCourses.contains(c.getCname()))
                                allCourses.add(c);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                for (Courses_MT17015 c: allCourses){
                    if(c.getCspec().equalsIgnoreCase(mStudentSpecialization)){
                        Scourses.add(c);
                    }

                    if(!c.getCbuck().equalsIgnoreCase("None")){
                        Bcourses.add(c);
                    }
                }

                commonCourses = new ArrayList<Courses_MT17015>(Scourses);
                commonCourses.retainAll(Bcourses);

                Log.v("Position", "beforeGenerate");
                //generate list
                generateResults();

                swapFragment();
            }
        });

        return rootView;
    }

    //generating Lists of subjects
    private void generateResults(){
//        Random rand = new Random();

        int all = mCourses-mBCourses-mSCourses, allLeft;
        int combine = mBCourses+mSCourses;
        allLeft = all;
//        allCourses.removeAll(Bcourses);
//        allCourses.removeAll(Scourses);

        Log.v("INPUTS", String.valueOf(mCourses)+mBCourses+mSCourses+allLeft+combine);

        ArrayList<String> newcombination = new ArrayList<>();

        Iterator<Courses_MT17015> allIterator = allCourses.iterator();
        Iterator<Courses_MT17015> bIterator = Bcourses.iterator();
        Iterator<Courses_MT17015> sIterator = Scourses.iterator();

        for (Courses_MT17015 allCours : allCourses) {
            Log.v("COURSE", allCours.getCname());
        }

        list = new ArrayList<>();

        while((bIterator.hasNext() && mBCourses>0) || (sIterator.hasNext() && mSCourses>0)) {
            combination = new ArrayList<>();
            allLeft = all;
            int b = mBCourses;
            int s = mSCourses;

            //add prefered course
            for (String pcours : Pcourses) {
                if(!combination.contains(pcours)){
                    combination.add(pcours);
                }

            }

            allLeft -= combination.size();

            Log.v("IN BETWEEN", combination.toString());

            for (int i = 0; i < combine && allLeft>0; i++) {
                if (i < combine && b > 0 && bIterator.hasNext()) {
                    Courses_MT17015 c = bIterator.next();
                    if(!combination.contains(c.getCname())){
                        combination.add(c.getCname());
                        i++;
                        b--;
                    }

                }

                if (i < combine && s > 0 && sIterator.hasNext()) {
                    Courses_MT17015 c = sIterator.next();
                    if(!combination.contains(c.getCname())){
                        combination.add(c.getCname());
                        i++;
                        s--;
                    }

                }
            }



//            while (allIterator.hasNext()) {
//                newcombination = new ArrayList<>(combination);
                for (int i = 0; i <= allLeft;) {
                    if (allIterator.hasNext()) {
                        Courses_MT17015 c = allIterator.next();
                        if(!combination.contains(c.getCname())) {        //&& !Bcourses.contains(c) && !Scourses.contains(c)
                            combination.add(c.getCname());
                            i++;
                        }
                    }
                }
//
//            }
//            if(!list.contains(combination))
                list.add(combination);
            Log.v("LISTTTTT", list.toString());
        }


        for (int i = 0; i < list.size(); i++) {
            if(checkClash(list.get(i))){
                Log.v("Removed", list.get(i).toString());
                list.remove(i);
            }
//            Log.v("Not Removed", list.get(i).toString());
        }


    }


    //clash checking
    private ArrayList<String> Courses2=new ArrayList<String>();
    private ArrayList<String> clashsub = new ArrayList<String>();
    private ArrayList<String> subclash = new ArrayList<String>();
    private ArrayList<String> clashtime = new ArrayList<String>();
    private ArrayList<String> clashday = new ArrayList<String>();
    private boolean isClashing = false;

    public boolean checkClash(final ArrayList<String> Ccourses){
        isClashing = false;

        clashsub = new ArrayList<String>();

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

        dbtt2 = FirebaseDatabase.getInstance().getReference("Time_Table");
        dbtt2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int t = 0; t < Ccourses.size(); t++) {
                    String curcourse = Ccourses.get(t);
                    for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                        String subject = Snapshot.getKey();
//                        Log.d("subject:", subject);
                        if (subject.contains(curcourse) && !(Ccourses.contains(subject)) && (subject.contains("Tut") || subject.contains("Lab")) && !(subject.equals(curcourse))) {
                            {
                                Ccourses.add(subject);
//                                Log.d("Lab Added:", subject);
                            }
                        }
                    }
                }
                for (int j = 0; j < Ccourses.size(); j++) {
                    final String cname = Ccourses.get(j);
//                    Log.d("Tag", "Time_Table/" + cname);
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
//                                        Log.d("Tag2", slotday);
                                        if (slotday.equals("Mon")) {
                                            Mon.add(cname);
//                                            Log.d("Tag3", String.valueOf(Mon.size()));
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
//                                                    Log.d("Tag3", String.valueOf(fs1));
                                                } else {
                                                    String[] ss1 = s1.split(":");
                                                    fs1 = (float) (Integer.parseInt(ss1[0]));
                                                    if (fs1 < 8.0)
                                                        fs1 += 12.0;
                                                    fs1 += (((float) (Integer.parseInt(ss1[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fs1));
                                                }
                                                if (s2.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fs2 = (float) (Integer.parseInt(s2));
                                                    if (fs2 < 8.0)
                                                        fs2 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fs2));
                                                } else {
                                                    String[] ss2 = s2.split(":");
                                                    fs2 = (float) (Integer.parseInt(ss2[0]));
                                                    if (fs2 < 8.0)
                                                        fs2 += 12.0;
                                                    fs2 += (((float) (Integer.parseInt(ss2[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fs2));
                                                }
                                                if (e1.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fe1 = (float) (Integer.parseInt(e1));
                                                    if (fe1 < 8.0)
                                                        fe1 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fe1));
                                                } else {
                                                    String[] es1 = e1.split(":");
                                                    fe1 = (float) (Integer.parseInt(es1[0]));
                                                    if (fe1 < 8.0)
                                                        fe1 += 12.0;
                                                    fe1 += (((float) (Integer.parseInt(es1[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fe1));
                                                }
                                                if (e2.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fe2 = (float) (Integer.parseInt(e2));
                                                    if (fe2 < 8.0)
                                                        fe2 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fe2));
                                                } else {
                                                    String[] es2 = e2.split(":");
                                                    fe2 = (float) (Integer.parseInt(es2[0]));
                                                    if (fe2 < 8.0)
                                                        fe2 += 12.0;
                                                    fe2 += (((float) (Integer.parseInt(es2[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fe2));
                                                }
//                                                Log.v("CHECKING CLASH", "Mon");
                                                if ((fs2 >= fs1 && fs2 < fe1) || (fs1 >= fs2 && fs1 < fe2)) {
                                                    isClashing = true;
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
//                                                    Log.d("Tag3", String.valueOf(fs1));
                                                } else {
                                                    String[] ss1 = s1.split(":");
                                                    fs1 = (float) (Integer.parseInt(ss1[0]));
                                                    if (fs1 < 8.0)
                                                        fs1 += 12.0;
                                                    fs1 += (((float) (Integer.parseInt(ss1[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fs1));
                                                }
                                                if (s2.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fs2 = (float) (Integer.parseInt(s2));
                                                    if (fs2 < 8.0)
                                                        fs2 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fs2));
                                                } else {
                                                    String[] ss2 = s2.split(":");
                                                    fs2 = (float) (Integer.parseInt(ss2[0]));
                                                    if (fs2 < 8.0)
                                                        fs2 += 12.0;
                                                    fs2 += (((float) (Integer.parseInt(ss2[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fs2));
                                                }
                                                if (e1.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fe1 = (float) (Integer.parseInt(e1));
                                                    if (fe1 < 8.0)
                                                        fe1 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fe1));
                                                } else {
                                                    String[] es1 = e1.split(":");
                                                    fe1 = (float) (Integer.parseInt(es1[0]));
                                                    if (fe1 < 8.0)
                                                        fe1 += 12.0;
                                                    fe1 += (((float) (Integer.parseInt(es1[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fe1));
                                                }
                                                if (e2.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fe2 = (float) (Integer.parseInt(e2));
                                                    if (fe2 < 8.0)
                                                        fe2 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fe2));
                                                } else {
                                                    String[] es2 = e2.split(":");
                                                    fe2 = (float) (Integer.parseInt(es2[0]));
                                                    if (fe2 < 8.0)
                                                        fe2 += 12.0;
                                                    fe2 += (((float) (Integer.parseInt(es2[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fe2));
                                                }
//                                                Log.v("CHECKING CLASH", "Tue");
                                                if ((fs2 >= fs1 && fs2 < fe1) || (fs1 >= fs2 && fs1 < fe2)) {
                                                    isClashing = true;
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
//                                                    Log.d("Tag3", String.valueOf(fs1));
                                                } else {
                                                    String[] ss1 = s1.split(":");
                                                    fs1 = (float) (Integer.parseInt(ss1[0]));
                                                    if (fs1 < 8.0)
                                                        fs1 += 12.0;
                                                    fs1 += (((float) (Integer.parseInt(ss1[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fs1));
                                                }
                                                if (s2.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fs2 = (float) (Integer.parseInt(s2));
                                                    if (fs2 < 8.0)
                                                        fs2 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fs2));
                                                } else {
                                                    String[] ss2 = s2.split(":");
                                                    fs2 = (float) (Integer.parseInt(ss2[0]));
                                                    if (fs2 < 8.0)
                                                        fs2 += 12.0;
                                                    fs2 += (((float) (Integer.parseInt(ss2[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fs2));
                                                }
                                                if (e1.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fe1 = (float) (Integer.parseInt(e1));
                                                    if (fe1 < 8.0)
                                                        fe1 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fe1));
                                                } else {
                                                    String[] es1 = e1.split(":");
                                                    fe1 = (float) (Integer.parseInt(es1[0]));
                                                    if (fe1 < 8.0)
                                                        fe1 += 12.0;
                                                    fe1 += (((float) (Integer.parseInt(es1[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fe1));
                                                }
                                                if (e2.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fe2 = (float) (Integer.parseInt(e2));
                                                    if (fe2 < 8.0)
                                                        fe2 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fe2));
                                                } else {
                                                    String[] es2 = e2.split(":");
                                                    fe2 = (float) (Integer.parseInt(es2[0]));
                                                    if (fe2 < 8.0)
                                                        fe2 += 12.0;
                                                    fe2 += (((float) (Integer.parseInt(es2[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fe2));
                                                }
//                                                Log.v("CHECKING CLASH", "Wed");
                                                if ((fs2 >= fs1 && fs2 < fe1) || (fs1 >= fs2 && fs1 < fe2)) {
                                                    isClashing = true;
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
//                                                    Log.d("Tag3", String.valueOf(fs1));
                                                } else {
                                                    String[] ss1 = s1.split(":");
                                                    fs1 = (float) (Integer.parseInt(ss1[0]));
                                                    if (fs1 < 8.0)
                                                        fs1 += 12.0;
                                                    fs1 += (((float) (Integer.parseInt(ss1[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fs1));
                                                }
                                                if (s2.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fs2 = (float) (Integer.parseInt(s2));
                                                    if (fs2 < 8.0)
                                                        fs2 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fs2));
                                                } else {
                                                    String[] ss2 = s2.split(":");
                                                    fs2 = (float) (Integer.parseInt(ss2[0]));
                                                    if (fs2 < 8.0)
                                                        fs2 += 12.0;
                                                    fs2 += (((float) (Integer.parseInt(ss2[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fs2));
                                                }
                                                if (e1.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fe1 = (float) (Integer.parseInt(e1));
                                                    if (fe1 < 8.0)
                                                        fe1 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fe1));
                                                } else {
                                                    String[] es1 = e1.split(":");
                                                    fe1 = (float) (Integer.parseInt(es1[0]));
                                                    if (fe1 < 8.0)
                                                        fe1 += 12.0;
                                                    fe1 += (((float) (Integer.parseInt(es1[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fe1));
                                                }
                                                if (e2.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fe2 = (float) (Integer.parseInt(e2));
                                                    if (fe2 < 8.0)
                                                        fe2 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fe2));
                                                } else {
                                                    String[] es2 = e2.split(":");
                                                    fe2 = (float) (Integer.parseInt(es2[0]));
                                                    if (fe2 < 8.0)
                                                        fe2 += 12.0;
                                                    fe2 += (((float) (Integer.parseInt(es2[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fe2));
                                                }
//                                                Log.v("CHECKING CLASH", "Thur");
                                                if ((fs2 >= fs1 && fs2 < fe1) || (fs1 >= fs2 && fs1 < fe2)) {
                                                    isClashing = true;
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
//                                                    Log.d("Tag3", String.valueOf(fs1));
                                                } else {
                                                    String[] ss1 = s1.split(":");
                                                    fs1 = (float) (Integer.parseInt(ss1[0]));
                                                    if (fs1 < 8.0)
                                                        fs1 += 12.0;
                                                    fs1 += (((float) (Integer.parseInt(ss1[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fs1));
                                                }
                                                if (s2.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fs2 = (float) (Integer.parseInt(s2));
                                                    if (fs2 < 8.0)
                                                        fs2 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fs2));
                                                } else {
                                                    String[] ss2 = s2.split(":");
                                                    fs2 = (float) (Integer.parseInt(ss2[0]));
                                                    if (fs2 < 8.0)
                                                        fs2 += 12.0;
                                                    fs2 += (((float) (Integer.parseInt(ss2[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fs2));
                                                }
                                                if (e1.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fe1 = (float) (Integer.parseInt(e1));
                                                    if (fe1 < 8.0)
                                                        fe1 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fe1));
                                                } else {
                                                    String[] es1 = e1.split(":");
                                                    fe1 = (float) (Integer.parseInt(es1[0]));
                                                    if (fe1 < 8.0)
                                                        fe1 += 12.0;
                                                    fe1 += (((float) (Integer.parseInt(es1[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fe1));
                                                }
                                                if (e2.indexOf(":") == -1) {
                                                    //String[] ss1=s1.split()
                                                    fe2 = (float) (Integer.parseInt(e2));
                                                    if (fe2 < 8.0)
                                                        fe2 += 12.0;
//                                                    Log.d("Tag3", String.valueOf(fe2));
                                                } else {
                                                    String[] es2 = e2.split(":");
                                                    fe2 = (float) (Integer.parseInt(es2[0]));
                                                    if (fe2 < 8.0)
                                                        fe2 += 12.0;
                                                    fe2 += (((float) (Integer.parseInt(es2[1]))) / 60.0);
//                                                    Log.d("Tag3", String.valueOf(fe2));
                                                }
//                                                Log.v("CHECKING CLASH", "Fri");
                                                if ((fs2 >= fs1 && fs2 < fe1) || (fs1 >= fs2 && fs1 < fe2)) {
                                                    isClashing = true;
                                                }

                                            }

                                        }
                                    }

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


        return isClashing;
    }

}
