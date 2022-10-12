
package com.example.kallz2u.Fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kallz2u.Permissions.Permissions;
import com.example.kallz2u.R;
import com.example.kallz2u.databinding.FragmentContactBinding;
import com.google.firebase.database.DatabaseReference;

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
                    /*ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Email.*/
            };

            ContentResolver cr = getActivity().getContentResolver();
            Cursor cursor=cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,projection,null,null,null);
            if(cursor != null){
                while(cursor.moveToFirst()){
                    /*String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));
                    String email=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_EMAIL);*/

                }
            }
        }else{

        }

    }
}

    /*private FragmentContactBinding binding;
    private DatabaseReference databaseReference;
    private Permissions permissions;
    private PreferenceManager preferenceManager;

    private void getUsers(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USER)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null){
                        List<User> users = new ArrayList<>();
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if (currentUserId.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }
                            User user = new User();
                            user.username = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            users.add(user);
                        }
                        if (users.size() > 0){
                            UsersAdapter usersAdapter = new UsersAdapter(users);
                            binding.usersRecyclerView.setAdapter(usersAdapter);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        }else {
                            showErrorMessage();
                        }
                    }else {
                        showErrorMessage();
                    }
                });
    }

    private void showErrorMessage(){

    }

    private void loading(Boolean isLoading){
            if (isLoading){
                binding.imageButton84.setVisibility(View.VISIBLE);
            }else{
                binding.imageButton84.setVisibility(View.INVISIBLE);
            }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentContactBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        preferenceManager = new PreferenceManager(getContext());
        getUsers();
    }*/

    /*private void getUserContact(){
        if(permissions.isContactOk(getContext())){
            String[] projection = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Email.
            };

            ContentResolver cr = getActivity().getContentResolver();
            Cursor cursor=cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,projection,null,null,null);
            if(cursor != null){
                while(cursor.moveToFirst()){
                    String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));
                    String email=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_EMAIL);

                }
            }
        }else{

        }

    }*/