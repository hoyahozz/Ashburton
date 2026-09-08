package com.hoyahozz.ashburton.testing

import androidx.room3.Dao
import androidx.room3.Database
import androidx.room3.Entity
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.PrimaryKey
import androidx.room3.Query
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.room3.Transaction
import androidx.sqlite.driver.AndroidSQLiteDriver
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Entity(tableName = "test_stories")
data class TestStoryEntity(
  @PrimaryKey val id: String,
  val title: String,
  val publishedAtEpochSeconds: Long,
)

@Dao
interface TestStoryDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsertAll(stories: List<TestStoryEntity>)

  @Query("DELETE FROM test_stories")
  suspend fun deleteAll()

  @Query(
    """
    SELECT * FROM test_stories
    ORDER BY publishedAtEpochSeconds DESC, id ASC
    """,
  )
  suspend fun newestFirst(): List<TestStoryEntity>

  @Transaction
  suspend fun replaceAll(stories: List<TestStoryEntity>) {
    deleteAll()
    upsertAll(stories)
  }
}

@Database(
  entities = [TestStoryEntity::class],
  version = 1,
  exportSchema = true,
)
abstract class TestStoriesDatabase : RoomDatabase() {
  abstract fun storyDao(): TestStoryDao
}

private data class TestStory(val id: String, val title: String, val publishedAtEpochSeconds: Long)

private class TestStoryRepository(private val storyDao: TestStoryDao) {
  suspend fun replaceStories(stories: List<TestStory>) {
    storyDao.replaceAll(stories.map(TestStory::toEntity))
  }

  suspend fun storiesNewestFirst(): List<TestStory> =
    storyDao.newestFirst().map(TestStoryEntity::toStory)
}

private fun TestStory.toEntity() = TestStoryEntity(
  id = id,
  title = title,
  publishedAtEpochSeconds = publishedAtEpochSeconds,
)

private fun TestStoryEntity.toStory() = TestStory(
  id = id,
  title = title,
  publishedAtEpochSeconds = publishedAtEpochSeconds,
)

@RunWith(AndroidJUnit4::class)
class StoryRepositoryRoomIntegrationTest {
  private lateinit var database: TestStoriesDatabase
  private lateinit var repository: TestStoryRepository

  @Before
  fun createDatabase() {
    database =
      Room.inMemoryDatabaseBuilder<TestStoriesDatabase>()
        .setDriver(AndroidSQLiteDriver())
        .build()
    repository = TestStoryRepository(database.storyDao())
  }

  @After
  fun closeDatabase() {
    database.close()
  }

  @Test
  fun replacingStoriesRemovesStaleRowsAndReturnsDeterministicNewestFirst() = runTest {
    repository.replaceStories(
      listOf(
        TestStory("stale", "Old story", 100),
        TestStory("retained", "Earlier title", 200),
      ),
    )

    repository.replaceStories(
      listOf(
        TestStory("second", "Second at same time", 300),
        TestStory("retained", "Updated title", 400),
        TestStory("first", "First at same time", 300),
      ),
    )

    assertEquals(
      listOf(
        TestStory("retained", "Updated title", 400),
        TestStory("first", "First at same time", 300),
        TestStory("second", "Second at same time", 300),
      ),
      repository.storiesNewestFirst(),
    )
  }
}
