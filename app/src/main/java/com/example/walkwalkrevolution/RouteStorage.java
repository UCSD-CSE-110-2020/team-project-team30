package com.example.walkwalkrevolution;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteStorage {
    private static List<Route> routes;

    private RouteStorage() {
    }

    public static void init() {
        routes = Collections.synchronizedList(new ArrayList<Route>());
    }

    public static List<Route> getRoutes() {
       return routes;
    }

    public static void addRoute(Route r) {
        Log.d("RouteStorage", String.format("Adding route to storage: %s", r.toString()));
        routes.add(r);

        for (Route z : routes)
            Log.d("RouteStorage", String.format("Route in list: %s", z.toString()));
    }
}
