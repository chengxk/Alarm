package com.mumu.alarm.bean.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.mumu.alarm.bean.Alarm;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALARM".
*/
public class AlarmDao extends AbstractDao<Alarm, Long> {

    public static final String TABLENAME = "ALARM";

    /**
     * Properties of entity Alarm.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property RingPath = new Property(2, String.class, "ringPath", false, "RING_PATH");
        public final static Property RingName = new Property(3, String.class, "ringName", false, "RING_NAME");
        public final static Property RingType = new Property(4, Integer.class, "ringType", false, "RING_TYPE");
        public final static Property RemindMode = new Property(5, Integer.class, "remindMode", false, "REMIND_MODE");
        public final static Property Sleepy = new Property(6, Integer.class, "sleepy", false, "SLEEPY");
        public final static Property CloseAlarmMode = new Property(7, Integer.class, "closeAlarmMode", false, "CLOSE_ALARM_MODE");
        public final static Property AlarmCircle = new Property(8, byte[].class, "alarmCircle", false, "ALARM_CIRCLE");
    };


    public AlarmDao(DaoConfig config) {
        super(config);
    }
    
    public AlarmDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALARM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"RING_PATH\" TEXT," + // 2: ringPath
                "\"RING_NAME\" TEXT," + // 3: ringName
                "\"RING_TYPE\" INTEGER," + // 4: ringType
                "\"REMIND_MODE\" INTEGER," + // 5: remindMode
                "\"SLEEPY\" INTEGER," + // 6: sleepy
                "\"CLOSE_ALARM_MODE\" INTEGER," + // 7: closeAlarmMode
                "\"ALARM_CIRCLE\" BLOB);"); // 8: alarmCircle
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALARM\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Alarm entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String ringPath = entity.getRingPath();
        if (ringPath != null) {
            stmt.bindString(3, ringPath);
        }
 
        String ringName = entity.getRingName();
        if (ringName != null) {
            stmt.bindString(4, ringName);
        }
 
        Integer ringType = entity.getRingType();
        if (ringType != null) {
            stmt.bindLong(5, ringType);
        }
 
        Integer remindMode = entity.getRemindMode();
        if (remindMode != null) {
            stmt.bindLong(6, remindMode);
        }
 
        Integer sleepy = entity.getSleepy();
        if (sleepy != null) {
            stmt.bindLong(7, sleepy);
        }
 
        Integer closeAlarmMode = entity.getCloseAlarmMode();
        if (closeAlarmMode != null) {
            stmt.bindLong(8, closeAlarmMode);
        }
 
        byte[] alarmCircle = entity.getAlarmCircle();
        if (alarmCircle != null) {
            stmt.bindBlob(9, alarmCircle);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Alarm readEntity(Cursor cursor, int offset) {
        Alarm entity = new Alarm( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // ringPath
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // ringName
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // ringType
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // remindMode
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // sleepy
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // closeAlarmMode
            cursor.isNull(offset + 8) ? null : cursor.getBlob(offset + 8) // alarmCircle
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Alarm entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setRingPath(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRingName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRingType(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setRemindMode(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setSleepy(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setCloseAlarmMode(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setAlarmCircle(cursor.isNull(offset + 8) ? null : cursor.getBlob(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Alarm entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Alarm entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
