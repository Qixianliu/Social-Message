package com.example.kallz2u.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kallz2u.R;
import com.example.kallz2u.databinding.FragmentGroupMemberBinding;


public class GroupMemberFragment extends Fragment {

    private FragmentGroupMemberBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentGroupMemberBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}