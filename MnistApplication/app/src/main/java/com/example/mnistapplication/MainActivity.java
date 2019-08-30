package com.example.mnistapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG = getClass().getSimpleName();
    ImageView IV, ResizeIV;
    Button CaptureBtn;
    Button AlbumBtn;
    Button SendBtn;
    File tempSelectFile;
    final static int TAKE_PICTURE = 1;

    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1111;
    static final int REQUEST_TAKE_ALBUM = 2222;

    Uri photoURI, albumURI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //layout과 변수 연결
        IV = findViewById(R.id.IV);
        ResizeIV = findViewById(R.id.ResizeIV);
        CaptureBtn = findViewById(R.id.CaptureBtn);
        AlbumBtn = findViewById(R.id.AlbumBtn);
        SendBtn = findViewById(R.id.SendBtn);
        //SendBtn.setEnabled(false);



        // camera버튼에 리스너 추가
        CaptureBtn.setOnClickListener(this);
        // album 버튼 리스너 추가
        AlbumBtn.setOnClickListener(this);
        // send 버튼 리스너 추가
        SendBtn.setOnClickListener(this);

        // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    // 버튼 onClick리스너 처리부분
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CaptureBtn:
                // 카메라 앱을 여는 소스
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, TAKE_PICTURE);
                dispatchTakePictureIntent();
                break;
            case R.id.AlbumBtn:
                // 앨범 여는 소스
                getAlbum();
                break;
            case R.id.SendBtn:
                // 예측 소스
                FileUploadUtils.goSend(tempSelectFile);
        }
    }

    // 카메라로 촬영한 영상을 가져오는 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    galleryAddPic();
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {
                            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

                            // 이미지 회전 90도 180도 270도
                            Bitmap rotatedBitmap = null;
                            switch (orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    rotatedBitmap = rotateImage(bitmap, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    rotatedBitmap = rotateImage(bitmap, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    rotatedBitmap = rotateImage(bitmap, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:
                                default:
                                    rotatedBitmap = bitmap;
                            }

                            //IV.setImageBitmap(rotatedBitmap);
                            Log.i("rotateBitmap", rotatedBitmap.toString());
                            Bitmap resize = resize(mCurrentPhotoPath);
                            tempSelectFile = new File(mCurrentPhotoPath);
                            Log.i("tempselctfile", tempSelectFile.toString());


                        }
                    }
                    break;
                }

                case REQUEST_TAKE_ALBUM:
                    if(resultCode == Activity.RESULT_OK) {
                        if(intent.getData() != null) {
                            try{
                                File albumFile = null;
                                albumFile = createImageFile();
                                photoURI = intent.getData();
                                albumURI = Uri.fromFile(albumFile);
                                galleryAddPic();
                                IV.setImageURI(photoURI);
                                Log.i("PHOTOURI",albumURI.toString());
                            }catch (Exception e){
                                e.printStackTrace();
                                Log.i("알림","앨범에서 가져오기 에러");
                            }
                        }
                    }
                    break;
            }

        } catch (Exception e) {
            Log.i("OnActivityResult", e.toString());
        }
    }

    //카메라로 촬영한 이미지를 파일로 저장
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        try{
            mCurrentPhotoPath = image.getAbsolutePath();
            Log.i("IMAGE", mCurrentPhotoPath);
            //return image;
        }
        catch (Exception e) {
            Log.i("IMAGE", e.toString());
        }
        return image;
    }

    //카메라 인텐트를 실행
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.mnistapplication.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }

        }

    }

    // 이미지를 회전해주는 함수
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    // 앨범 인텐트 실행
    private void getAlbum() {
        //앨범에서 이미지 가져옴
        //앨범 열기
        Log.i("getAlbum", "Call");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
        Log.i("getAlbum", "Closed");
    }

    private void galleryAddPic() {
        Log.i("galleryAddPic", "Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        //해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안됨)
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "사진이 앨번에 저장되었습니다.", Toast.LENGTH_LONG).show();
        Log.i("galleryAddPic", "Closed");
    }


    // 비트맵 이미지 RESIZE
    public static Bitmap resize(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bitmap= BitmapFactory.decodeFile(filePath, options);

        int width = 28; // 축소시킬 너비
        int height = 28; // 축소시킬 높이
        float bmpWidth = bitmap.getWidth();
        float bmpHeight = bitmap.getHeight();

        if (bmpWidth > width) {
            // 원하는 너비보다 클 경우의 설정
            float mWidth = bmpWidth / 100;
            float scale = width/ mWidth;
            bmpWidth *= (scale / 100);
            bmpHeight *= (scale / 100);
        } else if (bmpHeight > height) {
            // 원하는 높이보다 클 경우의 설정
            float mHeight = bmpHeight / 100;
            float scale = height/ mHeight;
            bmpWidth *= (scale / 100);
            bmpHeight *= (scale / 100);
        }

        Bitmap resizedBmp = Bitmap.createScaledBitmap(bitmap, (int) bmpWidth, (int) bmpHeight, true);
        Log.i("RESIZE", resizedBmp.toString());
        //ResizeIV.setImageBitmap(resizedBmp);
        return resizedBmp;

    }

}
