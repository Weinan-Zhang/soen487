package com.soen487.rest.project.data.service.artist.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtils {
    public static void output(HttpServletResponse response, int responseCode, String responseStr) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(responseCode);
        out.print(responseStr);
        out.flush();
    }
}
