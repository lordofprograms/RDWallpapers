package com.crazzylab.rdwallpapers.model.data.db.migration

import com.crazzylab.rdwallpapers.Constants
import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Created by Михаил on 24.08.2017.
 */
class RealmImageMigration: RealmMigration{

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        realm.schema.create(Constants.IMAGE_MODEL)
                .addField("image", String::class.java)
                .addField("name", String::class.java)
                .addField("fileName", String::class.java)
    }

    override fun hashCode(): Int = RealmImageMigration::class.java.hashCode()

    override fun equals(other: Any?): Boolean = other != null && other is RealmImageMigration

}