package com.vinappstudio.honeydolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText enterMessage;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterMessage = findViewById(R.id.editText);
        saveButton = findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!enterMessage.getText().toString().equals("")) {
                    String message = enterMessage.getText().toString();
                    writeToFile(message);
                }else {
                    // do nothing for now
                     }
            }
        });
        try {
            if (readFromFile()!=null) {
                enterMessage.setText(readFromFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
      /* there are classes that come with java,android that are made to do all of the heavy
      leafting for us , all of the data we feed into OutputScreenWriter it comes in as bits
       (Stream of bits ), in order to trans form those bits into Strings or Vice versa
       so they  can b saved into file  , and when we read them  we do the reverse */

    public void writeToFile(String message) {
/*             we are dealing with stream of data  , and things can go wrong quickly  we
        need to add an exception*/
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("todoList.txt"
                    , Context.MODE_PRIVATE));
            outputStreamWriter.write(message);
            outputStreamWriter.close();
            // always close your streams because streams takes a lot of memory
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFromFile() throws IOException {
        String result = "";

        InputStream inputStream = openFileInput("todoList.txt");
   /*      add exception to method signature , dont have to surround with catch every time
         input stream is still in bits,  we need inputStream reader so it can read
         next we need Buffer reader, class that create a bucket/buffer where we put
         all of the bits inside , then we able to read those bits in a Better faster Way*/
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String tempString = "";
            // now we get actual string into builds by
            //bits into char into String ,to  show it t users
            StringBuilder stringBuilder = new StringBuilder();
            while ((tempString = bufferedReader.readLine()) != null) {
                stringBuilder.append(tempString);
            }
            inputStream.close();
            result = stringBuilder.toString();
        }

        return result;
    }
}