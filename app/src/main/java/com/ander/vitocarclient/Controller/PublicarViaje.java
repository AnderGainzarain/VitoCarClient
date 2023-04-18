package com.ander.vitocarclient.Controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ander.vitocarclient.Controller.Uils.DateManager;
import com.ander.vitocarclient.Controller.Uils.FormValidation;
import com.ander.vitocarclient.R;

import java.time.LocalDateTime;

import Model.ActiveUser;
import Model.Viaje;
import Network.ApiClient;
import Network.ApiViaje;
import Vista.ToastControll;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicarViaje extends Fragment {

    private Spinner sOrigen;
    private Spinner sDestino;
    private final String[] ciudades = {"Vitoria", "Donostia", "Bilbo"};
    private EditText precio;
    private EditText fecha;
    private EditText hora;
    private Button publicar;
    private ActiveUser au = ActiveUser.getActiveUser();
    private ImageButton ibFechaSalida;
    private ImageButton ibHoraSalida;
    public PublicarViaje() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publicar_viaje, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Get the spinners from the xml
        sOrigen = view.findViewById(R.id.sOrigenPublicar);
        sDestino = view.findViewById(R.id.sDestinoPublicar);
        // get the date and precio from the xml
        fecha = view.findViewById(R.id.etFechaSalidaPublicar);
        hora = view.findViewById(R.id.etHoraSalidaPublicar);
        precio = view.findViewById(R.id.etPrecioPublicar);
        // Get the fecha and hora salida image buttons
        ibFechaSalida=view.findViewById(R.id.ibCalendarPublicar);
        ibHoraSalida=view.findViewById(R.id.ibTimePublicar);
       // Create the contents of the spinners
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ciudades);
        // Load the contents into the spinners
        sOrigen.setAdapter(adaptador);
        sDestino.setAdapter(adaptador);
        // Set the event listener to publicar
        publicar = view.findViewById(R.id.btnPublicar);
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do something when the button is clicked
                String origen = sOrigen.getSelectedItem().toString();
                String destino = sDestino.getSelectedItem().toString();
                String fechaSalida = fecha.getText().toString();
                String horaSalida = hora.getText().toString();
                // Check if the precio is introduced
                if(precio.getText().toString().equals("")){
                    Toast.makeText(getContext(),ToastControll.precioVacio(), Toast.LENGTH_LONG).show();
                    return;
                }else{
                    String coste = precio.getText().toString();
                    if(FormValidation.validate(getContext(),origen,destino,fechaSalida,coste).equals(false)) return;

                    publicarViajes(origen,destino,fechaSalida,horaSalida,Integer.parseInt(coste));
                }
            }
        });
        ibFechaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarFecha(view);
            }
        });
        ibHoraSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarHora(view);
            }
        });
    }
    public void publicarViajes(String origen, String destino, String fechaSalida, String hora, int coste) {
        Viaje viaje = new Viaje(coste, origen, destino, fechaSalida + " " + hora);
        Call<Viaje> call = ApiClient.getClient().create(ApiViaje.class).publicarViaje(1111, viaje);
        call.enqueue(new Callback<Viaje>() {
            @Override
            public void onResponse(Call<Viaje> call, Response<Viaje> response) {
                if(response.isSuccessful()){
                    Viaje viaje = response.body();
                    if (viaje!=null)
                    Toast.makeText(getContext(),Vista.ToastControll.viajePublicado(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Viaje> call, Throwable t) {
                Toast.makeText(getContext(),Vista.ToastControll.errorPublicar() + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void mostrarFecha (View v){

        DatePickerDialog date = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fecha.setText(year+"-"+ dateFormat(month+1) +"-"+dateFormat(dayOfMonth));

            }
        }, 2023, 01,28 );
        date.show();
    }
    private String dateFormat(int n){
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    public void mostrarHora (View v){
        TimePickerDialog time = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hora.setText(dateFormat(hourOfDay)+":"+dateFormat(minute) + ":00");
            }
        }, 10, 15, true);
        time.show();
    }
}