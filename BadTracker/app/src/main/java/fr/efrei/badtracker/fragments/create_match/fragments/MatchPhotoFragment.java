package fr.efrei.badtracker.fragments.create_match.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.Utils;
import fr.efrei.badtracker.fragments.create_match.CreateMatchFragment;
import fr.efrei.badtracker.models.MatchLocation;

import static android.app.Activity.RESULT_OK;

public class MatchPhotoFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private ImageView photo;
    private View view;
    private File photoFile;
    private boolean valid = false;
    private CreateMatchFragment createMatchFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_match_photo, container, false);

        setRetainInstance(true);

        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        createMatchFragment = (CreateMatchFragment) navHostFragment.getParentFragment();

        photo = view.findViewById(R.id.photo);

        MatchPhotoFragmentArgs args = MatchPhotoFragmentArgs.fromBundle(getArguments());
        String image = args.getPhoto();
        if(image != null && !image.isEmpty()) {
            photoFile = new File(image);
            updatePhoto();
        }

        Button takePicture = view.findViewById(R.id.take_picture);
        takePicture.setOnClickListener(v -> takePicture());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            updatePhoto();
            save();
        }
    }

    private void updatePhoto() {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
                    Uri.parse("file:" + photoFile.getAbsolutePath()));
            photo.setImageBitmap(bitmap);
            valid = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Utils.showSnackbar(view, R.string.create_image_file, android.R.string.ok,
                    view -> {});
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(getContext(),
                    "com.example.android.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public boolean validate() {
        return valid;
    }

    private void save() {
        if(photoFile != null) {
            createMatchFragment.setImage(photoFile.getAbsolutePath());
        }
    }
}