package GSONAdapters;

import com.google.gson.*;
import Entities.User;
import Entities.Client; 
import Entities.Admin;
import Entities.Book;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Map;

public class UserAdapter implements JsonSerializer<User>, JsonDeserializer<User> {

    // CORREGIDO: El gsonInterno ahora tiene el escudo anti-reflexión activado
    private final Gson gsonInterno = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                // Si el campo pertenece a un servicio, printer o hilos internos, lo ignoramos por completo
                String typeName = f.getDeclaredType().getTypeName();
                return typeName.equals("BorrowService") || 
                    typeName.equals("Printer") ||
                    typeName.equals("BookService") ||
                    typeName.equals("UserService") ||
                    typeName.contains("ThreadLocal") ||
                    typeName.contains("Scanner");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        })
        .create();

    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        
        // 1. Atributos básicos del usuario
        jsonObject.addProperty("id", src.getID());
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("surname", src.getSurname());
        jsonObject.addProperty("email", src.getEmail());
        jsonObject.addProperty("password", src.getPassword());
        jsonObject.addProperty("score", src.getScore());
        jsonObject.addProperty("isBanned", src.getBannedUserStatus());
        jsonObject.addProperty("type", src.getClass().getSimpleName()); 

        // 2. CONSTRUCCIÓN MANUAL DEL INVENTARIO ACTUAL
        JsonObject currentInvJson = new JsonObject();
        if (src.getUserCurrentInventoryMap() != null) {
            for (Map.Entry<Integer, Book> entry : src.getUserCurrentInventoryMap().entrySet()) {
                Book libro = entry.getValue();
                JsonObject libroJson = new JsonObject();
                // Solo guardamos datos simples del libro para evitar bucles infinitos
                libroJson.addProperty("title", libro.getTitle()); 
                // libroJson.addProperty("id", libro.getId()); 
                
                // Lo añadimos al JSON del inventario usando su llave (ID del libro)
                currentInvJson.add(String.valueOf(entry.getKey()), libroJson);
            }
        }
        jsonObject.add("userCurrentInventory", currentInvJson);

        // 3. CONSTRUCCIÓN MANUAL DEL INVENTARIO PASADO
        JsonObject pastInvJson = new JsonObject();
        if (src.getUserPastInventoryMap() != null) {
            for (Map.Entry<Integer, Book> entry : src.getUserPastInventoryMap().entrySet()) {
                Book libro = entry.getValue();
                JsonObject libroJson = new JsonObject();
                libroJson.addProperty("title", libro.getTitle());
                pastInvJson.add(String.valueOf(entry.getKey()), libroJson);
            }
        }
        jsonObject.add("userPastInventory", pastInvJson);

        return jsonObject;
    }

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null || !json.isJsonObject()) {
            return null;
        }

        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement typeElement = jsonObject.get("type");

        if (typeElement == null) {
            return null; 
        }

        String type = typeElement.getAsString();

        switch (type) {
            case "Client":
                return gsonInterno.fromJson(jsonObject, Client.class);
            case "Admin":
                return gsonInterno.fromJson(jsonObject, Admin.class);
            default:
                throw new JsonParseException("Subclase de usuario desconocida: " + type);
        }
    }
}