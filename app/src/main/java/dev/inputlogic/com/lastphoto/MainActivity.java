package dev.inputlogic.com.lastphoto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView mImageView = (ImageView) findViewById(R.id.last_photo_thumb);
            mImageView.setImageBitmap(imageBitmap);
            hideButton();
            saveImage(imageBitmap);
        }
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void saveImage(Bitmap bm) {
        Context ctx = getBaseContext();
        File dir = ctx.getDir("img", Context.MODE_PRIVATE);
        File mPath = new File(dir, "test.png");

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(mPath);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void hideButton() {
        Button btn = (Button) findViewById(R.id.btn_take_photo);
        btn.setVisibility(View.GONE);
    }

    private void showButton() {
        Button btn = (Button) findViewById(R.id.btn_take_photo);
        btn.setVisibility(View.VISIBLE);
    }
}
