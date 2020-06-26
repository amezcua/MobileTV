package net.byteabyte.mobiletv.core.di

import dagger.Module
import dagger.Provides
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker
import net.byteabyte.mobiletv.core.tvshows.ShowImagePickerImpl

@Module(includes = [CoreModule.Internal::class])
class CoreModule {
    @Module
    internal class Internal {
        @Provides
        fun provideShowImagePicker(): ShowImagePicker = ShowImagePickerImpl()
    }
}