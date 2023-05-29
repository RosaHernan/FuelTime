package sv.edu.utec.fueltimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import sv.edu.utec.fueltimeapp.Helper.AdminSQLiteOpenHelper;

public class Puntos_LealFragment extends Fragment {

    Button btnConectarPLeal;

    TextView lblInfoLeal,lblTitulo;

    EditText edtUsuarioLeal;

    public Puntos_LealFragment(){

    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        AppCompatActivity appCompatActivity = new AppCompatActivity();

    }

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_puntos_leal, container, false);

        btnConectarPLeal = view.findViewById(R.id.btnConectarLeal);
        lblInfoLeal = view.findViewById(R.id.tvInfoPuntosLeal);
        lblTitulo = view.findViewById(R.id.tvInfoUsu);

        edtUsuarioLeal = view.findViewById(R.id.edtUsuarioLeal);

        btnConectarPLeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PuntosLeal()){
                    Toast.makeText(getActivity().getApplicationContext(), "Logueado Correctemente en Leal", Toast.LENGTH_LONG).show();
                    edtUsuarioLeal.setVisibility(view.INVISIBLE);
                    btnConectarPLeal.setVisibility(view.INVISIBLE);
                    lblInfoLeal.setVisibility(view.VISIBLE);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "No se encontro el usuario", Toast.LENGTH_LONG).show();
                }
            }
        });



        return view;

    }

    @SuppressLint("Range")
    private boolean PuntosLeal(){
        String idUsu = "";
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity().getApplicationContext(),"fulltimeBD",null,2);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String usuario=edtUsuarioLeal.getText().toString();
        Cursor cursor;

        cursor = bd.rawQuery("SELECT id_usuario from Usuarios WHERE usuario=?",new String[]{usuario});
        if(cursor.moveToFirst()){
            idUsu = cursor.getString(cursor.getColumnIndex("id_usuario"));
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "El Usuario no existe", Toast.LENGTH_LONG).show();
        }

        cursor = bd.rawQuery("SELECT * from Leal WHERE id_usuario=?",new String[]{idUsu});
        if(cursor.moveToFirst()){
            String gasolinera=cursor.getString(cursor.getColumnIndex("nombre_gasolinera"));
            String puntos=cursor.getString(cursor.getColumnIndex("puntos"));
            lblInfoLeal.setText(gasolinera + ": " + puntos);
        }else{

            Date dt = new Date();
            SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
            String formatteDate = df.format(dt.getDate());

            Random aleatorio = new Random(System.currentTimeMillis());
            int puntos = aleatorio.nextInt(100 * 10);

            ContentValues informacion =new ContentValues();
            informacion.put("id_usuario",idUsu);
            informacion.put("puntos",puntos);
            informacion.put("fecha_creacion",formatteDate);
            informacion.put("nombre_gasolinera","Texaco");
            informacion.put("fecha_modificacion",formatteDate);
            bd.insert("Leal", null, informacion);
        }

        cursor = bd.rawQuery("SELECT * from Leal WHERE id_usuario=?",new String[]{idUsu});
        if(cursor.moveToFirst()){
            String gasolinera=cursor.getString(cursor.getColumnIndex("nombre_gasolinera"));
            String puntos=cursor.getString(cursor.getColumnIndex("puntos"));
            lblInfoLeal.setText(gasolinera + ": " + puntos);
        }else{
            return false;
        }

        return true;
    }
}

