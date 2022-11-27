package com.example.alta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText contro, nom, sem, carr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contro = findViewById(R.id.Ncontrol);
        nom = findViewById(R.id.Name);
        sem = findViewById(R.id.Sen);
        carr = findViewById(R.id.Carr);
    }

    // Damos de alta los usuarios en nuestra aplicación
    public void altas(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nControl = contro.getText().toString();
        String nombre = nom.getText().toString();
        String semestre = sem.getText().toString();
        String carrera = carr.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("nControl", nControl);
        registro.put("nombre", nombre);
        registro.put("semestre", semestre);
        registro.put("carrera", carrera);
        //Los inserto en la base de datos
        bd.insert("usuario", null, registro);
        bd.close();
        Toast.makeText(this,"Datos del alumno cargados completamente", Toast.LENGTH_SHORT).show();
        this.limpia();
    }
    public void limpia(){
        contro.setText("");
        nom.setText("");
        sem.setText("");
        carr.setText("");
    }
    public void limpiar(View view){
        this.limpia();
    }

    public void consulta(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nControl = contro.getText().toString();
        Cursor fila = bd.rawQuery("select nombre, semestre, carrera from usuario where nControl=" + nControl, null);
        //Cursor fila = bd.rawQuery("select nombre, semestre, carrera from usuario", null);
        if(fila.moveToFirst()){
            Toast.makeText(this,"Estoy dentro del primer registro", Toast.LENGTH_SHORT).show();
            nom.setText(fila.getString(0));
            sem.setText(fila.getString(1));
            carr.setText(fila.getString(2));
        } else {
            Toast.makeText(this,"No existe ningún alumno con ese Número de Control", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void baja(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nControl = contro.getText().toString();
        //Se borra la base de datos del usuario por el Número de Control
        int cant = bd.delete("usuario", "nControl = "+nControl, null);
        bd.close();
        contro.setText("");
        nom.setText("");
        sem.setText("");
        carr.setText("");
        if(cant == 1){
            Toast.makeText(this, "Alumno eliminado", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "No existe el alumno", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificacion(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nControl = contro.getText().toString();
        String nombre = nom.getText().toString();
        String semestre = sem.getText().toString();
        String carrera = carr.getText().toString();
        ContentValues registro = new ContentValues();
        //Actualizamos con los nuevos datos, la información cambiada
        registro.put("nombre", nombre);
        registro.put("semestre", semestre);
        registro.put("carrera", carrera);
        int cant = bd.update("usuario", registro, "nControl= "+nControl,null);
        //int cant = bd.update("usuario", registro, "nControl = " + nControl, null);
        bd.close();
        if(cant == 1){
            Toast.makeText(this, "Datos modificados con éxito", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "No existe el alumno", Toast.LENGTH_SHORT).show();
        }
    }
}//class