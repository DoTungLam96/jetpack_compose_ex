import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Tạo DataStore

val Context.dataStore by preferencesDataStore(name = "app_prefs")
class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {



    private val dataStore = context.dataStore
    // 🛠 Kiểm tra key có tồn tại không (an toàn hơn)
    suspend fun containsKey(key: Preferences.Key<*>): Boolean {
        return dataStore.data.map { it.contains(key) }.first()
    }

    // ✅ Lưu danh sách String dưới dạng Set<String>
    suspend fun putStringList(key: Preferences.Key<Set<String>>, value: List<String>) {
        dataStore.edit { prefs ->
            prefs[key] = value.toSet()
        }
    }

    // ✅ Lấy danh sách String
    fun getStringList(key: Preferences.Key<Set<String>>): Flow<List<String>> {
        return dataStore.data.map { prefs ->
            prefs[key]?.toList() ?: emptyList()
        }
    }

    // ✅ Lưu Boolean
    suspend fun putBool(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    // ✅ Lấy Boolean (thêm giá trị mặc định)
    fun getBool(key: Preferences.Key<Boolean>, defaultValue: Boolean = false): Flow<Boolean> {
        return dataStore.data.map { prefs ->
            prefs[key] ?: defaultValue
        }
    }

    // ✅ Lưu String
    suspend fun putString(key: Preferences.Key<String>, value: String) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    // ✅ Lấy String (thêm giá trị mặc định)
    fun getString(key: Preferences.Key<String>, defaultValue: String = ""): Flow<String> {
        return dataStore.data.map { prefs ->
            prefs[key] ?: defaultValue
        }
    }

    // ✅ Lưu Int
    suspend fun putInt(key: Preferences.Key<Int>, value: Int) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    // ✅ Lấy Int (thêm giá trị mặc định)
    fun getInt(key: Preferences.Key<Int>, defaultValue: Int = 0): Flow<Int> {
        return dataStore.data.map { prefs ->
            prefs[key] ?: defaultValue
        }
    }

    // ✅ Xóa toàn bộ dữ liệu
    suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    // ✅ Xóa một key cụ thể
    suspend fun remove(key: Preferences.Key<*>) {
        dataStore.edit { prefs ->
            prefs.remove(key)
        }
    }
}