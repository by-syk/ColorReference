package com.by_syk.mdcolor.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by By_syk on 2016-04-13.
 */
public class SP {
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    public static final String TAG_LAUNCH = "times_launch";

    /**
     * @param enable_stats Enable recording times of launching if true.
     */
    public SP(Context context, boolean enable_stats) {
        if (context == null) {
            return;
        }

        sharedPreferences = context.getSharedPreferences(context
                .getPackageName(), Context.MODE_PRIVATE);

        // Record the times of launching.
        recordLaunchTimes(enable_stats);
    }

    public SP(Context context) {
        this(context, true);
    }

    /**
     * @param enable_stats If true, increase 1, or do nothing.
     */
    private void recordLaunchTimes(boolean enable_stats) {
        if (enable_stats) {
            save(TAG_LAUNCH, getInt(TAG_LAUNCH) + 1);
        }
    }

    public int getLaunchTimes() {
        return getInt(TAG_LAUNCH);
    }

    /**
     * Call {@link #put(String, boolean)} and {@link #save()}.
     */
    public boolean save(String tag, boolean value) {
        if (sharedPreferences == null || tag == null) {
            return false;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putBoolean(tag, value);

        return save();
    }

    public boolean reverseAndSave(String tag, boolean def) {
        if (sharedPreferences == null || tag == null) {
            return false;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putBoolean(tag, !getBoolean(tag, def));

        return save();
    }

    public boolean reverseAndSave(String tag) {
        return reverseAndSave(tag, false);
    }

    /**
     * Set a boolean value in the preferences editor, to be written back once
     * {@link #save()} is called.
     *
     * @param tag The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same SP object, so you can
     * chain put calls together.
     */
    public SP put(String tag, boolean value) {
        if (sharedPreferences == null || tag == null) {
            return this;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putBoolean(tag, value);

        return this;
    }

    /**
     * Call {@link #put(String, int)} and {@link #save()}.
     */
    public boolean save(String tag, int value) {
        if (sharedPreferences == null || tag == null) {
            return false;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putInt(tag, value);

        return save();
    }

    /**
     * Set an int value in the preferences editor, to be written back once
     * {@link #save()} is called.
     *
     * @param tag The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same SP object, so you can
     * chain put calls together.
     */
    public SP put(String tag, int value) {
        if (sharedPreferences == null || tag == null) {
            return this;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putInt(tag, value);

        return this;
    }

    /**
     * Call {@link #put(String, long)} and {@link #save()}.
     */
    public boolean save(String tag, long value) {
        if (sharedPreferences == null || tag == null) {
            return false;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putLong(tag, value);

        return save();
    }

    /**
     * Set a long value in the preferences editor, to be written back once
     * {@link #save()} is called.
     *
     * @param tag The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same SP object, so you can
     * chain put calls together.
     */
    public SP put(String tag, long value) {
        if (sharedPreferences == null || tag == null) {
            return this;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putLong(tag, value);

        return this;
    }

    /**
     * Call {@link #put(String, float)} and {@link #save()}.
     */
    public boolean save(String tag, float value) {
        if (sharedPreferences == null || tag == null) {
            return false;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putFloat(tag, value);

        return save();
    }

    /**
     * Set a float value in the preferences editor, to be written back once
     * {@link #save()} is called.
     *
     * @param tag The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same SP object, so you can
     * chain put calls together.
     */
    public SP put(String tag, float value) {
        if (sharedPreferences == null || tag == null) {
            return this;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putFloat(tag, value);

        return this;
    }

    /**
     * Call {@link #put(String, String)} and {@link #save()}.
     */
    public boolean save(String tag, String value) {
        if (sharedPreferences == null || tag == null) {
            return false;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putString(tag, value);

        return save();
    }

    /**
     * Set a String value in the preferences editor, to be written back once
     * {@link #save()} is called.
     *
     * @param tag The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same SP object, so you can
     * chain put calls together.
     */
    public SP put(String tag, String value) {
        if (sharedPreferences == null || tag == null) {
            return this;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putString(tag, value);

        return this;
    }

    /**
     * Save your preferences changes back from this Editor to the
     * {@link SharedPreferences} object it is editing.
     *
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    @TargetApi(9)
    public boolean save() {
        if (editor == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= 9) {
            editor.apply();
            return true;
        }
        return editor.commit();
    }

    /**
     * Retrieve a boolean value from the preferences.
     *
     * @param tag The name of the preference to retrieve.
     * @param def Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or def.
     */
    public boolean getBoolean(String tag, boolean def) {
        return sharedPreferences != null && tag != null
                && sharedPreferences.getBoolean(tag, def);
    }

    /**
     * Retrieve a boolean value from the preferences.
     *
     * @param tag The name of the preference to retrieve.
     *
     * @return Returns the preference value if it exists, or false.
     */
    public boolean getBoolean(String tag) {
        return getBoolean(tag, false);
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param tag The name of the preference to retrieve.
     * @param def Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or def.
     */
    public int getInt(String tag, int def) {
        if (sharedPreferences == null || tag == null) {
            return 0;
        }

        return sharedPreferences.getInt(tag, def);
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param tag The name of the preference to retrieve.
     *
     * @return Returns the preference value if it exists, or 0.
     */
    public int getInt(String tag) {
        return getInt(tag, 0);
    }

    /**
     * Retrieve a long value from the preferences.
     *
     * @param tag The name of the preference to retrieve.
     * @param def Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or def.
     */
    public long getLong(String tag, long def) {
        if (sharedPreferences == null || tag == null) {
            return 0L;
        }

        return sharedPreferences.getLong(tag, def);
    }

    /**
     * Retrieve a long value from the preferences.
     *
     * @param tag The name of the preference to retrieve.
     *
     * @return Returns the preference value if it exists, or 0L.
     */
    public long getLong(String tag) {
        return getLong(tag, 0L);
    }

    /**
     * Retrieve a float value from the preferences.
     *
     * @param tag The name of the preference to retrieve.
     * @param def Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or def.
     */
    public float getFloat(String tag, float def) {
        if (sharedPreferences == null || tag == null)  {
            return 0.0f;
        }

        return sharedPreferences.getFloat(tag, def);
    }

    /**
     * Retrieve a float value from the preferences.
     *
     * @param tag The name of the preference to retrieve.
     *
     * @return Returns the preference value if it exists, or 0.0f.
     */
    public float getFloat(String tag) {
        return getFloat(tag, 0.0f);
    }

    /**
     * Retrieve a String value from the preferences.
     *
     * @param tag The name of the preference to retrieve.
     * @param def Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or def.
     */
    public String getString(String tag, String def) {
        if (sharedPreferences == null || tag == null) {
            return "";
        }

        return sharedPreferences.getString(tag, def);
    }

    /**
     * Retrieve a String value from the preferences.
     *
     * @param tag The name of the preference to retrieve.
     *
     * @return Returns the preference value if it exists, or "".
     */
    public String getString(String tag) {
        return getString(tag, "");
    }

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param tag The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     *         otherwise false.
     */
    public boolean contains(String tag) {
        return sharedPreferences != null && tag != null
                && sharedPreferences.contains(tag);
    }

    /**
     * Call {@link #remove(String)} and {@link #save()}.
     */
    public boolean delete(String tag) {
        if (sharedPreferences == null || tag == null) {
            return false;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.remove(tag);

        return save();
    }

    /**
     * Mark in the editor that a preference value should be removed, which
     * will be done in the actual preferences once {@link #save()} is
     * called.
     *
     * @param tag The name of the preference to remove.
     *
     * @return Returns a reference to the same SP object, so you can
     * chain put calls together.
     */
    public SP remove(String tag) {
        if (sharedPreferences == null || tag == null) {
            return this;
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.remove(tag);

        return this;
    }
}