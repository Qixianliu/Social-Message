package com.example.kallz2u.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kallz2u.R;
import com.example.kallz2u.databinding.FragmentCreateGroupBinding;

import java.security.Permission;
import java.security.Permissions;


public class CreateGroupFragment extends Fragment {

    private FragmentCreateGroupBinding binding;
    private Permissions appPermission;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateGroupBinding.inflate(inflater,container,false);
        appPermission = new Permissions();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Group Detail");
        return binding.getRoot();
    }

   /*  @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnDone.setOnClickListener(done -> {

            String groupName = binding.edtGroupName.getText().toString().trim();
            if (groupName.isEmpty()) {
                binding.edtGroupName.setError("Filed is required");
                binding.edtGroupName.requestFocus();
            }
            else{
                Bundle bundle = new Bundle();
                bundle.putString("GroupName", groupName);
                GroupMemberFragment memberFragment=new GroupMemberFragment();
                memberFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.groupContainer,memberFragment)
                        .commit();
            }
        });
    }*/
}