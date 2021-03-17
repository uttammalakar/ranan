package www.uttammalakar.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Total extends AppCompatActivity implements View.OnClickListener {
      private Button stockin,stockout,total;
    MydatabaseHelper mydatabaseHelper;

    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        stockin=(Button) findViewById(R.id.showstockin);
        stockout=(Button)findViewById(R.id.showstockout);
        total=(Button)findViewById(R.id.showtotal);


        stockin.setOnClickListener(this);
        stockout.setOnClickListener(this);
        total.setOnClickListener(this);

        mydatabaseHelper=new MydatabaseHelper(this);

        sqLiteDatabase=mydatabaseHelper.getWritableDatabase();


    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.showstockin){

            Cursor cursor=mydatabaseHelper.displayAllData();
            if (cursor.getCount()==0){
                 showData("worng","No data");
                Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();
                return;
                                     }
            StringBuffer stringBuffer=new StringBuffer();
            while (cursor.moveToNext()){

                stringBuffer.append("ID"+cursor.getString(0)+"\n");
                stringBuffer.append(cursor.getString(4)+"\n");
                stringBuffer.append("Product :"+cursor.getString(1)+"\n");
                stringBuffer.append("Price :"+cursor.getString(2)+"\n");
                stringBuffer.append("Quantity :"+cursor.getString(3)+"\n\n");

            }
            showData("All Stock in product",stringBuffer.toString());

        }

        //my new code for stock out
        if (v.getId()==R.id.showstockout){

            Cursor cursor1=mydatabaseHelper.displayAllDatastockout();



            if (cursor1.getCount()==0){
                showData("worng","No data");
                Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuffer stringBuffer=new StringBuffer();

            while (cursor1.moveToNext()){
                stringBuffer.append("ID"+cursor1.getString(0)+"\n");
                stringBuffer.append(cursor1.getString(4)+"\n");
                stringBuffer.append("Product:"+cursor1.getString(1)+ "\n");
                stringBuffer.append("Price:"+cursor1.getString(2)+"\n");
                stringBuffer.append("Quantity:"+cursor1.getString(3)+"\n\n");
                // try to new about intager
                int a=cursor1.getInt(3);
                    a=+a;
                stringBuffer.append("total:"+a+"\n");

                a=cursor1.getCount();
                stringBuffer.append(a);



                ///end

            }
            showData("All Stock out product",stringBuffer.toString());

        }
        // show total





            //end show total

    }
    public void showData(String title,String message){


        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.create();

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setCancelable(true);
        builder.show();

    };

}