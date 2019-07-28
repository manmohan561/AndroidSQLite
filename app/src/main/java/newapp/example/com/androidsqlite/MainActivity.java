package newapp.example.com.androidsqlite;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;
    private Button  btnViewData;
    int y=0, x=0,p=0,q=0,trials=5;
    EditText e1;
    EditText e2;
    TextView t2,t;
    Button b1,b2;
    RelativeLayout lay;
    public void set(int x,int y)
    {
        int l=Math.abs(x-y);
        if(l<=50) {
            if (x > y) {
                t2.setText( "Lower Age" );
                p++;
                lay.setBackgroundColor( Color.rgb( l + 80, Math.abs( 50 - l ) + 50, 0 ) );
            } else if (x == y) {
                t2.setText( "Correct Age" );
                q++;
                lay.setBackgroundColor( Color.GREEN );
            } else if (x < y) {
                t2.setText( "Higher Age" );
                p++;
                lay.setBackgroundColor( Color.rgb( l + 80, Math.abs( 50 - l ) + 50, 0 ) );
            }
        }
        else
        {
            t2.setText( "High Deviation" );
            lay.setBackgroundColor( Color.RED);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t = findViewById(R.id.textView2);
        e1 = findViewById(R.id.edt1);
        e2 = findViewById(R.id.edt2);
        t2 = findViewById(R.id.tv2);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        btnViewData = findViewById(R.id.btnView);
        lay=findViewById( R.id.la1 );
        mDatabaseHelper = new DatabaseHelper(this);

        /*e1.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str1=e1.getText().toString().trim();
                b1.setEnabled( !str1.isEmpty()) ;

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        } );
*/


        b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                x=Integer.parseInt(e1.getText().toString());
                if(x>0)
                {
                    toastMessage("Your Age is Set");
                    e1.setText( "" );
                }
                else if(x==0)
                {
                    toastMessage("You must put something in the text field!");
                }


            }
        } );

        b2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String value=e2.getText().toString();
                y=Integer.parseInt( value );
                trials--;
                if(trials>=0)
                {
                    set( x, y );
                    if(trials==0)
                    toastMessage(trials+"Trials remaining \n tap once more to know the result");
                    else
                        toastMessage(trials+"Trials remaining ");
                }
                else if(trials==-1){
                    String newEntry =("Incorrect= "+p+" AND "+"Correct= "+q);
                    AddData(newEntry);
                        t2.setText( p +" Times Failed" );
                }
                else if(trials<-1){
                    toastMessage( "Game is Over" );
                }
            }
        } );

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setText("");
                e2.setText( "" );
                t2.setText( "" );
                trials=5;p=0;q=0;
                lay.setBackgroundColor( Color.WHITE);
                Intent intent = new Intent(MainActivity.this, newapp.example.com.androidsqlite.ListDataActivity.class);
                startActivity(intent);

            }
        });

    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}