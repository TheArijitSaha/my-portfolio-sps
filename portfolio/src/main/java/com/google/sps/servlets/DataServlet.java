// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Servlet that returns comments data */
@WebServlet("/comments")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Convert comments data to JSON
    /* Gson gson = new Gson();
    String json = gson.toJson(comments);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json); */
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String commentText = getParameter(request, "comment", "");

    // Add comment to datastore if the string has positive length
    if (commentText.length() > 0) {
      long timestamp = System.currentTimeMillis();

      // Form Entity
      Entity taskEntity = new Entity("Comment");
      taskEntity.setProperty("text", commentText);
      taskEntity.setProperty("timestamp", timestamp);

      // Store in Datastore
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(taskEntity);
    }

    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }

  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}
