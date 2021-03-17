package www.uttammalakar.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MydatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="uttam.db";
    private static final String TABLE_NAMEIN="stockin";
    private static final String TABLE_NAMEOUT="stockout";
    private static final String TABLE_NAMEPRO="product";
    private static final int VERSION=3;
    //colam name
    private static final String ID="id";
    private static final String NAME="name";
    private static final String PRICE="price";
    private static final String QUANTITY="quantity";
    private static final String PRODUCT="products";
    private static final String DATE="date";
    //****************************************

    //create table STOCK IN
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAMEIN+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR(255),"+PRICE+" INTEGER,"+QUANTITY+" INTEGER,"+DATE+" VARCHAR(255));";

    //table stock out
    private static final String CREATE_TABLE2 = "CREATE TABLE "+TABLE_NAMEOUT+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR(255),"+PRICE+" INTEGER,"+QUANTITY+" INTEGER,"+DATE+" VARCHAR(255));";
    //product table
    private static final String CREATE_TABLE3 = "CREATE TABLE "+TABLE_NAMEPRO+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"+PRODUCT+" VARCHAR(255),"+DATE+" VARCHAR(255) );";
    //******************************show table*******************************************
    private static final String SHOWSTOCKIN = "SELECT * FROM "+TABLE_NAMEIN;
    private static final String SHOWSTOCKOUT = "SELECT * FROM "+TABLE_NAMEOUT;
    //drop table

    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAMEIN;
    private static final String DROP_TABLE2="DROP TABLE IF EXISTS "+TABLE_NAMEOUT;
    private static final String DROP_TABLE3="DROP TABLE IF EXISTS "+TABLE_NAMEPRO;

    private Context context;

    public MydatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Toast.makeText(context,"Create table",Toast.LENGTH_SHORT).show();

            db.execSQL(CREATE_TABLE);


            db.execSQL(CREATE_TABLE2);

            db.execSQL(CREATE_TABLE3);
            onCreate(db);

        }catch (Exception e)
        {
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            Toast.makeText(context,"onUpgrade is called",Toast.LENGTH_SHORT).show();

            db.execSQL(DROP_TABLE);

           // onCreate(db);

            db.execSQL(DROP_TABLE2);

         //   onCreate(db);

            db.execSQL(DROP_TABLE3);

            onCreate(db);
        }catch (Exception e)
        {
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_SHORT).show();
        }

}

    public long insertData(String table_Name,String name,String price,String quantity)
    {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        //////date try
        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        ////*********************
        contentValues.put(NAME,name);
        contentValues.put(PRICE,Integer.parseInt(price));
        contentValues.put(QUANTITY,Integer.parseInt(quantity));
        contentValues.put(DATE,currentDate);


        long rowId =  sqLiteDatabase.insert(table_Name,null,contentValues);

        sqLiteDatabase.close();

        return rowId;
        //**********************************************


    }

    public long insertData1(String table_Name,String product)
    {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        //////date insert automatic
        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        ////*********************
        contentValues.put("products",product);
        contentValues.put("date",currentDate);

        long rowIdd =  sqLiteDatabase.insert(table_Name,null,contentValues);

        sqLiteDatabase.close();

        return rowIdd;
    }
//stock in
   public Cursor  displayAllData(){
    SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
    Cursor cursor=sqLiteDatabase.rawQuery(SHOWSTOCKIN,null );

      return cursor;
                                 }
                                 //same methode for show stock out
 public Cursor  displayAllDatastockout(){

     SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
     Cursor cursor1=sqLiteDatabase.rawQuery(SHOWSTOCKOUT,null);
     return cursor1;

 }
 //3/14/2021 spinner code by uttam
 public ArrayList<String> grtAllProvince(){

        ArrayList<String> list=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAMEPRO;

            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    String products = cursor.getString(cursor.getColumnIndex("products"));
                    list.add(products);
                }

            }
            db.setTransactionSuccessful();
        }catch (Exception e){

            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
 }

}
