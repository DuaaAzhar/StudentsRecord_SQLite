package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class StudentsDB {
    public static final String KEY_ID ="_id";
    public static final String KEY_NAME ="Person_Name";
    public static final String KEY_CGPA ="_CGPA";
    public static final String KEY_CELL ="_Cell";


    private final  String DATABASE_NAME="ContactsDB";
    private final  String DATABASE_TABLE="StudentsTable";
    private final  int DATABASE_VERSION=1;

    private DBHelper ourHelper;  //it is like the viewholder that we make in recyclerView
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public StudentsDB(Context context){
        ourContext=context;
    }
    private class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context){
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            /*
            we should make query for
            CREATE TABLE ContactsTable(
            _id INTEGER PRIMARY KEY AUTOINCREMENT,
            person_name TEXT NOT NULL,
            _cell TEXT NOT NULL);
             */
            String sqlCode= "CREATE TABLE "+DATABASE_TABLE+"("+
                    KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    KEY_NAME+ " TEXT NOT NULL,"+
                    KEY_CELL+ " TEXT NOT NULL,"+
                    KEY_CGPA+ " TEXT NOT NULL);";
            db.execSQL(sqlCode);



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);

        }
    }
    public StudentsDB open(){
        ourHelper=new DBHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        ourHelper.close();
    }
    public long CreateEntry(String Id, String name, String cell, String CGPA){
        long create=-1;
        String Query = "Select * from " + DATABASE_TABLE + " where " + KEY_ID + " = " + Id;
        Cursor c=ourDatabase.rawQuery(Query,null);
        if(c.getCount()<=0)
        {
            ContentValues cv=new ContentValues();
            cv.put(KEY_ID, Id);
            cv.put(KEY_NAME, name);
            cv.put(KEY_CELL,cell);
            cv.put(KEY_CGPA, CGPA);
            create=ourDatabase.insert(DATABASE_TABLE,null,cv);
        }
        else {
            Toast.makeText(ourContext, "Id Already Exists!!", Toast.LENGTH_SHORT).show();
        }

        return create;
    }
    public String ShowData(){
        String[] columns= new String[]{KEY_ID,KEY_NAME,KEY_CELL,KEY_CGPA};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        String result="";
        int ID=c.getColumnIndex(KEY_ID);
        int Name=c.getColumnIndex(KEY_NAME);
        int Cell=c.getColumnIndex(KEY_CELL);
        int cgpa=c.getColumnIndex(KEY_CGPA);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result=result+"ID: "+ c.getString(ID) +"\n"+
                    "Name: "+c.getString(Name)+ "\n"+
                    "Contact Number: "+c.getString(Cell)+ "\n"+
                    "CGPA: "+c.getString(cgpa)+"\n";

        }
        c.close();
    return result;
    }
    public long UpdateEntry(String Id, String newName, String newCell, String newCgpa){
        ContentValues cv=new ContentValues();
        cv.put(KEY_ID,Id);
        cv.put(KEY_NAME,newName);
        cv.put(KEY_CELL,newCell);
        cv.put(KEY_CGPA,newCgpa);
        long update=-1;
        String Query = "Select * from " + DATABASE_TABLE + " where " + KEY_ID + " = " + Id;
        Cursor c=ourDatabase.rawQuery(Query,null);
        if(c.getCount()<=0)
        {
            Toast.makeText(ourContext, "Record Does Not Exist!!", Toast.LENGTH_SHORT).show();
        }
        else {
            update=ourDatabase.update(DATABASE_TABLE,cv,KEY_ID+"=?", new String[]{Id});
            Toast.makeText(ourContext, "Record Updated Successfully!!", Toast.LENGTH_SHORT).show();
        }
        return update;
    }
    public long DeleteData(String Id){
        long del=-1;
        String Query = "Select * from " + DATABASE_TABLE + " where " + KEY_ID + " = " + Id;
        Cursor c=ourDatabase.rawQuery(Query,null);
        if(c.getCount()<=0)
        {
            Toast.makeText(ourContext, "Record Does Not Exist!!", Toast.LENGTH_SHORT).show();
        }
        else {
            del=ourDatabase.delete(DATABASE_TABLE, KEY_ID + "=?", new String[]{Id});
            Toast.makeText(ourContext, "Record Deleted Successfully!!", Toast.LENGTH_SHORT).show();
        }
        return del;
    }

}
