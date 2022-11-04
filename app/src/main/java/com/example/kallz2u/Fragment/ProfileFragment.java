package com.example.kallz2u.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.kallz2u.R;
import com.example.kallz2u.activites.MymessageActivity;
import com.example.kallz2u.activites.SignInActivity;
import com.example.kallz2u.utilities.PreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView imageView34,imageButton53,imageButton54,imageButton49;
    private View view;
    private ProgressDialog progressDialog;
    private EditText editTextTextPersonName;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        progressDialog = new ProgressDialog(getActivity());
        imageView34 = view.findViewById(R.id.imageView34);
        imageButton53 = view.findViewById(R.id.imageButton53);
        imageButton54 = view.findViewById(R.id.imageButton54);
        imageButton49 = view.findViewById(R.id.imageButton49);
        editTextTextPersonName = view.findViewById(R.id.editTextTextPersonName);
        imageView34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTake();
            }
        });
        imageButton53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(), SignInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(i);
                getActivity().finish();
            }
        });
        imageButton49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MymessageActivity.class));
            }
        });
        imageButton54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"save success",Toast.LENGTH_SHORT).show();
                PreferencesUtils.putString(getActivity(),"name",editTextTextPersonName.getText().toString().trim());
            }
        });
        if (!TextUtils.isEmpty( PreferencesUtils.getString(getActivity(),"photo"))){
            Glide.with(getActivity()).applyDefaultRequestOptions(RequestOptions.bitmapTransform(new CircleCrop())).load(PreferencesUtils.getString(getActivity(),"photo")).into(imageView34);
        }
        if (!TextUtils.isEmpty( PreferencesUtils.getString(getActivity(),"name"))){
            editTextTextPersonName.setText(PreferencesUtils.getString(getActivity(),"name"));
        }
        return view;
    }

    private void doTake() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .theme(com.zhihu.matisse.R.style.Matisse_Zhihu)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(
                        true,
                        "com.example.kallz2u",
                        ""/*"abc"*/))
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(com.zhihu.matisse.R.dimen.album_item_height))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .setOnSelectedListener((uriList, pathList) -> {
                    Log.e("onSelected", "onSelected: pathList=" + pathList);
                })
                .showSingleMediaType(true)//true表示不能同时显示图片和视频
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(isChecked -> {
                    Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                })
                .forResult(2);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                PreferencesUtils.putString(getActivity(),"photo",Matisse.obtainPathResult(data).get(0));
                Glide.with(getActivity())
                        .applyDefaultRequestOptions(RequestOptions.bitmapTransform(new CircleCrop())).load(Matisse.obtainPathResult(data).get(0)).into(imageView34);
//                try {
//                    uploadPhoto(Matisse.obtainPathResult(data).get(0));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    private void uploadPhoto(String path) throws IOException {
//        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://social-message-7a142.appspot.com");
        StorageReference mStorage = storageRef.child(PreferencesUtils.getString(getActivity(),"name"))
                .child(Uri.fromFile(new File(path)).getLastPathSegment());
        mStorage.putFile(Uri.fromFile(new File(path))).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("============", "onSuccess: uri= "+ uri.toString());
                        FirebaseFirestore database = FirebaseFirestore.getInstance();
                        final Map<String, Object> postMap = new HashMap<>();
                        postMap.put("image",uri.toString());
                        database.collection("userS")
                                .add(postMap)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(getActivity(), "upload photo success", Toast.LENGTH_SHORT).show();
                                    if (progressDialog!=null){
                                        progressDialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(exception -> {
                                    Log.d("ERROR1", exception.getMessage());
                                    Toast.makeText(getActivity(), "upload photo fail", Toast.LENGTH_SHORT).show();
                                    if (progressDialog!=null){
                                        progressDialog.dismiss();
                                    }
                                });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ERROR2", e.toString());
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}