package com.crudapi.mobile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.crudapi.mobile.client.Client;
import com.crudapi.mobile.client.ClientDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Client.class}, version = 1, exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private static volatile RoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public abstract ClientDao clientDao();

    public static RoomDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    RoomDatabase.class,
                                    "room_database")
                            .addCallback(sRoomDatabaseCalllback)
                            .build();
                }
            }
        }

        return INSTANCE;

    }

    private static RoomDatabase.Callback sRoomDatabaseCalllback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                ClientDao clientDao = INSTANCE.clientDao();
                clientDao.deleteAll();

                clientDao.insert(new Client("Demba", "Ba", "+221 77 262 11 91"));
                clientDao.insert(new Client("Samba", "Sidibé", "+221 77 123 45 67"));
            });
        }
    };

}
