package gutierrez.jorge.dgalvan;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Macbook on 04/12/16.
 */

public class Recycler_View_Holder extends RecyclerView.ViewHolder {

    ImageView imagen_paquete;
    TextView numero;
    TextView complemento;
    TextView guarnicion;
    TextView bebida;
    CardView cardView;

    public Recycler_View_Holder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cardview);
        imagen_paquete = (ImageView) itemView.findViewById(R.id.img_paquete);
        numero = (TextView) itemView.findViewById(R.id.txt_numero_paquete);
        complemento = (TextView) itemView.findViewById(R.id.txt_complemento);
        guarnicion = (TextView) itemView.findViewById(R.id.txt_guarnicion);
        bebida = (TextView) itemView.findViewById(R.id.txt_bebida);
    }
}
