package org.bitharis.panos.hkrcaretaker;

import android.support.v4.app.Fragment;

/**
 * Created by panos on 12/16/2016.
 */

public interface FragmentCommunicator {
        public void replaceFragment(Fragment f);
        public void passStrings(String... params);

}
