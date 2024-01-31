/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baker.utils;

import com.baker.simpleExceptions.SimpleException;
import java.io.IOException;
import org.json.JSONObject;

/**
 *
 * @author Baker
 */
public class JsonParser {

    public JSONObject parseJson(String jsonString) throws SimpleException {
        try {
            // Creating a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject;
        } catch (Exception e) {
            throw new SimpleException(e.getMessage());
        }
    }
}
