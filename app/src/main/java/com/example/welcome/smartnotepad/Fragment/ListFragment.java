package com.example.welcome.smartnotepad.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.welcome.smartnotepad.Adapter.ListNoteAdapter;
import com.example.welcome.smartnotepad.DBManager.NotepadManager;
import com.example.welcome.smartnotepad.DBModel.NotepadModel;
import com.example.welcome.smartnotepad.R;
import com.example.welcome.smartnotepad.StartActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView lstNoteData;
    View RootView;
    ArrayList<NotepadModel> objListNoteData;
    ListNoteAdapter objListAdapterNote;
    NotepadManager objNotepadManager;
    Fragment currentFragment;
    FragmentManager manager;
    FragmentTransaction transaction;
    int count=0;
    ArrayList<Integer> arrDeletedItemPosition;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getActivity().getFragmentManager().addOnBackStackChangedListener(this);
        ((StartActivity)getActivity()).hideUpButton();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w("*** start of CreateView","**");
        Initialization();
        setHasOptionsMenu(true);
        RootView = inflater.inflate(R.layout.fragment_start, container, false);
        lstNoteData= (ListView) RootView.findViewById(R.id.listNote);
        objNotepadManager=new NotepadManager(getActivity().getBaseContext());
        objListNoteData=objNotepadManager.getAllNoteTitle();
        objListAdapterNote=new ListNoteAdapter(getActivity().getBaseContext(),objListNoteData);
        lstNoteData.setAdapter(objListAdapterNote);

        lstNoteData.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        lstNoteData.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                int selectedId=objListNoteData.get(i).getId();
                //Toast.makeText(getActivity().getBaseContext(),"title: "+ lstNoteData.getCheckedItemCount() , Toast.LENGTH_SHORT).show();
                actionMode.setTitle(lstNoteData.getCheckedItemCount()+" items selected to delete");
                objListAdapterNote.toggleSelection(i);
                arrDeletedItemPosition.add(selectedId);
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater=actionMode.getMenuInflater();
                inflater.inflate(R.menu.main,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.mnDlt:
                        for(int i :arrDeletedItemPosition) {
                            if(objNotepadManager.deleteData(i))
                                Toast.makeText(getActivity().getBaseContext(), "deleted"+i +  "", Toast.LENGTH_SHORT).show();
                        }
                        arrDeletedItemPosition.clear();
                        actionMode.finish();
                        currentFragment = new ListFragment();
                        manager = getFragmentManager();
                        transaction = manager.beginTransaction();
                        transaction.replace(R.id.llFragmentHolder, currentFragment);
                        transaction.commit();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });

        lstNoteData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity().getBaseContext(),"position: "+ i , Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                TextView txtview = ((TextView) view.findViewById(R.id.tv_note_id));
                int itemId =Integer.parseInt(txtview.getText().toString());
                //  Toast.makeText(getActivity().getBaseContext(),"title: "+ itemId , Toast.LENGTH_SHORT).show();
                bundle.putInt("modifyId", itemId);
                currentFragment.setArguments(bundle);
                transaction = manager.beginTransaction();
                transaction.replace(R.id.llFragmentHolder, currentFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        // Inflate the layout for this fragment
        Log.w("*** out of CreateView","**");
        return RootView;
    }

    private void Initialization() {
        currentFragment = new EntryFragment();
        manager = getFragmentManager();
        arrDeletedItemPosition=new  ArrayList();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onBackStackChanged() {
        if(getActivity().getFragmentManager().getBackStackEntryCount() < 1) {
            ((StartActivity)getActivity()).hideUpButton();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
