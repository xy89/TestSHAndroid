
package com.example.testserver;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    
    //private final static String TAG = MainActivity.class.getSimpleName();
    private final static String TAG = "testserver";
    
    private final String  serverURL = "http://10.32.168.175:8080/TestSH/account/";
    
    private EditText accountEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView resultTextView;
    private TextView registerTextView;
    private TextView forgetTextView;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountEditText = (EditText)findViewById(R.id.login_account);
        accountEditText.clearFocus();
        passwordEditText = (EditText)findViewById(R.id.login_password);
        passwordEditText.clearFocus();
        loginButton = (Button)findViewById(R.id.login); 
        resultTextView = (TextView)findViewById(R.id.result);
        registerTextView =  (TextView)findViewById(R.id.register);
        forgetTextView = (TextView)findViewById(R.id.forget);
        registerTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {               
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
            
        });
        
        forgetTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {                
                
            }
            
        });
        
        accountEditText.addTextChangedListener(accoutTextWacther);
        passwordEditText.addTextChangedListener(passwordTextWacther);
        loginButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               String account = accountEditText.getText().toString();
               String password = passwordEditText.getText().toString();
               String url = serverURL + "get?account=" + account;
               new LoginTask().execute(url);                
            } 
        });
        
    }
    
    TextWatcher accoutTextWacther = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            boolean accountEmpty = s.toString().isEmpty();
            boolean passwordEmpty = passwordEditText.getText().toString().isEmpty();
            if(!accountEmpty && !passwordEmpty) {
                loginButton.setEnabled(true);
            }else {
                loginButton.setEnabled(false);
            }
       }
    };
    TextWatcher passwordTextWacther = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            boolean accountEmpty = accountEditText.getText().toString().isEmpty();
            boolean passwordEmpty = s.toString().isEmpty();
            if(!accountEmpty && !passwordEmpty) {
                loginButton.setEnabled(true);
            }else{
                loginButton.setEnabled(false);
            }
        }
    };
    
    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {            
            return login(urls[0]);
        }
        
        @Override
        protected void onPostExecute(String result) {
            resultTextView.setText(result);
        }
        
    }
    
    private String login(String url) {
        
        InputStream is = null;
        int len = 50;       
        
        try {
            URL mUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)mUrl.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            if(response == 200) {
                is = conn.getInputStream();
                Reader reader = new InputStreamReader(is, "UTF-8" );
                char[] buf = new char[len];
                reader.read(buf);
                return new String(buf);
            }
           
        }catch(Exception e) {
            Log.e(TAG,"error message :" + e.getMessage());
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "fail";
 
    }
}
