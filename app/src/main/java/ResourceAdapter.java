import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by admin on 3/11/2018.
 */

public class ResourceAdapter extends FragmentPagerAdapter {
    Context rCont;
    public ResourceAdapter(Context context, FragmentManager fm){
        super(fm);
        rCont = context;
    }
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
