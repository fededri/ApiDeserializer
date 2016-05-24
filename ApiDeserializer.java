package com.certicamara.Deserializers;

import com.certicamara.ApiResultResponse;
import com.certicamara.connections.ResultApiMessage;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Federico Torres on 24/5/2016.
 * Type Adapter que se debe registrar en el gson utilizado por retrofit para cada clase, permite parsear el json en un objeto o en su defecto obtener el error de respuesta que envía la API
 * <p/>
 * gsonBuilder.registerTypeAdapter(Example.class, new ApiDeserializer());
 * Cada Clase contenedera del json debe implementar ApiResultResponse (success y resultMessage)
 */
public class ApiDeserializer implements JsonDeserializer<ApiResultResponse> {

    @Override
    public ApiResultResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {


        try {
            Class<?> clazz = Class.forName(((Class) typeOfT).getName());
            Object object = clazz.newInstance();

            if (object instanceof ApiResultResponse) {


                Gson gson = new Gson();
                try {
                    object = gson.fromJson(json, clazz);
                    ((ApiResultResponse) object).setSuccess(true);
                } catch (Exception e) {
                    try {
                        e.printStackTrace();
                        ResultApiMessage apiResult = gson.fromJson(json, ResultApiMessage.class);
                        ((ApiResultResponse) object).setResultMessage(apiResult.getResults());
                        ((ApiResultResponse) object).setSuccess(false);
                    } catch (Exception secondEx) {
                        ((ApiResultResponse) object).setResultMessage("No se pudo completar la operacion");
                        ((ApiResultResponse) object).setSuccess(false);
                    }

                }

            }

            return (ApiResultResponse) object;


        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResultResponse() {
                @Override
                public String getResultMessage() {
                    return "No se pudo completar la operacion";
                }

                @Override
                public void setResultMessage(String resultMessage) {

                }

                @Override
                public boolean isSuccess() {
                    return false;
                }

                @Override
                public void setSuccess(boolean success) {

                }
            };
        }


        /*LoginResponse loginResponse = new LoginResponse();
        Gson gson = new Gson();
        try {
            loginResponse = gson.fromJson(json, LoginResponse.class);
            loginResponse.setSuccess(true);
        } catch (Exception e) {
            try {
                e.printStackTrace();
                ResultApiMessage apiResult = gson.fromJson(json, ResultApiMessage.class);
                loginResponse.setResultMessage(apiResult.getResults());
                loginResponse.setSuccess(false);
            } catch (Exception secondEx) {
                loginResponse.setResultMessage("No se pudo completar la operacion");
                loginResponse.setSuccess(false);
            }

        }


        return loginResponse;*/
    }
}
