
package com.example.kallz2u.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kallz2u.R;
import com.example.kallz2u.bean.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ContactFragment extends Fragment {

    private View ContactsView;
    private RecyclerView myContactsList;

    private DatabaseReference databaseReference, UsersRef;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;

    public ContactFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ContactsView = inflater.inflate(R.layout.fragment_contact,container,false);

        myContactsList = ContactsView.findViewById(R.id.usersRecyclerView);
        myContactsList.setLayoutManager(new LinearLayoutManager(ContactsView.getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();



        // Inflate the layout for this fragment
        return ContactsView;
    }

    @Override
    public void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(databaseReference,User.class)
                        .build();

        FirebaseRecyclerAdapter<User,ContactsViewHolder> adapter
                = new FirebaseRecyclerAdapter<User, ContactsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ContactsViewHolder holder, int position, @NonNull User model) {
                String userIDs = getRef(position).getKey();
                UsersRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.username.setText(model.getUsername());
                        holder.email.setText(model.getEmail());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                 View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_layout,parent,false);
                ContactsViewHolder viewHolder = new ContactsViewHolder(view);
                return viewHolder;
            }
        };

        myContactsList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder{

        TextView username, email;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.txtContactName);
            email = itemView.findViewById(R.id.txtContactEmail);
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