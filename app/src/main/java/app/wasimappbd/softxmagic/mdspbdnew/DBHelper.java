package app.wasimappbd.softxmagic.mdspbdnew;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.net.IDN;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Mdsp2018.db";
    public static final String PROJECT_TABLE_NAME = "Projects";
    public static final String USER_TABLE_NAME = "Users";
    public static final String PROJECT_TABLE_SUBMISSION = "Submission";
    public static final String PROJECT_TABLE_FEEDBACK = "Feedback";
    public static final String PROJECT_TABLE_GALLERY = "ImageGallery";
    public static final String RESOURCE_TABLE_GALLERY = "ResourceGallery";
    public static final String PROJECT_TABLE_RESOURCE = "Resource";
    public static final String PROJECT_TABLE_WORKPHASE = "WorkPhases";
    Context context;
    private HashMap hp;
    SharedPreferences sharedPref;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 43);
    }

    // Projects table create statement
    private static final String CREATE_TABLE_SHELTER = "CREATE TABLE " + PROJECT_TABLE_NAME
            + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,code TEXT,district INTEGER, date DATETIME)";

    // Projects table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE_NAME
            + "(id INTEGER PRIMARY KEY AUTOINCREMENT, userid INTEGER, name TEXT, contact TEXT, username TEXT, password TEXT, organization TEXT, designation TEXT, district INTEGER, pin INTEGER, date DATETIME)";


    // Projects table create statement
    private static final String CREATE_TABLE_SUBMISION = "CREATE TABLE " + PROJECT_TABLE_SUBMISSION
            + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "refid INTEGER, " +
            "userid INTEGER, " +
            "ShelterCode TEXT, " +
            "MajorTasks TEXT, " +
            "Tasks TEXT, " +
            "Comments TEXT, " +
            "Latitude TEXT, " +
            "Longitude TEXT, " +
            "Area TEXT, " +
            "Status TEXT, " +
            "SendServer INTEGER, " +
            "SubmisionDate DATETIME)";


    private static final String CREATE_TABLE_WORKPHASE = "CREATE TABLE " + PROJECT_TABLE_WORKPHASE
            + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tid INTEGER, " +
            "SubTask TEXT, " +
            "MajorTasks TEXT, " +
            "Flag INTEGER)";

    private static final String CREATE_TABLE_RESOURCE = "CREATE TABLE " + PROJECT_TABLE_RESOURCE
            + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "refid INTEGER, " +
            "userid INTEGER, " +
            "shelter INTEGER, " +
            "materials INTEGER, " +
            "SendServer INTEGER, " +
            "date DATETIME)";

    private static final String CREATE_TABLE_GALLERY = "CREATE TABLE " + PROJECT_TABLE_GALLERY
            + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userid INTEGER, " +
            "subid INTEGER, " +
            "shelter TEXT, " +
            "Image1 TEXT, " +
            "Caption1 TEXT, " +
            "Image2 TEXT, " +
            "Caption2 TEXT, " +
            "Image3 TEXT, " +
            "Caption3 TEXT, " +
            "Image4 TEXT, " +
            "Caption4 TEXT, " +
            "Image5 TEXT, " +
            "Caption5 TEXT, " +
            "date DATETIME)";


    private static final String CREATE_RESOURXE_GALLERY = "CREATE TABLE " + RESOURCE_TABLE_GALLERY
            + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userid INTEGER, " +
            "resid INTEGER, " +
            "Image1 TEXT, " +
            "Caption1 TEXT, " +
            "Image2 TEXT, " +
            "Caption2 TEXT, " +
            "Image3 TEXT, " +
            "Caption3 TEXT, " +
            "Image4 TEXT, " +
            "Caption4 TEXT, " +
            "Image5 TEXT, " +
            "Caption5 TEXT, " +
            "date DATETIME)";
    ///////////// Feedback form table ////////
    private static final String CREATE_TABLE_FEEDBACK = "CREATE TABLE " + PROJECT_TABLE_FEEDBACK
            + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "refid INTEGER, " +
            "userid INTEGER, " +
            "subid INTEGER, " +
            "shelter TEXT, " +
            "mtask TEXT, " +
            "subdate DATETIME, " +
            "hqfeed TEXT, " +
            "status INTEGER, " +
            "hqfeeddate DATETIME, " +
            "userfeed TEXT, " +
            "userfeeddate DATETIME, " +
            "userimg TEXT)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_GALLERY);
        db.execSQL(CREATE_TABLE_SHELTER);
        db.execSQL(CREATE_TABLE_SUBMISION);
        db.execSQL(CREATE_TABLE_FEEDBACK);
        db.execSQL(CREATE_RESOURXE_GALLERY);
        db.execSQL(CREATE_TABLE_RESOURCE);
        db.execSQL(CREATE_TABLE_WORKPHASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //db.execSQL("DROP TABLE IF EXISTS Projects");
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROJECT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROJECT_TABLE_SUBMISSION);
        db.execSQL("DROP TABLE IF EXISTS " + PROJECT_TABLE_FEEDBACK);
        db.execSQL("DROP TABLE IF EXISTS " + PROJECT_TABLE_GALLERY);
        db.execSQL("DROP TABLE IF EXISTS " + RESOURCE_TABLE_GALLERY);
        db.execSQL("DROP TABLE IF EXISTS " + PROJECT_TABLE_RESOURCE);
        db.execSQL("DROP TABLE IF EXISTS " + PROJECT_TABLE_WORKPHASE);
        onCreate(db);
    }







    /////// Insert ///////////
    public boolean insertAllSyncData(HashMap<String, String> shelterValues,HashMap<String, String> taskValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("code", shelterValues.get("code"));
        database.insert(PROJECT_TABLE_NAME, null, values);

        ContentValues tVal = new ContentValues();
        tVal.put("MajorTasks", taskValues.get("name"));
        tVal.put("SubTask", taskValues.get("stask"));
        tVal.put("Flag", taskValues.get("flag"));
        database.insert(PROJECT_TABLE_WORKPHASE, null, tVal);

        database.close();
        return true;
    }
    /*/////// Get data with id ///////////
    public Cursor checkExisting(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_NAME + " where code='"+code+"'", null );
        //db.close();
        return res;
    }

    public boolean updateShelter(Integer id,String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        db.update(PROJECT_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    */

    /* ====================================== Shelter All Query ================================================*/

    /////// Insert ///////////
    /*public boolean insertTask(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put("task_id", queryValues.get("task_id"));
        values.put("task_name", queryValues.get("task_name"));
        values.put("flag", queryValues.get("flag"));
        database.insert(PROJECT_TABLE_TASK, null, values);
        database.close();
        return true;
    }

    public boolean insertTask(String task, Integer flag) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // values.put("task_id", queryValues.get("task_id"));
        values.put("task_name", task);
        values.put("flag", Integer.toString(flag));
        database.insert(PROJECT_TABLE_TASK, null, values);
        database.close();
        return true;
    }*/

    public boolean insertTask(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tid", queryValues.get("id"));
        values.put("MajorTasks", queryValues.get("name"));
        values.put("SubTask", queryValues.get("stask"));
        values.put("Flag", queryValues.get("flag"));
        database.insert(PROJECT_TABLE_WORKPHASE, null, values);
        database.close();
        return true;
    }

    /////// Get data with id ///////////
    public Cursor checkExistingTask(int code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select id from " + PROJECT_TABLE_WORKPHASE + " where tid ='"+code+"'", null );
        //db.close();
        return res;
    }

    public boolean updateTask(Integer id,String code,String flag,String stask) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MajorTasks", code);
        contentValues.put("SubTask", stask);
        contentValues.put("Flag", flag);

        db.update(PROJECT_TABLE_WORKPHASE, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }


    public Cursor getAllTasks() {
        //String sql = "SELECT * FROM " + PROJECT_TABLE_WORKPHASE + " ORDER BY Flag ASC";
        String stask = "Task";
        String sql = "SELECT * FROM " + PROJECT_TABLE_WORKPHASE + " WHERE SubTask = '"+stask+"' ORDER BY Flag ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public Cursor getAllSubTasks(String mtask) {
        String sql = "SELECT * FROM " + PROJECT_TABLE_WORKPHASE + " WHERE SubTask = '"+mtask+"' ORDER BY Flag ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }


    public ArrayList<String> getAllTasks1() {
        ArrayList<String> array_list = new ArrayList<String>();
        String stask = "Task";
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + PROJECT_TABLE_WORKPHASE + " WHERE SubTask = '"+stask+"' ORDER BY Flag ASC";
        Cursor res = db.rawQuery(sql, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
           // array_list.add(res.getString(res.getColumnIndex("MajorTasks")));
            array_list.add(res.getString(res.getColumnIndex("MajorTasks")));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }



    public boolean deleteAllTasks () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROJECT_TABLE_WORKPHASE, null, null);
        return true;
    }

    /* ====================================== Shelter All Query ================================================*/




    /////// Insert ///////////
    public boolean insertShelter(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("code", queryValues.get("code"));
        values.put("district", queryValues.get("district"));
        database.insert(PROJECT_TABLE_NAME, null, values);
        database.close();
        return true;
    }
    /////// Get data with id ///////////
    public Cursor checkExisting(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_NAME + " where code='"+code+"'", null );
        //db.close();
        return res;
    }

    public boolean updateShelter(Integer id,String code,Integer district) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        contentValues.put("district", district);
        db.update(PROJECT_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_NAME + " where id="+id+"", null );
        //res.close();
        return res;
    }

    public ArrayList<String> getLikeShelter(String key, int did) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = null;
       /* if(did!=0){
            sql = "SELECT code FROM " + PROJECT_TABLE_NAME + " WHERE code LIKE '%" + key + "%' AND district = '"+ did +"'";
        }
        else{
            sql = "SELECT code FROM " + PROJECT_TABLE_NAME + " WHERE code LIKE '%" + key + "%'";
        }*/
        sql = "SELECT code FROM " + PROJECT_TABLE_NAME + " WHERE code LIKE '%" + key + "%'";
        Cursor res = db.rawQuery(sql, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex("code")));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<String> getAllShelter() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from Projects", null );
        Cursor res = db.rawQuery("SELECT code,district FROM " + PROJECT_TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex("code")));
            array_list.add(res.getString(res.getColumnIndex("district")));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    /////// Update Data ///////////
    public boolean updateContact (Integer id, String name, String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("code", code);
        db.update(PROJECT_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    /////// Count Total Data ///////////
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJECT_TABLE_NAME);
        return numRows;
    }
    /////// Delete one Data ///////////
    public Integer deleteShelter (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PROJECT_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    /////// Delete All Data ///////////
    public boolean deleteAllShelter () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROJECT_TABLE_NAME, null, null);
        return true;
    }


    /* ====================================== Submittd Form All Query ================================================*/
    /////// Insert ///////////
    public boolean insertSubmittedForm(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("refid", queryValues.get("refid"));
        values.put("userid", queryValues.get("userid"));
        values.put("ShelterCode", queryValues.get("shelid"));
        values.put("MajorTasks", queryValues.get("mtask"));
        values.put("Tasks", queryValues.get("task"));
        values.put("Comments", queryValues.get("ucom"));
        values.put("Latitude", queryValues.get("latv"));
        values.put("Longitude", queryValues.get("longv"));
        values.put("SubmisionDate", queryValues.get("SubmisionDate"));
        values.put("Status", "New");
        values.put("SendServer", 1);

        database.insert(PROJECT_TABLE_SUBMISSION, null, values);
        database.close();
        return true;
    }

    /////// Insert ///////////
    public boolean insertSaveForm(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        values.put("userid", queryValues.get("userid"));
        values.put("ShelterCode", queryValues.get("shelid"));
        values.put("MajorTasks", queryValues.get("mtask"));
        values.put("Tasks", queryValues.get("stask"));
        values.put("Comments", queryValues.get("ucom"));
        values.put("Latitude", queryValues.get("latv"));
        values.put("Longitude", queryValues.get("longv"));
        values.put("Area", queryValues.get("area"));
        values.put("SubmisionDate", date);
        values.put("Status", "New");
        values.put("SendServer", 0);

        database.insert(PROJECT_TABLE_SUBMISSION, null, values);
        database.close();
        return true;
    }


    /////// Get data with id ///////////
    public Cursor checkExistingSub(int refid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_SUBMISSION + " where refid="+refid+"", null );
        //db.close();
        return res;
    }
    /////// Get data with id ///////////
    public Cursor getDataSubmissionDetails(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_SUBMISSION + " where id="+id+"", null );
        //res.close();
        return res;
    }


    public Cursor getDataSUbmittedForm(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_SUBMISSION + " where id="+id+"", null );
        //res.close();
        return res;
    }


    public Cursor getDataMultiplrForm(String itemsid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_SUBMISSION + " where id in ("+ itemsid +") ", null );
        //res.close();
        return res;
    }

    public ArrayList<String> getAllLikeForms(int userid) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from Projects", null );
        Cursor res = db.rawQuery("SELECT * FROM " + PROJECT_TABLE_SUBMISSION + " WHERE userid = '" + userid + "'", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex("ShelterCode")));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<String> getAllFormsArray() {
        ArrayList<String> array_list = new ArrayList<String>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from Projects", null );
        Cursor res = db.rawQuery("SELECT * FROM " + PROJECT_TABLE_SUBMISSION, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex("refid")));
            res.moveToNext();
        }
        return array_list;
    }

    public Cursor getAllForms(int status,int userid) {
        String sql = "SELECT * FROM " + PROJECT_TABLE_SUBMISSION + " WHERE userid = '" + userid + "' AND SendServer = '" + status + "' ORDER BY refid DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public int getLastSubmissionId() {
        int id = 0;
        String sql = "SELECT * FROM " + PROJECT_TABLE_SUBMISSION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        if(res.moveToLast()){
            id = res.getInt(0);//to get id, 0 is the column index
        }
        //res.close();
        return id;
    }
    /////// Update Data ///////////
    public boolean updateSUbmittedForm (int id,int refid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SendServer", 1);
        contentValues.put("refid", refid);
        db.update(PROJECT_TABLE_SUBMISSION, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean updateSubmission (HashMap<String, String> queryValues,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("refid", queryValues.get("refid"));
        values.put("userid", queryValues.get("userid"));
        values.put("ShelterCode", queryValues.get("shelid"));
        values.put("MajorTasks", queryValues.get("mtask"));
        values.put("Tasks", queryValues.get("task"));
        values.put("Comments", queryValues.get("ucom"));
        values.put("Latitude", queryValues.get("latv"));
        values.put("Longitude", queryValues.get("longv"));
        values.put("SubmisionDate", queryValues.get("SubmisionDate"));
        values.put("Status", "New");
        values.put("SendServer", 1);

        db.update(PROJECT_TABLE_SUBMISSION, values, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean updateSaveSub (HashMap<String, String> queryValues,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        ContentValues values = new ContentValues();
        values.put("ShelterCode", queryValues.get("shelid"));
        values.put("MajorTasks", queryValues.get("mtask"));
        values.put("Tasks", queryValues.get("stask"));
        values.put("Comments", queryValues.get("ucom"));
        values.put("SubmisionDate", date);
        values.put("Status", "New");
        values.put("SendServer", 0);

        db.update(PROJECT_TABLE_SUBMISSION, values, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    /////// Count Total Data ///////////
    public int numberOfRowsSUbmittedForm(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJECT_TABLE_SUBMISSION);
        return numRows;
    }

    /////// Count Total Data ///////////
    public int totalPendingForm(int uid, int id){
        String sql = "SELECT * FROM " + PROJECT_TABLE_SUBMISSION + " WHERE SendServer = '"+ id +"' AND userid = '"+ uid +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        res.moveToLast();
        res.close();
        db.close();

        if(res.getCount() > 0) {
            int totalid = res.getCount();
            return totalid;
        }
        else{
            return 0;
        }


    }
    /////// Delete one Data ///////////
    /*public Integer deleteSUbmittedForm (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PROJECT_TABLE_SUBMISSION,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }*/
    public int deleteSUbmittedForm (long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PROJECT_TABLE_SUBMISSION,"id = " + id,null);
    }
    /////// Delete All Data ///////////
    public boolean deleteAllSUbmittedForm () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROJECT_TABLE_SUBMISSION, null, null);
        return true;
    }



    /* ====================================== Users Form All Query ================================================*/

    public Cursor loginWithPin(String upin) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + USER_TABLE_NAME + " where pin='"+upin+"'", null );
        //db.close();
        return res;
    }


    public Cursor checkExistingUser(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + USER_TABLE_NAME + " where userid='"+code+"'", null );
        //db.close();
        return res;
    }

    public boolean updateUsers(HashMap<String, String> queryValues,Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        values.put("userid", queryValues.get("userid"));
        values.put("name", queryValues.get("name"));
        values.put("contact", queryValues.get("contact"));
        values.put("username", queryValues.get("username"));
        values.put("password", queryValues.get("pass"));
        values.put("organization", queryValues.get("org"));
        values.put("designation", queryValues.get("desig"));
        values.put("district", queryValues.get("district"));
        values.put("pin", queryValues.get("pin"));
        values.put("date", date);
        db.update(USER_TABLE_NAME, values, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    /////////////////////////////// Insert ///////////////////////////////
    public boolean insertUser(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        values.put("userid", queryValues.get("userid"));
        values.put("name", queryValues.get("name"));
        values.put("contact", queryValues.get("contact"));
        values.put("username", queryValues.get("username"));
        values.put("password", queryValues.get("pass"));
        values.put("organization", queryValues.get("org"));
        values.put("designation", queryValues.get("desig"));
        values.put("district", queryValues.get("district"));
        values.put("pin", queryValues.get("pin"));
        values.put("date", date);

        database.insert(USER_TABLE_NAME, null, values);
        database.close();
        return true;
    }


    /////// Get data with id ///////////
    public Cursor getDataUsers(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + USER_TABLE_NAME + " where userid="+id+"", null );
        return res;
    }


    public ArrayList<String> getAllLikeUsers(int userid) {
        ArrayList<String> array_list = new ArrayList<String>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from Projects", null );
        Cursor res = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE userid = '" + userid + "'", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("username")));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<String> getAllUsers() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from Projects", null );
        Cursor res = db.rawQuery("SELECT code FROM " + USER_TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex("username")));
            res.moveToNext();
        }
        return array_list;
    }
    /////// Update Data ///////////
    public boolean updateUsers (Integer id, String name, String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("code", code);
        db.update(USER_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    /////// Count Total Data ///////////
    public int numberOfRowsUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USER_TABLE_NAME);
        return numRows;
    }
    /////// Delete one Data ///////////
    public Integer deleteUsers (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    /////// Delete All Data ///////////
    public boolean deleteAllUsers () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE_NAME, null, null);
        return true;
    }




    /* ====================================== Feedback form All Query ================================================*/
    /////////////////////////////// Insert ///////////////////////////////
    public boolean insertFeedback(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("refid", queryValues.get("refid"));
        values.put("userid", queryValues.get("userid"));
        values.put("shelter", queryValues.get("shelter"));
        values.put("mtask", queryValues.get("mtask"));
        values.put("subid", queryValues.get("subid"));
        values.put("subdate", queryValues.get("subdate"));
        values.put("hqfeeddate", queryValues.get("hqfeeddate"));
        values.put("hqfeed", queryValues.get("hqfeed"));
        values.put("status", queryValues.get("hqstatus"));
        values.put("userfeed", queryValues.get("userfeed"));
        //values.put("userimg", queryValues.get("userimg"));
        values.put("userfeeddate", queryValues.get("userfeeddate"));

        database.insert(PROJECT_TABLE_FEEDBACK, null, values);
        database.close();
        return true;
    }


    public int totalUserFeedback(int id){
        String sql = "SELECT * FROM " + PROJECT_TABLE_FEEDBACK + " WHERE userid = '"+ id +"' GROUP BY subid";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        res.moveToLast();
        if(res.getCount() > 0) {
            int totalid = res.getCount();
            return totalid;
        }
        else{
            return 0;
        }
    }

    /////// Get data with id ///////////
    public Cursor checkExistingFeed(int subid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_FEEDBACK + " where refid="+subid+"", null );
        //db.close();
        return res;
    }

    public Cursor checkExistingFeedback(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_FEEDBACK + " where refid="+id+"", null );
        //db.close();
        return res;
    }


    public boolean updateUserFeedback (HashMap<String, String> queryValues,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("status",  queryValues.get("status"));
        values.put("userfeed", queryValues.get("userfeed"));
        values.put("userfeeddate", queryValues.get("userfeeddate"));
        values.put("userimg", queryValues.get("userimg"));

        db.update(PROJECT_TABLE_FEEDBACK, values, "id = ? ", new String[] { Integer.toString(id) } );
        db.close();
        return true;
    }

    public boolean updateFeedback (HashMap<String, String> queryValues,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("refid", queryValues.get("refid"));
        values.put("shelter", queryValues.get("shelter"));
        values.put("mtask", queryValues.get("mtask"));
        values.put("subdate", queryValues.get("subdate"));
        values.put("userid", queryValues.get("userid"));
        values.put("hqfeeddate", queryValues.get("hqfeeddate"));
        values.put("subid", queryValues.get("subid"));
        values.put("hqfeed", queryValues.get("hqfeed"));
        values.put("status", queryValues.get("hqstatus"));
        values.put("userfeed", queryValues.get("userfeed"));
        values.put("userimg", queryValues.get("userimg"));
        values.put("userfeeddate", queryValues.get("userfeeddate"));

        db.update(PROJECT_TABLE_FEEDBACK, values, "refid = ? ", new String[] { Integer.toString(id) } );
        db.close();
        return true;
    }

    /////// Get data with id ///////////
    public Cursor getDataFeedback(int userid) {
        //String sql = "SELECT * FROM " + PROJECT_TABLE_SUBMISSION + " WHERE userid = '" + userid + "' AND SendServer = '" + status + "'";
        //String sql = "SELECT * FROM " + PROJECT_TABLE_FEEDBACK + " WHERE userid = '" + userid + "' AND (hqfs1 != 12 OR hqfs2 != 12 OR hqfs3 != 12)  ORDER BY id DESC";
        String sql = "SELECT * FROM " + PROJECT_TABLE_FEEDBACK + " WHERE userid = '" + userid + "' GROUP BY subid ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    /////// Get data with id ///////////
    public Cursor getSubmissionFeedbacks(int userid,int subid) {
        //String sql = "SELECT * FROM " + PROJECT_TABLE_SUBMISSION + " WHERE userid = '" + userid + "' AND SendServer = '" + status + "'";
        //String sql = "SELECT * FROM " + PROJECT_TABLE_FEEDBACK + " WHERE userid = '" + userid + "' AND (hqfs1 != 12 OR hqfs2 != 12 OR hqfs3 != 12)  ORDER BY id DESC";
        String sql = "SELECT * FROM " + PROJECT_TABLE_FEEDBACK + " WHERE userid = '" + userid + "' AND subid = '" + subid + "' ORDER BY id DESC ";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public ArrayList<String> getAllLikeFeedback(int userid,int subid) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from Projects", null );
        String sql = "SELECT * FROM " + PROJECT_TABLE_FEEDBACK + " WHERE userid = ? AND subid = ?";

        Cursor res = db.rawQuery(sql,new String[]{String.valueOf(userid),String.valueOf(subid)});


        //Cursor res = db.rawQuery(sql, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex("hqfeed")));
            array_list.add(res.getString(res.getColumnIndex("hqfeeddate")));
            array_list.add(res.getString(res.getColumnIndex("status")));
            array_list.add(res.getString(res.getColumnIndex("subid")));
            array_list.add(res.getString(res.getColumnIndex("userfeed")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllFeedback() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from Projects", null );
        Cursor res = db.rawQuery("SELECT shelter,status,userid FROM " + PROJECT_TABLE_FEEDBACK, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex("shelter")));
            array_list.add(res.getString(res.getColumnIndex("status")));
            array_list.add(res.getString(res.getColumnIndex("userid")));
            res.moveToNext();
        }
        return array_list;
    }
    /////// Update Data ///////////
    public boolean updateFeedback (Integer id, String name, String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("shelter", name);
        db.update(PROJECT_TABLE_FEEDBACK, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    /////// Count Total Data ///////////
    public int numberOfRowsFeedback(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJECT_TABLE_FEEDBACK);
        return numRows;
    }
    /////// Delete one Data ///////////
    public Integer deleteFeedback (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PROJECT_TABLE_FEEDBACK,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    /////// Delete one Data ///////////
    public Integer deleteFeedbackBySubmission (Integer subid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PROJECT_TABLE_FEEDBACK,
                "subid = ? ",
                new String[] { Integer.toString(subid) });
    }
    /////// Delete All Data ///////////
    public boolean deleteAllFeedback () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROJECT_TABLE_FEEDBACK, null, null);
        return true;
    }

    /* ====================================== Gallery form All Query ================================================*/
    /////////////////////////////// Insert ///////////////////////////////
    public boolean insertGallery(int uid,int sid, String shelter, String c1,String c2,String c3,String c4,String c5,
                                 String img1,String img2,String img3,String img4,String img5) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("userid", uid);
        values.put("subid", sid);
        values.put("shelter", shelter);
        values.put("Image1", img1);
        values.put("Image2", img2);
        values.put("Image3", img3);
        values.put("Image4", img4);
        values.put("Image5", img5);
        values.put("Caption1", c1);
        values.put("Caption2", c2);
        values.put("Caption3", c3);
        values.put("Caption4", c4);
        values.put("Caption5", c5);

        database.insert(PROJECT_TABLE_GALLERY, null, values);
        database.close();
        return true;
    }


    public boolean updateGallery(int uid,int sid, String shelter, String c1,String c2,String c3,String c4,String c5,
                                 String img1,String img2,String img3,String img4,String img5) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("userid", uid);
        values.put("subid", sid);
        values.put("shelter", shelter);
        values.put("Image1", img1);
        values.put("Image2", img2);
        values.put("Image3", img3);
        values.put("Image4", img4);
        values.put("Image5", img5);
        values.put("Caption1", c1);
        values.put("Caption2", c2);
        values.put("Caption3", c3);
        values.put("Caption4", c4);
        values.put("Caption5", c5);

        database.update(PROJECT_TABLE_GALLERY, values, "subid = ? ", new String[] { Integer.toString(sid) } );
        database.close();
        return true;
    }

    /////// Get data with id ///////////
    public Cursor getDataGallery(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_GALLERY + " where subid="+id+"", null );
        return res;
    }

    public Cursor getDataDetailsGallery(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_GALLERY + " where subid="+id+"", null );
        return res;
    }


    public ArrayList<String> getAllLikeGallery(int userid) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from Projects", null );
        Cursor res = db.rawQuery("SELECT * FROM " + PROJECT_TABLE_GALLERY + " WHERE userid = '" + userid + "'", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex("shelter")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllGallery() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from Projects", null );
        Cursor res = db.rawQuery("SELECT * FROM " + PROJECT_TABLE_GALLERY, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex("shelter")));
            res.moveToNext();
        }
        return array_list;
    }

    /////// Count Total Data ///////////
    public int numberOfRowsGallery(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROJECT_TABLE_GALLERY);
        return numRows;
    }
    /////// Delete one Data ///////////
    public Integer deleteGallery (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PROJECT_TABLE_GALLERY,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    /////// Delete All Data ///////////
    public boolean deleteAllGallery () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROJECT_TABLE_GALLERY, null, null);
        return true;
    }


    //////////////////////////// Resource ////////////////////////////////////
    public boolean insertResourceForm(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("userid", queryValues.get("userid"));
        values.put("shelter", queryValues.get("shelter"));
        values.put("materials", queryValues.get("materials"));
        values.put("SendServer", 0);

        database.insert(PROJECT_TABLE_RESOURCE, null, values);
        database.close();
        return true;
    }

    public int getLastResourceId() {
        int id = 0;
        String sql = "SELECT * FROM " + PROJECT_TABLE_RESOURCE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        if(res.moveToLast()){
            id = res.getInt(0);//to get id, 0 is the column index
        }
        //res.close();
        return id;
    }
    public int totalUserResources(int uid , int id){
        String sql = "SELECT * FROM " + PROJECT_TABLE_RESOURCE + " WHERE userid = '"+ uid +"' AND SendServer = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        res.moveToLast();
        if(res.getCount() > 0) {
            int totalid = res.getCount();
            return totalid;
        }
        else{
            return 0;
        }
    }
    public boolean insertResourceGallery(int uid,int sid, String c1,String c2,String c3,String c4,String c5,
                                         String img1,String img2,String img3,String img4,String img5) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("userid", uid);
        values.put("resid", sid);
        values.put("Image1", img1);
        values.put("Image2", img2);
        values.put("Image3", img3);
        values.put("Image4", img4);
        values.put("Image5", img5);
        values.put("Caption1", c1);
        values.put("Caption2", c2);
        values.put("Caption3", c3);
        values.put("Caption4", c4);
        values.put("Caption5", c5);

        database.insert(RESOURCE_TABLE_GALLERY, null, values);
        database.close();
        return true;
    }


    public boolean updateResourceGallery(int uid,int sid, String c1,String img1) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("userid", uid);
        values.put("resid", sid);
        values.put("Image1", img1);
        values.put("Caption1", c1);

        database.update(RESOURCE_TABLE_GALLERY, values, "subid = ? ", new String[] { Integer.toString(sid) } );
        database.close();
        return true;
    }

    public Cursor getAllResources(int status,int userid) {
        String sql = "SELECT * FROM " + PROJECT_TABLE_RESOURCE + " WHERE userid = '" + userid + "' AND SendServer = '" + status + "' ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    /////// Get data with id ///////////
    public Cursor getResourceData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PROJECT_TABLE_RESOURCE + " where id="+id+"", null );
        //res.close();
        return res;
    }

    /////// Get data with id ///////////
    public Cursor getResourceGallery(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + RESOURCE_TABLE_GALLERY + " where resid="+id+"", null );
        return res;
    }

    /////// Update Data ///////////
    public boolean updateResourceForm (int id,int refid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SendServer", 1);
        contentValues.put("refid", refid);
        db.update(PROJECT_TABLE_RESOURCE, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    /////// Delete All Data ///////////
    public boolean deleteAllResources () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROJECT_TABLE_RESOURCE, null, null);
        return true;
    }

    public boolean deleteAllResourcesGallery () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RESOURCE_TABLE_GALLERY, null, null);
        return true;
    }

}