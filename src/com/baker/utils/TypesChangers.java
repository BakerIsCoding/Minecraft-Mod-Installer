/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baker.utils;

import com.baker.simpleExceptions.SimpleException;
import org.json.JSONObject;

/**
 *
 * @author Baker
 */
public class TypesChangers {

    public String integerToString(int userInteger) throws SimpleException {
        try {
            String finalString = String.valueOf(userInteger);
            return finalString;
        } catch (Exception e) {
            throw new SimpleException("Something went wrong trying to convert " + userInteger + " to String");
        }
    }

    public String booleanToString(Boolean userBool) throws SimpleException {
        if (userBool) {
            return "Yes";
        } else if (!userBool) {
            return "No";
        } else {
            throw new SimpleException("Something went wrong trying to convert " + userBool + " to String");
        }
    }

    public Integer StringToInteger(String userString) throws SimpleException {
        try {
            return Integer.parseInt(userString);
        } catch (Exception e) {
            System.out.println("Something went wrong trying to convert " + userString + " to Integer");
            throw new SimpleException("Input a number, not text");
        }
    }

    public String jsonObjectToString(JSONObject userJsonObject) throws SimpleException {
        try {
            String convertedJsonObject = userJsonObject.toString();
            return convertedJsonObject;
        } catch (Exception e) {
            throw new SimpleException("Something went wrong trying to convert " + userJsonObject + " to String");
        }

    }

    public Long StringSizeToLong(String sizeStr) {
        try {
            if (sizeStr != null && sizeStr.endsWith("MB")) {
                // Elimina la unidad 'MB' y convierte el número a double
                String numberStr = sizeStr.substring(0, sizeStr.length() - 2).trim();
                double sizeInMb = Double.parseDouble(numberStr);

                // Convierte MB a bytes
                return (long) (sizeInMb * 1024 * 1024);
            } else {
                throw new IllegalArgumentException("El formato del tamaño del archivo no es válido.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error al parsear el tamaño del archivo.", e);
        }
    }

}
