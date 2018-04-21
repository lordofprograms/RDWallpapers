package com.crazzylab.rdwallpapers.model.data.db.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Михаил on 24.08.2017.
 */

open class ImageModel : RealmObject() {

    @PrimaryKey
    open var id: Long? = null
    open var image: String? = null
    open var name: String? = null
    open var fileName: String? = null

}