package com.example.walkwalkrevolution;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteStorage {
    private static List<Route> routes;
    private static Context context;

    private RouteStorage() {
    }

    public static void init(Context context) {
        RouteStorage.context = context;
        RouteStorage.routes = Collections.synchronizedList(new ArrayList<Route>());

        // Retrieve stored routes from persistent storage
        SharedPreferences routePrefs = context.getSharedPreferences("routeStorageInfo", Context.MODE_PRIVATE);

        int numRoutesStored = routePrefs.getInt("NumRoutesStored", 0);
        if (numRoutesStored == 0) {
            Log.d("RouteStorage Init", "No routes found in persistent storage, starting fresh");
            return;
        }

        Log.d("RouteStorage Init", String.format("Attempting to retrieve %d routes from SharedPreferences", numRoutesStored));

        Gson gson = new Gson();
        for (int i = 0; i < numRoutesStored; i++) {
            String routeStr = routePrefs.getString("Route_" + i, null);
            if (routeStr == null) {
                Log.e("RouteStorage Init", String.format("Could not retrieve route %d from SharedPreferences: Entry does not exist", i));
                continue;
            }

            Route route = gson.fromJson(routeStr, Route.class);
            Log.d("RouteStorage Init", String.format("Retrieving Route of index %d from SharedPrefs: %s", i, route.toString()));
            routes.add(route);
        }
    }

    public static List<Route> getRoutes() {
       return routes;
    }

    public static void addRoute(Route r) {
        Log.d("RouteStorage", String.format("Adding route to storage: %s", r.toString()));

        SharedPreferences routePrefs = context.getSharedPreferences("routeStorageInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor routePrefsEditor = routePrefs.edit();

        Gson gson = new Gson();
        String routeStr = gson.toJson(r, Route.class);
        routePrefsEditor.putString("Route_" + routes.size(), routeStr);

        // Update the size of `routes' after it is placed in persistent storage for proper indexing
        routes.add(r);
        routePrefsEditor.putInt("NumRoutesStored", routes.size());

        routePrefsEditor.commit();
    }

    /**
     * Saves all the routes to SharedPreferences. Currently unused
     *
     * All information about the routes is stored in the `routeStorageInfo` file.
     *
     * Fields in SharedPreferences file:
     *  - NumRoutesStored: The number of routes that are stored, indexed by their position in the
     *                      `routes' List.
     *  - Route_XX: The XXth route, where XX is the index of the route in the `routes' List.
     */
    public static void saveRoutesToPersistentStorage() {
        SharedPreferences routePrefs = context.getSharedPreferences("routeStorageInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor routePrefsEditor = routePrefs.edit();

        routePrefsEditor.putInt("NumRoutesStored", routes.size());

        if (routes.size() == 0) {
            Log.d("RouteStorage saveRoutesToPersistentStorage", "No Routes to save to persistent storage");
            return;
        }

        Gson gson = new Gson();
        for (int i = 0; i < routes.size(); i++) {

            String routeStr = gson.toJson(routes.get(i), Route.class);
            routePrefsEditor.putString("Route_" + i, routeStr);
        }

        routePrefsEditor.commit();
    }
}
