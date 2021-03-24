package www.ratan.com;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class StockIn extends AppCompatActivity implements View.OnClickListener {
    private EditText productName,priceEdittext,quantityEdittext;

    MydatabaseHelper mydatabaseHelper;
    SQLiteDatabase sqLiteDatabase;

    static Stack stack=new Stack();

    static String products[];

    Heap<String> heap=new Heap<>();

    static class Node{

        String product;

        Node next;

        public Node(String product) {
            this.product = product;
        next=null;

        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "product='" + product + '\'' +
                    ", next=" + next +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return product.equals(node.product) &&
                    next.equals(node.next);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public int hashCode() {
            return Objects.hash(product, next);
        }
    }

    static  class Stack{

int length;
Node node;

        public Stack() {

            length=0;
            node=null;
        }

        public void  push(String product){

            Node top=new Node(product);
            top.setNext(node);

            node=top;

            length++;

        }

        public boolean isEmpty(){

            return length==0;

        }

        public int size(){

            return length;
        }

        public String pop() throws Exception {

            if( isEmpty() ){

                throw new Exception("The stack is empty");

            }

            String res=node.getProduct();

            node=node.getNext();

            length--;

            return res;

        }

    }

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //accotion bar hide
        getSupportActionBar().hide();
        //end
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in);

        mydatabaseHelper=new MydatabaseHelper(this);

        sqLiteDatabase=mydatabaseHelper.getWritableDatabase();

       // productName=(EditText) findViewById(R.id.nameeditstockin);
        priceEdittext= (EditText) findViewById(R.id.priceeditstockin);
        quantityEdittext=(EditText)findViewById(R.id.producteditstockin);
        Button savebutton = (Button) findViewById(R.id.buttonstockin);
        spinner=(Spinner)findViewById(R.id.spinnerstockin);

        savebutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {



        //uttam code
       // if(productName.length()==0){ productName.setError("Enter name"); }

         if (priceEdittext.length()==0){priceEdittext.setError("Enter price");}
        else if (quantityEdittext.length()==0){quantityEdittext.setError("Enter quantity");}
        else{
            String product_name=spinner.getSelectedItem().toString();
       // String name=productName.getText().toString().trim();
        String price=priceEdittext.getText().toString().trim();
        String quantity=quantityEdittext.getText().toString().trim();
         if (v.getId()==R.id.buttonstockin) {

             long rowId = mydatabaseHelper.insertData("stockin", product_name, price, quantity);

             if (rowId == -1) {

                 Toast.makeText(getApplicationContext(), "NOT SAVE ", Toast.LENGTH_SHORT).show();

             } else {

                 Toast.makeText(getApplicationContext(), "SAVE", Toast.LENGTH_SHORT).show();

             }

         }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Cursor cursor=mydatabaseHelper.displayAllData();

        while(cursor.moveToNext()){

stack.push(cursor.getString(1));
//heap.push(cursor.getColumnName(1));

        }

        products=new String[stack.size()];

        int index=0;

        while(!stack.isEmpty()){

            try {

                products[index++]=stack.pop();

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

//uttam data upload by spinner coad 15/03/2021
        ArrayList<String> listpro=mydatabaseHelper.getAllproduct();
        Spinner sp_in=(Spinner)findViewById(R.id.spinnerstockin);
        ArrayAdapter <String> adapter_in=new ArrayAdapter<String>(this,R.layout.spinnerlayout,R.id.text,listpro);
        sp_in.setAdapter(adapter_in);
        sp_in.setEnabled(true);
        sp_in.setVisibility(View.VISIBLE);

// end

    /*amit code spinner

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        spinner.setEnabled(true);
        spinner.setVisibility(View.VISIBLE);

*/
    }
}

