package com.example.jamarco.edittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mEditName;
    EditText mEditEmail;
    EditText mEditPass;
    EditText mEditCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditName = (EditText) findViewById(R.id.editName);
        mEditEmail = (EditText) findViewById(R.id.editEmail);
        mEditPass = (EditText) findViewById(R.id.editPass);
        mEditCep = (EditText) findViewById(R.id.editCEP);

        mEditPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v == mEditPass && EditorInfo.IME_ACTION_DONE == actionId){
                    String name = mEditName.getText().toString();
                    String email = mEditEmail.getText().toString();
                    String password = mEditPass.getText().toString();

                    boolean ok = true;

                    if (name.isEmpty()){
                        mEditName.setError(getString(R.string.error_msg_name));
                    }

                    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        mEditEmail.setError(getString(R.string.error_msg_email));
                        ok = false;
                    }

                    if (!password.equals("123")){
                        mEditPass.setError(getString(R.string.error_msg_pass));
                        ok = false;
                    }

                    if (ok){
                        Toast.makeText(getBaseContext(),getString(R.string.sucess_msg,name,email),Toast.LENGTH_SHORT).show();
                    }

                }
                return true;
            }
        });

        mEditCep.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating){
                    isUpdating = false;
                    return;
                }

                boolean hasMask = s.toString().indexOf('.') > -1 ||
                        s.toString().indexOf('-') > - 1;

                String str = s.toString()
                        .replaceAll("[.]","")
                        .replaceAll("[-]","");

                if (count > before){
                    if (str.length() > 5){
                        str = str.substring(0,2) + '.' +
                                str.substring(2,5) + '-' +
                                str.substring(5);
                    }
                    else if (str.length() > 2){
                        str = str.substring(0,2) + '.' +
                                str.substring(2);
                    }

                    isUpdating = true;
                    mEditCep.setText(str);
                    mEditCep.setSelection(mEditCep.getText().length());
                }
                else {
                    isUpdating = true;
                    mEditCep.setText(str);
                    mEditCep.setSelection(Math.max(0,Math.min(hasMask ?
                    start - before :start,str.length())));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
