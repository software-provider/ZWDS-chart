package com.meowbox.progressions.db

import com.meowbox.progressions.Database
import com.squareup.sqldelight.db.SqlDriver

object Db {
    private var driverRef: SqlDriver? = null
    private var dbRef: Database? = null

    const val databaseName: String = "progressions.sqlite3"

    val ready: Boolean get() = dbRef != null
    val instance: Database get() = dbRef!!

    internal fun dbClear() {
        if (driverRef != null)
            driverRef!!.close()

        dbRef = null
        driverRef = null
    }

    fun dbSetup(driver: SqlDriver) {
        val db = createQueryWrapper(driver)
        driverRef = driver
        dbRef = db
    }
}
