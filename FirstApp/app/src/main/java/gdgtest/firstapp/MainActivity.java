package gdgtest.firstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.*;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private String mCurrentPhotoPath;
    //private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);


        final Button button = (Button) findViewById(R.id.buttonTakePic);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    // Perform action on click
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    File f = null;
                    try {
                        f = createImageFile();
                        mCurrentPhotoPath = f.getAbsolutePath();

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    } catch (IOException e) {
                        e.printStackTrace();
                        f = null;
                        mCurrentPhotoPath = null;
                    }
                    startActivityForResult(intent, 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        final Button button2 = (Button) findViewById(R.id.buttonSendPostcard);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    // Perform action on click
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("*/*");
                    //intent.putExtra(Intent.EXTRA_SUBJECT, (TextView)findViewById(R.id.SubjectMsg).getText().toString);
                    //intent.putExtra(Intent.EXTRA_STREAM, attachment);
                    startActivityForResult(intent, 0);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

/*        final Button buttonMsgTxt = (Button) findViewById(R.id.MsgText);
        buttonMsgTxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 0);
            }
        });*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (mCurrentPhotoPath != null) {
                // set the image to display
                Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                ImageView mImageView = (ImageView) findViewById(R.id.ImageDisplayed);
                mImageView.setImageBitmap(bitmap);
                mImageView.setVisibility(View.VISIBLE); // optional/redundant
                //galleryAddPic();
                mCurrentPhotoPath = null;
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File albumF = getAlbumDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                albumF      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    /* Photo album for this application */
    private String getAlbumName() {
        return getString(R.string.album_name);
    }

    private File getAlbumDir() {
        File storageDir = null;

        File f = new File("/");
        File file[] = f.listFiles();


        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                storageDir = new File(
                        Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES),
                        getAlbumName());
            } else {
                storageDir = new File (
                        Environment.getExternalStorageDirectory()
                                + "/dcim/"
                                + getAlbumName());
            }

            try {
                File f2 = new File("/storage/emulated/0");
                if(f2.canWrite())
                {
                    Log.d("CameraSample", "Can create directory");
                }
                if (storageDir != null) {
                    if (!storageDir.mkdirs()) {
                        if (!storageDir.exists()) {
                            Log.d("CameraSample", "failed to create directory");
                            return null;
                        }
                    }
                }
            }
            catch(Exception ex) {
                Log.d(ex.getMessage(),ex.toString());
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://gdgtest.firstapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://gdgtest.firstapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
