package www.uttammalakar.com;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Addproduct extends AppCompatActivity implements View.OnClickListener {

    EditText product;

    Button button;

    MydatabaseHelper mydatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        mydatabaseHelper=new MydatabaseHelper(this);

        SQLiteDatabase sqLiteDatabase=mydatabaseHelper.getWritableDatabase();


product=(EditText)findViewById(R.id.Product);

button =(Button)findViewById(R.id.AddProduct);

if(product.getText().toString().trim().length()>=-1 ) {

   // Toast.makeText(getApplicationContext(),"I am here",Toast.LENGTH_LONG).show();

    button.setOnClickListener(this);

}else{

    Toast.makeText(getApplicationContext(),"Empty information is not allowed",Toast.LENGTH_LONG).show();

}

    }

    @Override
    public void onClick(View v) {


        String  Product=product.getText().toString();

        if( v.getId()==R.id.AddProduct ){
            if (product.length()==0){ product.setError("Product name");}
        else {
                try {

                    long x = mydatabaseHelper.insertData1("product", Product);

                    if (x != -1) {

                        Toast.makeText(getApplicationContext(), "The data is stored successfully", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(getApplicationContext(), "The data is not stored sucessfully", Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {


                }
            }
        }

    }
}