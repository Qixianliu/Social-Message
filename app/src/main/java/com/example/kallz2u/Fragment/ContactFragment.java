package com.example.kallz2u.Fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kallz2u.Permissions.Permissions;
import com.example.kallz2u.R;
import com.example.kallz2u.databinding.FragmentContactBinding;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {

    private FragmentContactBinding binding;
    private DatabaseReference databaseReference;
    private Permissions permissions;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
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
        binding = FragmentContactBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        permissions= new Permissions();
        return view;
    }

    private void getUserContact(){
        if(permissions.isContactOk(getContext())){
            String[] projection = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME,
            };

            ContentResolver cr = getActivity().getContentResolver();
            Cursor cursor=cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,projection,null,null,null);
            /*if(cursor != null){
                while(cursor.moveToFirst()){
                   String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                   String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                   number = number.replace("\\s","");
                   String num = String.valueOf(number.charAt(0));

                   if (num.equals("0")){
                       number = number.replace("(?:0)+","+61");
                    }
                }
            }*/
        }else permissions.requestContact(getActivity());{

        }

    }
}