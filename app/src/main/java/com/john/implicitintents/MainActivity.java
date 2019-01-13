package com.john.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AttachEventToPhoneText();
    }

    private void AttachEventToPhoneText()
    {
        EditText editText = findViewById(R.id.dialer_text);

        if (editText != null)
        {
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
            {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
                {
                    boolean handled = false;

                    if (actionId == EditorInfo.IME_ACTION_SEND)
                    {
                        dialNumber();
                        handled = true;
                    }
                    return handled;
                }
            });
        }
    }

    public void openWebsite(View view)
    {
        EditText mWebsiteEditText; // Variable to store the editText
        mWebsiteEditText = findViewById(R.id.website_edit_text); // Assign content to the variable

        String url = mWebsiteEditText.getText().toString(); // Variable to store the editText and convert it to a string
        Uri webpage = Uri.parse(url); // Variable to store the url

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage); // Pass the variable to the intent

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
        else
        {
            DisplayError();
            LogError();
        }
    }

    public void openLocation(View view)
    {
        EditText mLocationEditText; // Variable to store the editText
        mLocationEditText = findViewById(R.id.location_edit_text); // Assign content to the variable

        String loc = mLocationEditText.getText().toString(); // Variable to store the editText and convert it to a string
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);

        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri); // Pass the variable to the intent

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
        else
        {
            DisplayError();
            LogError();
        }
    }

    public void shareText(View view)
    {
        EditText mShareEditText; // Variable to store the editText
        mShareEditText = findViewById(R.id.share_edit_text); // Assign content to the variable

        String text = mShareEditText.getText().toString(); // Variable to store the editText and convert it to a string
        String mimeType = "text/plain";

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Share this text with: ")
                .setText(text)
                .startChooser();
    }

    public void takePicture(View view)
    {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePicture.resolveActivity(getPackageManager()) != null)
        {
            startActivity(takePicture);
        }
        else
        {
            DisplayError();
            LogError();
        }
    }

    public void dialNumber()
    {
        EditText text = findViewById(R.id.dialer_text);
        String phoneNumber = "tel:" + text.getText().toString();

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNumber));

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
        else
        {
            DisplayError();
            LogError();
        }
    }

    private void LogError()
    {
        Log.d(LOG_TAG, "@string/error_message");
    }

    private void DisplayError()
    {
        Toast toast = Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT);

        toast.show();
    }
}
