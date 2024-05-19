package br.com.delflix.application.utils;

import br.com.delflix.shared.Enum.GenderEnum;
import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;

public class GenderUtils {
    public static String GetGender(RequestDvdJson request)
    {
        if(IsNumeric(request.getGender().toString()))
        {
            return (GenderEnum.GetByValue(Integer.parseInt(request.getGender().toString())).toString());   
        }
        else
        {
            return (GenderEnum.GetByName(request.getGender().toString()).toString());
        }
    }

    private static boolean IsNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

}
