package com.app.apiapplicationdemo.apiHelper;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.apiapplicationdemo.CrudOperation;
import com.app.apiapplicationdemo.MainActivity;
import com.app.apiapplicationdemo.R;
import com.app.apiapplicationdemo.model.Person;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonApiCalls {
    public final static String BASE_URL = "https://62e1182cfa99731d75cdd699.mockapi.io/tblPerson";
    private OkHttpClient client;
    private Request request;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private AppCompatActivity activity;

    public PersonApiCalls(AppCompatActivity activity) {
        client = new OkHttpClient();
        this.activity = activity;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void crud(CrudOperation type, Person person) {
        if (type.equals(CrudOperation.CREATE)) {
            String url = BASE_URL;
            request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSON, new Gson().toJson(person)))
                    .build();
        } else if (type.equals(CrudOperation.UPDATE)) {
            String url = BASE_URL + "/" + person.getId();
            request = new Request.Builder()
                    .url(url)
                    .put(RequestBody.create(JSON, new Gson().toJson(person)))
                    .build();
        } else if (type.equals(CrudOperation.DELETE)) {
            String url = BASE_URL + "/" + person.getId();
            request = new Request.Builder()
                    .url(url)
                    .delete()
                    .build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Error: " + e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                String data = response.body().string();

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!request.method().equals("DELETE")) {
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                        }
                    }
                });
            }
        });

    }


}
