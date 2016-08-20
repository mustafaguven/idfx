package tr.com.idefix.data.type_adapter_factory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import tr.com.idefix.data.entity.ChangePasswordResponseEntity;
import tr.com.idefix.data.entity.SozlesmeResponseEntity;

/**
 * Created by mustafaguven on 16.11.2015.
 */
public class PasswordResponseAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        if (typeToken.getType() == ChangePasswordResponseEntity.class) {
            return (TypeAdapter<T>) newPasswordResponseAdapter(gson.getDelegateAdapter(this, TypeToken.get(ChangePasswordResponseEntity.class)));
        } else if (typeToken.getType() == SozlesmeResponseEntity.class) {
            return (TypeAdapter<T>) stringResponseAdapter(gson.getDelegateAdapter(this, TypeToken.get(SozlesmeResponseEntity.class)));
        } else
            return null;
    }

    private  TypeAdapter<ChangePasswordResponseEntity> newPasswordResponseAdapter (final TypeAdapter<ChangePasswordResponseEntity> delegateAdapter) {
        return new TypeAdapter<ChangePasswordResponseEntity>() {

            @Override
            public void write(JsonWriter out, ChangePasswordResponseEntity value) throws IOException {
                delegateAdapter.write(out, value);
            }

            @Override
            public ChangePasswordResponseEntity read(JsonReader in) throws IOException {
                in.beginArray();
                ChangePasswordResponseEntity response = delegateAdapter.read(in);
                while(in.hasNext()) {
                    // Skip remaining elements in the array
                    in.skipValue();
                }
                in.endArray();
                return response;
            }
        };
    }

    private  TypeAdapter<SozlesmeResponseEntity> stringResponseAdapter (final TypeAdapter<SozlesmeResponseEntity> delegateAdapter) {

        return new TypeAdapter<SozlesmeResponseEntity>() {
            @Override
            public void write(JsonWriter out, SozlesmeResponseEntity value) throws IOException {
                delegateAdapter.write(out, value);
            }

            @Override
            public SozlesmeResponseEntity read(JsonReader in) throws IOException {
                String s = in.nextString();
                SozlesmeResponseEntity sozlesmeResponseEntity = new SozlesmeResponseEntity();
                sozlesmeResponseEntity.html(s);
                return sozlesmeResponseEntity;

/*                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                try {

                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

                    String line;

                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                String result = sb.toString();*/



            }
        };

        /*return new TypeAdapter<String>() {

            @Override
            public void write(JsonWriter out, ChangePasswordResponseEntity value) throws IOException {
                delegateAdapter.write(out, value);
            }

            @Override
            public String read(JsonReader in) throws IOException {
                in.beginArray();
                String response = delegateAdapter.read(in);
                while(in.hasNext()) {
                    // Skip remaining elements in the array
                    in.skipValue();
                }
                in.endArray();
                return response;
            }
        };*/
    }
}