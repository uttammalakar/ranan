package www.ratan.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Total extends AppCompatActivity implements View.OnClickListener {
      private Button stockin,stockout,total;
    MydatabaseHelper mydatabaseHelper;

    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //accotion bar hide
        getSupportActionBar().hide();
        //end
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
                stringBuffer.append("Quantity :"+cursor.getString(3)+"\n");
                stringBuffer.append("Price :"+cursor.getString(2)+"\n\n");

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
                stringBuffer.append("Quantity:"+cursor1.getString(3)+"\n");
                stringBuffer.append("Price:"+cursor1.getString(2)+"\n\n");

                // try to new about intager



                ///end

            }
            showData("All Stock out product",stringBuffer.toString());

        }
        // show total


else if(v.getId()==R.id.showtotal)
        {

            Set<String> set=new HashSet<>();

            Map<String,Double > map=new HashMap<>();

            Cursor cursor=mydatabaseHelper.displayAllData();
            if (cursor.getCount()==0){
                showData("worng","No data");
                Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String,Integer> q=new HashMap<>();

            while (cursor.moveToNext()){

                // try to new about intager

                String pname=cursor.getString(1).trim();

                int quan=Integer.parseInt( cursor.getString(3) );
                double pr=Double.parseDouble( cursor.getString(2) );

      if(!map.containsKey(pname)) {

          map.put(pname, pr);

      }else{

          map.put(pname,map.get(pname)+pr);

      }

      if(!q.containsKey(pname)) {

          q.put(pname, quan);


      }else{

          q.put(pname,q.get(pname)+quan);

          ///end
      }

            }

Cursor cursor1=mydatabaseHelper.displayAllDatastockout();

            /*

                stringBuffer.append("ID"+cursor1.getString(0)+"\n");
                stringBuffer.append(cursor1.getString(4)+"\n");
                stringBuffer.append("Product:"+cursor1.getString(1)+ "\n");
                stringBuffer.append("Quantity:"+cursor1.getString(3)+"\n");
                stringBuffer.append("Price:"+cursor1.getString(2)+"\n\n");

             */

            while(cursor1.moveToNext()){

                String productName=cursor1.getString(1).trim();

                double price=Double.parseDouble(cursor1.getString(2));

                String id=cursor1.getString(0);

                int quality=Integer.parseInt(cursor1.getString(3));

                if(set.size()!=0 && set.contains(productName)){

                    try {

                        map.put(productName, map.get(productName) - price);
                        q.put(productName, q.get(productName) - quality);

                        Toast.makeText(this,"am here",Toast.LENGTH_LONG);

                    }catch (Exception e){


                    }

                }else{

                    map.put(productName, map.get(productName) - price);

                    try {


                        q.put(productName, q.get(productName) - quality);

                    }catch (Exception e){


                    }

                    set.add(productName);

                }

            }

StringBuilder sb=new StringBuilder();

         for(String i: map.keySet()){

             try {

                 sb.append("Name: " + i + "\n" + "Quan: " + q.get(i) + "\n" + "Price: " + map.get(i) + "\n\n");

             }catch (Exception e){


             }

         }

         showData("summary",sb.toString());

        }



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