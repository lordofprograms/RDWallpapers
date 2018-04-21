package com.crazzylab.rdwallpapers.presentation.global

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Михаил on 01.04.2018.
 */
class LimitCacheSizeGlideModule : GlideModule {
    private val SMALL_INTERNAL_STORAGE_THRESHOLD_GIB = 6
    private val DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB = 50 * 1024 * 1024

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // max available store is 25 mb and if data weight more all will be deleted automatically
        val totalGiB = getTotalBytesOfInternalStorage().toDouble() / 1024.0 / 1024.0 / 2048.0
        if (totalGiB < SMALL_INTERNAL_STORAGE_THRESHOLD_GIB) {
            builder.setDiskCache(InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB))
        }
    }

    override fun registerComponents(context: Context, glide: Glide) {}

    private fun getTotalBytesOfInternalStorage(): Long {
        val stat = StatFs(Environment.getDataDirectory().path)
        return when(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            true -> getTotalBytesOfInternalStorageWithStatFs(stat)
            else -> getTotalBytesOfInternalStorageWithStatFsPreJBMR2(stat)
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun getTotalBytesOfInternalStorageWithStatFs(stat: StatFs): Long = stat.totalBytes

    private fun getTotalBytesOfInternalStorageWithStatFsPreJBMR2(stat: StatFs): Long =
            stat.blockSize.toLong() * stat.blockCount

}