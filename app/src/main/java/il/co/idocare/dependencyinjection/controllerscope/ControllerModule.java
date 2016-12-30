package il.co.idocare.dependencyinjection.controllerscope;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;
import il.co.idocare.deviceinfo.GooglePlayServicesChecker;
import il.co.idocare.dialogs.DialogsManager;
import il.co.idocare.screens.navigationdrawer.NavigationDrawerController;
import il.co.idocare.users.UsersManager;
import il.co.idocare.users.UsersRetriever;
import il.co.idocare.utils.eventbusregistrator.EventBusRegistrator;
import il.co.idocare.utils.multithreading.MainThreadPoster;
import il.co.idocare.requests.retrievers.RequestsRetriever;
import il.co.idocare.useractions.cachers.UserActionCacher;
import il.co.idocare.screens.common.FrameHelper;
import il.co.idocare.requests.RequestsManager;
import il.co.idocare.utils.multithreading.BackgroundThreadPoster;
import il.co.idocare.networking.ServerSyncController;
import il.co.idocare.pictures.CameraAdapter;
import il.co.idocare.utils.Logger;

@Module
public class ControllerModule {

    private Activity mActivity;
    private FragmentManager mFragmentManager;

    public ControllerModule(@NonNull Activity activity, FragmentManager fragmentManager) {
        mActivity = activity;
        mFragmentManager = fragmentManager;
    }

    @Provides
    @ControllerScope
    Context context() {
        return mActivity;
    }

    @Provides
    @ControllerScope
    Activity activity() {
        return mActivity;
    }

    @Provides
    @ControllerScope
    FragmentManager fragmentManager() {
        return mFragmentManager;
    }

    @Provides
    @ControllerScope
    GooglePlayServicesChecker googlePlayServicesChecker(Activity activity) {
        return new GooglePlayServicesChecker(activity);
    }

    @Provides
    CameraAdapter cameraAdapter(Activity activity) {
        return new CameraAdapter(activity);
    }

    @Provides
    @ControllerScope
    FrameHelper frameHelper(FragmentManager fragmentManager) {
        return new FrameHelper(fragmentManager);
    }

    @Provides
    @ControllerScope
    DialogsManager dialogsManager(FragmentManager fragmentManager) {
        return new DialogsManager(fragmentManager);
    }

    @Provides
    @ControllerScope
    RequestsManager requestsManager(
            BackgroundThreadPoster backgroundThreadPoster,
            MainThreadPoster mainThreadPoster,
            UserActionCacher userActionCacher,
            RequestsRetriever requestsRetriever,
            Logger logger,
            ServerSyncController serverSyncController) {
        return new RequestsManager(backgroundThreadPoster, mainThreadPoster, userActionCacher,
                requestsRetriever, logger, serverSyncController);
    }

    @Provides
    UsersManager usersManager(UsersRetriever usersRetriever,
                              BackgroundThreadPoster backgroundThreadPoster,
                              MainThreadPoster mainThreadPoster) {
        return new UsersManager(usersRetriever, backgroundThreadPoster, mainThreadPoster);
    }


    @Provides
    EventBusRegistrator eventBusRegistrator(EventBus eventBus, Logger logger) {
        return new EventBusRegistrator(eventBus, logger);
    }

    @Provides
    NavigationDrawerController navigationDrawerController() {
        return (NavigationDrawerController) mActivity;
    }
}
