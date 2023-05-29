package sv.edu.utec.fueltimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import sv.edu.utec.fueltimeapp.Helper.AdminSQLiteOpenHelper;

public class RegistroFragment extends Fragment {

    Button btnRegistrar;
    EditText edtNombre,edtApellido,edtUser,edtContrasena;

    RadioButton rdbGeneroM,rdbGeneroF,rdbGeneroOT;

    public RegistroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity appCompatActivity = new AppCompatActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro, container, false);

        edtNombre=view.findViewById(R.id.edtNOMBRES);
        edtApellido=view.findViewById(R.id.edtAPELLIDOS);
        edtUser=view.findViewById(R.id.edtUSER);
        edtContrasena=view.findViewById(R.id.edtCONTRASENA);
        btnRegistrar=view.findViewById(R.id.btnRegistro);
        rdbGeneroM=view.findViewById(R.id.rdbGeneroM);
        rdbGeneroF=view.findViewById(R.id.rdbGeneroF);
        rdbGeneroOT=view.findViewById(R.id.rdbGeneroOT);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Registrar()) {
                    fragmentosR(new InicioFragment());
                }
            }
        });
        return  view;
    }

    @SuppressLint("CommitTransaction")
    private void fragmentosR(android.app.Fragment fragment){
        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmLayoutContainer,fragment);
        fragmentTransaction.commit();
    }

    private  boolean  Registrar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity().getApplicationContext(),"fulltimeBD",null,2);
        SQLiteDatabase bd= admin.getWritableDatabase();
        Cursor cursor;
        String nom=edtNombre.getText().toString();
        String ape=edtApellido.getText().toString();
        String user=edtUser.getText().toString();
        String contra=edtContrasena.getText().toString();
        String genero="";

        if (rdbGeneroM.isChecked()){
            genero="MA";
        } else if (rdbGeneroF.isChecked()) {
            genero="FE";
        }else {
            genero="OT";
        }

        ContentValues informacion =new ContentValues();
        informacion.put("nombres",nom);
        informacion.put("apellidos",ape);
        informacion.put("usuario",user);
        informacion.put("contrasena",contra);
        informacion.put("genero",genero);
        try {
            cursor = bd.rawQuery("SELECT usuario from Usuarios WHERE usuario=?",new String[]{user});
            if (cursor.moveToFirst()){
                @SuppressLint("Range") String us=cursor.getString(cursor.getColumnIndex("usuario"));
                if (us.equals(user) ){
                    Toast.makeText(getActivity().getApplicationContext(), "Este Usuario ya esta registrado", Toast.LENGTH_LONG).show();
                    return  false;
                }
            }
            bd.insert("Usuarios", null, informacion);

            Toast.makeText(getActivity().getApplicationContext(), "Usuario Registrado Correctamente", Toast.LENGTH_LONG).show();
            bd.close();
            cursor.close();
            return true;

        } catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), ""+e.getCause(), Toast.LENGTH_LONG).show();
            return false;
        }
    }
}