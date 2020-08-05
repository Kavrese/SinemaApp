package com.example.sinemaapp.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sinemaapp.R;
import com.example.sinemaapp.classes.MainActivity;
import com.example.sinemaapp.classes.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class Profile_fragment extends Fragment {
    Button exit;
    ImageView avatar,img_back;
    TextView name_user;
    static final int GALLERY_REQUEST = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_fragment,container,false);
        name_user = view.findViewById(R.id.name_user);
        exit = view.findViewById(R.id.button_exit);
        img_back = view.findViewById(R.id.img_back);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        avatar = view.findViewById(R.id.avatar);
        avatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Vibrator vibrator = (Vibrator) avatar.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                Context wrapper = new ContextThemeWrapper(avatar.getContext(),R.style.PopurMenuDark);
                PopupMenu avatarPopur = new PopupMenu(wrapper,view);
                avatarPopur.inflate(R.menu.menu_edit_avatar);
                avatarPopur.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit_old:

                                break;
                            case R.id.set_new:
                                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                                photoIntent.setType("image/*");
                                startActivityForResult(photoIntent,GALLERY_REQUEST);
                                break;
                        }
                        return false;
                    }
                });
                avatarPopur.show();
                return false;
            }
        });
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child("items").child(email.substring(0,email.indexOf("@")));
        databaseReference.child("avatar_icon").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Bitmap bitmap = BitmapFactory.decodeFile(snapshot.getValue(String.class));
               avatar.setImageBitmap(bitmap);
               img_back.setImageBitmap(bitmap);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("user_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name_user.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(view.getContext(), StartActivity.class);
                view.getContext().startActivity(in);
            }
        });
        return view;
    }
    public File convertBitmapToFile(Bitmap bitmap,File dir){
        String name = new SimpleDateFormat("dd.MM.yyyy-HHmmss").format(new Date())+".jpg";
        File file = new File(dir,name);
        if(!dir.exists())
            dir.mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.e("tag",e.getMessage());
        }
        try {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } finally {
                if (fos != null) fos.close();
            }
            //   CropImage.activity(selectedImage).start((Activity) exit.getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        switch (requestCode){
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(exit.getContext().getContentResolver(), selectedImage);
                        avatar.setImageBitmap(bitmap);
                        saveBdImg(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }

    private void saveBdImg(Bitmap bitmap) {
        File file = convertBitmapToFile(bitmap,new File(Environment.getExternalStorageDirectory()+"/SinemaApp/Avatars/"));
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        DatabaseReference ava = FirebaseDatabase.getInstance().getReference().child("users").child("items").child(email.substring(0,email.indexOf("@")));
        ava.child("avatar_icon").setValue(file.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(exit,"Аватарка сохранениа", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }
}
