package com.proteam.happysadapp.di.application;

import androidx.lifecycle.ViewModel;

import com.proteam.happysadapp.ui.HomeViewModel;
import com.tribe.fitness.base.viewmodel.BaseViewModel;
import com.tribe.fitness.base.viewmodel.ViewModelProviderFactory;
import com.tribe.fitness.repository.BaseRepository;
import com.tribe.fitness.repository.onboarding.companiondevice.HomeRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import javax.inject.Provider;
import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

/**
 * Created by Mohd Irfan on 30/08/20.
 * email -  mohd.irfan@bambooapp.com
 */
@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelProviderFactory getViewModelProviderFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providesMap){
        return new ViewModelProviderFactory(providesMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(BaseViewModel.class)
    ViewModel getBaseViewModel(BaseRepository repository) {
        return new BaseViewModel(repository);
    }

    @Provides
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    ViewModel getHomeViewModel(HomeRepository repository) {
        return new HomeViewModel(repository);
    }

}
