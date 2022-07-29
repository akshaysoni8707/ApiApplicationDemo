package com.app.apiapplicationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.apiapplicationdemo.apiHelper.PersonApiCalls;
import com.app.apiapplicationdemo.model.Person;

public class FormActivity extends AppCompatActivity {
    EditText name, country, email;
    Button button;
    Person person;
    PersonApiCalls personApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        personApi = new PersonApiCalls(this);

        name = findViewById(R.id.editPersonName);
        country = findViewById(R.id.editPersonCountry);
        email = findViewById(R.id.editPersonEmail);
        button = findViewById(R.id.saveBtn);

        Intent intent = getIntent();

        if (intent.getSerializableExtra("person") != null) {
            person = (Person) intent.getSerializableExtra("person");
            name.setText(person.getName());
            country.setText(person.getCountry());
            email.setText(person.getEmail());
        } else {
            person = new Person();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person.setName(name.getText().toString());
                person.setCountry(country.getText().toString());
                person.setEmail(email.getText().toString());
                if (person.getId() == 0) {
                    personApi.crud(CrudOperation.CREATE,person);
                } else {
                    personApi.crud(CrudOperation.UPDATE,person);
                }
//                Intent intent1 = new Intent(FormActivity.this, MainActivity.class);
//                startActivity(intent1);
            }
        });


    }
}