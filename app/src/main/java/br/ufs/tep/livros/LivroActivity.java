package br.ufs.tep.livros;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LivroActivity extends ActionBarActivity {

    private BDSQLiteHelper bd;
    private EditText nome;
    private EditText autor;
    private EditText ano;
    Button alterar;
    Button remover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro);

        alterar = (Button) findViewById(R.id.btUpdate);
        remover = (Button) findViewById(R.id.btDelete);


        nome = (EditText) findViewById(R.id.etNome);
        autor = (EditText) findViewById(R.id.etAutor);
        ano = (EditText) findViewById(R.id.etAno);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("ID", 0);
        bd = new BDSQLiteHelper(this);
        Livro livro = bd.getLivro(id);

        nome.setText(livro.getTitulo());
        autor.setText(livro.getAutor());
        ano.setText(Integer.toString(livro.getAno()));

        alterar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Livro livro = new Livro();
                livro.setId(id);
                livro.setTitulo(nome.getText().toString());
                livro.setAutor(autor.getText().toString());
                livro.setAno(Integer.parseInt(ano.getText().toString()));

                bd.updateLivro(livro);
                Toast.makeText(getBaseContext(), "Livro alterado com sucesso.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Livro livro = new Livro();
                livro.setId(id);
                bd.deleteLivro(livro);
                bd.updateLivro(livro);
                nome.setText("");
                autor.setText("");
                ano.setText("");
                remover.setEnabled(false);
                Toast.makeText(getBaseContext(), "Livro removido com sucesso.",
                        Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_livro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
