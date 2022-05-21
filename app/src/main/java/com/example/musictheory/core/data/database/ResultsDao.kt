package com.example.musictheory.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musictheory.core.data.model.Result
import com.example.musictheory.trainingtest.data.model.MusicTestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultsDao {
    /**
     * Временно не решаем конфикты, а закрываем на них глаза
     *
     * @return возвращает id, который задается автоматически
     *
     * и отправляется на ResultFragment
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveResult(result: Result): Long

    /**
     * Временно не решаем конфикты, а закрываем на них глаза
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTest(test: MusicTestEntity)

    @Query("SELECT * FROM results WHERE id = :id")
    fun getResult(id: Long): Flow<List<Result>>

    /**
     * @param id
     *
     * У mongodb автоматическая генерация через специальный класс,
     *
     * поэтому использует строку в качестве id
     */
    @Query("SELECT * FROM tests WHERE id = :id")
    fun getTest(id: String): Flow<List<MusicTestEntity>>
}
