package net.programistka.shoppingadvisor.wizard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.programistka.shoppingadvisor.R;

public class SevenDaysFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Wizard - Step 1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wizard_fragment, container, false);
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        SevenDaysFragment fragment = new SevenDaysFragment();
////        FragmentTransaction transaction = getFragmentManager().beginTransaction();
////        transaction.add(R.id.wizard_container, fragment, "first");
////        transaction.addToBackStack(null);
////        transaction.commit();
//    }
}
