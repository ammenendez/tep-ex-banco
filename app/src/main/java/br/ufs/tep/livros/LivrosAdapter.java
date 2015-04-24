package br.ufs.tep.livros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ammenendez on 23/04/15.
 */
public class LivrosAdapter extends ArrayAdapter<Livro> {

    private final Context context;
    private final ArrayList<Livro> elementos;

    public LivrosAdapter(Context context, ArrayList<Livro> elementos) {
        super(context, R.layout.layoutlivro, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.layoutlivro, parent, false);

        TextView titulo = (TextView) rowView.findViewById(R.id.tvTitulo);
        TextView ano = (TextView) rowView.findViewById(R.id.tvAno);
        TextView autor = (TextView) rowView.findViewById(R.id.tvAutor);

        titulo.setText(elementos.get(position).getTitulo());
        autor.setText(elementos.get(position).getAutor());
        ano.setText(Integer.toString(elementos.get(position).getAno()));
//        ano.setText(elementos.get(position).getAno());

        return rowView;
    }


}
