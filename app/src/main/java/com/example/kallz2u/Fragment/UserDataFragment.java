package com.example.kallz2u.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kallz2u.R;
import com.example.kallz2u.databinding.FragmentUserDataBinding;
import com.example.kallz2u.utilities.AllConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDataFragment extends Fragment {
    private FragmentUserDataBinding binding;
    private String storagePath,name,status;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    /*
    private StorageReference storageReference;
    private Uri imageUri;
    * */

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDataFragment newInstance(String param1, String param2) {
        UserDataFragment fragment = new UserDataFragment();
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
        binding=FragmentUserDataBinding.inflate(inflater,container,false);
        View view=binding.btnDataDone.getRootView();

        return view;
    }

    private boolean isStoragePermissionOK(){
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            requestStoragePermission();
            return false;
        }
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AllConstants
                    .CONTACTS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pickImage();
                else
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_SHORT).show();

                break;
        }
    }*/

    private boolean checkName() {
        name = binding.edtUserName.getText().toString().trim();
        if (name.isEmpty()) {
            binding.edtUserName.setError("Filed is required");
            return false;
        } else {
            binding.edtUserName.setError(null);
            return true;
        }
    }

    private boolean checkStatus() {
        status = binding.edtUserStatus.getText().toString();
        if (status.isEmpty()) {
            binding.edtUserStatus.setError("Filed is required");
            return false;
        } else {
            binding.edtUserStatus.setError(null);
            return true;
        }
    }
}