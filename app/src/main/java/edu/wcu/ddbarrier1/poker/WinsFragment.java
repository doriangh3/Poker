package edu.wcu.ddbarrier1.poker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Dorian Barrier
 *
 * Implements the functionality for the wins fragment which displayes each win amount
 * followed by the hand type associated with each hand type.
 */
public class WinsFragment extends Fragment {

    /**Names of each particular achievement. Get From XML!!!!*/
    String[] NAMES = {"Royal Flush","Straight Flush",
            "4 of a Kind","Full House",
            "Flush","Straight","3 of a Kind","2 Pair","Pair"};

    /**Amount of wins for each hand type*/
    String[] AMOUNT = {"1000","250  ","100  ","50    ","30    ","25    ","20    ","10    ","5      "};

    /**
     * Sets list view adapater and clickListener for list view.
     * When a list view is clicked it creates a new intent that starts the wins description activity
     * with the apporiate criteria
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_wins, container, false);

        final ListView listView = (ListView) v.findViewById(R.id.ListView1);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent nextActivity = null;
                switch (position) {
                    case 0:
                        nextActivity = new Intent(getActivity(), WinsDescActivity.class);
                        nextActivity.putExtra("fragType", 0);
                        break;
                    case 1:
                        nextActivity = new Intent(getActivity(), WinsDescActivity.class);
                        nextActivity.putExtra("fragType", 1);
                        break;
                    case 2:
                        nextActivity = new Intent(getActivity(), WinsDescActivity.class);
                        nextActivity.putExtra("fragType", 2);
                        break;
                    case 3:
                        nextActivity = new Intent(getActivity(), WinsDescActivity.class);
                        nextActivity.putExtra("fragType", 3);
                        break;
                    case 4:
                        nextActivity = new Intent(getActivity(), WinsDescActivity.class);
                        nextActivity.putExtra("fragType", 4);
                        break;
                    case 5:
                        nextActivity = new Intent(getActivity(), WinsDescActivity.class);
                        nextActivity.putExtra("fragType", 5);
                        break;
                    case 6:
                        nextActivity = new Intent(getActivity(), WinsDescActivity.class);
                        nextActivity.putExtra("fragType", 6);
                        break;
                    case 7:
                        nextActivity = new Intent(getActivity(), WinsDescActivity.class);
                        nextActivity.putExtra("fragType", 7);
                        break;
                    case 8:
                        nextActivity = new Intent(getActivity(), WinsDescActivity.class);
                        nextActivity.putExtra("fragType", 8);
                        break;
                    default:
                }

                getActivity().startActivity(nextActivity);

            }
        });//end onItemClick

        // Inflate the layout for this fragment
        return v;
    }//end onCreateView()


    /**
     * Class to create a custom adapter for the wins list view
     */
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return NAMES.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.activity_custom_list_view,null);

            TextView tv1 = (TextView) view.findViewById(R.id.winsAmount);
            TextView tv2 = (TextView) view.findViewById(R.id.winsType);

            tv1.setText(AMOUNT[i]);
            tv2.setText(NAMES[i]);

            return view;
        }
    }//end class BaseAdapter

}//end WinsFragment
