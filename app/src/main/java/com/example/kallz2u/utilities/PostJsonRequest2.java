package com.example.kallz2u.utilities;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PostJsonRequest2 extends JsonRequest<JSONObject> {
   private boolean needToken = true;


   public static final int TYPE_API_QUGUANZHANG = 1;

   public static final int TYPE_API_HUIJIEQIANBAO = 2;

   private int type = TYPE_API_QUGUANZHANG;

   private String productId = "1";


   public PostJsonRequest2(String url, String params, Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener) {
      super(Request.Method.POST, url, params, listener, errorListener);
   }



   public PostJsonRequest2(String url, JsonArray params, Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener, int type, String productId) {
      super(Method.POST, url, params.toString(), listener, errorListener);
      this.type = type;
      this.productId = productId;
   }


   @Override
   public Map<String, String> getHeaders() throws AuthFailureError {
      Map<String, String> headers = new HashMap<String, String>();
      headers.put("Content-Type", "application/json");
      headers.put("Authorization", "key=AAAACXg9HiE:APA91bHlkrsIVYJVdqKpl0MU14z3gOFaW4u6N9An9g6MIL0ucQBk_WgLow9hel1B5zOs6kw9YL5TJz5sgAGVqOQ93sow7Dcd0vieZj_HrVUB0WDUCVySceUzgGOH-qG0DcOdn3JGEOcL");

      return headers;
   }


   @Override
   protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

      Response<JSONObject> superResponse;
      try {
         String jsonString = new String(response.data,
                 HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
         superResponse = Response.success(new JSONObject(jsonString),
                 HttpHeaderParser.parseCacheHeaders(response));
      } catch (UnsupportedEncodingException e) {
         superResponse = Response.error(new ParseError(e));
      } catch (JSONException je) {
         superResponse = Response.error(new ParseError(je));
      }
      String authToken = response.headers.get("x-auth-token");
      return superResponse;
   }



}
