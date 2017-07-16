package com.prapps.iris.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.prapps.iris.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by royce on 02-04-2017.
 */

public class MainActivity extends AppCompatActivity {

    private EditText mInputEditText;

    private ListView mListView;
    private MessageListAdapter messageListAdapter;
    //0 default ,1 track,2 for nearby, 3 for zipcode,
    // 4 for quoteSrc country, 5 for quoteSrc zip, 6 for dest conuntry, 7 for dest zipcode
    // 8 for ship online ,
    // 100 for next help
    private int mode = 0;
    //   private Toolbar mToolbar;
    private List<String> trackKeywords = Arrays.asList("track", "tracking");
    private List<String> centerKeywords = Arrays.asList("service", "dhl", "nearby", "location", "express", "drop box",
            "dropbox", "service", "service point", "nearest", "center", "centre");
    private List<String> quoteKeywords = Arrays.asList("price", "rate", "cost", "quote", "time", "estimate", "estimated");
    private List<String> shipKeywords = Arrays.asList("ship", "ship online", "webship", "send", "send a courier", "courier",
            "send a");
    private List<String> yesKeys = Arrays.asList("yes", "ya", "yeah", "ok", "okay", "k");
    private List<String> noKeys = Arrays.asList("nah", "no", "nope", "never");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.message_list);
        //  mToolbar = (Toolbar) findViewById(R.id.toolbar);

        //   setSupportActionBar(mToolbar);

        messageListAdapter = new MessageListAdapter(this);
        mListView.setAdapter(messageListAdapter);

        mInputEditText = (EditText) findViewById(R.id.message_input);

        findViewById(R.id.message_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendClick();
            }
        });

        showModeMessage(0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


    }

    private void showModeMessage(int m) {

        mode = m;
        String message = "";
        switch (mode) {
            case 0:
                message = "Hi , This is Iris here , how may I help you ? ";
                break;
            case 1:
                message = "Please enter your 10 digit tracking number";
                break;
            case 2:
                message = "Please enter your country";
                break;
            case 3:
                message = "Please enter your zip code";
                break;
            case 4:
                message = "Please enter your source country";
                break;
            case 5:
                message = "Please enter your source zip code";
                break;
            case 6:
                message = "Please enter your destination country";
                break;
            case 7:
                message = "Please enter your destination zip code";
                break;
            case 8:
                message = "Please enter your customs value in dollars";
                break;
            case 9:
                message = "Please enter your source country";
                break;
            case 10:
                message = "Please enter your source company name";
                break;
            case 11:
                message = "Please enter your source address";
                break;
            case 12:
                message = "Please enter your source zip code";
                break;
            case 13:
                message = "Please enter your source phone number";
                break;
            case 14:
                message = "Please enter your source email address";
                break;
            case 15:
                message = "Please enter your destination country";
                break;
            case 16:
                message = "Please enter your destination company name";
                break;
            case 17:
                message = "Please enter your destination address";
                break;
            case 18:
                message = "Please enter your destination zip code";
                break;
            case 19:
                message = "Please enter your destination phone number";
                break;
            case 20:
                message = "Please enter your destination email address";
                break;
            case 21:
                message = "When are you shipping ?";
                break;
            case 22:
                message = "How many packages do you need to send ?";
                break;
            case 23:
                message = "Do you want to create a Commercial Invoice for your non-dutiable shipment";
                break;
            case 24:
                message = "Do you want to create receipt containing all shipment information for your files";
                break;
            case 25:
                message = "If you have a promo code enter the same , else reply NO";
                break;

            case 100:
                message = "Is there anything else Iris can do for you?";
                break;
            case 101:
                message = "Sorry, Iris didn't get you, Is there anything else Iris can do for you?";
                break;
            case 102:
                message = "Tell me what I can do for you ?";
                break;
            case 103:
                message = "Thanks for talking to me , Have a great day ahead , Bye";
                break;
        }

        messageListAdapter.add(message, true);
        mListView.setSelection(messageListAdapter.getCount() - 1);
    }


    private void onSendClick() {
        if (mInputEditText.getText().toString().contentEquals("")) {
            Toast.makeText(this, "Please enter a non empty message", Toast.LENGTH_SHORT).show();
            return;
        }
        addUserResponse();
    }

    private void showErrorMessage(int n) {
        Toast.makeText(this, "Please enter a valid non empty message ", Toast.LENGTH_SHORT).show();
        mInputEditText.setText("");
    }

    private void addUserResponse() {
        String msg = mInputEditText.getText().toString();
        if (msg.replaceAll(" ", "").contentEquals("")) {
            showErrorMessage(0);
            return;
        }
        messageListAdapter.add(mInputEditText.getText().toString(), false);
        mListView.setSelection(messageListAdapter.getCount() - 1);
        mInputEditText.setText("");
        //   hideKeyboard();
        if (mode == 0 || mode == 100 || mode == 101 || mode == 102)
            listContainsString(msg);
        else if (mode == 1)
            getTrackInfo(msg);
        else if (mode == 2)
            showModeMessage(3);
        else if (mode == 3)
            getNearby(msg);
        else if (mode >= 4 && mode <= 6 || mode >= 8 && mode <= 24)
            showModeMessage(++mode);
        else if (mode == 7)
            getQuote();
        else if (mode == 25)
            showThanks();
    }

    private void showThanks() {
        messageListAdapter.add("Thanks , your order has been created, our team member will be there at your address" +
                " on the specified day", true);
        mListView.setSelection(messageListAdapter.getCount() - 1);
        showModeMessage(100);
    }

    private void getQuote() {
        messageListAdapter.add("Please wait while we estimate the quote and time for you", true);
        mListView.setSelection(messageListAdapter.getCount() - 1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                messageListAdapter.add("Your estimated quote is 12.75$ and expected time of transit is 6 days", true);
                mListView.setSelection(messageListAdapter.getCount() - 1);
                showModeMessage(100);
            }
        }, 1200);
    }

    private void getNearby(String msg) {
        try {
            int m = Integer.parseInt(msg);
            messageListAdapter.add("Please wait while we look it up for you", true);
            mListView.setSelection(messageListAdapter.getCount() - 1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    messageListAdapter.add("DHL Authorized Shipping Center®\n" +
                            "1917 Seminary Road\n" +
                            "Silver Spring, MD 20910\n" +
                            "2nd Floor\n (202) 829-5050\n" +
                            "Latest drop-off:\t6:00 PM [M-F]\n" +
                            "No Saturday Pickup", true);
                    mListView.setSelection(messageListAdapter.getCount() - 1);
                    messageListAdapter.add("Find directions here \n" +
                            "https://www.google.com/maps/dir/39.0086471,-77.0403989/1917+Seminary+Rd,+" +
                            "Silver+Spring,+MD+20910/@39.0086762,-77.0432099,17z", true);
                    mListView.setSelection(messageListAdapter.getCount() - 1);
                    showModeMessage(100);
                }
            }, 1200);
        } catch (NumberFormatException ex) {
            messageListAdapter.add("Please enter a valid zip code", true);
            mListView.setSelection(messageListAdapter.getCount() - 1);
        }
    }


    private void getTrackInfo(String msg) {
        if (msg.length() == 10) {
            messageListAdapter.add("Please wait while we look it up for you", true);
            mListView.setSelection(messageListAdapter.getCount() - 1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    messageListAdapter.add("Your package has reached the delivery facility at VERONA , ITALY", true);
                    mListView.setSelection(messageListAdapter.getCount() - 1);
                    showModeMessage(100);
                }
            }, 1200);
        } else {
            messageListAdapter.add("You have entered an invalid tracking number", true);
            mListView.setSelection(messageListAdapter.getCount() - 1);
        }
    }


    private void listContainsString(String msg) {
        for (String k : trackKeywords) {
            if (msg.toLowerCase().contains(k)) {
                showModeMessage(1);
                return;
            }
        }

        for (String k : centerKeywords) {
            if (msg.toLowerCase().contains(k)) {
                showModeMessage(2);
                return;
            }
        }

        for (String k : quoteKeywords) {
            if (msg.toLowerCase().contains(k)) {
                showModeMessage(4);
                return;
            }
        }

        for (String k : shipKeywords) {
            if (msg.toLowerCase().contains(k)) {
                showModeMessage(8);
                return;
            }
        }

        for (String k : noKeys) {
            if (msg.toLowerCase().contains(k)) {
                showModeMessage(102);
                return;
            }
        }
        for (String k : noKeys) {
            if (msg.toLowerCase().contains(k)) {
                showModeMessage(103);
                return;
            }
        }

        showModeMessage(101);


    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
