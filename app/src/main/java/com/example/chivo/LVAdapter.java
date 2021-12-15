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

public class LVAdapter extends ArrayAdapter<DBdata> {
    public LVAdapter(@NonNull Context context, ArrayList<DBdata> Dbdata) {
        super(context, 0, Dbdata);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }
        DBdata dBdata = getItem(position);
        TextView txt_titledb = listitemView.findViewById(R.id.txt_TitleDatadb);
        txt_titledb.setText(" Dueño: " + dBdata.getDueño() + " Unidad: " + dBdata.getUnidad() + "\n"
                            + " Fecha: " + dBdata.getFecha() + " Hora: " + dBdata.getHora());

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ticket = new Intent(getContext(), CrudActivity.class);
                ticket.putExtra("Sucursal", dBdata.getSucursal());
                ticket.putExtra("Folio", dBdata.getFolio());
                ticket.putExtra("Fecha", dBdata.getFecha());
                ticket.putExtra("Hora", dBdata.getHora());
                ticket.putExtra("Ruta", dBdata.getRuta());
                ticket.putExtra("Dueño", dBdata.getDueño());
                ticket.putExtra("Unidad", dBdata.getUnidad());
                ticket.putExtra("Placas", dBdata.getPlacas());
                ticket.putExtra("Litros", dBdata.getLitros());
                ticket.putExtra("Estatus", dBdata.getEstatus());
                getContext().startActivity(ticket);
            }
        });

        return listitemView;
    }
}