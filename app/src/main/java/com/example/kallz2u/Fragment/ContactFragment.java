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
import com.example.kallz2u.adapters.UsersAdapter;
import com.example.kallz2u.bean.User;
import com.example.kallz2u.databinding.FragmentContactBinding;
import com.example.kallz2u.utilities.Constants;
import com.example.kallz2u.utilities.PreferenceManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class ContactFragment extends Fragment {

    private FragmentContactBinding binding;
    private DatabaseReference databaseReference;
    private Permissions permissions;
    private PreferenceManager preferenceManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentContactBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        preferenceManager = new PreferenceManager(getContext());
        getUsers();
    }


    private void loading(Boolean isLoading){
        if (isLoading){
            binding.card.setVisibility(View.VISIBLE);
        }
        else{
            binding.card.setVisibility(View.INVISIBLE);
        }
    }

    private void showErrorMessage(){
    }

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
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if (currentUserId.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }
                            User user = new User();
                            user.username = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                        }
                        if (users.size() > 0 ){
                            UsersAdapter usersAdapter = new UsersAdapter(users);
                            binding.card.setVisibility(View.VISIBLE);
                        }
                        else{
                            showErrorMessage();
                        }

                    }
                    else{
                        showErrorMessage();
                    }
                });
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

    /*private void getUserContact(){
        if(permissions.isContactOk(getContext())){
            String[] projection = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME,
            };

            ContentResolver cr = getActivity().getContentResolver();
            Cursor cursor=cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,projection,null,null,null);
            if(cursor != null){
                while(cursor.moveToFirst()){
                   String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                   String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                   number = number.replace("\\s","");
                   String num = String.valueOf(number.charAt(0));

                   if (num.equals("0")){
                       number = number.replace("(?:0)+","+61");
                    }
                }
            }
        }else permissions.requestContact(getActivity());{

        }

    }*/
}