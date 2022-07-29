package com.app.apiapplicationdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.apiapplicationdemo.apiHelper.PersonApiCalls;
import com.app.apiapplicationdemo.model.Person;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    ArrayList<Person> persons;
    AppCompatActivity context;
    PersonApiCalls personApi;

    public PersonAdapter(AppCompatActivity context, ArrayList<Person> persons) {
        this.context = context;
        this.persons = persons;
    }

    @NonNull
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.person_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.ViewHolder holder, int position) {
        Person person = persons.get(position);

        if(person != null){
            holder.name.setText(person.getName());
            holder.email.setText(String.valueOf(person.getEmail()));
            holder.country.setText(String.valueOf(person.getCountry()));
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FormActivity.class);
                    intent.putExtra("person",person);
                    context.startActivity(intent);
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    personApi = new PersonApiCalls(context);
                    personApi.crud(CrudOperation.DELETE,person);
                    persons.remove(person);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, country;
        Button edit, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.personName);
            email = itemView.findViewById(R.id.personEmail);
            country = itemView.findViewById(R.id.personCountry);
            edit = itemView.findViewById(R.id.editBtn);
            delete = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
