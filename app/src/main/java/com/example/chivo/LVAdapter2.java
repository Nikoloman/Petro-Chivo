package com.example.chivo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LVAdapter2 extends ArrayAdapter<DBdata2> {
    public LVAdapter2(@NonNull Context context, ArrayList<DBdata2> DBdata2){
        super(context, 0, DBdata2);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }
        DBdata2 dBdata = getItem(position);
        TextView txt_titledb = listitemView.findViewById(R.id.txt_TitleDatadb);
        txt_titledb.setText("Sucursal: " + dBdata.getSucursal() + " Proveedor: " + dBdata.getProveedor() + " Fecha: " + dBdata.getFecha() + " Hora: " + dBdata.getHora());

        //NOTAS IMPORTANTES!!!!!!
        //crear otras 2 clases de Java para mostrar el listview de cargas
        //Asíes, otras dos, nunca son sufiecientes clases
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carga = new Intent(getContext(), CrudActivity2.class);
                carga.putExtra("Sucursal", dBdata.getSucursal());
                carga.putExtra("Proveedor", dBdata.getProveedor());
                carga.putExtra("Fecha", dBdata.getFecha());
                carga.putExtra("Hora", dBdata.getHora());
                carga.putExtra("Litros", dBdata.getLitros());
                getContext().startActivity(carga);
            }
        });
        return listitemView;
    }
}
