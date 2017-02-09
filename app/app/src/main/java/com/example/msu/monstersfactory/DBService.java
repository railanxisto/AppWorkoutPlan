package com.example.msu.monstersfactory;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Railan on 5/5/2015.
 */
public class DBService extends SQLiteOpenHelper{

    private String DBPATH = "";
    private String DBNAME ="MonsterFactory.db";

    Context context;

    @Override
    public void onCreate(SQLiteDatabase db){
        String teste="";
        try {
            teste = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        DBPATH = teste + "/databases/";
        System.out.println("Aquuuui: " + teste);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DBService(Context context){
        super(context, "MonsterFactory.db", null, 1);
        this.context = context;
    }

    private boolean checkDataBase(){

        SQLiteDatabase db = null;
        String teste="";
        try {
            teste = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        DBPATH = teste + "/databases/";
        System.out.println("Aquuuui: " + teste);
        try {
            String path = DBPATH + DBNAME;
            db =
                    SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            db.close();
        } catch (SQLiteException e) {
            // O banco não existe
        }

        // Retorna verdadeiro se o banco existir, pois o ponteiro irá existir,
        // se não houver referencia é porque o banco não existe
        return db != null;
    }

    private void createDataBase()
            throws Exception {

        // Primeiro temos que verificar se o banco da aplicação
        // já foi criado
        boolean exists = checkDataBase();

        if(!exists) {
            // Chamaremos esse método para que o android
            // crie um banco vazio e o diretório onde iremos copiar
            // no banco que está no assets.
            this.getReadableDatabase();

            // Se o banco de dados não existir iremos copiar o nosso
            // arquivo em /assets para o local onde o android os salva
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Não foi possível copiar o arquivo");
            }

        }
    }

    private void copyDatabase()
            throws IOException {
        String teste="";
        try {
            teste = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        DBPATH = teste + "/databases/";
        System.out.println("Aquuuui: " + teste);
        String dbPath = DBPATH + DBNAME;

        // Abre o arquivo o destino para copiar o banco de dados
        OutputStream dbStream = new FileOutputStream(dbPath);

        // Abre Stream do nosso arquivo que esta no assets
        InputStream dbInputStream =
                context.getAssets().open("MonsterFactory.db");

        byte[] buffer = new byte[1024];
        int length;
        while((length = dbInputStream.read(buffer)) > 0) {
            dbStream.write(buffer, 0, length);
        }

        dbInputStream.close();

        dbStream.flush();
        dbStream.close();

    }

    public SQLiteDatabase getDatabase() {
        try{
            // Verificando se o banco já foi criado e se não foi o
            // mesmo é criado.
            createDataBase();

            // Abrindo database
            String teste="";
            try {
                teste = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            DBPATH = teste + "/databases/";
            System.out.println("Aquuuui: " + teste);
            String path = DBPATH + DBNAME;

            return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }catch (Exception e) {
            // Se não conseguir copiar o banco um novo será retornado
            return getWritableDatabase();
        }

    }


//        public void addContact(Contact contact){
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues initialValues = new ContentValues();
//            initialValues.put(KEY_NAME, contact.getName());
//            initialValues.put(KEY_EMAIL, contact.getEmail());
//            initialValues.put(KEY_PHONE, contact.getCellPhone());
//            db.insert(TABLE, null, initialValues);
//            db.close();
//        }
//        public int updateContact(Contact contact){
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues args = new ContentValues();
//            args.put(KEY_NAME, contact.getName());
//            args.put(KEY_EMAIL, contact.getEmail());
//            args.put(KEY_PHONE, contact.getCellPhone());
//            return db.update(TABLE, args, KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
//        }
//
//        public int deleteContact(Contact contact){
//            SQLiteDatabase db = this.getWritableDatabase();
//            int result = db.delete(TABLE, KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
//            return result;
//        }
//
//        public ArrayList<Contact> getAllContacts(){
//            ArrayList<Contact> contacts = new ArrayList<Contact>();
//            String query = "select * from "+TABLE;
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(query, null);
//            if(cursor.moveToFirst()){
//                do{
//                    Contact contact = new Contact();
//                    //contact.setId(Integer.parseInt(cursor.getString(0)));
//                    contact.setId(cursor.getInt(0));
//                    contact.setName(cursor.getString(1));
//                    contact.setCellPhone(cursor.getString(2));
//                    contact.setEmail(cursor.getString(3));
//                    contacts.add(contact);
//                }while(cursor.moveToNext());
//            }
//            return contacts;
//        }


}







//    private final static int DATABASE_VERSION = 1;
//    private final static String DATABASE_NAME = "testejaja.db";
//    private final static String TABLE = "contacts";
//
//    private static final String KEY_ID = "id";
//    private static final String KEY_NAME = "name";
//    private static final String KEY_EMAIL = "email";
//    private static final String KEY_PHONE = "phone";
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db)
//    {
//        System.out.println("buceta");
//        String sql = "CREATE TABLE " + TABLE + "("
//                + KEY_ID + " integer primary key autoincrement, "
//                + KEY_NAME +  " VARCHAR(50), "
//                + KEY_PHONE + " VARCHAR(20), "
//                + KEY_EMAIL + " VARCHAR(20));";
//        db.execSQL(sql);
//
//    }

//    private final static int DATABASE_VERSION = 1;
//    private final static String DATABASE_NAME = "MonsterFactory.db";
//
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db)
//    {
//        System.out.println("Aquiiiiiiiiiiiiiiiii");
//        String sql1 = "CREATE TABLE Categorie (idCategorie integer primary key, nameCategorie VARCHAR(50));\n" +
//                "CREATE TABLE Level (idLevel integer primary key, nameLevel VARCHAR(50));\n" +
//                "CREATE TABLE MuscularGroup (idMuscularGroup integer primary key, nameMuscularGroup VARCHAR(50));\n" +
//                "CREATE TABLE User (idUser integer primary key, nameUser VARCHAR(100), idCategorie integer, age integer, height real, weight real, BMI real );\n" +
//                "CREATE TABLE WorkoutPlan (idWorkoutPlan integer primary key, nameWorkoutPlan VARCHAR(100), exercise1 integer, exercise2 integer, exercise3 integer, exercise4 integer, exercise5 integer, exercise6 integer, exercise7 integer );\n" +
//                "CREATE TABLE Exercise (id integer primary key, nameExercise VARCHAR(100), frequency VARCHAR(100), Categorie_idCategorie integer, Level_idLevel integer, MuscularGroup_idMuscularGroup integer, image integer);\n";
////                "insert into Categorie (idCategorie, nameCategorie) values (1, 'Health and Welfare');\n" +
//                "insert into Categorie (idCategorie, nameCategorie) values (2, 'Loose Weight and Fat');\n" +
//                "insert into Categorie (idCategorie, nameCategorie) values (3, 'Hypertrophy');\n" +
//                "insert into Categorie (idCategorie, nameCategorie) values (4, 'Resistence');\n" +
//                "insert into Categorie (idCategorie, nameCategorie) values (5, 'Flexibility');\n" +
//                "\n" +
//                "insert into Level (idLevel, nameLevel) values (1, 'Beginner');\n" +
//                "insert into Level (idLevel, nameLevel) values (2, 'Intermediate');\n" +
//                "insert into Level (idLevel, nameLevel) values (3, 'Professional');\n" +
//                "\n" +
//                "insert into MuscularGroup (idMuscularGroup, nameMuscularGroup) values (1, 'Triceps');\n" +
//                "insert into MuscularGroup (idMuscularGroup, nameMuscularGroup) values (2, 'Shoulders');\n" +
//                "insert into MuscularGroup (idMuscularGroup, nameMuscularGroup) values (3, 'Biceps');\n" +
//                "insert into MuscularGroup (idMuscularGroup, nameMuscularGroup) values (4, 'Legs');\n" +
//                "insert into MuscularGroup (idMuscularGroup, nameMuscularGroup) values (5, 'Chest');\n" +
//                "insert into MuscularGroup (idMuscularGroup, nameMuscularGroup) values (6, 'Back');\n" +
//                "insert into MuscularGroup (idMuscularGroup, nameMuscularGroup) values (7, 'Abs');\n";
//            String sql2 =
//                "--Chest\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (1, 'BARBELL BENCH PRESS', '3x12 - 1 Day per Week', 1, 1, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (2, 'BARBELL BENCH PRESS', '3x10 - 2 Day per Week', 1, 2, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (3, 'BARBELL BENCH PRESS', '3x8 - 1 Day per Week', 1, 3, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (4, 'BARBELL BENCH PRESS', '3x12 - 2 Day per Week', 2, 1, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (5, 'BARBELL BENCH PRESS', '3x10 - 2 Day per Week', 2, 2, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (6, 'BARBELL BENCH PRESS', '3x8 - 2 Day per Week', 2, 3, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (7, 'BARBELL BENCH PRESS', '3x12 - 2 Day per Week', 3, 1, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (8, 'BARBELL BENCH PRESS', '3x10 - 2 Day per Week', 3, 2, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (9, 'BARBELL BENCH PRESS', '3x8 - 2 Day per Week', 3, 3, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (10, 'BARBELL BENCH PRESS', '3x12 - 2 Day per Week', 4, 1, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (11, 'BARBELL BENCH PRESS', '3x12 - 2 Day per Week', 4, 2, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (12, 'BARBELL BENCH PRESS', '4x10 - 2 Day per Week', 4, 3, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (13, 'BARBELL BENCH PRESS', '3x12 - 2 Day per Week', 5, 1, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (14, 'BARBELL BENCH PRESS', '3x12 - 2 Day per Week', 5, 2, 5, 1);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (15, 'BARBELL BENCH PRESS', '3x10 - 2 Day per Week', 5, 3, 5, 1);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (16, 'BUTTERFLY', '3x12 - 1 Day per Week', 1, 1, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (17, 'BUTTERFLY', '3x10 - 1 Day per Week', 1, 2, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (18, 'BUTTERFLY', '3x8 - 1 Day per Week', 1, 3, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (19, 'BUTTERFLY', '3x12 - 2 Day per Week', 2, 1, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (20, 'BUTTERFLY', '3x10 - 2 Day per Week', 2, 2, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (21, 'BUTTERFLY', '3x8 - 2 Day per Week', 2, 3, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (22, 'BUTTERFLY', '3x12 - 2 Day per Week', 3, 1, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (23, 'BUTTERFLY', '3x10 - 2 Day per Week', 3, 2, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (24, 'BUTTERFLY', '3x8 - 2 Day per Week', 3, 3, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (25, 'BUTTERFLY', '3x12 - 2 Day per Week', 4, 1, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (26, 'BUTTERFLY', '3x12 - 2 Day per Week', 4, 2, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (27, 'BUTTERFLY', '4x10 - 2 Day per Week', 4, 3, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (28, 'BUTTERFLY', '3x12 - 1 Day per Week', 5, 1, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (29, 'BUTTERFLY', '3x12 - 1 Day per Week', 5, 2, 5, 2);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (30, 'BUTTERFLY', '3x10 - 1 Day per Week', 5, 3, 5, 2);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (31, 'INCLINE DUMBBELL FLYES', '3x12 - 1 Day per Week', 1, 1, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (32, 'INCLINE DUMBBELL FLYES', '3x10 - 1 Day per Week', 1, 2, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (33, 'INCLINE DUMBBELL FLYES', '3x8 - 1 Day per Week', 1, 3, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (34, 'INCLINE DUMBBELL FLYES', '3x12 - 2 Day per Week', 2, 1, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (35, 'INCLINE DUMBBELL FLYES', '3x10 - 2 Day per Week', 2, 2, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (36, 'INCLINE DUMBBELL FLYES', '3x8 - 2 Day per Week', 2, 3, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (37, 'INCLINE DUMBBELL FLYES', '3x12 - 2 Day per Week', 3, 1, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (38, 'INCLINE DUMBBELL FLYES', '3x10 - 2 Day per Week', 3, 2, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (39, 'INCLINE DUMBBELL FLYES', '3x8 - 2 Day per Week', 3, 3, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (40, 'INCLINE DUMBBELL FLYES', '3x12 - 2 Day per Week', 4, 1, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (41, 'INCLINE DUMBBELL FLYES', '3x12 - 2 Day per Week', 4, 2, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (42, 'INCLINE DUMBBELL FLYES', '4x10 - 2 Day per Week', 4, 3, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (43, 'INCLINE DUMBBELL FLYES', '3x12 - 1 Day per Week', 5, 1, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (44, 'INCLINE DUMBBELL FLYES', '3x12 - 1 Day per Week', 5, 2, 5, 3);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (45, 'INCLINE DUMBBELL FLYES', '3x10 - 1 Day per Week', 5, 3, 5, 3);\n";
//        String sql3 =
//                "--Triceps\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (46, 'BENCH DIPS', '3x12 - 1 Day per Week', 1, 1, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (47, 'BENCH DIPS', '3x10 - 1 Day per Week', 1, 2, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (48, 'BENCH DIPS', '3x8 - 1 Day per Week', 1, 3, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (49, 'BENCH DIPS', '3x12 - 2 Day per Week', 2, 1, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (50, 'BENCH DIPS', '3x10 - 2 Day per Week', 2, 2, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (51, 'BENCH DIPS', '3x8 - 2 Day per Week', 2, 3, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (52, 'BENCH DIPS', '3x12 - 2 Day per Week', 3, 1, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (53, 'BENCH DIPS', '3x10 - 2 Day per Week', 3, 2, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (54, 'BENCH DIPS', '3x8 - 2 Day per Week', 3, 3, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (55, 'BENCH DIPS', '3x12 - 2 Day per Week', 4, 1, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (56, 'BENCH DIPS', '3x12 - 2 Day per Week', 4, 2, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (57, 'BENCH DIPS', '4x10 - 2 Day per Week', 4, 3, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (58, 'BENCH DIPS', '3x12 - 1 Day per Week', 5, 1, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (59, 'BENCH DIPS', '3x12 - 1 Day per Week', 5, 2, 1, 4);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (60, 'BENCH DIPS', '3x10 - 1 Day per Week', 5, 3, 1, 4);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (61, 'SEATED TRICEPS PRESS', '3x12 - 1 Day per Week', 1, 1, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (62, 'SEATED TRICEPS PRESS', '3x10 - 1 Day per Week', 1, 2, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (63, 'SEATED TRICEPS PRESS', '3x8 - 1 Day per Week', 1, 3, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (64, 'SEATED TRICEPS PRESS', '3x12 - 2 Day per Week', 2, 1, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (65, 'SEATED TRICEPS PRESS', '3x10 - 2 Day per Week', 2, 2, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (66, 'SEATED TRICEPS PRESS', '3x8 - 2 Day per Week', 2, 3, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (67, 'SEATED TRICEPS PRESS', '3x12 - 2 Day per Week', 3, 1, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (68, 'SEATED TRICEPS PRESS', '3x10 - 2 Day per Week', 3, 2, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (69, 'SEATED TRICEPS PRESS', '3x8 - 2 Day per Week', 3, 3, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (70, 'SEATED TRICEPS PRESS', '3x12 - 2 Day per Week', 4, 1, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (71, 'SEATED TRICEPS PRESS', '3x12 - 2 Day per Week', 4, 2, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (72, 'SEATED TRICEPS PRESS', '4x10 - 2 Day per Week', 4, 3, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (73, 'SEATED TRICEPS PRESS', '3x12 - 1 Day per Week', 5, 1, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (74, 'SEATED TRICEPS PRESS', '3x12 - 1 Day per Week', 5, 2, 1, 5);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (75, 'SEATED TRICEPS PRESS', '3x10 - 1 Day per Week', 5, 3, 1, 5);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (76, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x12 - 1 Day per Week', 1, 1, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (77, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x10 - 1 Day per Week', 1, 2, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (78, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x8 - 1 Day per Week', 1, 3, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (79, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x12 - 2 Day per Week', 2, 1, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (80, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x10 - 2 Day per Week', 2, 2, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (81, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x8 - 2 Day per Week', 2, 3, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (82, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x12 - 2 Day per Week', 3, 1, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (83, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x10 - 2 Day per Week', 3, 2, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (84, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x8 - 2 Day per Week', 3, 3, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (85, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x12 - 2 Day per Week', 4, 1, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (86, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x12 - 2 Day per Week', 4, 2, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (87, 'REVERSE GRIP TRICEPS PUSHDOWN', '4x10 - 2 Day per Week', 4, 3, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (88, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x12 - 1 Day per Week', 5, 1, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (89, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x12 - 1 Day per Week', 5, 2, 1, 6);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (90, 'REVERSE GRIP TRICEPS PUSHDOWN', '3x10 - 1 Day per Week', 5, 3, 1, 6);\n";
//        String sql4 =
//                "--Shoulders\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (91, 'ALTERNATING DELTOID RAISE', '3x12 - 1 Day per Week', 1, 1, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (92, 'ALTERNATING DELTOID RAISE', '3x10 - 1 Day per Week', 1, 2, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (93, 'ALTERNATING DELTOID RAISE', '3x8 - 1 Day per Week', 1, 3, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (94, 'ALTERNATING DELTOID RAISE', '3x12 - 2 Day per Week', 2, 1, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (95, 'ALTERNATING DELTOID RAISE', '3x10 - 2 Day per Week', 2, 2, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (96, 'ALTERNATING DELTOID RAISE', '3x8 - 2 Day per Week', 2, 3, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (97, 'ALTERNATING DELTOID RAISE', '3x12 - 2 Day per Week', 3, 1, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (98, 'ALTERNATING DELTOID RAISE', '3x10 - 2 Day per Week', 3, 2, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (99, 'ALTERNATING DELTOID RAISE', '3x8 - 2 Day per Week', 3, 3, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (100, 'ALTERNATING DELTOID RAISE', '3x12 - 2 Day per Week', 4, 1, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (101, 'ALTERNATING DELTOID RAISE', '3x12 - 2 Day per Week', 4, 2, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (102, 'ALTERNATING DELTOID RAISE', '4x10 - 2 Day per Week', 4, 3, 2, 7);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (244, 'BAND PULL APART', '3x12 - 2 Day per Week', 5, 1, 2, 20);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (245, 'BAND PULL APART', '3x12 - 2 Day per Week', 5, 2, 2, 20);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (246, 'BAND PULL APART', '3x10 - 2 Day per Week', 5, 3, 2, 20);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (106, 'ARNOLD DUMBBELL PRESS', '3x12 - 1 Day per Week', 1, 1, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (107, 'ARNOLD DUMBBELL PRESS', '3x10 - 1 Day per Week', 1, 2, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (108, 'ARNOLD DUMBBELL PRESS', '3x8 - 1 Day per Week', 1, 3, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (109, 'ARNOLD DUMBBELL PRESS', '3x12 - 2 Day per Week', 2, 1, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (110, 'ARNOLD DUMBBELL PRESS', '3x10 - 2 Day per Week', 2, 2, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (111, 'ARNOLD DUMBBELL PRESS', '3x8 - 2 Day per Week', 2, 3, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (112, 'ARNOLD DUMBBELL PRESS', '3x12 - 2 Day per Week', 3, 1, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (113, 'ARNOLD DUMBBELL PRESS', '3x10 - 2 Day per Week', 3, 2, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (114, 'ARNOLD DUMBBELL PRESS', '3x8 - 2 Day per Week', 3, 3, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (115, 'ARNOLD DUMBBELL PRESS', '3x12 - 2 Day per Week', 4, 1, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (116, 'ARNOLD DUMBBELL PRESS', '3x12 - 2 Day per Week', 4, 2, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (117, 'ARNOLD DUMBBELL PRESS', '4x10 - 2 Day per Week', 4, 3, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (118, 'ARNOLD DUMBBELL PRESS', '3x12 - 1 Day per Week', 5, 1, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (119, 'ARNOLD DUMBBELL PRESS', '3x12 - 1 Day per Week', 5, 2, 2, 8);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (120, 'ARNOLD DUMBBELL PRESS', '3x10 - 1 Day per Week', 5, 3, 2, 8);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (121, 'BARBELL SHOULDER PRESS', '3x12 - 1 Day per Week', 1, 1, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (122, 'BARBELL SHOULDER PRESS', '3x10 - 1 Day per Week', 1, 2, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (123, 'BARBELL SHOULDER PRESS', '3x8 - 1 Day per Week', 1, 3, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (124, 'BARBELL SHOULDER PRESS', '3x12 - 2 Day per Week', 2, 1, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (125, 'BARBELL SHOULDER PRESS', '3x10 - 2 Day per Week', 2, 2, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (126, 'BARBELL SHOULDER PRESS', '3x8 - 2 Day per Week', 2, 3, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (127, 'BARBELL SHOULDER PRESS', '3x12 - 2 Day per Week', 3, 1, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (128, 'BARBELL SHOULDER PRESS', '3x10 - 2 Day per Week', 3, 2, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (129, 'BARBELL SHOULDER PRESS', '3x8 - 2 Day per Week', 3, 3, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (130, 'BARBELL SHOULDER PRESS', '3x12 - 2 Day per Week', 4, 1, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (131, 'BARBELL SHOULDER PRESS', '3x12 - 2 Day per Week', 4, 2, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (132, 'BARBELL SHOULDER PRESS', '4x10 - 2 Day per Week', 4, 3, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (133, 'BARBELL SHOULDER PRESS', '3x12 - 1 Day per Week', 5, 1, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (134, 'BARBELL SHOULDER PRESS', '3x12 - 1 Day per Week', 5, 2, 2, 9);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (135, 'BARBELL SHOULDER PRESS', '3x10 - 1 Day per Week', 5, 3, 2, 9);\n";
//        String sql5 =
//                "--Biceps\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (136, 'BARBELL CURL', '3x12 - 1 Day per Week', 1, 1, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (137, 'BARBELL CURL', '3x10 - 1 Day per Week', 1, 2, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (138, 'BARBELL CURL', '3x8 - 1 Day per Week', 1, 3, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (139, 'BARBELL CURL', '3x12 - 2 Day per Week', 2, 1, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (140, 'BARBELL CURL', '3x10 - 2 Day per Week', 2, 2, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (141, 'BARBELL CURL', '3x8 - 2 Day per Week', 2, 3, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (142, 'BARBELL CURL', '3x12 - 2 Day per Week', 3, 1, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (143, 'BARBELL CURL', '3x10 - 2 Day per Week', 3, 2, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (144, 'BARBELL CURL', '3x8 - 2 Day per Week', 3, 3, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (145, 'BARBELL CURL', '3x12 - 2 Day per Week', 4, 1, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (146, 'BARBELL CURL', '3x12 - 2 Day per Week', 4, 2, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (147, 'BARBELL CURL', '4x10 - 2 Day per Week', 4, 3, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (148, 'BARBELL CURL', '3x12 - 1 Day per Week', 5, 1, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (149, 'BARBELL CURL', '3x12 - 1 Day per Week', 5, 2, 3, 10);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (150, 'BARBELL CURL', '3x10 - 1 Day per Week', 5, 3, 3, 10);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (151, 'CABLE PREACHER CURL', '3x12 - 1 Day per Week', 1, 1, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (152, 'CABLE PREACHER CURL', '3x10 - 1 Day per Week', 1, 2, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (153, 'CABLE PREACHER CURL', '3x8 - 1 Day per Week', 1, 3, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (154, 'CABLE PREACHER CURL', '3x12 - 2 Day per Week', 2, 1, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (155, 'CABLE PREACHER CURL', '3x10 - 2 Day per Week', 2, 2, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (156, 'CABLE PREACHER CURL', '3x8 - 2 Day per Week', 2, 3, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (157, 'CABLE PREACHER CURL', '3x12 - 2 Day per Week', 3, 1, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (158, 'CABLE PREACHER CURL', '3x10 - 2 Day per Week', 3, 2, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (159, 'CABLE PREACHER CURL', '3x8 - 2 Day per Week', 3, 3, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (160, 'CABLE PREACHER CURL', '3x12 - 2 Day per Week', 4, 1, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (161, 'CABLE PREACHER CURL', '3x12 - 2 Day per Week', 4, 2, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (162, 'CABLE PREACHER CURL', '4x10 - 2 Day per Week', 4, 3, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (163, 'CABLE PREACHER CURL', '3x12 - 1 Day per Week', 5, 1, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (164, 'CABLE PREACHER CURL', '3x12 - 1 Day per Week', 5, 2, 3, 11);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (165, 'CABLE PREACHER CURL', '3x10 - 1 Day per Week', 5, 3, 3, 11);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (166, 'CONCENTRATION CURLS', '3x12 - 1 Day per Week', 1, 1, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (167, 'CONCENTRATION CURLS', '3x10 - 1 Day per Week', 1, 2, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (168, 'CONCENTRATION CURLS', '3x8 - 1 Day per Week', 1, 3, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (169, 'CONCENTRATION CURLS', '3x12 - 2 Day per Week', 2, 1, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (170, 'CONCENTRATION CURLS', '3x10 - 2 Day per Week', 2, 2, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (171, 'CONCENTRATION CURLS', '3x8 - 2 Day per Week', 2, 3, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (172, 'CONCENTRATION CURLS', '3x12 - 2 Day per Week', 3, 1, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (173, 'CONCENTRATION CURLS', '3x10 - 2 Day per Week', 3, 2, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (174, 'CONCENTRATION CURLS', '3x8 - 2 Day per Week', 3, 3, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (175, 'CONCENTRATION CURLS', '3x12 - 2 Day per Week', 4, 1, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (176, 'CONCENTRATION CURLS', '3x12 - 2 Day per Week', 4, 2, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (177, 'CONCENTRATION CURLS', '4x10 - 2 Day per Week', 4, 3, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (178, 'CONCENTRATION CURLS', '3x12 - 1 Day per Week', 5, 1, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (179, 'CONCENTRATION CURLS', '3x12 - 1 Day per Week', 5, 2, 3, 12);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (180, 'CONCENTRATION CURLS', '3x10 - 1 Day per Week', 5, 3, 3, 12);\n";
//        String sql6 =
//                "--Legs\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (181, 'BARBELL FULL SQUAT', '3x12 - 1 Day per Week', 1, 1, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (182, 'BARBELL FULL SQUAT', '3x10 - 1 Day per Week', 1, 2, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (183, 'BARBELL FULL SQUAT', '3x8 - 1 Day per Week', 1, 3, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (184, 'BARBELL FULL SQUAT', '3x12 - 2 Day per Week', 2, 1, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (185, 'BARBELL FULL SQUAT', '3x10 - 2 Day per Week', 2, 2, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (186, 'BARBELL FULL SQUAT', '3x8 - 2 Day per Week', 2, 3, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (187, 'BARBELL FULL SQUAT', '3x12 - 2 Day per Week', 3, 1, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (188, 'BARBELL FULL SQUAT', '3x10 - 2 Day per Week', 3, 2, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (189, 'BARBELL FULL SQUAT', '3x8 - 2 Day per Week', 3, 3, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (190, 'BARBELL FULL SQUAT', '3x12 - 2 Day per Week', 4, 1, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (191, 'BARBELL FULL SQUAT', '3x12 - 2 Day per Week', 4, 2, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (192, 'BARBELL FULL SQUAT', '4x10 - 2 Day per Week', 4, 3, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (193, 'BARBELL FULL SQUAT', '3x12 - 1 Day per Week', 5, 1, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (194, 'BARBELL FULL SQUAT', '3x12 - 1 Day per Week', 5, 2, 4, 13);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (195, 'BARBELL FULL SQUAT', '3x10 - 1 Day per Week', 5, 3, 4, 13);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (196, 'LEG PRESS', '3x12 - 1 Day per Week', 1, 1, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (197, 'LEG PRESS', '3x10 - 1 Day per Week', 1, 2, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (198, 'LEG PRESS', '3x8 - 1 Day per Week', 1, 3, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (199, 'LEG PRESS', '3x12 - 2 Day per Week', 2, 1, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (200, 'LEG PRESS', '3x10 - 2 Day per Week', 2, 2, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (201, 'LEG PRESS', '3x8 - 2 Day per Week', 2, 3, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (202, 'LEG PRESS', '3x12 - 2 Day per Week', 3, 1, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (203, 'LEG PRESS', '3x10 - 2 Day per Week', 3, 2, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (204, 'LEG PRESS', '3x8 - 2 Day per Week', 3, 3, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (205, 'LEG PRESS', '3x12 - 2 Day per Week', 4, 1, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (206, 'LEG PRESS', '3x12 - 2 Day per Week', 4, 2, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (207, 'LEG PRESS', '4x10 - 2 Day per Week', 4, 3, 4, 14);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (241, 'ILIOTIBIAL TRACT-SMR', '3x12 - 2 Day per Week', 5, 1, 4, 18);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (242, 'ILIOTIBIAL TRACT-SMR', '3x12 - 2 Day per Week', 5, 2, 4, 18);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (243, 'ILIOTIBIAL TRACT-SMR', '3x10 - 2 Day per Week', 5, 3, 4, 18);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (211, 'BARBELL LUNGE', '3x12 - 1 Day per Week', 1, 1, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (212, 'BARBELL LUNGE', '3x10 - 1 Day per Week', 1, 2, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (213, 'BARBELL LUNGE', '3x8 - 1 Day per Week', 1, 3, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (214, 'BARBELL LUNGE', '3x12 - 2 Day per Week', 2, 1, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (215, 'BARBELL LUNGE', '3x10 - 2 Day per Week', 2, 2, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (216, 'BARBELL LUNGE', '3x8 - 2 Day per Week', 2, 3, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (217, 'BARBELL LUNGE', '3x12 - 2 Day per Week', 3, 1, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (218, 'BARBELL LUNGE', '3x10 - 2 Day per Week', 3, 2, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (219, 'BARBELL LUNGE', '3x8 - 2 Day per Week', 3, 3, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (220, 'BARBELL LUNGE', '3x12 - 2 Day per Week', 4, 1, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (221, 'BARBELL LUNGE', '3x12 - 2 Day per Week', 4, 2, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (222, 'BARBELL LUNGE', '4x10 - 2 Day per Week', 4, 3, 4, 15);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (238, 'HIP CIRCLES (PRONE)', '3x12 - 2 Day per Week', 5, 1, 4, 17);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (239, 'HIP CIRCLES (PRONE)', '3x12 - 2 Day per Week', 5, 2, 4, 17);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (240, 'HIP CIRCLES (PRONE)', '3x10 - 2 Day per Week', 5, 3, 4, 17);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (226, 'BARBELL SEATED CALF RAISE', '3x12 - 1 Day per Week', 1, 1, 4, 16);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (227, 'BARBELL SEATED CALF RAISE', '3x10 - 1 Day per Week', 1, 2, 4, 16);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (228, 'BARBELL SEATED CALF RAISE', '3x8 - 1 Day per Week', 1, 3, 4, 16);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (229, 'BARBELL SEATED CALF RAISE', '3x12 - 2 Day per Week', 2, 1, 4, 16);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (230, 'BARBELL SEATED CALF RAISE', '3x10 - 2 Day per Week', 2, 2, 4, 16);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (231, 'BARBELL SEATED CALF RAISE', '3x8 - 2 Day per Week', 2, 3, 4, 16);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (232, 'BARBELL SEATED CALF RAISE', '3x12 - 2 Day per Week', 3, 1, 4, 16);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (233, 'BARBELL SEATED CALF RAISE', '3x10 - 2 Day per Week', 3, 2, 4, 16);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (234, 'BARBELL SEATED CALF RAISE', '3x8 - 2 Day per Week', 3, 3, 4, 16);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (235, 'BARBELL SEATED CALF RAISE', '3x12 - 2 Day per Week', 4, 1, 4, 16);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (236, 'BARBELL SEATED CALF RAISE', '3x12 - 2 Day per Week', 4, 2, 4, 16);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (237, 'BARBELL SEATED CALF RAISE', '4x10 - 2 Day per Week', 4, 3, 4, 16);\n";
//        String sql7 =
//                "--Back\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (247, 'BENT OVER BARBELL ROW', '3x12 - 1 Day per Week', 1, 1, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (248, 'BENT OVER BARBELL ROW', '3x10 - 1 Day per Week', 1, 2, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (249, 'BENT OVER BARBELL ROW', '3x8 - 1 Day per Week', 1, 3, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (250, 'BENT OVER BARBELL ROW', '3x12 - 2 Day per Week', 2, 1, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (251, 'BENT OVER BARBELL ROW', '3x10 - 2 Day per Week', 2, 2, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (252, 'BENT OVER BARBELL ROW', '3x8 - 2 Day per Week', 2, 3, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (253, 'BENT OVER BARBELL ROW', '3x12 - 2 Day per Week', 3, 1, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (254, 'BENT OVER BARBELL ROW', '3x10 - 2 Day per Week', 3, 2, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (255, 'BENT OVER BARBELL ROW', '3x8 - 2 Day per Week', 3, 3, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (256, 'BENT OVER BARBELL ROW', '3x12 - 2 Day per Week', 4, 1, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (257, 'BENT OVER BARBELL ROW', '3x12 - 2 Day per Week', 4, 2, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (258, 'BENT OVER BARBELL ROW', '4x10 - 2 Day per Week', 4, 3, 6, 21);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (259, 'MIXED GRIP CHIN', '3x12 - 2 Day per Week', 5, 1, 6, 22);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (260, 'MIXED GRIP CHIN', '3x12 - 2 Day per Week', 5, 2, 6, 22);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (261, 'MIXED GRIP CHIN', '3x10 - 2 Day per Week', 5, 3, 6, 22);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (262, 'LYING T-BAR ROW', '3x12 - 1 Day per Week', 1, 1, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (263, 'LYING T-BAR ROW', '3x10 - 1 Day per Week', 1, 2, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (264, 'LYING T-BAR ROW', '3x8 - 1 Day per Week', 1, 3, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (265, 'LYING T-BAR ROW', '3x12 - 2 Day per Week', 2, 1, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (266, 'LYING T-BAR ROW', '3x10 - 2 Day per Week', 2, 2, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (267, 'LYING T-BAR ROW', '3x8 - 2 Day per Week', 2, 3, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (268, 'LYING T-BAR ROW', '3x12 - 2 Day per Week', 3, 1, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (269, 'LYING T-BAR ROW', '3x10 - 2 Day per Week', 3, 2, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (270, 'LYING T-BAR ROW', '3x8 - 2 Day per Week', 3, 3, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (271, 'LYING T-BAR ROW', '3x12 - 2 Day per Week', 4, 1, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (272, 'LYING T-BAR ROW', '3x12 - 2 Day per Week', 4, 2, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (273, 'LYING T-BAR ROW', '4x10 - 2 Day per Week', 4, 3, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (274, 'LYING T-BAR ROW', '3x12 - 1 Day per Week', 5, 1, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (275, 'LYING T-BAR ROW', '3x12 - 1 Day per Week', 5, 2, 6, 23);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (276, 'LYING T-BAR ROW', '3x10 - 1 Day per Week', 5, 3, 6, 23);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (277, 'LYING T-BAR ROW', '3x12 - 1 Day per Week', 1, 1, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (278, 'LYING T-BAR ROW', '3x10 - 1 Day per Week', 1, 2, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (279, 'LYING T-BAR ROW', '3x8 - 1 Day per Week', 1, 3, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (280, 'LYING T-BAR ROW', '3x12 - 2 Day per Week', 2, 1, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (281, 'LYING T-BAR ROW', '3x10 - 2 Day per Week', 2, 2, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (282, 'LYING T-BAR ROW', '3x8 - 2 Day per Week', 2, 3, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (283, 'LYING T-BAR ROW', '3x12 - 2 Day per Week', 3, 1, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (284, 'LYING T-BAR ROW', '3x10 - 2 Day per Week', 3, 2, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (285, 'LYING T-BAR ROW', '3x8 - 2 Day per Week', 3, 3, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (286, 'LYING T-BAR ROW', '3x12 - 2 Day per Week', 4, 1, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (287, 'LYING T-BAR ROW', '3x12 - 2 Day per Week', 4, 2, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (288, 'LYING T-BAR ROW', '4x10 - 2 Day per Week', 4, 3, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (289, 'LYING T-BAR ROW', '3x12 - 1 Day per Week', 5, 1, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (290, 'LYING T-BAR ROW', '3x12 - 1 Day per Week', 5, 2, 6, 24);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (291, 'LYING T-BAR ROW', '3x10 - 1 Day per Week', 5, 3, 6, 24);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (292, '3/4 SIT-UP', '3x12 - 1 Day per Week', 1, 1, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (293, '3/4 SIT-UP', '3x10 - 1 Day per Week', 1, 2, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (294, '3/4 SIT-UP', '3x8 - 1 Day per Week', 1, 3, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (295, '3/4 SIT-UP', '3x12 - 2 Day per Week', 2, 1, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (296, '3/4 SIT-UP', '3x10 - 2 Day per Week', 2, 2, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (297, '3/4 SIT-UP', '3x8 - 2 Day per Week', 2, 3, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (298, '3/4 SIT-UP', '3x12 - 2 Day per Week', 3, 1, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (299, '3/4 SIT-UP', '3x10 - 2 Day per Week', 3, 2, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (300, '3/4 SIT-UP', '3x8 - 2 Day per Week', 3, 3, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (301, '3/4 SIT-UP', '3x12 - 2 Day per Week', 4, 1, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (302, '3/4 SIT-UP', '3x12 - 2 Day per Week', 4, 2, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (303, '3/4 SIT-UP', '4x10 - 2 Day per Week', 4, 3, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (304, '3/4 SIT-UP', '3x12 - 1 Day per Week', 5, 1, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (305, '3/4 SIT-UP', '3x12 - 1 Day per Week', 5, 2, 7, 25);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (306, '3/4 SIT-UP', '3x10 - 1 Day per Week', 5, 3, 7, 25);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (307, 'CABLE REVERSE CRUNCH', '3x12 - 1 Day per Week', 1, 1, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (308, 'CABLE REVERSE CRUNCH', '3x10 - 1 Day per Week', 1, 2, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (309, 'CABLE REVERSE CRUNCH', '3x8 - 1 Day per Week', 1, 3, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (310, 'CABLE REVERSE CRUNCH', '3x12 - 2 Day per Week', 2, 1, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (311, 'CABLE REVERSE CRUNCH', '3x10 - 2 Day per Week', 2, 2, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (312, 'CABLE REVERSE CRUNCH', '3x8 - 2 Day per Week', 2, 3, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (313, 'CABLE REVERSE CRUNCH', '3x12 - 2 Day per Week', 3, 1, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (314, 'CABLE REVERSE CRUNCH', '3x10 - 2 Day per Week', 3, 2, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (315, 'CABLE REVERSE CRUNCH', '3x8 - 2 Day per Week', 3, 3, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (316, 'CABLE REVERSE CRUNCH', '3x12 - 2 Day per Week', 4, 1, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (317, 'CABLE REVERSE CRUNCH', '3x12 - 2 Day per Week', 4, 2, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (318, 'CABLE REVERSE CRUNCH', '4x10 - 2 Day per Week', 4, 3, 7, 26);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (319, 'BARBELL ROLLOUT FROM BENCH', '3x12 - 1 Day per Week', 5, 1, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (320, 'BARBELL ROLLOUT FROM BENCH', '3x12 - 1 Day per Week', 5, 2, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (321, 'BARBELL ROLLOUT FROM BENCH', '3x10 - 1 Day per Week', 5, 3, 7, 27);\n" +
//                "\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (322, 'DECLINE CRUNCH', '3x12 - 1 Day per Week', 1, 1, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (323, 'DECLINE CRUNCH', '3x10 - 1 Day per Week', 1, 2, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (324, 'DECLINE CRUNCH', '3x8 - 1 Day per Week', 1, 3, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (325, 'DECLINE CRUNCH', '3x12 - 2 Day per Week', 2, 1, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (326, 'DECLINE CRUNCH', '3x10 - 2 Day per Week', 2, 2, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (327, 'DECLINE CRUNCH', '3x8 - 2 Day per Week', 2, 3, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (328, 'DECLINE CRUNCH', '3x12 - 2 Day per Week', 3, 1, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (329, 'DECLINE CRUNCH', '3x10 - 2 Day per Week', 3, 2, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (330, 'DECLINE CRUNCH', '3x8 - 2 Day per Week', 3, 3, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (331, 'DECLINE CRUNCH', '3x12 - 2 Day per Week', 4, 1, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (332, 'DECLINE CRUNCH', '3x12 - 2 Day per Week', 4, 2, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (333, 'DECLINE CRUNCH', '4x10 - 2 Day per Week', 4, 3, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (334, 'DECLINE CRUNCH', '3x12 - 1 Day per Week', 5, 1, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (335, 'DECLINE CRUNCH', '3x12 - 1 Day per Week', 5, 2, 7, 27);\n" +
//                "insert into Exercise (id, nameExercise, frequency, Categorie_idCategorie, Level_idLevel, MuscularGroup_idMuscularGroup, image) \n" +
//                "values (336, 'DECLINE CRUNCH', '3x10 - 1 Day per Week', 5, 3, 7, 27);\n";
// System.out.println("Buceta");
// db.execSQL(sql1);
//System.out.println("Buceta2");

//        db.execSQL(sql2);
//        db.execSQL(sql3);
//        db.execSQL(sql4);
//        db.execSQL(sql5);
//        db.execSQL(sql6);
//        db.execSQL(sql7);

//}
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("Drop table if exists exercise");
//    }
//
//    public DBService(Context context){
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    public Cursor query(String sql, String[] args)
//    {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(sql, args);
//        return cursor;
//    }

//    public void addContact(Contact contact){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(KEY_NAME, contact.getName());
//        initialValues.put(KEY_EMAIL, contact.getEmail());
//        initialValues.put(KEY_PHONE, contact.getCellPhone());
//        db.insert(TABLE, null, initialValues);
//        db.close();
//    }
//    public int updateContact(Contact contact){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues args = new ContentValues();
//        args.put(KEY_NAME, contact.getName());
//        args.put(KEY_EMAIL, contact.getEmail());
//        args.put(KEY_PHONE, contact.getCellPhone());
//        return db.update(TABLE, args, KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
//    }
//
//    public int deleteContact(Contact contact){
//        SQLiteDatabase db = this.getWritableDatabase();
//        int result = db.delete(TABLE, KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
//        return result;
//    }
//
//    public ArrayList<Contact> getAllContacts(){
//        ArrayList<Contact> contacts = new ArrayList<Contact>();
//        String query = "select * from "+TABLE;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        if(cursor.moveToFirst()){
//            do{
//                Contact contact = new Contact();
//                //contact.setId(Integer.parseInt(cursor.getString(0)));
//                contact.setId(cursor.getInt(0));
//                contact.setName(cursor.getString(1));
//                contact.setCellPhone(cursor.getString(2));
//                contact.setEmail(cursor.getString(3));
//                contacts.add(contact);
//            }while(cursor.moveToNext());
//        }
//        return contacts;
//    }



//}
