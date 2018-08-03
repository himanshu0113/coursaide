package com.example.ubantu.coursaide_admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ubantu on 4/14/18.
 */

public class SemesterCourse_fragment_recomm_result_MT17015 extends Fragment {

    private String[] listArr;
    private DatabaseReference userDatabase;
    private FirebaseUser user;
    private String uemail;
    private Student_MT17015 mStudent;
    private DataSnapshot studentSnapshot;
    private int selected;

    public View onCreateView(LayoutInflater inflater, ViewGroup recom_container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.semester_course_fragment_recomm_result, recom_container, false);

        Bundle bundle = getArguments();
        final ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>) bundle.getSerializable("list");


        //begin init test data
        final ArrayList<HashMap<String, Object>> m_data = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> map1;

        for (ArrayList<String> l : list) {
            map1 = new HashMap<String, Object>();
            map1.put("maintext", android.text.TextUtils.join(", ", l));
            m_data.add(map1);
        }

        userDatabase = FirebaseDatabase.getInstance().getReference("Student_MT17015");
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            uemail = user.getEmail();
            userDatabase.orderByChild("username").equalTo(uemail).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    mStudent = dataSnapshot.getValue(Student_MT17015.class);
                    studentSnapshot = dataSnapshot;
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


        for (HashMap<String, Object> m :m_data) //make data of this view should not be null (hide )
            m.put("checked", false);
        //end init data


        final ListView lv = (ListView) rootView.findViewById(R.id.list_view_recom);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);




        final SimpleAdapter adapter = new SimpleAdapter(getContext(),
                m_data,
                R.layout.single_list_item,
                new String[] {"maintext", "checked"},
                new int[] {R.id.single_result,  R.id.rb_Choice});

        adapter.setViewBinder(new SimpleAdapter.ViewBinder()
        {
            public boolean setViewValue(View view, Object data, String textRepresentation)
            {
                if (data == null) //if 2nd line text is null, its textview should be hidden
                {
                    view.setVisibility(View.GONE);
                    return true;
                }
                view.setVisibility(View.VISIBLE);
                return false;
            }

        });


        // Bind to our new adapter.
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
                RadioButton rb = (RadioButton) v.findViewById(R.id.rb_Choice);
                if (!rb.isChecked()) //OFF->ON
                {
                    for (HashMap<String, Object> m :m_data) //clean previous selected
                        m.put("checked", false);

                    m_data.get(arg2).put("checked", true);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //show result
        ((Button)rootView.findViewById(R.id.save_recom_result)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentSnapshot.getRef().removeValue();
                String id = userDatabase.push().getKey();

                int r = -1;
                for (int i = 0; i < m_data.size(); i++) //clean previous selected
                {
                    HashMap<String, Object> m = m_data.get(i);
                    Boolean x = (Boolean) m.get("checked");
                    if (x == true)
                    {
                        r = i;
                        break; //break, since it's a single choice list
                    }
                }
//                new AlertDialog.Builder(getContext()).setMessage("you selected:"+r).show();

                mStudent.setSemsubjects(list.get(r));
                userDatabase.child(id).setValue(mStudent);
            }
        });



        return rootView;
    }

}