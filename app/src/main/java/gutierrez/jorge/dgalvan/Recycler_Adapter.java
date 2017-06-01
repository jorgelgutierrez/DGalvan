package gutierrez.jorge.dgalvan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Macbook on 04/12/16.
 */

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_View_Holder> {

    String [] mynumeros;
    String [] mycomplementos;
    String [] myguarniciones;
    String [] mybebidas;
    Context context;
    LayoutInflater inflater;


    public Recycler_Adapter(Context context,String[] numeros_paquete, String[] complementos, String[] guarniciones, String[] bebidas){
        this.context = context;
        this.mynumeros = numeros_paquete;
        this.mycomplementos = complementos;
        this.myguarniciones = guarniciones;
        this.mybebidas = bebidas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Recycler_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_cardview_menu,parent,false);
        Recycler_View_Holder view = new Recycler_View_Holder(v);

        return view;
    }

    @Override
    public void onBindViewHolder(Recycler_View_Holder holder, int position) {
        holder.numero.setText(mynumeros[position]);
        holder.complemento.setText(mycomplementos[position]);
        holder.guarnicion.setText(myguarniciones[position]);
        holder.bebida.setText(mybebidas[position]);
        holder.cardView.setOnClickListener(onClickListener);
        holder.cardView.setTag(holder);
        holder.imagen_paquete.setImageResource(R.drawable.ic_restaurant);

    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Recycler_View_Holder view_holder = (Recycler_View_Holder)v.getTag();

            final Intent intent;
            int position = view_holder.getAdapterPosition();
            /*
            switch (position){
                case 0:
                    intent =  new Intent(context, Lienzo_Figuras.class);
                    intent.putExtra("figura",position);
                    Toast.makeText(context,mymensajes[position],Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    intent =  new Intent(context, Lienzo_Figuras.class);
                    intent.putExtra("figura",position);
                    Toast.makeText(context,mymensajes[position],Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    intent =  new Intent(context, Lienzo_Figuras.class);
                    intent.putExtra("figura",position);
                    Toast.makeText(context,mymensajes[position],Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    intent =  new Intent(context, Lienzo_Figuras.class);
                    intent.putExtra("figura",position);
                    Toast.makeText(context,mymensajes[position],Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    intent =  new Intent(context, Lienzo_Figuras.class);
                    intent.putExtra("figura",position);
                    Toast.makeText(context,mymensajes[position],Toast.LENGTH_LONG).show();
                    break;
                default:
                    intent =  new Intent(context, Lienzo_Figuras.class);
                    intent.putExtra("figura",-1);
                    break;
            }
            context.startActivity(intent);*/
        }
    };

    @Override
    public int getItemCount() {
        return mynumeros.length;
    }
}
