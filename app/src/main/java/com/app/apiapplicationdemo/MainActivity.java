package com.app.apiapplicationdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.apiapplicationdemo.apiHelper.PersonApiCalls;
import com.app.apiapplicationdemo.model.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ArrayList<Person> persons;
    AppCompatActivity context;
    PersonApiCalls personApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.personList);
        floatingActionButton = findViewById(R.id.addPersonBtn);
        context = this;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });
        getPersons();

    }

    public void getPersons() {
        persons = new ArrayList<>();
        personApi = new PersonApiCalls(this);

        Request request = new Request.Builder()
                .url(PersonApiCalls.BASE_URL)
                .build();
        try {
            personApi.getClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    persons = gson.fromJson(responseData, new TypeToken<ArrayList<Person>>() {
                    }.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(new PersonAdapter(context, persons));
                        }
                    });
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}