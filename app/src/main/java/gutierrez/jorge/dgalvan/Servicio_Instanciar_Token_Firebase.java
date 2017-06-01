package gutierrez.jorge.dgalvan;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by GUAPO on 29/08/2016.
 */
public class Servicio_Instanciar_Token_Firebase extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        //Conseguir actualizacion del token...
        String token = FirebaseInstanceId.getInstance().getToken();
        RegistrarToken(token);

    }

    //Metodo para registrar el token en las preferencias y en la bd remota para poder utilizarlo despues...
    private void RegistrarToken(String token){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token",token);
        editor.commit();

    }

}
 