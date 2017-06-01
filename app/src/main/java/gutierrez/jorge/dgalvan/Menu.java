package gutierrez.jorge.dgalvan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Menu extends Fragment {

    RecyclerView recyclerView;

    public Menu(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] numeros_paquete = getResources().getStringArray(R.array.numeros_paquete);
        String[] complementos = getResources().getStringArray(R.array.complementos);
        String[] guarniciones = getResources().getStringArray(R.array.guarniciones);
        String[] bebidas = getResources().getStringArray(R.array.bebidas);
        Recycler_Adapter adapter = new Recycler_Adapter(getContext(),numeros_paquete,complementos,guarniciones,bebidas);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_lista_menu_pedidos);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}
