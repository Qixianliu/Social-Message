package com.example.kallz2u.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.kallz2u.R;
import com.example.kallz2u.activites.FourthActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    int isUrgent = 0;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton imageButton8,imageButton7;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        imageButton7 = view.findViewById(R.id.imageButton7);
        imageButton8 = view.findViewById(R.id.imageButton8);
        imageButton7.setOnClickListener(new View.OnClickListener() {
            GroupSelectFragment groupSelectFragment = new GroupSelectFragment();
            @Override
            public void onClick(View view) {
                isUrgent = 0;
                Bundle bundle = new Bundle();
                bundle.putInt("isUrgent",isUrgent);
                GroupSelectFragment groupSelectFragment = new GroupSelectFragment();
                groupSelectFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container,groupSelectFragment).commit();
            }
        });
        imageButton8.setOnClickListener(new View.OnClickListener() {
            GroupSelectFragment groupSelectFragment = new GroupSelectFragment();
            @Override
            public void onClick(View view) {
                isUrgent = 1;
                Bundle bundle = new Bundle();
                bundle.putInt("isUrgent",isUrgent);
                GroupSelectFragment groupSelectFragment = new GroupSelectFragment();
                groupSelectFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container,groupSelectFragment).commit();
            }
        });
        return view;
    }
}