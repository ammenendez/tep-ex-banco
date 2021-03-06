package br.ufs.tep.livros;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ammenendez on 23/04/15.
 */
public class BDSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BookDB";

    private static final String TABELA_LIVROS = "livros";
    private static final String ID = "id";
    private static final String TITULO = "titulo";
    private static final String AUTOR = "autor";
    private static final String ANO = "ano";

    private static final String[] COLUNAS = {ID, TITULO, AUTOR, ANO};

    public BDSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE livros ("+
                               "id     INTEGER  PRIMARY KEY AUTOINCREMENT,"+
                               "titulo TEXT,"+
                               "autor  TEXT,"+
                               "ano    INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS livros");
        this.onCreate(db);
    }

    public void addLivro(Livro livro) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITULO, livro.getTitulo());
        values.put(AUTOR, livro.getAutor());
        values.put(ANO, new Integer(livro.getAno()));

        db.insert(TABELA_LIVROS, null, values);
        db.close();
    }

    public Livro getLivro(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABELA_LIVROS, // a. tabela
                        COLUNAS, // b. colunas
                        " id = ?", // c. colunas para comparar
                        new String[] { String.valueOf(id) }, // d. parâmetros
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        if (cursor == null) {
            return null;
        } else {
            cursor.moveToFirst();
            Livro livro = cursorToLivro(cursor);
            return livro;
        }
    }

    private Livro cursorToLivro(Cursor cursor) {
        Livro livro = new Livro();
        livro.setId(Integer.parseInt(cursor.getString(0)));
        livro.setTitulo(cursor.getString(1));
        livro.setAutor(cursor.getString(2));
        livro.setAno(Integer.parseInt(cursor.getString(3)));
        return livro;
    }

    public ArrayList<Livro> getAllLivros() {
        ArrayList<Livro> listaLivros = new ArrayList<Livro>();

        String query = "SELECT  * FROM " + TABELA_LIVROS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Livro livro = cursorToLivro(cursor);
                listaLivros.add(livro);
            } while (cursor.moveToNext());
        }
        return listaLivros;
    }


    public int updateLivro(Livro livro) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITULO, livro.getTitulo());
        values.put(AUTOR, livro.getAutor());
        values.put(ANO, new Integer(livro.getAno()));

        int i = db.update(TABELA_LIVROS, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(livro.getId()) }); //parâmetros

        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteLivro(Livro livro) {
        SQLiteDatabase db = this.getWritableDatabase();

        int i = db.delete(TABELA_LIVROS, //table name
                ID+" = ?",  // selections
                new String[] { String.valueOf(livro.getId()) }); //selections args

        db.close();
        return i; // número de linhas excluídas
    }

}
