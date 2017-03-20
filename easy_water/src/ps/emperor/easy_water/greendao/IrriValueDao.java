package ps.emperor.easy_water.greendao;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table IRRI_VALUE.
*/
public class IrriValueDao extends AbstractDao<IrriValue, Long> {

    public static final String TABLENAME = "IRRI_VALUE";

    /**
     * Properties of entity IrriValue.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IrriValue = new Property(1, String.class, "irriValue", false, "IRRI_VALUE");
        public final static Property UserInfo = new Property(2, String.class, "userInfo", false, "USER_INFO");
        public final static Property UserPhone = new Property(3, String.class, "userPhone", false, "USER_PHONE");
        public final static Property CropName = new Property(4, String.class, "cropName", false, "CROP_NAME");
        public final static Property CropStime = new Property(5, String.class, "cropStime", false, "CROP_STIME");
        public final static Property Area = new Property(6, String.class, "area", false, "AREA");
        public final static Property ValueControlChanID = new Property(7, String.class, "valueControlChanID", false, "VALUE_CONTROL_CHAN_ID");
        public final static Property ValueControlID = new Property(8, String.class, "valueControlID", false, "VALUE_CONTROL_ID");
        public final static Property TotalIrriTime = new Property(9, String.class, "totalIrriTime", false, "TOTAL_IRRI_TIME");
        public final static Property IrriDuration = new Property(10, String.class, "irriDuration", false, "IRRI_DURATION");
        public final static Property IrriCount = new Property(11, String.class, "irriCount", false, "IRRI_COUNT");
        public final static Property IrriWater = new Property(12, String.class, "irriWater", false, "IRRI_WATER");
        public final static Property ValueControlSwitch = new Property(13, String.class, "valueControlSwitch", false, "VALUE_CONTROL_SWITCH");
        public final static Property IsAllocationGrowers = new Property(14, String.class, "isAllocationGrowers", false, "IS_ALLOCATION_GROWERS");
        public final static Property IsAllocationCrop = new Property(15, String.class, "isAllocationCrop", false, "IS_ALLOCATION_CROP");
        public final static Property IsAllocationGroup = new Property(16, String.class, "isAllocationGroup", false, "IS_ALLOCATION_GROUP");
        public final static Property GroupName = new Property(17, String.class, "groupName", false, "GROUP_NAME");
    };


    public IrriValueDao(DaoConfig config) {
        super(config);
    }
    
    public IrriValueDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'IRRI_VALUE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'IRRI_VALUE' TEXT," + // 1: irriValue
                "'USER_INFO' TEXT," + // 2: userInfo
                "'USER_PHONE' TEXT," + // 3: userPhone
                "'CROP_NAME' TEXT," + // 4: cropName
                "'CROP_STIME' TEXT," + // 5: cropStime
                "'AREA' TEXT," + // 6: area
                "'VALUE_CONTROL_CHAN_ID' TEXT," + // 7: valueControlChanID
                "'VALUE_CONTROL_ID' TEXT," + // 8: valueControlID
                "'TOTAL_IRRI_TIME' TEXT," + // 9: totalIrriTime
                "'IRRI_DURATION' TEXT," + // 10: irriDuration
                "'IRRI_COUNT' TEXT," + // 11: irriCount
                "'IRRI_WATER' TEXT," + // 12: irriWater
                "'VALUE_CONTROL_SWITCH' TEXT," + // 13: valueControlSwitch
                "'IS_ALLOCATION_GROWERS' TEXT," + // 14: isAllocationGrowers
                "'IS_ALLOCATION_CROP' TEXT," + // 15: isAllocationCrop
                "'IS_ALLOCATION_GROUP' TEXT," + // 16: isAllocationGroup
                "'GROUP_NAME' TEXT);"); // 17: groupName
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'IRRI_VALUE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, IrriValue entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String irriValue = entity.getIrriValue();
        if (irriValue != null) {
            stmt.bindString(2, irriValue);
        }
 
        String userInfo = entity.getUserInfo();
        if (userInfo != null) {
            stmt.bindString(3, userInfo);
        }
 
        String userPhone = entity.getUserPhone();
        if (userPhone != null) {
            stmt.bindString(4, userPhone);
        }
 
        String cropName = entity.getCropName();
        if (cropName != null) {
            stmt.bindString(5, cropName);
        }
 
        String cropStime = entity.getCropStime();
        if (cropStime != null) {
            stmt.bindString(6, cropStime);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(7, area);
        }
 
        String valueControlChanID = entity.getValueControlChanID();
        if (valueControlChanID != null) {
            stmt.bindString(8, valueControlChanID);
        }
 
        String valueControlID = entity.getValueControlID();
        if (valueControlID != null) {
            stmt.bindString(9, valueControlID);
        }
 
        String totalIrriTime = entity.getTotalIrriTime();
        if (totalIrriTime != null) {
            stmt.bindString(10, totalIrriTime);
        }
 
        String irriDuration = entity.getIrriDuration();
        if (irriDuration != null) {
            stmt.bindString(11, irriDuration);
        }
 
        String irriCount = entity.getIrriCount();
        if (irriCount != null) {
            stmt.bindString(12, irriCount);
        }
 
        String irriWater = entity.getIrriWater();
        if (irriWater != null) {
            stmt.bindString(13, irriWater);
        }
 
        String valueControlSwitch = entity.getValueControlSwitch();
        if (valueControlSwitch != null) {
            stmt.bindString(14, valueControlSwitch);
        }
 
        String isAllocationGrowers = entity.getIsAllocationGrowers();
        if (isAllocationGrowers != null) {
            stmt.bindString(15, isAllocationGrowers);
        }
 
        String isAllocationCrop = entity.getIsAllocationCrop();
        if (isAllocationCrop != null) {
            stmt.bindString(16, isAllocationCrop);
        }
 
        String isAllocationGroup = entity.getIsAllocationGroup();
        if (isAllocationGroup != null) {
            stmt.bindString(17, isAllocationGroup);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(18, groupName);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public IrriValue readEntity(Cursor cursor, int offset) {
        IrriValue entity = new IrriValue( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // irriValue
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userInfo
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // userPhone
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // cropName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // cropStime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // area
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // valueControlChanID
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // valueControlID
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // totalIrriTime
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // irriDuration
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // irriCount
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // irriWater
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // valueControlSwitch
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // isAllocationGrowers
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // isAllocationCrop
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // isAllocationGroup
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17) // groupName
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, IrriValue entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIrriValue(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserInfo(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserPhone(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCropName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCropStime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setArea(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setValueControlChanID(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setValueControlID(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTotalIrriTime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setIrriDuration(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setIrriCount(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setIrriWater(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setValueControlSwitch(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setIsAllocationGrowers(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setIsAllocationCrop(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setIsAllocationGroup(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setGroupName(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(IrriValue entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(IrriValue entity) {
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